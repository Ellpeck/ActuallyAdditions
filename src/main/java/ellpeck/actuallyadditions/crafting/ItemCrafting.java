package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.blocks.metalists.TheMiscBlocks;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemCrafting{

    public static void init(){

        //Leaf Blower
        if(ConfigValues.enableLeafBlowerRecipe)
            GameRegistry.addRecipe(new ItemStack(InitItems.itemLeafBlower),
                    " F", "IP", "IR",
                    'F', new ItemStack(Items.flint),
                    'I', new ItemStack(Items.iron_ingot),
                    'P', new ItemStack(Blocks.piston),
                    'R', new ItemStack(Items.redstone));

        //Ender Pearl
        GameRegistry.addRecipe(new ItemStack(Items.ender_pearl),
                "XXX", "XXX", "XXX",
                'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.PEARL_SHARD.ordinal()));

        //Emerald
        GameRegistry.addRecipe(new ItemStack(Items.emerald),
                "XXX", "XXX", "XXX",
                'X', new ItemStack(InitItems.itemSpecialDrop, 1, TheSpecialDrops.EMERALD_SHARD.ordinal()));

        //Advanced Leaf Blower
        if(ConfigValues.enableLeafBlowerAdvancedRecipe)
            GameRegistry.addRecipe(new ItemStack(InitItems.itemLeafBlowerAdvanced),
                    " F", "DP", "DR",
                    'F', new ItemStack(Items.flint),
                    'D', new ItemStack(Items.diamond),
                    'P', new ItemStack(Blocks.piston),
                    'R', new ItemStack(Items.redstone));

        //Quartz
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.QUARTZ.ordinal()])
            GameRegistry.addSmelting(new ItemStack(InitBlocks.blockMisc, 1, TheMiscBlocks.ORE_QUARTZ.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 1F);

        //Knife
        if(ConfigValues.enableKnifeRecipe)
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemKnife),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()));

        //Crafter on a Stick
        if(ConfigValues.enableCrafterRecipe)
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemCrafterOnAStick),
                    new ItemStack(Blocks.crafting_table),
                    new ItemStack(Items.sign),
                    new ItemStack(Items.slime_ball));

        //Mashed Food
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.MASHED_FOOD.ordinal()])
            initMashedFoodRecipes();

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

    public static void initMashedFoodRecipes(){
        for(Object nextIterator : Item.itemRegistry){
            if(nextIterator instanceof ItemFood){
                ItemStack ingredient = new ItemStack((Item)nextIterator, 1, Util.WILDCARD);
                GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 8, TheMiscItems.MASHED_FOOD.ordinal()), ingredient, ingredient, ingredient, ingredient, new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD));
            }
        }
    }
}
