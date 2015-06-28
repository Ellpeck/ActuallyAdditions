package ellpeck.actuallyadditions.util;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class WorldUtil{

    public static ChunkCoordinates getCoordsFromSide(ForgeDirection side, int x, int y, int z){
        if(side == ForgeDirection.UNKNOWN) return null;
        return new ChunkCoordinates(x+side.offsetX, y+side.offsetY, z+side.offsetZ);
    }

    public static void breakBlockAtSide(ForgeDirection side, World world, int x, int y, int z){
        if(side == ForgeDirection.UNKNOWN){
            world.setBlockToAir(x, y, z);
            return;
        }
        ChunkCoordinates c = getCoordsFromSide(side, x, y, z);
        if(c != null){
            world.setBlockToAir(c.posX, c.posY, c.posZ);
        }
    }

    public static void pushEnergy(World world, int x, int y, int z, ForgeDirection side, EnergyStorage storage){
        TileEntity tile = getTileEntityFromSide(side, world, x, y, z);
        if(tile != null && tile instanceof IEnergyReceiver && storage.getEnergyStored() > 0){
            if(((IEnergyReceiver)tile).canConnectEnergy(side.getOpposite())){
                int receive = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), Math.min(storage.getMaxExtract(), storage.getEnergyStored()), false);
                storage.extractEnergy(receive, false);
            }
        }
    }

    public static void pushFluid(World world, int x, int y, int z, ForgeDirection side, FluidTank tank){
        TileEntity tile = getTileEntityFromSide(side, world, x, y, z);
        if(tile != null && tank.getFluid() != null && tile instanceof IFluidHandler){
            if(((IFluidHandler)tile).canFill(side.getOpposite(), tank.getFluid().getFluid())){
                int receive = ((IFluidHandler)tile).fill(side.getOpposite(), tank.getFluid(), true);
                tank.drain(receive, true);
            }
        }
    }

    public static ItemStack placeBlockAtSide(ForgeDirection side, World world, int x, int y, int z, ItemStack stack){
        if(world instanceof WorldServer && stack != null && stack.getItem() != null){

            //Fluids
            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
            if(fluid != null && fluid.getFluid().getBlock() != null && fluid.getFluid().getBlock().canPlaceBlockAt(world, x+side.offsetX, y+side.offsetY, z+side.offsetZ)){
                Block block = world.getBlock(x+side.offsetX, y+side.offsetY, z+side.offsetZ);
                if(!(block instanceof IFluidBlock) && block != Blocks.lava && block != Blocks.water && block != Blocks.flowing_lava && block != Blocks.flowing_water){
                    if(world.setBlock(x+side.offsetX, y+side.offsetY, z+side.offsetZ, fluid.getFluid().getBlock())){
                        return stack.getItem().getContainerItem(stack);
                    }
                }
            }

            //Plants
            if(stack.getItem() instanceof IPlantable){
                if(((IPlantable)stack.getItem()).getPlant(world, x, y, z).canPlaceBlockAt(world, x+side.offsetX, y+side.offsetY, z+side.offsetZ)){
                    if(world.setBlock(x+side.offsetX, y+side.offsetY, z+side.offsetZ, ((IPlantable)stack.getItem()).getPlant(world, x, y, z))){
                        stack.stackSize--;
                        return stack;
                    }
                }
            }

            try{
                //Blocks
                stack.tryPlaceItemIntoWorld(FakePlayerUtil.newFakePlayer(world), world, x, y, z, side == ForgeDirection.UNKNOWN ? 0 : side.ordinal(), 0, 0, 0);
                return stack;
            }
            catch(Exception e){
                ModUtil.LOGGER.log(Level.ERROR, "Something that places Blocks at "+x+", "+y+", "+z+" in World "+world.provider.dimensionId+" threw an Exception! Don't let that happen again!");
            }
        }
        return stack;
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
        switch(side){
            case 0: return ForgeDirection.UP;
            case 1: return ForgeDirection.DOWN;
            case 2: return ForgeDirection.NORTH;
            case 3: return ForgeDirection.EAST;
            case 4: return ForgeDirection.SOUTH;
            case 5: return ForgeDirection.WEST;
            default: return ForgeDirection.UNKNOWN;
        }
    }

    public static ArrayList<Material> getMaterialsAround(World world, int x, int y, int z){
        ArrayList<Material> blocks = new ArrayList<Material>();
        blocks.add(world.getBlock(x+1, y, z).getMaterial());
        blocks.add(world.getBlock(x-1, y, z).getMaterial());
        blocks.add(world.getBlock(x, y, z+1).getMaterial());
        blocks.add(world.getBlock(x, y, z-1).getMaterial());

        return blocks;
    }

}
