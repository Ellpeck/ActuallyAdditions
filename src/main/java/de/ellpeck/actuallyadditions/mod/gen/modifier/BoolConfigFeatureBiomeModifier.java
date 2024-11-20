package de.ellpeck.actuallyadditions.mod.gen.modifier;

import com.mojang.serialization.MapCodec;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.gen.ActuallyBiomeModifiers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeGenerationSettingsBuilder;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record BoolConfigFeatureBiomeModifier(HolderSet<Biome> biomes, HolderSet<PlacedFeature> features, GenerationStep.Decoration step,
                                             String boolConfig) implements BiomeModifier {

	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD && checkConfig() && this.biomes.contains(biome)) {
			BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
			this.features.forEach(holder -> generationSettings.addFeature(this.step, holder));
		}
	}

	private boolean checkConfig() {
		switch (boolConfig) {
			case "generateQuartz":
				return CommonConfig.Worldgen.GENERATE_QUARTZ.get();
			case "generateCanola":
				return CommonConfig.Worldgen.GENERATE_CANOLA.get();
			case "generateFlax":
				return CommonConfig.Worldgen.GENERATE_FLAX.get();
			case "generateCoffee":
				return CommonConfig.Worldgen.GENERATE_COFFEE.get();
			default:
				return true;
		}
	}

	@Override
	public MapCodec<? extends BiomeModifier> codec() {
		return ActuallyBiomeModifiers.BOOL_CONFIG_MODIFIER.get();
	}
}