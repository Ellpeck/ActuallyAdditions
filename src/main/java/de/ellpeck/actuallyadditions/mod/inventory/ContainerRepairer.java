// TODO: [port][note] no longer needed
///*
// * This file ("ContainerRepairer.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.inventory;
//
//import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
//import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotOutput;
//import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
//import de.ellpeck.actuallyadditions.mod.util.StackUtil;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.container.Container;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.item.ItemStack;
//
//public class ContainerRepairer extends Container {
//
//    private final TileEntityItemRepairer tileRepairer;
//
//    public ContainerRepairer(PlayerInventory inventory, TileEntityBase tile) {
//        this.tileRepairer = (TileEntityItemRepairer) tile;
//
//        this.addSlot(new SlotItemHandlerUnconditioned(this.tileRepairer.inv, TileEntityItemRepairer.SLOT_INPUT, 47, 53));
//        this.addSlot(new SlotOutput(this.tileRepairer.inv, TileEntityItemRepairer.SLOT_OUTPUT, 109, 53));
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 9; j++) {
//                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
//            }
//        }
//        for (int i = 0; i < 9; i++) {
//            this.addSlot(new Slot(inventory, i, 8 + i * 18, 155));
//        }
//    }
//
//    @Override
//    public ItemStack transferStackInSlot(PlayerEntity player, int slot) {
//        int inventoryStart = 2;
//        int inventoryEnd = inventoryStart + 26;
//        int hotbarStart = inventoryEnd + 1;
//        int hotbarEnd = hotbarStart + 8;
//
//        Slot theSlot = this.inventorySlots.get(slot);
//
//        if (theSlot != null && theSlot.getHasStack()) {
//            ItemStack newStack = theSlot.getStack();
//            ItemStack currentStack = newStack.copy();
//
//            //Other Slots in Inventory excluded
//            if (slot >= inventoryStart) {
//                //Shift from Inventory
//                if (TileEntityItemRepairer.canBeRepaired(newStack)) {
//                    if (!this.mergeItemStack(newStack, TileEntityItemRepairer.SLOT_INPUT, TileEntityItemRepairer.SLOT_INPUT + 1, false)) {
//                        return StackUtil.getEmpty();
//                    }
//                }
//                //
//
//                else if (slot >= inventoryStart && slot <= inventoryEnd) {
//                    if (!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)) {
//                        return StackUtil.getEmpty();
//                    }
//                } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)) {
//                    return StackUtil.getEmpty();
//                }
//            } else if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false)) {
//                return StackUtil.getEmpty();
//            }
//
//            if (!StackUtil.isValid(newStack)) {
//                theSlot.putStack(StackUtil.getEmpty());
//            } else {
//                theSlot.onSlotChanged();
//            }
//
//            if (newStack.getCount() == currentStack.getCount()) {
//                return StackUtil.getEmpty();
//            }
//            theSlot.onTake(player, newStack);
//
//            return currentStack;
//        }
//        return StackUtil.getEmpty();
//    }
//
//    @Override
//    public boolean canInteractWith(PlayerEntity player) {
//        return this.tileRepairer.canPlayerUse(player);
//    }
//}
