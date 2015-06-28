package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDropper extends TileEntityInventoryBase{

    private final int timeNeeded = ConfigIntValues.DROPPER_TIME_NEEDED.getValue();
    private int currentTime;

    public TileEntityDropper(int slots, String name){
        super(slots, name);
    }

    public TileEntityDropper(){
        super(9, "dropper");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
                if(this.currentTime > 0){
                    this.currentTime--;
                    if(this.currentTime <= 0){
                        if(this.removeFromInventory(false) != null){
                            ItemStack stack = this.removeFromInventory(true);
                            stack.stackSize = 1;
                            WorldUtil.dropItemAtSide(ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)), worldObj, xCoord, yCoord, zCoord, stack);
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

    public ItemStack removeFromInventory(boolean actuallyDo){
        for(int i = 0; i < this.slots.length; i++){
            if(this.slots[i] != null){
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
        return true;
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
