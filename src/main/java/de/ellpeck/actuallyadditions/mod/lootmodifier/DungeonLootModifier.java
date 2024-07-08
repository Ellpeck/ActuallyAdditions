package de.ellpeck.actuallyadditions.mod.lootmodifier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class DungeonLootModifier extends LootModifier {
	public static final Supplier<MapCodec<DungeonLootModifier>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, DungeonLootModifier::new)));

	public DungeonLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		RandomSource random = context.getRandom();
		if (CommonConfig.Other.DUNGEON_LOOT.get()) {
			ResourceLocation lootTable = context.getQueriedLootTableId();
			boolean addCrystals = false;
			boolean addDrillCore = false;
			boolean addQuartz = false;
			boolean addBatWings = false;
			if (BuiltInLootTables.SIMPLE_DUNGEON.equals(lootTable)) {
				addCrystals = true;
				addDrillCore = true;
				addQuartz = true;
			} else if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(lootTable)) {
				addCrystals = true;
				addDrillCore = true;
			} else if (BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(lootTable)) {
				addDrillCore = true;
				addQuartz = true;
			} else if (BuiltInLootTables.STRONGHOLD_LIBRARY.equals(lootTable)) {
				addBatWings = true;
			} else if (BuiltInLootTables.IGLOO_CHEST.equals(lootTable)) {
				addBatWings = true;
			} else if (BuiltInLootTables.DESERT_PYRAMID.equals(lootTable)) {
				addDrillCore = true;
				addBatWings = true;
			} else if (BuiltInLootTables.NETHER_BRIDGE.equals(lootTable)) {
				addBatWings = true;
				addCrystals = true;
				addDrillCore = true;
			} else if (BuiltInLootTables.END_CITY_TREASURE.equals(lootTable)) {
				addBatWings = true;
				addCrystals = true;
				addDrillCore = true;
				addQuartz = true;
			} else if (BuiltInLootTables.WOODLAND_MANSION.equals(lootTable)) {
				addBatWings = true;
				addCrystals = true;
				addDrillCore = true;
				addQuartz = true;
			}

			if (addCrystals) {
//				LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(0, TheCrystals.values().length - 1));
//				LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 3));
//				LootFunction[] functions = new LootFunction[] { damage, amount };
//				pool.addEntry(new LootEntryItem(InitItems.itemCrystal, 20, 0, functions, noCondition, ActuallyAdditions.MODID + ":crystalItems"));
//				pool.addEntry(new LootEntryItem(Item.getItemFromBlock(InitBlocks.blockCrystal), 3, 0, functions, noCondition, ActuallyAdditions.MODID + ":crystalBlocks"));
				if (random.nextInt(5) == 0) {
					int count = random.nextInt(3) + 1;
					Item crystal = getRandomItem(random, ActuallyTags.Items.CRYSTALS, ActuallyItems.RESTONIA_CRYSTAL.get());
					generatedLoot.add(new ItemStack(crystal, count));
				}

				if (random.nextInt(15) == 0) {
					int count = random.nextInt(3) + 1;
					Item crystal = getRandomItem(random, ActuallyTags.Items.CRYSTAL_BLOCKS, ActuallyBlocks.RESTONIA_CRYSTAL.getItem());
					generatedLoot.add(new ItemStack(crystal, count));
				}
			}
			if (addDrillCore) {
				System.out.println("Deciding to add drill core or not");
				if (random.nextInt(10) == 0) {
					generatedLoot.add(new ItemStack(ActuallyItems.DRILL_CORE.get()));
				}
//				LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.DRILL_CORE.ordinal()));
//				pool.addEntry(new LootEntryItem(InitItems.itemMisc, 5, 0, new LootFunction[] { damage }, noCondition, ActuallyAdditions.MODID + ":drillCore"));
			}
			if (addQuartz) {
				if (random.nextInt(5) == 0) {
					int count = random.nextInt(5) + 1;
					generatedLoot.add(new ItemStack(ActuallyItems.BLACK_QUARTZ.get(), count));
				}
//				LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.QUARTZ.ordinal()));
//				LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 5));
//				pool.addEntry(new LootEntryItem(InitItems.itemMisc, 20, 0, new LootFunction[] { damage, amount }, noCondition, ActuallyAdditions.MODID + ":quartz"));
			}
			if (addBatWings) {
				if (random.nextInt(10) == 0) {
					int count = random.nextInt(2) + 1;
					generatedLoot.add(new ItemStack(ActuallyItems.BATS_WING.get(), count));
				}
//				LootFunction damage = new SetMetadata(noCondition, new RandomValueRange(TheMiscItems.BAT_WING.ordinal()));
//				LootFunction amount = new SetCount(noCondition, new RandomValueRange(1, 2));
//				pool.addEntry(new LootEntryItem(InitItems.itemMisc, 5, 0, new LootFunction[] { damage, amount }, noCondition, ActuallyAdditions.MODID + ":batWings"));
			}
		}
		return generatedLoot;
	}

	private Item getRandomItem(RandomSource random, TagKey<Item> tagKey, Item defaultItem) {
		HolderSet.Named<Item> holderSet = BuiltInRegistries.ITEM.getTag(tagKey).orElse(null);
		if (holderSet != null) {
			Holder<Item> itemHolder = holderSet.getRandomElement(random).orElse(null);
			if (itemHolder != null) {
				return itemHolder.value();
			}
		}
		return defaultItem;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return ActuallyLootModifiers.DUNGEON_LOOT.get();
	}
}
