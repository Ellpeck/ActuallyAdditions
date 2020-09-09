package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityCoffeeMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCoffeeMachine extends BlockContainerBase {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

//    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625, 0, 0.0625, 1 - 0.0625, 1 - 0.0625 * 2, 1 - 0.0625);
    private static final VoxelShape SHAPE = Block.makeCuboidShape(1, 0, 1, 15, 14, 15); // might be wrong, correct is above

    public BlockCoffeeMachine() {
        super(STONE_PROPS);

        setDefaultState(getStateContainer().getBaseState().with(FACING, Direction.SOUTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

//    @Override
//    public boolean isFullCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean isOpaqueCube(IBlockState state) {
//        return false;
//    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntityCoffeeMachine machine = (TileEntityCoffeeMachine) world.getTileEntity(pos);
            if (machine != null) {
                if (!this.tryUseItemOnTank(player, hand, machine.tank)) {
                    // todo: add back
//                    player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.COFFEE_MACHINE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return ActionResultType.SUCCESS;
        }

        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityCoffeeMachine();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

//    @Override
//    public IBlockState withRotation(IBlockState state, Rotation rot) {
//        return state.withProperty(BlockHorizontal.FACING, rot.rotate(state.getValue(BlockHorizontal.FACING)));
//    }
//
//    @Override
//    public IBlockState withMirror(IBlockState state, Mirror mirror) {
//        return this.withRotation(state, mirror.toRotation(state.getValue(BlockHorizontal.FACING)));
//    }
}
