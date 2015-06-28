package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.*;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ItemCrafting{

    public static void init(){

        //Rice Stuff
        if(ConfigCrafting.RICE_GADGETS.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3),
                    "RRR",
                    'R', TheFoods.RICE.getOredictName()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.RICE_SLIME.ordinal()),
                    " R ", "RBR", " R ",
                    'R', TheMiscItems.RICE_DOUGH.getOredictName(),
                    'B', Items.water_bucket));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.RICE_SLIME.ordinal()),
                    " R ", "RBR", " R ",
                    'R', TheMiscItems.RICE_DOUGH.getOredictName(),
                    'B', new ItemStack(Items.potionitem)));
        }

        //Leaf Blower
        if(ConfigCrafting.LEAF_BLOWER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLeafBlower),
                    " F", "IP", "IC",
                    'F', new ItemStack(Items.flint),
                    'I', "ingotIron",
                    'P', new ItemStack(Blocks.piston),
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName()));

        //Drill
        if(ConfigCrafting.DRILL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrill),
                    "DDD", "CRC", "III",
                    'D', "gemDiamond",
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName(),
                    'R', "dustRedstone",
                    'I', "blockIron"));

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
                    'R', TheMiscBlocks.ENDER_CASING.getOredictName()));
        }

        //Drill Size
        if(ConfigCrafting.DRILL_SIZE.isEnabled()){
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeThreeByThree),
                    "DID", "ICI", "DID",
                    'I', "ingotIron",
                    'D', "gemDiamond",
                    'C', TheMiscItems.COIL.getOredictName()));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeFiveByFive),
                    "DID", "ICI", "DID",
                    'I', "ingotIron",
                    'D', "gemDiamond",
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName()));
        }

        //Drill Silk Touch
        if(ConfigCrafting.DRILL_SILK_TOUCH.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeSilkTouch),
                    "DSD", "SCS", "DSD",
                    'D', "gemEmerald",
                    'S', "gemDiamond",
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName()));

        //Drill Placing
        if(ConfigCrafting.DRILL_PLACING.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemDrillUpgradeBlockPlacing),
                    "CEC", "RAR", "CEC",
                    'C', "cobblestone",
                    'E', Items.ender_pearl,
                    'A', TheMiscItems.COIL.getOredictName(),
                    'R', "ingotIron"));

        //Battery
        if(ConfigCrafting.BATTERY.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemBattery),
                    " R ", "ICI", "III",
                    'R', "dustRedstone",
                    'I', "ingotIron",
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName()));

        //Coil
        if(ConfigCrafting.COIL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    " R ", "RIR", " R ",
                    'I', "ingotIron",
                    'R', "dustRedstone"));

        //Cup
        if(ConfigCrafting.CUP.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()),
                    "S S", "SCS", "SSS",
                    'S', "stone",
                    'C', ((INameableItem)InitItems.itemCoffeeBean).getOredictName()));

        //Paxels
        if(ConfigCrafting.PAXELS.isEnabled()){
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.woodenPaxel),
                    new ItemStack(Items.wooden_axe),
                    new ItemStack(Items.wooden_pickaxe),
                    new ItemStack(Items.wooden_shovel),
                    new ItemStack(Items.wooden_sword),
                    new ItemStack(Items.wooden_hoe)));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.stonePaxel),
                    new ItemStack(Items.stone_axe),
                    new ItemStack(Items.stone_pickaxe),
                    new ItemStack(Items.stone_shovel),
                    new ItemStack(Items.stone_sword),
                    new ItemStack(Items.stone_hoe)));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.ironPaxel),
                    new ItemStack(Items.iron_axe),
                    new ItemStack(Items.iron_pickaxe),
                    new ItemStack(Items.iron_shovel),
                    new ItemStack(Items.iron_sword),
                    new ItemStack(Items.iron_hoe)));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.goldPaxel),
                    new ItemStack(Items.golden_axe),
                    new ItemStack(Items.golden_pickaxe),
                    new ItemStack(Items.golden_shovel),
                    new ItemStack(Items.golden_sword),
                    new ItemStack(Items.golden_hoe)));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.diamondPaxel),
                    new ItemStack(Items.diamond_axe),
                    new ItemStack(Items.diamond_pickaxe),
                    new ItemStack(Items.diamond_shovel),
                    new ItemStack(Items.diamond_sword),
                    new ItemStack(Items.diamond_hoe)));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.emeraldPaxel),
                    new ItemStack(InitItems.itemAxeEmerald),
                    new ItemStack(InitItems.itemPickaxeEmerald),
                    new ItemStack(InitItems.itemSwordEmerald),
                    new ItemStack(InitItems.itemShovelEmerald),
                    new ItemStack(InitItems.itemHoeEmerald)));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.obsidianPaxel),
                    new ItemStack(InitItems.itemAxeObsidian),
                    new ItemStack(InitItems.itemPickaxeObsidian),
                    new ItemStack(InitItems.itemSwordObsidian),
                    new ItemStack(InitItems.itemShovelObsidian),
                    new ItemStack(InitItems.itemHoeObsidian)));
        }

        //Resonant Rice
        if(ConfigCrafting.RESONANT_RICE.isEnabled())
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.itemResonantRice),
                    TheFoods.RICE.getOredictName(), "nuggetEnderium", Items.gunpowder));

        //Advanced Coil
        if(ConfigCrafting.ADV_COIL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL_ADVANCED.ordinal()),
                    " G ", "GCG", " G ",
                    'C', TheMiscItems.COIL.getOredictName(),
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
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName()));

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
                    TheMiscItems.KNIFE_BLADE.getOredictName(),
                    TheMiscItems.KNIFE_HANDLE.getOredictName()));

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
            for(Object nextIterator : Item.itemRegistry){
                if(nextIterator instanceof ItemFood || nextIterator instanceof IPlantable || nextIterator instanceof IGrowable){
                    ItemStack ingredient = new ItemStack((Item)nextIterator, 1, Util.WILDCARD);
                    GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.MASHED_FOOD.ordinal()), ingredient, ingredient, ingredient, ingredient, new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD));
                }
            }
        }
    }
}
