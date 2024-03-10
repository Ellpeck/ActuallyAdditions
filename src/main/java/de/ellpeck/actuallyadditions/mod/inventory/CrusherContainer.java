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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCrusher;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class CrusherContainer extends AbstractContainerMenu {
    public final TileEntityCrusher tileGrinder;
    public final boolean isDouble;

    public CrusherContainer(int windowId, Inventory inventory, TileEntityCrusher tile) {
        super(ActuallyContainers.GRINDER_CONTAINER.get(), windowId);
        this.tileGrinder = tile;
        this.isDouble = tile.isDouble;

        this.addSlot(new SlotItemHandlerUnconditioned(this.tileGrinder.inv, TileEntityCrusher.SLOT_INPUT_1, this.isDouble
                ? 51
                : 80, 21));
        this.addSlot(new SlotOutput(this.tileGrinder.inv, TileEntityCrusher.SLOT_OUTPUT_1_1, this.isDouble
                ? 37
                : 66, 69));
        this.addSlot(new SlotOutput(this.tileGrinder.inv, TileEntityCrusher.SLOT_OUTPUT_1_2, this.isDouble
                ? 64
                : 92, 69));
        if (this.isDouble) {
            this.addSlot(new SlotItemHandlerUnconditioned(this.tileGrinder.inv, TileEntityCrusher.SLOT_INPUT_2, 109, 21));
            this.addSlot(new SlotOutput(this.tileGrinder.inv, TileEntityCrusher.SLOT_OUTPUT_2_1, 96, 69));
            this.addSlot(new SlotOutput(this.tileGrinder.inv, TileEntityCrusher.SLOT_OUTPUT_2_2, 121, 69));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    public static CrusherContainer fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        return new CrusherContainer(windowId, inv, (TileEntityCrusher) Objects.requireNonNull(inv.player.level().getBlockEntity(data.readBlockPos())));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        int inventoryStart = this.isDouble
                ? 6
                : 3;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.slots.get(slot);

        if (theSlot != null && theSlot.hasItem()) {
            ItemStack newStack = theSlot.getItem();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if (slot == TileEntityCrusher.SLOT_OUTPUT_1_1 || slot == TileEntityCrusher.SLOT_OUTPUT_1_2 || this.isDouble && (slot == TileEntityCrusher.SLOT_OUTPUT_2_1 || slot == TileEntityCrusher.SLOT_OUTPUT_2_2)) {
                if (!this.moveItemStackTo(newStack, inventoryStart, hotbarEnd + 1, true)) {
                    return ItemStack.EMPTY;
                }
                theSlot.onQuickCraft(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if (slot >= inventoryStart) {
                //Shift from Inventory
                if (CrusherRecipeRegistry.getRecipeFromInput(newStack) != null) {
                    if (!this.moveItemStackTo(newStack, TileEntityCrusher.SLOT_INPUT_1, TileEntityCrusher.SLOT_INPUT_1 + 1, false)) {
                        if (this.isDouble) {
                            if (!this.moveItemStackTo(newStack, TileEntityCrusher.SLOT_INPUT_2, TileEntityCrusher.SLOT_INPUT_2 + 1, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else {
                            return ItemStack.EMPTY;
                        }
                    }
                }
                //

                else if (slot >= inventoryStart && slot <= inventoryEnd) {
                    if (!this.moveItemStackTo(newStack, hotbarStart, hotbarEnd + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.moveItemStackTo(newStack, inventoryStart, inventoryEnd + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(newStack, inventoryStart, hotbarEnd + 1, false)) {
                return ItemStack.EMPTY;
            }

            if (!StackUtil.isValid(newStack)) {
                theSlot.set(ItemStack.EMPTY);
            } else {
                theSlot.setChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) {
                return ItemStack.EMPTY;
            }
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.tileGrinder.canPlayerUse(player);
    }
}
