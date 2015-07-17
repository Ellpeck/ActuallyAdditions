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
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ItemCrafting{

    public static void init(){

        //Rice Stuff
        if(ConfigCrafting.RICE_GADGETS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3),
                    "RRR",
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
        if(ConfigCrafting.LEAF_BLOWER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLeafBlower),
                    " F", "IP", "IC",
                    'F', new ItemStack(Items.flint),
                    'I', "ingotIron",
                    'P', new ItemStack(Blocks.piston),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));

        //Drill
        if(ConfigCrafting.DRILL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrill),
                    "DDD", "CRC", "III",
                    'D', "gemDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    'R', "dustRedstone",
                    'I', "blockIron"));

        //Tele Staff
        if(ConfigCrafting.TELE_STAFF.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemTeleStaff),
                    "  E", " S ", "SB ",
                    'E', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDERPEARL_BLOCK.ordinal()),
                    'S', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal()),
                    'B', new ItemStack(InitItems.itemBattery, 1, Util.WILDCARD)));

        //Drill Speed
        if(ConfigCrafting.DRILL_SPEED.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeed),
                    "ISI", "SRS", "ISI",
                    'I', "ingotIron",
                    'S', Items.sugar,
                    'R', "dustRedstone"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeedII),
                    "ISI", "SCS", "ISI",
                    'I', "ingotIron",
                    'S', Items.sugar,
                    'C', Items.cake));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSpeedIII),
                    "ISI", "SFS", "ISI",
                    'I', "ingotIron",
                    'S', Items.sugar,
                    'F', "gemDiamond"));
        }

        //Drill Fortune
        if(ConfigCrafting.DRILL_FORTUNE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFortune),
                    "ISI", "SRS", "ISI",
                    'I', Blocks.glowstone,
                    'S', Items.redstone,
                    'R', Blocks.diamond_block));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFortuneII),
                    "ISI", "SRS", "ISI",
                    'I', Blocks.glowstone,
                    'S', Items.redstone,
                    'R', new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ENDER_CASING.ordinal())));
        }

        //Drill Size
        if(ConfigCrafting.DRILL_SIZE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeThreeByThree),
                    "DID", "ICI", "DID",
                    'I', "ingotIron",
                    'D', "gemDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal())));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFiveByFive),
                    "DID", "ICI", "DID",
                    'I', "ingotIron",
                    'D', "gemDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));
        }

        //Drill Silk Touch
        if(ConfigCrafting.DRILL_SILK_TOUCH.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSilkTouch),
                    "DSD", "SCS", "DSD",
                    'D', "gemEmerald",
                    'S', "gemDiamond",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));

        //Drill Placing
        if(ConfigCrafting.DRILL_PLACING.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeBlockPlacing),
                    "CEC", "RAR", "CEC",
                    'C', "cobblestone",
                    'E', Items.ender_pearl,
                    'A', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'R', "ingotIron"));

        //Battery
        if(ConfigCrafting.BATTERY.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBattery),
                    " R ", "ICI", "III",
                    'R', "dustRedstone",
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));

        //Double Battery
        if(ConfigCrafting.DOUBLE_BATTERY.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBatteryDouble),
                    " R ", "ICI", "III",
                    'R', new ItemStack(InitItems.itemBattery),
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));

        //Triple Battery
        if(ConfigCrafting.TRIPLE_BATTERY.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBatteryTriple),
                    " R ", "ICI", "III",
                    'R', new ItemStack(InitItems.itemBatteryDouble),
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));

        //Quadruple Battery
        if(ConfigCrafting.QUADRUPLE_BATTERY.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBatteryQuadruple),
                    " R ", "ICI", "III",
                    'R', new ItemStack(InitItems.itemBatteryTriple),
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));

        //Quintuple Battery
        if(ConfigCrafting.QUINTUPLE_BATTERY.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBatteryQuintuple),
                    " R ", "ICI", "III",
                    'R', new ItemStack(InitItems.itemBatteryQuadruple),
                    'I', "ingotIron",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));

        //Bat Wings
        if(ConfigCrafting.BAT_WINGS.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemWingsOfTheBats),
                    "WNW", "WDW", "WNW",
                    'W', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BAT_WING.ordinal()),
                    'D', "blockDiamond",
                    'N', new ItemStack(Items.nether_star)));

        //Quartz
        if(ConfigCrafting.QUARTZ.isEnabled())
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    new ItemStack(Items.coal),
                    new ItemStack(Items.quartz)));

        //Coil
        if(ConfigCrafting.COIL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    " R ", "RIR", " R ",
                    'I', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()),
                    'R', "dustRedstone"));

        //Cup
        if(ConfigCrafting.CUP.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()),
                    "S S", "SCS", "SSS",
                    'S', "stone",
                    'C', InitItems.itemCoffeeBean));

        //Resonant Rice
        if(ConfigCrafting.RESONANT_RICE.isEnabled() && !OreDictionary.getOres("nuggetEnderium", false).isEmpty())
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemResonantRice),
                    new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), "nuggetEnderium", Items.gunpowder));

        //Advanced Coil
        if(ConfigCrafting.ADV_COIL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    " G ", "GCG", " G ",
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    'G', "ingotGold"));

        //Ender Pearl
        GameRegistry.addRecipe(new ItemStack(Items.ender_pearl),
                "XXX", "XXX", "XXX",
                'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.PEARL_SHARD.ordinal()));

        //Emerald
        GameRegistry.addRecipe(new ItemStack(Items.emerald),
                "XXX", "XXX", "XXX",
                'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.EMERALD_SHARD.ordinal()));

        //Advanced Leaf Blower
        if(ConfigCrafting.LEAF_BLOWER_ADVANCED.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLeafBlowerAdvanced),
                    " F", "DP", "DC",
                    'F', new ItemStack(Items.flint),
                    'D', "gemDiamond",
                    'P', new ItemStack(Blocks.piston),
                    'C', new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal())));

        //Phantom Connector
        if(ConfigCrafting.PHANTOM_CONNECTOR.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemPhantomConnector),
                    "YE", "EY", "S ",
                    'Y', Items.ender_eye,
                    'E', Items.ender_pearl,
                    'S', "stickWood"));

        //Quartz
        GameRegistry.addSmelting(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()),
                new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);

        //Knife
        if(ConfigCrafting.KNIFE.isEnabled())
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemKnife),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal())));

        //Crafter on a Stick
        if(ConfigCrafting.STICK_CRAFTER.isEnabled())
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemCrafterOnAStick),
                    new ItemStack(Blocks.crafting_table),
                    new ItemStack(Items.sign),
                    "slimeball"));

        //Tiny Coal
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_COAL.ordinal()),
                new ItemStack(Items.coal));
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.TINY_CHAR.ordinal()),
                new ItemStack(Items.coal, 1, 1));

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

        if(ConfigCrafting.RING_SPEED.isEnabled()) addRingRecipeWithStack(ThePotionRings.SPEED.craftingItem, ThePotionRings.SPEED.ordinal());
        if(ConfigCrafting.RING_HASTE.isEnabled()) addRingRecipeWithStack(ThePotionRings.HASTE.craftingItem, ThePotionRings.HASTE.ordinal());
        if(ConfigCrafting.RING_STRENGTH.isEnabled()) addRingRecipeWithStack(ThePotionRings.STRENGTH.craftingItem, ThePotionRings.STRENGTH.ordinal());
        if(ConfigCrafting.RING_JUMP_BOOST.isEnabled()) addRingRecipeWithStack(ThePotionRings.JUMP_BOOST.craftingItem, ThePotionRings.JUMP_BOOST.ordinal());
        if(ConfigCrafting.RING_REGEN.isEnabled()) addRingRecipeWithStack(ThePotionRings.REGEN.craftingItem, ThePotionRings.REGEN.ordinal());
        if(ConfigCrafting.RING_RESISTANCE.isEnabled()) addRingRecipeWithStack(ThePotionRings.RESISTANCE.craftingItem, ThePotionRings.RESISTANCE.ordinal());
        if(ConfigCrafting.RING_FIRE_RESISTANCE.isEnabled()) addRingRecipeWithStack(ThePotionRings.FIRE_RESISTANCE.craftingItem, ThePotionRings.FIRE_RESISTANCE.ordinal());
        if(ConfigCrafting.RING_WATER_BREATHING.isEnabled()) addRingRecipeWithStack(ThePotionRings.WATER_BREATHING.craftingItem, ThePotionRings.WATER_BREATHING.ordinal());
        if(ConfigCrafting.RING_INVISIBILITY.isEnabled()) addRingRecipeWithStack(ThePotionRings.INVISIBILITY.craftingItem, ThePotionRings.INVISIBILITY.ordinal());
        if(ConfigCrafting.RING_NIGHT_VISION.isEnabled()) addRingRecipeWithStack(ThePotionRings.NIGHT_VISION.craftingItem, ThePotionRings.NIGHT_VISION.ordinal());
    }

    public static void addRingRecipeWithStack(ItemStack mainStack, int meta){
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPotionRing, 1, meta), mainStack, mainStack, mainStack, mainStack, new ItemStack(Blocks.diamond_block), new ItemStack(Items.nether_wart), new ItemStack(Items.potionitem), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPotionRingAdvanced, 1, meta), new ItemStack(InitItems.itemPotionRing, 1, meta), new ItemStack(Items.nether_star), new ItemStack(Items.nether_star));
    }

    public static void initMashedFoodRecipes(){
        if(ConfigCrafting.MASHED_FOOD.isEnabled()){
            for(Object item : Item.itemRegistry){
                if(item instanceof ItemFood || item instanceof IPlantable || item instanceof IGrowable){
                    if(!isBlacklisted(item)){
                        ItemStack ingredient = new ItemStack((Item)item, 1, Util.WILDCARD);
                        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.MASHED_FOOD.ordinal()), ingredient, ingredient, ingredient, ingredient, new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD));
                    }
                }
            }
        }
    }

    private static boolean isBlacklisted(Object item){
        for(String except : ConfigValues.mashedFoodCraftingExceptions){
            if(Item.itemRegistry.getNameForObject(item).equals(except)) return true;
        }
        return false;
    }
}
