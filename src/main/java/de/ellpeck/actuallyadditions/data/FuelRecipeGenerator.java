package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.LiquidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.SolidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.function.Consumer;

public class FuelRecipeGenerator extends RecipeProvider {
    public FuelRecipeGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void saveAdvancement(@Nonnull DirectoryCache pCache, @Nonnull JsonObject pAdvancementJson, @Nonnull Path pPath) {
        //Nah
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        addSolid(consumer, "coal", Items.COAL, 32000, 1600);
        addSolid(consumer, "stick", Items.STICK, 2000, 100);
        addSolid(consumer, "tiny-coal", ActuallyTags.Items.TINY_COALS, 4000, 200);
        addSolid(consumer, "charcoal", Items.CHARCOAL, 32000, 1600);
        addSolid(consumer, "coal-block", Items.COAL_BLOCK, 320000, 16000);
        addSolid(consumer, "lava", Items.LAVA_BUCKET, 400000, 20000);

        consumer.accept(new LiquidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "liquid_fuel/canola_oil"),
            new FluidStack(InitFluids.CANOLA_OIL.get(), 50), 4000, 100));

        consumer.accept(new LiquidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "liquid_fuel/refined_canola_oil"),
            new FluidStack(InitFluids.REFINED_CANOLA_OIL.get(), 50), 9600, 120));

        consumer.accept(new LiquidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "liquid_fuel/crystallized_canola_oil"),
            new FluidStack(InitFluids.CRYSTALLIZED_OIL.get(), 50), 28000, 280));

        consumer.accept(new LiquidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "liquid_fuel/empowered_canola_oil"),
            new FluidStack(InitFluids.EMPOWERED_OIL.get(), 50), 48000, 400));
    }

    private void addSolid(Consumer<IFinishedRecipe> consumer, String name, Item item, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), Ingredient.of(item), energy, burnTime));
    }
    private void addSolid(Consumer<IFinishedRecipe> consumer, String name, Ingredient item, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), item, energy, burnTime));
    }
    private void addSolid(Consumer<IFinishedRecipe> consumer, String name, ITag<Item> tag, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), Ingredient.of(tag), energy, burnTime));
    }
}
