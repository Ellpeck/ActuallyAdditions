package de.ellpeck.actuallyadditions.mod.gen.village;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.mod.AASounds;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ActuallyVillagers {
	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, ActuallyAdditions.MODID);
	public static final Supplier<VillagerProfession> ENGINEER = VILLAGER_PROFESSIONS.register("engineer", () -> new VillagerProfession("engineer", holder -> holder.value().equals(ActuallyPOITypes.ENGINEER.get()), holder -> holder.value().equals(ActuallyPOITypes.ENGINEER.get()), ImmutableSet.of(), ImmutableSet.of(), AASounds.VILLAGER_WORK_ENGINEER.get()));

	public static void init(IEventBus bus) {
		VILLAGER_PROFESSIONS.register(bus);
	}
}
