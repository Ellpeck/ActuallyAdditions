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
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

//Copied from BlockTorch.
//I have no idea what all of this means.
public class BlockTinyTorch extends BlockBase{

    //Thanks to xdjackiexd for these.
    //Man, I hate numbers.
    private static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.3125D, 0.5625D);
    private static final AxisAlignedBB TORCH_NORTH_AABB = new AxisAlignedBB(0.4375D, 0.25D, 0.8125D, 0.5625D, 0.5625D, 1.0D);
    private static final AxisAlignedBB TORCH_SOUTH_AABB = new AxisAlignedBB(0.4375D, 0.25D, 0.0D, 0.5625D, 0.5625D, 0.1875D);
    private static final AxisAlignedBB TORCH_WEST_AABB = new AxisAlignedBB(0.8125D, 0.25D, 0.4375D, 1.0D, 0.5625D, 0.5625D);
    private static final AxisAlignedBB TORCH_EAST_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.4375D, 0.1875D, 0.5625D, 0.5625D);

    public BlockTinyTorch(String name){
        super(Material.CIRCUITS, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTorch.FACING, EnumFacing.UP));
        this.setTickRandomly(true);

        this.setHardness(0.0F);
        this.setLightLevel(0.8F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        switch(state.getValue(BlockTorch.FACING)){
            case EAST:
                return TORCH_EAST_AABB;
            case WEST:
                return TORCH_WEST_AABB;
            case SOUTH:
                return TORCH_SOUTH_AABB;
            case NORTH:
                return TORCH_NORTH_AABB;
            default:
                return STANDING_AABB;
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    private boolean canPlaceOn(World worldIn, BlockPos pos){
        IBlockState state = worldIn.getBlockState(pos);
        return state.isSideSolid(worldIn, pos, EnumFacing.UP) || state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
        for(EnumFacing enumfacing : BlockTorch.FACING.getAllowedValues()){
            if(this.canPlaceAt(worldIn, pos, enumfacing)){
                return true;
            }
        }

        return false;
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing){
        BlockPos blockpos = pos.offset(facing.getOpposite());
        boolean flag = facing.getAxis().isHorizontal();
        return flag && worldIn.isSideSolid(blockpos, facing, true) || facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        if(this.canPlaceAt(worldIn, pos, facing)){
            return this.getDefaultState().withProperty(BlockTorch.FACING, facing);
        }
        else{
            for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL){
                if(worldIn.isSideSolid(pos.offset(enumfacing.getOpposite()), enumfacing, true)){
                    return this.getDefaultState().withProperty(BlockTorch.FACING, enumfacing);
                }
            }

            return this.getDefaultState();
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
        this.checkForDrop(worldIn, pos, state);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos otherPos){
        this.onNeighborChangeInternal(worldIn, pos, state);
    }

    protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state){
        if(!this.checkForDrop(worldIn, pos, state)){
            return true;
        }
        else{
            EnumFacing enumfacing = state.getValue(BlockTorch.FACING);
            EnumFacing.Axis axis = enumfacing.getAxis();
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            boolean flag = false;

            if(axis.isHorizontal() && !worldIn.isSideSolid(pos.offset(enumfacing1), enumfacing, true)){
                flag = true;
            }
            else if(axis.isVertical() && !this.canPlaceOn(worldIn, pos.offset(enumfacing1))){
                flag = true;
            }

            if(flag){
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
                return true;
            }
            else{
                return false;
            }
        }
    }

    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state){
        if(state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.getValue(BlockTorch.FACING))){
            return true;
        }
        else{
            if(worldIn.getBlockState(pos).getBlock() == this){
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }

            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
        if(rand.nextBoolean()){
            EnumFacing enumfacing = stateIn.getValue(BlockTorch.FACING);
            double d0 = (double)pos.getX()+0.5D;
            double d1 = (double)pos.getY()+0.4D;
            double d2 = (double)pos.getZ()+0.5D;

            if(enumfacing.getAxis().isHorizontal()){
                EnumFacing enumfacing1 = enumfacing.getOpposite();
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0+0.35D*(double)enumfacing1.getFrontOffsetX(), d1+0.22D, d2+0.35D*(double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0+0.35D*(double)enumfacing1.getFrontOffsetX(), d1+0.22D, d2+0.35D*(double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
            }
            else{
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        IBlockState iblockstate = this.getDefaultState();

        switch(meta){
            case 1:
                iblockstate = iblockstate.withProperty(BlockTorch.FACING, EnumFacing.EAST);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(BlockTorch.FACING, EnumFacing.WEST);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(BlockTorch.FACING, EnumFacing.SOUTH);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(BlockTorch.FACING, EnumFacing.NORTH);
                break;
            case 5:
            default:
                iblockstate = iblockstate.withProperty(BlockTorch.FACING, EnumFacing.UP);
        }

        return iblockstate;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getMetaFromState(IBlockState state){
        int i = 0;

        switch(state.getValue(BlockTorch.FACING)){
            case EAST:
                i = i | 1;
                break;
            case WEST:
                i = i | 2;
                break;
            case SOUTH:
                i = i | 3;
                break;
            case NORTH:
                i = i | 4;
                break;
            case DOWN:
            case UP:
            default:
                i = i | 5;
        }

        return i;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(BlockTorch.FACING, rot.rotate(state.getValue(BlockTorch.FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
        return state.withRotation(mirrorIn.toRotation(state.getValue(BlockTorch.FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockTorch.FACING);
    }
}
