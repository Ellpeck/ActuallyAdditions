/*
 * This file ("ContainerCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotOutput;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;


public class ContainerCoffeeMachine extends Container{

    private final TileEntityCoffeeMachine machine;

    public ContainerCoffeeMachine(InventoryPlayer inventory, TileEntityBase tile){
        this.machine = (TileEntityCoffeeMachine)tile;

        this.addSlotToContainer(new Slot(this.machine, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS, 37, 6));
        this.addSlotToContainer(new Slot(this.machine, TileEntityCoffeeMachine.SLOT_INPUT, 80, 42));
        this.addSlotToContainer(new SlotOutput(this.machine, TileEntityCoffeeMachine.SLOT_OUTPUT, 80, 73));

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                this.addSlotToContainer(new Slot(this.machine, j+i*2+3, 125+j*18, 6+i*18));
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 97+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 155));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 11;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = this.inventorySlots.get(slot);

        if(theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if(slot == TileEntityCoffeeMachine.SLOT_OUTPUT){
                if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, true)){
                    return null;
                }
                theSlot.onSlotChange(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if(slot >= inventoryStart){
                //Shift from Inventory
                if(newStack.getItem() == InitItems.itemMisc && newStack.getItemDamage() == TheMiscItems.CUP.ordinal()){
                    if(!this.mergeItemStack(newStack, TileEntityCoffeeMachine.SLOT_INPUT, TileEntityCoffeeMachine.SLOT_INPUT+1, false)){
                        return null;
                    }
                }
                else if(ItemCoffee.getIngredientFromStack(newStack) != null){
                    if(!this.mergeItemStack(newStack, 3, 11, false)){
                        return null;
                    }
                }
                else if(newStack.getItem() == InitItems.itemCoffeeBean){
                    if(!this.mergeItemStack(newStack, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS, TileEntityCoffeeMachine.SLOT_COFFEE_BEANS+1, false)){
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

            if(newStack.stackSize == 0){
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
    public boolean canInteractWith(EntityPlayer player){
        return this.machine.isUseableByPlayer(player);
    }
}