package de.ellpeck.actuallyadditions.mod.util;


import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.crafting.MiningLensRecipe;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeManager;

@SuppressWarnings("deprecation")
public class ResourceReloader implements ResourceManagerReloadListener {
    private final ReloadableServerResources data;
    public ResourceReloader(ReloadableServerResources dataPackRegistries) {
        data = dataPackRegistries;
    }

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        RecipeManager recipeManager = data.getRecipeManager();
        ActuallyAdditionsAPI.EMPOWERER_RECIPES.clear();
        ActuallyAdditionsAPI.EMPOWERER_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.EMPOWERING.get()));

        ActuallyAdditionsAPI.LIQUID_FUEL_RECIPES.clear();
        ActuallyAdditionsAPI.LIQUID_FUEL_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.LIQUID_FUEL.get()));

        ActuallyAdditionsAPI.PRESSING_RECIPES.clear();
        ActuallyAdditionsAPI.PRESSING_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.PRESSING.get()));

        ActuallyAdditionsAPI.FERMENTING_RECIPES.clear();
        ActuallyAdditionsAPI.FERMENTING_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.FERMENTING.get()));

        ActuallyAdditionsAPI.CONVERSION_LASER_RECIPES.clear();
        ActuallyAdditionsAPI.CONVERSION_LASER_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.LASER.get()));

        ActuallyAdditionsAPI.COLOR_CHANGE_RECIPES.clear();
        ActuallyAdditionsAPI.COLOR_CHANGE_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.COLOR_CHANGE.get()));

        ActuallyAdditionsAPI.MINING_LENS_RECIPES.clear();
        ActuallyAdditionsAPI.MINING_LENS_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.MINING_LENS.get()));

        // Cache mining lens total weights by type
        MiningLensRecipe.WEIGHT_CACHE.clear();
        ActuallyAdditionsAPI.MINING_LENS_RECIPES.forEach(recipe -> {
            MiningLensRecipe.Type type = recipe.value().getInputType();
            MiningLensRecipe.WEIGHT_CACHE.put(type, MiningLensRecipe.WEIGHT_CACHE.getOrDefault(type, 0) + recipe.value().getWeight().asInt());
        });

        ActuallyAdditionsAPI.CRUSHER_RECIPES.clear();
        ActuallyAdditionsAPI.CRUSHER_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.CRUSHING.get()));

        ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS.clear();
        ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.COFFEE_INGREDIENT.get()));
    }
}
