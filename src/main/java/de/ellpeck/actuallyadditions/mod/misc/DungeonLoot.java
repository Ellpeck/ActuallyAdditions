/*
 * This file ("DungeonLoot.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DungeonLoot{

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event){
        if(ConfigBoolValues.DUNGEON_LOOT.isEnabled() && event.getName() != null && event.getTable() != null){
            boolean addCrystals = false;
            boolean addDrillCore = false;
            boolean addQuartz = false;
            boolean addBatWings = false;
            boolean addBook = false;

            if(LootTableList.CHESTS_SIMPLE_DUNGEON.equals(event.getName())){
                addCrystals = true;
                addDrillCore = true;
                addQuartz = true;
            }
            else if(LootTableList.CHESTS_ABANDONED_MINESHAFT.equals(event.getName())){
                addCrystals = true;
                addDrillCore = true;
            }
            else if(LootTableList.CHESTS_VILLAGE_BLACKSMITH.equals(event.getName())){
                addDrillCore = true;
                addQuartz = true;
                addBatWings = true;
                addBook = true;
            }
            else if(LootTableList.CHESTS_STRONGHOLD_LIBRARY.equals(event.getName())){
                addBatWings = true;
                addBook = true;
            }
            else if(LootTableList.CHESTS_IGLOO_CHEST.equals(event.getName())){
                addBatWings = true;
            }

            LootPool pool = event.getTable().getPool("main");
            if(pool != null){
                LootCondition[] noCondition = new LootCondition[0];

                if(addCrystals){
                    LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(0, TheCrystals.values().length-1));
                    LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 3));
                    LootFunction[] functions = new LootFunction[]{damage, amount};

                    pool.addEntry(new LootEntryItem(InitItems.itemCrystal, 50, 0, functions, noCondition, ModUtil.MOD_ID+":crystalItems"));
                    pool.addEntry(new LootEntryItem(Item.getItemFromBlock(InitBlocks.blockCrystal), 5, 0, functions, noCondition, ModUtil.MOD_ID+":crystalBlocks"));
                }

                if(addDrillCore){
                    LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.DRILL_CORE.ordinal()));
                    pool.addEntry(new LootEntryItem(InitItems.itemMisc, 10, 0, new LootFunction[]{damage}, noCondition, ModUtil.MOD_ID+":drillCore"));
                }

                if(addQuartz){
                    LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.QUARTZ.ordinal()));
                    LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 10));
                    pool.addEntry(new LootEntryItem(InitItems.itemMisc, 80, 0, new LootFunction[]{damage, amount}, noCondition, ModUtil.MOD_ID+":quartz"));
                }

                if(addBatWings){
                    LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.BAT_WING.ordinal()));
                    LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 2));
                    pool.addEntry(new LootEntryItem(InitItems.itemMisc, 5, 0, new LootFunction[]{damage, amount}, noCondition, ModUtil.MOD_ID+":batWings"));
                }

                if(addBook){
                    LootFunction amount = new SetCount(noCondition, new RandomValueRange(1));
                    pool.addEntry(new LootEntryItem(InitItems.itemBooklet, 100, 0, new LootFunction[]{amount}, noCondition, ModUtil.MOD_ID+":booklet"));
                }
            }
        }
    }
}
