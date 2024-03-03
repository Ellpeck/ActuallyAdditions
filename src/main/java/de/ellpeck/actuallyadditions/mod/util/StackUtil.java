/*
 * This file ("StackUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Collection;
import java.util.List;

public final class StackUtil {

    /**
     * Pretty much just a check for {@link ItemStack#isEmpty()} but exists in case Mojang does some more refactoring.
     *
     * @param stack The stack
     * @return If the stack is not empty, or if it's an IDisableableItem, if its enabled.
     */
    @Deprecated
    public static boolean isValid(ItemStack stack) {
        return stack != null && !stack.isEmpty();
        //        if (stack == null) AwfulUtil.callTheFuckinPolice("Null ItemStack detected", stack);
        //        Item i = stack.getItem();
        //        if (i instanceof IDisableableItem) return !((IDisableableItem) i).isDisabled();
        //        return !stack.isEmpty();
    }

    /**
     * Checks if a collection of stacks are empty, as {@link Collection#isEmpty()} does not care about empty stacks.
     *
     * @param stacks Some ItemStacks
     * @return If all stacks in the collection return true for {@link ItemStack#isEmpty()}
     */
    @Deprecated
    public static boolean isEmpty(Collection<ItemStack> stacks) {
        if (stacks.isEmpty()) {
            return true;
        }
        for (ItemStack s : stacks) {
            if (!s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all provided itemstacks will fit in the AA handler.  Use addAll below to actually add the stacks.  This is strictly a check function.
     *
     * @param inv            The AA Item handler
     * @param stacks         The stacks to add
     * @param fromAutomation If these stacks are coming from a pipe or other external source, or internally, like from the TE's update() method.
     * @return If all stacks fit fully.  If even one item would not fit, the method returns false.
     */
    public static boolean canAddAll(ItemStackHandlerAA inv, List<ItemStack> stacks, boolean fromAutomation) {
        int counter = 0;
        ItemStackHandlerAA dummy = testDummy(inv, 0, inv.getSlots());
        for (ItemStack s : stacks) {
            for (int i = 0; i < dummy.getSlots(); i++) {
                s = dummy.insertItem(i, s, false, fromAutomation);
                if (s.isEmpty()) {
                    break;
                }
            }
            if (s.isEmpty()) {
                counter++;
            }
        }
        return counter == stacks.size();
    }

    /**
     * Adds all itemstacks in a list to an AA item handler.  Must be an AA item handler to support the automation bool.
     *
     * @param inv            The AA Item handler
     * @param stacks         The stacks to add
     * @param fromAutomation If these stacks are coming from a pipe or other external source, or internally, like from the TE's update() method.
     */
    public static void addAll(ItemStackHandlerAA inv, List<ItemStack> stacks, boolean fromAutomation) {
        int slotMax = inv.getSlots();
        for (ItemStack s : stacks) {
            for (int i = 0; i < slotMax; i++) {
                s = inv.insertItem(i, s, false, fromAutomation);
                if (s.isEmpty()) {
                    break;
                }
            }
        }
    }

    /**
     * Checks if all provided itemstacks will fit in the AA handler.  Use addAll below to actually add the stacks.  This is strictly a check function.
     *
     * @param inv            The AA Item handler
     * @param stacks         The stacks to add
     * @param slot           The starting slot.
     * @param endSlot        The ending slot, exclusive.
     * @param fromAutomation If these stacks are coming from a pipe or other external source, or internally, like from the TE's update() method.
     * @return If all stacks fit fully.  If even one item would not fit, the method returns false.
     */
    public static boolean canAddAll(ItemStackHandlerAA inv, List<ItemStack> stacks, int slot, int endSlot, boolean fromAutomation) {
        int counter = 0;
        ItemStackHandlerAA dummy = testDummy(inv, slot, endSlot);
        for (ItemStack s : stacks) {
            for (int i = 0; i < dummy.getSlots(); i++) {
                s = dummy.insertItem(i, s, false, fromAutomation);
                if (s.isEmpty()) {
                    break;
                }
            }
            if (s.isEmpty()) {
                counter++;
            }
        }
        return counter == stacks.size();
    }

    /**
     * Adds all itemstacks in a list to an AA item handler.  Must be an AA item handler to support the automation bool.
     *
     * @param inv            The AA Item handler
     * @param stacks         The stacks to add
     * @param slot           The starting slot.
     * @param endSlot        The ending slot, exclusive.
     * @param fromAutomation If these stacks are coming from a pipe or other external source, or internally, like from the TE's update() method.
     */
    public static void addAll(ItemStackHandlerAA inv, List<ItemStack> stacks, int slot, int endSlot, boolean fromAutomation) {
        for (ItemStack s : stacks) {
            for (int i = slot; i < endSlot; i++) {
                s = inv.insertItem(i, s, false, fromAutomation);
                if (s.isEmpty()) {
                    break;
                }
            }
        }
    }

    /**
     * Util method to find the first filled item in a handler.  Searches from slot 0 to the end.
     *
     * @param inv The IItemHandler to search.
     * @return The first filled slot, or -1 if all slots are empty.
     */
    public static int findFirstFilled(IItemHandler inv) {
        for (int i = 0; i < inv.getSlots(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Helper method to add stack size and return the stack.
     */
    public static ItemStack grow(ItemStack s, int i) {
        s.grow(i);
        return s;
    }

    /**
     * Helper method to remove stack size and return the stack.
     */
    public static ItemStack shrink(ItemStack s, int i) {
        s.shrink(i);
        return s;
    }

    /**
     * Helper method to remove stack size and return the stack.
     */
    public static ItemStack shrinkForContainer(ItemStack s, int i) {
        ItemStack sc = s.copy();
        s.shrink(i);
        if (s.isEmpty()) {
            return sc.getItem().getCraftingRemainingItem(sc);
        }
        return s;
    }

    /**
     * Interaction method for working with Common Capabilities.
     *
     * @param wrapper   The wrapper holding at least one instance
     * @param stack     The stack to insert.  Should not be empty.
     * @param simulate  If this is a simulation
     * @param slotStart Start range
     * @param slotEnd   End range
     * @return The remainder that was not inserted.
     */
    public static ItemStack insertItem(SlotlessableItemHandlerWrapper wrapper, ItemStack stack, boolean simulate, int slotStart, int slotEnd) {
        if (stack.isEmpty()) {
            return stack;
        }

        if (ActuallyAdditions.commonCapsLoaded) {
//            Object handler = wrapper.getSlotlessHandler();
            //            if (handler instanceof ISlotlessItemHandler) {
            //                remain = ((ISlotlessItemHandler) handler).insertItem(remain, simulate);
            //                if (!ItemStack.areItemStacksEqual(remain, stack)) {
            //                    return remain;
            //                }
            //            }
        }

        return wrapper.getNormalHandler().map(e -> {
            ItemStack remain = stack.copy();
            for (int i = Math.max(0, slotStart); i < Math.min(slotEnd, e.getSlots()); i++) {
                remain = e.insertItem(i, remain, simulate);
            }

            return remain;
        }).orElse(stack);
    }

    /**
     * Constructs a clone of the given item handler, from the given slots.  The new item handler will have the provided slot as slot 0.
     * This is used for testing the ability to add all itemstacks, and should not be used for anything else.
     */
    public static ItemStackHandlerAA testDummy(ItemStackHandlerAA inv, int slot, int endSlot) {
        NonNullList<ItemStack> stacks = NonNullList.withSize(endSlot - slot, ItemStack.EMPTY);
        for (int i = slot; i < endSlot; i++) {
            stacks.set(i - slot, inv.getStackInSlot(i).copy());
        }
        return new ItemStackHandlerAA(stacks, inv.getAcceptor(), inv.getRemover());
    }

}
