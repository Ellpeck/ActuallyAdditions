package ellpeck.actuallyadditions.util;

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

    public WorldPos(int x, int y, int z){
        this(null, x, y, z);
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
}
