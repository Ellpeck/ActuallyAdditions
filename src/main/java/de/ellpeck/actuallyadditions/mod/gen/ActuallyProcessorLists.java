package de.ellpeck.actuallyadditions.mod.gen;

import com.google.common.collect.ImmutableList;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

public class ActuallyProcessorLists {
	public static final ResourceKey<StructureProcessorList> ENGINEER_HOUSE_PROCESSOR_LIST_KEY = ResourceKey.create(
			Registries.PROCESSOR_LIST, new ResourceLocation(ActuallyAdditions.MODID, "engineer_house"));

	public static void bootstrap(BootstapContext<StructureProcessorList> context) {
		register(context, ENGINEER_HOUSE_PROCESSOR_LIST_KEY, ImmutableList.of(new RuleProcessor(ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_ORANGE.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_MAGENTA.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_LIGHT_BLUE.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_YELLOW.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_LIME.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_PINK.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_GRAY.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_LIGHT_GRAY.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_CYAN.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_PURPLE.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_BLUE.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_BROWN.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_GREEN.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_RED.get().defaultBlockState().setValue(BlockStateProperties.LIT, true)),
				new ProcessorRule(new RandomBlockMatchTest(ActuallyBlocks.LAMP_WHITE.get(), 0.0625F), AlwaysTrueTest.INSTANCE, ActuallyBlocks.LAMP_BLACK.get().defaultBlockState().setValue(BlockStateProperties.LIT, true))
		))));
	}

	private static void register(BootstapContext<StructureProcessorList> bootstapContext,
	                             ResourceKey<StructureProcessorList> processorListResourceKey,
	                             List<StructureProcessor> structureProcessorList) {
		bootstapContext.register(processorListResourceKey, new StructureProcessorList(structureProcessorList));
	}
}
