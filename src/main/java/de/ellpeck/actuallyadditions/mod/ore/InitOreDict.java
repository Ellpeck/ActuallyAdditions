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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

// TODO: [port][change] migrate to TAGS
@Deprecated
public final class InitOreDict {

    public static void init() {
        ActuallyAdditions.LOGGER.info("Initializing OreDictionary Entries...");

        //Vanilla Ores
        addOre(Items.COAL, "coal");

        //Ores for Pulverizers etc.
        //        addOre(InitItems.itemDust.get(), TheDusts.IRON.ordinal(), "dustIron");
        //        addOre(InitItems.itemDust.get(), TheDusts.GOLD.ordinal(), "dustGold");
        //        addOre(InitItems.itemDust.get(), TheDusts.DIAMOND.ordinal(), "dustDiamond");
        //        addOre(InitItems.itemDust.get(), TheDusts.EMERALD.ordinal(), "dustEmerald");
        //        addOre(InitItems.itemDust.get(), TheDusts.LAPIS.ordinal(), "dustLapis");
        //        addOre(InitItems.itemDust.get(), TheDusts.QUARTZ.ordinal(), "dustQuartz");
        //        addOre(InitItems.itemDust.get(), TheDusts.QUARTZ.ordinal(), "dustNetherQuartz");
        //        addOre(InitItems.itemDust.get(), TheDusts.COAL.ordinal(), "dustCoal");
        //        addOre(InitItems.itemDust.get(), TheDusts.QUARTZ_BLACK.ordinal(), "dustQuartzBlack");
        //        addOre(ActuallyBlocks.blockMisc.get(), TheMiscBlocks.ORE_QUARTZ.ordinal(), "oreQuartzBlack");
        //        addOre(InitItems.itemMisc.get(), TheMiscItems.QUARTZ.ordinal(), "gemQuartzBlack");
        //
        //        //For Thermal Expansion Machine that "grows crops"
        //        addOre(InitItems.itemCanolaSeed.get(), "seedCanola");
        //        addOre(InitItems.itemMisc.get(), TheMiscItems.CANOLA.ordinal(), "cropCanola");
        //        addOre(InitItems.itemRiceSeed.get(), "seedRice");
        //        addOre(InitItems.itemFoods.get(), TheFoods.RICE.ordinal(), "cropRice");
        //        addOre(InitItems.itemFlaxSeed.get(), "seedFlax");
        //        addOre(Items.STRING, "cropFlax");
        //        addOre(InitItems.itemCoffeeSeed.get(), "seedCoffee");
        //        addOre(InitItems.itemCoffeeBean.get(), "cropCoffee");
        //
        //        //For Crafting
        //        addOre(InitItems.itemMisc.get(), TheMiscItems.RICE_SLIME.ordinal(), "slimeball");
        //        addOre(ActuallyBlocks.blockMisc.get(), TheMiscBlocks.CHARCOAL_BLOCK.ordinal(), "blockCharcoal");
        //        addOre(ActuallyBlocks.blockMisc.get(), TheMiscBlocks.QUARTZ.ordinal(), "blockQuartzBlack");
        //        addOre(InitItems.itemMisc.get(), TheMiscItems.BLACK_DYE.ordinal(), "dyeBlack");
        //        addOre(InitItems.itemMisc.get(), TheMiscItems.BLACK_DYE.ordinal(), "dye");
    }

    private static void addOre(Item item, int meta, String name) {
        //        addOre(new ItemStack(item, 1, meta), name);
    }

    private static void addOre(Item item, String name) {
        //        addOre(item, 0, name);
    }

    private static void addOre(Block block, int meta, String name) {
        //        addOre(new ItemStack(block, 1, meta), name);
    }

    private static void addOre(ItemStack stack, String name) {
        //        OreDictionary.registerOre(name, stack);
    }
}
