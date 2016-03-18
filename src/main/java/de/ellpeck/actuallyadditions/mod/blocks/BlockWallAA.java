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
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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

    public BlockWallAA(String name, Block base, int meta){
        super(base.getMaterial(), name);
        this.meta = meta;

        this.setHardness(1.5F);
        this.setResistance(10F);
        this.setStepSound(base.stepSound);

        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWall.UP, false).withProperty(BlockWall.NORTH, false).withProperty(BlockWall.EAST, false).withProperty(BlockWall.SOUTH, false).withProperty(BlockWall.WEST, false));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        return state.withProperty(BlockWall.UP, !worldIn.isAirBlock(pos.up())).withProperty(BlockWall.NORTH, this.canConnectTo(worldIn, pos.north())).withProperty(BlockWall.EAST, this.canConnectTo(worldIn, pos.east())).withProperty(BlockWall.SOUTH, this.canConnectTo(worldIn, pos.south())).withProperty(BlockWall.WEST, this.canConnectTo(worldIn, pos.west()));
    }

    @Override
    public boolean isFullCube(){
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side){
        return side != EnumFacing.DOWN || super.shouldSideBeRendered(worldIn, pos, side);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state){
        this.setBlockBoundsBasedOnState(worldIn, pos);
        this.maxY = 1.5D;
        return super.getCollisionBoundingBox(worldIn, pos, state);
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public int damageDropped(IBlockState state){
        return meta;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos){
        boolean flag = this.canConnectTo(worldIn, pos.north());
        boolean flag1 = this.canConnectTo(worldIn, pos.south());
        boolean flag2 = this.canConnectTo(worldIn, pos.west());
        boolean flag3 = this.canConnectTo(worldIn, pos.east());
        float f = 0.25F;
        float f1 = 0.75F;
        float f2 = 0.25F;
        float f3 = 0.75F;
        float f4 = 1.0F;

        if(flag){
            f2 = 0.0F;
        }
        if(flag1){
            f3 = 1.0F;
        }
        if(flag2){
            f = 0.0F;
        }
        if(flag3){
            f1 = 1.0F;
        }

        if(flag && flag1 && !flag2 && !flag3){
            f4 = 0.8125F;
            f = 0.3125F;
            f1 = 0.6875F;
        }
        else if(!flag && !flag1 && flag2 && flag3){
            f4 = 0.8125F;
            f2 = 0.3125F;
            f3 = 0.6875F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, f4, f3);
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        list.add(new ItemStack(item, 1, 0));
    }

    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos){
        Block block = PosUtil.getBlock(pos, worldIn);
        return block != Blocks.barrier && (!(block != this && !(block instanceof BlockFenceGate)) || ((block.getMaterial().isOpaque() && block.isFullCube()) && block.getMaterial() != Material.gourd));
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
    protected BlockState createBlockState(){
        return new BlockState(this, BlockWall.UP, BlockWall.NORTH, BlockWall.EAST, BlockWall.WEST, BlockWall.SOUTH);
    }
}
