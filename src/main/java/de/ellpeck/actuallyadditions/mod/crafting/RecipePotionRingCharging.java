/*
 * This file ("RecipePotionRingCharging.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.items.ItemPotionRing;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class RecipePotionRingCharging implements IRecipe{

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn){
        boolean hasRing = false;

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(StackUtil.isValid(stack)){
                if(stack.getItem() instanceof ItemPotionRing){
                    if(!hasRing){
                        hasRing = true;
                    }
                    else{
                        return false;
                    }
                }
                else if(stack.getItem() != Items.BLAZE_POWDER){
                    return false;
                }
            }
        }

        return hasRing;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv){
        ItemStack inputRing = StackUtil.getNull();
        int totalBlaze = 0;

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(StackUtil.isValid(stack)){
                if(stack.getItem() instanceof ItemPotionRing){
                    inputRing = stack;
                }
                else if(stack.getItem() == Items.BLAZE_POWDER){
                    totalBlaze += 20;
                }
            }
        }

        if(StackUtil.isValid(inputRing) && totalBlaze > 0){
            ItemStack copy = inputRing.copy();

            int total = ItemPotionRing.getStoredBlaze(copy)+totalBlaze;
            if(total <= ItemPotionRing.MAX_BLAZE){
                ItemPotionRing.setStoredBlaze(copy, total);
                return copy;
            }
        }

        return StackUtil.getNull();
    }

    @Override
    public int getRecipeSize(){
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput(){
        return StackUtil.getNull();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv){
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }
}
