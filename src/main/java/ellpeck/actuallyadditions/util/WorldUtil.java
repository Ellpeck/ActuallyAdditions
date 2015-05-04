package ellpeck.actuallyadditions.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldUtil{

    public static ChunkCoordinates getCoordsFromSide(ForgeDirection side, int x, int y, int z){
        if(side == ForgeDirection.UNKNOWN) return null;
        return new ChunkCoordinates(x+side.offsetX, y+side.offsetY, z+side.offsetZ);
    }

    public static void breakBlockAtSide(ForgeDirection side, World world, int x, int y, int z){
        ChunkCoordinates c = getCoordsFromSide(side, x, y, z);
        if(c != null){
            world.setBlockToAir(c.posX, c.posY, c.posZ);
        }
    }

    public static boolean placeBlockAtSide(ForgeDirection side, World world, int x, int y, int z, ItemStack stack){
        if(world instanceof WorldServer){
            if(side != ForgeDirection.UNKNOWN){
                return stack.tryPlaceItemIntoWorld(FakePlayerUtil.newFakePlayer(world), world, x, y, z, side.ordinal(), 0, 0, 0);
            }
        }
        return false;
    }

    public static boolean dropItemAtSide(ForgeDirection side, World world, int x, int y, int z, ItemStack stack){
        if(side != ForgeDirection.UNKNOWN){
            ChunkCoordinates coords = getCoordsFromSide(side, x, y, z);
            if(coords != null){
                EntityItem item = new EntityItem(world, coords.posX+0.5, coords.posY+0.5, coords.posZ+0.5, stack);
                item.motionX = 0;
                item.motionY = 0;
                item.motionZ = 0;
                world.spawnEntityInWorld(item);
            }
        }
        return false;
    }

    public static TileEntity getTileEntityFromSide(ForgeDirection side, World world, int x, int y, int z){
        ChunkCoordinates c = getCoordsFromSide(side, x, y, z);
        if(c != null){
            return world.getTileEntity(c.posX, c.posY, c.posZ);
        }
        return null;
    }

    public static ForgeDirection getDirectionByRotatingSide(int side){
        if(side == 0) return ForgeDirection.UP;
        if(side == 1) return ForgeDirection.DOWN;
        if(side == 2) return ForgeDirection.NORTH;
        if(side == 3) return ForgeDirection.EAST;
        if(side == 4) return ForgeDirection.SOUTH;
        if(side == 5) return ForgeDirection.WEST;
        else return ForgeDirection.UNKNOWN;
    }

}
