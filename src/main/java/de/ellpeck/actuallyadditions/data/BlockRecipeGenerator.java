package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
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
        Recipe.shapeless(ActuallyBlocks.BATTERY_BOX.getItem()).ingredients(ActuallyBlocks.ENERGIZER.get(), ActuallyBlocks.ENERVATOR.get(), ActuallyItems.COIL.get()).save(consumer);

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
                .define('B', ActuallyItems.BATTERY_DOUBLE.get())
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
                .define('C', ActuallyItems.COIL_ADVANCED.get())
                .save(consumer);

        //Display Stand
        Recipe.shaped(ActuallyBlocks.DISPLAY_STAND.getItem())
                .pattern(" R ", "EEE", "GGG")
                .define('R', ActuallyItems.COIL_ADVANCED.get())
                .define('E', ActuallyBlocks.ETHETIC_GREEN_BLOCK.get())
                .define('G', ActuallyBlocks.ETHETIC_WHITE_BLOCK.get())
                .save(consumer);

        //Vertical Digger
        Recipe.shaped(ActuallyBlocks.VERTICAL_DIGGER.getItem())
                .pattern("IRI", "RCR", "IDI")
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('I', ActuallyBlocks.IRON_CASING.get())
                .define('C', ActuallyItems.EMPOWERED_VOID_CRYSTAL.get())
                .define('D', ActuallyItems.DRILL.get())
                .save(consumer);

        //Black Quartz Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_WALL.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Black Quartz Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_SLAB.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_STAIR.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Pillar Black Quartz Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Pillar Black Quartz Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_PILLAR_SLAB.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Pillar Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_PILLAR_STAIR.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR_BLOCK.get(), consumer);

        //Chiseled Black Quartz Wall
        Recipe.wall(ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get(), consumer);

        //Chiseled Black Quartz Slab
        Recipe.slab(ActuallyBlocks.CHISELED_BLACK_QUARTZ_SLAB.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get(), consumer);

        //Chiseled Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.CHISELED_BLACK_QUARTZ_STAIR.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ_BLOCK.get(), consumer);

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
                .define('C', ActuallyItems.COIL_ADVANCED.get())
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
                .ingredients(ActuallyBlocks.LASER_RELAY_ITEM.get(), ActuallyItems.COIL_ADVANCED.get(), ActuallyItems.BLACK_QUARTZ.get())
                .save(consumer);

        // Item Interface
        Recipe.shaped(ActuallyBlocks.ITEM_INTERFACE.getItem())
                .pattern("OBO", "RCR", "OBO")
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('O', ActuallyItems.COIL.get())
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('C', Tags.Items.CHESTS_WOODEN)
                .save(consumer);

        // Hopping Item Interface
        Recipe.shapeless(ActuallyBlocks.ITEM_INTERFACE_HOPPING.get()).ingredients(ActuallyBlocks.ITEM_INTERFACE.get()).save(consumer);


    }

    @Override
    protected void saveAdvancement(DirectoryCache p_208310_1_, JsonObject p_208310_2_, Path p_208310_3_) {
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
        }
    }
}
