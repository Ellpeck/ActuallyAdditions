package de.ellpeck.actuallyadditions.inventory;

import de.ellpeck.actuallyadditions.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.inventory.slot.SlotOutput;
import de.ellpeck.actuallyadditions.items.ItemSolidifiedExperience;
import de.ellpeck.actuallyadditions.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.tile.TileEntityXPSolidifier;
import de.ellpeck.actuallyadditions.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerXPSolidifier extends Container {

    private final TileEntityXPSolidifier solidifier;

    public ContainerXPSolidifier(InventoryPlayer inventory, TileEntityBase tile) {
        this.solidifier = (TileEntityXPSolidifier) tile;

        this.addSlotToContainer(new SlotOutput(this.solidifier.inv, 0, 95, 8));
        this.addSlotToContainer(new SlotItemHandlerUnconditioned(this.solidifier.inv, 1, 65, 8));

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
        int inventoryStart = 2;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.inventorySlots.get(slot);

        if (theSlot != null && theSlot.getHasStack()) {
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Other Slots in Inventory excluded
            if (slot >= inventoryStart) {
                if (newStack.getItem() instanceof ItemSolidifiedExperience) {
                    if (!this.mergeItemStack(newStack, 1, 2, false)) { return StackUtil.getEmpty(); }
                } else if (slot >= inventoryStart && slot <= inventoryEnd) {
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
        return this.solidifier.canPlayerUse(player);
    }
}