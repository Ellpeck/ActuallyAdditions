package ellpeck.actuallyadditions.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldPos{

    private int x;
    private int y;
    private int z;
    private World world;

    public WorldPos(World world, int x, int y, int z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
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
        return this.world;
    }

    public Block getBlock(){
        return this.world != null ? this.world.getBlock(this.x, this.y, this.z) : null;
    }

    public TileEntity getTileEntity(){
        return this.world != null ? this.world.getTileEntity(this.x, this.y, this.z) : null;
    }

    public Material getMaterial(){
        return this.world != null ? this.world.getBlock(this.x, this.y, this.z).getMaterial() : null;
    }

    public Item getItemBlock(){
        return this.world != null ? Item.getItemFromBlock(this.world.getBlock(this.x, this.y, this.z)) : null;
    }

    public int getMetadata(){
        return this.world != null ? this.world.getBlockMetadata(this.x, this.y, this.z) : 0;
    }

    public boolean isEqual(WorldPos pos){
        return pos != null && this.x == pos.getX() && this.y == pos.getY() && this.z == pos.getZ() && this.world == pos.getWorld();
    }

    public WorldPos copy(){
        return new WorldPos(this.world, this.x, this.y, this.z);
    }
}
