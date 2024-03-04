package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class CrushingRecipeGenerator extends RecipeProvider {
    public CrushingRecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Crushing " + super.getName();
    }

//    @Override //TODO: Flanks do your RecipeOutput wrapper thingy ;)
//    protected @Nullable CompletableFuture<?> saveAdvancement(CachedOutput stack, FinishedRecipe finishedRecipe, JsonObject advancementJson) {
//        return null;
//        //Nope... maybe later...
//    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        CrushingRecipe recipe = new CrushingRecipe(Ingredient.of(Items.BONE), new ItemStack(Items.BONE_MEAL, 6), 1.0f, ItemStack.EMPTY, 0.0f);
        consumer.accept(new ResourceLocation(ActuallyAdditions.MODID, "crushing/iron_crusher"), recipe, null);
    }
}
