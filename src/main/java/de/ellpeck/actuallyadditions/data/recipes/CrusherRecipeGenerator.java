package de.ellpeck.actuallyadditions.data.recipes;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.recipes.CrusherRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class CrusherRecipeGenerator extends RecipeProvider {
    
    public CrusherRecipeGenerator(DataGenerator dataGenerator){
        super(dataGenerator);
    }
    
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer){
        super.registerRecipes(consumer);
        consumer.accept(new CrusherRecipe(new ResourceLocation(ActuallyAdditions.MODID, "test"), Ingredient.fromItems(Items.DIAMOND), new ItemStack(Items.DIRT, 64)));
    }
}
