package ellpeck.actuallyadditions.util;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidHandler;

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

    public static void pushEnergy(World world, int x, int y, int z, ForgeDirection side, EnergyStorage storage){
        TileEntity tile = getTileEntityFromSide(side, world, x, y, z);
        if(tile != null && tile instanceof IEnergyReceiver){
            if(((IEnergyReceiver)tile).canConnectEnergy(side.getOpposite())){
                int receive = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), Math.min(storage.getMaxExtract(), storage.getEnergyStored()), false);
                storage.extractEnergy(receive, false);
                world.markBlockForUpdate(x+side.offsetX, y+side.offsetY, z+side.offsetZ);
            }
        }
    }

    public static void pushFluid(World world, int x, int y, int z, ForgeDirection side, FluidTank tank){
        TileEntity tile = getTileEntityFromSide(side, world, x, y, z);
        if(tile != null && tank.getFluid() != null && tile instanceof IFluidHandler){
            if(((IFluidHandler)tile).canFill(side.getOpposite(), tank.getFluid().getFluid())){
                int receive = ((IFluidHandler)tile).fill(side.getOpposite(), tank.getFluid(), true);
                tank.drain(receive, true);
                world.markBlockForUpdate(x+side.offsetX, y+side.offsetY, z+side.offsetZ);
            }
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
