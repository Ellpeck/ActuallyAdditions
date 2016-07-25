/*
 * This file ("ContainerDrill.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import cofh.api.energy.IEnergyContainerItem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.items.ItemDrillUpgrade;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;


public class ContainerDrill extends Container{

    public static final int SLOT_AMOUNT = 5;

    private final InventoryDrill drillInventory = new InventoryDrill();
    private final InventoryPlayer inventory;

    public ContainerDrill(InventoryPlayer inventory){
        this.inventory = inventory;

        for(int i = 0; i < SLOT_AMOUNT; i++){
            this.addSlotToContainer(new Slot(this.drillInventory, i, 44+i*18, 19){
                @Override
                public boolean isItemValid(ItemStack stack){
                    return stack.getItem() instanceof ItemDrillUpgrade || stack.getItem() instanceof IEnergyContainerItem || (ActuallyAdditions.teslaLoaded && stack.hasCapability(TeslaUtil.teslaProducer, null));
                }
            });
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 58+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            if(i == inventory.currentItem){
                this.addSlotToContainer(new SlotImmovable(inventory, i, 8+i*18, 116));
            }
            else{
                this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 116));
            }
        }

        ItemStack stack = inventory.getCurrentItem();
        if(stack != null && stack.getItem() instanceof ItemDrill){
            ItemDrill.loadSlotsFromNBT(this.drillInventory.slots, inventory.getCurrentItem());
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        int inventoryStart = 5;
        int inventoryEnd = inventoryStart+26;
        int hotbarStart = inventoryEnd+1;
        int hotbarEnd = hotbarStart+8;

        Slot theSlot = this.inventorySlots.get(slot);

        if(theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Other Slots in Inventory excluded
            if(slot >= inventoryStart){
                //Shift from Inventory
                if(newStack.getItem() instanceof ItemDrillUpgrade || newStack.getItem() instanceof IEnergyContainerItem || (ActuallyAdditions.teslaLoaded && newStack.hasCapability(TeslaUtil.teslaProducer, null))){
                    if(!this.mergeItemStack(newStack, 0, 5, false)){
                        return null;
                    }
                }
                //

                else if(slot >= inventoryStart && slot <= inventoryEnd){
                    if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false)){
                        return null;
                    }
                }
                else if(slot >= inventoryEnd+1 && slot < hotbarEnd+1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false)){
                    return null;
                }
            }
            else if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false)){
                return null;
            }

            if(newStack.stackSize <= 0){
                theSlot.putStack(null);
            }
            else{
                theSlot.onSlotChanged();
            }

            if(newStack.stackSize == currentStack.stackSize){
                return null;
            }
            theSlot.onPickupFromSlot(player, newStack);

            return currentStack;
        }
        return null;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player){
        if(clickTypeIn == ClickType.SWAP && dragType == this.inventory.currentItem){
            return null;
        }
        else{
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player){
        ItemStack stack = this.inventory.getCurrentItem();
        if(stack != null && stack.getItem() instanceof ItemDrill){
            ItemDrill.writeSlotsToNBT(this.drillInventory.slots, this.inventory.getCurrentItem());
        }
        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.drillInventory.isUseableByPlayer(player);
    }

    public static class InventoryDrill implements IInventory{

        public ItemStack[] slots = new ItemStack[SLOT_AMOUNT];

        @Override
        public String getName(){
            return "drill";
        }

        @Override
        public int getInventoryStackLimit(){
            return 64;
        }

        @Override
        public void markDirty(){

        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer player){
            return true;
        }

        @Override
        public void openInventory(EntityPlayer player){

        }

        @Override
        public void closeInventory(EntityPlayer player){

        }

        @Override
        public boolean isItemValidForSlot(int index, ItemStack stack){
            return true;
        }

        @Override
        public int getField(int id){
            return 0;
        }

        @Override
        public void setField(int id, int value){

        }

        @Override
        public int getFieldCount(){
            return 0;
        }

        @Override
        public void clear(){
            int length = this.slots.length;
            this.slots = new ItemStack[length];
        }

        @Override
        public void setInventorySlotContents(int i, ItemStack stack){
            this.slots[i] = stack;
            this.markDirty();
        }

        @Override
        public int getSizeInventory(){
            return this.slots.length;
        }

        @Override
        public ItemStack getStackInSlot(int i){
            if(i < this.getSizeInventory()){
                return this.slots[i];
            }
            return null;
        }

        @Override
        public ItemStack decrStackSize(int i, int j){
            if(this.slots[i] != null){
                ItemStack stackAt;
                if(this.slots[i].stackSize <= j){
                    stackAt = this.slots[i];
                    this.slots[i] = null;
                    this.markDirty();
                    return stackAt;
                }
                else{
                    stackAt = this.slots[i].splitStack(j);
                    if(this.slots[i].stackSize <= 0){
                        this.slots[i] = null;
                    }
                    this.markDirty();
                    return stackAt;
                }
            }
            return null;
        }

        @Override
        public ItemStack removeStackFromSlot(int index){
            ItemStack stack = this.slots[index];
            this.slots[index] = null;
            return stack;
        }

        @Override
        public boolean hasCustomName(){
            return false;
        }


        @Override
        public ITextComponent getDisplayName(){
            return new TextComponentTranslation(this.getName());
        }
    }
}