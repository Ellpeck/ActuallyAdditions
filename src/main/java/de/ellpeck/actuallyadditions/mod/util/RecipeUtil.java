/*
 * This file ("RecipeUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RecipeUtil{

    public static LensConversionRecipe lastReconstructorRecipe(){
        List<LensConversionRecipe> list = ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES;
        return list.get(list.size()-1);
    }

    public static CrusherRecipe lastCrusherRecipe(){
        List<CrusherRecipe> list = ActuallyAdditionsAPI.CRUSHER_RECIPES;
        return list.get(list.size()-1);
    }

    public static IRecipe lastIRecipe(){
        List<IRecipe> list = CraftingManager.getInstance().getRecipeList();
        return list.get(list.size()-1);
    }

    public static EmpowererRecipe lastEmpowererRecipe(){
        List<EmpowererRecipe> list = ActuallyAdditionsAPI.EMPOWERER_RECIPES;
        return list.get(list.size()-1);
    }

    public static List<ItemStack> getCrusherRecipeOutputOnes(CrusherRecipe recipe){
        return doRecipeOrWhatever(recipe.outputOneStack, recipe.outputOne, recipe.outputOneAmount);
    }

    public static List<ItemStack> getCrusherRecipeOutputTwos(CrusherRecipe recipe){
        return doRecipeOrWhatever(recipe.outputTwoStack, recipe.outputTwo, recipe.outputTwoAmount);
    }

    public static List<ItemStack> getCrusherRecipeInputs(CrusherRecipe recipe){
        return doRecipeOrWhatever(recipe.inputStack, recipe.input, 1);
    }

    public static List<ItemStack> getConversionLensInputs(LensConversionRecipe recipe){
        return doRecipeOrWhatever(recipe.inputStack, recipe.input, 1);
    }

    public static List<ItemStack> getConversionLensOutputs(LensConversionRecipe recipe){
        return doRecipeOrWhatever(recipe.outputStack, recipe.output, 1);
    }

    private static List<ItemStack> doRecipeOrWhatever(ItemStack stack, String oredict, int amount){
        if(stack != null){
            return Collections.singletonList(stack.copy());
        }

        if(oredict == null || oredict.isEmpty()){
            return null;
        }

        List<ItemStack> stacks = OreDictionary.getOres(oredict, false);
        if(stacks != null && !stacks.isEmpty()){
            List<ItemStack> stacksCopy = new ArrayList<ItemStack>();
            for(ItemStack aStack : stacks){
                if(aStack != null){
                    ItemStack stackCopy = aStack.copy();
                    stackCopy.stackSize = amount;
                    stacksCopy.add(stackCopy);
                }
            }
            return stacksCopy;
        }
        return null;
    }
}
