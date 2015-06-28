package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
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

                                if(addToInventory(this.slots, drops, false)){
                                    worldObj.playAuxSFX(2001, coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ, Block.getIdFromBlock(blockToBreak) + (meta << 12));
                                    WorldUtil.breakBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord);
                                    addToInventory(this.slots, drops, true);
                                    this.markDirty();
                                }
                            }
                            else if(this.isPlacer && worldObj.getBlock(coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ).isReplaceable(worldObj, coordsBlock.posX, coordsBlock.posY, coordsBlock.posZ)){
                                int theSlot = testInventory(this.slots);
                                this.setInventorySlotContents(theSlot, WorldUtil.placeBlockAtSide(sideToManipulate, worldObj, xCoord, yCoord, zCoord, this.slots[theSlot]));
                                if(this.slots[theSlot] != null && this.slots[theSlot].stackSize <= 0) this.slots[theSlot] = null;
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

    public static boolean addToInventory(ItemStack[] slots, ArrayList<ItemStack> stacks, boolean actuallyDo){
        int working = 0;
        for(ItemStack stack : stacks){
            for(int i = 0; i < slots.length; i++){
                if(slots[i] == null || (slots[i].isItemEqual(stack) && slots[i].stackSize <= stack.getMaxStackSize()-stack.stackSize)){
                    working++;
                    if(actuallyDo){
                        if(slots[i] == null) slots[i] = stack.copy();
                        else slots[i].stackSize += stack.stackSize;
                    }
                    break;
                }
            }
        }
        return working >= stacks.size();
    }

    public static int testInventory(ItemStack[] slots){
        for(int i = 0; i < slots.length; i++){
            if(slots[i] != null){
                return i;
            }
        }
        return 0;
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
