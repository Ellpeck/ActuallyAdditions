package de.ellpeck.actuallyadditions.mod.util;


import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
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

        ActuallyAdditionsAPI.SOLID_FUEL_RECIPES.clear();
        ActuallyAdditionsAPI.SOLID_FUEL_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.SOLID_FUEL.get()));

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

        ActuallyAdditionsAPI.CRUSHER_RECIPES.clear();
        ActuallyAdditionsAPI.CRUSHER_RECIPES.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.CRUSHING.get()));

        ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS.clear();
        ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS.addAll(recipeManager.getAllRecipesFor(ActuallyRecipes.Types.COFFEE_INGREDIENT.get()));
    }
}
