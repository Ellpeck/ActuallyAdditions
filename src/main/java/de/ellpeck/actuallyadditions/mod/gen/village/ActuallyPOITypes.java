package de.ellpeck.actuallyadditions.mod.gen.village;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ActuallyPOITypes {
	public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, ActuallyAdditions.MODID);

	public static DeferredHolder<PoiType, PoiType> ENGINEER = POI_TYPES.register("engineer", () -> new PoiType(ImmutableSet.copyOf(ActuallyBlocks.COFFEE_MACHINE.get().getStateDefinition().getPossibleStates()), 1, 1));

	public static void init(IEventBus bus) {
		POI_TYPES.register(bus);
	}
}
