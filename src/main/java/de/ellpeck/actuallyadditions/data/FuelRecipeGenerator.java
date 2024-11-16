package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.LiquidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.SolidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
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

        addSolid(recipeOutput, "coal", Items.COAL, 32000, 1600);
        addSolid(recipeOutput, "stick", Items.STICK, 2000, 100);
        addSolid(recipeOutput, "tiny-coal", ActuallyTags.Items.TINY_COALS, 4000, 200);
        addSolid(recipeOutput, "charcoal", Items.CHARCOAL, 32000, 1600);
        addSolid(recipeOutput, "coal-block", Tags.Items.STORAGE_BLOCKS_COAL, 320000, 16000);
        addSolid(recipeOutput, "lava", Items.LAVA_BUCKET, 400000, 20000);


        recipeOutput.accept(ActuallyAdditions.modLoc("liquid_fuel/canola_oil"), new LiquidFuelRecipe(
            new FluidStack(InitFluids.CANOLA_OIL.get(), 50), 4000, 100), null);

        recipeOutput.accept(ActuallyAdditions.modLoc("liquid_fuel/refined_canola_oil"), new LiquidFuelRecipe(
            new FluidStack(InitFluids.REFINED_CANOLA_OIL.get(), 50), 9600, 120), null);

        recipeOutput.accept(ActuallyAdditions.modLoc("liquid_fuel/crystallized_canola_oil"),new LiquidFuelRecipe(
            new FluidStack(InitFluids.CRYSTALLIZED_OIL.get(), 50), 28000, 280), null);

        recipeOutput.accept(ActuallyAdditions.modLoc("liquid_fuel/empowered_canola_oil"), new LiquidFuelRecipe(
            new FluidStack(InitFluids.EMPOWERED_OIL.get(), 50), 48000, 400), null);
    }

    private void addSolid(RecipeOutput consumer, String name, Item item, int energy, int burnTime) {
        ResourceLocation id = ActuallyAdditions.modLoc("solid_fuel/"+name);
        consumer.accept(id, new SolidFuelRecipe(Ingredient.of(item), energy, burnTime), null);
    }
    private void addSolid(RecipeOutput consumer, String name, Ingredient item, int energy, int burnTime) {
        ResourceLocation id = ActuallyAdditions.modLoc("solid_fuel/"+name);
        consumer.accept(id, new SolidFuelRecipe(item, energy, burnTime), null);
    }
    private void addSolid(RecipeOutput consumer, String name, TagKey<Item> tag, int energy, int burnTime) {
        ResourceLocation id = ActuallyAdditions.modLoc("solid_fuel/"+name);
        consumer.accept(id, new SolidFuelRecipe(Ingredient.of(tag), energy, burnTime), null);
    }
}
