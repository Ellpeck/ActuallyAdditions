/*
 * This file ("ContainerFluidCollector.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory;

import ellpeck.actuallyadditions.inventory.slot.SlotOutput;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityFluidCollector;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

@InventoryContainer
public class ContainerFluidCollector extends Container{

    private TileEntityFluidCollector collector;

    public ContainerFluidCollector(InventoryPlayer inventory, TileEntityBase tile){
        this.collector = (TileEntityFluidCollector)tile;

        this.addSlotToContainer(new Slot(collector, 0, 90, 73));
        this.addSlotToContainer(new SlotOutput(collector, 1, 90, 42));

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.collector.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 2;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);

        if (theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if(slot == 1){
                if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, true)) return null;
                theSlot.onSlotChange(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if(slot >= inventoryStart){
                //Shift from Inventory
                if(FluidContainerRegistry.isEmptyContainer(newStack)){
                    if(!this.mergeItemStack(newStack, 0, 1, false)) return null;
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
}