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

    public static final IAcceptor ACCEPT_TRUE = (a, b, c) -> true;
    public static final IRemover REMOVE_TRUE = (a, b) -> true;
    public static final IAcceptor ACCEPT_FALSE = (a, b, c) -> false;
    public static final IRemover REMOVE_FALSE = (a, b) -> false;

    IAcceptor acceptor;
    IRemover remover;

    public ItemStackHandlerAA(NonNullList<ItemStack> stacks, IAcceptor acceptor, IRemover remover) {
        super(stacks);
        this.acceptor = acceptor;
        this.remover = remover;
    }

    public ItemStackHandlerAA(int slots, IAcceptor acceptor, IRemover remover) {
        super(slots);
        this.acceptor = acceptor;
        this.remover = remover;
    }

    public ItemStackHandlerAA(NonNullList<ItemStack> stacks) {
        this(stacks, ACCEPT_TRUE, REMOVE_TRUE);
    }

    public ItemStackHandlerAA(int slots) {
        this(slots, ACCEPT_TRUE, REMOVE_TRUE);
    }

    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return this.insertItem(slot, stack, simulate, true);
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate, boolean fromAutomation) {
        if (!this.canAccept(slot, stack, fromAutomation)) return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.extractItem(slot, amount, simulate, true);
    }

    public ItemStack extractItem(int slot, int amount, boolean simulate, boolean byAutomation) {
        if (!this.canRemove(slot, byAutomation)) return ItemStack.EMPTY;
        return super.extractItem(slot, amount, simulate);
    }

    public final boolean canAccept(int slot, ItemStack stack, boolean automation) {
        return this.getAcceptor().canAccept(slot, stack, automation);
    }

    public final boolean canRemove(int slot, boolean automation) {
        return this.getRemover().canRemove(slot, automation);
    }

    public IAcceptor getAcceptor() {
        return this.acceptor;
    }

    public IRemover getRemover() {
        return this.remover;
    }

    public static interface IAcceptor {
        boolean canAccept(int slot, ItemStack stack, boolean automation);
    }

    public static interface IRemover {
        boolean canRemove(int slot, boolean automation);
    }
}
