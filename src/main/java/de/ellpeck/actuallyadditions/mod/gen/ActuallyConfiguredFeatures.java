package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ActuallyConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLACK_QUARTZ = FeatureUtils.createKey("actuallyadditions:ore_black_quartz");
//	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLACK_QUARTZ_BURIED = FeatureUtils.createKey("actuallyadditions:ore_black_quartz_buried");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneRuleTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		List<OreConfiguration.TargetBlockState> list = List.of(
				OreConfiguration.target(stoneRuleTest, ActuallyBlocks.BLACK_QUARTZ_ORE.get().defaultBlockState())
		);
		FeatureUtils.register(context, ORE_BLACK_QUARTZ, Feature.ORE, new OreConfiguration(list, 6));

	}
}
