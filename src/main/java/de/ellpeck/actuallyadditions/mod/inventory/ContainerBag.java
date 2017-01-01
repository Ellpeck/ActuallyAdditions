/*
 * This file ("ContainerBag.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotDeletion;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.items.ItemBag;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerCustom;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBag extends Container implements IButtonReactor{

    public final FilterSettings filter = new FilterSettings(4, false, true, false, false, 0, -1000);
    private final ItemStackHandlerCustom bagInventory;
    private final InventoryPlayer inventory;
    private final boolean isVoid;
    public boolean autoInsert;
    private boolean oldAutoInsert;

    public ContainerBag(InventoryPlayer inventory, boolean isVoid){
        this.inventory = inventory;
        this.bagInventory = new ItemStackHandlerCustom(getSlotAmount(isVoid));
        this.isVoid = isVoid;

        for(int i = 0; i < 4; i++){
            this.addSlotToContainer(new SlotFilter(this.filter, i, 155, 10+i*18));
        }

        if(this.isVoid){
            this.addSlotToContainer(new SlotDeletion(this.bagInventory, 0, 64, 65){
                @Override
                public boolean isItemValid(ItemStack stack){
                    return ContainerBag.this.filter.check(stack);
                }
            });
        }
        else{
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 7; j++){
                    this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.bagInventory, j+i*7, 10+j*18, 10+i*18){
                        @Override
                        public boolean isItemValid(ItemStack stack){
                            return ContainerBag.this.filter.check(stack);
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
            ItemDrill.loadSlotsFromNBT(this.bagInventory, inventory.getCurrentItem());
            if(stack.hasTagCompound()){
                NBTTagCompound compound = stack.getTagCompound();
                this.filter.readFromNBT(compound, "Filter");
                this.autoInsert = compound.getBoolean("AutoInsert");
            }
        }
    }

    public static int getSlotAmount(boolean isVoid){
        return isVoid ? 1 : 28;
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
                listener.sendProgressBarUpdate(this, 5, this.filter.respectMod ? 1 : 0);
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
        else if(id == 5){
            this.filter.respectMod = data == 1;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        int inventoryStart = this.bagInventory.getSlots()+4;
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
                if(this.isVoid || !this.filter.check(newStack) || !this.mergeItemStack(newStack, 4, 32, false)){
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
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return StackUtil.getNull();
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player){
        if(SlotFilter.checkFilter(this, slotId, player)){
            return StackUtil.getNull();
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
            ItemDrill.writeSlotsToNBT(this.bagInventory, this.inventory.getCurrentItem());
            NBTTagCompound compound = stack.getTagCompound();
            this.filter.writeToNBT(compound, "Filter");
            compound.setBoolean("AutoInsert", this.autoInsert);
        }
        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return true;
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
}