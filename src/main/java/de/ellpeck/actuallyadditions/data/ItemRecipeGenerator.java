package de.ellpeck.actuallyadditions.data;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.crafting.TargetNBTIngredient;
import de.ellpeck.actuallyadditions.mod.crafting.WrappedRecipe;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.data.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.fml.RegistryObject;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

public class ItemRecipeGenerator extends RecipeProvider {
    public ItemRecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        generatePaxels(consumer);

        //Goggles
        Recipe.shaped(ActuallyItems.ENGINEERS_GOGGLES.get())
            .pattern(" R ")
            .pattern("IGI")
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', Items.IRON_BARS)
            .define('G', Tags.Items.GLASS).save(consumer);

        //Advanced Goggles
        Recipe.shaped(ActuallyItems.ENGINEERS_GOGGLES_ADVANCED.get())
            .pattern(" R ")
            .pattern("IGI")
            .define('R', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL.get())
            .define('I', Items.IRON_BARS)
            .define('G', ActuallyItems.ENGINEERS_GOGGLES.get()).save(consumer);

        //Laser Upgrades
        //Invisibility
        Recipe.shaped(ActuallyItems.LASER_UPGRADE_INVISIBILITY.get(), 4)
            .pattern("GGG")
            .pattern("RCR")
            .pattern("GGG")
            .define('G', Tags.Items.GLASS_BLACK)
            .define('R', ActuallyItems.VOID_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(consumer);

        //Range
        Recipe.shaped(ActuallyItems.LASER_UPGRADE_RANGE.get(), 2)
            .pattern("GGC")
            .pattern("RCR")
            .pattern("CGG")
            .define('R', Items.COMPASS)
            .define('G', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(consumer);

        //Filling Wand
        Recipe.shaped(ActuallyItems.HANDHELD_FILLER.get())
            .pattern("IPI")
            .pattern("DCD")
            .pattern(" B ")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('P', ActuallyItems.PALIS_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('B', ActuallyItems.TRIPLE_BATTERY.get()).save(consumer);

        //Bag
        Recipe.shaped(ActuallyItems.TRAVELERS_SACK.get())
            .pattern("SLS")
            .pattern("SCS")
            .pattern("LVL")
            .define('S', Tags.Items.STRING)
            .define('L', Tags.Items.LEATHER)
            .define('C', Tags.Items.CHESTS_WOODEN)
            .define('V', ActuallyBlocks.VOID_CRYSTAL.getItem()).save(consumer);

        //Void Bag
        Recipe.shapeless(ActuallyItems.VOID_SACK.get())
            .requires(ActuallyItems.TRAVELERS_SACK.get())
            .requires(Tags.Items.ENDER_PEARLS)
            .requires(Tags.Items.OBSIDIAN)
            .requires(ActuallyBlocks.VOID_CRYSTAL.getItem())
            .save(consumer);

        //Lens
        Recipe.shaped(ActuallyItems.LENS.get())
            .pattern("GGG")
            .pattern("GBG")
            .pattern("GGG")
            .define('G', Tags.Items.GLASS)
            .define('B', ActuallyItems.BLACK_QUARTZ.get()).save(consumer);

        //Booklet
        Recipe.shapeless(ActuallyItems.ITEM_BOOKLET.get())
            .ingredients(ActuallyItems.CANOLA_SEEDS.get(), Items.PAPER).save(consumer);


        //Clearing NBT Storage
        Recipe.shapeless(ActuallyItems.LASER_WRENCH.get()).ingredients(ActuallyItems.LASER_WRENCH.get()).name(new ResourceLocation(ActuallyAdditions.MODID, "laser_wrench_nbt")).save(consumer);
        Recipe.shapeless(ActuallyItems.PHANTOM_CONNECTOR.get()).ingredients(ActuallyItems.PHANTOM_CONNECTOR.get()).save(consumer);

        //Disenchanting Lens
        Recipe.shapeless(ActuallyItems.LENS_OF_DISENCHANTING.get())
            .requires(ActuallyItems.LENS.get())
            .requires(Items.ENCHANTING_TABLE)
            .requires(ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get(), 7).save(consumer);

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
            .define('E', Tags.Items.GEMS_EMERALD).save(consumer);

        //Killer Lens
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        enchantedBook.enchant(Enchantments.SHARPNESS, 5);
        Recipe.shapeless(ActuallyItems.LENS_OF_THE_KILLER.get())
            .requires(Items.DIAMOND_SWORD)
            .requires(ActuallyItems.LENS_OF_CERTAIN_DEATH.get())
            .requires(NBTIngredient.of(enchantedBook)).save(consumer);


        //Filter
        Recipe.shaped(ActuallyItems.FILTER.get())
            .pattern("III")
            .pattern("IQI")
            .pattern("III")
            .define('I', Items.IRON_BARS)
            .define('Q', ActuallyItems.BLACK_QUARTZ.get()).save(consumer);

        //Crate Keeper
        Recipe.shaped(ActuallyItems.CRATE_KEEPER.get())
            .pattern("WIW")
            .pattern("IQI")
            .pattern("WIW")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('W', ItemTags.PLANKS)
            .define('Q', ActuallyItems.BLACK_QUARTZ.get()).save(consumer);

        //Laser Wrench
        Recipe.shaped(ActuallyItems.LASER_WRENCH.get())
            .pattern("C  ")
            .pattern(" S ")
            .pattern("  S")
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('S', ActuallyItems.ENORI_CRYSTAL.get()).save(consumer);


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
            .save(consumer, new ResourceLocation(ActuallyAdditions.MODID, "rice_slime"));

        Recipe.shaped(ActuallyItems.RICE_SLIMEBALL.get())
            .requiresBook()
            .pattern(" R ")
            .pattern("RBR")
            .pattern(" R ")
            .define('R', ActuallyItems.RICE_DOUGH.get())
            .define('B', Items.POTION)
            .save(consumer, new ResourceLocation(ActuallyAdditions.MODID, "rice_slime_potion"));

        //Leaf Blower
        Recipe.shaped(ActuallyItems.LEAF_BLOWER.get())
            .pattern(" F")
            .pattern("IP")
            .pattern("IC")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('P', Items.PISTON)
            .define('F', Items.FLINT)
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(consumer);

        //Drill //TODO the rest of the coloring recipes
        Recipe.shaped(ActuallyItems.DRILL_MAIN.get())
            .pattern("DDD")
            .pattern("CRC")
            .pattern("III")
            .define('D', Tags.Items.GEMS_DIAMOND)
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .define('R', ActuallyItems.DRILL_CORE.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get()).save(consumer);

        //Drill Core
        Recipe.shaped(ActuallyItems.DRILL_CORE.get())
            .pattern("ICI")
            .pattern("CRC")
            .pattern("ICI")
            .define('C', ActuallyItems.BASIC_COIL.get())
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get()).save(consumer);

        //Tele Staff
        Recipe.shaped(ActuallyItems.TELEPORT_STAFF.get())
            .pattern(" FE")
            .pattern(" S ")
            .pattern("SB ")
            .define('F', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
            .define('E', ActuallyBlocks.ENDER_PEARL_BLOCK.getItem())
            .define('S', ActuallyBlocks.ENDER_CASING.getItem())
            .define('B', ActuallyItems.SINGLE_BATTERY.get()).save(consumer);

        //Drill Speed upgrade
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get()).save(consumer);

        //Drill Speed upgrade II
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED_II.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', Items.CAKE).save(consumer);

        //Drill Speed upgrade III
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SPEED_III.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('S', Items.SUGAR)
            .define('R', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get()).save(consumer);

        //Drill Fortune upgrade
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FORTUNE.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', Items.GLOWSTONE)
            .define('S', Tags.Items.DUSTS_REDSTONE)
            .define('R', ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.getItem()).save(consumer);

        //Drill Fortune upgrade II
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FORTUNE_II.get())
            .pattern("ISI")
            .pattern("SRS")
            .pattern("ISI")
            .define('I', Items.GLOWSTONE)
            .define('S', ActuallyItems.EMPOWERED_RESTONIA_CRYSTAL.get())
            .define('R', ActuallyBlocks.ENDER_CASING.getItem()).save(consumer);

        //3x3
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_THREE_BY_THREE.get())
            .pattern("DID")
            .pattern("ICI")
            .pattern("DID")
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.BASIC_COIL.get()).save(consumer);

        //5x5
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_FIVE_BY_FIVE.get())
            .pattern("DID")
            .pattern("ICI")
            .pattern("DID")
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('D', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(consumer);

        //Silk Touch
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_SILK_TOUCH.get())
            .pattern("DSD")
            .pattern("SCS")
            .pattern("DSD")
            .define('D', ActuallyItems.EMERADIC_CRYSTAL.get())
            .define('S', ActuallyItems.DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get()).save(consumer);

        //Placing
        Recipe.shaped(ActuallyItems.DRILL_UPGRADE_BLOCK_PLACING.get())
            .pattern("CEC")
            .pattern("RAR")
            .pattern("CEC")
            .define('C', Tags.Items.COBBLESTONE)
            .define('E', Items.PAPER)
            .define('A', ActuallyItems.BASIC_COIL.get())
            .define('R', ActuallyItems.ENORI_CRYSTAL.get()).save(consumer);

        //Bat Wings
        Recipe.shaped(ActuallyItems.WINGS_OF_THE_BATS.get())
            .pattern("WNW")
            .pattern("WDW")
            .pattern("WNW")
            .define('W', ActuallyItems.BATS_WING.get())
            .define('N', ActuallyBlocks.DIAMATINE_CRYSTAL.getItem())
            .define('D', ActuallyItems.ENDER_STAR.get()).save(consumer);

        //Coil
        Recipe.shaped(ActuallyItems.BASIC_COIL.get())
            .pattern(" R ")
            .pattern("RIR")
            .pattern(" R ")
            .define('I', ActuallyItems.BLACK_QUARTZ.get())
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get()).save(consumer);

        //Advanced Coil
        Recipe.shaped(ActuallyItems.ADVANCED_COIL.get())
            .pattern("GGG")
            .pattern("GCG")
            .pattern("GGG")
            .define('C', ActuallyItems.BASIC_COIL.get())
            .define('G', Items.GOLD_NUGGET).save(consumer);

        //Battery
        Recipe.shaped(ActuallyItems.SINGLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', ActuallyItems.RESTONIA_CRYSTAL.get())
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(consumer);

        //Double Battery
        Recipe.shaped(ActuallyItems.DOUBLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetNBTIngredient.of(ActuallyItems.SINGLE_BATTERY.get()))
            .define('I', ActuallyItems.ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(WrappedRecipe.Inject(consumer, ActuallyRecipes.KEEP_DATA_SHAPED_RECIPE.get()));

        //Triple Battery
        Recipe.shaped(ActuallyItems.TRIPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetNBTIngredient.of(ActuallyItems.DOUBLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(WrappedRecipe.Inject(consumer, ActuallyRecipes.KEEP_DATA_SHAPED_RECIPE.get()));

        //Quad Battery
        Recipe.shaped(ActuallyItems.QUADRUPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetNBTIngredient.of(ActuallyItems.TRIPLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_ENORI_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(WrappedRecipe.Inject(consumer, ActuallyRecipes.KEEP_DATA_SHAPED_RECIPE.get()));

        //Quintuple Battery
        Recipe.shaped(ActuallyItems.QUINTUPLE_BATTERY.get())
            .pattern(" R ")
            .pattern("ICI")
            .pattern("III")
            .define('R', TargetNBTIngredient.of(ActuallyItems.QUADRUPLE_BATTERY.get()))
            .define('I', ActuallyItems.EMPOWERED_DIAMATINE_CRYSTAL.get())
            .define('C', ActuallyItems.ADVANCED_COIL.get())
            .save(WrappedRecipe.Inject(consumer, ActuallyRecipes.KEEP_DATA_SHAPED_RECIPE.get()));



        //
        //        //Magnet Ring
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemMagnetRing), "RIB", "IOI", "BIR", 'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 'B', new ItemStack(Items.DYE, 1, 4), 'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        //        recipeMagnetRing = RecipeUtil.lastIRecipe();
        //
        //        //Growth Ring
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemGrowthRing), "SIS", "IOI", "SIS", 'S', new ItemStack(Items.WHEAT_SEEDS), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()), 'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        //        recipeGrowthRing = RecipeUtil.lastIRecipe();
        //
        //        //Water Ring
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemWaterRemovalRing), "BIB", "IOI", "BIB", 'B', new ItemStack(Items.WATER_BUCKET), 'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()), 'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        //        recipeWaterRing = RecipeUtil.lastIRecipe();
        //
        //
        //        //Cup
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()), "S S", "SCS", "SSS", 'S', "stone", 'C', "cropCoffee");
        //        recipeCup = RecipeUtil.lastIRecipe();
        //
        //        //Resonant Rice
        //        if (!OreDictionary.getOres("nuggetEnderium", false).isEmpty()) {
        //            RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemResonantRice), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), "nuggetEnderium", Items.GUNPOWDER);
        //        }
        //
        //        //Advanced Leaf Blower
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemLeafBlowerAdvanced), " F", "DP", "DC", 'F', new ItemStack(Items.FLINT), 'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), 'P', new ItemStack(Blocks.PISTON), 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()));
        //        recipeLeafBlowerAdvanced = RecipeUtil.lastIRecipe();
        //
        //        //Phantom Connector
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemPhantomConnector), "YE", "EY", "S ", 'Y', Items.ENDER_EYE, 'E', Items.ENDER_PEARL, 'S', "stickWood");
        //        recipePhantomConnector = RecipeUtil.lastIRecipe();
        //
        //        //Player Probe
        //        RecipeHandler.addOreDictRecipe(new ItemStack(InitItems.itemPlayerProbe), "A A", "AIA", "RHR", 'A', new ItemStack(Blocks.IRON_BARS), 'R', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.REDSTONE.ordinal()), 'H', new ItemStack(Items.SKULL, 1, 1), 'I', new ItemStack(Items.IRON_HELMET));
        //        recipePlayerProbe = RecipeUtil.lastIRecipe();
        //
        //        //Quartz
        //        GameRegistry.addSmelting(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);
        //
        //        //Knife
        //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemKnife), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()));
        //        recipeKnife = RecipeUtil.lastIRecipe();
        //
        //        //Crafter on a Stick
        //        RecipeHandler.addShapelessOreDictRecipe(new ItemStack(InitItems.itemCrafterOnAStick), new ItemStack(Blocks.CRAFTING_TABLE), new ItemStack(Items.SIGN));
        //
        //        //Tiny Coal
        //        if (ConfigBoolValues.TINY_COAL_STUFF.isEnabled()) {
        //            RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_COAL.ordinal()), new ItemStack(Items.COAL));
        //            recipeTinyCoal = RecipeUtil.lastIRecipe();
        //            RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_CHAR.ordinal()), new ItemStack(Items.COAL, 1, 1));
        //            recipeTinyChar = RecipeUtil.lastIRecipe();
        //            RecipeHandler.addOreDictRecipe(new ItemStack(Items.COAL), "CCC", "C C", "CCC", 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_COAL.ordinal()));
        //            RecipeHandler.addOreDictRecipe(new ItemStack(Items.COAL, 1, 1), "CCC", "C C", "CCC", 'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_CHAR.ordinal()));
        //        }
        //
        //        //Rice Seeds
        //        RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemRiceSeed), new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()));
        //
        //        //Canola Seeds
        //        RecipeHandler.addShapelessRecipe(new ItemStack(InitItems.itemCanolaSeed), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal()));
        //
        //        //Rings
        //        initPotionRingRecipes();
        //
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

    protected void generatePaxels(Consumer<IFinishedRecipe> consumer) {
        addPaxel(consumer, ActuallyItems.WOODEN_AIOT, Items.WOODEN_AXE, Items.WOODEN_PICKAXE, Items.WOODEN_SWORD, Items.WOODEN_SHOVEL, Items.WOODEN_HOE);
        addPaxel(consumer, ActuallyItems.STONE_AIOT, Items.STONE_AXE, Items.STONE_PICKAXE, Items.STONE_SWORD, Items.STONE_SHOVEL, Items.STONE_HOE);
        addPaxel(consumer, ActuallyItems.IRON_AIOT, Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_HOE);
        addPaxel(consumer, ActuallyItems.GOLD_AIOT, Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_HOE);
        addPaxel(consumer, ActuallyItems.DIAMOND_AIOT, Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_HOE);
        addPaxel(consumer, ActuallyItems.NETHERITE_AIOT, Items.NETHERITE_AXE, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_HOE);
}

    public static void addPaxel(Consumer<IFinishedRecipe> consumer, RegistryObject<Item> output, Item axe, Item pickaxe, Item sword, Item shovel, Item hoe) {
        Recipe.shapeless(output.get())
            .requires(axe)
            .requires(pickaxe)
            .requires(sword)
            .requires(shovel)
            .requires(hoe)
            .save(consumer);
    }

    public static void addPaxel(Consumer<IFinishedRecipe> consumer, RegistryObject<Item> output, RegistryObject<Item> axe, RegistryObject<Item> pickaxe, RegistryObject<Item> sword, RegistryObject<Item> shovel, RegistryObject<Item> hoe) {
        Recipe.shapeless(output.get())
            .requires(axe.get())
            .requires(pickaxe.get())
            .requires(sword.get())
            .requires(shovel.get())
            .requires(hoe.get())
            .save(consumer);
    }


    @Override
    protected void saveAdvancement(DirectoryCache cache, JsonObject cache2, Path advancementJson) {
        //Nope...
    }

    public static void addToolAndArmorRecipes(Consumer<IFinishedRecipe> consumer, RegistryObject<Item> base, RegistryObject<Item> pickaxe, RegistryObject<Item> sword, RegistryObject<Item> axe, RegistryObject<Item> shovel, RegistryObject<Item> hoe, RegistryObject<Item> helm, RegistryObject<Item> chest, RegistryObject<Item> pants, RegistryObject<Item> boots) {
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
        public static ItemRecipeGenerator.Recipe.Shapeless shapeless(IItemProvider result) {
            return new ItemRecipeGenerator.Recipe.Shapeless(result);
        }

        public static ItemRecipeGenerator.Recipe.Shapeless shapeless(IItemProvider result, int count) {
            return new ItemRecipeGenerator.Recipe.Shapeless(result, count);
        }

        public static ItemRecipeGenerator.Recipe.Shaped shaped(IItemProvider result) {
            return new ItemRecipeGenerator.Recipe.Shaped(result);
        }

        public static ItemRecipeGenerator.Recipe.Shaped shaped(IItemProvider result, int count) {
            return new ItemRecipeGenerator.Recipe.Shaped(result, count);
        }

        private static class Shapeless extends ShapelessRecipeBuilder {
            private ResourceLocation name;
            public Shapeless(IItemProvider result) {
                this(result, 1);
            }

            public Shapeless(IItemProvider result, int countIn) {
                super(result, countIn);
            }

            public ItemRecipeGenerator.Recipe.Shapeless ingredients(IItemProvider... ingredients) {
                Arrays.asList(ingredients).forEach(this::requires);
                return this;
            }

            public ItemRecipeGenerator.Recipe.Shapeless name(ResourceLocation name) {
                this.name = name;
                return this;
            }

            @Override
            public void save(Consumer<IFinishedRecipe> consumer) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                if (this.name != null) {
                    this.save(consumer, this.name);
                } else {
                    super.save(consumer);
                }
            }
        }

        private static class Shaped extends ShapedRecipeBuilder {
            public Shaped(IItemProvider resultIn) {
                this(resultIn, 1);
            }

            public Shaped(IItemProvider resultIn, int countIn) {
                super(resultIn, countIn);
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

            public ItemRecipeGenerator.Recipe.Shaped patternSingleKey(char key, IItemProvider resource, String... lines) {
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
            public void save(Consumer<IFinishedRecipe> consumerIn) {
                this.unlockedBy("has_book", has(ActuallyItems.ITEM_BOOKLET.get()));
                super.save(consumerIn);
            }
        }
    }
}
