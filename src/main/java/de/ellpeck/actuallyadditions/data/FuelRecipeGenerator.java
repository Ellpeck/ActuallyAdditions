package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.LiquidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class FuelRecipeGenerator extends RecipeProvider {
    public FuelRecipeGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public String getName() {
        return "Fuel " + super.getName();
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        var recipeOutput = new NoAdvRecipeOutput(output);


        recipeOutput.accept(ActuallyAdditions.modLoc("liquid_fuel/canola_oil"), new LiquidFuelRecipe(
            new FluidStack(InitFluids.CANOLA_OIL.get(), 50), 4000, 100), null);

        recipeOutput.accept(ActuallyAdditions.modLoc("liquid_fuel/refined_canola_oil"), new LiquidFuelRecipe(
            new FluidStack(InitFluids.REFINED_CANOLA_OIL.get(), 50), 9600, 120), null);

        recipeOutput.accept(ActuallyAdditions.modLoc("liquid_fuel/crystallized_canola_oil"),new LiquidFuelRecipe(
            new FluidStack(InitFluids.CRYSTALLIZED_OIL.get(), 50), 28000, 280), null);

        recipeOutput.accept(ActuallyAdditions.modLoc("liquid_fuel/empowered_canola_oil"), new LiquidFuelRecipe(
            new FluidStack(InitFluids.EMPOWERED_OIL.get(), 50), 48000, 400), null);
    }
}
