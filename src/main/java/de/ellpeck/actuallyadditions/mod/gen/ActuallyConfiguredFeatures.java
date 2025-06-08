package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.base.AACrops;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class ActuallyConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLACK_QUARTZ = createKey("ore_black_quartz");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CANOLA_PATCH = createKey("canola_patch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLAX_PATCH = createKey("flax_patch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COFFEE_PATCH = createKey("coffee_patch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> RICE_PATCH = createKey("rice_patch");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneRuleTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		RuleTest deepslateRuleTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		List<OreConfiguration.TargetBlockState> list = List.of(
				OreConfiguration.target(stoneRuleTest, ActuallyBlocks.BLACK_QUARTZ_ORE.get().defaultBlockState()),
				OreConfiguration.target(deepslateRuleTest, ActuallyBlocks.BLACK_QUARTZ_ORE.get().defaultBlockState())
		);
		FeatureUtils.register(context, ORE_BLACK_QUARTZ, Feature.ORE, new OreConfiguration(list, 6));
		FeatureUtils.register(context, CANOLA_PATCH, Feature.RANDOM_PATCH, plantPatch(ActuallyBlocks.CANOLA.get(), 10));
		FeatureUtils.register(context, FLAX_PATCH, Feature.RANDOM_PATCH, plantPatch(ActuallyBlocks.FLAX.get(), 8));
		FeatureUtils.register(context, COFFEE_PATCH, Feature.RANDOM_PATCH, plantPatch(ActuallyBlocks.COFFEE.get(), 6));
		FeatureUtils.register(context, RICE_PATCH, Feature.RANDOM_PATCH, waterPlantPatch(ActuallyBlocks.RICE.get(), 20));
	}

	private static RandomPatchConfiguration plantPatch(Block crop, int tries) {
		BlockStateProvider stateProvider = BlockStateProvider.simple(crop.defaultBlockState().setValue(AACrops.AGE, AACrops.MAX_AGE).setValue(AACrops.PERSISTENT, true));
		return FeatureUtils.simpleRandomPatchConfiguration(
				tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(stateProvider))
		);
	}

	private static RandomPatchConfiguration waterPlantPatch(Block crop, int tries) {
		BlockStateProvider stateProvider = BlockStateProvider.simple(crop.defaultBlockState().setValue(AACrops.AGE, AACrops.MAX_AGE).setValue(AACrops.PERSISTENT, true));
		return new RandomPatchConfiguration(tries, 8, 0, PlacementUtils.inlinePlaced(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(stateProvider),
				BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
						BlockPredicate.ONLY_IN_AIR_PREDICATE,
						BlockPredicate.anyOf(
								BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
								BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
								BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
								BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER)
						)
				)))
		);
	}

	private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ActuallyAdditions.modLoc(name));
	}
}
