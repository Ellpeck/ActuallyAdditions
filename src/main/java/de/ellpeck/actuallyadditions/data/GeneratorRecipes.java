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

        // Castings
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ENDER_CASING.get())
                .key('e', Tags.Items.ENDER_PEARLS).key('d', ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get())
                .key('q', ActuallyBlocks.BLACK_QUARTZ.get())
                .patternLine("ede").patternLine("dqd").patternLine("ede")
                .addCriterion("has_enderpearl", hasItem(Tags.Items.ENDER_PEARLS))
                .addCriterion("has_empowered_diamatine", hasItem(ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get()))
                .build(consumer);

        // Batteries
        battery(ActuallyItems.SINGLE_BATTERY, ActuallyItems.RESTONIA_CRYSTAL, ActuallyItems.ENORI_CRYSTAL).build(consumer);
        battery(ActuallyItems.DOUBLE_BATTERY, ActuallyItems.SINGLE_BATTERY, ActuallyItems.ENORI_CRYSTAL).build(consumer);
        battery(ActuallyItems.TRIPLE_BATTERY, ActuallyItems.DOUBLE_BATTERY, ActuallyItems.ENORI_EMPOWERED_CRYSTAL).build(consumer);
        battery(ActuallyItems.QUADRUPLE_BATTERY, ActuallyItems.TRIPLE_BATTERY, ActuallyItems.ENORI_EMPOWERED_CRYSTAL).build(consumer);
        battery(ActuallyItems.QUINTUPLE_BATTERY, ActuallyItems.QUADRUPLE_BATTERY, ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.BASIC_COIL.get())
                .key('r', ActuallyItems.RESTONIA_CRYSTAL.get()).key('b', ActuallyItems.BLACK_QUARTZ.get())
                .patternLine(" r ").patternLine("rbr").patternLine(" r ")
                .addCriterion("has_restonia", hasItem(ActuallyItems.RESTONIA_CRYSTAL.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.ADVANCED_COIL.get())
                .key('g', Tags.Items.NUGGETS_GOLD).key('c', ActuallyItems.BASIC_COIL.get())
                .patternLine("ggg").patternLine("gcg").patternLine("ggg")
                .addCriterion("has_basic_coil", hasItem(ActuallyItems.BASIC_COIL.get())).build(consumer);

        // Functional Items
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

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.TELEPORT_STAFF.get())
                .key('a', ActuallyItems.DIAMATINE_CRYSTAL.get()).key('b', ActuallyBlocks.ENDERPEARL.get())
                .key('c', ActuallyBlocks.ENDER_CASING.get()).key('d', ActuallyItems.SINGLE_BATTERY.get())
                .patternLine(" ab").patternLine(" c ").patternLine("cd ")
                .addCriterion("has_battery", hasItem(ActuallyItems.SINGLE_BATTERY.get()))
                .build(consumer);

        // Drills
        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_CORE.get())
                .key('a', ActuallyBlocks.CRYSTAL_ENORI.get()).key('b', ActuallyItems.BASIC_COIL.get())
                .key('c', ActuallyItems.RESTONIA_CRYSTAL.get())
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_basic_coil", hasItem(ActuallyItems.BASIC_COIL.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_MAIN.get())
                .key('a', ActuallyItems.DIAMATINE_CRYSTAL.get()).key('b', ActuallyItems.ADVANCED_COIL.get())
                .key('c', ActuallyItems.DRILL_CORE.get()).key('d', ActuallyBlocks.CRYSTAL_ENORI.get())
                .patternLine("aaa").patternLine("bcb").patternLine("ddd")
                .addCriterion("has_core", hasItem(ActuallyItems.DRILL_CORE.get()))
                .build(consumer);

        drillColor(ActuallyItems.DRILL_BLACK, Tags.Items.DYES_BLACK).build(consumer);
        drillColor(ActuallyItems.DRILL_BLUE, Tags.Items.DYES_BLUE).build(consumer);
        drillColor(ActuallyItems.DRILL_BROWN, Tags.Items.DYES_BROWN).build(consumer);
        drillColor(ActuallyItems.DRILL_CYAN, Tags.Items.DYES_CYAN).build(consumer);
        drillColor(ActuallyItems.DRILL_GRAY, Tags.Items.DYES_GRAY).build(consumer);
        drillColor(ActuallyItems.DRILL_GREEN, Tags.Items.DYES_GREEN).build(consumer);
        drillColor(ActuallyItems.DRILL_LIGHT_GRAY, Tags.Items.DYES_LIGHT_GRAY).build(consumer);
        drillColor(ActuallyItems.DRILL_LIME, Tags.Items.DYES_LIME).build(consumer);
        drillColor(ActuallyItems.DRILL_MAGENTA, Tags.Items.DYES_MAGENTA).build(consumer);
        drillColor(ActuallyItems.DRILL_ORANGE, Tags.Items.DYES_ORANGE).build(consumer);
        drillColor(ActuallyItems.DRILL_PINK, Tags.Items.DYES_PINK).build(consumer);
        drillColor(ActuallyItems.DRILL_PURPLE, Tags.Items.DYES_PURPLE).build(consumer);
        drillColor(ActuallyItems.DRILL_RED, Tags.Items.DYES_RED).build(consumer);
        drillColor(ActuallyItems.DRILL_WHITE, Tags.Items.DYES_WHITE).build(consumer);
        drillColor(ActuallyItems.DRILL_YELLOW, Tags.Items.DYES_YELLOW).build(consumer);

        // Drill augments
        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_SPEED_AUGMENT_I.get())
                .key('a', ActuallyItems.ENORI_CRYSTAL.get()).key('b', Items.SUGAR)
                .key('c', ActuallyItems.RESTONIA_CRYSTAL.get())
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_restonia", hasItem(ActuallyItems.RESTONIA_CRYSTAL.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_SPEED_AUGMENT_II.get())
                .key('a', ActuallyItems.ENORI_CRYSTAL.get()).key('b', Items.SUGAR)
                .key('c', Items.CAKE)
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_cake", hasItem(Items.CAKE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_SPEED_AUGMENT_III.get())
                .key('a', ActuallyItems.ENORI_EMPOWERED_CRYSTAL.get()).key('b', Items.SUGAR)
                .key('c', ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get())
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_empowered_diamatine", hasItem(ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_SILK_TOUCH_AUGMENT.get())
                .key('a', ActuallyItems.EMERADIC_EMPOWERED_CRYSTAL.get()).key('b', ActuallyItems.DIAMATINE_CRYSTAL.get())
                .key('c', ActuallyItems.ADVANCED_COIL.get())
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_empowered_emeradic", hasItem(ActuallyItems.EMERADIC_EMPOWERED_CRYSTAL.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_FORTUNE_AUGMENT_I.get())
                .key('a', Blocks.GLOWSTONE).key('b', Tags.Items.DUSTS_REDSTONE)
                .key('c', ActuallyBlocks.CRYSTAL_EMPOWERED_EMERADIC.get())
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_empowered_diamatine", hasItem(ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_FORTUNE_AUGMENT_II.get())
                .key('a', Blocks.GLOWSTONE).key('b', ActuallyItems.RESTONIA_CRYSTAL.get())
                .key('c', ActuallyBlocks.ENDER_CASING.get())
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_enercasing", hasItem(ActuallyBlocks.ENDER_CASING.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_MINING_AUGMENT_I.get())
                .key('a', ActuallyItems.DIAMATINE_CRYSTAL.get()).key('b', ActuallyItems.ENORI_CRYSTAL.get())
                .key('c', ActuallyItems.BASIC_COIL.get())
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_diamatine", hasItem(ActuallyItems.DIAMATINE_CRYSTAL.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_MINING_AUGMENT_II.get())
                .key('a', ActuallyItems.DIAMATINE_CRYSTAL.get()).key('b', ActuallyItems.ENORI_EMPOWERED_CRYSTAL.get())
                .key('c', ActuallyItems.ADVANCED_COIL.get())
                .patternLine("aba").patternLine("bcb").patternLine("aba")
                .addCriterion("has_empowered_enori", hasItem(ActuallyItems.ENORI_EMPOWERED_CRYSTAL.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ActuallyItems.DRILL_BLOCK_PLACING_AUGMENT.get())
                .key('a', Tags.Items.COBBLESTONE).key('b', Items.PAPER)
                .key('c', ActuallyItems.BASIC_COIL.get()).key('d', ActuallyItems.ENORI_CRYSTAL.get())
                .patternLine("aba").patternLine("dcd").patternLine("aba")
                .addCriterion("has_basic_coil", hasItem(ActuallyItems.BASIC_COIL.get()))
                .build(consumer);

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
        gridOfToResult(ActuallyItems.BLACK_QUARTZ.get(), ActuallyBlocks.BLACK_QUARTZ.get(), false, consumer);

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
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ActuallyBlocks.ORE_BLACK_QUARTZ.get()), ActuallyItems.BLACK_QUARTZ.get(), 0.7F, 100).addCriterion("has_black_quartz_ore", hasItem(ActuallyBlocks.ORE_BLACK_QUARTZ.get())).build(consumer);
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
                .key('c', ActuallyItems.PALIS_CRYSTAL.get()).key('q', ActuallyItems.BLACK_QUARTZ.get())
                .patternLine("gcg")
                .patternLine("bqb")
                .patternLine("gcg")
                .addCriterion("has_palis", hasItem(ActuallyItems.PALIS_CRYSTAL.get()))
                .addCriterion("has_color_item", hasItem(color))
                .build(consumer);
    }

    private ShapedRecipeBuilder battery(Supplier<Item> result, Supplier<Item> special, Supplier<Item> base) {
        return ShapedRecipeBuilder.shapedRecipe(result.get())
                .key('c', special.get()).key('a', ActuallyItems.ADVANCED_COIL.get())
                .key('b', base.get())
                .patternLine(" c ").patternLine("bab").patternLine("bbb")
                .addCriterion("has_base", hasItem(base.get()))
                .addCriterion("has_coil", hasItem(ActuallyItems.ADVANCED_COIL.get()));
    }

    private ShapelessRecipeBuilder drillColor(Supplier<Item> result, ITag<Item> color) {
        return ShapelessRecipeBuilder.shapelessRecipe(result.get())
                .addIngredient(ActuallyItems.DRILL_MAIN.get())
                .addIngredient(color)
                .addCriterion("has_drill", hasItem(ActuallyItems.DRILL_MAIN.get()));
    }
}
