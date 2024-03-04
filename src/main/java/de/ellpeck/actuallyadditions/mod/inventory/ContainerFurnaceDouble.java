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

import de.ellpeck.actuallyadditions.mod.crafting.SingleItem;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotOutput;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPoweredFurnace;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ContainerFurnaceDouble extends AbstractContainerMenu {

    public final TileEntityPoweredFurnace furnace;

    public ContainerFurnaceDouble(int windowId, Inventory inventory, TileEntityPoweredFurnace tile) {
        super(ActuallyContainers.FURNACE_DOUBLE_CONTAINER.get(), windowId);
        this.furnace = tile;

        this.addSlot(new SlotItemHandlerUnconditioned(this.furnace.inv, TileEntityPoweredFurnace.SLOT_INPUT_1, 51, 21));
        this.addSlot(new SlotOutput(this.furnace.inv, TileEntityPoweredFurnace.SLOT_OUTPUT_1, 51, 69));
        this.addSlot(new SlotItemHandlerUnconditioned(this.furnace.inv, TileEntityPoweredFurnace.SLOT_INPUT_2, 109, 21));
        this.addSlot(new SlotOutput(this.furnace.inv, TileEntityPoweredFurnace.SLOT_OUTPUT_2, 108, 69));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    public static ContainerFurnaceDouble fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        return new ContainerFurnaceDouble(windowId, inv, (TileEntityPoweredFurnace) Objects.requireNonNull(inv.player.level().getBlockEntity(data.readBlockPos())));
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int slot) {
        int inventoryStart = 4;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.slots.get(slot);

        if (theSlot != null && theSlot.hasItem()) {
            ItemStack newStack = theSlot.getItem();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if (slot == TileEntityPoweredFurnace.SLOT_OUTPUT_1 || slot == TileEntityPoweredFurnace.SLOT_OUTPUT_2) {
                if (!this.moveItemStackTo(newStack, inventoryStart, hotbarEnd + 1, true)) {
                    return ItemStack.EMPTY;
                }
                theSlot.onQuickCraft(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if (slot >= inventoryStart) {
                // TODO: VALIDATE
                RecipeHolder<SmeltingRecipe> recipeHolder = this.furnace.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleItem(newStack), this.furnace.getLevel()).orElse(null);
                if (recipeHolder == null) {
                    return ItemStack.EMPTY;
                }

                ItemStack recipeOutput = recipeHolder.value().getResultItem(player.level().registryAccess());

                //Shift from Inventory
                if (StackUtil.isValid(recipeOutput)) {
                    if (!this.moveItemStackTo(newStack, TileEntityPoweredFurnace.SLOT_INPUT_1, TileEntityPoweredFurnace.SLOT_INPUT_1 + 1, false)) {
                        if (!this.moveItemStackTo(newStack, TileEntityPoweredFurnace.SLOT_INPUT_2, TileEntityPoweredFurnace.SLOT_INPUT_2 + 1, false)) {
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
    public boolean stillValid(@Nonnull Player player) {
        return this.furnace.canPlayerUse(player);
    }
}
