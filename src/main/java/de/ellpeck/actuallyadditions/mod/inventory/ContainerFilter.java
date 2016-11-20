/*
 * This file ("ContainerFilter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.items.ItemFilter;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Arrays;


public class ContainerFilter extends Container{

    public static final int SLOT_AMOUNT = 24;

    private final InventoryFilter filterInventory = new InventoryFilter();
    private final InventoryPlayer inventory;

    public ContainerFilter(InventoryPlayer inventory){
        this.inventory = inventory;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 6; j++){
                this.addSlotToContainer(new SlotFilter(this.filterInventory, j+(i*6), 35+j*18, 10+i*18));
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 94+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            if(i == inventory.currentItem){
                this.addSlotToContainer(new SlotImmovable(inventory, i, 8+i*18, 152));
            }
            else{
                this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 152));
            }
        }

        ItemStack stack = inventory.getCurrentItem();
        if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemFilter){
            ItemDrill.loadSlotsFromNBT(this.filterInventory.slots, inventory.getCurrentItem());
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        int inventoryStart = SLOT_AMOUNT;
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
                //
                if(slot >= inventoryStart && slot <= inventoryEnd){
                    if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false)){
                        return StackUtil.getNull();
                    }
                }
                else if(slot >= inventoryEnd+1 && slot < hotbarEnd+1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false)){
                    return StackUtil.getNull();
                }
            }
            else if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false)){
                return StackUtil.getNull();
            }

            if(!StackUtil.isValid(newStack)){
                theSlot.putStack(StackUtil.getNull());
            }
            else{
                theSlot.onSlotChanged();
            }

            if(StackUtil.getStackSize(newStack) == StackUtil.getStackSize(currentStack)){
                return StackUtil.getNull();
            }
            theSlot.func_190901_a(player, newStack);

            return currentStack;
        }
        return StackUtil.getNull();
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player){
        if(slotId >= 0 && slotId < this.inventorySlots.size() && this.getSlot(slotId) instanceof SlotFilter){
            //Calls the Filter's SlotClick function
            return ((SlotFilter)this.getSlot(slotId)).slotClick(player);
        }
        else if(clickTypeIn == ClickType.SWAP && dragType == this.inventory.currentItem){
            return null;
        }
        else{
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player){
        ItemStack stack = this.inventory.getCurrentItem();
        if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemFilter){
            ItemDrill.writeSlotsToNBT(this.filterInventory.slots, this.inventory.getCurrentItem());
        }
        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.filterInventory.isUseableByPlayer(player);
    }

    public static class InventoryFilter implements IInventory{

        public NonNullList<ItemStack> slots = StackUtil.createSlots(SLOT_AMOUNT);

        @Override
        public String getName(){
            return "filter";
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
            this.slots.clear();
        }

        @Override
        public void setInventorySlotContents(int i, ItemStack stack){
            this.slots.set(i, stack);
            this.markDirty();
        }

        @Override
        public int getSizeInventory(){
            return this.slots.size();
        }

        @Override
        public boolean func_191420_l(){
            return StackUtil.isIInvEmpty(this.slots);
        }

        @Override
        public ItemStack getStackInSlot(int i){
            if(i < this.getSizeInventory()){
                return this.slots.get(i);
            }
            return StackUtil.getNull();
        }

        @Override
        public ItemStack decrStackSize(int i, int j){
            if(StackUtil.isValid(this.slots.get(i))){
                ItemStack stackAt;
                if(StackUtil.getStackSize(this.slots.get(i)) <= j){
                    stackAt = this.slots.get(i);
                    this.slots.set(i, StackUtil.getNull());
                    this.markDirty();
                    return stackAt;
                }
                else{
                    stackAt = this.slots.get(i).splitStack(j);
                    if(StackUtil.getStackSize(this.slots.get(i)) <= 0){
                        this.slots.set(i, StackUtil.getNull());
                    }
                    this.markDirty();
                    return stackAt;
                }
            }
            return StackUtil.getNull();
        }

        @Override
        public ItemStack removeStackFromSlot(int index){
            ItemStack stack = this.slots.get(index);
            this.slots.set(index, StackUtil.getNull());
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