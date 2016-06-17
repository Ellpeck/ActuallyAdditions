/*
 * This file ("PosUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public final class PosUtil{

    public static Material getMaterial(BlockPos pos, IBlockAccess world){
        return getBlock(pos, world).getMaterial(world.getBlockState(pos));
    }

    public static Block getBlock(BlockPos pos, IBlockAccess world){
        if(pos != null){
            IBlockState state = world.getBlockState(pos);
            if(state != null){
                return state.getBlock();
            }
        }
        return null;
    }

    public static int getMetadata(BlockPos pos, IBlockAccess world){
        Block block = getBlock(pos, world);
        return block != null ? block.getMetaFromState(world.getBlockState(pos)) : 0;
    }

    public static int getMetadata(IBlockState state){
        Block block = state.getBlock();
        return block != null ? block.getMetaFromState(state) : 0;
    }

    public static BlockPos offset(BlockPos pos, int x, int y, int z){
        return new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
    }

    public static boolean setBlock(BlockPos pos, World world, Block block, int meta, int flag){
        return world.setBlockState(pos, block.getStateFromMeta(meta), flag);
    }

    public static Vec3d toVec(BlockPos pos){
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }

    public static BlockPos copyPos(BlockPos pos){
        return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public static ItemBlock getItemBlock(BlockPos pos, IBlockAccess world){
        Item item = Item.getItemFromBlock(getBlock(pos, world));
        if(item instanceof ItemBlock){
            return (ItemBlock)item;
        }
        return null;
    }

    public static void setMetadata(BlockPos pos, World world, int meta, int flag){
        world.setBlockState(pos, getBlock(pos, world).getStateFromMeta(meta), flag);
    }

    public static boolean areSamePos(BlockPos first, BlockPos second){
        return first.getX() == second.getX() && first.getY() == second.getY() && first.getZ() == second.getZ();
    }
}
