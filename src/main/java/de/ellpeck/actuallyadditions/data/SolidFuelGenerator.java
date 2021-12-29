package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.SolidFuelRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

import java.nio.file.Path;
import java.util.function.Consumer;

public class SolidFuelGenerator extends RecipeProvider {
    public SolidFuelGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void saveAdvancement(DirectoryCache pCache, JsonObject pAdvancementJson, Path pPath) {
        //Nah
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        addFuel(consumer, "coal", Items.COAL, 48000, 1600);
        addFuel(consumer, "stick", Items.STICK, 1000, 200); //TEST
        addFuel(consumer, "charcoal", Items.CHARCOAL, 48000, 1600);
    }

    private void addFuel(Consumer<IFinishedRecipe> consumer, String name, Item item, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), Ingredient.of(item), energy, burnTime));
    }
    private void addFuel(Consumer<IFinishedRecipe> consumer, String name, Ingredient item, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), item, energy, burnTime));
    }
    private void addFuel(Consumer<IFinishedRecipe> consumer, String name, ITag tag, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), Ingredient.of(tag), energy, burnTime));
    }
}
