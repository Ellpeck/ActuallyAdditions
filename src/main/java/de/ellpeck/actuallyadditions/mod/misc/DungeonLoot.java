/*
 * This file ("DungeonLoot.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheJams;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetDamage;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DungeonLoot{

    public static final ResourceLocation JAM_HOUSE = new ResourceLocation(ModUtil.MOD_ID, "jam_house");
    public static final ResourceLocation LUSH_CAVES = new ResourceLocation(ModUtil.MOD_ID, "lush_caves");
    public static final ResourceLocation ENGINEER_HOUSE = new ResourceLocation(ModUtil.MOD_ID, "engineer_house");

    public DungeonLoot(){
        LootTableList.register(JAM_HOUSE);
        LootTableList.register(LUSH_CAVES);
        LootTableList.register(ENGINEER_HOUSE);
    }

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event){
        if(ConfigBoolValues.DUNGEON_LOOT.isEnabled() && event.getName() != null && event.getTable() != null){
            LootCondition[] noCondition = new LootCondition[0];

            LootPool pool = event.getTable().getPool("main");
            if(pool == null){
                pool = new LootPool(new LootEntry[0], noCondition, new RandomValueRange(5, 10), new RandomValueRange(0), "main");
                event.getTable().addPool(pool);
            }

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
                addBook = true;
            }
            else if(LootTableList.CHESTS_STRONGHOLD_LIBRARY.equals(event.getName())){
                addBatWings = true;
                addBook = true;
            }
            else if(LootTableList.CHESTS_IGLOO_CHEST.equals(event.getName())){
                addBatWings = true;
            }
            else if(LootTableList.CHESTS_DESERT_PYRAMID.equals(event.getName())){
                addDrillCore = true;
                addBatWings = true;
            }
            else if(LootTableList.CHESTS_NETHER_BRIDGE.equals(event.getName())){
                addBatWings = true;
                addCrystals = true;
                addDrillCore = true;
                addBook = true;
            }
            else if(LootTableList.CHESTS_END_CITY_TREASURE.equals(event.getName())){
                addBatWings = true;
                addCrystals = true;
                addDrillCore = true;
                addQuartz = true;
            }
            else if(LootTableList.CHESTS_WOODLAND_MANSION.equals(event.getName())){
                addBatWings = true;
                addCrystals = true;
                addDrillCore = true;
                addQuartz = true;
                addBook = true;
            }
            else if(JAM_HOUSE.equals(event.getName())){
                LootFunction jamDamage = new SetMetadata(noCondition, new RandomValueRange(0, TheJams.values().length-1));
                LootFunction jamAmount = new SetCount(noCondition, new RandomValueRange(3, 5));
                pool.addEntry(new LootEntryItem(InitItems.itemJams, 2, 0, new LootFunction[]{jamDamage, jamAmount}, noCondition, ModUtil.MOD_ID+":jams"));

                LootFunction glassAmount = new SetCount(noCondition, new RandomValueRange(2));
                pool.addEntry(new LootEntryItem(Items.GLASS_BOTTLE, 1, 0, new LootFunction[]{glassAmount}, noCondition, ModUtil.MOD_ID+":bottles"));
            }
            else if(LUSH_CAVES.equals(event.getName())){
                addBook = true;
                addQuartz = true;
                addBatWings = true;
                addCrystals = true;

                pool.addEntry(new LootEntryItem(Items.BOOK, 50, 0, new LootFunction[0], noCondition, ModUtil.MOD_ID+":book"));

                LootFunction bonesAmount = new SetCount(noCondition, new RandomValueRange(1, 12));
                pool.addEntry(new LootEntryItem(Items.BONE, 100, 0, new LootFunction[]{bonesAmount}, noCondition, ModUtil.MOD_ID+":bones"));

                Item[] aiots = new Item[]{InitItems.woodenPaxel, InitItems.stonePaxel, InitItems.quartzPaxel, InitItems.itemPaxelCrystalBlack, InitItems.itemPaxelCrystalWhite};
                for(int i = 0; i < aiots.length; i++){
                    LootFunction damage = new SetDamage(noCondition, new RandomValueRange(0F, 0.25F));
                    pool.addEntry(new LootEntryItem(aiots[i], 30-i*5, 0, new LootFunction[]{damage}, noCondition, ModUtil.MOD_ID+":aiot"+i));
                }

                Item[] armor = new Item[]{Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS};
                for(int i = 0; i < armor.length; i++){
                    LootFunction damage = new SetDamage(noCondition, new RandomValueRange(0F, 0.75F));
                    pool.addEntry(new LootEntryItem(armor[i], 50, 0, new LootFunction[]{damage}, noCondition, ModUtil.MOD_ID+":armor"+i));
                }
            }
            else if(ENGINEER_HOUSE.equals(event.getName())){
                addBook = true;
                addQuartz = true;
                addBatWings = true;
                addCrystals = true;
                addDrillCore = true;

                LootFunction woodCaseAmount = new SetCount(noCondition, new RandomValueRange(3, 10));
                LootFunction woodCaseDamage = new SetMetadata(noCondition, new RandomValueRange(TheMiscBlocks.WOOD_CASING.ordinal()));
                pool.addEntry(new LootEntryItem(Item.getItemFromBlock(InitBlocks.blockMisc), 60, 0, new LootFunction[]{woodCaseAmount, woodCaseDamage}, noCondition, ModUtil.MOD_ID+":woodenCase"));

                LootFunction ironCaseAmount = new SetCount(noCondition, new RandomValueRange(1, 3));
                LootFunction ironCaseDamage = new SetMetadata(noCondition, new RandomValueRange(TheMiscBlocks.IRON_CASING.ordinal()));
                pool.addEntry(new LootEntryItem(Item.getItemFromBlock(InitBlocks.blockMisc), 40, 0, new LootFunction[]{ironCaseAmount, ironCaseDamage}, noCondition, ModUtil.MOD_ID+":ironCase"));
            }

            if(addCrystals){
                LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(0, TheCrystals.values().length-1));
                LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 3));
                LootFunction[] functions = new LootFunction[]{damage, amount};

                pool.addEntry(new LootEntryItem(InitItems.itemCrystal, 20, 0, functions, noCondition, ModUtil.MOD_ID+":crystalItems"));
                pool.addEntry(new LootEntryItem(Item.getItemFromBlock(InitBlocks.blockCrystal), 3, 0, functions, noCondition, ModUtil.MOD_ID+":crystalBlocks"));
            }

            if(addDrillCore){
                LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.DRILL_CORE.ordinal()));
                pool.addEntry(new LootEntryItem(InitItems.itemMisc, 5, 0, new LootFunction[]{damage}, noCondition, ModUtil.MOD_ID+":drillCore"));
            }

            if(addQuartz){
                LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.QUARTZ.ordinal()));
                LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 5));
                pool.addEntry(new LootEntryItem(InitItems.itemMisc, 20, 0, new LootFunction[]{damage, amount}, noCondition, ModUtil.MOD_ID+":quartz"));
            }

            if(addBatWings){
                LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.BAT_WING.ordinal()));
                LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 2));
                pool.addEntry(new LootEntryItem(InitItems.itemMisc, 5, 0, new LootFunction[]{damage, amount}, noCondition, ModUtil.MOD_ID+":batWings"));
            }

            if(addBook){
                LootFunction amount = new SetCount(noCondition, new RandomValueRange(1));
                pool.addEntry(new LootEntryItem(InitItems.itemBooklet, 10, 0, new LootFunction[]{amount}, noCondition, ModUtil.MOD_ID+":booklet"));
            }
        }
    }
}
