/*
 * This file ("DungeonLoot.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc;

import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheCrystals;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class DungeonLoot{

    public static void init(){
        if(ConfigBoolValues.DUNGEON_LOOT.isEnabled()){
            ModUtil.LOGGER.info("Initializing Dungeon Loot...");

            ChestGenHooks dungeon = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
            ChestGenHooks mineshaft = ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR);
            ChestGenHooks blacksmith = ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH);

            for(int i = 0; i < TheCrystals.values().length; i++){
                WeightedRandomChestContent item = new WeightedRandomChestContent(new ItemStack(InitItems.itemCrystal, 1, i), 2, 4, 5);
                WeightedRandomChestContent block = new WeightedRandomChestContent(new ItemStack(InitBlocks.blockCrystal, 1, i), 1, 3, 1);
                dungeon.addItem(item);
                dungeon.addItem(block);
                mineshaft.addItem(item);
                mineshaft.addItem(block);
            }
            WeightedRandomChestContent drillCore = new WeightedRandomChestContent(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.DRILL_CORE.ordinal()), 1, 1, 3);
            dungeon.addItem(drillCore);
            mineshaft.addItem(drillCore);
            blacksmith.addItem(drillCore);

            WeightedRandomChestContent quartz = new WeightedRandomChestContent(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.QUARTZ.ordinal()), 3, 4, 30);
            dungeon.addItem(quartz);
            blacksmith.addItem(quartz);
        }
    }
}
