package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.function.Consumer;

public class CrushingRecipeGenerator extends RecipeProvider {
    public CrushingRecipeGenerator(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void saveAdvancement(@Nonnull HashCache p_208310_1_, @Nonnull JsonObject p_208310_2_, @Nonnull Path p_208310_3_) {
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new CrushingRecipe.Result(new ResourceLocation(ActuallyAdditions.MODID, "crushing/bone_crusher"),
            Ingredient.of(Items.BONE), Items.BONE_MEAL, 6, 1.0f , Items.AIR, 0, 0.0f));
    }
}
