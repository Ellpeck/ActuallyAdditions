package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.SolidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.base.ActuallyItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.function.Consumer;

public class SolidFuelGenerator extends RecipeProvider {
    public SolidFuelGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void saveAdvancement(@Nonnull DirectoryCache pCache, @Nonnull JsonObject pAdvancementJson, @Nonnull Path pPath) {
        //Nah
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        addFuel(consumer, "coal", Items.COAL, 32000, 1600);
        addFuel(consumer, "stick", Items.STICK, 2000, 100);
        addFuel(consumer, "tiny-coal", ActuallyTags.Items.TINY_COALS, 4000, 200);
        addFuel(consumer, "charcoal", Items.CHARCOAL, 32000, 1600);
        addFuel(consumer, "coal-block", Items.COAL_BLOCK, 320000, 16000);
        addFuel(consumer, "charcoal-block", ActuallyBlocks.CHARCOAL_BLOCK.getItem(), 320000, 16000);
        addFuel(consumer, "lava", Items.LAVA_BUCKET, 400000, 20000);
    }

    private void addFuel(Consumer<IFinishedRecipe> consumer, String name, Item item, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), Ingredient.of(item), energy, burnTime));
    }
    private void addFuel(Consumer<IFinishedRecipe> consumer, String name, Ingredient item, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), item, energy, burnTime));
    }
    private void addFuel(Consumer<IFinishedRecipe> consumer, String name, ITag<Item> tag, int energy, int burnTime) {
        consumer.accept(new SolidFuelRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "solid_fuel/"+name), Ingredient.of(tag), energy, burnTime));
    }
}
