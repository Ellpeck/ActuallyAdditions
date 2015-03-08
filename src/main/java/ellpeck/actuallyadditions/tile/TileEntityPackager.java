package ellpeck.actuallyadditions.tile;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPackager extends TileEntityInventoryBase{

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;

    public int currentWaitTime;
    public final int waitTime = 30;

    public boolean isTwoByTwo = false;

    public TileEntityPackager(){
        super(2, "tileEntityPackager");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(){
        if(!worldObj.isRemote){
            if(this.canBeConverted(this.slots[SLOT_INPUT])){

                ItemStack output = findMatchingRecipe(this.slots[SLOT_INPUT]);
                if(this.slots[SLOT_OUTPUT] == null || (this.slots[SLOT_OUTPUT].isItemEqual(output) && this.slots[SLOT_OUTPUT].stackSize <= this.slots[SLOT_OUTPUT].getMaxStackSize()-output.stackSize)){

                    if(this.currentWaitTime >= this.waitTime){
                        this.currentWaitTime = 0;

                        if (this.slots[SLOT_OUTPUT] == null) this.slots[SLOT_OUTPUT] = output.copy();
                        else if(this.slots[SLOT_OUTPUT].getItem() == output.getItem()) this.slots[SLOT_OUTPUT].stackSize += output.stackSize;

                        this.slots[SLOT_INPUT].stackSize--;
                        if (this.slots[SLOT_INPUT].stackSize <= 0) this.slots[SLOT_INPUT] = null;
                    }
                    else this.currentWaitTime++;
                }
            }
        }
    }

    public boolean canBeConverted(ItemStack stack){
        return findMatchingRecipe(stack) != null;
    }

    public ItemStack findMatchingRecipe(ItemStack stack){
        int k = this.isTwoByTwo ? 2 : 3;

        TheInventoryCrafting craftMatrix = new TheInventoryCrafting(null, k, k);
        for(int i = 0; i < craftMatrix.getSizeInventory(); i++){
            craftMatrix.setInventorySlotContents(i, stack.copy());
        }
        return CraftingManager.getInstance().findMatchingRecipe(craftMatrix, this.worldObj);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("CurrentWaitTime", this.currentWaitTime);
        compound.setBoolean("EnabledSlots", this.isTwoByTwo);

    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.currentWaitTime = compound.getInteger("CurrentWaitTime");
        this.isTwoByTwo = compound.getBoolean("EnabledSlots");
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack){
        return i == SLOT_INPUT;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side){
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side){
        return slot == SLOT_OUTPUT;
    }

    public static class TheInventoryCrafting extends InventoryCrafting{

        private ItemStack[] stackList;

        public TheInventoryCrafting(Container container, int x, int y){
            super(container, x, y);
            this.stackList = new ItemStack[x*y];
        }

        @Override
        public void setInventorySlotContents(int index, ItemStack stack){
            this.stackList[index] = stack;
        }

        @Override
        public ItemStack getStackInSlot(int position){
            return this.stackList[position];
        }

        @Override
        public int getSizeInventory(){
            return this.stackList.length;
        }
    }
}
