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
import net.minecraft.world.World;

/**
 * This utility class describes a position in the world
 */
public class Position{

    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TileEntity getTileEntity(World world){
        return world != null ? world.getTileEntity(this.toBlockPos()) : null;
    }

    public Material getMaterial(World world){
        if(world != null){
            Block block = this.getBlock(world);
            if(block != null){
                return block.getMaterial();
            }
        }
        return null;
    }

    public Item getItemBlock(World world){
        return world != null ? Item.getItemFromBlock(this.getBlock(world)) : null;
    }

    public Block getBlock(World world){
        if(world != null){
            IBlockState state = this.getBlockState(world);
            if(state != null){
                return state.getBlock();
            }
        }
        return null;
    }

    public int getMetadata(World world){
        //TODO Fix meta
        return /*world != null ? world.getBlockMetadata(this.x, this.y, this.z) : */0;
    }

    public void setMetadata(World world, int meta, int flag){
        //TODO Fix meta
        /*if(world != null){
            world.setBlockMetadataWithNotify(this.x, this.y, this.z, meta, flag);
        }*/
    }

    public boolean isEqual(Position pos){
        return pos != null && this.x == pos.getX() && this.y == pos.getY() && this.z == pos.getZ();
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getZ(){
        return this.z;
    }

    public boolean setBlock(World world, Block block, int meta, int flag){
        //TODO Fix meta
        return world != null && this.setBlockState(world, block.getDefaultState(), meta, flag);
    }

    public boolean setBlockState(World world, IBlockState state, int meta, int flag){
        //TODO Fix meta
        return world.setBlockState(this.toBlockPos(), state, flag);
    }

    public Position copy(){
        return new Position(this.x, this.y, this.z);
    }

    public String toString(){
        return "["+this.x+", "+this.y+", "+this.z+"]";
    }

    public Vec3 toVec(){
        return new Vec3(this.x, this.y, this.z);
    }

    public BlockPos toBlockPos(){
        return new BlockPos(this.x, this.y, this.z);
    }

    public IBlockState getBlockState(World world){
        return world != null ? world.getBlockState(this.toBlockPos()) : null;
    }

    public Position getOffsetPosition(EnumFacing side){
        return new Position(this.x+side.getFrontOffsetX(), this.y+side.getFrontOffsetY(), this.z+side.getFrontOffsetZ());
    }

    public static Position fromTileEntity(TileEntity tile){
        BlockPos pos = tile.getPos();
        return new Position(pos.getX(), pos.getY(), pos.getZ());
    }
}
