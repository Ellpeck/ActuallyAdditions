package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.util.IItemProvider;

import java.nio.file.Path;
import java.util.function.Consumer;

public class MiscRecipeGenerator extends RecipeProvider {
    public MiscRecipeGenerator(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void saveAdvancement(DirectoryCache pCache, JsonObject pAdvancementJson, Path pPath) {
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> p_200404_0_) {

    }



    private void addCrystalEmpowering(Consumer<IFinishedRecipe> consumer) {

    }
}
