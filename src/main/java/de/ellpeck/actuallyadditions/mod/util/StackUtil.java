/*
 * This file ("StackUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public final class StackUtil{

    public static ItemStack validateCopy(ItemStack stack){
        if(isValid(stack)){
            return stack.copy();
        }
        else{
            return getNull();
        }
    }

    public static ItemStack validateCheck(ItemStack stack){
        if(isValid(stack)){
            return stack;
        }
        else{
            return getNull();
        }
    }

    public static boolean isValid(ItemStack stack){
        return stack != null && !stack.func_190926_b();
    }

    public static ItemStack getNull(){
        return ItemStack.field_190927_a;
    }

    public static int getStackSize(ItemStack stack){
        if(!isValid(stack)){
            return 0;
        }
        else{
            return stack.func_190916_E();
        }
    }

    public static ItemStack setStackSize(ItemStack stack, int size){
        if(size <= 0){
            if(isValid(stack)){
                return stack.getItem().getContainerItem(stack);
            }
            else{
                return getNull();
            }
        }
        stack.func_190920_e(size);
        return stack;
    }

    public static ItemStack addStackSize(ItemStack stack, int size){
        return setStackSize(stack, getStackSize(stack)+size);
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
        return NonNullList.func_191197_a(size, getNull());
    }


}
