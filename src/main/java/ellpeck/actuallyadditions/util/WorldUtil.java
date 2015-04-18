package ellpeck.actuallyadditions.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class WorldUtil{

    public static ChunkCoordinates getCoordsFromSide(int side, int x, int y, int z){
        if(side == 0) return new ChunkCoordinates(x, y + 1, z);
        if(side == 1) return new ChunkCoordinates(x, y - 1, z);
        if(side == 2) return new ChunkCoordinates(x, y, z - 1);
        if(side == 3) return new ChunkCoordinates(x + 1, y, z);
        if(side == 4) return new ChunkCoordinates(x, y, z + 1);
        if(side == 5) return new ChunkCoordinates(x - 1, y, z);
        else return null;
    }

    public static void breakBlockAtSide(int side, World world, int x, int y, int z){
        ChunkCoordinates c = getCoordsFromSide(side, x, y, z);
        if(c != null){
            world.setBlockToAir(c.posX, c.posY, c.posZ);
        }
    }

    public static void placeBlockAtSide(int side, World world, int x, int y, int z, ItemStack stack){
        Block block = Block.getBlockFromItem(stack.getItem());
        int meta = stack.getItem().getDamage(stack);
        ChunkCoordinates c = getCoordsFromSide(side, x, y, z);
        if(c != null){
            world.setBlock(c.posX, c.posY, c.posZ, block, meta, 2);
        }
    }

    public static TileEntity getTileEntityFromSide(int side, World world, int x, int y, int z){
        ChunkCoordinates c = getCoordsFromSide(side, x, y, z);
        if(c != null){
            return world.getTileEntity(c.posX, c.posY, c.posZ);
        }
        else return null;
    }

}
