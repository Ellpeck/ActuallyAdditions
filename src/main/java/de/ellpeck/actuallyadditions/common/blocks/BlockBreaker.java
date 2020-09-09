package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBreaker;
import de.ellpeck.actuallyadditions.common.tile.TileEntityPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBreaker extends BlockContainerBase {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private final boolean isPlacer;

    public BlockBreaker(boolean isPlacer) {
        super(STONE_PROPS);
        this.isPlacer = isPlacer;

        setDefaultState(getStateContainer().getBaseState().with(FACING, Direction.SOUTH));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return this.isPlacer ? new TileEntityPlacer() : new TileEntityBreaker();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (this.tryToggleRedstone(world, pos, player)) { return ActionResultType.SUCCESS; }
        if (!world.isRemote) {
            TileEntityBreaker breaker = (TileEntityBreaker) world.getTileEntity(pos);
            if (breaker != null) {
                // todo: come back to this once we have guis
//                NetworkHooks.openGui(player, new SimpleNamedContainerProvider(());
//                player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.BREAKER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        // @todo: might be wrong
        return getDefaultState().with(FACING, context.getFace().getOpposite());
    }

//    @Override
//    public IBlockState withRotation(IBlockState state, Rotation rot) {
//        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
//    }
//
//    @Override
//    public IBlockState withMirror(IBlockState state, Mirror mirror) {
//        return this.withRotation(state, mirror.toRotation(state.getValue(BlockDirectional.FACING)));
//    }
}
