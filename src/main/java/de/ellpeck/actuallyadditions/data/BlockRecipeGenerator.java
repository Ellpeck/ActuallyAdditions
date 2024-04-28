package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
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
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        var recipeOutput = new NoAdvRecipeOutput(output);

        //Battery Box
        Recipe.shapeless(ActuallyBlocks.BATTERY_BOX.getItem()).ingredients(ActuallyBlocks.ENERGIZER.get(), ActuallyBlocks.ENERVATOR.get(), ActuallyItems.BASIC_COIL.get()).save(recipeOutput);

        //Farmer
        Recipe.shaped(ActuallyBlocks.FARMER.getItem())
                .pattern("ISI", "SCS", "ISI")
                .define('I', ActuallyBlocks.ENORI_CRYSTAL.getItem())
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .define('S', Tags.Items.SEEDS)
                .save(recipeOutput);

        //Empowerer
        Recipe.shaped(ActuallyBlocks.EMPOWERER.getItem())
                .pattern(" R ", " B ", "CDC")
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('B', ActuallyItems.DOUBLE_BATTERY.get())
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .define('D', ActuallyBlocks.DISPLAY_STAND.get())
                .save(recipeOutput);

        //Tiny Torch
        Recipe.shaped(ActuallyBlocks.TINY_TORCH.getItem(), 2)
                .pattern("C", "S")
                .define('C', ActuallyTags.Items.TINY_COALS)
                .define('S', Tags.Items.RODS_WOODEN)
                .save(recipeOutput);

        //Fireworks Box
        Recipe.shaped(ActuallyBlocks.FIREWORK_BOX.getItem())
                .pattern("GFG", "SAS", "CCC")
                .define('G', Tags.Items.GUNPOWDER)
                .define('S', Tags.Items.RODS_WOODEN)
                .define('A', ActuallyBlocks.IRON_CASING.get())
                .define('F', Items.FIREWORK_ROCKET)
                .define('C', ActuallyItems.ENORI_CRYSTAL.get())
                .save(recipeOutput);

        //Shock Suppressor
        Recipe.shaped(ActuallyBlocks.SHOCK_SUPPRESSOR.getItem())
            .pattern("OAO", "ACA", "OAO")
            .define('A', ActuallyItems.EMPOWERED_VOID_CRYSTAL.get())
            .define('O', Tags.Items.OBSIDIAN)
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(recipeOutput);

        //Display Stand
        Recipe.shaped(ActuallyBlocks.DISPLAY_STAND.getItem())
            .pattern(" R ", "EEE", "GGG")
            .define('R', ActuallyItems.ADVANCED_COIL.get())
            .define('E', ActuallyBlocks.ETHETIC_GREEN_BLOCK.get())
            .define('G', ActuallyBlocks.ETHETIC_WHITE_BLOCK.get())
            .save(recipeOutput);

        //Vertical Digger
        Recipe.shaped(ActuallyBlocks.VERTICAL_DIGGER.getItem())
            .pattern("IRI", "RCR", "IDI")
            .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
            .define('I', ActuallyBlocks.IRON_CASING.get())
            .define('C', ActuallyItems.EMPOWERED_VOID_CRYSTAL.get())
            .define('D', ActuallyTags.Items.DRILLS)
            .save(recipeOutput);

        // Player Interface
        Recipe.shaped(ActuallyBlocks.PLAYER_INTERFACE.getItem())
            .pattern("CWC", "ECE", "CAC")
            .define('C', ActuallyBlocks.ENDER_CASING.getItem())
            .define('W', Items.WITHER_SKELETON_SKULL)
            .define('E', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL)
            .define('A', ActuallyItems.ADVANCED_COIL)
            .save(recipeOutput);

        //Black Quartz Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_WALL.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), recipeOutput);

        //Black Quartz Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_SLAB.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), recipeOutput);

        //Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_STAIR.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), recipeOutput);

        //Smooth Black Quartz Wall
        Recipe.wall(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_WALL.getItem(), ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get(), recipeOutput);

        //Smooth Black Quartz Slab
        Recipe.slab(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_SLAB.getItem(), ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get(), recipeOutput);

        //Smooth Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.SMOOTH_BLACK_QUARTZ_STAIR.getItem(), ActuallyBlocks.SMOOTH_BLACK_QUARTZ.get(), recipeOutput);

        //Black Quartz Brick Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_BRICK_WALL.getItem(), ActuallyBlocks.BLACK_QUARTZ_BRICK.get(), recipeOutput);

        //Black Quartz Brick Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_BRICK_SLAB.getItem(), ActuallyBlocks.BLACK_QUARTZ_BRICK.get(), recipeOutput);

        //Black Quartz Brick Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_BRICK_STAIR.getItem(), ActuallyBlocks.BLACK_QUARTZ_BRICK.get(), recipeOutput);

        //Pillar Black Quartz Wall
        Recipe.wall(ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), recipeOutput);

        //Pillar Black Quartz Slab
        Recipe.slab(ActuallyBlocks.BLACK_QUARTZ_PILLAR_SLAB.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), recipeOutput);

        //Pillar Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.BLACK_QUARTZ_PILLAR_STAIR.getItem(), ActuallyBlocks.BLACK_QUARTZ_PILLAR.get(), recipeOutput);

        //Chiseled Black Quartz Wall
        Recipe.wall(ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ.get(), recipeOutput);

        //Chiseled Black Quartz Slab
        Recipe.slab(ActuallyBlocks.CHISELED_BLACK_QUARTZ_SLAB.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ.get(), recipeOutput);

        //Chiseled Black Quartz Stairs
        Recipe.stairs(ActuallyBlocks.CHISELED_BLACK_QUARTZ_STAIR.getItem(), ActuallyBlocks.CHISELED_BLACK_QUARTZ.get(), recipeOutput);

        //Ethetic White Wall
        Recipe.wall(ActuallyBlocks.ETHETIC_WHITE_WALL.getItem(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), recipeOutput);

        //Ethetic White Slab
        Recipe.slab(ActuallyBlocks.ETHETIC_WHITE_SLAB.getItem(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), recipeOutput);

        //Ethetic White Stairs
        Recipe.stairs(ActuallyBlocks.ETHETIC_WHITE_STAIRS.getItem(), ActuallyBlocks.ETHETIC_WHITE_BLOCK.get(), recipeOutput);

        // Ethetic Green Wall
        Recipe.wall(ActuallyBlocks.ETHETIC_GREEN_WALL.getItem(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), recipeOutput);

        // Ethetic Green Slab
        Recipe.slab(ActuallyBlocks.ETHETIC_GREEN_SLAB.getItem(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), recipeOutput);

        // Ethetic Green Stairs
        Recipe.stairs(ActuallyBlocks.ETHETIC_GREEN_STAIRS.getItem(), ActuallyBlocks.ETHETIC_GREEN_BLOCK.get(), recipeOutput);

        // Atomic Reconstructor
        Recipe.shaped(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.getItem())
                .pattern("IRI", "RCR", "IRI")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .save(recipeOutput);

        // Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY.getItem(), 4)
                .pattern("OBO", "RCR", "OBO")
                .define('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('O', Tags.Items.OBSIDIAN)
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('C', ActuallyItems.ADVANCED_COIL.get())
                .save(recipeOutput);

        // Advanced Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY_ADVANCED.getItem())
                .pattern(" I ", "XRX", " I ")
                .define('I', ActuallyItems.ENORI_CRYSTAL.get())
                .define('R', ActuallyBlocks.LASER_RELAY.get())
                .define('X', ActuallyItems.RESTONIA_CRYSTAL.get())
                .save(recipeOutput);

        // Extreme Laser Relay
        Recipe.shaped(ActuallyBlocks.LASER_RELAY_EXTREME.getItem())
                .pattern(" I ", "XRX", " I ")
                .define('I', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
                .define('R', ActuallyBlocks.LASER_RELAY_ADVANCED.get())
                .define('X', ActuallyItems.RESTONIA_CRYSTAL.get())
                .save(recipeOutput);

        // Whitelist Item Laser Relay
        Recipe.shapeless(ActuallyBlocks.LASER_RELAY_ITEM_ADVANCED.getItem())
                .ingredients(ActuallyBlocks.LASER_RELAY_ITEM.get(), ActuallyItems.ADVANCED_COIL.get(), ActuallyItems.BLACK_QUARTZ.get())
                .save(recipeOutput);

        // Item Interface
        Recipe.shaped(ActuallyBlocks.ITEM_INTERFACE.getItem())
                .pattern("OBO", "RCR", "OBO")
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('O', ActuallyItems.BASIC_COIL.get())
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('C', Tags.Items.CHESTS_WOODEN)
                .save(recipeOutput);

        // Hopping Item Interface
        Recipe.shapeless(ActuallyBlocks.ITEM_INTERFACE_HOPPING.get()).ingredients(ActuallyBlocks.ITEM_INTERFACE.get()).save(recipeOutput);

        //Wood Casing
        Recipe.shaped(ActuallyBlocks.WOOD_CASING.getItem())
            .pattern("WSW", "SRS", "WSW")
            .define('S', Tags.Items.RODS_WOODEN)
            .define('W', ItemTags.PLANKS)
            .define('R', ItemTags.LOGS)
            .save(recipeOutput);

        //Iron Casing
        Recipe.shaped(ActuallyBlocks.IRON_CASING.getItem())
            .pattern("WSW", "SQS", "WSW")
            .define('Q', ActuallyItems.BLACK_QUARTZ.get())
            .define('W', Tags.Items.INGOTS_IRON)
            .define('S', Tags.Items.RODS_WOODEN)
            .save(recipeOutput);

        //Ender Casing
        Recipe.shaped(ActuallyBlocks.ENDER_CASING.getItem())
            .pattern("WSW", "SRS", "WSW")
            .define('W', Tags.Items.ENDER_PEARLS)
            .define('R', ActuallyBlocks.BLACK_QUARTZ.getItem())
            .define('S', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
            .save(recipeOutput);

        //Coffee Maker
        Recipe.shaped(ActuallyBlocks.COFFEE_MACHINE.getItem())
            .pattern(" C ", " I ", "XVX")
            .define('C', ActuallyItems.COFFEE_BEANS)
            .define('I', ActuallyBlocks.IRON_CASING.getItem())
            .define('X', ActuallyItems.BASIC_COIL.get())
            .define('V', ActuallyItems.VOID_CRYSTAL.get())
            .save(recipeOutput);

        //Canola Press
        Recipe.shaped(ActuallyBlocks.CANOLA_PRESS.getItem())
            .pattern("CEC","CXC","CAC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('E', ActuallyItems.ENORI_CRYSTAL)
            .define('X', ActuallyItems.CANOLA)
            .define('A', ActuallyItems.ADVANCED_COIL)
            .save(recipeOutput);

        //Fermenting Barrel
        Recipe.shaped(ActuallyBlocks.FERMENTING_BARREL.getItem())
            .pattern("LAL","LCL","LWL")
            .define('L', ItemTags.LOGS)
            .define('A', ActuallyItems.ENORI_CRYSTAL)
            .define('C', ActuallyItems.CANOLA)
            .define('W', ActuallyBlocks.WOOD_CASING.getItem())
            .save(recipeOutput);

        //Oil Generator
        Recipe.shaped(ActuallyBlocks.OIL_GENERATOR.getItem())
            .pattern("CIC","CAC","CIC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('A', ActuallyItems.CANOLA)
            .define('I', ActuallyBlocks.IRON_CASING.getItem())
            .save(recipeOutput);

        //Coal generator.
        Recipe.shaped(ActuallyBlocks.COAL_GENERATOR.getItem())
            .pattern("CIC","CAC","CIC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('A', Items.COAL)
            .define('I', ActuallyBlocks.IRON_CASING.getItem())
            .save(recipeOutput);

        // Auto breaker.
        Recipe.shaped(ActuallyBlocks.BREAKER.getItem())
            .pattern("CCC","CXV","CCC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('V', ActuallyItems.VOID_CRYSTAL)
            .define('X', ActuallyItems.BASIC_COIL)
            .save(recipeOutput);

        // Auto placer.
        Recipe.shaped(ActuallyBlocks.PLACER.getItem())
            .pattern("CCC", "CXP", "CCC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('P', ActuallyItems.PALIS_CRYSTAL)
            .define('X', ActuallyItems.BASIC_COIL)
            .save(recipeOutput);

        // Dropper
        Recipe.shaped(ActuallyBlocks.DROPPER.getItem())
            .pattern("CPC", "CDA", "CPC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('P', ActuallyItems.PALIS_CRYSTAL)
            .define('D', Items.DROPPER)
            .define('A', ActuallyItems.ADVANCED_COIL)
            .save(recipeOutput);

        // Fluid Placer
        Recipe.shaped(ActuallyBlocks.FLUID_PLACER.getItem())
            .pattern("BPB")
            .define('B', Items.BUCKET)
            .define('P', ActuallyBlocks.PLACER.get())
            .save(recipeOutput);

        // Fluid Collector
        Recipe.shaped(ActuallyBlocks.FLUID_COLLECTOR.getItem())
            .pattern("BFB")
            .define('B', Items.BUCKET)
            .define('F', ActuallyBlocks.BREAKER.get())
            .save(recipeOutput);

        // Phantom Placer
        Recipe.shapeless(ActuallyBlocks.PHANTOM_PLACER.get())
                .ingredients(ActuallyBlocks.PLACER.get(), ActuallyBlocks.PHANTOM_ITEMFACE.get())
                .save(recipeOutput);

        // Phantom Breaker
        Recipe.shapeless(ActuallyBlocks.PHANTOM_BREAKER.get())
                .ingredients(ActuallyBlocks.BREAKER.get(), ActuallyBlocks.PHANTOM_ITEMFACE.get())
                .save(recipeOutput);

        // Powered furnace.
        Recipe.shaped(ActuallyBlocks.POWERED_FURNACE.getItem())
                .pattern("EXC", "FIF", "CXE")
                .define('E', ActuallyItems.ENORI_CRYSTAL)
                .define('X', ActuallyItems.BASIC_COIL)
                .define('C', Tags.Items.COBBLESTONE)
                .define('F', Items.FURNACE)
                .define('I', ActuallyBlocks.IRON_CASING.getItem())
                .save(recipeOutput);

        // Greenhouse glass
        Recipe.shaped(ActuallyBlocks.GREENHOUSE_GLASS.getItem(), 2)
                .pattern("GSG", "SES", "GSG")
                .define('G', Tags.Items.GLASS)
                .define('S', ItemTags.SAPLINGS)
                .define('E', ActuallyItems.EMPOWERED_PALIS_CRYSTAL)
                .save(recipeOutput);

        // Block of Black Quartz
        Recipe.shaped(ActuallyBlocks.BLACK_QUARTZ.getItem())
                .pattern("BB", "BB")
                .define('B', ActuallyItems.BLACK_QUARTZ)
                .save(recipeOutput);

        // Pillar of Black Quartz
        Recipe.shaped(ActuallyBlocks.BLACK_QUARTZ_PILLAR.getItem())
                .pattern("B", "B")
                .define('B', ActuallyItems.BLACK_QUARTZ)
                .save(recipeOutput);

        // Chiseled Block of Black Quartz
        Recipe.shaped(ActuallyBlocks.CHISELED_BLACK_QUARTZ.getItem(), 2)
                .pattern("B", "B")
                .define('B', ActuallyBlocks.BLACK_QUARTZ.getItem())
                .save(recipeOutput);

        // Lava Factory Controller
        Recipe.shaped(ActuallyBlocks.LAVA_FACTORY_CONTROLLER.getItem())
                .pattern("ACA", "EAE", "BBB")
                .define('B', Items.LAVA_BUCKET)
                .define('A', ActuallyItems.ADVANCED_COIL.get())
                .define('E', ActuallyBlocks.EMPOWERED_ENORI_CRYSTAL.get())
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .save(recipeOutput);

        // Lava Casing
        Recipe.shaped(ActuallyBlocks.LAVA_FACTORY_CASING.getItem(), 32)
                .pattern("ECE")
                .define('E', ActuallyBlocks.ENORI_CRYSTAL.get())
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .save(recipeOutput);

        // Ranged Collector
        Recipe.shaped(ActuallyBlocks.RANGED_COLLECTOR.getItem())
                .pattern(" V ", "EHE", " C ")
                .define('V', ActuallyItems.VOID_CRYSTAL.get())
                .define('E', Tags.Items.ENDER_PEARLS)
                .define('H', Items.HOPPER)
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .save(recipeOutput);

        // Crusher
        Recipe.shaped(ActuallyBlocks.CRUSHER.getItem())
                .pattern("RFC", "BIB", "CFR")
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('F', Items.FLINT)
                .define('C', Tags.Items.COBBLESTONE)
                .define('I', ActuallyBlocks.IRON_CASING.get())
                .define('B', ActuallyItems.BASIC_COIL.get())
                .save(recipeOutput);

        // Double Crusher
        Recipe.shaped(ActuallyBlocks.CRUSHER_DOUBLE.getItem())
                .pattern("SAS", "CIC", "SAS")
                .define('S', Tags.Items.COBBLESTONE)
                .define('A', ActuallyItems.ADVANCED_COIL.get())
                .define('C', ActuallyBlocks.CRUSHER.get())
                .define('I', ActuallyBlocks.IRON_CASING.get())
                .save(recipeOutput);

        // Energizer
        Recipe.shaped(ActuallyBlocks.ENERGIZER.getItem())
                .pattern("R R", "AIA", "R R")
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('A', ActuallyItems.ADVANCED_COIL.get())
                .define('I', ActuallyBlocks.IRON_CASING.get())
                .save(recipeOutput);

        // Enervator
        Recipe.shaped(ActuallyBlocks.ENERVATOR.getItem())
                .pattern(" R ", "AIA", " R ")
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('A', ActuallyItems.ADVANCED_COIL.get())
                .define('I', ActuallyBlocks.IRON_CASING.get())
                .save(recipeOutput);

        // Leaf-eating Generator
        Recipe.shaped(ActuallyBlocks.LEAF_GENERATOR.getItem())
                .pattern("CIC", "ELE", "CAC")
                .define('C', ActuallyItems.ENORI_CRYSTAL.get())
                .define('I', ActuallyBlocks.IRON_CASING.get())
                .define('E', ActuallyBlocks.EMPOWERED_RESTONIA_CRYSTAL.get())
                .define('L', ItemTags.LEAVES)
                .define('A', ActuallyItems.ADVANCED_COIL.get())
                .save(recipeOutput);

        // Phantom Itemface
        Recipe.shaped(ActuallyBlocks.PHANTOM_ITEMFACE.getItem())
                .pattern(" C ", "MEM", " A ")
                .define('C', Tags.Items.CHESTS_WOODEN)
                .define('M', Items.PHANTOM_MEMBRANE)
                .define('E', ActuallyBlocks.ENDER_CASING.get())
                .define('A', ActuallyItems.ADVANCED_COIL.get())
                .save(recipeOutput);

        // Phantom Liquiface
        Recipe.shaped(ActuallyBlocks.PHANTOM_LIQUIFACE.getItem())
                .pattern("BIB")
                .define('B', Items.BUCKET)
                .define('I', ActuallyBlocks.PHANTOM_ITEMFACE.get())
                .save(recipeOutput);

        // Phantom Energyface
        Recipe.shaped(ActuallyBlocks.PHANTOM_ENERGYFACE.getItem())
                .pattern(" R ", "RIR", " R ")
                .define('R', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL)
                .define('I', ActuallyBlocks.PHANTOM_ITEMFACE.get())
                .save(recipeOutput);

        // Phantom Redstoneface
        Recipe.shaped(ActuallyBlocks.PHANTOM_REDSTONEFACE.getItem())
                .pattern("DRD", "RIR", "DRD")
                .define('D', Tags.Items.DUSTS_REDSTONE)
                .define('R', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL)
                .define('I', ActuallyBlocks.PHANTOM_ITEMFACE.get())
                .save(recipeOutput);

        // Phantom Booster
        Recipe.shaped(ActuallyBlocks.PHANTOM_BOOSTER.getItem())
                .pattern("RDR", "DCD", "RDR")
                .define('D', ActuallyItems.DIAMATINE_CRYSTAL)
                .define('R', ActuallyItems.RESTONIA_CRYSTAL)
                .define('C', ActuallyBlocks.ENDER_CASING.getItem())
                .save(recipeOutput);

        // Automatic Feeder
        Recipe.shaped(ActuallyBlocks.FEEDER.getItem())
                .pattern("PGP", "BCB", "PGP")
                .define('P', ItemTags.PLANKS)
                .define('G', Items.GOLDEN_CARROT)
                .define('B', ActuallyItems.BASIC_COIL)
                .define('C', ActuallyBlocks.WOOD_CASING.getItem())
                .save(recipeOutput);

        // Bio Reactor
        Recipe.shaped(ActuallyBlocks.BIOREACTOR.getItem())
                .pattern("EIE", "ESE", "EIE")
                .define('E', ActuallyItems.EMPOWERED_ENORI_CRYSTAL)
                .define('I', ActuallyBlocks.IRON_CASING.getItem())
                .define('S', ItemTags.SAPLINGS)
                .save(recipeOutput);

        // Heat Collector
        Recipe.shaped(ActuallyBlocks.HEAT_COLLECTOR.getItem())
                .pattern("IRI", "AEA", "ICI")
                .define('I', Items.IRON_BARS)
                .define('R', Items.REPEATER)
                .define('A', ActuallyItems.ADVANCED_COIL)
                .define('E', ActuallyItems.ENORI_CRYSTAL)
                .define('C', ActuallyBlocks.IRON_CASING.get())
                .save(recipeOutput);

        // Long-Range Breaker
        Recipe.shaped(ActuallyBlocks.LONG_RANGE_BREAKER.getItem())
                .pattern("AAA", " V ")
                .define('A', ActuallyBlocks.BREAKER.get())
                .define('V', ActuallyItems.VOID_CRYSTAL)
                .save(recipeOutput);
    }

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
