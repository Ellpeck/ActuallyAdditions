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

import java.util.Collection;
import java.util.List;

import de.ellpeck.actuallyadditions.api.misc.IDisableableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

public final class StackUtil{

    public static ItemStack validateCopy(ItemStack stack){
        if(isValid(stack)){
            return stack.copy();
        }
        else{
            return getEmpty();
        }
    }

    public static ItemStack validateCheck(ItemStack stack){
        if(isValid(stack)){
            return stack;
        }
        else{
            return getEmpty();
        }
    }

    public static boolean isValid(ItemStack stack){
    	if(stack == null) AwfulUtil.callTheFuckinPolice("Null ItemStack detected", stack);
    	Item i = stack.getItem();
    	if(i instanceof IDisableableItem) return !((IDisableableItem) i).isDisabled();
        return !stack.isEmpty();
    }

    public static ItemStack getEmpty(){
        return ItemStack.EMPTY;
    }

    public static int getStackSize(ItemStack stack){
        if(!isValid(stack)){
            return 0;
        }
        else{
            return stack.getCount();
        }
    }

    public static ItemStack setStackSize(ItemStack stack, int size){
        return setStackSize(stack, size, false);
    }

    public static ItemStack setStackSize(ItemStack stack, int size, boolean containerOnEmpty){
        if(size <= 0){
            if(isValid(stack) && containerOnEmpty){
                return stack.getItem().getContainerItem(stack);
            }
            else{
                return getEmpty();
            }
        }
        stack.setCount(size);
        return stack;
    }

    public static ItemStack addStackSize(ItemStack stack, int size){
        return addStackSize(stack, size, false);
    }

    public static ItemStack addStackSize(ItemStack stack, int size, boolean containerOnEmpty){
        return setStackSize(stack, getStackSize(stack)+size, containerOnEmpty);
    }

    public static boolean isIInvEmpty(NonNullList<ItemStack> slots){
        for(ItemStack stack : slots){
            if(StackUtil.isValid(stack)){
                return false;
            }
        }

        return true;
    }

    public static NonNullList<ItemStack> createSlots(int size){
        return NonNullList.withSize(size, getEmpty());
    }
    
    public static boolean isEmpty(Collection<ItemStack> stacks) {
    	if(stacks.isEmpty()) return true;
    	else for(ItemStack s : stacks) if (!s.isEmpty()) return false;
    	return true;
    }
    
    public static boolean canAddAll(IItemHandler inv, List<ItemStack> stacks) {

        int slotMax = inv.getSlots();
        int counter = 0;

        for(ItemStack s : stacks) {
            for(int i = 0; i < slotMax; i++) {
                s = inv.insertItem(i, s, true);
                if(s.isEmpty()) break;
            }
            if(s.isEmpty()) counter++;
        }
        return counter == stacks.size();
    }
    
    public static void addAll(IItemHandler inv, List<ItemStack> stacks) {
        int slotMax = inv.getSlots();
        for(ItemStack s : stacks) {
            for(int i = 0; i < slotMax; i++) {
                s = inv.insertItem(i, s, false);
                if(s.isEmpty()) break;
            }
        }
    }


}
