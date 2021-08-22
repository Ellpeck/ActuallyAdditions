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
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        //Battery Box
        Recipe.shapeless(ActuallyBlocks.BATTERY_BOX.get()).ingredients(ActuallyBlocks.ENERGIZER.get(), ActuallyBlocks.ENERVATOR.get(), ActuallyItems.COIL.get()).save(consumer);

        //Farmer
        Recipe.shaped(ActuallyBlocks.FARMER.get())
            .pattern("ISI", "SCS", "ISI")
            .key('I', ActuallyBlocks.ENORI_CRYSTAL.getItem())
            .key('C', ActuallyBlocks.IRON_CASING.get())
            .key('S', Tags.Items.SEEDS)
            .build(consumer);

        //Empowerer
        Recipe.shaped(ActuallyBlocks.EMPOWERER.get())
            .pattern(" R ", " B ", "CDC")
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('B', ActuallyItems.BATTERY_DOUBLE.get())
            .define('C', ActuallyBlocks.IRON_CASING.get())
            .define('D', ActuallyBlocks.DISPLAY_STAND.get())
            .save(consumer);

        //Tiny Torch
        Recipe.shaped(ActuallyBlocks.TINY_TORCH.get(), 2)
            .pattern("C", "S")
            .define('C', ActuallyTags.Items.TINY_COALS)
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer, new ResourceLocation(ActuallyAdditions.MODID, "tiny_torch"));

        //Fireworks Box
        Recipe.shaped(ActuallyBlocks.FIREWORK_BOX.get())
            .pattern("GFG", "SAS", "CCC")
            .define('G', Tags.Items.GUNPOWDER)
            .define('S', Tags.Items.RODS_WOODEN)
            .define('A', ActuallyBlocks.IRON_CASING.get())
            .define('F', Items.FIREWORK_ROCKET)
            .define('C', ActuallyItems.ENORI_CRYSTAL.get())
            .save(consumer);

        //Shock Suppressor
        Recipe.shaped(ActuallyBlocks.SHOCK_SUPPRESSOR.get())
            .pattern("OAO", "ACA", "OAO")
            .define('A', ActuallyItems.VOID_EMPOWERED_CRYSTAL.get())
            .define('O', Tags.Items.OBSIDIAN)
            .define('C', ActuallyItems.COIL_ADVANCED.get())
            .save(consumer);

        //Display Stand
        Recipe.shaped(ActuallyBlocks.DISPLAY_STAND.get())
            .pattern(" R ", "EEE", "GGG")
            .define('R', ActuallyItems.COIL_ADVANCED.get())
            .define('E', ActuallyBlocks.ETHETIC_GREEN_BLOCK.get())
            .define('G', ActuallyBlocks.ETHETIC_WHITE_BLOCK.get())
            .save(consumer);

        //Vertical Digger
        Recipe.shaped(ActuallyBlocks.Vertical_DIGGER.get())
            .pattern("IRI", "RCR", "IDI")
            .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
            .define('I', ActuallyBlocks.IRON_CASING.get())
            .define('C', ActuallyItems.VOID_EMPOWERED_CRYSTAL.get())
            .define('D', ActuallyItems.DRILL.get())
            .save(consumer);

        //Black Quartz Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_WALL.get(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Black Quartz Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_SLAB.get(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_STAIR.get(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Pillar Black Quartz Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.get(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Pillar Black Quartz Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_PILLAR_SLAB.get(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Pillar Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_PILLAR_STAIR.get(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Chiseled Black Quartz Wall
        Recipe.wall(ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.get(), ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get(), consumer);

        //Chiseled Black Quartz Slab
        Recipe.slab(ActuallyBlocks.CHISELED_BLACK_QUARTZ_SLAB.get(), ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get(), consumer);

        //Chiseled Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.CHISELED_BLACK_QUARTZ_STAIR.get(), ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get(), consumer);

        //Ethetic White Wall
        Recipe.wall(ActuallyBlocks.ETHETIC_WHITE_WALL.get(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), consumer);

        //Ethetic White Slab
        Recipe.slab(ActuallyBlocks.ETHETIC_WHITE_SLAB.get(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), consumer);

        //Ethetic White Stairs
        Recipe.stairs(ActuallyBlocks.ETHETIC_WHITE_STAIRS.get(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), consumer);

        // Ethetic Green Wall
        Recipe.wall(ActuallyBlocks.ETHETIC_GREEN_WALL.get(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), consumer);

        // Ethetic Green Slab
        Recipe.slab(ActuallyBlocks.ETHETIC_GREEN_SLAB.get(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), consumer);

        // Ethetic Green Stairs
        Recipe.stairs(ActuallyBlocks.ETHETIC_GREEN_STAIRS.get(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), consumer);

        // Atomic Reconstructor
        Recipe.shaped(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get())
            .pattern("IRI", "RCR", "IRI")
            .key('R', Tags.Items.DUSTS_REDSTONE)
            .key('I', Tags.Items.INGOTS_IRON)
            .key('C', ActuallyBlocks.IRON_CASING.get())
            .build(consumer);

        // Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY.get(), 4)
            .pattern("OBO","RCR","OBO")
            .key('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
            .key('O', Tags.Items.OBSIDIAN)
            .key('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .key('C', ActuallyItems.COIL_ADVANCED.get())
            .build(consumer);

        // Advanced Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY_ADVANCED.get())
            .pattern(" I ", "XRX", " I ")
            .key('I', ActuallyItems.ENORI_CRYSTAL.get())
            .key('R', ActuallyBlocks.LASER_RELAY.get())
            .key('X', ActuallyItems.RESTONIA_CRYSTAL.get())
            .build(consumer);

        // Extreme Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY_EXTREME.get())
            .pattern(" I ", "XRX", " I ")
            .key('I', ActuallyItems.DIAMATINE_EMPOWERED_CRYSTAL.get())
            .key('R', ActuallyBlocks.LASER_RELAY_ADVANCED.get())
            .key('X', ActuallyItems.RESTONIA_CRYSTAL.get())
            .build(consumer);

        // Whitelist Item Laser Relay
        Recipe.shapeless(ActuallyBlocks.LASER_RELAY_ITEM_ADVANCED.get())
            .ingredients(ActuallyBlocks.LASER_RELAY_ITEM.get(), ActuallyItems.COIL_ADVANCED.get(), ActuallyItems.BLACK_QUARTZ.get())
            .build(consumer);

        // Item Interface
        Recipe.shaped(ActuallyBlocks.ITEM_VIEWER.get())
            .pattern("OBO", "RCR", "OBO")
            .key('B', Tags.Items.DUSTS_REDSTONE)
            .key('O', ActuallyItems.COIL.get())
            .key('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .key('C', Tags.Items.CHESTS_WOODEN)
            .build(consumer);

        // Hopping Item Interface
        Recipe.shapeless(ActuallyBlocks.ITEM_VIEWER_HOPPING.get()).ingredients(ActuallyBlocks.ITEM_VIEWER.get()).build(consumer);


    }

    @Override
    protected void saveRecipeAdvancement(DirectoryCache cache, JsonObject cache2, Path advancementJson) {
        //Nope... maybe later...
    }

    public static class Recipe {
        public static Shapeless shapeless(IItemProvider result) {
            return new Shapeless(result);
        }

        public static Shapeless shapeless(IItemProvider result, int count) {
            return new Shapeless(result, count);
        }

        public static Shaped shaped(IItemProvider result) {
            return new Shaped(result);
        }

        public static Shaped shaped(IItemProvider result, int count) {
            return new Shaped(result, count);
        }

        public static void stairs(IItemProvider result, IItemProvider resource, Consumer<IFinishedRecipe> consumer) {
            Recipe.shaped(result).patternSingleKey('Q', resource, "Q  ", "QQ ", "QQQ").save(consumer);
        }

        public static void wall(IItemProvider result, IItemProvider resource, Consumer<IFinishedRecipe> consumer) {
            Recipe.shaped(result).patternSingleKey('Q', resource, "QQQ", "QQQ").save(consumer);
        }

        public static void slab(IItemProvider result, IItemProvider resource, Consumer<IFinishedRecipe> consumer) {
            Recipe.shaped(result).patternSingleKey('Q', resource, "QQQ").save(consumer);
        }

        private static class Shapeless extends ShapelessRecipeBuilder {
            public Shapeless(IItemProvider result) {
                this(result, 1);
            }

            public Shapeless(IItemProvider result, int countIn) {
                super(result, countIn);
            }

            public Shapeless ingredients(IItemProvider... ingredients) {
                Arrays.asList(ingredients).forEach(this::requires);
                return this;
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumer) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumer);
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation location) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumer, location);
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

            public Shaped patternSingleKey(char key, IItemProvider resource, String... lines) {
                this.define(key, resource);
                for (String line : lines) {
                    this.pattern(line);
                }

                return this;
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumerIn) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumerIn);
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumerIn, id);
            }
        }
    }
}
