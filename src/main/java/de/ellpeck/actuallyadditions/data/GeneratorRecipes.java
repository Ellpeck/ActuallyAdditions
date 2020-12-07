package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

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



        // Hot stuff
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ActuallyBlocks.ORE_BLACK_QUARTZ.get()), ActuallyItems.BLACK_QUARTS.get(), 0.7F, 100).addCriterion("has_black_quartz_ore", hasItem(ActuallyBlocks.ORE_BLACK_QUARTZ.get())).build(consumer);
    }
}
