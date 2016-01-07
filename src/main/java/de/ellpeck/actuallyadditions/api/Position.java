/*
 * This file ("Position.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * This utility class describes a position in the world
 */
public class Position extends BlockPos{

    public Position(int x, int y, int z){
        super(x, y, z);
    }

    public TileEntity getTileEntity(IBlockAccess world){
        return world != null ? world.getTileEntity(this) : null;
    }

    public Material getMaterial(IBlockAccess world){
        if(world != null){
            Block block = this.getBlock(world);
            if(block != null){
                return block.getMaterial();
            }
        }
        return null;
    }

    public Item getItemBlock(IBlockAccess world){
        return world != null ? Item.getItemFromBlock(this.getBlock(world)) : null;
    }

    public Block getBlock(IBlockAccess world){
        if(world != null){
            IBlockState state = this.getBlockState(world);
            if(state != null){
                return state.getBlock();
            }
        }
        return null;
    }

    public int getMetadata(IBlockAccess world){
        //TODO Fix meta
        return /*world != null ? world.getBlockMetadata(this.x, this.y, this.z) : */0;
    }

    public void setMetadata(IBlockAccess world, int meta, int flag){
        //TODO Fix meta
        /*if(world != null){
            world.setBlockMetadataWithNotify(this.x, this.y, this.z, meta, flag);
        }*/
    }

    public boolean isEqual(Position pos){
        return pos != null && this.getX() == pos.getX() && this.getY() == pos.getY() && this.getZ() == pos.getZ();
    }

    public boolean setBlock(World world, Block block, int meta, int flag){
        //TODO Fix meta
        return world != null && this.setBlockState(world, block.getDefaultState(), meta, flag);
    }

    public boolean setBlockState(World world, IBlockState state, int meta, int flag){
        //TODO Fix meta
        return world.setBlockState(this, state, flag);
    }

    public Position copy(){
        return new Position(this.getX(), this.getY(), this.getZ());
    }

    public String toString(){
        return "["+this.getX()+", "+this.getY()+", "+this.getZ()+"]";
    }

    public Vec3 toVec(){
        return new Vec3(this.getX(), this.getY(), this.getZ());
    }

    public IBlockState getBlockState(IBlockAccess world){
        return world != null ? world.getBlockState(this) : null;
    }

    public Position getOffsetPosition(EnumFacing side){
        return new Position(this.getX()+side.getFrontOffsetX(), this.getY()+side.getFrontOffsetY(), this.getZ()+side.getFrontOffsetZ());
    }

    public Position getOffsetPosition(int x, int y, int z){
        return new Position(this.getX()+x, this.getY()+y, this.getZ()+z);
    }

    public static Position fromTileEntity(TileEntity tile){
        return fromBlockPos(tile.getPos());
    }

    public static Position fromBlockPos(BlockPos pos){
        return (Position)pos;
    }
}
