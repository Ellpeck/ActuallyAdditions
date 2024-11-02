package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class DataMapGenerator extends DataMapProvider {
	public DataMapGenerator(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather() {
		final float SEEDS = 0.3F;
		final float CROPS = 0.65F;

		final var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
		compostables.add(ActuallyItems.RICE_SEEDS, new Compostable(SEEDS), false);
		compostables.add(ActuallyItems.COFFEE_BEANS, new Compostable(SEEDS), false);
		compostables.add(ActuallyItems.CANOLA_SEEDS, new Compostable(SEEDS), false);
		compostables.add(ActuallyItems.FLAX_SEEDS, new Compostable(SEEDS), false);

		compostables.add(ActuallyItems.RICE, new Compostable(CROPS), false);
		compostables.add(ActuallyItems.COFFEE_BEANS, new Compostable(CROPS), false);
		compostables.add(ActuallyItems.CANOLA, new Compostable(CROPS), false);
		compostables.add(ActuallyItems.FLAX_SEEDS, new Compostable(CROPS), false);

		final var furnaceFuels = builder(NeoForgeDataMaps.FURNACE_FUELS);
		furnaceFuels.add(ActuallyItems.TINY_COAL, new FurnaceFuel(200), false);
		furnaceFuels.add(ActuallyItems.TINY_CHARCOAL, new FurnaceFuel(200), false);
	}
}
