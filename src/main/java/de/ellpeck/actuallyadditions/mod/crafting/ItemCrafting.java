/*
 * This file ("ItemCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.*;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

public final class ItemCrafting{

    public static final ArrayList<IRecipe> RECIPES_POTION_RINGS = new ArrayList<IRecipe>();
    public static final ArrayList<IRecipe> RECIPES_DRILL_COLORING = new ArrayList<IRecipe>();
    public static IRecipe recipePhantomConnector;
    public static IRecipe recipeCoil;
    public static IRecipe recipeCoilAdvanced;
    public static IRecipe recipeBook;
    public static IRecipe recipeTinyCoal;
    public static IRecipe recipeTinyChar;
    public static IRecipe recipeDrill;
    public static IRecipe recipeDrillSpeedI;
    public static IRecipe recipeDrillSpeedII;
    public static IRecipe recipeDrillSpeedIII;
    public static IRecipe recipeDrillFortuneI;
    public static IRecipe recipeDrillFortuneII;
    public static IRecipe recipeDrillSilk;
    public static IRecipe recipeDrillPlacing;
    public static IRecipe recipeDrillThree;
    public static IRecipe recipeDrillFive;
    public static IRecipe recipeBattery;
    public static IRecipe recipeBatteryDouble;
    public static IRecipe recipeBatteryTriple;
    public static IRecipe recipeBatteryQuadruple;
    public static IRecipe recipeBatteryQuintuple;
    public static IRecipe recipeStaff;
    public static IRecipe recipeGrowthRing;
    public static IRecipe recipeMagnetRing;
    public static IRecipe recipeWaterRing;
    public static IRecipe recipeWings;
    public static IRecipe recipeCup;
    public static IRecipe recipeKnifeHandle;
    public static IRecipe recipeKnifeBlade;
    public static IRecipe recipeKnife;
    public static IRecipe recipeRing;
    public static IRecipe recipeDough;
    public static IRecipe recipeRiceDough;
    public static IRecipe recipeLeafBlower;
    public static IRecipe recipeLeafBlowerAdvanced;
    public static IRecipe recipeChestToCrateUpgrade;
    public static IRecipe recipeSmallToMediumCrateUpgrade;
    public static IRecipe recipeMediumToLargeCrateUpgrade;
    public static IRecipe recipeLaserWrench;
    public static IRecipe recipeDrillCore;
    public static IRecipe recipeBlackDye;
    public static IRecipe recipeLens;
    public static IRecipe recipeCrateKeeper;
    public static IRecipe recipeEnderStar;
    public static IRecipe recipeSpawnerChanger;
    public static IRecipe recipeFilter;
    public static IRecipe recipePlayerProbe;
    public static IRecipe recipeDisenchantingLens;
    public static IRecipe recipeMiningLens;
    public static IRecipe recipeBag;
    public static IRecipe recipeVoidBag;
    public static IRecipe recipeLensMoreDeath;
    public static IRecipe recipeFillingWand;
    public static IRecipe recipeLaserUpgradeInvisibility;
    public static IRecipe recipeLaserUpgradeRange;
    public static IRecipe recipeGoggles;
    public static IRecipe recipeGogglesAdvanced;

    public static void init(){

        //Advanced Goggles
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemEngineerGogglesAdvanced),
                " R ", "IGI",
                'R', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.REDSTONE.ordinal()),
                'I', new ItemStack(Blocks.IRON_BARS),
                'G', new ItemStack(InitItems.itemEngineerGoggles)));
        recipeGogglesAdvanced = RecipeUtil.lastIRecipe();

        //Goggles
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemEngineerGoggles),
                " R ", "IGI",
                'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                'I', new ItemStack(Blocks.IRON_BARS),
                'G', "blockGlass"));
        recipeGoggles = RecipeUtil.lastIRecipe();

        //Laser Invis Upgrade
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLaserUpgradeInvisibility, 4),
                "GGG", "RCR", "GGG",
                'G', "blockGlassBlack",
                'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeLaserUpgradeInvisibility = RecipeUtil.lastIRecipe();

        //Laser Range Upgrade
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLaserUpgradeRange, 2),
                "GGC", "RCR", "CGG",
                'R', new ItemStack(Items.COMPASS),
                'G', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeLaserUpgradeRange = RecipeUtil.lastIRecipe();

        //Filling Wand
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFillingWand),
                "IPI", "DCD", " B ",
                'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()),
                'P', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                'B', new ItemStack(InitItems.itemBatteryTriple)));
        recipeFillingWand = RecipeUtil.lastIRecipe();

        //Bag
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBag),
                "SLS", "SCS", "LVL",
                'S', new ItemStack(Items.STRING),
                'L', new ItemStack(Items.LEATHER),
                'C', "chestWood",
                'V', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal())));
        recipeBag = RecipeUtil.lastIRecipe();

        //Void Bag
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemVoidBag),
                new ItemStack(InitItems.itemBag),
                new ItemStack(Items.ENDER_PEARL),
                new ItemStack(Blocks.OBSIDIAN),
                new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal())));
        recipeVoidBag = RecipeUtil.lastIRecipe();

        //Lens
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()),
                "GGG", "GBG", "GGG",
                'G', "blockGlass",
                'B', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
        recipeLens = RecipeUtil.lastIRecipe();

        //Black Dye
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.BLACK_DYE.ordinal()), new ItemStack(InitBlocks.blockBlackLotus)));
        recipeBlackDye = RecipeUtil.lastIRecipe();

        //Booklet
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemBooklet), new ItemStack(InitItems.itemCanolaSeed), new ItemStack(Items.PAPER)));
        recipeBook = RecipeUtil.lastIRecipe();

        //Clearing NBT Storage
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemLaserWrench), new ItemStack(InitItems.itemLaserWrench));
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPhantomConnector), new ItemStack(InitItems.itemPhantomConnector));
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemSpawnerChanger), new ItemStack(InitItems.itemSpawnerChanger));

        //Chest To Crate Upgrade
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemChestToCrateUpgrade),
                " W ", "WCW", " W ",
                'C', new ItemStack(InitBlocks.blockGiantChest),
                'W', "plankWood"));
        recipeChestToCrateUpgrade = RecipeUtil.lastIRecipe();

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemSmallToMediumCrateUpgrade),
                " W ", "WCW", " W ",
                'C', new ItemStack(InitBlocks.blockGiantChestMedium),
                'W', "plankWood"));
        recipeSmallToMediumCrateUpgrade = RecipeUtil.lastIRecipe();

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMediumToLargeCrateUpgrade),
                " W ", "WCW", " W ",
                'C', new ItemStack(InitBlocks.blockGiantChestLarge),
                'W', "plankWood"));
        recipeMediumToLargeCrateUpgrade = RecipeUtil.lastIRecipe();

        //Disenchanting Lens
        ItemStack crystal = new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal());
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemDisenchantingLens),
                new ItemStack(Blocks.ENCHANTING_TABLE),
                crystal.copy(),
                crystal.copy(),
                crystal.copy(),
                crystal.copy(),
                crystal.copy(),
                crystal.copy(),
                crystal.copy(),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal())));
        recipeDisenchantingLens = RecipeUtil.lastIRecipe();

        //Mining Lens
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMiningLens),
                "DGI", "CLB", "QPE",
                'D', "gemDiamond",
                'G', "ingotGold",
                'I', "ingotIron",
                'C', "coal",
                'L', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()),
                'B', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                'Q', "gemQuartz",
                'P', "gemLapis",
                'E', "gemEmerald"));
        recipeMiningLens = RecipeUtil.lastIRecipe();

        //Killer Lens
        ItemStack enchBook = new ItemStack(Items.ENCHANTED_BOOK);
        Items.ENCHANTED_BOOK.addEnchantment(enchBook, new EnchantmentData(Enchantments.SHARPNESS, 5));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMoreDamageLens),
                new ItemStack(Items.DIAMOND_SWORD),
                new ItemStack(InitItems.itemDamageLens),
                enchBook));
        recipeLensMoreDeath = RecipeUtil.lastIRecipe();

        //Filter
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemFilter),
                "III", "IQI", "III",
                'I', new ItemStack(Blocks.IRON_BARS),
                'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
        recipeFilter = RecipeUtil.lastIRecipe();

        //Crate Keeper
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemCrateKeeper),
                "WIW", "IQI", "WIW",
                'I', "ingotIron",
                'W', "plankWood",
                'Q', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal())));
        recipeCrateKeeper = RecipeUtil.lastIRecipe();

        //Spawner Changer
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemSpawnerChanger),
                "MSM", "SDS", "MSM",
                'M', new ItemStack(Items.MAGMA_CREAM),
                'S', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.SPAWNER_SHARD.ordinal()),
                'D', new ItemStack(InitBlocks.blockCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal())));
        recipeSpawnerChanger = RecipeUtil.lastIRecipe();

        //Laser Wrench
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLaserWrench),
                "C  ", " S ", "  S",
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                'S', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal())));
        recipeLaserWrench = RecipeUtil.lastIRecipe();

        //Rice Stuff
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.PAPER, 3),
                "R  ", " R ", "  R",
                'R', new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.RICE_SLIME.ordinal()),
                " R ", "RBR", " R ",
                'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RICE_DOUGH.ordinal()),
                'B', Items.WATER_BUCKET));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.RICE_SLIME.ordinal()),
                " R ", "RBR", " R ",
                'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RICE_DOUGH.ordinal()),
                'B', new ItemStack(Items.POTIONITEM)));

        //Leaf Blower
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLeafBlower),
                " F", "IP", "IC",
                'F', new ItemStack(Items.FLINT),
                'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                'P', new ItemStack(Blocks.PISTON),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeLeafBlower = RecipeUtil.lastIRecipe();

        //Drill
        ItemStack lightBlueDrill = new ItemStack(InitItems.itemDrill, 1, TheColoredLampColors.LIGHT_BLUE.ordinal());
        GameRegistry.addRecipe(new ShapedOreRecipe(lightBlueDrill.copy(),
                "DDD", "CRC", "III",
                'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DRILL_CORE.ordinal()),
                'I', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal())));
        recipeDrill = RecipeUtil.lastIRecipe();

        for(int i = 0; i < 16; i++){
            if(i != TheColoredLampColors.LIGHT_BLUE.ordinal()){
                GameRegistry.addRecipe(new RecipeKeepDataShapeless(new ItemStack(InitItems.itemDrill, 1, i), new ItemStack(InitItems.itemDrill, 1, Util.WILDCARD), lightBlueDrill.copy(), "dye"+TheColoredLampColors.values()[i].oreName));
                RECIPES_DRILL_COLORING.add(RecipeUtil.lastIRecipe());
            }
        }

        //Drill Core
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DRILL_CORE.ordinal()),
                "ICI", "CRC", "ICI",
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                'I', new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal())));
        recipeDrillCore = RecipeUtil.lastIRecipe();

        //Tele Staff
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemTeleStaff),
                " FE", " S ", "SB ",
                'F', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()),
                'E', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                'S', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()),
                'B', new ItemStack(InitItems.itemBattery, 1, Util.WILDCARD)));
        recipeStaff = RecipeUtil.lastIRecipe();

        //Drill Speed
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeed),
                "ISI", "SRS", "ISI",
                'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                'S', Items.SUGAR,
                'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal())));
        recipeDrillSpeedI = RecipeUtil.lastIRecipe();

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeedII),
                "ISI", "SCS", "ISI",
                'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                'S', Items.SUGAR,
                'C', Items.CAKE));
        recipeDrillSpeedII = RecipeUtil.lastIRecipe();

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeedIII),
                "ISI", "SFS", "ISI",
                'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()),
                'S', Items.SUGAR,
                'F', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal())));
        recipeDrillSpeedIII = RecipeUtil.lastIRecipe();

        //Drill Fortune
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFortune),
                "ISI", "SRS", "ISI",
                'I', Blocks.GLOWSTONE,
                'S', Items.REDSTONE,
                'R', new ItemStack(InitBlocks.blockCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal())));
        recipeDrillFortuneI = RecipeUtil.lastIRecipe();

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFortuneII),
                "ISI", "SRS", "ISI",
                'I', Blocks.GLOWSTONE,
                'S', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.REDSTONE.ordinal()),
                'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
        recipeDrillFortuneII = RecipeUtil.lastIRecipe();

        //Drill Size
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeThreeByThree),
                "DID", "ICI", "DID",
                'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal())));
        recipeDrillThree = RecipeUtil.lastIRecipe();

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFiveByFive),
                "DID", "ICI", "DID",
                'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()),
                'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeDrillFive = RecipeUtil.lastIRecipe();

        //Drill Silk Touch
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSilkTouch),
                "DSD", "SCS", "DSD",
                'D', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.EMERALD.ordinal()),
                'S', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeDrillSilk = RecipeUtil.lastIRecipe();

        //Drill Placing
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeBlockPlacing),
                "CEC", "RAR", "CEC",
                'C', "cobblestone",
                'E', Items.PAPER,
                'A', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal())));
        recipeDrillPlacing = RecipeUtil.lastIRecipe();

        //Battery
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBattery),
                " R ", "ICI", "III",
                'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeBattery = RecipeUtil.lastIRecipe();

        //Double Battery
        GameRegistry.addRecipe(new RecipeKeepDataShaped(new ItemStack(InitItems.itemBatteryDouble), new ItemStack(InitItems.itemBattery),
                " R ", "ICI", "III",
                'R', new ItemStack(InitItems.itemBattery),
                'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeBatteryDouble = RecipeUtil.lastIRecipe();

        //Magnet Ring
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMagnetRing),
                "RIB", "IOI", "BIR",
                'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                'I', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                'B', new ItemStack(Items.DYE, 1, 4),
                'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal())));
        recipeMagnetRing = RecipeUtil.lastIRecipe();

        //Growth Ring
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemGrowthRing),
                "SIS", "IOI", "SIS",
                'S', new ItemStack(Items.WHEAT_SEEDS),
                'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()),
                'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal())));
        recipeGrowthRing = RecipeUtil.lastIRecipe();

        //Water Ring
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemWaterRemovalRing),
                "BIB", "IOI", "BIB",
                'B', new ItemStack(Items.WATER_BUCKET),
                'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()),
                'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal())));
        recipeWaterRing = RecipeUtil.lastIRecipe();

        //Triple Battery
        GameRegistry.addRecipe(new RecipeKeepDataShaped(new ItemStack(InitItems.itemBatteryTriple), new ItemStack(InitItems.itemBatteryDouble),
                " R ", "ICI", "III",
                'R', new ItemStack(InitItems.itemBatteryDouble),
                'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeBatteryTriple = RecipeUtil.lastIRecipe();

        //Quadruple Battery
        GameRegistry.addRecipe(new RecipeKeepDataShaped(new ItemStack(InitItems.itemBatteryQuadruple), new ItemStack(InitItems.itemBatteryTriple),
                " R ", "ICI", "III",
                'R', new ItemStack(InitItems.itemBatteryTriple),
                'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.IRON.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeBatteryQuadruple = RecipeUtil.lastIRecipe();

        //Quintuple Battery
        GameRegistry.addRecipe(new RecipeKeepDataShaped(new ItemStack(InitItems.itemBatteryQuintuple), new ItemStack(InitItems.itemBatteryQuadruple),
                " R ", "ICI", "III",
                'R', new ItemStack(InitItems.itemBatteryQuadruple),
                'I', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeBatteryQuintuple = RecipeUtil.lastIRecipe();

        //Bat Wings
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemWingsOfTheBats),
                "WNW", "WDW", "WNW",
                'W', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BAT_WING.ordinal()),
                'N', new ItemStack(InitBlocks.blockCrystalEmpowered, 1, TheCrystals.DIAMOND.ordinal()),
                'D', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.ENDER_STAR.ordinal())));
        recipeWings = RecipeUtil.lastIRecipe();

        //Quartz
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                new ItemStack(Items.COAL),
                new ItemStack(Items.QUARTZ)));

        //Coil
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                " R ", "RIR", " R ",
                'I', ConfigBoolValues.SUPER_DUPER_HARD_MODE.isEnabled() ? new ItemStack(InitItems.itemMisc, 1, TheMiscItems.ENDER_STAR.ordinal()) : new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                'R', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal())));
        recipeCoil = RecipeUtil.lastIRecipe();

        //Cup
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()),
                "S S", "SCS", "SSS",
                'S', "stone",
                'C', InitItems.itemCoffeeBean));
        recipeCup = RecipeUtil.lastIRecipe();

        //Resonant Rice
        if(!OreDictionary.getOres("nuggetEnderium", false).isEmpty()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemResonantRice),
                    new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), "nuggetEnderium", Items.GUNPOWDER));
        }

        //Advanced Coil
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                "GGG", "GCG", "GGG",
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                'G', "nuggetGold"));
        recipeCoilAdvanced = RecipeUtil.lastIRecipe();

        //Advanced Leaf Blower
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLeafBlowerAdvanced),
                " F", "DP", "DC",
                'F', new ItemStack(Items.FLINT),
                'D', new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                'P', new ItemStack(Blocks.PISTON),
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        recipeLeafBlowerAdvanced = RecipeUtil.lastIRecipe();

        //Phantom Connector
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemPhantomConnector),
                "YE", "EY", "S ",
                'Y', Items.ENDER_EYE,
                'E', Items.ENDER_PEARL,
                'S', "stickWood"));
        recipePhantomConnector = RecipeUtil.lastIRecipe();

        //Player Probe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemPlayerProbe),
                "A A", "AIA", "RHR",
                'A', new ItemStack(Blocks.IRON_BARS),
                'R', new ItemStack(InitItems.itemCrystalEmpowered, 1, TheCrystals.REDSTONE.ordinal()),
                'H', new ItemStack(Items.SKULL, 1, 1),
                'I', new ItemStack(Items.IRON_HELMET)));
        recipePlayerProbe = RecipeUtil.lastIRecipe();

        //Quartz
        GameRegistry.addSmelting(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);

        //Knife
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemKnife),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal())));
        recipeKnife = RecipeUtil.lastIRecipe();

        //Crafter on a Stick
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemCrafterOnAStick),
                new ItemStack(Blocks.CRAFTING_TABLE),
                new ItemStack(Items.SIGN)));

        //Tiny Coal
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_COAL.ordinal()),
                new ItemStack(Items.COAL));
        recipeTinyCoal = RecipeUtil.lastIRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_CHAR.ordinal()),
                new ItemStack(Items.COAL, 1, 1));
        recipeTinyChar = RecipeUtil.lastIRecipe();
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.COAL),
                "CCC", "C C", "CCC",
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_COAL.ordinal())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.COAL, 1, 1),
                "CCC", "C C", "CCC",
                'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.TINY_CHAR.ordinal())));

        //Rice Seeds
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemRiceSeed),
                new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()));

        //Canola Seeds
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemCanolaSeed),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CANOLA.ordinal()));

        //Rings
        initPotionRingRecipes();

        //Ingots from Dusts
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.IRON.ordinal()),
                new ItemStack(Items.IRON_INGOT), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()),
                new ItemStack(Items.GOLD_INGOT), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.DIAMOND.ordinal()),
                new ItemStack(Items.DIAMOND), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.EMERALD.ordinal()),
                new ItemStack(Items.EMERALD), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.LAPIS.ordinal()),
                new ItemStack(Items.DYE, 1, 4), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ_BLACK.ordinal()),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ.ordinal()),
                new ItemStack(Items.QUARTZ), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()),
                new ItemStack(Items.COAL), 1F);

    }

    public static void initPotionRingRecipes(){
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()),
                "IGI", "GDG", "IGI",
                'G', "ingotGold",
                'I', "ingotIron",
                'D', "dustGlowstone"));
        recipeRing = RecipeUtil.lastIRecipe();

        addRingRecipeWithStack(ThePotionRings.SPEED.craftingItem, ThePotionRings.SPEED.ordinal());
        addRingRecipeWithStack(ThePotionRings.HASTE.craftingItem, ThePotionRings.HASTE.ordinal());
        addRingRecipeWithStack(ThePotionRings.STRENGTH.craftingItem, ThePotionRings.STRENGTH.ordinal());
        addRingRecipeWithStack(ThePotionRings.JUMP_BOOST.craftingItem, ThePotionRings.JUMP_BOOST.ordinal());
        addRingRecipeWithStack(ThePotionRings.REGEN.craftingItem, ThePotionRings.REGEN.ordinal());
        addRingRecipeWithStack(ThePotionRings.RESISTANCE.craftingItem, ThePotionRings.RESISTANCE.ordinal());
        addRingRecipeWithStack(ThePotionRings.FIRE_RESISTANCE.craftingItem, ThePotionRings.FIRE_RESISTANCE.ordinal());
        addRingRecipeWithStack(ThePotionRings.WATER_BREATHING.craftingItem, ThePotionRings.WATER_BREATHING.ordinal());
        addRingRecipeWithStack(ThePotionRings.INVISIBILITY.craftingItem, ThePotionRings.INVISIBILITY.ordinal());
        addRingRecipeWithStack(ThePotionRings.NIGHT_VISION.craftingItem, ThePotionRings.NIGHT_VISION.ordinal());
    }

    public static void addRingRecipeWithStack(ItemStack mainStack, int meta){
        ItemStack potion = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potion, PotionTypes.AWKWARD);

        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPotionRing, 1, meta), mainStack, mainStack, mainStack, mainStack, new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()), new ItemStack(Items.NETHER_WART), potion, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        RECIPES_POTION_RINGS.add(RecipeUtil.lastIRecipe());

        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPotionRingAdvanced, 1, meta), new ItemStack(InitItems.itemPotionRing, 1, meta), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.ENDER_STAR.ordinal()));
        RECIPES_POTION_RINGS.add(RecipeUtil.lastIRecipe());
    }
}