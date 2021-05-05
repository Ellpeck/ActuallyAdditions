package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

public class BlockRecipeGenerator extends RecipeProvider {
    public BlockRecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        //Battery Box
        Recipe.shapeless(ActuallyBlocks.BATTERY_BOX.get()).ingredients(ActuallyBlocks.ENERGIZER.get(), ActuallyBlocks.ENERVATOR.get(), ActuallyItems.COIL.get()).build(consumer);

        //Farmer
        Recipe.shaped(ActuallyBlocks.FARMER.get())
            .pattern("ISI", "SCS", "ISI")
            .key('I', ActuallyBlocks.CRYSTAL_ENORI.get())
            .key('C', ActuallyBlocks.IRON_CASING.get())
            .key('S', Tags.Items.SEEDS)
            .build(consumer);

       //Empowerer
       ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.EMPOWERER.get())
               .patternLine(" R ")
               .patternLine(" B ")
               .patternLine("CDC")
               .key('R', ActuallyItems.RESTONIA_CRYSTAL.get())
               .key('B', ActuallyItems.BATTERY_DOUBLE.get())
               .key('C', ActuallyBlocks.IRON_CASING.get())
               .key('D', ActuallyBlocks.DISPLAY_STAND.get())
               .addCriterion("", hasItem(Items.AIR))
               .build(consumer);

       //Tiny Torch
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.TINY_TORCH.get(), 2)
                .patternLine("C")
                .patternLine("S")
                .key('C', ActuallyTags.Items.TINY_COALS)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer, new ResourceLocation(ActuallyAdditions.MODID, "tiny_torch"));

        //Fireworks Box
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.FIREWORK_BOX.get())
                .patternLine("GFG")
                .patternLine("SAS")
                .patternLine("CCC")
                .key('G', Tags.Items.GUNPOWDER)
                .key('S', Tags.Items.RODS_WOODEN)
                .key('A', ActuallyBlocks.IRON_CASING.get())
                .key('F', Items.FIREWORK_ROCKET)
                .key('C', ActuallyItems.ENORI_CRYSTAL.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Shock Suppressor
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.SHOCK_SUPPRESSOR.get())
                .patternLine("OAO")
                .patternLine("ACA")
                .patternLine("OAO")
                .key('A', ActuallyItems.VOID_EMPOWERED_CRYSTAL.get())
                .key('O', Tags.Items.OBSIDIAN)
                .key('C', ActuallyItems.COIL_ADVANCED.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Display Stand
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.DISPLAY_STAND.get())
                .patternLine(" R ")
                .patternLine("EEE")
                .patternLine("GGG")
                .key('R', ActuallyItems.COIL_ADVANCED.get())
                .key('E', ActuallyBlocks.ETHETIC_GREEN_BLOCK.get())
                .key('G', ActuallyBlocks.ETHETIC_WHITE_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Vertical Digger
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.MINER.get())
                .patternLine("IRI")
                .patternLine("RCR")
                .patternLine("IDI")
                .key('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .key('I', ActuallyBlocks.IRON_CASING.get())
                .key('C', ActuallyItems.VOID_EMPOWERED_CRYSTAL.get())
                .key('D', ActuallyItems.DRILL.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Black Quartz Wall
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.BLACK_QUARTZ_WALL.get())
                .patternLine("QQQ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Black Quartz Slab
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.BLACK_QUARTZ_SLAB.get())
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Black Quartz Stairs
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.BLACK_QUARTZ_STAIR.get())
                .patternLine("Q  ")
                .patternLine("QQ ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get())
                .build(consumer);

        //Pillar Black Quartz Wall
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.get())
                .patternLine("QQQ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Pillar Black Quartz Slab
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.BLACK_QUARTZ_PILLAR_SLAB.get())
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Pillar Black Quartz Stairs
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.BLACK_QUARTZ_PILLAR_STAIR.get())
                .patternLine("Q  ")
                .patternLine("QQ ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get())
                .build(consumer);

        //Chiseled Black Quartz Wall
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.get())
                .patternLine("QQQ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Chiseled Black Quartz Slab
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.CHISELED_BLACK_QUARTZ_SLAB.get())
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Chiseled Black Quartz Stairs
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.CHISELED_BLACK_QUARTZ_STAIR.get())
                .patternLine("Q  ")
                .patternLine("QQ ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get())
                .build(consumer);

        //Ethetic White Wall
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ETHETIC_WHITE_WALL.get())
                .patternLine("QQQ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.ETHETIC_WHITE_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Ethetic White Slab
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ETHETIC_WHITE_SLAB.get())
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.ETHETIC_WHITE_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Ethetic White Stairs
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ETHETIC_WHITE_STAIRS.get())
                .patternLine("Q  ")
                .patternLine("QQ ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.ETHETIC_WHITE_BLOCK.get())
                .build(consumer);

        //Ethetic Green Wall
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ETHETIC_GREEN_WALL.get())
                .patternLine("QQQ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.ETHETIC_GREEN_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Ethetic Green Slab
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ETHETIC_GREEN_SLAB.get())
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.ETHETIC_GREEN_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Ethetic Green Stairs
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ETHETIC_GREEN_STAIRS.get())
                .patternLine("Q  ")
                .patternLine("QQ ")
                .patternLine("QQQ")
                .key('Q', ActuallyBlocks.ETHETIC_GREEN_BLOCK.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);

        //Atomic Reconstructor
        ShapedRecipeBuilder.shapedRecipe(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get())
                .patternLine("IRI")
                .patternLine("RCR")
                .patternLine("IRI")
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('C', ActuallyBlocks.IRON_CASING.get())
                .addCriterion("", hasItem(Items.AIR))
                .build(consumer);
    }

    @Override
    protected void saveRecipeAdvancement(DirectoryCache cache, JsonObject cache2, Path advancementJson) {
        //Nope...
    }

    public static class Recipe {
        public static Shapeless shapeless(IItemProvider result) {
            return new Shapeless(result);
        }

        public static Shaped shaped(IItemProvider result) {
            return new Shaped(result);
        }

        private static class Shapeless extends ShapelessRecipeBuilder {
            public Shapeless(IItemProvider result) {
                this(result, 1);
            }

            public Shapeless(IItemProvider result, int countIn) {
                super(result, countIn);
            }

            public Shapeless ingredients(IItemProvider... ingredients) {
                Arrays.asList(ingredients).forEach(this::addIngredient);
                return this;
            }

            @Override
            public void build(Consumer<IFinishedRecipe> consumer) {
                this.addCriterion("has_book", hasItem(ActuallyItems.ITEM_BOOKLET.get()));
                super.build(consumer);
            }

            @Override
            public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation location) {
                this.addCriterion("has_book", hasItem(ActuallyItems.ITEM_BOOKLET.get()));
                super.build(consumer, location);
            }
        }

        private static class Shaped extends ShapedRecipeBuilder {
            public Shaped(IItemProvider resultIn) {
                this(resultIn, 1);
            }

            public Shaped(IItemProvider resultIn, int countIn) {
                super(resultIn, countIn);
            }

            public Shaped pattern(String line1, String line2, String line3) {
                this.patternLine(line1);
                this.patternLine(line2);
                this.patternLine(line3);
                return this;
            }

            public Shaped pattern(String line1, String line2) {
                this.patternLine(line1);
                this.patternLine(line2);
                return this;
            }

            @Override
            public void build(Consumer<IFinishedRecipe> consumerIn) {
                this.addCriterion("has_book", hasItem(ActuallyItems.ITEM_BOOKLET.get()));
                super.build(consumerIn);
            }

            @Override
            public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
                this.addCriterion("has_book", hasItem(ActuallyItems.ITEM_BOOKLET.get()));
                super.build(consumerIn, id);
            }
        }
    }
}
