package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.BlockShapes;
import de.ellpeck.actuallyadditions.common.blocks.HorizontallyDirectionalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BioReactorBlock extends HorizontallyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BioReactorBlock() {
        super(Properties.create(Material.ROCK));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return BlockShapes.BioReactorShape.SHAPE_N;
            case EAST:
                return BlockShapes.BioReactorShape.SHAPE_E;
            case SOUTH:
                return BlockShapes.BioReactorShape.SHAPE_S;
            case WEST:
                return BlockShapes.BioReactorShape.SHAPE_W;
            default:
                return BlockShapes.BioReactorShape.SHAPE_N;
        }
    }
}
