package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityXPSolidifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BlockXPSolidifier extends BlockContainerBase {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BlockXPSolidifier() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(2.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));

        setDefaultState(getStateContainer().getBaseState().with(FACING, Direction.SOUTH));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityXPSolidifier();
    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntityXPSolidifier solidifier = (TileEntityXPSolidifier) world.getTileEntity(pos);
            if (solidifier != null) {
                // todo: add back
//                player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.XP_SOLIDIFIER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                return ActionResultType.SUCCESS;
            }
        }

        return super.onBlockActivated(state, world, pos, player, handIn, hit);
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
}
