package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

/**
 * Wrapper for Fully Direction block states extending from our base blocks. It's not super nice but it'll do.
 */
public abstract class FullyDirectionalBlock extends BlockBase {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public FullyDirectionalBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public abstract static class Container extends BlockContainerBase {
        public static final DirectionProperty FACING = BlockStateProperties.FACING;

        public Container(Properties properties) {
            super(properties);

            this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
        }

        @Override
        public BlockState getStateForPlacement(BlockItemUseContext context) {
            return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }
    }
}
