package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

import java.util.ArrayList;

public class TileEntityBreaker extends TileEntityInventoryBase{

    private boolean isPlacer;

    private final int timeNeeded = ConfigIntValues.BREAKER_TIME_NEEDED.getValue();
    private int currentTime;

    @SuppressWarnings("unused")
    public TileEntityBreaker(){
        super(9, "");
    }

    public TileEntityBreaker(boolean isPlacer){
        super(9, isPlacer ? "placer" : "breaker");
        this.isPlacer = isPlacer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        int sideToBreak = -1;

                        int metaOfCurrentBlock = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                        if(metaOfCurrentBlock == 0) sideToBreak = 1;
                        else if(metaOfCurrentBlock == 1) sideToBreak = 0;
                        else if(metaOfCurrentBlock == 2) sideToBreak = 2;
                        else if(metaOfCurrentBlock == 3) sideToBreak = 4;
                        else if(metaOfCurrentBlock == 4) sideToBreak = 5;
                        else if(metaOfCurrentBlock == 5) sideToBreak = 3;

                        ChunkCoordinates coordsOfBlockToBreak = WorldUtil.getCoordsFromSide(sideToBreak, xCoord, yCoord, zCoord);
                        if(coordsOfBlockToBreak != null){
                            Block blockToBreak = worldObj.getBlock(coordsOfBlockToBreak.posX, coordsOfBlockToBreak.posY, coordsOfBlockToBreak.posZ);
                            if(!this.isPlacer && blockToBreak != null && blockToBreak.getBlockHardness(worldObj, coordsOfBlockToBreak.posX, coordsOfBlockToBreak.posY, coordsOfBlockToBreak.posZ) > -1.0F){
                                ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                                int meta = worldObj.getBlockMetadata(coordsOfBlockToBreak.posX, coordsOfBlockToBreak.posY, coordsOfBlockToBreak.posZ);
                                drops.addAll(blockToBreak.getDrops(worldObj, coordsOfBlockToBreak.posX, coordsOfBlockToBreak.posY, coordsOfBlockToBreak.posZ, meta, 0));

                                if(this.addToInventory(drops, false)){
                                    worldObj.playAuxSFX(2001, coordsOfBlockToBreak.posX, coordsOfBlockToBreak.posY, coordsOfBlockToBreak.posZ, Block.getIdFromBlock(blockToBreak) + (meta << 12));
                                    WorldUtil.breakBlockAtSide(sideToBreak, worldObj, xCoord, yCoord, zCoord);
                                    this.addToInventory(drops, true);
                                    this.markDirty();
                                }
                            }
                            else if(this.isPlacer && (worldObj.getBlock(coordsOfBlockToBreak.posX, coordsOfBlockToBreak.posY, coordsOfBlockToBreak.posZ).isReplaceable(worldObj, coordsOfBlockToBreak.posX, coordsOfBlockToBreak.posY, coordsOfBlockToBreak.posZ))){
                                ItemStack removeFalse = this.removeFromInventory(false);
                                if(removeFalse != null && Block.getBlockFromItem(removeFalse.getItem()) != blockToBreak && Block.getBlockFromItem(removeFalse.getItem()).canPlaceBlockAt(worldObj, coordsOfBlockToBreak.posX, coordsOfBlockToBreak.posY, coordsOfBlockToBreak.posZ)){
                                    ItemStack stack = this.removeFromInventory(true);
                                    //TODO insert sound effect
                                    WorldUtil.placeBlockAtSide(sideToBreak, worldObj, xCoord, yCoord, zCoord, stack);
                                }
                            }
                        }
                    }
                }
                else this.currentTime = this.timeNeeded;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setBoolean("IsPlacer", this.isPlacer);
        compound.setString("Name", this.name);
        compound.setInteger("CurrentTime", this.currentTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.isPlacer = compound.getBoolean("IsPlacer");
        this.name = compound.getString("Name");
        this.currentTime = compound.getInteger("CurrentTime");
    }

    public boolean addToInventory(ArrayList<ItemStack> stacks, boolean actuallyDo){
        int working = 0;
        for(ItemStack stack : stacks){
            for(int i = 0; i < this.slots.length; i++){
                if(this.slots[i] == null || (this.slots[i].isItemEqual(stack) && this.slots[i].stackSize <= stack.getMaxStackSize()-stack.stackSize)){
                    working++;
                    if(actuallyDo){
                        if(this.slots[i] == null) this.slots[i] = stack.copy();
                        else this.slots[i].stackSize += stack.stackSize;
                    }
                    break;
                }
            }
        }
        return working >= stacks.size();
    }

    public ItemStack removeFromInventory(boolean actuallyDo){
        for(int i = 0; i < this.slots.length; i++){
            if(this.slots[i] != null && !(Block.getBlockFromItem(this.slots[i].getItem()) instanceof BlockAir)){
                ItemStack slot = this.slots[i].copy();
                if(actuallyDo){
                    this.slots[i].stackSize--;
                    if(this.slots[i].stackSize <= 0) this.slots[i] = null;
                }
                return slot;
            }
        }
        return null;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return this.isPlacer;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return true;
    }

}
