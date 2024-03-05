package de.ellpeck.actuallyadditions.mod.gen;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.gen.modifier.BoolConfigFeatureBiomeModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ActuallyBiomeModifiers {
	protected static final ResourceKey<BiomeModifier> ADD_BLACK_QUARTZ_ORE_MODIFIER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(ActuallyAdditions.MODID, "add_black_quartz"));

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
		HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);

		HolderSet.Named<Biome> overworldHolder = biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD);

		context.register(ADD_BLACK_QUARTZ_ORE_MODIFIER, new BoolConfigFeatureBiomeModifier(
				overworldHolder,
				HolderSet.direct(placedGetter.getOrThrow(ActuallyPlacedFeatures.PLACED_ORE_BLACK_QUARTZ)),
				GenerationStep.Decoration.UNDERGROUND_ORES, "generateQuartz"
		));
	}
}
