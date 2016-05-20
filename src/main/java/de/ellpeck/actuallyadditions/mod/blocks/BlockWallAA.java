/*
 * This file ("BlockWallAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockWallAA extends BlockBase{

    protected static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[]{new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.875D, 0.6875D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    protected static final AxisAlignedBB[] CLIP_AABB_BY_INDEX = new AxisAlignedBB[]{AABB_BY_INDEX[0].setMaxY(1.5D), AABB_BY_INDEX[1].setMaxY(1.5D), AABB_BY_INDEX[2].setMaxY(1.5D), AABB_BY_INDEX[3].setMaxY(1.5D), AABB_BY_INDEX[4].setMaxY(1.5D), AABB_BY_INDEX[5].setMaxY(1.5D), AABB_BY_INDEX[6].setMaxY(1.5D), AABB_BY_INDEX[7].setMaxY(1.5D), AABB_BY_INDEX[8].setMaxY(1.5D), AABB_BY_INDEX[9].setMaxY(1.5D), AABB_BY_INDEX[10].setMaxY(1.5D), AABB_BY_INDEX[11].setMaxY(1.5D), AABB_BY_INDEX[12].setMaxY(1.5D), AABB_BY_INDEX[13].setMaxY(1.5D), AABB_BY_INDEX[14].setMaxY(1.5D), AABB_BY_INDEX[15].setMaxY(1.5D)};

    private final int meta;

    public BlockWallAA(String name, Block base){
        this(name, base, 0);
    }


    @SuppressWarnings("deprecation")
    public BlockWallAA(String name, Block base, int meta){
        super(base.getMaterial(base.getDefaultState()), name);
        this.meta = meta;

        this.setHardness(1.5F);
        this.setResistance(10F);
        this.setSoundType(base.getSoundType());

        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWall.UP, false).withProperty(BlockWall.NORTH, false).withProperty(BlockWall.EAST, false).withProperty(BlockWall.SOUTH, false).withProperty(BlockWall.WEST, false));
    }

    private static int yesThisIsCopyPastedFromBlockWallAndIHaveNoIdeaWhatThisMethodDoes(IBlockState state){
        int i = 0;

        if(state.getValue(BlockWall.NORTH)){
            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }

        if(state.getValue(BlockWall.EAST)){
            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }

        if(state.getValue(BlockWall.SOUTH)){
            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }

        if(state.getValue(BlockWall.WEST)){
            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }

        return i;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos){
        boolean flag = this.canConnectTo(worldIn, pos.north());
        boolean flag1 = this.canConnectTo(worldIn, pos.east());
        boolean flag2 = this.canConnectTo(worldIn, pos.south());
        boolean flag3 = this.canConnectTo(worldIn, pos.west());
        boolean flag4 = flag && !flag1 && flag2 && !flag3 || !flag && flag1 && !flag2 && flag3;
        return state.withProperty(BlockWall.UP, !flag4 || !worldIn.isAirBlock(pos.up())).withProperty(BlockWall.NORTH, flag).withProperty(BlockWall.EAST, flag1).withProperty(BlockWall.SOUTH, flag2).withProperty(BlockWall.WEST, flag3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos){
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, EnumFacing side){
        return side != EnumFacing.DOWN || super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        state = this.getActualState(state, source, pos);
        return AABB_BY_INDEX[yesThisIsCopyPastedFromBlockWallAndIHaveNoIdeaWhatThisMethodDoes(state)];
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @Nonnull World worldIn, @Nonnull BlockPos pos){
        blockState = this.getActualState(blockState, worldIn, pos);
        return CLIP_AABB_BY_INDEX[yesThisIsCopyPastedFromBlockWallAndIHaveNoIdeaWhatThisMethodDoes(blockState)];
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public int damageDropped(IBlockState state){
        return this.meta;
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(@Nonnull Item item, CreativeTabs tab, List list){
        list.add(new ItemStack(item, 1, 0));
    }

    @SuppressWarnings("deprecation")
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos){
        Block block = PosUtil.getBlock(pos, worldIn);
        IBlockState state = worldIn.getBlockState(pos);
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof BlockFenceGate)) || ((block.getMaterial(state).isOpaque() && block.isFullCube(state)) && block.getMaterial(state) != Material.GOURD));
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return 0;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockWall.UP, BlockWall.NORTH, BlockWall.EAST, BlockWall.WEST, BlockWall.SOUTH);
    }
}
