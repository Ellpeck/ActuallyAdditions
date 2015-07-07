package ellpeck.actuallyadditions.util;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class WorldUtil{

    public static WorldPos getCoordsFromSide(ForgeDirection side, World world, int x, int y, int z){
        if(side == ForgeDirection.UNKNOWN) return null;
        return new WorldPos(world, x+side.offsetX, y+side.offsetY, z+side.offsetZ);
    }

    public static void breakBlockAtSide(ForgeDirection side, World world, int x, int y, int z){
        if(side == ForgeDirection.UNKNOWN){
            world.setBlockToAir(x, y, z);
            return;
        }
        WorldPos c = getCoordsFromSide(side, world, x, y, z);
        if(c != null){
            world.setBlockToAir(c.getX(), c.getY(), c.getZ());
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

    public static void updateTileAndTilesAround(TileEntity tile){
        tile.getWorldObj().markBlockForUpdate(tile.xCoord+1, tile.yCoord, tile.zCoord);
        tile.getWorldObj().markBlockForUpdate(tile.xCoord-1, tile.yCoord, tile.zCoord);
        tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord+1, tile.zCoord);
        tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord-1, tile.zCoord);
        tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord+1);
        tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord-1);
        tile.markDirty();
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
            WorldPos coords = getCoordsFromSide(side, world, x, y, z);
            if(coords != null){
                EntityItem item = new EntityItem(world, coords.getX()+0.5, coords.getY()+0.5, coords.getZ()+0.5, stack);
                item.motionX = 0;
                item.motionY = 0;
                item.motionZ = 0;
                world.spawnEntityInWorld(item);
            }
        }
        return false;
    }

    public static TileEntity getTileEntityFromSide(ForgeDirection side, World world, int x, int y, int z){
        WorldPos c = getCoordsFromSide(side, world, x, y, z);
        if(c != null){
            return world.getTileEntity(c.getX(), c.getY(), c.getZ());
        }
        return null;
    }
    
    public static void fillBucket(FluidTank tank, ItemStack[] slots, int inputSlot, int outputSlot){
        if(slots[inputSlot] != null && tank.getFluid() != null){
            ItemStack filled = FluidContainerRegistry.fillFluidContainer(tank.getFluid(), slots[inputSlot].copy());
            if(filled != null && FluidContainerRegistry.isEmptyContainer(slots[inputSlot]) && (slots[outputSlot] == null || (slots[outputSlot].isItemEqual(filled) && slots[outputSlot].stackSize < slots[outputSlot].getMaxStackSize()))){
                int cap = FluidContainerRegistry.getContainerCapacity(tank.getFluid(), slots[inputSlot]);
                if(cap > 0 && cap <= tank.getFluidAmount()){
                    if(slots[outputSlot] == null) slots[outputSlot] = FluidContainerRegistry.fillFluidContainer(tank.getFluid(), slots[inputSlot].copy());
                    else slots[outputSlot].stackSize++;

                    if(slots[outputSlot] != null){
                        tank.drain(cap, true);
                        slots[inputSlot].stackSize--;
                        if(slots[inputSlot].stackSize <= 0) slots[inputSlot] = null;
                    }
                }
            }
        }
    }

    public static void emptyBucket(FluidTank tank, ItemStack[] slots, int inputSlot, int outputSlot){
        emptyBucket(tank, slots, inputSlot, outputSlot, null);
    }

    public static void emptyBucket(FluidTank tank, ItemStack[] slots, int inputSlot, int outputSlot, Fluid containedFluid){
        if(slots[inputSlot] != null && FluidContainerRegistry.isFilledContainer(slots[inputSlot]) && (slots[outputSlot] == null || (slots[outputSlot].isItemEqual(FluidContainerRegistry.drainFluidContainer(slots[inputSlot].copy())) && slots[outputSlot].stackSize < slots[outputSlot].getMaxStackSize()))){
            if(containedFluid == null || FluidContainerRegistry.containsFluid(slots[inputSlot], new FluidStack(containedFluid, 0))){
                if((tank.getFluid() == null || FluidContainerRegistry.getFluidForFilledItem(slots[inputSlot]).isFluidEqual(tank.getFluid())) && tank.getCapacity()-tank.getFluidAmount() >= FluidContainerRegistry.getContainerCapacity(slots[inputSlot])){
                    if(slots[outputSlot] == null) slots[outputSlot] = FluidContainerRegistry.drainFluidContainer(slots[inputSlot].copy());
                    else slots[outputSlot].stackSize++;

                    tank.fill(FluidContainerRegistry.getFluidForFilledItem(slots[inputSlot]), true);
                    slots[inputSlot].stackSize--;
                    if(slots[inputSlot].stackSize <= 0) slots[inputSlot] = null;
                }
            }
        }
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

    public static boolean addToInventory(ItemStack[] slots, ArrayList<ItemStack> stacks, boolean actuallyDo){
        int working = 0;
        for(ItemStack stack : stacks){
            for(int i = 0; i < slots.length; i++){
                if(slots[i] == null || (slots[i].isItemEqual(stack) && slots[i].stackSize <= stack.getMaxStackSize()-stack.stackSize)){
                    working++;
                    if(actuallyDo){
                        if(slots[i] == null) slots[i] = stack.copy();
                        else slots[i].stackSize += stack.stackSize;
                    }
                    break;
                }
            }
        }
        return working >= stacks.size();
    }

    public static int findFirstFilledSlot(ItemStack[] slots){
        for(int i = 0; i < slots.length; i++){
            if(slots[i] != null){
                return i;
            }
        }
        return 0;
    }

    public static MovingObjectPosition getMovingObjectPosWithReachDistance(World world, EntityPlayer player, double distance){
        float f = 1.0F;
        float f1 = player.prevRotationPitch+(player.rotationPitch-player.prevRotationPitch)*f;
        float f2 = player.prevRotationYaw+(player.rotationYaw-player.prevRotationYaw)*f;
        double d0 = player.prevPosX+(player.posX-player.prevPosX)*(double)f;
        double d1 = player.prevPosY+(player.posY-player.prevPosY)*(double)f+(double)player.getEyeHeight();
        double d2 = player.prevPosZ+(player.posZ-player.prevPosZ)*(double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2*0.017453292F-(float)Math.PI);
        float f4 = MathHelper.sin(-f2*0.017453292F-(float)Math.PI);
        float f5 = -MathHelper.cos(-f1*0.017453292F);
        float f6 = MathHelper.sin(-f1*0.017453292F);
        float f7 = f4*f5;
        float f8 = f3*f5;
        Vec3 vec31 = vec3.addVector((double)f7*distance, (double)f6*distance, (double)f8*distance);
        return world.func_147447_a(vec3, vec31, false, true, false);
    }
}
