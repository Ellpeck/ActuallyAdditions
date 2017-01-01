/*
 * This file ("ContainerGrinder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotOutput;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGrinder;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class ContainerGrinder extends Container{

    public final TileEntityGrinder tileGrinder;
    private final boolean isDouble;

    public ContainerGrinder(InventoryPlayer inventory, TileEntityBase tile, boolean isDouble){
        this.tileGrinder = (TileEntityGrinder)tile;
        this.isDouble = isDouble;

        this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.tileGrinder.slots, TileEntityGrinder.SLOT_INPUT_1, this.isDouble ? 51 : 80, 21));
        this.addSlotToContainer(new SlotOutput(this.tileGrinder.slots, TileEntityGrinder.SLOT_OUTPUT_1_1, this.isDouble ? 37 : 66, 69));
        this.addSlotToContainer(new SlotOutput(this.tileGrinder.slots, TileEntityGrinder.SLOT_OUTPUT_1_2, this.isDouble ? 64 : 92, 69));
        if(this.isDouble){
            this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.tileGrinder.slots, TileEntityGrinder.SLOT_INPUT_2, 109, 21));
            this.addSlotToContainer(new SlotOutput(this.tileGrinder.slots, TileEntityGrinder.SLOT_OUTPUT_2_1, 96, 69));
            this.addSlotToContainer(new SlotOutput(this.tileGrinder.slots, TileEntityGrinder.SLOT_OUTPUT_2_2, 121, 69));
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
        int inventoryStart = this.isDouble ? 6 : 3;
        int inventoryEnd = inventoryStart+26;
        int hotbarStart = inventoryEnd+1;
        int hotbarEnd = hotbarStart+8;

        Slot theSlot = this.inventorySlots.get(slot);

        if(theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if(slot == TileEntityGrinder.SLOT_OUTPUT_1_1 || slot == TileEntityGrinder.SLOT_OUTPUT_1_2 || (this.isDouble && (slot == TileEntityGrinder.SLOT_OUTPUT_2_1 || slot == TileEntityGrinder.SLOT_OUTPUT_2_2))){
                if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, true)){
                    return StackUtil.getNull();
                }
                theSlot.onSlotChange(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if(slot >= inventoryStart){
                //Shift from Inventory
                if(CrusherRecipeRegistry.getRecipeFromInput(newStack) != null){
                    if(!this.mergeItemStack(newStack, TileEntityGrinder.SLOT_INPUT_1, TileEntityGrinder.SLOT_INPUT_1+1, false)){
                        if(this.isDouble){
                            if(!this.mergeItemStack(newStack, TileEntityGrinder.SLOT_INPUT_2, TileEntityGrinder.SLOT_INPUT_2+1, false)){
                                return StackUtil.getNull();
                            }
                        }
                        else{
                            return StackUtil.getNull();
                        }
                    }
                }
                //

                else if(slot >= inventoryStart && slot <= inventoryEnd){
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
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return StackUtil.getNull();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileGrinder.canPlayerUse(player);
    }
}