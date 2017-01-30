/*
 * This file ("RecipeBioMash.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemKnife;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class RecipeBioMash implements IRecipe{

    @Override
    public boolean matches(InventoryCrafting inv, World world){
        boolean foundFood = false;
        boolean hasKnife = false;

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(StackUtil.isValid(stack)){
                if(stack.getItem() instanceof ItemKnife){
                    if(hasKnife){
                        return false;
                    }
                    else{
                        hasKnife = true;
                    }
                }
                else if(stack.getItem() instanceof ItemFood){
                    foundFood = true;
                }
                else{
                    return false;
                }
            }
        }

        return foundFood && hasKnife;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv){
        int amount = 0;

        for(int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack stack = inv.getStackInSlot(i);
            if(StackUtil.isValid(stack)){
                if(stack.getItem() instanceof ItemFood){
                    ItemFood food = (ItemFood)stack.getItem();
                    float heal = food.getHealAmount(stack);
                    float sat = food.getSaturationModifier(stack);

                    amount += MathHelper.ceil(heal*sat);
                }
            }
        }

        if(amount > 0 && amount <= 64){
            return new ItemStack(InitItems.itemMisc, amount, TheMiscItems.MASHED_FOOD.ordinal());
        }
        else{
            return StackUtil.getNull();
        }
    }

    @Override
    public int getRecipeSize(){
        return 2;
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
