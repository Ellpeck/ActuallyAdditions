package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.lootmodifier.BatLootModifier;
import de.ellpeck.actuallyadditions.mod.lootmodifier.DungeonLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class GlobalLootModifierGenerator extends GlobalLootModifierProvider {
	public GlobalLootModifierGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider, ActuallyAdditions.MODID);
	}

	@Override
	protected void start() {
		this.add("bat_loot", new BatLootModifier(
				new LootItemCondition[]{
						LootItemKilledByPlayerCondition.killedByPlayer().build(),
						LootTableIdCondition.builder(EntityType.BAT.getDefaultLootTable().location()).build()
				}));
		this.add("dungeon_loot", new DungeonLootModifier(
				new LootItemCondition[]{
						AnyOfCondition.anyOf(
								LootTableIdCondition.builder(BuiltInLootTables.SIMPLE_DUNGEON.location()),
								LootTableIdCondition.builder(BuiltInLootTables.ABANDONED_MINESHAFT.location()),
								LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_WEAPONSMITH.location()),
								LootTableIdCondition.builder(BuiltInLootTables.STRONGHOLD_LIBRARY.location()),
								LootTableIdCondition.builder(BuiltInLootTables.IGLOO_CHEST.location()),
								LootTableIdCondition.builder(BuiltInLootTables.DESERT_PYRAMID.location()),
								LootTableIdCondition.builder(BuiltInLootTables.NETHER_BRIDGE.location()),
								LootTableIdCondition.builder(BuiltInLootTables.END_CITY_TREASURE.location()),
								LootTableIdCondition.builder(BuiltInLootTables.WOODLAND_MANSION.location())
						).build()
				}));
	}
}
