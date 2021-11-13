package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.function.Consumer;

public class CrushingRecipeGenerator extends RecipeProvider {
    public CrushingRecipeGenerator(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void saveAdvancement(@Nonnull DirectoryCache p_208310_1_, @Nonnull JsonObject p_208310_2_, @Nonnull Path p_208310_3_) {
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        consumer.accept(new CrushingRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "bone_crusher"),
            Ingredient.of(Items.BONE), Items.BONE_MEAL, 6, 1.0f , Items.AIR, 0, 0.0f));
    }
}
