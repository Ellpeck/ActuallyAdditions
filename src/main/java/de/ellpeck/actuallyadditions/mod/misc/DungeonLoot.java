/*
 * This file ("DungeonLoot.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;

//TODO Fix dungeon loot (oh god)
public class DungeonLoot{

    public static void init(){
        if(ConfigBoolValues.DUNGEON_LOOT.isEnabled()){
            ModUtil.LOGGER.info("Initializing Dungeon Loot...");

            /*ChestGenHooks dungeon = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
            ChestGenHooks mineshaft = ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR);
            ChestGenHooks blacksmith = ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH);

            for(int i = 0; i < TheCrystals.values().length; i++){
                WeightedRandomChestContent item = new WeightedRandomChestContent(InitItems.itemCrystal, i, 2, 4, 5);
                WeightedRandomChestContent block = new WeightedRandomChestContent(Item.getItemFromBlock(InitBlocks.blockCrystal), i, 1, 3, 1);
                dungeon.addItem(item);
                dungeon.addItem(block);
                mineshaft.addItem(item);
                mineshaft.addItem(block);
            }
            WeightedRandomChestContent drillCore = new WeightedRandomChestContent(InitItems.itemMisc, TheMiscItems.DRILL_CORE.ordinal(), 1, 1, 3);
            dungeon.addItem(drillCore);
            mineshaft.addItem(drillCore);
            blacksmith.addItem(drillCore);

            WeightedRandomChestContent quartz = new WeightedRandomChestContent(InitItems.itemMisc, TheMiscItems.QUARTZ.ordinal(), 3, 4, 30);
            dungeon.addItem(quartz);
            blacksmith.addItem(quartz);*/
        }
    }
}
