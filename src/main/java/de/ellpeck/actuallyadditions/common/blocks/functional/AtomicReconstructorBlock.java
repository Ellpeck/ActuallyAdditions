package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.BlockShapes;
import de.ellpeck.actuallyadditions.common.blocks.FullyDirectionalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class AtomicReconstructorBlock extends FullyDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public AtomicReconstructorBlock() {
        super(Properties.create(Material.ROCK)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(0)
            .hardnessAndResistance(10f, 80f)
            .sound(SoundType.STONE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case UP:
                return BlockShapes.AtomicReconstructorShape.SHAPE_U;
            case DOWN:
                return BlockShapes.AtomicReconstructorShape.SHAPE_D;
            case NORTH:
                return BlockShapes.AtomicReconstructorShape.SHAPE_N;
            case EAST:
                return BlockShapes.AtomicReconstructorShape.SHAPE_E;
            case SOUTH:
                return BlockShapes.AtomicReconstructorShape.SHAPE_S;
            case WEST:
                return BlockShapes.AtomicReconstructorShape.SHAPE_W;
            default:
                return BlockShapes.AtomicReconstructorShape.SHAPE_N;
        }
    }
}
