package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.ColorChangeRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
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
        //Wool
        changeColor(consumer, Items.WHITE_WOOL, Items.BLACK_WOOL);
        changeColor(consumer, Items.ORANGE_WOOL, Items.WHITE_WOOL);
        changeColor(consumer, Items.MAGENTA_WOOL, Items.ORANGE_WOOL);
        changeColor(consumer, Items.LIGHT_BLUE_WOOL, Items.MAGENTA_WOOL);
        changeColor(consumer, Items.YELLOW_WOOL, Items.LIGHT_BLUE_WOOL);
        changeColor(consumer, Items.LIME_WOOL, Items.YELLOW_WOOL);
        changeColor(consumer, Items.PINK_WOOL, Items.LIME_WOOL);
        changeColor(consumer, Items.GRAY_WOOL, Items.PINK_WOOL);
        changeColor(consumer, Items.LIGHT_GRAY_WOOL, Items.GRAY_WOOL);
        changeColor(consumer, Items.CYAN_WOOL, Items.LIGHT_GRAY_WOOL);
        changeColor(consumer, Items.PURPLE_WOOL, Items.CYAN_WOOL);
        changeColor(consumer, Items.BLUE_WOOL, Items.PURPLE_WOOL);
        changeColor(consumer, Items.BROWN_WOOL, Items.BLUE_WOOL);
        changeColor(consumer, Items.GREEN_WOOL, Items.BROWN_WOOL);
        changeColor(consumer, Items.RED_WOOL, Items.GREEN_WOOL);
        changeColor(consumer, Items.BLACK_WOOL, Items.RED_WOOL);
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
