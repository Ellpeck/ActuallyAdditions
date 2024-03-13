package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.lootmodifier.BatLootModifier;
import de.ellpeck.actuallyadditions.mod.lootmodifier.DungeonLootModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public class GlobalLootModifierGenerator extends GlobalLootModifierProvider {
	public GlobalLootModifierGenerator(PackOutput packOutput) {
		super(packOutput, ActuallyAdditions.MODID);
	}

	@Override
	protected void start() {
		this.add("bat_loot", new BatLootModifier(
				new LootItemCondition[]{
						LootItemKilledByPlayerCondition.killedByPlayer().build(),
						LootTableIdCondition.builder(EntityType.BAT.getDefaultLootTable()).build()
				}));
		this.add("dungeon_loot", new DungeonLootModifier(
				new LootItemCondition[]{
						AnyOfCondition.anyOf(
								LootTableIdCondition.builder(BuiltInLootTables.SIMPLE_DUNGEON),
								LootTableIdCondition.builder(BuiltInLootTables.ABANDONED_MINESHAFT),
								LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_WEAPONSMITH),
								LootTableIdCondition.builder(BuiltInLootTables.STRONGHOLD_LIBRARY),
								LootTableIdCondition.builder(BuiltInLootTables.IGLOO_CHEST),
								LootTableIdCondition.builder(BuiltInLootTables.DESERT_PYRAMID),
								LootTableIdCondition.builder(BuiltInLootTables.NETHER_BRIDGE),
								LootTableIdCondition.builder(BuiltInLootTables.END_CITY_TREASURE),
								LootTableIdCondition.builder(BuiltInLootTables.WOODLAND_MANSION)
						).build()
				}));
	}
}
