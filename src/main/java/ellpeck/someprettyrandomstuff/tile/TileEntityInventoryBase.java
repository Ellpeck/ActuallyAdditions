package ellpeck.someprettyrandomstuff.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileEntityInventoryBase extends TileEntityBase implements IInventory{

    public ItemStack slots[];
    public String name;

    public TileEntityInventoryBase(int slots, String name){
        this.initializeSlots(slots);
        this.name = name;
    }

    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        NBTTagList tagList = new NBTTagList();
        for(int currentIndex = 0; currentIndex < slots.length; ++currentIndex){
            if (slots[currentIndex] != null){
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte)currentIndex);
                slots[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        compound.setTag("Items", tagList);
    }

    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList tagList = compound.getTagList("Items", 10);
        for (int i = 0; i < tagList.tagCount(); ++i){
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < slots.length){
                slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    public int getInventoryStackLimit(){
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player){
        return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
    }

    public boolean isItemValidForSlot(int i, ItemStack stack){
        return false;
    }

    public ItemStack getStackInSlotOnClosing(int i){
        return getStackInSlot(i);
    }

    public void setInventorySlotContents(int i, ItemStack stack){
        this.slots[i] = stack;
    }

    public int getSizeInventory(){
        return slots.length;
    }

    public ItemStack getStackInSlot(int i){
        return slots[i];
    }

    public ItemStack decrStackSize(int i, int j){
        if (slots[i] != null) {
            ItemStack stackAt;
            if (slots[i].stackSize <= j) {
                stackAt = slots[i];
                slots[i] = null;
                return stackAt;
            } else {
                stackAt = slots[i].splitStack(j);
                if (slots[i].stackSize == 0)
                    slots[i] = null;
                return stackAt;
            }
        }
        return null;
    }

    public void initializeSlots(int itemAmount){
        this.slots = new ItemStack[itemAmount];
    }

    public String getInventoryName(){
        return this.name;
    }

    public boolean hasCustomInventoryName(){
        return false;
    }

    public void openInventory(){

    }

    public void closeInventory(){

    }
}
