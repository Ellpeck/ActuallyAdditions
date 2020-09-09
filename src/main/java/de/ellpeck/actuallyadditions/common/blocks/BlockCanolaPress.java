package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityCanolaPress;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCanolaPress extends BlockContainerBase {

    public BlockCanolaPress() {
        super(STONE_PROPS);
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


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityCanolaPress();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntityCanolaPress press = (TileEntityCanolaPress) world.getTileEntity(pos);
            if (press != null) {
                if (!this.tryUseItemOnTank(player, hand, press.tank)) {
                    // todo: add back
//                    player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.CANOLA_PRESS.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }
}
