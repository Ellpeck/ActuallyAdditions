package de.ellpeck.actuallyadditions.common.util;

import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.common.util.crafting.RecipeHandler;
import net.minecraft.item.crafting.IRecipe;

public final class RecipeUtil {

    public static LensConversionRecipe lastReconstructorRecipe() {
        List<LensConversionRecipe> list = ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES;
        return list.get(list.size() - 1);
    }

    public static CrusherRecipe lastCrusherRecipe() {
        List<CrusherRecipe> list = ActuallyAdditionsAPI.CRUSHER_RECIPES;
        return list.get(list.size() - 1);
    }

    public static IRecipe lastIRecipe() {
        return RecipeHandler.lastRecipe;
    }

    public static EmpowererRecipe lastEmpowererRecipe() {
        List<EmpowererRecipe> list = ActuallyAdditionsAPI.EMPOWERER_RECIPES;
        return list.get(list.size() - 1);
    }
}
