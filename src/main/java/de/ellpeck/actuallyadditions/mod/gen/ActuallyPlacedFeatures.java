package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public class ActuallyPlacedFeatures {
	public static final ResourceKey<PlacedFeature> PLACED_ORE_BLACK_QUARTZ = createKey("ore_black_quartz");
	public static final ResourceKey<PlacedFeature> CANOLA_PATCH = createKey("canola_patch");
	public static final ResourceKey<PlacedFeature> FLAX_PATCH = createKey("flax_patch");
	public static final ResourceKey<PlacedFeature> COFFEE_PATCH = createKey("coffee_patch");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

		PlacementUtils.register(context, PLACED_ORE_BLACK_QUARTZ, holdergetter.getOrThrow(ActuallyConfiguredFeatures.ORE_BLACK_QUARTZ),
				commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-25), VerticalAnchor.absolute(45))));

		PlacementUtils.register(
				context,
				CANOLA_PATCH,
				holdergetter.getOrThrow(ActuallyConfiguredFeatures.CANOLA_PATCH),
				RarityFilter.onAverageOnceEvery(8),
				NoiseThresholdCountPlacement.of(-0.8, 5, 10),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);
		PlacementUtils.register(
				context,
				FLAX_PATCH,
				holdergetter.getOrThrow(ActuallyConfiguredFeatures.FLAX_PATCH),
				RarityFilter.onAverageOnceEvery(8),
				NoiseThresholdCountPlacement.of(-0.8, 5, 10),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);
		PlacementUtils.register(
				context,
				COFFEE_PATCH,
				holdergetter.getOrThrow(ActuallyConfiguredFeatures.COFFEE_PATCH),
				RarityFilter.onAverageOnceEvery(8),
				NoiseThresholdCountPlacement.of(-0.8, 5, 10),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);
	}

	private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier1) {
		return List.of(modifier, InSquarePlacement.spread(), modifier1, BiomeFilter.biome());
	}

	private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
		return orePlacement(CountPlacement.of(count), modifier);
	}

	private static ResourceKey<PlacedFeature> createKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, ActuallyAdditions.modLoc(name));
	}
}
