package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class GeneratorBlockStates extends BlockStateProvider {
    public GeneratorBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ActuallyAdditions.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        buildCubeAll(ActuallyBlocks.blockCrystalClusterRedstone);
        buildCubeAll(ActuallyBlocks.blockCrystalClusterLapis);
        buildCubeAll(ActuallyBlocks.blockCrystalClusterDiamond);
        buildCubeAll(ActuallyBlocks.blockCrystalClusterCoal);
        buildCubeAll(ActuallyBlocks.blockCrystalClusterEmerald);
        buildCubeAll(ActuallyBlocks.blockCrystalClusterIron);
        buildCubeAll(ActuallyBlocks.blockBatteryBox);
        buildCubeAll(ActuallyBlocks.blockItemViewerHopping);
        buildCubeAll(ActuallyBlocks.blockFarmer);
        buildCubeAll(ActuallyBlocks.blockBioReactor);
        buildCubeAll(ActuallyBlocks.blockEmpowerer);
        buildCubeAll(ActuallyBlocks.blockTinyTorch);
        buildCubeAll(ActuallyBlocks.blockShockSuppressor);
        buildCubeAll(ActuallyBlocks.blockDisplayStand);
        buildCubeAll(ActuallyBlocks.blockPlayerInterface);
        buildCubeAll(ActuallyBlocks.blockItemViewer);
        buildCubeAll(ActuallyBlocks.blockFireworkBox);
        buildCubeAll(ActuallyBlocks.blockMiner);
        buildCubeAll(ActuallyBlocks.blockAtomicReconstructor);
        buildCubeAll(ActuallyBlocks.blockCrystalRedstone);
        buildCubeAll(ActuallyBlocks.blockCrystalLapis);
        buildCubeAll(ActuallyBlocks.blockCrystalDiamond);
        buildCubeAll(ActuallyBlocks.blockCrystalVoid);
        buildCubeAll(ActuallyBlocks.blockCrystalEmerald);
        buildCubeAll(ActuallyBlocks.blockCrystalQuarts);
        buildCubeAll(ActuallyBlocks.blockCrystalEmpoweredRedstone);
        buildCubeAll(ActuallyBlocks.blockCrystalEmpoweredLapis);
        buildCubeAll(ActuallyBlocks.blockCrystalEmpoweredDiamond);
        buildCubeAll(ActuallyBlocks.blockCrystalEmpoweredVoid);
        buildCubeAll(ActuallyBlocks.blockCrystalEmpoweredEmerald);
        buildCubeAll(ActuallyBlocks.blockCrystalEmpoweredQuarts);
        buildCubeAll(ActuallyBlocks.blockBlackLotus);
        buildCubeAll(ActuallyBlocks.blockLaserRelay);
        buildCubeAll(ActuallyBlocks.blockLaserRelayAdvanced);
        buildCubeAll(ActuallyBlocks.blockLaserRelayExtreme);
        buildCubeAll(ActuallyBlocks.blockLaserRelayFluids);
        buildCubeAll(ActuallyBlocks.blockLaserRelayItem);
        buildCubeAll(ActuallyBlocks.blockLaserRelayItemWhitelist);
        buildCubeAll(ActuallyBlocks.blockRangedCollector);
        buildCubeAll(ActuallyBlocks.blockDirectionalBreaker);
        buildCubeAll(ActuallyBlocks.blockLeafGenerator);
        buildCubeAll(ActuallyBlocks.blockSmileyCloud);
        buildCubeAll(ActuallyBlocks.blockXPSolidifier);
        buildCubeAll(ActuallyBlocks.blockTestifiBucksGreenWall);
        buildCubeAll(ActuallyBlocks.blockTestifiBucksWhiteWall);
        stairsBlock((StairsBlock) ActuallyBlocks.blockTestifiBucksGreenStairs.get(), modLoc("block/green_wall_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.blockTestifiBucksWhiteStairs.get(), modLoc("block/white_wall_block"));
        slabBlock((SlabBlock) ActuallyBlocks.blockTestifiBucksGreenSlab.get(), modLoc("block/green_wall_block"), modLoc("block/green_wall_block"));
        slabBlock((SlabBlock) ActuallyBlocks.blockTestifiBucksWhiteSlab.get(), modLoc("block/white_wall_block"), modLoc("block/white_wall_block"));
        fenceBlock((FenceBlock) ActuallyBlocks.blockTestifiBucksGreenFence.get(), modLoc("block/green_wall_block"));
        fenceBlock((FenceBlock) ActuallyBlocks.blockTestifiBucksWhiteFence.get(), modLoc("block/white_wall_block"));
        buildLitState(ActuallyBlocks.LAMP_WHITE);
        buildLitState(ActuallyBlocks.LAMP_ORANGE);
        buildLitState(ActuallyBlocks.LAMP_MAGENTA);
        buildLitState(ActuallyBlocks.LAMP_LIGHT_BLUE);
        buildLitState(ActuallyBlocks.LAMP_YELLOW);
        buildLitState(ActuallyBlocks.LAMP_LIME);
        buildLitState(ActuallyBlocks.LAMP_PINK);
        buildLitState(ActuallyBlocks.LAMP_GRAY);
        buildLitState(ActuallyBlocks.LAMP_LIGHT_GRAY);
        buildLitState(ActuallyBlocks.LAMP_CYAN);
        buildLitState(ActuallyBlocks.LAMP_PURPLE);
        buildLitState(ActuallyBlocks.LAMP_BLUE);
        buildLitState(ActuallyBlocks.LAMP_BROWN);
        buildLitState(ActuallyBlocks.LAMP_GREEN);
        buildLitState(ActuallyBlocks.LAMP_RED);
        buildLitState(ActuallyBlocks.LAMP_BLACK);
        buildCubeAll(ActuallyBlocks.blockLampPowerer);
        buildCubeAll(ActuallyBlocks.blockTreasureChest);
        buildCubeAll(ActuallyBlocks.blockEnergizer);
        buildCubeAll(ActuallyBlocks.blockEnervator);
        buildCubeAll(ActuallyBlocks.blockLavaFactoryController);
        buildCubeAll(ActuallyBlocks.blockCanolaPress);
        buildCubeAll(ActuallyBlocks.blockPhantomface);
        buildCubeAll(ActuallyBlocks.blockPhantomPlacer);
        buildCubeAll(ActuallyBlocks.blockPhantomLiquiface);
        buildCubeAll(ActuallyBlocks.blockPhantomEnergyface);
        buildCubeAll(ActuallyBlocks.blockPhantomRedstoneface);
        buildCubeAll(ActuallyBlocks.blockPhantomBreaker);
        buildCubeAll(ActuallyBlocks.blockCoalGenerator);
        buildCubeAll(ActuallyBlocks.blockOilGenerator);
        buildCubeAll(ActuallyBlocks.blockFermentingBarrel);
        buildCubeAll(ActuallyBlocks.blockRice);
        buildCubeAll(ActuallyBlocks.blockCanola);
        buildCubeAll(ActuallyBlocks.blockFlax);
        buildCubeAll(ActuallyBlocks.blockCoffee);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTS);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTS_CHISELED);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTS_PILLAR);
        buildCubeAll(ActuallyBlocks.CHARCOAL);
        buildCubeAll(ActuallyBlocks.ENDER_CASING);
        buildCubeAll(ActuallyBlocks.ENDERPEARL);
        buildCubeAll(ActuallyBlocks.IRON_CASING);
        buildCubeAll(ActuallyBlocks.IRON_CASING_SNOW);
        buildCubeAll(ActuallyBlocks.LAVA_FACTORY_CASE);
        buildCubeAll(ActuallyBlocks.ORE_BLACK_QUARTS);
        buildCubeAll(ActuallyBlocks.WOOD_CASING);
        buildCubeAll(ActuallyBlocks.blockFeeder);
        buildCubeAll(ActuallyBlocks.blockGrinder);
        buildCubeAll(ActuallyBlocks.blockGrinderDouble);
        buildCubeAll(ActuallyBlocks.blockFurnaceDouble);
        buildCubeAll(ActuallyBlocks.blockInputter);
        buildCubeAll(ActuallyBlocks.blockInputterAdvanced);
        buildCubeAll(ActuallyBlocks.blockFishingNet);
        buildCubeAll(ActuallyBlocks.blockFurnaceSolar);
        buildCubeAll(ActuallyBlocks.blockHeatCollector);
        buildCubeAll(ActuallyBlocks.blockItemRepairer);
        buildCubeAll(ActuallyBlocks.blockGreenhouseGlass);
        buildCubeAll(ActuallyBlocks.blockBreaker);
        buildCubeAll(ActuallyBlocks.blockPlacer);
        buildCubeAll(ActuallyBlocks.blockDropper);
        buildCubeAll(ActuallyBlocks.blockFluidPlacer);
        buildCubeAll(ActuallyBlocks.blockFluidCollector);
        buildCubeAll(ActuallyBlocks.blockCoffeeMachine);
        buildCubeAll(ActuallyBlocks.blockPhantomBooster);
//        buildCubeAll(ActuallyBlocks.blockWildPlant);
        wallBlock((WallBlock) ActuallyBlocks.blockQuartzWall.get(), modLoc("block/black_quartz_block"));
        wallBlock((WallBlock) ActuallyBlocks.blockChiseledQuartzWall.get(), modLoc("block/black_quartz_chiseled_block"));
        wallBlock((WallBlock) ActuallyBlocks.blockPillarQuartzWall.get(), modLoc("block/black_quartz_pillar_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.blockQuartzStair.get(), modLoc("block/black_quartz_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.blockChiseledQuartzStair.get(), modLoc("block/black_quartz_chiseled_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.blockPillarQuartzStair.get(), modLoc("block/black_quartz_pillar_block"));
        slabBlock((SlabBlock) ActuallyBlocks.blockQuartzSlab.get(), modLoc("block/black_quartz_block"), modLoc("block/black_quartz_block"));
        slabBlock((SlabBlock) ActuallyBlocks.blockChiseledQuartzSlab.get(), modLoc("block/black_quartz_chiseled_block"), modLoc("block/black_quartz_chiseled_block"));
        slabBlock((SlabBlock) ActuallyBlocks.blockPillarQuartzSlab.get(), modLoc("block/black_quartz_pillar_block"), modLoc("block/black_quartz_pillar_block"));
    }

    private void buildCubeAll(Supplier<Block> block) {
        simpleBlock(block.get());
    }

    private void buildLitState(Supplier<Block> block) {
        ResourceLocation name = block.get().getRegistryName();
        assert name != null;

        getVariantBuilder(block.get())
            .partialState().with(BlockStateProperties.LIT, false)
                .addModels(ConfiguredModel.builder().modelFile(models().cubeAll(name.toString(), modLoc("block/" + name.getPath()))).build())
            .partialState().with(BlockStateProperties.LIT, true)
                .addModels(ConfiguredModel.builder().modelFile(models().cubeAll(name.toString(), modLoc("block/" + name.getPath().replace("_block", "_on_block")))).build());
    }
}
