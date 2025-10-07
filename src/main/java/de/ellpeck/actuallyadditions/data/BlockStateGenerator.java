package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.BlockTinyTorch;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput packOutput, ExistingFileHelper exFileHelper) {
        super(packOutput, ActuallyAdditions.MODID, exFileHelper);
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
        fullyDirectionalBlock(ActuallyBlocks.LONG_RANGE_BREAKER);

        // Horizontal Directional Blocks
        horizontallyDirectionalBlock(ActuallyBlocks.FARMER);
        horizontallyDirectionalBlock(ActuallyBlocks.BIOREACTOR);
        horizontallyDirectionalBlock(ActuallyBlocks.VERTICAL_DIGGER);
        horizontallyDirectionalBlock(ActuallyBlocks.LEAF_GENERATOR);
        horizontallyDirectionalBlock(ActuallyBlocks.COFFEE_MACHINE);
        horizontallyDirectionalBlock(ActuallyBlocks.CRUSHER);
        horizontallyDirectionalBlock(ActuallyBlocks.CRUSHER_DOUBLE);
        horizontallyDirectionalBlock(ActuallyBlocks.POWERED_FURNACE);
        horizontallyDirectionalBlock(ActuallyBlocks.COAL_GENERATOR);
        horizontallyDirectionalBlock(ActuallyBlocks.OIL_GENERATOR);
        fullyDirectionalBlock(ActuallyBlocks.LAMP_CONTROLLER);
        horizontallyDirectionalBlock(ActuallyBlocks.LAVA_FACTORY_CONTROLLER);

        // Standard Block
        fullyDirectionalBlock(ActuallyBlocks.RESTONIA_CRYSTAL_CLUSTER);
        fullyDirectionalBlock(ActuallyBlocks.PALIS_CRYSTAL_CLUSTER);
        fullyDirectionalBlock(ActuallyBlocks.DIAMATINE_CRYSTAL_CLUSTER);
        fullyDirectionalBlock(ActuallyBlocks.VOID_CRYSTAL_CLUSTER);
        fullyDirectionalBlock(ActuallyBlocks.EMERADIC_CRYSTAL_CLUSTER);
        fullyDirectionalBlock(ActuallyBlocks.ENORI_CRYSTAL_CLUSTER);
        standardBlock(ActuallyBlocks.BATTERY_BOX);
        standardBlock(ActuallyBlocks.ITEM_INTERFACE_HOPPING);
        standardBlock(ActuallyBlocks.EMPOWERER);
        tinyTorchBlock(ActuallyBlocks.TINY_TORCH);
        standardBlock(ActuallyBlocks.SHOCK_SUPPRESSOR);
        standardBlock(ActuallyBlocks.DISPLAY_STAND);
        standardBlock(ActuallyBlocks.PLAYER_INTERFACE);
        standardBlock(ActuallyBlocks.ITEM_INTERFACE);
        standardBlock(ActuallyBlocks.FIREWORK_BOX);
        buildCubeAll(ActuallyBlocks.RESTONIA_CRYSTAL);
        buildCubeAll(ActuallyBlocks.PALIS_CRYSTAL);
        buildCubeAll(ActuallyBlocks.DIAMATINE_CRYSTAL);
        buildCubeAll(ActuallyBlocks.VOID_CRYSTAL);
        buildCubeAll(ActuallyBlocks.EMERADIC_CRYSTAL);
        buildCubeAll(ActuallyBlocks.ENORI_CRYSTAL);
        buildCubeAll(ActuallyBlocks.EMPOWERED_RESTONIA_CRYSTAL);
        buildCubeAll(ActuallyBlocks.EMPOWERED_PALIS_CRYSTAL);
        buildCubeAll(ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL);
        buildCubeAll(ActuallyBlocks.EMPOWERED_VOID_CRYSTAL);
        buildCubeAll(ActuallyBlocks.EMPOWERED_EMERADIC_CRYSTAL);
        buildCubeAll(ActuallyBlocks.EMPOWERED_ENORI_CRYSTAL);
        fullyDirectionalBlock(ActuallyBlocks.LASER_RELAY);
        fullyDirectionalBlock(ActuallyBlocks.LASER_RELAY_ADVANCED);
        fullyDirectionalBlock(ActuallyBlocks.LASER_RELAY_EXTREME);
        fullyDirectionalBlock(ActuallyBlocks.LASER_RELAY_FLUIDS);
        fullyDirectionalBlock(ActuallyBlocks.LASER_RELAY_ITEM);
        fullyDirectionalBlock(ActuallyBlocks.LASER_RELAY_ITEM_ADVANCED);
        standardBlock(ActuallyBlocks.RANGED_COLLECTOR);
        standardBlock(ActuallyBlocks.XP_SOLIDIFIER);
        standardBlock(ActuallyBlocks.ENERGIZER);
        standardBlock(ActuallyBlocks.ENERVATOR);
        standardBlock(ActuallyBlocks.CANOLA_PRESS);
        standardBlock(ActuallyBlocks.PHANTOM_ITEMFACE);
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
        getVariantBuilder(ActuallyBlocks.CANOLA.get()).partialState()
            .with(CropBlock.AGE, 0).modelForState().modelFile(models().crop("canola_1", modLoc("block/canola_stage_1")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 1).modelForState().modelFile(models().crop("canola_2", modLoc("block/canola_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 2).modelForState().modelFile(models().crop("canola_2", modLoc("block/canola_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 3).modelForState().modelFile(models().crop("canola_2", modLoc("block/canola_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 4).modelForState().modelFile(models().crop("canola_3", modLoc("block/canola_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 5).modelForState().modelFile(models().crop("canola_3", modLoc("block/canola_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 6).modelForState().modelFile(models().crop("canola_3", modLoc("block/canola_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 7).modelForState().modelFile(models().crop("canola_4", modLoc("block/canola_stage_4")).renderType("minecraft:cutout")).addModel();

        getVariantBuilder(ActuallyBlocks.RICE.get()).partialState()
            .with(CropBlock.AGE, 0).modelForState().modelFile(models().crop("rice_1", modLoc("block/rice_stage_1")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 1).modelForState().modelFile(models().crop("rice_2", modLoc("block/rice_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 2).modelForState().modelFile(models().crop("rice_2", modLoc("block/rice_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 3).modelForState().modelFile(models().crop("rice_2", modLoc("block/rice_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 4).modelForState().modelFile(models().crop("rice_3", modLoc("block/rice_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 5).modelForState().modelFile(models().crop("rice_3", modLoc("block/rice_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 6).modelForState().modelFile(models().crop("rice_3", modLoc("block/rice_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 7).modelForState().modelFile(models().crop("rice_4", modLoc("block/rice_stage_4")).renderType("minecraft:cutout")).addModel();

        getVariantBuilder(ActuallyBlocks.FLAX.get()).partialState()
            .with(CropBlock.AGE, 0).modelForState().modelFile(models().crop("flax_1", modLoc("block/flax_stage_1")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 1).modelForState().modelFile(models().crop("flax_2", modLoc("block/flax_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 2).modelForState().modelFile(models().crop("flax_2", modLoc("block/flax_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 3).modelForState().modelFile(models().crop("flax_2", modLoc("block/flax_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 4).modelForState().modelFile(models().crop("flax_3", modLoc("block/flax_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 5).modelForState().modelFile(models().crop("flax_3", modLoc("block/flax_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 6).modelForState().modelFile(models().crop("flax_3", modLoc("block/flax_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 7).modelForState().modelFile(models().crop("flax_4", modLoc("block/flax_stage_4")).renderType("minecraft:cutout")).addModel();

        getVariantBuilder(ActuallyBlocks.COFFEE.get()).partialState()
            .with(CropBlock.AGE, 0).modelForState().modelFile(models().crop("coffee_1", modLoc("block/coffee_stage_1")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 1).modelForState().modelFile(models().crop("coffee_2", modLoc("block/coffee_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 2).modelForState().modelFile(models().crop("coffee_2", modLoc("block/coffee_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 3).modelForState().modelFile(models().crop("coffee_2", modLoc("block/coffee_stage_2")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 4).modelForState().modelFile(models().crop("coffee_3", modLoc("block/coffee_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 5).modelForState().modelFile(models().crop("coffee_3", modLoc("block/coffee_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 6).modelForState().modelFile(models().crop("coffee_3", modLoc("block/coffee_stage_3")).renderType("minecraft:cutout")).addModel()
            .partialState().with(CropBlock.AGE, 7).modelForState().modelFile(models().crop("coffee_4", modLoc("block/coffee_stage_4")).renderType("minecraft:cutout")).addModel();


        buildCubeAll(ActuallyBlocks.ENDER_CASING);
        buildCubeAll(ActuallyBlocks.IRON_CASING);
        standardBlock(ActuallyBlocks.LAVA_FACTORY_CASING);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTZ_ORE);
        buildCubeAll(ActuallyBlocks.WOOD_CASING);

        // Quartz
        standardBlock(ActuallyBlocks.BLACK_QUARTZ);
        buildCubeAll(ActuallyBlocks.SMOOTH_BLACK_QUARTZ);
        standardBlock(ActuallyBlocks.CHISELED_BLACK_QUARTZ);
        standardBlock(ActuallyBlocks.BLACK_QUARTZ_PILLAR);
        buildCubeAll(ActuallyBlocks.BLACK_QUARTZ_BRICK);
        buildCubeAll(ActuallyBlocks.ETHETIC_GREEN_BLOCK);
        buildCubeAll(ActuallyBlocks.ETHETIC_WHITE_BLOCK);

        // Walls
        wallBlock((WallBlock) ActuallyBlocks.BLACK_QUARTZ_WALL.get(), modLoc("block/black_quartz_block"));
        wallBlock((WallBlock) ActuallyBlocks.SMOOTH_BLACK_QUARTZ_WALL.get(), modLoc("block/smooth_black_quartz_block"));
        wallBlock((WallBlock) ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.get(), modLoc("block/chiseled_black_quartz_block"));
        wallBlock((WallBlock) ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.get(), modLoc("block/black_quartz_pillar_block"));
        wallBlock((WallBlock) ActuallyBlocks.BLACK_QUARTZ_BRICK_WALL.get(), modLoc("block/black_quartz_brick_block"));
        wallBlock((WallBlock) ActuallyBlocks.ETHETIC_GREEN_WALL.get(), modLoc("block/ethetic_green_block"));
        wallBlock((WallBlock) ActuallyBlocks.ETHETIC_WHITE_WALL.get(), modLoc("block/ethetic_white_block"));

        // Stairs
        stairsBlock((StairBlock) ActuallyBlocks.BLACK_QUARTZ_STAIR.get(), modLoc("block/black_quartz_block"));
        stairsBlock((StairBlock) ActuallyBlocks.SMOOTH_BLACK_QUARTZ_STAIR.get(), modLoc("block/smooth_black_quartz_block"));
        stairsBlock((StairBlock) ActuallyBlocks.CHISELED_BLACK_QUARTZ_STAIR.get(), modLoc("block/chiseled_black_quartz_block"));
        stairsBlock((StairBlock) ActuallyBlocks.BLACK_QUARTZ_PILLAR_STAIR.get(), modLoc("block/black_quartz_pillar_block"));
        stairsBlock((StairBlock) ActuallyBlocks.BLACK_QUARTZ_BRICK_STAIR.get(), modLoc("block/black_quartz_brick_block"));
        stairsBlock((StairBlock) ActuallyBlocks.ETHETIC_GREEN_STAIRS.get(), modLoc("block/ethetic_green_block"));
        stairsBlock((StairBlock) ActuallyBlocks.ETHETIC_WHITE_STAIRS.get(), modLoc("block/ethetic_white_block"));

        // Slabs
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_QUARTZ_SLAB.get(), modLoc("block/black_quartz_block"), modLoc("block/black_quartz_block"));
        slabBlock((SlabBlock) ActuallyBlocks.SMOOTH_BLACK_QUARTZ_SLAB.get(), modLoc("block/smooth_black_quartz_block"), modLoc("block/smooth_black_quartz_block"));
        slabBlock((SlabBlock) ActuallyBlocks.CHISELED_BLACK_QUARTZ_SLAB.get(), modLoc("block/chiseled_black_quartz_block"), modLoc("block/chiseled_black_quartz_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_QUARTZ_PILLAR_SLAB.get(), modLoc("block/black_quartz_pillar_block"), modLoc("block/black_quartz_pillar_block"));
        slabBlock((SlabBlock) ActuallyBlocks.BLACK_QUARTZ_BRICK_SLAB.get(), modLoc("block/black_quartz_brick_block"), modLoc("block/black_quartz_brick_block"));
        slabBlock((SlabBlock) ActuallyBlocks.ETHETIC_GREEN_SLAB.get(), modLoc("block/ethetic_green_block"), modLoc("block/ethetic_green_block"));
        slabBlock((SlabBlock) ActuallyBlocks.ETHETIC_WHITE_SLAB.get(), modLoc("block/ethetic_white_block"), modLoc("block/ethetic_white_block"));
    }


    private void buildCubeAll(Supplier<Block> block) {
        simpleBlock(block.get());
    }

    private void standardBlock(Supplier<Block> block) {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block.get());
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name.getPath()));

        assert name != null;
        simpleBlock(block.get(), model);
    }

    private void standardBlockWithCube(Supplier<Block> block) {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block.get());
        assert name != null;

        ModelFile model = models().cubeAll(name.toString(), modLoc("block/" + name.getPath()));
        simpleBlock(block.get(), model);
    }

    private void buildLitState(Supplier<Block> block) {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block.get());
        ModelFile offModel = models().withExistingParent(name.toString(), "cube_all")
            .texture("all", modLoc("block/" + name.getPath()));
        ModelFile onModel = models().withExistingParent(name.toString() + "_on", "cube_all")
            .texture("all", modLoc("block/" + name.getPath() + "_on"));


        getVariantBuilder(block.get())
            .partialState().with(BlockStateProperties.LIT, false)
            .modelForState().modelFile(offModel).addModel()
            .partialState().with(BlockStateProperties.LIT, true)
            .modelForState().modelFile(onModel).addModel();
    }

    private void fullyDirectionalBlock(Supplier<Block> block) {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block.get());
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name.getPath()));
        ModelFile verModel = new ModelFile.UncheckedModelFile(modLoc("block/" + name.getPath() + "_ver"));

        assert name != null;
        directionalBlock(block.get(), model);
    }

    private void horizontallyDirectionalBlock(Supplier<Block> block) {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block.get());
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name.getPath()));

        assert name != null;
        horizontalBlock(block.get(), model);
    }

    private void tinyTorchBlock(Supplier<Block> block) {
        assert block.get() instanceof BlockTinyTorch;
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block.get());
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name.getPath()));
        ModelFile wallModel = new ModelFile.UncheckedModelFile(modLoc("block/" + name.getPath() + "_wall"));

        assert name != null;
        getVariantBuilder(block.get())
                .partialState().with(BlockTinyTorch.FACING, Direction.UP)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockTinyTorch.FACING, Direction.EAST)
                .modelForState().modelFile(wallModel).addModel()
                .partialState().with(BlockTinyTorch.FACING, Direction.NORTH)
                .modelForState().modelFile(wallModel).rotationY(270).addModel()
                .partialState().with(BlockTinyTorch.FACING, Direction.SOUTH)
                .modelForState().modelFile(wallModel).rotationY(90).addModel()
                .partialState().with(BlockTinyTorch.FACING, Direction.WEST)
                .modelForState().modelFile(wallModel).rotationY(180).addModel();
    }
}
