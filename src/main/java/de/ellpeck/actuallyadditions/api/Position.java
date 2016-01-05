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
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
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
        return world != null ? world.getTileEntity(this.x, this.y, this.z) : null;
    }

    public Material getMaterial(World world){
        return world != null ? world.getBlock(this.x, this.y, this.z).getMaterial() : null;
    }

    public Item getItemBlock(World world){
        return world != null ? Item.getItemFromBlock(this.getBlock(world)) : null;
    }

    public Block getBlock(World world){
        return world != null ? world.getBlock(this.x, this.y, this.z) : null;
    }

    public int getMetadata(World world){
        return world != null ? world.getBlockMetadata(this.x, this.y, this.z) : 0;
    }

    public void setMetadata(World world, int meta, int flag){
        if(world != null){
            world.setBlockMetadataWithNotify(this.x, this.y, this.z, meta, flag);
        }
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

    public void setBlock(World world, Block block, int meta, int flag){
        if(world != null){
            world.setBlock(this.x, this.y, this.z, block, meta, flag);
        }
    }

    public Position copy(){
        return new Position(this.x, this.y, this.z);
    }

    public String toString(){
        return "["+this.x+", "+this.y+", "+this.z+"]";
    }

    public Vec3 toVec(){
        return Vec3.createVectorHelper(this.x, this.y, this.z);
    }
}
