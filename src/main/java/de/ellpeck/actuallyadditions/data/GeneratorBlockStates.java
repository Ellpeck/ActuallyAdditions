package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
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
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class GeneratorBlockStates extends BlockStateProvider {
    public GeneratorBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ActuallyAdditions.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        fullyDirectionalBlock(ActuallyBlocks.ATOMIC_RECONSTRUCTOR);

        buildCubeAll(ActuallyBlocks.CRYSTAL_CLUSTER_RESTONIA);
        buildCubeAll(ActuallyBlocks.CRYSTAL_CLUSTER_PALIS);
        buildCubeAll(ActuallyBlocks.CRYSTAL_CLUSTER_DIAMATINE);
        buildCubeAll(ActuallyBlocks.CRYSTAL_CLUSTER_VOID);
        buildCubeAll(ActuallyBlocks.CRYSTAL_CLUSTER_EMERADIC);
        buildCubeAll(ActuallyBlocks.CRYSTAL_CLUSTER_ENORI);
        buildCubeAll(ActuallyBlocks.BATTERY_BOX);
        buildCubeAll(ActuallyBlocks.HOPPING_ITEM_INTERFACE);
        buildCubeAll(ActuallyBlocks.FARMER);
        buildCubeAll(ActuallyBlocks.BIO_REACTOR);
        buildCubeAll(ActuallyBlocks.EMPOWERER);
        buildCubeAll(ActuallyBlocks.TINY_TORCH);
        buildCubeAll(ActuallyBlocks.SHOCK_SUPPRESSOR);
        buildCubeAll(ActuallyBlocks.DISPLAY_STAND);
        buildCubeAll(ActuallyBlocks.PLAYER_INTERFACE);
        buildCubeAll(ActuallyBlocks.ITEM_INTERFACE);
        buildCubeAll(ActuallyBlocks.FIREWORK_BOX);
        buildCubeAll(ActuallyBlocks.MINER);
        buildCubeAll(ActuallyBlocks.CRYSTAL_RESTONIA);
        buildCubeAll(ActuallyBlocks.CRYSTAL_PALIS);
        buildCubeAll(ActuallyBlocks.CRYSTAL_DIAMATINE);
        buildCubeAll(ActuallyBlocks.CRYSTAL_VOID);
        buildCubeAll(ActuallyBlocks.CRYSTAL_EMERADIC);
        buildCubeAll(ActuallyBlocks.CRYSTAL_ENORI);
        buildCubeAll(ActuallyBlocks.CRYSTAL_EMPOWERED_RESTONIA);
        buildCubeAll(ActuallyBlocks.CRYSTAL_EMPOWERED_PALIS);
        buildCubeAll(ActuallyBlocks.CRYSTAL_EMPOWERED_DIAMATINE);
        buildCubeAll(ActuallyBlocks.CRYSTAL_EMPOWERED_VOID);
        buildCubeAll(ActuallyBlocks.CRYSTAL_EMPOWERED_EMERADIC);
        buildCubeAll(ActuallyBlocks.CRYSTAL_EMPOWERED_ENORI);
        buildCubeAll(ActuallyBlocks.ENERGY_LASER_RELAY);
        buildCubeAll(ActuallyBlocks.ENERGY_LASER_RELAY_ADVANCED);
        buildCubeAll(ActuallyBlocks.ENERGY_LASER_RELAY_EXTREME);
        buildCubeAll(ActuallyBlocks.FLUIDS_LASER_RELAY);
        buildCubeAll(ActuallyBlocks.ITEM_LASER_RELAY);
        buildCubeAll(ActuallyBlocks.ADVANCED_ITEM_LASER_RELAY);
        buildCubeAll(ActuallyBlocks.RANGED_COLLECTOR);
        buildCubeAll(ActuallyBlocks.DIRECTIONAL_BREAKER);
        buildCubeAll(ActuallyBlocks.LEAF_GENERATOR);
        buildCubeAll(ActuallyBlocks.XP_SOLIDIFIER);
        buildCubeAll(ActuallyBlocks.GREEN_BLOCK);
        buildCubeAll(ActuallyBlocks.WHITE_BLOCK);
        stairsBlock((StairsBlock) ActuallyBlocks.GREEN_STAIRS.get(), modLoc("block/green_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.WHITE_STAIRS.get(), modLoc("block/white_block"));
        slabBlock((SlabBlock) ActuallyBlocks.GREEN_SLAB.get(), modLoc("block/green_block"), modLoc("block/green_block"));
        slabBlock((SlabBlock) ActuallyBlocks.WHITE_SLAB.get(), modLoc("block/white_block"), modLoc("block/white_block"));
        wallBlock((WallBlock) ActuallyBlocks.GREEN_WALL.get(), modLoc("block/green_block"));
        wallBlock((WallBlock) ActuallyBlocks.WHITE_WALL.get(), modLoc("block/white_block"));
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
        buildCubeAll(ActuallyBlocks.LAMP_CONTROLLER);
        buildCubeAll(ActuallyBlocks.ENERGIZER);
        buildCubeAll(ActuallyBlocks.ENERVATOR);
        buildCubeAll(ActuallyBlocks.LAVA_FACTORY_CONTROLLER);
        buildCubeAll(ActuallyBlocks.CANOLA_PRESS);
        buildCubeAll(ActuallyBlocks.PHANTOMFACE);
        buildCubeAll(ActuallyBlocks.PHANTOM_PLACER);
        buildCubeAll(ActuallyBlocks.PHANTOM_LIQUIFACE);
        buildCubeAll(ActuallyBlocks.PHANTOM_ENERGYFACE);
        buildCubeAll(ActuallyBlocks.PHANTOM_REDSTONEFACE);
        buildCubeAll(ActuallyBlocks.PHANTOM_BREAKER);
        buildCubeAll(ActuallyBlocks.COAL_GENERATOR);
        buildCubeAll(ActuallyBlocks.OIL_GENERATOR);
        buildCubeAll(ActuallyBlocks.FERMENTING_BARREL);
        buildCubeAll(ActuallyBlocks.RICE);
        buildCubeAll(ActuallyBlocks.CANOLA);
        buildCubeAll(ActuallyBlocks.FLAX);
        buildCubeAll(ActuallyBlocks.COFFEE);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTZ);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTZ_CHISELED);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTZ_PILLAR);
        buildCubeAll(ActuallyBlocks.CHARCOAL);
        buildCubeAll(ActuallyBlocks.ENDER_CASING);
        buildCubeAll(ActuallyBlocks.ENDERPEARL);
        buildCubeAll(ActuallyBlocks.IRON_CASING);
        buildCubeAll(ActuallyBlocks.IRON_CASING_SNOW);
        buildCubeAll(ActuallyBlocks.LAVA_FACTORY_CASE);
        buildCubeAll(ActuallyBlocks.ORE_BLACK_QUARTZ);
        buildCubeAll(ActuallyBlocks.WOOD_CASING);
        buildCubeAll(ActuallyBlocks.FEEDER);
        buildCubeAll(ActuallyBlocks.CRUSHER);
        buildCubeAll(ActuallyBlocks.CRUSHER_DOUBLE);
        buildCubeAll(ActuallyBlocks.POWERED_FURNACE);
        buildCubeAll(ActuallyBlocks.ESD);
        buildCubeAll(ActuallyBlocks.ESD_ADVANCED);
        buildCubeAll(ActuallyBlocks.HEAT_COLLECTOR);
        buildCubeAll(ActuallyBlocks.GREENHOUSE_GLASS);
        buildCubeAll(ActuallyBlocks.BREAKER);
        buildCubeAll(ActuallyBlocks.PLACER);
        buildCubeAll(ActuallyBlocks.DROPPER);
        buildCubeAll(ActuallyBlocks.FLUID_PLACER);
        buildCubeAll(ActuallyBlocks.FLUID_COLLECTOR);
        buildCubeAll(ActuallyBlocks.COFFEE_MACHINE);
        buildCubeAll(ActuallyBlocks.PHANTOM_BOOSTER);
//        buildCubeAll(ActuallyBlocks.WildPlant);
        wallBlock((WallBlock) ActuallyBlocks.BLACK_QUARTZ_WALL.get(), modLoc("block/black_quartz_block"));
        wallBlock((WallBlock) ActuallyBlocks.BLACK_CHISELED_QUARTZ_WALL.get(), modLoc("block/black_quartz_chiseled_block"));
        wallBlock((WallBlock) ActuallyBlocks.BLACK_PILLAR_QUARTZ_WALL.get(), modLoc("block/black_quartz_pillar_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_QUARTZ_STAIR.get(), modLoc("block/black_quartz_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_CHISELED_QUARTZ_STAIR.get(), modLoc("block/black_quartz_chiseled_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_PILLAR_QUARTZ_STAIR.get(), modLoc("block/black_quartz_pillar_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_QUARTZ_SLAB.get(), modLoc("block/black_quartz_block"), modLoc("block/black_quartz_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_CHISELED_QUARTZ_SLAB.get(), modLoc("block/black_quartz_chiseled_block"), modLoc("block/black_quartz_chiseled_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_PILLAR_QUARTZ_SLAB.get(), modLoc("block/black_quartz_pillar_block"), modLoc("block/black_quartz_pillar_block"));
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

    private void fullyDirectionalBlock(Supplier<Block> block) {
        ResourceLocation name = block.get().getRegistryName();

        // Todo: come back and fix this, it's still not right
        assert name != null;
        getVariantBuilder(block.get())
                .forAllStates(state -> {
                    Direction dir = state.get(BlockStateProperties.FACING);
                    System.out.println(dir);
                    return ConfiguredModel.builder()
                            .modelFile(models().withExistingParent(name.toString(), "block/orientable")
                                    .texture("side", modLoc(String.format("block/%s", name.getPath())))
                                    .texture("front", modLoc(String.format("block/%s_front", name.getPath())))
                                    .texture("top", modLoc(String.format("block/%s_top", name.getPath()))))
                            .rotationX(dir == Direction.DOWN ? 90 : (dir == Direction.UP ? 270 : 0))
                            .rotationY(dir.getAxis().isVertical() ? 0 : dir.getHorizontalIndex() * 90)
                            .build();
                });
    }

}
