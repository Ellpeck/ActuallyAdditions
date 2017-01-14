/*
 * This file ("InitOreDict.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.ore;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheDusts;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class InitOreDict{

    public static void init(){
        ModUtil.LOGGER.info("Initializing OreDictionary Entries...");

        //Vanilla Ores
        addOre(Items.COAL, "coal");

        //Ores for Pulverizers etc.
        addOre(InitItems.itemDust, TheDusts.IRON.ordinal(), "dustIron");
        addOre(InitItems.itemDust, TheDusts.GOLD.ordinal(), "dustGold");
        addOre(InitItems.itemDust, TheDusts.DIAMOND.ordinal(), "dustDiamond");
        addOre(InitItems.itemDust, TheDusts.EMERALD.ordinal(), "dustEmerald");
        addOre(InitItems.itemDust, TheDusts.LAPIS.ordinal(), "dustLapis");
        addOre(InitItems.itemDust, TheDusts.QUARTZ.ordinal(), "dustQuartz");
        addOre(InitItems.itemDust, TheDusts.QUARTZ.ordinal(), "dustNetherQuartz");
        addOre(InitItems.itemDust, TheDusts.COAL.ordinal(), "dustCoal");
        addOre(InitItems.itemDust, TheDusts.QUARTZ_BLACK.ordinal(), "dustQuartzBlack");
        addOre(InitBlocks.blockMisc, TheMiscBlocks.ORE_QUARTZ.ordinal(), "oreQuartzBlack");
        addOre(InitItems.itemMisc, TheMiscItems.QUARTZ.ordinal(), "gemQuartzBlack");

        //For Thermal Expansion Machine that "grows crops"
        addOre(InitItems.itemCanolaSeed, "seedCanola");
        addOre(InitItems.itemMisc, TheMiscItems.CANOLA.ordinal(), "cropCanola");
        addOre(InitItems.itemRiceSeed, "seedRice");
        addOre(InitItems.itemFoods, TheFoods.RICE.ordinal(), "cropRice");
        addOre(InitItems.itemFlaxSeed, "seedFlax");
        addOre(Items.STRING, "cropFlax");
        addOre(InitItems.itemCoffeeSeed, "seedCoffee");
        addOre(InitItems.itemCoffeeBean, "cropCoffee");

        //For Crafting
        addOre(InitItems.itemMisc, TheMiscItems.RICE_SLIME.ordinal(), "slimeball");
        addOre(InitBlocks.blockMisc, TheMiscBlocks.CHARCOAL_BLOCK.ordinal(), "blockCharcoal");
        addOre(InitItems.itemMisc, TheMiscItems.BLACK_DYE.ordinal(), "dyeBlack");
    }

    private static void addOre(Item item, int meta, String name){
        addOre(new ItemStack(item, 1, meta), name);
    }

    private static void addOre(Item item, String name){
        addOre(item, 0, name);
    }

    private static void addOre(Block block, String name){
        addOre(block, 0, name);
    }

    private static void addOre(Block block, int meta, String name){
        addOre(new ItemStack(block, 1, meta), name);
    }

    private static void addOre(ItemStack stack, String name){
        OreDictionary.registerOre(name, stack);
    }
}
