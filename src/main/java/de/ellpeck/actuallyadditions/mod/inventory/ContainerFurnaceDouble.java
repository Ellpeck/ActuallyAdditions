/*
 * This file ("ContainerFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.network.PacketBuffer;

import java.util.Objects;

public class ContainerFurnaceDouble extends Container {

    public final TileEntityFurnaceDouble furnace;

    public static ContainerFurnaceDouble fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new ContainerFurnaceDouble(windowId, inv, (TileEntityFurnaceDouble) Objects.requireNonNull(inv.player.world.getTileEntity(data.readBlockPos())));
    }

    public ContainerFurnaceDouble(int windowId, PlayerInventory inventory, TileEntityFurnaceDouble tile) {
        super(ActuallyContainers.FURNACE_DOUBLE_CONTAINER.get(), windowId);
        this.furnace = tile;

        this.addSlot(new SlotItemHandlerUnconditioned(this.furnace.inv, TileEntityFurnaceDouble.SLOT_INPUT_1, 51, 21));
        this.addSlot(new SlotOutput(this.furnace.inv, TileEntityFurnaceDouble.SLOT_OUTPUT_1, 51, 69));
        this.addSlot(new SlotItemHandlerUnconditioned(this.furnace.inv, TileEntityFurnaceDouble.SLOT_INPUT_2, 109, 21));
        this.addSlot(new SlotOutput(this.furnace.inv, TileEntityFurnaceDouble.SLOT_OUTPUT_2, 108, 69));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slot) {
        int inventoryStart = 4;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.inventorySlots.get(slot);

        if (theSlot != null && theSlot.getHasStack()) {
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if (slot == TileEntityFurnaceDouble.SLOT_OUTPUT_1 || slot == TileEntityFurnaceDouble.SLOT_OUTPUT_2) {
                if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, true)) {
                    return StackUtil.getEmpty();
                }
                theSlot.onSlotChange(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if (slot >= inventoryStart) {
                //Shift from Inventory
                if (StackUtil.isValid(FurnaceRecipes.instance().getSmeltingResult(newStack))) {
                    if (!this.mergeItemStack(newStack, TileEntityFurnaceDouble.SLOT_INPUT_1, TileEntityFurnaceDouble.SLOT_INPUT_1 + 1, false)) {
                        if (!this.mergeItemStack(newStack, TileEntityFurnaceDouble.SLOT_INPUT_2, TileEntityFurnaceDouble.SLOT_INPUT_2 + 1, false)) {
                            return StackUtil.getEmpty();
                        }
                    }
                }
                //

                else if (slot >= inventoryStart && slot <= inventoryEnd) {
                    if (!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)) {
                        return StackUtil.getEmpty();
                    }
                } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)) {
                    return StackUtil.getEmpty();
                }
            } else if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false)) {
                return StackUtil.getEmpty();
            }

            if (!StackUtil.isValid(newStack)) {
                theSlot.putStack(StackUtil.getEmpty());
            } else {
                theSlot.onSlotChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) {
                return StackUtil.getEmpty();
            }
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return StackUtil.getEmpty();
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return this.furnace.canPlayerUse(player);
    }
}
