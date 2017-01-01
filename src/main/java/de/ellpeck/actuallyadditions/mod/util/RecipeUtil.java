/*
 * This file ("RecipeUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

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
}
