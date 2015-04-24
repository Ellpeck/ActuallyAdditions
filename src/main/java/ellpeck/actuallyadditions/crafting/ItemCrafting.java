package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.metalists.ThePotionRings;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ItemCrafting{

    public static void init(){

       //Leaf Blower
        if(ConfigCrafting.LEAF_BLOWER.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemLeafBlower),
                    " F", "IP", "IC",
                    'F', new ItemStack(Items.flint),
                    'I', "ingotIron",
                    'P', new ItemStack(Blocks.piston),
                    'C', TheMiscItems.COIL_ADVANCED.getOredictName()));

        //Coil
        if(ConfigCrafting.COIL.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.COIL.ordinal()),
                    " R ", "RIR", " R ",
                    'I', "ingotIron",
                    'R', "dustRedstone"));

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
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemCrafterOnAStick),
                    new ItemStack(Blocks.crafting_table),
                    new ItemStack(Items.sign),
                    new ItemStack(Items.slime_ball));

        //SpeedUpgrade
        if(ConfigCrafting.SPEED_UPGRADE.isEnabled())
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.itemSpeedUpgrade),
                    "RGR", "GUG", "RGR",
                    'U', TheMiscItems.COIL.getOredictName(),
                    'R', "dustRedstone",
                    'G', "ingotGold"));

        //Mashed Food
        if(ConfigCrafting.MASHED_FOOD.isEnabled())
            initMashedFoodRecipes();

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
        if(ConfigCrafting.RING_SATURATION.isEnabled()) addRingRecipeWithStack(ThePotionRings.SATURATION.craftingItem, ThePotionRings.SATURATION.ordinal());

    }

    public static void addRingRecipeWithStack(ItemStack mainStack, int meta){
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPotionRing, 1, meta), mainStack, mainStack, mainStack, mainStack, new ItemStack(Blocks.diamond_block), new ItemStack(Items.nether_wart), new ItemStack(Items.potionitem), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.RING.ordinal()));
        GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemPotionRingAdvanced, 1, meta), new ItemStack(InitItems.itemPotionRing, 1, meta), new ItemStack(Items.nether_star));
    }

    public static void initMashedFoodRecipes(){
        for(Object nextIterator : Item.itemRegistry){
            if(nextIterator instanceof ItemFood){
                ItemStack ingredient = new ItemStack((Item)nextIterator, 1, Util.WILDCARD);
                GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.MASHED_FOOD.ordinal()), ingredient, ingredient, ingredient, ingredient, new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD));
            }
        }
    }
}
