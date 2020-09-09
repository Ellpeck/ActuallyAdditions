package de.ellpeck.actuallyadditions.common.inventory;

import de.ellpeck.actuallyadditions.common.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBioReactor;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBioReactor extends Container {

    private final TileEntityBioReactor tile;

    public ContainerBioReactor(InventoryPlayer inventory, TileEntityBase tile) {
        this.tile = (TileEntityBioReactor) tile;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.tile.inv, j + i * 2, 50 + j * 18, 13 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        int inventoryStart = 8;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.inventorySlots.get(slot);

        if (theSlot != null && theSlot.getHasStack()) {
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Other Slots in Inventory excluded
            if (slot >= inventoryStart) {
                //Shift from Inventory
                if (TileEntityBioReactor.isValidItem(newStack)) {
                    if (!this.mergeItemStack(newStack, 0, 8, false)) { return StackUtil.getEmpty(); }
                }
                //

                else if (slot >= inventoryStart && slot <= inventoryEnd) {
                    if (!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)) { return StackUtil.getEmpty(); }
                } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)) { return StackUtil.getEmpty(); }
            } else if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false)) { return StackUtil.getEmpty(); }

            if (!StackUtil.isValid(newStack)) {
                theSlot.putStack(StackUtil.getEmpty());
            } else {
                theSlot.onSlotChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) { return StackUtil.getEmpty(); }
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return StackUtil.getEmpty();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.canPlayerUse(player);
    }
}
