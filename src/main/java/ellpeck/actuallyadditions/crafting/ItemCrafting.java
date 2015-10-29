/*
 * This file ("ItemCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.*;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

public class ItemCrafting{

    public static IRecipe recipePhantomConnector;
    public static IRecipe recipeCoil;
    public static IRecipe recipeCoilAdvanced;
    public static IRecipe recipeBook;
    public static IRecipe recipeTinyCoal;
    public static IRecipe recipeTinyChar;
    public static ArrayList<IRecipe> recipesMashedFood = new ArrayList<IRecipe>();
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
    public static ArrayList<IRecipe> recipesPotionRings = new ArrayList<IRecipe>();
    public static IRecipe recipeChestToCrateUpgrade;
    public static IRecipe recipeLaserWrench;
    public static IRecipe recipeDrillCore;

    public static void init(){

        //Booklet
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemLexicon), new ItemStack(InitItems.itemCanolaSeed), new ItemStack(Items.paper)));
        recipeBook = Util.GetRecipes.lastIRecipe();

        //Clearing NBT Storage
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemLaserWrench), new ItemStack(InitItems.itemLaserWrench));
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPhantomConnector), new ItemStack(InitItems.itemPhantomConnector));

        //Chest To Crate Upgrade
        if(ConfigCrafting.CHEST_TO_CRATE_UPGRADE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemChestToCrateUpgrade),
                    "CWC", "WWW", "CWC",
                    'C', new ItemStack(Blocks.chest),
                    'W', "plankWood"));
            recipeChestToCrateUpgrade = Util.GetRecipes.lastIRecipe();
        }

        //Laser Wrench
        if(ConfigCrafting.LASER_WRENCH.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLaserWrench),
                    "C  ", " S ", "  S",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'S', "ingotIron"));
            recipeLaserWrench = Util.GetRecipes.lastIRecipe();
        }

        //Rice Stuff
        if(ConfigCrafting.RICE_GADGETS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3),
                    "R  ", " R ", "  R",
                    'R', new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal())));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.RICE_SLIME.ordinal()),
                    " R ", "RBR", " R ",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RICE_DOUGH.ordinal()),
                    'B', Items.water_bucket));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.RICE_SLIME.ordinal()),
                    " R ", "RBR", " R ",
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RICE_DOUGH.ordinal()),
                    'B', new ItemStack(Items.potionitem)));
        }

        //Leaf Blower
        if(ConfigCrafting.LEAF_BLOWER.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLeafBlower),
                    " F", "IP", "IC",
                    'F', new ItemStack(Items.flint),
                    'I', "ingotIron",
                    'P', new ItemStack(Blocks.piston),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeLeafBlower = Util.GetRecipes.lastIRecipe();
        }

        //Drill
        if(ConfigCrafting.DRILL.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrill),
                    "DDD", "CRC", "III",
                    'D', "gemDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'R', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DRILL_CORE.ordinal()),
                    'I', "blockIron"));
            recipeDrill = Util.GetRecipes.lastIRecipe();
        }

        //Drill Core
        if(ConfigCrafting.DRILL_CORE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DRILL_CORE.ordinal()),
                    "ICI", "CRC", "ICI",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'R', "dustRedstone",
                    'I', "blockIron"));
            recipeDrillCore = Util.GetRecipes.lastIRecipe();
        }

        //Tele Staff
        if(ConfigCrafting.TELE_STAFF.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemTeleStaff),
                    "  E", " S ", "SB ",
                    'E', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                    'S', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()),
                    'B', new ItemStack(InitItems.itemBattery, 1, Util.WILDCARD)));
            recipeStaff = Util.GetRecipes.lastIRecipe();
        }

        //Drill Speed
        if(ConfigCrafting.DRILL_SPEED.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeed),
                    "ISI", "SRS", "ISI",
                    'I', "ingotIron",
                    'S', Items.sugar,
                    'R', "dustRedstone"));
            recipeDrillSpeedI = Util.GetRecipes.lastIRecipe();

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeedII),
                    "ISI", "SCS", "ISI",
                    'I', "ingotIron",
                    'S', Items.sugar,
                    'C', Items.cake));
            recipeDrillSpeedII = Util.GetRecipes.lastIRecipe();

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeedIII),
                    "ISI", "SFS", "ISI",
                    'I', "ingotIron",
                    'S', Items.sugar,
                    'F', "gemDiamond"));
            recipeDrillSpeedIII = Util.GetRecipes.lastIRecipe();
        }

        //Drill Fortune
        if(ConfigCrafting.DRILL_FORTUNE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFortune),
                    "ISI", "SRS", "ISI",
                    'I', Blocks.glowstone,
                    'S', Items.redstone,
                    'R', Blocks.diamond_block));
            recipeDrillFortuneI = Util.GetRecipes.lastIRecipe();

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFortuneII),
                    "ISI", "SRS", "ISI",
                    'I', Blocks.glowstone,
                    'S', Items.redstone,
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
            recipeDrillFortuneII = Util.GetRecipes.lastIRecipe();
        }

        //Drill Size
        if(ConfigCrafting.DRILL_SIZE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeThreeByThree),
                    "DID", "ICI", "DID",
                    'I', "ingotIron",
                    'D', "gemDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal())));
            recipeDrillThree = Util.GetRecipes.lastIRecipe();

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFiveByFive),
                    "DID", "ICI", "DID",
                    'I', "ingotIron",
                    'D', "gemDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeDrillFive = Util.GetRecipes.lastIRecipe();
        }

        //Drill Silk Touch
        if(ConfigCrafting.DRILL_SILK_TOUCH.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSilkTouch),
                    "DSD", "SCS", "DSD",
                    'D', "gemEmerald",
                    'S', "gemDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeDrillSilk = Util.GetRecipes.lastIRecipe();
        }

        //Drill Placing
        if(ConfigCrafting.DRILL_PLACING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeBlockPlacing),
                    "CEC", "RAR", "CEC",
                    'C', "cobblestone",
                    'E', Items.paper,
                    'A', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'R', "ingotIron"));
            recipeDrillPlacing = Util.GetRecipes.lastIRecipe();
        }

        //Battery
        if(ConfigCrafting.BATTERY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBattery),
                    " R ", "ICI", "III",
                    'R', "dustRedstone",
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeBattery = Util.GetRecipes.lastIRecipe();
        }

        //Double Battery
        if(ConfigCrafting.DOUBLE_BATTERY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBatteryDouble),
                    " R ", "ICI", "III",
                    'R', new ItemStack(InitItems.itemBattery),
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeBatteryDouble = Util.GetRecipes.lastIRecipe();
        }

        //Magnet Ring
        if(ConfigCrafting.MAGNET_RING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMagnetRing),
                    "RIB", "IOI", "BIR",
                    'R', "dustRedstone",
                    'I', "ingotIron",
                    'B', new ItemStack(Items.dye, 1, 4),
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal())));
            recipeMagnetRing = Util.GetRecipes.lastIRecipe();
        }

        //Growth Ring
        if(ConfigCrafting.GROWTH_RING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemGrowthRing),
                    "SIS", "IOI", "SIS",
                    'S', new ItemStack(Items.wheat_seeds),
                    'I', "ingotIron",
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal())));
            recipeGrowthRing = Util.GetRecipes.lastIRecipe();
        }

        //Water Ring
        if(ConfigCrafting.WATER_RING.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemWaterRemovalRing),
                    "BIB", "IOI", "BIB",
                    'B', new ItemStack(Items.water_bucket),
                    'I', "ingotIron",
                    'O', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal())));
            recipeWaterRing = Util.GetRecipes.lastIRecipe();
        }

        //Triple Battery
        if(ConfigCrafting.TRIPLE_BATTERY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBatteryTriple),
                    " R ", "ICI", "III",
                    'R', new ItemStack(InitItems.itemBatteryDouble),
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeBatteryTriple = Util.GetRecipes.lastIRecipe();
        }

        //Quadruple Battery
        if(ConfigCrafting.QUADRUPLE_BATTERY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBatteryQuadruple),
                    " R ", "ICI", "III",
                    'R', new ItemStack(InitItems.itemBatteryTriple),
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeBatteryQuadruple = Util.GetRecipes.lastIRecipe();
        }

        //Quintuple Battery
        if(ConfigCrafting.QUINTUPLE_BATTERY.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBatteryQuintuple),
                    " R ", "ICI", "III",
                    'R', new ItemStack(InitItems.itemBatteryQuadruple),
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeBatteryQuintuple = Util.GetRecipes.lastIRecipe();
        }

        //Bat Wings
        if(ConfigCrafting.BAT_WINGS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemWingsOfTheBats),
                    "WNW", "WDW", "WNW",
                    'W', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BAT_WING.ordinal()),
                    'N', "blockDiamond",
                    'D', new ItemStack(Items.nether_star)));
            recipeWings = Util.GetRecipes.lastIRecipe();
        }

        //Quartz
        if(ConfigCrafting.QUARTZ.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    new ItemStack(Items.coal),
                    new ItemStack(Items.quartz)));
        }

        //Coil
        if(ConfigCrafting.COIL.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    " R ", "RIR", " R ",
                    'I', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    'R', "dustRedstone"));
            recipeCoil = Util.GetRecipes.lastIRecipe();
        }

        //Cup
        if(ConfigCrafting.CUP.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()),
                    "S S", "SCS", "SSS",
                    'S', "stone",
                    'C', InitItems.itemCoffeeBean));
            recipeCup = Util.GetRecipes.lastIRecipe();
        }

        //Resonant Rice
        if(ConfigCrafting.RESONANT_RICE.isEnabled() && !OreDictionary.getOres("nuggetEnderium", false).isEmpty()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemResonantRice),
                    new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), "nuggetEnderium", Items.gunpowder));
        }

        //Advanced Coil
        if(ConfigCrafting.ADV_COIL.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    " G ", "GCG", " G ",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'G', "ingotGold"));
            recipeCoilAdvanced = Util.GetRecipes.lastIRecipe();
        }

        //Ender Pearl
        GameRegistry.addRecipe(new ItemStack(Items.ender_pearl),
                "XXX", "XXX", "XXX",
                'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.PEARL_SHARD.ordinal()));

        //Emerald
        GameRegistry.addRecipe(new ItemStack(Items.emerald),
                "XXX", "XXX", "XXX",
                'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.EMERALD_SHARD.ordinal()));

        //Advanced Leaf Blower
        if(ConfigCrafting.LEAF_BLOWER_ADVANCED.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLeafBlowerAdvanced),
                    " F", "DP", "DC",
                    'F', new ItemStack(Items.flint),
                    'D', "gemDiamond",
                    'P', new ItemStack(Blocks.piston),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
            recipeLeafBlowerAdvanced = Util.GetRecipes.lastIRecipe();
        }

        //Phantom Connector
        if(ConfigCrafting.PHANTOM_CONNECTOR.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemPhantomConnector),
                    "YE", "EY", "S ",
                    'Y', Items.ender_eye,
                    'E', Items.ender_pearl,
                    'S', "stickWood"));
            recipePhantomConnector = Util.GetRecipes.lastIRecipe();
        }

        //Quartz
        GameRegistry.addSmelting(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);

        //Knife
        if(ConfigCrafting.KNIFE.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemKnife),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal())));
            recipeKnife = Util.GetRecipes.lastIRecipe();
        }

        //Crafter on a Stick
        if(ConfigCrafting.STICK_CRAFTER.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemCrafterOnAStick),
                    new ItemStack(Blocks.crafting_table),
                    new ItemStack(Items.sign)));
        }

        //Tiny Coal
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_COAL.ordinal()),
                new ItemStack(Items.coal));
        recipeTinyCoal = Util.GetRecipes.lastIRecipe();
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_CHAR.ordinal()),
                new ItemStack(Items.coal, 1, 1));
        recipeTinyChar = Util.GetRecipes.lastIRecipe();

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
                new ItemStack(Items.iron_ingot), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()),
                new ItemStack(Items.gold_ingot), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.DIAMOND.ordinal()),
                new ItemStack(Items.diamond), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.EMERALD.ordinal()),
                new ItemStack(Items.emerald), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.LAPIS.ordinal()),
                new ItemStack(Items.dye, 1, 4), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ_BLACK.ordinal()),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.QUARTZ.ordinal()),
                new ItemStack(Items.quartz), 1F);
        GameRegistry.addSmelting(new ItemStack(InitItems.itemDust, 1, TheDusts.COAL.ordinal()),
                new ItemStack(Items.coal), 1F);

    }

    public static void initPotionRingRecipes(){
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()),
                "IGI", "GDG", "IGI",
                'G', "ingotGold",
                'I', "ingotIron",
                'D', "dustGlowstone"));
        recipeRing = Util.GetRecipes.lastIRecipe();

        if(ConfigCrafting.RING_SPEED.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.SPEED.craftingItem, ThePotionRings.SPEED.ordinal());
        }
        if(ConfigCrafting.RING_HASTE.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.HASTE.craftingItem, ThePotionRings.HASTE.ordinal());
        }
        if(ConfigCrafting.RING_STRENGTH.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.STRENGTH.craftingItem, ThePotionRings.STRENGTH.ordinal());
        }
        if(ConfigCrafting.RING_JUMP_BOOST.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.JUMP_BOOST.craftingItem, ThePotionRings.JUMP_BOOST.ordinal());
        }
        if(ConfigCrafting.RING_REGEN.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.REGEN.craftingItem, ThePotionRings.REGEN.ordinal());
        }
        if(ConfigCrafting.RING_RESISTANCE.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.RESISTANCE.craftingItem, ThePotionRings.RESISTANCE.ordinal());
        }
        if(ConfigCrafting.RING_FIRE_RESISTANCE.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.FIRE_RESISTANCE.craftingItem, ThePotionRings.FIRE_RESISTANCE.ordinal());
        }
        if(ConfigCrafting.RING_WATER_BREATHING.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.WATER_BREATHING.craftingItem, ThePotionRings.WATER_BREATHING.ordinal());
        }
        if(ConfigCrafting.RING_INVISIBILITY.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.INVISIBILITY.craftingItem, ThePotionRings.INVISIBILITY.ordinal());
        }
        if(ConfigCrafting.RING_NIGHT_VISION.isEnabled()){
            addRingRecipeWithStack(ThePotionRings.NIGHT_VISION.craftingItem, ThePotionRings.NIGHT_VISION.ordinal());
        }
    }

    public static void addRingRecipeWithStack(ItemStack mainStack, int meta){
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPotionRing, 1, meta), mainStack, mainStack, mainStack, mainStack, new ItemStack(Blocks.diamond_block), new ItemStack(Items.nether_wart), new ItemStack(Items.potionitem), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        recipesPotionRings.add(Util.GetRecipes.lastIRecipe());
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPotionRingAdvanced, 1, meta), new ItemStack(InitItems.itemPotionRing, 1, meta), new ItemStack(Items.nether_star), new ItemStack(Items.nether_star));
        recipesPotionRings.add(Util.GetRecipes.lastIRecipe());
    }

    public static void initMashedFoodRecipes(){
        if(ConfigCrafting.MASHED_FOOD.isEnabled()){
            for(Object item : Item.itemRegistry){
                if(item instanceof ItemFood || item instanceof IPlantable || item instanceof IGrowable){
                    if(!isBlacklisted(item)){
                        ItemStack ingredient = new ItemStack((Item)item, 1, Util.WILDCARD);
                        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.MASHED_FOOD.ordinal()), ingredient, ingredient, ingredient, ingredient, new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD));
                        recipesMashedFood.add(Util.GetRecipes.lastIRecipe());
                    }
                }
            }
        }
    }

    private static boolean isBlacklisted(Object item){
        for(String except : ConfigValues.mashedFoodCraftingExceptions){
            if(Item.itemRegistry.getNameForObject(item).equals(except)){
                return true;
            }
        }
        return false;
    }
}
