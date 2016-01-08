/*
 * This file ("PosUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PosUtil{

    public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);

    public static Block getBlock(BlockPos pos, IBlockAccess world){
        return world.getBlockState(pos).getBlock();
    }

    public static Material getMaterial(BlockPos pos, IBlockAccess world){
        return getBlock(pos, world).getMaterial();
    }

    public static int getMetadata(BlockPos pos, IBlockAccess world){
        return getBlock(pos, world).getMetaFromState(world.getBlockState(pos));
    }

    public static BlockPos offset(BlockPos pos, int x, int y, int z){
        return new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
    }

    public static boolean setBlock(BlockPos pos, World world, Block block, int meta, int flag){
        return world.setBlockState(pos, block.getStateFromMeta(meta), flag);
    }

    public static Vec3 toVec(BlockPos pos){
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
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
