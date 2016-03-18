/*
 * This file ("BlockWallAA.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateBase;
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

import java.util.List;

public class BlockWallAA extends BlockBase{

    private int meta;

    public BlockWallAA(String name, Block base){
        this(name, base, 0);
    }

    protected static final AxisAlignedBB[] field_185751_g = new AxisAlignedBB[]{new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.875D, 0.6875D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    protected static final AxisAlignedBB[] field_185750_B = new AxisAlignedBB[]{field_185751_g[0].setMaxY(1.5D), field_185751_g[1].setMaxY(1.5D), field_185751_g[2].setMaxY(1.5D), field_185751_g[3].setMaxY(1.5D), field_185751_g[4].setMaxY(1.5D), field_185751_g[5].setMaxY(1.5D), field_185751_g[6].setMaxY(1.5D), field_185751_g[7].setMaxY(1.5D), field_185751_g[8].setMaxY(1.5D), field_185751_g[9].setMaxY(1.5D), field_185751_g[10].setMaxY(1.5D), field_185751_g[11].setMaxY(1.5D), field_185751_g[12].setMaxY(1.5D), field_185751_g[13].setMaxY(1.5D), field_185751_g[14].setMaxY(1.5D), field_185751_g[15].setMaxY(1.5D)};


    public BlockWallAA(String name, Block base, int meta){
        super(base.getMaterial(base.getDefaultState()), name);
        this.meta = meta;

        this.setHardness(1.5F);
        this.setResistance(10F);
        this.setStepSound(base.getStepSound());

        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWall.UP, false).withProperty(BlockWall.NORTH, false).withProperty(BlockWall.EAST, false).withProperty(BlockWall.SOUTH, false).withProperty(BlockWall.WEST, false));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        return state.withProperty(BlockWall.UP, !worldIn.isAirBlock(pos.up())).withProperty(BlockWall.NORTH, this.canConnectTo(worldIn, pos.north())).withProperty(BlockWall.EAST, this.canConnectTo(worldIn, pos.east())).withProperty(BlockWall.SOUTH, this.canConnectTo(worldIn, pos.south())).withProperty(BlockWall.WEST, this.canConnectTo(worldIn, pos.west()));
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return side != EnumFacing.DOWN || super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        state = this.getActualState(state, source, pos);
        return field_185751_g[figureOutSomeWallStuff(state)];
    }

    public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos){
        blockState = this.getActualState(blockState, worldIn, pos);
        return field_185750_B[figureOutSomeWallStuff(blockState)];
    }

    private static int figureOutSomeWallStuff(IBlockState state){
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

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public int damageDropped(IBlockState state){
        return meta;
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        list.add(new ItemStack(item, 1, 0));
    }

    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos){
        Block block = PosUtil.getBlock(pos, worldIn);
        IBlockState state = worldIn.getBlockState(pos);
        return block != Blocks.barrier && (!(block != this && !(block instanceof BlockFenceGate)) || ((block.getMaterial(state).isOpaque() && block.isFullCube(state)) && block.getMaterial(state) != Material.gourd));
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockWall.UP, BlockWall.NORTH, BlockWall.EAST, BlockWall.WEST, BlockWall.SOUTH);
    }
}
