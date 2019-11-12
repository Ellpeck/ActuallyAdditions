/*
 * This file ("ActuallyAdditions.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ellpeck.actuallyadditions.mod.block.AABlocks;
import de.ellpeck.actuallyadditions.mod.item.AAItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(ActuallyAdditions.MODID)
public class ActuallyAdditions {

	public static final String MODID = "actuallyadditions";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final ItemGroup GROUP = new ItemGroup("actuallyadditions") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(AAItems.BLACK_QUARTZ);
		}
	};

	public ActuallyAdditions() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		ctx.getModEventBus().register(this);
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent e) {
		for (Biome b : ForgeRegistries.BIOMES) {
			if (b.getFeatures(Decoration.UNDERGROUND_ORES).stream().anyMatch(ActuallyAdditions::isIronOreFeature)) {
				b.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, AABlocks.BLACK_QUARTZ_ORE.getDefaultState(), 8), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 64)));
			}
		}
	}

	public static boolean isIronOreFeature(ConfiguredFeature<?> f) {
		if (f.feature == Feature.DECORATED) {
			DecoratedFeatureConfig cfg = (DecoratedFeatureConfig) f.config;
			if (cfg.feature.feature == Feature.ORE) return ((OreFeatureConfig) cfg.feature.config).state == Blocks.IRON_ORE.getDefaultState();
			return false;
		}
		return false;
	}
}
