package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.blocks.functional.AtomicReconstructorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class GeneratorBlockStates extends BlockStateProvider {
    public GeneratorBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ActuallyAdditions.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Fully Directional Blocks
        fullyDirectionalBlock(ActuallyBlocks.ATOMIC_RECONSTRUCTOR);
        fullyDirectionalBlock(ActuallyBlocks.BREAKER);
        fullyDirectionalBlock(ActuallyBlocks.PLACER);
        fullyDirectionalBlock(ActuallyBlocks.DROPPER);
        fullyDirectionalBlock(ActuallyBlocks.FLUID_PLACER);
        fullyDirectionalBlock(ActuallyBlocks.FLUID_COLLECTOR);
        fullyDirectionalBlock(ActuallyBlocks.DIRECTIONAL_BREAKER);

        // Horizontal Directional Blocks
        horizontallyDirectionalBlock(ActuallyBlocks.FARMER);
        horizontallyDirectionalBlock(ActuallyBlocks.BIO_REACTOR);
        horizontallyDirectionalBlock(ActuallyBlocks.MINER);
        horizontallyDirectionalBlock(ActuallyBlocks.LEAF_GENERATOR);
        horizontallyDirectionalBlock(ActuallyBlocks.COFFEE_MACHINE);
        horizontallyDirectionalBlock(ActuallyBlocks.CRUSHER);
        horizontallyDirectionalBlock(ActuallyBlocks.CRUSHER_DOUBLE);
        horizontallyDirectionalBlock(ActuallyBlocks.POWERED_FURNACE);
        horizontallyDirectionalBlock(ActuallyBlocks.DOUBLE_POWERED_FURNACE);
        horizontallyDirectionalBlock(ActuallyBlocks.COAL_GENERATOR);
        horizontallyDirectionalBlock(ActuallyBlocks.OIL_GENERATOR);
        horizontallyDirectionalBlock(ActuallyBlocks.LAMP_CONTROLLER);
        horizontallyDirectionalBlock(ActuallyBlocks.LAVA_FACTORY_CONTROLLER);

        // Standard Block
        standardBlock(ActuallyBlocks.CRYSTAL_CLUSTER_RESTONIA);
        standardBlock(ActuallyBlocks.CRYSTAL_CLUSTER_PALIS);
        standardBlock(ActuallyBlocks.CRYSTAL_CLUSTER_DIAMATINE);
        standardBlock(ActuallyBlocks.CRYSTAL_CLUSTER_VOID);
        standardBlock(ActuallyBlocks.CRYSTAL_CLUSTER_EMERADIC);
        standardBlock(ActuallyBlocks.CRYSTAL_CLUSTER_ENORI);
        standardBlock(ActuallyBlocks.BATTERY_BOX);
        standardBlock(ActuallyBlocks.HOPPING_ITEM_INTERFACE);
        standardBlock(ActuallyBlocks.EMPOWERER);
        standardBlock(ActuallyBlocks.TINY_TORCH);
        standardBlock(ActuallyBlocks.SHOCK_SUPPRESSOR);
        standardBlock(ActuallyBlocks.DISPLAY_STAND);
        standardBlock(ActuallyBlocks.PLAYER_INTERFACE);
        standardBlock(ActuallyBlocks.ITEM_INTERFACE);
        standardBlock(ActuallyBlocks.FIREWORK_BOX);
        standardBlock(ActuallyBlocks.CRYSTAL_RESTONIA);
        standardBlock(ActuallyBlocks.CRYSTAL_PALIS);
        standardBlock(ActuallyBlocks.CRYSTAL_DIAMATINE);
        standardBlock(ActuallyBlocks.CRYSTAL_VOID);
        standardBlock(ActuallyBlocks.CRYSTAL_EMERADIC);
        standardBlock(ActuallyBlocks.CRYSTAL_ENORI);
        standardBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_RESTONIA);
        standardBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_PALIS);
        standardBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_DIAMATINE);
        standardBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_VOID);
        standardBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_EMERADIC);
        standardBlock(ActuallyBlocks.CRYSTAL_EMPOWERED_ENORI);
        standardBlock(ActuallyBlocks.ENERGY_LASER_RELAY);
        standardBlock(ActuallyBlocks.ENERGY_LASER_RELAY_ADVANCED);
        standardBlock(ActuallyBlocks.ENERGY_LASER_RELAY_EXTREME);
        standardBlock(ActuallyBlocks.FLUIDS_LASER_RELAY);
        standardBlock(ActuallyBlocks.ITEM_LASER_RELAY);
        standardBlock(ActuallyBlocks.ADVANCED_ITEM_LASER_RELAY);
        standardBlock(ActuallyBlocks.RANGED_COLLECTOR);
        standardBlock(ActuallyBlocks.XP_SOLIDIFIER);
        standardBlock(ActuallyBlocks.ENERGIZER);
        standardBlock(ActuallyBlocks.ENERVATOR);
        standardBlock(ActuallyBlocks.CANOLA_PRESS);
        standardBlock(ActuallyBlocks.PHANTOMFACE);
        standardBlock(ActuallyBlocks.PHANTOM_PLACER);
        standardBlock(ActuallyBlocks.PHANTOM_LIQUIFACE);
        standardBlock(ActuallyBlocks.PHANTOM_ENERGYFACE);
        standardBlock(ActuallyBlocks.PHANTOM_REDSTONEFACE);
        standardBlock(ActuallyBlocks.PHANTOM_BREAKER);
        standardBlock(ActuallyBlocks.FERMENTING_BARREL);
        standardBlock(ActuallyBlocks.FEEDER);
        standardBlock(ActuallyBlocks.HEAT_COLLECTOR);
        standardBlock(ActuallyBlocks.GREENHOUSE_GLASS);
        standardBlock(ActuallyBlocks.PHANTOM_BOOSTER);

        // Lamps
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

        // TO BE SORTED
        buildCubeAll(ActuallyBlocks.RICE);
        buildCubeAll(ActuallyBlocks.CANOLA);
        buildCubeAll(ActuallyBlocks.FLAX);
        buildCubeAll(ActuallyBlocks.COFFEE);

        buildCubeAll(ActuallyBlocks.CHARCOAL);
        buildCubeAll(ActuallyBlocks.ENDER_CASING);
        buildCubeAll(ActuallyBlocks.ENDERPEARL);
        buildCubeAll(ActuallyBlocks.IRON_CASING);
        standardBlock(ActuallyBlocks.LAVA_FACTORY_CASE);
        buildCubeAll(ActuallyBlocks.ORE_BLACK_QUARTZ);
        buildCubeAll(ActuallyBlocks.WOOD_CASING);

        // Quartz
        standardBlock(ActuallyBlocks.BLACK_QUARTZ);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTZ_SMOOTH);
        standardBlock(ActuallyBlocks.BLACK_QUARTZ_CHISELED);
        standardBlock(ActuallyBlocks.BLACK_QUARTZ_PILLAR);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTZ_BRICK);
        buildCubeAll(ActuallyBlocks.GREEN_BLOCK);
        buildCubeAll(ActuallyBlocks.WHITE_BLOCK);

        // Walls
        wallBlock((WallBlock) ActuallyBlocks.BLACK_QUARTZ_WALL.get(), modLoc("block/black_quartz_block"));
        wallBlock((WallBlock) ActuallyBlocks.BLACK_SMOOTH_QUARTZ_WALL.get(), modLoc("block/black_quartz_smooth_block"));
        wallBlock((WallBlock) ActuallyBlocks.BLACK_CHISELED_QUARTZ_WALL.get(), modLoc("block/black_quartz_chiseled_block"));
        wallBlock((WallBlock) ActuallyBlocks.BLACK_PILLAR_QUARTZ_WALL.get(), modLoc("block/black_quartz_pillar_block"));
        wallBlock((WallBlock) ActuallyBlocks.BLACK_BRICK_QUARTZ_WALL.get(), modLoc("block/black_quartz_brick_block"));

        wallBlock((WallBlock) ActuallyBlocks.GREEN_WALL.get(), modLoc("block/green_block"));
        wallBlock((WallBlock) ActuallyBlocks.WHITE_WALL.get(), modLoc("block/white_block"));

        // Stairs
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_QUARTZ_STAIR.get(), modLoc("block/black_quartz_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_SMOOTH_QUARTZ_STAIR.get(), modLoc("block/black_quartz_smooth_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_CHISELED_QUARTZ_STAIR.get(), modLoc("block/black_quartz_chiseled_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_PILLAR_QUARTZ_STAIR.get(), modLoc("block/black_quartz_pillar_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_BRICK_QUARTZ_STAIR.get(), modLoc("block/black_quartz_brick_block"));

        stairsBlock((StairsBlock) ActuallyBlocks.GREEN_STAIRS.get(), modLoc("block/green_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.WHITE_STAIRS.get(), modLoc("block/white_block"));

        // Slabs
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_QUARTZ_SLAB.get(), modLoc("block/black_quartz_block"), modLoc("block/black_quartz_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_SMOOTH_QUARTZ_SLAB.get(), modLoc("block/black_quartz_smooth_block"), modLoc("block/black_quartz_smooth_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_CHISELED_QUARTZ_SLAB.get(), modLoc("block/black_quartz_chiseled_block"), modLoc("block/black_quartz_chiseled_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_PILLAR_QUARTZ_SLAB.get(), modLoc("block/black_quartz_pillar_block"), modLoc("block/black_quartz_pillar_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_BRICK_QUARTZ_SLAB.get(), modLoc("block/black_quartz_brick_block"), modLoc("block/black_quartz_brick_block"));

        slabBlock((SlabBlock) ActuallyBlocks.GREEN_SLAB.get(), modLoc("block/green_block"), modLoc("block/green_block"));
        slabBlock((SlabBlock) ActuallyBlocks.WHITE_SLAB.get(), modLoc("block/white_block"), modLoc("block/white_block"));

        //buildCubeAll(ActuallyBlocks.ESD);
        //buildCubeAll(ActuallyBlocks.ESD_ADVANCED);
        //buildCubeAll(ActuallyBlocks.WildPlant);
        //buildCubeAll(ActuallyBlocks.IRON_CASING_SNOW);
    }

    private void buildCubeAll(Supplier<Block> block) {
        simpleBlock(block.get());
    }

    private void standardBlock(Supplier<Block> block) {
        ResourceLocation name = block.get().getRegistryName();
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name.toString().split(":")[1]));

        assert name != null;
        simpleBlock(block.get(), model);
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

    private void fullyDirectionalBlock(Supplier<Block> block) {
        ResourceLocation name = block.get().getRegistryName();
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name.toString().split(":")[1]));
        ModelFile verModel = new ModelFile.UncheckedModelFile(modLoc("block/" + name.toString().split(":")[1] + "_ver"));

        assert name != null;
        directionalBlock(block.get(), model);
    }

    private void horizontallyDirectionalBlock(Supplier<Block> block) {
        ResourceLocation name = block.get().getRegistryName();
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name.toString().split(":")[1]));

        assert name != null;
        horizontalBlock(block.get(), model);
    }

}
