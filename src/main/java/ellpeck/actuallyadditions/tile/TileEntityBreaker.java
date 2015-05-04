package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityBreaker extends TileEntityInventoryBase{

    public static class TileEntityPlacer extends TileEntityBreaker{

        public TileEntityPlacer(){
            super(9, "placer");
            this.isPlacer = true;
        }

    }

    public boolean isPlacer;

    private final int timeNeeded = ConfigIntValues.BREAKER_TIME_NEEDED.getValue();
    private int currentTime;

    public TileEntityBreaker(int slots, String name){
        super(slots, name);
    }

    public TileEntityBreaker(){
        super(9, "breaker");
        this.isPlacer = false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        ForgeDirection sideToManipulate = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

                        ChunkCoordinates coordsBlock = WorldUtil.getCoordsFromSide(sideToManipulate, xCoord, yCoord, zCoord);
                        if(coordsBlock != null){
                            Block blockToBreak = worldObj.getBlock(coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ);
                            if(!this.isPlacer && blockToBreak != null && blockToBreak.getBlockHardness(worldObj, coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ) > -1.0F){
                                ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
                                int meta = worldObj.getBlockMetadata(coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ);
                                drops.addAll(blockToBreak.getDrops(worldObj, coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ, meta, 0));

                                if(this.addToInventory(drops, false)){
                                    worldObj.playAuxSFX(2001, coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ, Block.getIdFromBlock(blockToBreak) + (meta << 12));
                                    WorldUtil.breakBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord);
                                    this.addToInventory(drops, true);
                                    this.markDirty();
                                }
                            }
                            else if(this.isPlacer && (worldObj.getBlock(coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ).isReplaceable(worldObj, coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ))){
                                ItemStack removeFalse = this.removeFromInventory(false);
                                if(removeFalse != null && Block.getBlockFromItem(removeFalse.getItem()) != blockToBreak && WorldUtil.placeBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, removeFalse)){
                                    this.removeFromInventory(true);
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
        compound.setInteger("CurrentTime", this.currentTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
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
