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
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class WorldPos{

    private int x;
    private int y;
    private int z;
    private int worldID;

    public WorldPos(World world, int x, int y, int z){
        this.worldID = world.provider.dimensionId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Block getBlock(){
        return this.getWorld() != null ? this.getWorld().getBlock(this.x, this.y, this.z) : null;
    }

    public TileEntity getTileEntity(){
        return this.getWorld() != null ? this.getWorld().getTileEntity(this.x, this.y, this.z) : null;
    }

    public Material getMaterial(){
        return this.getWorld() != null ? this.getWorld().getBlock(this.x, this.y, this.z).getMaterial() : null;
    }

    public Item getItemBlock(){
        return this.getWorld() != null ? Item.getItemFromBlock(this.getBlock()) : null;
    }

    public int getMetadata(){
        return this.getWorld() != null ? this.getWorld().getBlockMetadata(this.x, this.y, this.z) : 0;
    }

    public boolean isEqual(WorldPos pos){
        return pos != null && this.x == pos.getX() && this.y == pos.getY() && this.z == pos.getZ() && this.getWorld() == pos.getWorld();
    }

    public void update(){
        if(this.getWorld() != null){
            this.getWorld().markBlockForUpdate(this.x, this.y, this.z);
        }
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

    public World getWorld(){
        return DimensionManager.getWorld(this.worldID);
    }

    public WorldPos copy(){
        return new WorldPos(this.getWorld(), this.x, this.y, this.z);
    }

    public String toString(){
        return "["+this.x+", "+this.y+", "+this.z+" in world "+this.worldID+"]";
    }
}
