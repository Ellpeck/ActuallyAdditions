package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.ColorChangeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.function.Consumer;

public class ColorChangeGenerator extends RecipeProvider {
    public ColorChangeGenerator(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }
    @Override
    protected void saveAdvancement(@Nonnull DirectoryCache pCache, @Nonnull JsonObject pAdvancementJson, @Nonnull Path pPath) {
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        buildWool(consumer);
        buildStainedGlass(consumer);
        buildStainedGlassPane(consumer);
        buildTerracotta(consumer);
        buildGlazedTerracotta(consumer);
        buildCarpet(consumer);
        buildLamps(consumer);
    }

    private void buildWool(@Nonnull Consumer<IFinishedRecipe> c) {
        //Wool
        changeColor(c, Items.WHITE_WOOL, Items.BLACK_WOOL);
        changeColor(c, Items.ORANGE_WOOL, Items.WHITE_WOOL);
        changeColor(c, Items.MAGENTA_WOOL, Items.ORANGE_WOOL);
        changeColor(c, Items.LIGHT_BLUE_WOOL, Items.MAGENTA_WOOL);
        changeColor(c, Items.YELLOW_WOOL, Items.LIGHT_BLUE_WOOL);
        changeColor(c, Items.LIME_WOOL, Items.YELLOW_WOOL);
        changeColor(c, Items.PINK_WOOL, Items.LIME_WOOL);
        changeColor(c, Items.GRAY_WOOL, Items.PINK_WOOL);
        changeColor(c, Items.LIGHT_GRAY_WOOL, Items.GRAY_WOOL);
        changeColor(c, Items.CYAN_WOOL, Items.LIGHT_GRAY_WOOL);
        changeColor(c, Items.PURPLE_WOOL, Items.CYAN_WOOL);
        changeColor(c, Items.BLUE_WOOL, Items.PURPLE_WOOL);
        changeColor(c, Items.BROWN_WOOL, Items.BLUE_WOOL);
        changeColor(c, Items.GREEN_WOOL, Items.BROWN_WOOL);
        changeColor(c, Items.RED_WOOL, Items.GREEN_WOOL);
        changeColor(c, Items.BLACK_WOOL, Items.RED_WOOL);
    }

    private void buildStainedGlass(@Nonnull Consumer<IFinishedRecipe> c) {
        changeColor(c, Items.WHITE_STAINED_GLASS, Items.BLACK_STAINED_GLASS);
        changeColor(c, Items.ORANGE_STAINED_GLASS, Items.WHITE_STAINED_GLASS);
        changeColor(c, Items.MAGENTA_STAINED_GLASS, Items.ORANGE_STAINED_GLASS);
        changeColor(c, Items.LIGHT_BLUE_STAINED_GLASS, Items.MAGENTA_STAINED_GLASS);
        changeColor(c, Items.YELLOW_STAINED_GLASS, Items.LIGHT_BLUE_STAINED_GLASS);
        changeColor(c, Items.LIME_STAINED_GLASS, Items.YELLOW_STAINED_GLASS);
        changeColor(c, Items.PINK_STAINED_GLASS, Items.LIME_STAINED_GLASS);
        changeColor(c, Items.GRAY_STAINED_GLASS, Items.PINK_STAINED_GLASS);
        changeColor(c, Items.LIGHT_GRAY_STAINED_GLASS, Items.GRAY_STAINED_GLASS);
        changeColor(c, Items.CYAN_STAINED_GLASS, Items.LIGHT_GRAY_STAINED_GLASS);
        changeColor(c, Items.PURPLE_STAINED_GLASS, Items.CYAN_STAINED_GLASS);
        changeColor(c, Items.BLUE_STAINED_GLASS, Items.PURPLE_STAINED_GLASS);
        changeColor(c, Items.BROWN_STAINED_GLASS, Items.BLUE_STAINED_GLASS);
        changeColor(c, Items.GREEN_STAINED_GLASS, Items.BROWN_STAINED_GLASS);
        changeColor(c, Items.RED_STAINED_GLASS, Items.GREEN_STAINED_GLASS);
        changeColor(c, Items.BLACK_STAINED_GLASS, Items.RED_STAINED_GLASS);
    }

    private void buildStainedGlassPane(@Nonnull Consumer<IFinishedRecipe> c) {
        changeColor(c, Items.WHITE_STAINED_GLASS_PANE, Items.BLACK_STAINED_GLASS_PANE);
        changeColor(c, Items.ORANGE_STAINED_GLASS_PANE, Items.WHITE_STAINED_GLASS_PANE);
        changeColor(c, Items.MAGENTA_STAINED_GLASS_PANE, Items.ORANGE_STAINED_GLASS_PANE);
        changeColor(c, Items.LIGHT_BLUE_STAINED_GLASS_PANE, Items.MAGENTA_STAINED_GLASS_PANE);
        changeColor(c, Items.YELLOW_STAINED_GLASS_PANE, Items.LIGHT_BLUE_STAINED_GLASS_PANE);
        changeColor(c, Items.LIME_STAINED_GLASS_PANE, Items.YELLOW_STAINED_GLASS_PANE);
        changeColor(c, Items.PINK_STAINED_GLASS_PANE, Items.LIME_STAINED_GLASS_PANE);
        changeColor(c, Items.GRAY_STAINED_GLASS_PANE, Items.PINK_STAINED_GLASS_PANE);
        changeColor(c, Items.LIGHT_GRAY_STAINED_GLASS_PANE, Items.GRAY_STAINED_GLASS_PANE);
        changeColor(c, Items.CYAN_STAINED_GLASS_PANE, Items.LIGHT_GRAY_STAINED_GLASS_PANE);
        changeColor(c, Items.PURPLE_STAINED_GLASS_PANE, Items.CYAN_STAINED_GLASS_PANE);
        changeColor(c, Items.BLUE_STAINED_GLASS_PANE, Items.PURPLE_STAINED_GLASS_PANE);
        changeColor(c, Items.BROWN_STAINED_GLASS_PANE, Items.BLUE_STAINED_GLASS_PANE);
        changeColor(c, Items.GREEN_STAINED_GLASS_PANE, Items.BROWN_STAINED_GLASS_PANE);
        changeColor(c, Items.RED_STAINED_GLASS_PANE, Items.GREEN_STAINED_GLASS_PANE);
        changeColor(c, Items.BLACK_STAINED_GLASS_PANE, Items.RED_STAINED_GLASS_PANE);
    }

    private void buildTerracotta(@Nonnull Consumer<IFinishedRecipe> c) {
        changeColor(c, Items.WHITE_TERRACOTTA, Items.BLACK_TERRACOTTA);
        changeColor(c, Items.ORANGE_TERRACOTTA, Items.WHITE_TERRACOTTA);
        changeColor(c, Items.MAGENTA_TERRACOTTA, Items.ORANGE_TERRACOTTA);
        changeColor(c, Items.LIGHT_BLUE_TERRACOTTA, Items.MAGENTA_TERRACOTTA);
        changeColor(c, Items.YELLOW_TERRACOTTA, Items.LIGHT_BLUE_TERRACOTTA);
        changeColor(c, Items.LIME_TERRACOTTA, Items.YELLOW_TERRACOTTA);
        changeColor(c, Items.PINK_TERRACOTTA, Items.LIME_TERRACOTTA);
        changeColor(c, Items.GRAY_TERRACOTTA, Items.PINK_TERRACOTTA);
        changeColor(c, Items.LIGHT_GRAY_TERRACOTTA, Items.GRAY_TERRACOTTA);
        changeColor(c, Items.CYAN_TERRACOTTA, Items.LIGHT_GRAY_TERRACOTTA);
        changeColor(c, Items.PURPLE_TERRACOTTA, Items.CYAN_TERRACOTTA);
        changeColor(c, Items.BLUE_TERRACOTTA, Items.PURPLE_TERRACOTTA);
        changeColor(c, Items.BROWN_TERRACOTTA, Items.BLUE_TERRACOTTA);
        changeColor(c, Items.GREEN_TERRACOTTA, Items.BROWN_TERRACOTTA);
        changeColor(c, Items.RED_TERRACOTTA, Items.GREEN_TERRACOTTA);
        changeColor(c, Items.BLACK_TERRACOTTA, Items.RED_TERRACOTTA);
    }

    private void buildGlazedTerracotta(@Nonnull Consumer<IFinishedRecipe> c) {
        changeColor(c, Items.WHITE_GLAZED_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA);
        changeColor(c, Items.ORANGE_GLAZED_TERRACOTTA, Items.WHITE_GLAZED_TERRACOTTA);
        changeColor(c, Items.MAGENTA_GLAZED_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA);
        changeColor(c, Items.LIGHT_BLUE_GLAZED_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA);
        changeColor(c, Items.YELLOW_GLAZED_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA);
        changeColor(c, Items.LIME_GLAZED_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA);
        changeColor(c, Items.PINK_GLAZED_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA);
        changeColor(c, Items.GRAY_GLAZED_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA);
        changeColor(c, Items.LIGHT_GRAY_GLAZED_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA);
        changeColor(c, Items.CYAN_GLAZED_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA);
        changeColor(c, Items.PURPLE_GLAZED_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA);
        changeColor(c, Items.BLUE_GLAZED_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA);
        changeColor(c, Items.BROWN_GLAZED_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA);
        changeColor(c, Items.GREEN_GLAZED_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA);
        changeColor(c, Items.RED_GLAZED_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA);
        changeColor(c, Items.BLACK_GLAZED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA);
    }

    private void buildCarpet(@Nonnull Consumer<IFinishedRecipe> c) {
        changeColor(c, Items.WHITE_CARPET, Items.BLACK_CARPET);
        changeColor(c, Items.ORANGE_CARPET, Items.WHITE_CARPET);
        changeColor(c, Items.MAGENTA_CARPET, Items.ORANGE_CARPET);
        changeColor(c, Items.LIGHT_BLUE_CARPET, Items.MAGENTA_CARPET);
        changeColor(c, Items.YELLOW_CARPET, Items.LIGHT_BLUE_CARPET);
        changeColor(c, Items.LIME_CARPET, Items.YELLOW_CARPET);
        changeColor(c, Items.PINK_CARPET, Items.LIME_CARPET);
        changeColor(c, Items.GRAY_CARPET, Items.PINK_CARPET);
        changeColor(c, Items.LIGHT_GRAY_CARPET, Items.GRAY_CARPET);
        changeColor(c, Items.CYAN_CARPET, Items.LIGHT_GRAY_CARPET);
        changeColor(c, Items.PURPLE_CARPET, Items.CYAN_CARPET);
        changeColor(c, Items.BLUE_CARPET, Items.PURPLE_CARPET);
        changeColor(c, Items.BROWN_CARPET, Items.BLUE_CARPET);
        changeColor(c, Items.GREEN_CARPET, Items.BROWN_CARPET);
        changeColor(c, Items.RED_CARPET, Items.GREEN_CARPET);
        changeColor(c, Items.BLACK_CARPET, Items.RED_CARPET);
    }

    private void buildLamps(@Nonnull Consumer<IFinishedRecipe> c) {
        changeColor(c, ActuallyBlocks.LAMP_WHITE.getItem(), ActuallyBlocks.LAMP_BLACK.getItem());
        changeColor(c, ActuallyBlocks.LAMP_ORANGE.getItem(), ActuallyBlocks.LAMP_WHITE.getItem());
        changeColor(c, ActuallyBlocks.LAMP_MAGENTA.getItem(), ActuallyBlocks.LAMP_ORANGE.getItem());
        changeColor(c, ActuallyBlocks.LAMP_LIGHT_BLUE.getItem(), ActuallyBlocks.LAMP_MAGENTA.getItem());
        changeColor(c, ActuallyBlocks.LAMP_YELLOW.getItem(), ActuallyBlocks.LAMP_LIGHT_BLUE.getItem());
        changeColor(c, ActuallyBlocks.LAMP_LIME.getItem(), ActuallyBlocks.LAMP_YELLOW.getItem());
        changeColor(c, ActuallyBlocks.LAMP_PINK.getItem(), ActuallyBlocks.LAMP_LIME.getItem());
        changeColor(c, ActuallyBlocks.LAMP_GRAY.getItem(), ActuallyBlocks.LAMP_PINK.getItem());
        changeColor(c, ActuallyBlocks.LAMP_LIGHT_GRAY.getItem(), ActuallyBlocks.LAMP_GRAY.getItem());
        changeColor(c, ActuallyBlocks.LAMP_CYAN.getItem(), ActuallyBlocks.LAMP_LIGHT_GRAY.getItem());
        changeColor(c, ActuallyBlocks.LAMP_PURPLE.getItem(), ActuallyBlocks.LAMP_CYAN.getItem());
        changeColor(c, ActuallyBlocks.LAMP_BLUE.getItem(), ActuallyBlocks.LAMP_PURPLE.getItem());
        changeColor(c, ActuallyBlocks.LAMP_BROWN.getItem(), ActuallyBlocks.LAMP_BLUE.getItem());
        changeColor(c, ActuallyBlocks.LAMP_GREEN.getItem(), ActuallyBlocks.LAMP_BROWN.getItem());
        changeColor(c, ActuallyBlocks.LAMP_RED.getItem(), ActuallyBlocks.LAMP_GREEN.getItem());
        changeColor(c, ActuallyBlocks.LAMP_BLACK.getItem(), ActuallyBlocks.LAMP_RED.getItem());
    }


    private void changeColor(Consumer<IFinishedRecipe> consumer, IItemProvider output, Ingredient input) {
        consumer.accept(new ColorChangeRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "colorchange/" + output.asItem().getRegistryName().getPath()),
                input, output));
    }
    private void changeColor(Consumer<IFinishedRecipe> consumer, IItemProvider output, IItemProvider input) {
        consumer.accept(new ColorChangeRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "colorchange/" + output.asItem().getRegistryName().getPath()),
                Ingredient.of(input), output));
    }
    private void changeColor(Consumer<IFinishedRecipe> consumer, IItemProvider output, ItemStack input) {
        consumer.accept(new ColorChangeRecipe.FinishedRecipe(new ResourceLocation(ActuallyAdditions.MODID, "colorchange/" + output.asItem().getRegistryName().getPath()),
                Ingredient.of(input), output));
    }
}
