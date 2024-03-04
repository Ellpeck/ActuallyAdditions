package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.conditions.BoolConfigCondition;
import de.ellpeck.actuallyadditions.mod.crafting.RecipeKeepDataShaped;
import de.ellpeck.actuallyadditions.mod.crafting.TargetNBTIngredient;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.util.NoAdvRecipeOutput;
import de.ellpeck.actuallyadditions.mod.util.RecipeInjector;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.NBTIngredient;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class ItemRecipeGenerator extends RecipeProvider {
    public ItemRecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Item " + super.getName();
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        var recipeOutput = new NoAdvRecipeOutput(output);

        generatePaxels(recipeOutput);

        //Goggles
        Recipe.shaped(ActuallyItems.ENGINEERS_GOGGLES.get())
            .pattern(" R ")
            .pattern("IGI")
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', Items.IRON_BARS)
            .define('G', Tags.Items.GLASS).save(recipeOutput);

        //Advanced Goggles
        Recipe.shaped(ActuallyItems.ENGINEERS_GOGGLES_ADVANCED.get())
            .pattern(" R ")
            .pattern("IGI")
            .define('R', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL.get())
            .define('I', Items.IRON_BARS)
            .define('G', ActuallyItems.ENGINEERS_GOGGLES.get()).save(recipeOutput);

        //Laser Upgrades
        //Invisibility
        Recipe.shaped(ActuallyItems.LASER_UPGRADE_INVISIBILITY.get(), 4)
            .pattern("GGG")
            .pattern("RCR")
            .pattern("GGG")
            .define('G', Tags.Items.GLASS_BLACK)
            .define('R', ActuallyItems.VOID_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Range
        Recipe.shaped(ActuallyItems.LASER_UPGRADE_RANGE.get(), 2)
            .pattern("GGC")
            .pattern("RCR")
            .pattern("CGG")
            .define('R', Items.COMPASS)
            .define('G', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Filling Wand
        Recipe.shaped(ActuallyItems.HANDHELD_FILLER.get())
            .pattern("IPI")
            .pattern("DCD")
            .pattern(" B ")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('P', ActuallyItems.PALIS_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('B', ActuallyItems.TRIPLE_BATTERY.get()).save(recipeOutput);

        //Bag
        Recipe.shaped(ActuallyItems.TRAVELERS_SACK.get())
            .pattern("SLS")
            .pattern("SCS")
            .pattern("LVL")
            .define('S', Tags.Items.STRING)
            .define('L', Tags.Items.LEATHER)
            .define('C', Tags.Items.CHESTS_WOODEN)
            .define('V', ActuallyBlocks.VOID_CRYSTAL.getItem()).save(recipeOutput);

        //Void Bag
        Recipe.shapeless(ActuallyItems.VOID_SACK.get())
            .requires(ActuallyItems.TRAVELERS_SACK.get())
            .requires(Tags.Items.ENDER_PEARLS)
            .requires(Tags.Items.OBSIDIAN)
            .requires(ActuallyBlocks.VOID_CRYSTAL.getItem())
            .save(recipeOutput);

        //Lens
        Recipe.shaped(ActuallyItems.LENS.get())
            .pattern("GGG")
            .pattern("GBG")
            .pattern("GGG")
            .define('G', Tags.Items.GLASS)
            .define('B', ActuallyItems.BLACK_QUARTZ.get()).save(recipeOutput);

        //Booklet
        Recipe.shapeless(ActuallyItems.ITEM_BOOKLET.get())
            .ingredients(ActuallyItems.CANOLA_SEEDS.get(), Items.PAPER).save(recipeOutput);


        //Clearing NBT Storage
        Recipe.shapeless(ActuallyItems.LASER_WRENCH.get()).ingredients(ActuallyItems.LASER_WRENCH.get()).name(new ResourceLocation(ActuallyAdditions.MODID, "laser_wrench_nbt")).save(recipeOutput);
        Recipe.shapeless(ActuallyItems.PHANTOM_CONNECTOR.get()).ingredients(ActuallyItems.PHANTOM_CONNECTOR.get()).name(new ResourceLocation(ActuallyAdditions.MODID, "phantom_clearing")).save(recipeOutput);

        //Disenchanting Lens
        Recipe.shapeless(ActuallyItems.LENS_OF_DISENCHANTING.get())
            .requires(ActuallyItems.LENS.get())
            .requires(Items.ENCHANTING_TABLE)
            .requires(ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get(), 7).save(recipeOutput);

        //Mining Lens
        Recipe.shaped(ActuallyItems.LENS_OF_THE_MINER.get())
            .pattern("DGI")
            .pattern("CLB")
            .pattern("QPE")
            .define('D', Tags.Items.GEMS_DIAMOND)
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('I', Tags.Items.INGOTS_IRON)
            .define('C', ItemTags.COALS)
            .define('L', ActuallyItems.LENS.get())
            .define('B', ActuallyItems.BLACK_QUARTZ.get())
            .define('Q', Tags.Items.GEMS_QUARTZ)
            .define('P', Tags.Items.GEMS_LAPIS)
            .define('E', Tags.Items.GEMS_EMERALD).save(recipeOutput);

        //Killer Lens
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        enchantedBook.enchant(Enchantments.SHARPNESS, 5);
        Recipe.shapeless(ActuallyItems.LENS_OF_THE_KILLER.get())
            .requires(Items.DIAMOND_SWORD)
            .requires(ActuallyItems.LENS_OF_CERTAIN_DEATH.get())
            .requires(NBTIngredient.of(true, enchantedBook)).save(recipeOutput);


        //Filter
        Recipe.shaped(ActuallyItems.FILTER.get())
            .pattern("III")
            .pattern("IQI")
            .pattern("III")
            .define('I', Items.IRON_BARS)
            .define('Q', ActuallyItems.BLACK_QUARTZ.get()).save(recipeOutput);

        //Crate Keeper
        Recipe.shaped(ActuallyItems.CRATE_KEEPER.get())
            .pattern("WIW")
            .pattern("IQI")
            .pattern("WIW")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', ItemTags.PLANKS)
            .define('Q', ActuallyItems.BLACK_QUARTZ.get()).save(recipeOutput);

        //Laser Wrench
        Recipe.shaped(ActuallyItems.LASER_WRENCH.get())
            .pattern("C  ")
            .pattern(" S ")
            .pattern("  S")
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('S', ActuallyItems.ENORI_CRYSTAL.get()).save(recipeOutput);


/*        //Rice Recipes
        Recipe.shaped(Items.PAPER, 3)
            .pattern("R  ")
            .pattern(" R ")
            .pattern("  R")
            .define('R', TheFoods.RICE).save(consumer); //TODO foods need worked on still.*/

        Recipe.shaped(ActuallyItems.RICE_SLIMEBALL.get())
            .requiresBook()
            .pattern(" R ")
            .pattern("RBR")
            .pattern(" R ")
            .define('R', ActuallyItems.RICE_DOUGH.get())
            .define('B', Items.WATER_BUCKET)
            .save(recipeOutput, new ResourceLocation(ActuallyAdditions.MODID, "rice_slime"));

        Recipe.shaped(ActuallyItems.RICE_SLIMEBALL.get())
            .requiresBook()
            .pattern(" R ")
            .pattern("RBR")
            .pattern(" R ")
            .define('R', ActuallyItems.RICE_DOUGH.get())
            .define('B', Items.POTION)
            .save(recipeOutput, new ResourceLocation(ActuallyAdditions.MODID, "rice_slime_potion"));

        //Leaf Blower
        Recipe.shaped(ActuallyItems.LEAF_BLOWER.get())
            .pattern(" F")
            .pattern("IP")
            .pattern("IC")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('P', Items.PISTON)
            .define('F', Items.FLINT)
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Advanced Leaf Blower
        Recipe.shaped(ActuallyItems.ADVANCED_LEAF_BLOWER.get()).pattern(" F", "DP", "DC")
                .define('F', Items.FLINT)
                .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
                .define('P', Items.PISTON)
                .define('C', ActuallyItems.ADVANCED_COIL.get())
                .save(recipeOutput);

        //Drill //TODO the rest of the coloring recipes
        Recipe.shaped(ActuallyItems.DRILL_MAIN.get())
            .pattern("DDD")
            .pattern("CRC")
            .pattern("III")
            .define('D', Tags.Items.GEMS_DIAMOND)
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('R', ActuallyItems.DRILL_CORE.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get()).save(recipeOutput);

        //Drill Core
        Recipe.shaped(ActuallyItems.DRILL_CORE.get())
            .pattern("ICI")
            .pattern("CRC")
            .pattern("ICI")
            .define('C', ActuallyItems.BASIC_COIL.get())
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get()).save(recipeOutput);

        //Tele Staff
        Recipe.shaped(ActuallyItems.TELEPORT_STAFF.get())
            .pattern(" FE")
            .pattern(" S ")
            .pattern("SB ")
            .define('F', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
            .define('E', Tags.Items.ENDER_PEARLS)
            .define('S', ActuallyBlocks.ENDER_CASING.getItem())
            .define('B', ActuallyItems.SINGLE_BATTERY.get()).save(recipeOutput);

        //Drill Speed upgrade
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get()).save(recipeOutput);

        //Drill Speed upgrade II
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED_II.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', Items.CAKE).save(recipeOutput);

        //Drill Speed upgrade III
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED_III.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get()).save(recipeOutput);

        //Drill Fortune upgrade
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FORTUNE.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', Items.GLOWSTONE)
            .define('S', Tags.Items.DUSTS_REDSTONE)
            .define('R', ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.getItem()).save(recipeOutput);

        //Drill Fortune upgrade II
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FORTUNE_II.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', Items.GLOWSTONE)
            .define('S', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL.get())
            .define('R', ActuallyBlocks.ENDER_CASING.getItem()).save(recipeOutput);

        //3x3
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_THREE_BY_THREE.get())
            .pattern("DID")
            .pattern("ICI")
            .pattern("DID")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.BASIC_COIL.get()).save(recipeOutput);

        //5x5
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FIVE_BY_FIVE.get())
            .pattern("DID")
            .pattern("ICI")
            .pattern("DID")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Silk Touch
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SILK_TOUCH.get())
            .pattern("DSD")
            .pattern("SCS")
            .pattern("DSD")
            .define('D', ActuallyItems.EMERADIC_CRYSTAL.get())
            .define('S', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(recipeOutput);

        //Placing
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_BLOCK_PLACING.get())
            .pattern("CEC")
            .pattern("RAR")
            .pattern("CEC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('E', Items.PAPER)
            .define('A', ActuallyItems.BASIC_COIL.get())
            .define('R', ActuallyItems.ENORI_CRYSTAL.get()).save(recipeOutput);

        //Bat Wings
        Recipe.shaped(ActuallyItems.WINGS_OF_THE_BATS.get())
            .pattern("WNW")
            .pattern("WDW")
            .pattern("WNW")
            .define('W', ActuallyItems.BATS_WING.get())
            .define('N', ActuallyBlocks.DIAMATINE_CRYSTAL.getItem())
            .define('D', ActuallyItems.ENDER_STAR.get()).save(recipeOutput);

        //Coil
        Recipe.shaped(ActuallyItems.BASIC_COIL.get())
            .pattern(" R ")
            .pattern("RIR")
            .pattern(" R ")
            .define('I', ActuallyItems.BLACK_QUARTZ.get())
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get()).save(recipeOutput);

        //Advanced Coil
        Recipe.shaped(ActuallyItems.ADVANCED_COIL.get())
            .pattern("GGG")
            .pattern("GCG")
            .pattern("GGG")
            .define('C', ActuallyItems.BASIC_COIL.get())
            .define('G', Items.GOLD_NUGGET).save(recipeOutput);

        //Battery
        Recipe.shaped(ActuallyItems.SINGLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(recipeOutput);

        //Double Battery
        Recipe.shaped(ActuallyItems.DOUBLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetNBTIngredient.of(ActuallyItems.SINGLE_BATTERY.get()))
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(new RecipeInjector<ShapedRecipe>(recipeOutput, RecipeKeepDataShaped::new));

        //Triple Battery
        Recipe.shaped(ActuallyItems.TRIPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetNBTIngredient.of(ActuallyItems.DOUBLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(new RecipeInjector<ShapedRecipe>(recipeOutput, RecipeKeepDataShaped::new));

        //Quad Battery
        Recipe.shaped(ActuallyItems.QUADRUPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetNBTIngredient.of(ActuallyItems.TRIPLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(new RecipeInjector<ShapedRecipe>(recipeOutput, RecipeKeepDataShaped::new));

        //Quintuple Battery
        Recipe.shaped(ActuallyItems.QUINTUPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetNBTIngredient.of(ActuallyItems.QUADRUPLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(new RecipeInjector<ShapedRecipe>(recipeOutput, RecipeKeepDataShaped::new));

        //Magnet Ring
        Recipe.shaped(ActuallyItems.RING_OF_MAGNETIZING.get())
                .pattern("RIB", "IOI", "BIR")
                .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
                .define('I', ActuallyItems.ENORI_CRYSTAL.get())
                .define('B', Items.LAPIS_LAZULI)
                .define('O', ActuallyItems.RING.get())
                .save(recipeOutput);

        //Growth Ring
        Recipe.shaped(ActuallyItems.RING_OF_GROWTH.get())
                .pattern("SIS", "IOI", "SIS")
                .define('S', Tags.Items.SEEDS)
                .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
                .define('O', ActuallyItems.RING.get())
                .save(recipeOutput);

        Recipe.shapeless(ActuallyItems.CRAFTER_ON_A_STICK.get()).requires(Items.CRAFTING_TABLE).requires(ItemTags.SIGNS).save(recipeOutput);

        RecipeOutput boolConsumer = recipeOutput.withConditions(new BoolConfigCondition("tinyCoalStuff"));

        Recipe.shapeless(ActuallyItems.TINY_COAL.get(), 8)
                .requires(Items.COAL)
                .save(boolConsumer, new ResourceLocation(ActuallyAdditions.MODID, "coal_to_tiny"));
        Recipe.shapeless(ActuallyItems.TINY_CHARCOAL.get(), 8)
                .requires(Items.CHARCOAL).save(boolConsumer, new ResourceLocation(ActuallyAdditions.MODID, "charcoal_to_tiny"));
        Recipe.shaped(Items.COAL)
                .pattern("CCC", "C C", "CCC").define('C', ActuallyItems.TINY_COAL.get())
                .save(boolConsumer, new ResourceLocation(ActuallyAdditions.MODID, "tiny_to_coal"));
        Recipe.shaped(Items.CHARCOAL)
                .pattern("CCC", "C C", "CCC").define('C', ActuallyItems.TINY_CHARCOAL.get())
                .save(boolConsumer, new ResourceLocation(ActuallyAdditions.MODID, "tiny_to_charcoal"));

        //Canola Seeds
        Recipe.shapeless(ActuallyItems.CANOLA_SEEDS.get())
                .requires(ActuallyItems.CANOLA.get())
                .save(recipeOutput);

        //Rice Seeds
        Recipe.shapeless(ActuallyItems.RICE_SEEDS.get())
                .requires(ActuallyItems.RICE.get())
                .save(recipeOutput);

        //Cup
        Recipe.shaped(ActuallyItems.EMPTY_CUP.get())
                .pattern("S S", "SCS", "SSS")
                .define('S', Tags.Items.STONE)
                .define('C', ActuallyItems.COFFEE_BEANS.get())
                .save(recipeOutput);

        //Phantom Connector
        Recipe.shaped(ActuallyItems.PHANTOM_CONNECTOR.get())
                .pattern("YE", "EY", "S ")
                .define('Y', Items.ENDER_EYE)
                .define('E', Tags.Items.ENDER_PEARLS)
                .define('S', Tags.Items.RODS_WOODEN)
                .save(recipeOutput);

        //Player Probe
        Recipe.shaped(ActuallyItems.PLAYER_PROBE.get())
                .pattern("A A", "AIA", "RHR")
                .define('A', Items.IRON_BARS)
                .define('R', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL.get())
                .define('H', Items.WITHER_SKELETON_SKULL)
                .define('I', Items.IRON_HELMET)
                .save(recipeOutput);

        //Shards
        addShard(recipeOutput, ActuallyItems.VOID_CRYSTAL_SHARD, ActuallyItems.VOID_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.ENORI_CRYSTAL_SHARD, ActuallyItems.ENORI_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.RESTONIA_CRYSTAL_SHARD, ActuallyItems.RESTONIA_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.PALIS_CRYSTAL_SHARD, ActuallyItems.PALIS_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.DIAMATINE_CRYSTAL_SHARD, ActuallyItems.DIAMATINE_CRYSTAL);
        addShard(recipeOutput, ActuallyItems.EMERADIC_CRYSTAL_SHARD, ActuallyItems.EMERADIC_CRYSTAL);


        //        //Quartz
        //        GameRegistry.addSmelting(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);

        //        //Ingots from Dusts
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.IRON.ordinal()), new ItemStack(Items.IRON_INGOT), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()), new ItemStack(Items.GOLD_INGOT), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.DIAMOND.ordinal()), new ItemStack(Items.DIAMOND), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.EMERALD.ordinal()), new ItemStack(Items.EMERALD), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.LAPIS.ordinal()), new ItemStack(Items.DYE, 1, 4), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ_BLACK.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ.ordinal()), new ItemStack(Items.QUARTZ), 1F);
        //        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()), new ItemStack(Items.COAL), 1F);
    }

    protected void generatePaxels(RecipeOutput consumer) {
        addPaxel(consumer, ActuallyItems.WOODEN_AIOT, Items.WOODEN_AXE, Items.WOODEN_PICKAXE, Items.WOODEN_SWORD, Items.WOODEN_SHOVEL, Items.WOODEN_HOE);
        addPaxel(consumer, ActuallyItems.STONE_AIOT, Items.STONE_AXE, Items.STONE_PICKAXE, Items.STONE_SWORD, Items.STONE_SHOVEL, Items.STONE_HOE);
        addPaxel(consumer, ActuallyItems.IRON_AIOT, Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_HOE);
        addPaxel(consumer, ActuallyItems.GOLD_AIOT, Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_HOE);
        addPaxel(consumer, ActuallyItems.DIAMOND_AIOT, Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_HOE);
        addPaxel(consumer, ActuallyItems.NETHERITE_AIOT, Items.NETHERITE_AXE, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_HOE);
}

    public static void addPaxel(RecipeOutput consumer, DeferredItem<? extends Item> output, Item axe, Item pickaxe, Item sword, Item shovel, Item hoe) {
        Recipe.shapeless(output.get())
            .requires(axe)
            .requires(pickaxe)
            .requires(sword)
            .requires(shovel)
            .requires(hoe)
            .save(consumer);
    }

    public static void addPaxel(RecipeOutput consumer, DeferredItem<? extends Item> output, DeferredItem<? extends Item> axe, DeferredItem<? extends Item> pickaxe, DeferredItem<? extends Item> sword, DeferredItem<? extends Item> shovel, DeferredItem<? extends Item> hoe) {
        Recipe.shapeless(output.get())
            .requires(axe.get())
            .requires(pickaxe.get())
            .requires(sword.get())
            .requires(shovel.get())
            .requires(hoe.get())
            .save(consumer);
    }

    public static void decompress(RecipeOutput consumer, DeferredItem<? extends Item> output, DeferredItem<? extends Item> input) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(output.get());
        Recipe.shapeless(output.get(), 9).requires(input.get()).save(consumer, new ResourceLocation(key.getNamespace(), "decompress/" + key.getPath()));
    }
    public static void compress(RecipeOutput consumer, DeferredItem<? extends Item> output, DeferredItem<? extends Item> input) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(output.get());
        Recipe.shaped(output.get()).pattern("xxx","xxx", "xxx").define('x', input.get()).save(consumer, new ResourceLocation(key.getNamespace(), "compress/" + key.getPath()));
    }
    public static void addShard(RecipeOutput consumer, DeferredItem<? extends Item> shard, DeferredItem<? extends Item> crystal) {
        compress(consumer, crystal, shard);
        decompress(consumer, shard, crystal);
    }

    public static void addToolAndArmorRecipes(RecipeOutput consumer, DeferredItem<? extends Item> base, DeferredItem<? extends Item> pickaxe, DeferredItem<? extends Item> sword, DeferredItem<? extends Item> axe, DeferredItem<? extends Item> shovel, DeferredItem<? extends Item> hoe, DeferredItem<? extends Item> helm, DeferredItem<? extends Item> chest, DeferredItem<? extends Item> pants, DeferredItem<? extends Item> boots) {
        //Pickaxe
        Recipe.shaped(pickaxe.get())
            .pattern("EEE", " S ", " S ")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Sword
        Recipe.shaped(sword.get())
            .pattern("E", "E", "S")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Axe
        Recipe.shaped(axe.get())
            .pattern("EE", "ES", " S")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Shovel
        Recipe.shaped(shovel.get())
            .pattern("E", "S", "S")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Hoe
        Recipe.shaped(hoe.get())
            .pattern("EE", " S", " S")
            .define('E', base.get())
            .define('S', Tags.Items.RODS_WOODEN)
            .save(consumer);

        //Helm
        Recipe.shaped(helm.get())
            .pattern("OOO", "O O")
            .define('O', base.get())
            .save(consumer);

        //Chest
        Recipe.shaped(chest.get())
            .pattern("O O", "OOO", "OOO")
            .define('O', base.get())
            .save(consumer);

        //Legs
        Recipe.shaped(pants.get())
            .pattern("OOO", "O O", "O O")
            .define('O', base.get())
            .save(consumer);

        //Boots
        Recipe.shaped(boots.get())
            .pattern("O O", "O O")
            .define('O', base.get())
            .save(consumer);
    }

    public static class Recipe {
        public static ItemRecipeGenerator.Recipe.Shapeless shapeless(ItemLike result) {
            return new ItemRecipeGenerator.Recipe.Shapeless(result);
        }

        public static ItemRecipeGenerator.Recipe.Shapeless shapeless(ItemLike result, int count) {
            return new ItemRecipeGenerator.Recipe.Shapeless(result, count);
        }

        public static ItemRecipeGenerator.Recipe.Shaped shaped(ItemLike result) {
            return new ItemRecipeGenerator.Recipe.Shaped(result);
        }

        public static ItemRecipeGenerator.Recipe.Shaped shaped(ItemLike result, int count) {
            return new ItemRecipeGenerator.Recipe.Shaped(result, count);
        }

        private static class Shapeless extends ShapelessRecipeBuilder {
            private ResourceLocation name;
            public Shapeless(ItemLike result) {
                this(result, 1);
            }

            public Shapeless(ItemLike result, int countIn) {
                super(RecipeCategory.MISC, result, countIn);
            }

            public ItemRecipeGenerator.Recipe.Shapeless ingredients(ItemLike... ingredients) {
                Arrays.asList(ingredients).forEach(this::requires);
                return this;
            }

            public ItemRecipeGenerator.Recipe.Shapeless name(ResourceLocation name) {
                this.name = name;
                return this;
            }

            @Override
            public void save(@Nonnull RecipeOutput consumer) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                if (this.name != null) {
                    this.save(consumer, this.name);
                } else {
                    super.save(consumer);
                }
            }
            @Override
            public void save(@Nonnull RecipeOutput consumer, @Nonnull ResourceLocation location) {
                this.unlockedBy("", has(Items.AIR));
                super.save(consumer, location);
            }
        }

        private static class Shaped extends ShapedRecipeBuilder {
            public Shaped(ItemLike resultIn) {
                this(resultIn, 1);
            }

            public Shaped(ItemLike resultIn, int countIn) {
                super(RecipeCategory.MISC, resultIn, countIn);
            }

            public ItemRecipeGenerator.Recipe.Shaped pattern(String line1, String line2, String line3) {
                this.pattern(line1);
                this.pattern(line2);
                this.pattern(line3);
                return this;
            }

            public ItemRecipeGenerator.Recipe.Shaped pattern(String line1, String line2) {
                this.pattern(line1);
                this.pattern(line2);
                return this;
            }

            public ItemRecipeGenerator.Recipe.Shaped patternSingleKey(char key, ItemLike resource, String... lines) {
                this.define(key, resource);
                for (String line : lines) {
                    this.pattern(line);
                }

                return this;
            }

            public ItemRecipeGenerator.Recipe.Shaped requiresBook() {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                return this;
            }

            @Override
            public void save(@Nonnull RecipeOutput consumerIn) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumerIn);
            }

            @Override
            public void save(@Nonnull RecipeOutput consumer, @Nonnull ResourceLocation location) {
                this.unlockedBy("", has(Items.AIR));
                super.save(consumer, location);
            }
        }
    }
}
