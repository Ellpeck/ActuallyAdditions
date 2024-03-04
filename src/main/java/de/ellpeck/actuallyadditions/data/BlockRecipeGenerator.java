package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class BlockRecipeGenerator extends RecipeProvider {
    public BlockRecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput consumer) {
        //Battery Box
        Recipe.shapeless(ActuallyBlocks.BATTERY_BOX.getItem()).ingredients(ActuallyBlocks.ENERGIZER.get(), ActuallyBlocks.ENERVATOR.get(), ActuallyItems.BASIC_COIL.get()).save(consumer);

        //Farmer
        Recipe.shaped(ActuallyBlocks.FARMER.getItem())
                .pattern("ISI", "SCS", "ISI")
                .define('I', ActuallyBlocks.ENORI_CRYSTAL.getItem())
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .define('S', Tags.Items.SEEDS)
                .save(consumer);

        //Empowerer
        Recipe.shaped(ActuallyBlocks.EMPOWERER.getItem())
                .pattern(" R ", " B ", "CDC")
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('B', ActuallyItems.DOUBLE_BATTERY.get())
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .define('D', ActuallyBlocks.DISPLAY_STAND.get())
                .save(consumer);

        //Tiny Torch
        Recipe.shaped(ActuallyBlocks.TINY_TORCH.getItem(), 2)
                .pattern("C", "S")
                .define('C', ActuallyTags.Items.TINY_COALS)
                .define('S', Tags.Items.RODS_WOODEN)
                .save(consumer);

        //Fireworks Box
        Recipe.shaped(ActuallyBlocks.FIREWORK_BOX.getItem())
                .pattern("GFG", "SAS", "CCC")
                .define('G', Tags.Items.GUNPOWDER)
                .define('S', Tags.Items.RODS_WOODEN)
                .define('A', ActuallyBlocks.IRON_CASING.get())
                .define('F', Items.FIREWORK_ROCKET)
                .define('C', ActuallyItems.ENORI_CRYSTAL.get())
                .save(consumer);

        //Shock Suppressor
        Recipe.shaped(ActuallyBlocks.SHOCK_SUPPRESSOR.getItem())
            .pattern("OAO", "ACA", "OAO")
            .define('A', ActuallyItems.EMPOWERED_VOID_CRYSTAL.get())
            .define('O', Tags.Items.OBSIDIAN)
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(consumer);

        //Display Stand
        Recipe.shaped(ActuallyBlocks.DISPLAY_STAND.getItem())
            .pattern(" R ", "EEE", "GGG")
            .define('R', ActuallyItems.ADVANCED_COIL.get())
            .define('E', ActuallyBlocks.ETHETIC_GREEN_BLOCK.get())
            .define('G', ActuallyBlocks.ETHETIC_WHITE_BLOCK.get())
            .save(consumer);

        //Vertical Digger
        Recipe.shaped(ActuallyBlocks.VERTICAL_DIGGER.getItem())
            .pattern("IRI", "RCR", "IDI")
            .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
            .define('I', ActuallyBlocks.IRON_CASING.get())
            .define('C', ActuallyItems.EMPOWERED_VOID_CRYSTAL.get())
            .define('D', ActuallyTags.Items.DRILLS)
            .save(consumer);

        //Black Quartz Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_WALL.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), consumer);

        //Black Quartz Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_SLAB.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), consumer);

        //Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_STAIR.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), consumer);

        //Smooth Black Quartz Wall
        Recipe.wall(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_WALL.getItem(), ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get(), consumer);

        //Smooth Black Quartz Slab
        Recipe.slab(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_SLAB.getItem(), ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get(), consumer);

        //Smooth Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_STAIR.getItem(), ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get(), consumer);

        //Black Quartz Brick Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_BRICK_WALL.getItem(), ActuallyBlocks.BLACK_QUARTZ_BRICK.get(), consumer);

        //Black Quartz Brick Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_BRICK_SLAB.getItem(), ActuallyBlocks.BLACK_QUARTZ_BRICK.get(), consumer);

        //Black Quartz Brick Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_BRICK_STAIR.getItem(), ActuallyBlocks.BLACK_QUARTZ_BRICK.get(), consumer);

        //Pillar Black Quartz Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), consumer);

        //Pillar Black Quartz Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_PILLAR_SLAB.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), consumer);

        //Pillar Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_PILLAR_STAIR.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), consumer);

        //Chiseled Black Quartz Wall
        Recipe.wall(ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ.get(), consumer);

        //Chiseled Black Quartz Slab
        Recipe.slab(ActuallyBlocks.CHISELED_BLACK_QUARTZ_SLAB.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ.get(), consumer);

        //Chiseled Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.CHISELED_BLACK_QUARTZ_STAIR.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ.get(), consumer);

        //Ethetic White Wall
        Recipe.wall(ActuallyBlocks.ETHETIC_WHITE_WALL.getItem(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), consumer);

        //Ethetic White Slab
        Recipe.slab(ActuallyBlocks.ETHETIC_WHITE_SLAB.getItem(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), consumer);

        //Ethetic White Stairs
        Recipe.stairs(ActuallyBlocks.ETHETIC_WHITE_STAIRS.getItem(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), consumer);

        // Ethetic Green Wall
        Recipe.wall(ActuallyBlocks.ETHETIC_GREEN_WALL.getItem(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), consumer);

        // Ethetic Green Slab
        Recipe.slab(ActuallyBlocks.ETHETIC_GREEN_SLAB.getItem(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), consumer);

        // Ethetic Green Stairs
        Recipe.stairs(ActuallyBlocks.ETHETIC_GREEN_STAIRS.getItem(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), consumer);

        // Atomic Reconstructor
        Recipe.shaped(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.getItem())
                .pattern("IRI", "RCR", "IRI")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .save(consumer);

        // Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY.getItem(), 4)
                .pattern("OBO", "RCR", "OBO")
                .define('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('O', Tags.Items.OBSIDIAN)
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('C', ActuallyItems.ADVANCED_COIL.get())
                .save(consumer);

        // Advanced Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY_ADVANCED.getItem())
                .pattern(" I ", "XRX", " I ")
                .define('I', ActuallyItems.ENORI_CRYSTAL.get())
                .define('R', ActuallyBlocks.LASER_RELAY.get())
                .define('X', ActuallyItems.RESTONIA_CRYSTAL.get())
                .save(consumer);

        // Extreme Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY_EXTREME.getItem())
                .pattern(" I ", "XRX", " I ")
                .define('I', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
                .define('R', ActuallyBlocks.LASER_RELAY_ADVANCED.get())
                .define('X', ActuallyItems.RESTONIA_CRYSTAL.get())
                .save(consumer);

        // Whitelist Item Laser Relay
        Recipe.shapeless(ActuallyBlocks.LASER_RELAY_ITEM_ADVANCED.getItem())
                .ingredients(ActuallyBlocks.LASER_RELAY_ITEM.get(), ActuallyItems.ADVANCED_COIL.get(), ActuallyItems.BLACK_QUARTZ.get())
                .save(consumer);

        // Item Interface
        Recipe.shaped(ActuallyBlocks.ITEM_INTERFACE.getItem())
                .pattern("OBO", "RCR", "OBO")
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('O', ActuallyItems.BASIC_COIL.get())
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('C', Tags.Items.CHESTS_WOODEN)
                .save(consumer);

        // Hopping Item Interface
        Recipe.shapeless(ActuallyBlocks.ITEM_INTERFACE_HOPPING.get()).ingredients(ActuallyBlocks.ITEM_INTERFACE.get()).save(consumer);

        //Wood Casing
        Recipe.shaped(ActuallyBlocks.WOOD_CASING.getItem())
            .pattern("WSW", "SRS", "WSW")
            .define('S', Tags.Items.RODS_WOODEN)
            .define('W', ItemTags.PLANKS)
            .define('R', ItemTags.LOGS)
            .save(consumer);

        //Iron Casing
        Recipe.shaped(ActuallyBlocks.IRON_CASING.getItem())
            .pattern("WSW", "SQS", "WSW")
            .define('Q', ActuallyItems.BLACK_QUARTZ.get())
            .define('W', Tags.Items.INGOTS_IRON)
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Ender Casing
        Recipe.shaped(ActuallyBlocks.ENDER_CASING.getItem())
            .pattern("WSW", "SRS", "WSW")
            .define('W', Tags.Items.ENDER_PEARLS)
            .define('R', ActuallyBlocks.BLACK_QUARTZ.getItem())
            .define('S', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
            .save(consumer);



    }

//    @Override //TODO: Flanks do your RecipeOutput wrapper thingy ;)
//    protected @Nullable CompletableFuture<?> saveAdvancement(CachedOutput stack, FinishedRecipe finishedRecipe, JsonObject advancementJson) {
//        return null;
//        //Nope... maybe later...
//    }

    public static class Recipe {
        public static Shapeless shapeless(ItemLike result) {
            return new Shapeless(result);
        }

        public static Shapeless shapeless(ItemLike result, int count) {
            return new Shapeless(result, count);
        }

        public static Shaped shaped(ItemLike result) {
            return new Shaped(result);
        }

        public static Shaped shaped(ItemLike result, int count) {
            return new Shaped(result, count);
        }

        public static void stairs(ItemLike result, ItemLike resource, RecipeOutput consumer) {
            Recipe.shaped(result).patternSingleKey('Q', resource, "Q  ", "QQ ", "QQQ").save(consumer);
        }

        public static void wall(ItemLike result, ItemLike resource, RecipeOutput consumer) {
            Recipe.shaped(result).patternSingleKey('Q', resource, "QQQ", "QQQ").save(consumer);
        }

        public static void slab(ItemLike result, ItemLike resource, RecipeOutput consumer) {
            Recipe.shaped(result).patternSingleKey('Q', resource, "QQQ").save(consumer);
        }

        private static class Shapeless extends ShapelessRecipeBuilder {
            public Shapeless(ItemLike result) {
                this(result, 1);
            }

            public Shapeless(ItemLike result, int countIn) {
                super(RecipeCategory.MISC, result, countIn);
            }

            public Shapeless ingredients(ItemLike... ingredients) {
                Arrays.asList(ingredients).forEach(this::requires);
                return this;
            }

            @Override
            public void save(RecipeOutput consumer) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumer);
            }
        }

        private static class Shaped extends ShapedRecipeBuilder {
            public Shaped(ItemLike resultIn) {
                this(resultIn, 1);
            }

            public Shaped(ItemLike resultIn, int countIn) {
                super(RecipeCategory.MISC, resultIn, countIn);
            }

            public Shaped pattern(String line1, String line2, String line3) {
                this.pattern(line1);
                this.pattern(line2);
                this.pattern(line3);
                return this;
            }

            public Shaped pattern(String line1, String line2) {
                this.pattern(line1);
                this.pattern(line2);
                return this;
            }

            public Shaped patternSingleKey(char key, ItemLike resource, String... lines) {
                this.define(key, resource);
                for (String line : lines) {
                    this.pattern(line);
                }

                return this;
            }

            @Override
            public void save(RecipeOutput consumerIn) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumerIn);
            }
        }
    }
}
