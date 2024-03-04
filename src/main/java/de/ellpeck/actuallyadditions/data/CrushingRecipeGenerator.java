package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class CrushingRecipeGenerator extends RecipeProvider {
    public CrushingRecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Crushing " + super.getName();
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        var recipeOutput = new NoAdvRecipeOutput(output);


        CrushingRecipe recipe = new CrushingRecipe(Ingredient.of(Items.BONE), new ItemStack(Items.BONE_MEAL, 6), 1.0f, ItemStack.EMPTY, 0.0f);
        recipeOutput.accept(new ResourceLocation(ActuallyAdditions.MODID, "crushing/iron_crusher"), recipe, null);
    }
}
