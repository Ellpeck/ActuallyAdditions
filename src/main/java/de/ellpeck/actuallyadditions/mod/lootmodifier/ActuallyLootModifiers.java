package de.ellpeck.actuallyadditions.mod.lootmodifier;

import com.mojang.serialization.MapCodec;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ActuallyLootModifiers {
	private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ActuallyAdditions.MODID);

	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> BAT_LOOT = GLM.register("bat_loot", BatLootModifier.CODEC);
	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> DUNGEON_LOOT = GLM.register("dungeon_loot", DungeonLootModifier.CODEC);

	public static void init(IEventBus evt) {
		GLM.register(evt);
	}
}
