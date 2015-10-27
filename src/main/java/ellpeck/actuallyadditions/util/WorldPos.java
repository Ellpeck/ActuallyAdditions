/*
 * This file ("WorldPos.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class WorldPos{

    private int x;
    private int y;
    private int z;
    private int worldID;

    public WorldPos(World world, int x, int y, int z){
        this(world.provider.dimensionId, x, y, z);
    }

    public WorldPos(int worldID, int x, int y, int z){
        this.worldID = worldID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getWorldID(){
        return this.worldID;
    }

    public TileEntity getTileEntity(){
        return this.getWorld() != null ? this.getWorld().getTileEntity(this.x, this.y, this.z) : null;
    }

    public World getWorld(){
        return DimensionManager.getWorld(this.worldID);
    }

    public Material getMaterial(){
        return this.getWorld() != null ? this.getWorld().getBlock(this.x, this.y, this.z).getMaterial() : null;
    }

    public Item getItemBlock(){
        return this.getWorld() != null ? Item.getItemFromBlock(this.getBlock()) : null;
    }

    public Block getBlock(){
        return this.getWorld() != null ? this.getWorld().getBlock(this.x, this.y, this.z) : null;
    }

    public int getMetadata(){
        return this.getWorld() != null ? this.getWorld().getBlockMetadata(this.x, this.y, this.z) : 0;
    }

    public boolean isEqual(WorldPos pos){
        return pos != null && this.x == pos.getX() && this.y == pos.getY() && this.z == pos.getZ() && this.getWorld() == pos.getWorld();
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

    public void update(){
        if(this.getWorld() != null){
            this.getWorld().markBlockForUpdate(this.x, this.y, this.z);
        }
    }

    public WorldPos copy(){
        return new WorldPos(this.getWorld(), this.x, this.y, this.z);
    }

    public String toString(){
        return "["+this.x+", "+this.y+", "+this.z+" in world "+this.worldID+"]";
    }

    public Vec3 toVec(){
        return Vec3.createVectorHelper(this.x, this.y, this.z);
    }
}
