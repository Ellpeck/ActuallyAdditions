package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.LiquidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.SolidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FuelRecipeGenerator extends RecipeProvider {
    public FuelRecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Fuel " + super.getName();
    }

    @Override
    protected @Nullable CompletableFuture<?> saveAdvancement(CachedOutput output, FinishedRecipe finishedRecipe, JsonObject advancementJson) {
        return null; //Nope...
    }

    @Override
    protected void buildRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        addSolid(consumer, "coal", Items.COAL, 32000, 1600);
        addSolid(consumer, "stick", Items.STICK, 2000, 100);
        addSolid(consumer, "tiny-coal", ActuallyTags.Items.TINY_COALS, 4000, 200);
        addSolid(consumer, "charcoal", Items.CHARCOAL, 32000, 1600);
        addSolid(consumer, "coal-block", Items.COAL_BLOCK, 320000, 16000);
        addSolid(consumer, "lava", Items.LAVA_BUCKET, 400000, 20000);

        consumer.accept(new LiquidFuelRecipe.Result(new ResourceLocation(ActuallyAdditions.MODID, "liquid_fuel/canola_oil"),
            new FluidStack(InitFluids.CANOLA_OIL.get(), 50), 4000, 100));

        consumer.accept(new LiquidFuelRecipe.Result(new ResourceLocation(ActuallyAdditions.MODID, "liquid_fuel/refined_canola_oil"),
            new FluidStack(InitFluids.REFINED_CANOLA_OIL.get(), 50), 9600, 120));

        consumer.accept(new LiquidFuelRecipe.Result(new ResourceLocation(ActuallyAdditions.MODID, "liquid_fuel/crystallized_canola_oil"),
            new FluidStack(InitFluids.CRYSTALLIZED_OIL.get(), 50), 28000, 280));

        consumer.accept(new LiquidFuelRecipe.Result(new ResourceLocation(ActuallyAdditions.MODID, "liquid_fuel/empowered_canola_oil"),
            new FluidStack(InitFluids.EMPOWERED_OIL.get(), 50), 48000, 400));
    }

    private void addSolid(Consumer<FinishedRecipe> consumer, String name, Item item, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.Result(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), Ingredient.of(item), energy, burnTime));
    }
    private void addSolid(Consumer<FinishedRecipe> consumer, String name, Ingredient item, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.Result(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), item, energy, burnTime));
    }
    private void addSolid(Consumer<FinishedRecipe> consumer, String name, TagKey<Item> tag, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.Result(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), Ingredient.of(tag), energy, burnTime));
    }
}
