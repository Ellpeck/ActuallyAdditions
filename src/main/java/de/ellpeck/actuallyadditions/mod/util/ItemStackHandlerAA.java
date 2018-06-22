/*
 * This file ("ItemStackHandlerAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

/**
 * The Actually Additions variant of ItemStackHandler.  Provides methods to disallow add/removal based on automation context.  Defaults to thinking operations are performed by automation.
 * @author Shadows
 */
public class ItemStackHandlerAA extends ItemStackHandler {

    public ItemStackHandlerAA(int slots) {
        super(slots);
    }

    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return insertItem(slot, stack, simulate, true);
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate, boolean fromAutomation) {
        if (!canAccept(slot, stack, fromAutomation)) return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return extractItem(slot, amount, simulate, true);
    }

    public ItemStack extractItem(int slot, int amount, boolean simulate, boolean byAutomation) {
        if (!canRemove(slot, byAutomation)) return ItemStack.EMPTY;
        return super.extractItem(slot, amount, simulate);
    }

    public boolean canAccept(int slot, ItemStack stack, boolean fromAutomation) {
        return true;
    }

    public boolean canRemove(int slot, boolean byAutomation) {
        return true;
    }
}
