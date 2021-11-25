package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ActuallyAdditions.MODID, exFileHelper);
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
        //horizontallyDirectionalBlock(ActuallyBlocks.BIOREACTOR);
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
        standardBlock(ActuallyBlocks.RESTONIA_CRYSTAL_CLUSTER);
        standardBlock(ActuallyBlocks.PALIS_CRYSTAL_CLUSTER);
        standardBlock(ActuallyBlocks.DIAMATINE_CRYSTAL_CLUSTER);
        standardBlock(ActuallyBlocks.VOID_CRYSTAL_CLUSTER);
        standardBlock(ActuallyBlocks.EMERADIC_CRYSTAL_CLUSTER);
        standardBlock(ActuallyBlocks.ENORI_CRYSTAL_CLUSTER);
        standardBlock(ActuallyBlocks.BATTERY_BOX);
        standardBlock(ActuallyBlocks.ITEM_INTERFACE_HOPPING);
        standardBlock(ActuallyBlocks.EMPOWERER);
        standardBlock(ActuallyBlocks.TINY_TORCH);
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
        standardBlock(ActuallyBlocks.LASER_RELAY);
        standardBlock(ActuallyBlocks.LASER_RELAY_ADVANCED);
        standardBlock(ActuallyBlocks.LASER_RELAY_EXTREME);
        standardBlock(ActuallyBlocks.LASER_RELAY_FLUIDS);
        standardBlock(ActuallyBlocks.LASER_RELAY_ITEM);
        standardBlock(ActuallyBlocks.LASER_RELAY_ITEM_ADVANCED);
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
        buildCubeAll(ActuallyBlocks.RICE);
        buildCubeAll(ActuallyBlocks.CANOLA);
        buildCubeAll(ActuallyBlocks.FLAX);
        buildCubeAll(ActuallyBlocks.COFFEE);

        //buildCubeAll(ActuallyBlocks.CHARCOAL); //TODO hmm?
        buildCubeAll(ActuallyBlocks.ENDER_CASING);
        buildCubeAll(ActuallyBlocks.ENDER_PEARL_BLOCK);
        buildCubeAll(ActuallyBlocks.IRON_CASING);
        standardBlock(ActuallyBlocks.LAVA_FACTORY_CASING);
        //buildCubeAll(ActuallyBlocks.ORE_BLACK_QUARTZ); //TODO HMM
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
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_QUARTZ_STAIR.get(), modLoc("block/black_quartz_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.SMOOTH_BLACK_QUARTZ_STAIR.get(), modLoc("block/smooth_black_quartz_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.CHISELED_BLACK_QUARTZ_STAIR.get(), modLoc("block/chiseled_black_quartz_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_QUARTZ_PILLAR_STAIR.get(), modLoc("block/black_quartz_pillar_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.BLACK_QUARTZ_BRICK_STAIR.get(), modLoc("block/black_quartz_brick_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.ETHETIC_GREEN_STAIRS.get(), modLoc("block/ethetic_green_block"));
        stairsBlock((StairsBlock) ActuallyBlocks.ETHETIC_WHITE_STAIRS.get(), modLoc("block/ethetic_white_block"));

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
        ResourceLocation name = block.get().getRegistryName();
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name.getPath()));

        assert name != null;
        simpleBlock(block.get(), model);
    }

    private void standardBlockWithCube(Supplier<Block> block) {
        ResourceLocation name = block.get().getRegistryName();
        assert name != null;

        ModelFile model = models().cubeAll(name.toString(), modLoc("block/" + name.getPath()));
        simpleBlock(block.get(), model);
    }

    private void buildLitState(Supplier<Block> block) {
        ResourceLocation name = block.get().getRegistryName();
        assert name != null;

        getVariantBuilder(block.get())
            .partialState().with(BlockStateProperties.LIT, false)
            .addModels(ConfiguredModel.builder().modelFile(models().cubeAll(name.toString(), modLoc("block/" + name.getPath()))).build())
            .partialState().with(BlockStateProperties.LIT, true)
            .addModels(ConfiguredModel.builder().modelFile(models().cubeAll(name.toString(), modLoc("block/" + name.getPath() + "_on"))).build());
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
