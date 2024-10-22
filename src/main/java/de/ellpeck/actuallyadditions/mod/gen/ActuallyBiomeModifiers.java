package de.ellpeck.actuallyadditions.mod.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.conditions.BoolConfigCondition;
import de.ellpeck.actuallyadditions.mod.gen.modifier.BoolConfigFeatureBiomeModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import net.neoforged.neoforge.registries.holdersets.OrHolderSet;

import java.util.function.Supplier;

public final class ActuallyBiomeModifiers {
	private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, ActuallyAdditions.MODID);
	public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<BoolConfigCondition>> BOOL_CONFIG_CONDITION = CONDITION_CODECS.register("bool_config_condition", () -> BoolConfigCondition.CODEC);

	public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ActuallyAdditions.MODID);
	public static final Supplier<MapCodec<BoolConfigFeatureBiomeModifier>> BOOL_CONFIG_MODIFIER = BIOME_MODIFIER_SERIALIZERS.register("bool_config_feature_modifier", () ->
			RecordCodecBuilder.mapCodec(builder -> builder.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(BoolConfigFeatureBiomeModifier::biomes),
					PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(BoolConfigFeatureBiomeModifier::features),
					GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(BoolConfigFeatureBiomeModifier::step),
					Codec.STRING.fieldOf("boolConfig").forGetter(BoolConfigFeatureBiomeModifier::boolConfig)
			).apply(builder, BoolConfigFeatureBiomeModifier::new))
	);

	public static void init(IEventBus bus) {
		CONDITION_CODECS.register(bus);
		BIOME_MODIFIER_SERIALIZERS.register(bus);
	}


	protected static final ResourceKey<BiomeModifier> ADD_BLACK_QUARTZ_ORE_MODIFIER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			ActuallyAdditions.modLoc("add_black_quartz"));
	protected static final ResourceKey<BiomeModifier> ADD_CANOLA = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			ActuallyAdditions.modLoc("add_canola"));
	protected static final ResourceKey<BiomeModifier> ADD_FLAX = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			ActuallyAdditions.modLoc("add_flax"));
	protected static final ResourceKey<BiomeModifier> ADD_COFFEE = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			ActuallyAdditions.modLoc("add_coffee"));

	public static void bootstrap(BootstrapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
		HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);

		HolderSet.Named<Biome> overworldHolder = biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD);

		context.register(ADD_BLACK_QUARTZ_ORE_MODIFIER, new BoolConfigFeatureBiomeModifier(
				overworldHolder,
				HolderSet.direct(placedGetter.getOrThrow(ActuallyPlacedFeatures.PLACED_ORE_BLACK_QUARTZ)),
				GenerationStep.Decoration.UNDERGROUND_ORES, "generateQuartz"
		));
		context.register(ADD_CANOLA, new BoolConfigFeatureBiomeModifier(
				new OrHolderSet<>(biomeGetter.getOrThrow(Tags.Biomes.IS_HOT_OVERWORLD), biomeGetter.getOrThrow(Tags.Biomes.IS_SPARSE_VEGETATION_OVERWORLD)),
				HolderSet.direct(placedGetter.getOrThrow(ActuallyPlacedFeatures.CANOLA_PATCH)),
				GenerationStep.Decoration.VEGETAL_DECORATION, "generateCanola"
		));
		context.register(ADD_FLAX, new BoolConfigFeatureBiomeModifier(
				new OrHolderSet<>(biomeGetter.getOrThrow(Tags.Biomes.IS_DENSE_VEGETATION_OVERWORLD), biomeGetter.getOrThrow(Tags.Biomes.IS_COLD_OVERWORLD)),
				HolderSet.direct(placedGetter.getOrThrow(ActuallyPlacedFeatures.FLAX_PATCH)),
				GenerationStep.Decoration.VEGETAL_DECORATION, "generateFlax"
		));
		context.register(ADD_COFFEE, new BoolConfigFeatureBiomeModifier(
				biomeGetter.getOrThrow(Tags.Biomes.IS_HOT_OVERWORLD),
				HolderSet.direct(placedGetter.getOrThrow(ActuallyPlacedFeatures.COFFEE_PATCH)),
				GenerationStep.Decoration.VEGETAL_DECORATION, "generateCoffee"
		));
	}
}
