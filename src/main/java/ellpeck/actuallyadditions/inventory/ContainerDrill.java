package ellpeck.actuallyadditions.inventory;

import cofh.api.energy.IEnergyContainerItem;
import ellpeck.actuallyadditions.inventory.slot.SlotImmovable;
import ellpeck.actuallyadditions.items.ItemDrill;
import ellpeck.actuallyadditions.items.ItemDrillUpgrade;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@InventoryContainer
public class ContainerDrill extends Container{

    private static final int SLOT_AMOUNT = 5;

    private InventoryDrill drillInventory = new InventoryDrill();
    private InventoryPlayer inventory;

    public ContainerDrill(InventoryPlayer inventory){
        this.inventory = inventory;

        for(int i = 0; i < SLOT_AMOUNT; i++){
            this.addSlotToContainer(new Slot(drillInventory, i, 44+i*18, 19){
                @Override
                public boolean isItemValid(ItemStack stack){
                    return stack.getItem() instanceof ItemDrillUpgrade || stack.getItem() instanceof IEnergyContainerItem;
                }
            });
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 58+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            if(i == inventory.currentItem) this.addSlotToContainer(new SlotImmovable(inventory, i, 8+i*18, 116));
            else this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 116));
        }

        ItemStack stack = inventory.getCurrentItem();
        if(stack != null && stack.getItem() instanceof ItemDrill){
            ItemStack[] slots = ((ItemDrill)stack.getItem()).getSlotsFromNBT(inventory.getCurrentItem());
            if(slots != null && slots.length > 0) this.drillInventory.slots = slots;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.drillInventory.isUseableByPlayer(player);
    }

    @Override
    public void onContainerClosed(EntityPlayer player){
        ItemStack stack = inventory.getCurrentItem();
        if(stack != null && stack.getItem() instanceof ItemDrill){
            ((ItemDrill)stack.getItem()).writeSlotsToNBT(this.drillInventory.slots, inventory.getCurrentItem());
        }
       super.onContainerClosed(player);
    }

    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer player){
        //par3 appears to be the type of clicking
        //par3 == 2 appears to be one of the number keys being hit
        if(par3 == 2 && par2 == inventory.currentItem) return null;
        else return super.slotClick(par1, par2, par3, player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 5;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);

        if (theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Other Slots in Inventory excluded
            if(slot >= inventoryStart){
                //Shift from Inventory
                if(newStack.getItem() instanceof ItemDrillUpgrade || newStack.getItem() instanceof IEnergyContainerItem){
                    if(!this.mergeItemStack(newStack, 0, 5, false)) return null;
                }
                //

                else if(slot >= inventoryStart && slot <= inventoryEnd){
                    if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false)) return null;
                }
                else if(slot >= inventoryEnd+1 && slot < hotbarEnd+1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false)) return null;
            }
            else if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false)) return null;

            if (newStack.stackSize == 0) theSlot.putStack(null);
            else theSlot.onSlotChanged();

            if (newStack.stackSize == currentStack.stackSize) return null;
            theSlot.onPickupFromSlot(player, newStack);

            return currentStack;
        }
        return null;
    }

    public static class InventoryDrill implements IInventory{

        public ItemStack[] slots = new ItemStack[SLOT_AMOUNT];

        @Override
        public String getInventoryName(){
            return "drill";
        }

        @Override
        public boolean hasCustomInventoryName(){
            return false;
        }

        @Override
        public int getInventoryStackLimit(){
            return 64;
        }

        @Override
        public void markDirty(){

        }

        @Override
        public void openInventory(){

        }

        @Override
        public void closeInventory(){

        }

        @Override
        public boolean isItemValidForSlot(int slot, ItemStack stack){
            return true;
        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer player){
            return true;
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int i){
            return getStackInSlot(i);
        }

        @Override
        public void setInventorySlotContents(int i, ItemStack stack){
            this.slots[i] = stack;
            this.markDirty();
        }

        @Override
        public int getSizeInventory(){
            return slots.length;
        }

        @Override
        public ItemStack getStackInSlot(int i){
            if(i < this.getSizeInventory()){
                return slots[i];
            }
            return null;
        }

        @Override
        public ItemStack decrStackSize(int i, int j){
            if (slots[i] != null){
                ItemStack stackAt;
                if(slots[i].stackSize <= j){
                    stackAt = slots[i];
                    slots[i] = null;
                    this.markDirty();
                    return stackAt;
                }
                else{
                    stackAt = slots[i].splitStack(j);
                    if (slots[i].stackSize == 0) slots[i] = null;
                    this.markDirty();
                    return stackAt;
                }
            }
            return null;
        }
    }
}