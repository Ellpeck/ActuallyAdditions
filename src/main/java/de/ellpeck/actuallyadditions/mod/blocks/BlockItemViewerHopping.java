/*
 * This file ("BlockItemViewerHopping.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewerHopping;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.List;

//Most of this is just copied from BlockHopper, no credit taken. Or clue what it is.
public class BlockItemViewerHopping extends BlockItemViewer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", facing -> facing != Direction.UP);

    private static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D);
    private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public BlockItemViewerHopping() {
        super(this.name);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    @Deprecated
    public void addCollisionBoxToList(BlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean someBool) {
        this.addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_AABB);
        this.addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
        this.addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
        this.addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
        this.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityItemViewerHopping();
    }

    @Override
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        Direction opp = facing.getOpposite();
        return this.getDefaultState().withProperty(FACING, opp == Direction.UP
            ? Direction.DOWN
            : opp);
    }

    @Override //was isFullyOpaque, not sure if correct change.
    public boolean isNormalCube(BlockState state) {
        return true;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean shouldSideBeRendered(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
        return true;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, Direction.byIndex(meta));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirror) {
        return this.withRotation(state, mirror.toRotation(state.getValue(FACING)));
    }
}
