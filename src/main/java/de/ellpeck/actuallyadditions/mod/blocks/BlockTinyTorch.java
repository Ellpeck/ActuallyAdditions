/*
 * This file ("BlockTinyTorch.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;

//Copied from BlockTorch.
//I have no idea what all of this means.
public class BlockTinyTorch extends BlockBase {

    //Thanks to xdjackiexd for these.
    //Man, I hate numbers.
    private static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.3125D, 0.5625D);
    private static final AxisAlignedBB TORCH_NORTH_AABB = new AxisAlignedBB(0.4375D, 0.25D, 0.8125D, 0.5625D, 0.5625D, 1.0D);
    private static final AxisAlignedBB TORCH_SOUTH_AABB = new AxisAlignedBB(0.4375D, 0.25D, 0.0D, 0.5625D, 0.5625D, 0.1875D);
    private static final AxisAlignedBB TORCH_WEST_AABB = new AxisAlignedBB(0.8125D, 0.25D, 0.4375D, 1.0D, 0.5625D, 0.5625D);
    private static final AxisAlignedBB TORCH_EAST_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.4375D, 0.1875D, 0.5625D, 0.5625D);

    public BlockTinyTorch() {
        super(Properties.create(Material.MISCELLANEOUS).sound(SoundType.WOOD).hardnessAndResistance(0.0F, 0.8F));
        //        TorchBlock
        //        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTorch.FACING, Direction.UP));
        //        this.setTickRandomly(true);

    }

    // TODO: [port] add back
    //
    //    @Override
    //    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
    //        switch (state.getValue(BlockTorch.FACING)) {
    //            case EAST:
    //                return TORCH_EAST_AABB;
    //            case WEST:
    //                return TORCH_WEST_AABB;
    //            case SOUTH:
    //                return TORCH_SOUTH_AABB;
    //            case NORTH:
    //                return TORCH_NORTH_AABB;
    //            default:
    //                return STANDING_AABB;
    //        }
    //    }
    //
    //    @Nullable
    //    @Override
    //    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    //        return NULL_AABB;
    //    }
    //
    //    @Override
    //    public boolean isOpaqueCube(BlockState state) {
    //        return false;
    //    }
    //
    //    @Override
    //    public boolean isFullCube(BlockState state) {
    //        return false;
    //    }
    //
    //    @Override
    //    public boolean isNormalCube(BlockState state) {
    //        return false;
    //    }
    //
    //    @Override
    //    public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction facing) {
    //        return BlockFaceShape.UNDEFINED;
    //    }
    //
    //    private boolean canPlaceOn(World worldIn, BlockPos pos) {
    //        BlockState state = worldIn.getBlockState(pos);
    //        return state.isSideSolid(worldIn, pos, Direction.UP) || state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    //    }
    //
    //    @Override
    //    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    //        for (Direction enumfacing : BlockTorch.FACING.getAllowedValues()) {
    //            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
    //                return true;
    //            }
    //        }
    //
    //        return false;
    //    }
    //
    //    private boolean canPlaceAt(World worldIn, BlockPos pos, Direction facing) {
    //        BlockPos blockpos = pos.offset(facing.getOpposite());
    //        boolean flag = facing.getAxis().isHorizontal();
    //        return flag && worldIn.isSideSolid(blockpos, facing, true) || facing.equals(Direction.UP) && this.canPlaceOn(worldIn, blockpos);
    //    }
    //
    //    @Override
    //    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    //        if (this.canPlaceAt(worldIn, pos, facing)) {
    //            return this.getDefaultState().withProperty(BlockTorch.FACING, facing);
    //        } else {
    //            for (Direction enumfacing : Direction.Plane.HORIZONTAL) {
    //                if (worldIn.isSideSolid(pos.offset(enumfacing.getOpposite()), enumfacing, true)) {
    //                    return this.getDefaultState().withProperty(BlockTorch.FACING, enumfacing);
    //                }
    //            }
    //
    //            return this.getDefaultState();
    //        }
    //    }
    //
    //    @Override
    //    public void onBlockAdded(World worldIn, BlockPos pos, BlockState state) {
    //        this.checkForDrop(worldIn, pos, state);
    //    }
    //
    //    @Override
    //    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos otherPos) {
    //        this.onNeighborChangeInternal(worldIn, pos, state);
    //    }
    //
    //    protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, BlockState state) {
    //        if (!this.checkForDrop(worldIn, pos, state)) {
    //            return true;
    //        } else {
    //            Direction enumfacing = state.getValue(BlockTorch.FACING);
    //            Direction.Axis axis = enumfacing.getAxis();
    //            Direction enumfacing1 = enumfacing.getOpposite();
    //            boolean flag = false;
    //
    //            if (axis.isHorizontal() && !worldIn.isSideSolid(pos.offset(enumfacing1), enumfacing, true)) {
    //                flag = true;
    //            } else if (axis.isVertical() && !this.canPlaceOn(worldIn, pos.offset(enumfacing1))) {
    //                flag = true;
    //            }
    //
    //            if (flag) {
    //                this.dropBlockAsItem(worldIn, pos, state, 0);
    //                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    //                return true;
    //            } else {
    //                return false;
    //            }
    //        }
    //    }
    //
    //    protected boolean checkForDrop(World worldIn, BlockPos pos, BlockState state) {
    //        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.getValue(BlockTorch.FACING))) {
    //            return true;
    //        } else {
    //            if (worldIn.getBlockState(pos).getBlock() == this) {
    //                this.dropBlockAsItem(worldIn, pos, state, 0);
    //                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    //            }
    //
    //            return false;
    //        }
    //    }
    //
    //    @Override
    //    @OnlyIn(Dist.CLIENT)
    //    public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    //        if (rand.nextBoolean()) {
    //            Direction enumfacing = stateIn.getValue(BlockTorch.FACING);
    //            double d0 = pos.getX() + 0.5D;
    //            double d1 = pos.getY() + 0.4D;
    //            double d2 = pos.getZ() + 0.5D;
    //
    //            if (enumfacing.getAxis().isHorizontal()) {
    //                Direction enumfacing1 = enumfacing.getOpposite();
    //                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.35D * enumfacing1.getXOffset(), d1 + 0.22D, d2 + 0.35D * enumfacing1.getZOffset(), 0.0D, 0.0D, 0.0D);
    //                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.35D * enumfacing1.getXOffset(), d1 + 0.22D, d2 + 0.35D * enumfacing1.getZOffset(), 0.0D, 0.0D, 0.0D);
    //            } else {
    //                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    //                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    //            }
    //        }
    //    }
    //
    //    @Override
    //    public BlockState getStateFromMeta(int meta) {
    //        BlockState iblockstate = this.getDefaultState();
    //
    //        switch (meta) {
    //            case 1:
    //                iblockstate = iblockstate.withProperty(BlockTorch.FACING, Direction.EAST);
    //                break;
    //            case 2:
    //                iblockstate = iblockstate.withProperty(BlockTorch.FACING, Direction.WEST);
    //                break;
    //            case 3:
    //                iblockstate = iblockstate.withProperty(BlockTorch.FACING, Direction.SOUTH);
    //                break;
    //            case 4:
    //                iblockstate = iblockstate.withProperty(BlockTorch.FACING, Direction.NORTH);
    //                break;
    //            case 5:
    //            default:
    //                iblockstate = iblockstate.withProperty(BlockTorch.FACING, Direction.UP);
    //        }
    //
    //        return iblockstate;
    //    }
    //
    //    @Override
    //    public BlockRenderLayer getRenderLayer() {
    //        return BlockRenderLayer.CUTOUT;
    //    }
    //
    //    @Override
    //    public int getMetaFromState(BlockState state) {
    //        int i = 0;
    //
    //        switch (state.getValue(BlockTorch.FACING)) {
    //            case EAST:
    //                i = i | 1;
    //                break;
    //            case WEST:
    //                i = i | 2;
    //                break;
    //            case SOUTH:
    //                i = i | 3;
    //                break;
    //            case NORTH:
    //                i = i | 4;
    //                break;
    //            case DOWN:
    //            case UP:
    //            default:
    //                i = i | 5;
    //        }
    //
    //        return i;
    //    }
    //
    //    @Override
    //    public BlockState withRotation(BlockState state, Rotation rot) {
    //        return state.withProperty(BlockTorch.FACING, rot.rotate(state.getValue(BlockTorch.FACING)));
    //    }
    //
    //    @Override
    //    public BlockState withMirror(BlockState state, Mirror mirrorIn) {
    //        return state.withRotation(mirrorIn.toRotation(state.getValue(BlockTorch.FACING)));
    //    }
    //
    //    @Override
    //    protected BlockStateContainer createBlockState() {
    //        return new BlockStateContainer(this, BlockTorch.FACING);
    //    }
}
