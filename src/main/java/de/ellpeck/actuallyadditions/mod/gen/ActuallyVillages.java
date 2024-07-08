package de.ellpeck.actuallyadditions.mod.gen;

import com.mojang.datafixers.util.Pair;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.util.ArrayList;
import java.util.List;

public class ActuallyVillages {


	private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry,
	                                      Registry<StructureProcessorList> processorListRegistry,
	                                      ResourceLocation poolRL,
	                                      String nbtPieceRL,
	                                      int weight) {

		// Grabs the processor list we want to use along with our piece.
		// This is a requirement as using the ProcessorLists.EMPTY field will cause the game to throw errors.
		// The reason why is the empty processor list in the world's registry is not the same instance as in that field once the world is started up.
		Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(ActuallyProcessorLists.ENGINEER_HOUSE_PROCESSOR_LIST_KEY);

		// Grab the pool we want to add to
		StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
		if (pool == null) return;

		// Grabs the nbt piece and creates a SinglePoolElement of it that we can add to a structure's pool.
		// Use .legacy( for villages/outposts and .single( for everything else
		SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

		// Use AccessTransformer or Accessor Mixin to make StructureTemplatePool's templates field public for us to see.
		// Weight is handled by how many times the entry appears in this list.
		// We do not need to worry about immutability as this field is created using Lists.newArrayList(); which makes a mutable list.
		for (int i = 0; i < weight; i++) {
			pool.templates.add(piece);
		}

		// Use AccessTransformer or Accessor Mixin to make StructureTemplatePool's rawTemplates field public for us to see.
		// This list of pairs of pieces and weights is not used by vanilla by default but another mod may need it for efficiency.
		// So lets add to this list for completeness. We need to make a copy of the array as it can be an immutable list.
		//   NOTE: This is a com.mojang.datafixers.util.Pair. It is NOT a fastUtil pair class. Use the mojang class.
		List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
		listOfPieceEntries.add(new Pair<>(piece, weight));
		pool.rawTemplates = listOfPieceEntries;
	}


	public static void modifyVillageStructures(final ServerAboutToStartEvent event) {
		Registry<StructureTemplatePool> templatePoolRegistry = event.getServer().registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
		Registry<StructureProcessorList> processorListRegistry = event.getServer().registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();

		//Add Engineer house to villages
		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				ResourceLocation.tryParse("minecraft:village/plains/houses"),
				ActuallyAdditions.MODID + ":andrew_period_house", 10);

		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				ResourceLocation.tryParse("minecraft:village/snowy/houses"),
				ActuallyAdditions.MODID + ":andrew_period_house", 10);

		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				ResourceLocation.tryParse("minecraft:village/savanna/houses"),
				ActuallyAdditions.MODID + ":andrew_period_house", 10);

		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				ResourceLocation.tryParse("minecraft:village/taiga/houses"),
				ActuallyAdditions.MODID + ":andrew_period_house", 10);

		addBuildingToPool(templatePoolRegistry, processorListRegistry,
				ResourceLocation.tryParse("minecraft:village/desert/houses"),
				ActuallyAdditions.MODID + ":andrew_period_house", 50);

		//Add Actually Additions crops to village farms
		StructureProcessor cropProcessor = new RuleProcessor(List.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.WHEAT, 0.30F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.RICE.get().defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.WHEAT, 0.25F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.COFFEE.get().defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.WHEAT, 0.10F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.CANOLA.get().defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.WHEAT, 0.05F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.FLAX.get().defaultBlockState())
		));

		addNewRuleToProcessorList(ResourceLocation.tryParse("minecraft:farm_plains"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("minecraft:farm_savanna"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("minecraft:farm_snowy"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("minecraft:farm_taiga"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("minecraft:farm_desert"), cropProcessor, processorListRegistry);

		// Can target other mod's processor lists
		addNewRuleToProcessorList(ResourceLocation.tryParse("repurposed_structures:villages/badlands/crop_replacement"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("repurposed_structures:villages/birch/crop_randomizer"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("repurposed_structures:villages/dark_forest/crop_randomizer"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("repurposed_structures:villages/giant_taiga/crop_randomizer"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("repurposed_structures:villages/jungle/crop_randomizer"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("repurposed_structures:villages/mountains/crop_randomizer"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("repurposed_structures:villages/oak/crop_randomizer"), cropProcessor, processorListRegistry);
		addNewRuleToProcessorList(ResourceLocation.tryParse("repurposed_structures:villages/swamp/crop_randomizer"), cropProcessor, processorListRegistry);
	}

	private static void addNewRuleToProcessorList(ResourceLocation targetProcessorList, StructureProcessor processorToAdd, Registry<StructureProcessorList> processorListRegistry) {
		processorListRegistry.getOptional(targetProcessorList)
				.ifPresent(processorList -> {
					List<StructureProcessor> newSafeList = new ArrayList<>(processorList.list());
					newSafeList.add(processorToAdd);
					processorList.list = newSafeList; //Have to use an AT to be able to modify the list (or Accessor Mixin)
				});
	}
}
