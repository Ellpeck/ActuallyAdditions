package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GeneratorRecipes extends RecipeProvider {
    public GeneratorRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ENDERPEARL.get())
                .key('e', Tags.Items.ENDER_PEARLS)
                .patternLine("ee ").patternLine("ee ")
                .addCriterion("has_enderpearl", hasItem(Tags.Items.ENDER_PEARLS)).build(consumer);

//        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ENDER_CASING.get())
//                .key('e', Tags.Items.ENDER_PEARLS).key('d', ActuallyItems.DIAMATINE)

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.BASIC_COIL.get())
                .key('r', ActuallyItems.RESTONIA_CRYSTAL.get()).key('b', ActuallyItems.BLACK_QUARTS.get())
                .patternLine(" r ").patternLine("rbr").patternLine(" r ")
                .addCriterion("has_restonia", hasItem(ActuallyItems.RESTONIA_CRYSTAL.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.ADVANCED_COIL.get())
                .key('g', Tags.Items.NUGGETS_GOLD).key('c', ActuallyItems.BASIC_COIL.get())
                .patternLine("ggg").patternLine("gcg").patternLine("ggg")
                .addCriterion("has_basic_coil", hasItem(ActuallyItems.BASIC_COIL.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.LEAF_BLOWER.get())
                .key('f', Items.FLINT).key('i', ActuallyItems.ENORI_CRYSTAL.get())
                .key('p', Items.PISTON).key('c', ActuallyItems.ADVANCED_COIL.get())
                .patternLine(" f ").patternLine("ip ").patternLine("ic ")
                .addCriterion("has_coil", hasItem(ActuallyItems.ADVANCED_COIL.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.ADVANCED_LEAF_BLOWER.get())
                .key('f', Items.FLINT).key('i', ActuallyItems.DIAMATINE_CRYSTAL.get())
                .key('p', Items.PISTON).key('c', ActuallyItems.ADVANCED_COIL.get())
                .patternLine(" f ").patternLine("ip ").patternLine("ic ")
                .addCriterion("has_diamatine", hasItem(ActuallyItems.DIAMATINE_CRYSTAL.get())).build(consumer);

        // Blocks of: Crystals & back again
        blockOfToItem(ActuallyBlocks.CRYSTAL_ENORI, ActuallyItems.ENORI_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_VOID, ActuallyItems.VOID_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_EMERADIC, ActuallyItems.EMERADIC_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_DIAMATINE, ActuallyItems.DIAMATINE_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_PALIS, ActuallyItems.PALIS_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_RESTONIA, ActuallyItems.RESTONIA_CRYSTAL, 9, consumer);

        blockOfToItem(ActuallyBlocks.CRYSTAL_EMPOWERED_ENORI, ActuallyItems.ENORI_EMPOWERED_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_EMPOWERED_VOID, ActuallyItems.VOID_EMPOWERED_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_EMPOWERED_EMERADIC, ActuallyItems.EMERADIC_EMPOWERED_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_EMPOWERED_DIAMATINE, ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_EMPOWERED_PALIS, ActuallyItems.PALIS_EMPOWERED_CRYSTAL, 9, consumer);
        blockOfToItem(ActuallyBlocks.CRYSTAL_EMPOWERED_RESTONIA, ActuallyItems.RESTONIA_EMPOWERED_CRYSTAL, 9, consumer);

        gridOfToResult(ActuallyItems.ENORI_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_ENORI.get(), true, consumer);
        gridOfToResult(ActuallyItems.VOID_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_VOID.get(), true, consumer);
        gridOfToResult(ActuallyItems.EMERADIC_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_EMERADIC.get(), true, consumer);
        gridOfToResult(ActuallyItems.DIAMATINE_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_DIAMATINE.get(), true, consumer);
        gridOfToResult(ActuallyItems.PALIS_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_PALIS.get(), true, consumer);
        gridOfToResult(ActuallyItems.RESTONIA_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_RESTONIA.get(), true, consumer);

        gridOfToResult(ActuallyItems.ENORI_EMPOWERED_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_EMPOWERED_ENORI.get(), true, consumer);
        gridOfToResult(ActuallyItems.VOID_EMPOWERED_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_EMPOWERED_VOID.get(), true, consumer);
        gridOfToResult(ActuallyItems.EMERADIC_EMPOWERED_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_EMPOWERED_EMERADIC.get(), true, consumer);
        gridOfToResult(ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_EMPOWERED_DIAMATINE.get(), true, consumer);
        gridOfToResult(ActuallyItems.PALIS_EMPOWERED_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_EMPOWERED_PALIS.get(), true, consumer);
        gridOfToResult(ActuallyItems.RESTONIA_EMPOWERED_CRYSTAL.get(), ActuallyBlocks.CRYSTAL_EMPOWERED_RESTONIA.get(), true, consumer);

        gridOfToResult(ActuallyItems.WHITE_CRYSTAL_SHARD.get(), ActuallyItems.ENORI_CRYSTAL.get(), true, consumer);
        gridOfToResult(ActuallyItems.BLACK_CRYSTAL_SHARD.get(), ActuallyItems.VOID_CRYSTAL.get(), true, consumer);
        gridOfToResult(ActuallyItems.GREEN_CRYSTAL_SHARD.get(), ActuallyItems.EMERADIC_CRYSTAL.get(), true, consumer);
        gridOfToResult(ActuallyItems.LIGHT_BLUE_CRYSTAL_SHARD.get(), ActuallyItems.DIAMATINE_CRYSTAL.get(), true, consumer);
        gridOfToResult(ActuallyItems.BLUE_CRYSTAL_SHARD.get(), ActuallyItems.PALIS_CRYSTAL.get(), true, consumer);
        gridOfToResult(ActuallyItems.RED_CRYSTAL_SHARD.get(), ActuallyItems.RESTONIA_CRYSTAL.get(), true, consumer);

        // Misc
        gridOfToResult(ActuallyItems.BLACK_QUARTS.get(), ActuallyBlocks.BLACK_QUARTZ.get(), false, consumer);

        lamp(ActuallyBlocks.LAMP_WHITE, Tags.Items.DYES_WHITE, consumer);
        lamp(ActuallyBlocks.LAMP_ORANGE, Tags.Items.DYES_ORANGE, consumer);
        lamp(ActuallyBlocks.LAMP_MAGENTA, Tags.Items.DYES_MAGENTA, consumer);
        lamp(ActuallyBlocks.LAMP_LIGHT_BLUE, Tags.Items.DYES_LIGHT_BLUE, consumer);
        lamp(ActuallyBlocks.LAMP_YELLOW, Tags.Items.DYES_YELLOW, consumer);
        lamp(ActuallyBlocks.LAMP_LIME, Tags.Items.DYES_LIME, consumer);
        lamp(ActuallyBlocks.LAMP_PINK, Tags.Items.DYES_PINK, consumer);
        lamp(ActuallyBlocks.LAMP_GRAY, Tags.Items.DYES_GRAY, consumer);
        lamp(ActuallyBlocks.LAMP_LIGHT_GRAY, Tags.Items.DYES_LIGHT_GRAY, consumer);
        lamp(ActuallyBlocks.LAMP_CYAN, Tags.Items.DYES_CYAN, consumer);
        lamp(ActuallyBlocks.LAMP_PURPLE, Tags.Items.DYES_PURPLE, consumer);
        lamp(ActuallyBlocks.LAMP_BLUE, Tags.Items.DYES_BLUE, consumer);
        lamp(ActuallyBlocks.LAMP_BROWN, Tags.Items.DYES_BROWN, consumer);
        lamp(ActuallyBlocks.LAMP_GREEN, Tags.Items.DYES_GREEN, consumer);
        lamp(ActuallyBlocks.LAMP_RED, Tags.Items.DYES_RED, consumer);
        lamp(ActuallyBlocks.LAMP_BLACK, Tags.Items.DYES_BLACK, consumer);

        // Hot stuff
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ActuallyBlocks.ORE_BLACK_QUARTZ.get()), ActuallyItems.BLACK_QUARTS.get(), 0.7F, 100).addCriterion("has_black_quartz_ore", hasItem(ActuallyBlocks.ORE_BLACK_QUARTZ.get())).build(consumer);
    }

    private void blockOfToItem(Supplier<Block> blockOf, Supplier<Item> result, int count, Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapelessRecipe(result.get(), count)
                .addIngredient(blockOf.get())
                .addCriterion("has_"+ Objects.requireNonNull(blockOf.get().getRegistryName()).getPath(), hasItem(blockOf.get()))
                .build(consumer, Objects.requireNonNull(result.get().getRegistryName()).toString() + "_block_of");
    }

    private void gridOfToResult(IItemProvider itemsTo, IItemProvider result, boolean isFull, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('i', itemsTo)
                .patternLine(isFull ? "iii" : "ii ")
                .patternLine(isFull ? "iii" : "ii ")
                .patternLine(isFull ? "iii" : "   ")
                .addCriterion("has_"+ Objects.requireNonNull(itemsTo.asItem().getRegistryName()).getPath(), hasItem(itemsTo))
                .build(consumer, Objects.requireNonNull(result.asItem().getRegistryName()).toString() + "_grid_of");
    }

    private void lamp(Supplier<Block> result, ITag<Item> color, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(result.get())
                .key('g', Blocks.GLOWSTONE).key('b', color)
                .key('c', ActuallyItems.PALIS_CRYSTAL.get()).key('q', ActuallyItems.BLACK_QUARTS.get())
                .patternLine("gcg")
                .patternLine("bqb")
                .patternLine("gcg")
                .addCriterion("has_palis", hasItem(ActuallyItems.PALIS_CRYSTAL.get()))
                .addCriterion("has_color_item", hasItem(color))
                .build(consumer);
    }
}
