/*
 * This file ("ContainerBag.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotDeletion;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.items.ItemBag;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;


public class ContainerBag extends Container implements IButtonReactor{

    public final FilterSettings filter = new FilterSettings(0, 4, false, true, false, 0, -1000);
    private final InventoryBag bagInventory;
    private final InventoryPlayer inventory;
    private final boolean isVoid;
    public boolean autoInsert;
    private boolean oldAutoInsert;

    public ContainerBag(InventoryPlayer inventory, boolean isVoid){
        this.inventory = inventory;
        this.bagInventory = new InventoryBag(isVoid);
        this.isVoid = isVoid;

        for(int i = 0; i < 4; i++){
            this.addSlotToContainer(new SlotFilter(this.bagInventory, i, 155, 10+i*18));
        }

        if(this.isVoid){
            this.addSlotToContainer(new SlotDeletion(this.bagInventory, 4, 64, 65){
                @Override
                public boolean isItemValid(ItemStack stack){
                    return ContainerBag.this.filter.check(stack, ContainerBag.this.bagInventory.slots);
                }
            });
        }
        else{
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 7; j++){
                    this.addSlotToContainer(new Slot(this.bagInventory, j+i*7+4, 10+j*18, 10+i*18){
                        @Override
                        public boolean isItemValid(ItemStack stack){
                            return ContainerBag.this.filter.check(stack, ContainerBag.this.bagInventory.slots);
                        }
                    });
                }
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
        if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemBag){
            ItemDrill.loadSlotsFromNBT(this.bagInventory.slots, inventory.getCurrentItem());
            if(stack.hasTagCompound()){
                NBTTagCompound compound = stack.getTagCompound();
                this.filter.readFromNBT(compound, "Filter");
                this.autoInsert = compound.getBoolean("AutoInsert");
            }
        }
    }

    public static int getSlotAmount(boolean isVoid){
        return isVoid ? 5 : 32;
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();

        if(this.filter.needsUpdateSend() || this.autoInsert != this.oldAutoInsert){
            for(IContainerListener listener : this.listeners){
                listener.sendProgressBarUpdate(this, 0, this.filter.isWhitelist ? 1 : 0);
                listener.sendProgressBarUpdate(this, 1, this.filter.respectMeta ? 1 : 0);
                listener.sendProgressBarUpdate(this, 2, this.filter.respectNBT ? 1 : 0);
                listener.sendProgressBarUpdate(this, 3, this.filter.respectOredict);
                listener.sendProgressBarUpdate(this, 4, this.autoInsert ? 1 : 0);
            }
            this.filter.updateLasts();
            this.oldAutoInsert = this.autoInsert;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        if(id == 0){
            this.filter.isWhitelist = data == 1;
        }
        else if(id == 1){
            this.filter.respectMeta = data == 1;
        }
        else if(id == 2){
            this.filter.respectNBT = data == 1;
        }
        else if(id == 3){
            this.filter.respectOredict = data;
        }
        else if(id == 4){
            this.autoInsert = data == 1;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        int inventoryStart = this.bagInventory.slots.length;
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
                if(this.isVoid || !this.filter.check(newStack, this.bagInventory.slots) || !this.mergeItemStack(newStack, 4, 32, false)){
                    if(slot >= inventoryStart && slot <= inventoryEnd){
                        if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false)){
                            return StackUtil.getNull();
                        }
                    }
                    else if(slot >= inventoryEnd+1 && slot < hotbarEnd+1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false)){
                        return StackUtil.getNull();
                    }
                }
                //

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
            theSlot.onPickupFromSlot(player, newStack);

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
        if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemBag){
            ItemDrill.writeSlotsToNBT(this.bagInventory.slots, this.inventory.getCurrentItem());
            NBTTagCompound compound = stack.getTagCompound();
            this.filter.writeToNBT(compound, "Filter");
            compound.setBoolean("AutoInsert", this.autoInsert);
        }
        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.bagInventory.isUseableByPlayer(player);
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player){
        if(buttonID == 0){
            this.autoInsert = !this.autoInsert;
        }
        else{
            this.filter.onButtonPressed(buttonID);
        }
    }

    public static class InventoryBag implements IInventory{

        public ItemStack[] slots;

        public InventoryBag(boolean isVoid){
            this.slots = new ItemStack[getSlotAmount(isVoid)];
            Arrays.fill(this.slots, StackUtil.getNull());
        }

        @Override
        public String getName(){
            return "bag";
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
            Arrays.fill(this.slots, StackUtil.getNull());
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
            return StackUtil.getNull();
        }

        @Override
        public ItemStack decrStackSize(int i, int j){
            if(StackUtil.isValid(this.slots[i])){
                ItemStack stackAt;
                if(StackUtil.getStackSize(this.slots[i]) <= j){
                    stackAt = this.slots[i];
                    this.slots[i] = StackUtil.getNull();
                    this.markDirty();
                    return stackAt;
                }
                else{
                    stackAt = this.slots[i].splitStack(j);
                    if(StackUtil.getStackSize(this.slots[i]) <= 0){
                        this.slots[i] = StackUtil.getNull();
                    }
                    this.markDirty();
                    return stackAt;
                }
            }
            return StackUtil.getNull();
        }

        @Override
        public ItemStack removeStackFromSlot(int index){
            ItemStack stack = this.slots[index];
            this.slots[index] = StackUtil.getNull();
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