/*
 * This file ("WorldUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.util;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;

public class WorldUtil{

    /**
     * Vertical Directions in Order:
     * Up, Down
     */
    public static final ForgeDirection[] VERTICAL_DIRECTIONS_ORDER = new ForgeDirection[]{ForgeDirection.UP, ForgeDirection.DOWN};
    /**
     * Cardinal Directions in Order:
     * North, East, South, West
     */
    public static final ForgeDirection[] CARDINAL_DIRECTIONS_ORDER = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST};

    public static void breakBlockAtSide(ForgeDirection side, World world, int x, int y, int z){
        breakBlockAtSide(side, world, x, y, z, 0);
    }

    public static void breakBlockAtSide(ForgeDirection side, World world, int x, int y, int z, int offset){
        if(side == ForgeDirection.UNKNOWN){
            world.setBlockToAir(x, y, z);
            return;
        }
        Position c = getCoordsFromSide(side, x, y, z, offset);
        if(c != null){
            world.setBlockToAir(c.getX(), c.getY(), c.getZ());
        }
    }

    public static Position getCoordsFromSide(ForgeDirection side, int x, int y, int z, int offset){
        if(side == ForgeDirection.UNKNOWN){
            return null;
        }
        return new Position(x+side.offsetX*(offset+1), y+side.offsetY*(offset+1), z+side.offsetZ*(offset+1));
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

    public static TileEntity getTileEntityFromSide(ForgeDirection side, World world, int x, int y, int z){
        Position c = getCoordsFromSide(side, x, y, z, 0);
        if(c != null){
            return world.getTileEntity(c.getX(), c.getY(), c.getZ());
        }
        return null;
    }

    /**
     * Checks if a given Block with a given Meta is present in given Positions
     *
     * @param positions The Positions, an array of {xCoord, yCoord, zCoord} arrays containing RELATIVE Positions
     * @param block     The Block
     * @param meta      The Meta
     * @param world     The World
     * @param x         The Start X Coord
     * @param y         The Start Y Coord
     * @param z         The Start Z Coord
     * @return Is every block present?
     */
    public static boolean hasBlocksInPlacesGiven(int[][] positions, Block block, int meta, World world, int x, int y, int z){
        for(int[] xYZ : positions){
            if(!(world.getBlock(x+xYZ[0], y+xYZ[1], z+xYZ[2]) == block && world.getBlockMetadata(x+xYZ[0], y+xYZ[1], z+xYZ[2]) == meta)){
                return false;
            }
        }
        return true;
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

            //Redstone
            else if(stack.getItem() == Items.redstone){
                world.setBlock(x+side.offsetX, y+side.offsetY, z+side.offsetZ, Blocks.redstone_wire);
                stack.stackSize--;
            }

            //Plants
            else if(stack.getItem() instanceof IPlantable){
                if(((IPlantable)stack.getItem()).getPlant(world, x, y, z).canPlaceBlockAt(world, x+side.offsetX, y+side.offsetY, z+side.offsetZ)){
                    if(world.setBlock(x+side.offsetX, y+side.offsetY, z+side.offsetZ, ((IPlantable)stack.getItem()).getPlant(world, x, y, z))){
                        stack.stackSize--;
                    }
                }
            }
            else{
                try{
                    //Blocks
                    stack.tryPlaceItemIntoWorld(FakePlayerUtil.getFakePlayer(world), world, x, y, z, side == ForgeDirection.UNKNOWN ? 0 : side.ordinal(), 0, 0, 0);
                    return stack;
                }
                catch(Exception e){
                    ModUtil.LOGGER.error("Something that places Blocks at "+x+", "+y+", "+z+" in World "+world.provider.dimensionId+" threw an Exception! Don't let that happen again!");
                }
            }
        }
        return stack;
    }

    public static boolean dropItemAtSide(ForgeDirection side, World world, int x, int y, int z, ItemStack stack){
        if(side != ForgeDirection.UNKNOWN){
            Position coords = getCoordsFromSide(side, x, y, z, 0);
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

    public static void fillBucket(FluidTank tank, ItemStack[] slots, int inputSlot, int outputSlot){
        if(slots[inputSlot] != null && tank.getFluid() != null){
            ItemStack filled = FluidContainerRegistry.fillFluidContainer(tank.getFluid(), slots[inputSlot].copy());
            if(filled != null && FluidContainerRegistry.isEmptyContainer(slots[inputSlot]) && (slots[outputSlot] == null || (slots[outputSlot].isItemEqual(filled) && slots[outputSlot].stackSize < slots[outputSlot].getMaxStackSize()))){
                int cap = FluidContainerRegistry.getContainerCapacity(tank.getFluid(), slots[inputSlot]);
                if(cap > 0 && cap <= tank.getFluidAmount()){
                    if(slots[outputSlot] == null){
                        slots[outputSlot] = FluidContainerRegistry.fillFluidContainer(tank.getFluid(), slots[inputSlot].copy());
                    }
                    else{
                        slots[outputSlot].stackSize++;
                    }

                    if(slots[outputSlot] != null){
                        tank.drain(cap, true);
                        slots[inputSlot].stackSize--;
                        if(slots[inputSlot].stackSize <= 0){
                            slots[inputSlot] = null;
                        }
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
                    if(slots[outputSlot] == null){
                        slots[outputSlot] = FluidContainerRegistry.drainFluidContainer(slots[inputSlot].copy());
                    }
                    else{
                        slots[outputSlot].stackSize++;
                    }

                    tank.fill(FluidContainerRegistry.getFluidForFilledItem(slots[inputSlot]), true);
                    slots[inputSlot].stackSize--;
                    if(slots[inputSlot].stackSize <= 0){
                        slots[inputSlot] = null;
                    }
                }
            }
        }
    }

    public static ForgeDirection getDirectionBySidesInOrder(int side){
        if(side >= 0 && side < VERTICAL_DIRECTIONS_ORDER.length+CARDINAL_DIRECTIONS_ORDER.length){
            if(side < VERTICAL_DIRECTIONS_ORDER.length){
                return VERTICAL_DIRECTIONS_ORDER[side];
            }
            else{
                return CARDINAL_DIRECTIONS_ORDER[side-VERTICAL_DIRECTIONS_ORDER.length];
            }
        }
        return ForgeDirection.UNKNOWN;
    }

    public static ArrayList<Material> getMaterialsAround(World world, int x, int y, int z){
        ArrayList<Material> blocks = new ArrayList<Material>();
        blocks.add(world.getBlock(x+1, y, z).getMaterial());
        blocks.add(world.getBlock(x-1, y, z).getMaterial());
        blocks.add(world.getBlock(x, y, z+1).getMaterial());
        blocks.add(world.getBlock(x, y, z-1).getMaterial());

        return blocks;
    }

    public static boolean addToInventory(IInventory inventory, int start, int end, ArrayList<ItemStack> stacks, boolean actuallyDo){
        return addToInventory(inventory, start, end, stacks, ForgeDirection.UNKNOWN, actuallyDo);
    }

    /**
     * Add an ArrayList of ItemStacks to an Array of slots
     *
     * @param inventory  The inventory to try to put the items into
     * @param stacks     The stacks to be put into the slots (Items don't actually get removed from there!)
     * @param side       The side to input from (use UNKNOWN if it should always work)
     * @param actuallyDo Do it or just test if it works?
     * @return Does it work?
     */
    public static boolean addToInventory(IInventory inventory, int start, int end, ArrayList<ItemStack> stacks, ForgeDirection side, boolean actuallyDo){
        //Copy the slots if just testing to later load them again
        ItemStack[] backupSlots = null;
        if(!actuallyDo){
            backupSlots = new ItemStack[inventory.getSizeInventory()];
            for(int i = 0; i < backupSlots.length; i++){
                ItemStack stack = inventory.getStackInSlot(i);
                if(stack != null){
                    backupSlots[i] = stack.copy();
                }
            }
        }

        int working = 0;
        for(ItemStack stackToPutIn : stacks){
            for(int i = start; i < end; i++){
                if(side == ForgeDirection.UNKNOWN || ((!(inventory instanceof ISidedInventory) || ((ISidedInventory)inventory).canInsertItem(i, stackToPutIn, side.ordinal())) && inventory.isItemValidForSlot(i, stackToPutIn))){
                    ItemStack stackInQuestion = inventory.getStackInSlot(i);
                    if(stackToPutIn != null && (stackInQuestion == null || (stackInQuestion.isItemEqual(stackToPutIn) && stackInQuestion.getMaxStackSize() >= stackInQuestion.stackSize+stackToPutIn.stackSize))){
                        if(stackInQuestion == null){
                            inventory.setInventorySlotContents(i, stackToPutIn.copy());
                        }
                        else{
                            stackInQuestion.stackSize += stackToPutIn.stackSize;
                        }
                        working++;

                        break;
                    }
                }
            }
        }

        //Load the slots again
        if(!actuallyDo && backupSlots != null){
            for(int i = 0; i < backupSlots.length; i++){
                inventory.setInventorySlotContents(i, backupSlots[i]);
            }
        }

        return working >= stacks.size();
    }

    public static boolean addToInventory(IInventory inventory, ArrayList<ItemStack> stacks, boolean actuallyDo){
        return addToInventory(inventory, stacks, ForgeDirection.UNKNOWN, actuallyDo);
    }

    public static boolean addToInventory(IInventory inventory, ArrayList<ItemStack> stacks, ForgeDirection side, boolean actuallyDo){
        return addToInventory(inventory, 0, inventory.getSizeInventory(), stacks, side, actuallyDo);
    }

    public static int findFirstFilledSlot(ItemStack[] slots){
        for(int i = 0; i < slots.length; i++){
            if(slots[i] != null){
                return i;
            }
        }
        return 0;
    }

    public static MovingObjectPosition getNearestPositionWithAir(World world, EntityPlayer player, int reach){
        return getMovingObjectPosWithReachDistance(world, player, reach, false, false, true);
    }

    private static MovingObjectPosition getMovingObjectPosWithReachDistance(World world, EntityPlayer player, double distance, boolean p1, boolean p2, boolean p3){
        float f = 1.0F;
        float f1 = player.prevRotationPitch+(player.rotationPitch-player.prevRotationPitch)*f;
        float f2 = player.prevRotationYaw+(player.rotationYaw-player.prevRotationYaw)*f;
        double d0 = player.prevPosX+(player.posX-player.prevPosX)*(double)f;
        double d1 = player.prevPosY+(player.posY-player.prevPosY)*(double)f+(double)(world.isRemote ? player.getEyeHeight()-player.getDefaultEyeHeight() : player.getEyeHeight());
        double d2 = player.prevPosZ+(player.posZ-player.prevPosZ)*(double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2*0.017453292F-(float)Math.PI);
        float f4 = MathHelper.sin(-f2*0.017453292F-(float)Math.PI);
        float f5 = -MathHelper.cos(-f1*0.017453292F);
        float f6 = MathHelper.sin(-f1*0.017453292F);
        float f7 = f4*f5;
        float f8 = f3*f5;
        Vec3 vec31 = vec3.addVector((double)f7*distance, (double)f6*distance, (double)f8*distance);
        return world.func_147447_a(vec3, vec31, p1, p2, p3);
    }

    public static MovingObjectPosition getNearestBlockWithDefaultReachDistance(World world, EntityPlayer player){
        return getMovingObjectPosWithReachDistance(world, player, player instanceof EntityPlayerMP ? ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance() : 5.0D, false, true, false);
    }

    /**
     * Harvests a Block by a Player
     *
     * @param world  The World
     * @param xPos   The X Coordinate
     * @param yPos   The Y Coordinate
     * @param zPos   The Z Coordinate
     * @param player The Player
     * @return If the Block could be harvested normally (so that it drops an item)
     */
    public static boolean playerHarvestBlock(World world, int xPos, int yPos, int zPos, EntityPlayer player){
        Block block = world.getBlock(xPos, yPos, zPos);
        int meta = world.getBlockMetadata(xPos, yPos, zPos);
        //If the Block can be harvested or not
        boolean canHarvest = block.canHarvestBlock(player, meta);

        //Send Block Breaking Event
        if(player instanceof EntityPlayerMP){
            BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP)player).theItemInWorldManager.getGameType(), (EntityPlayerMP)player, xPos, yPos, zPos);
            if(event.isCanceled()){
                return false;
            }
        }

        if(!world.isRemote){
            //Server-Side only, special cases
            block.onBlockHarvested(world, xPos, yPos, zPos, meta, player);
        }
        else{
            //Shows the Harvest Particles and plays the Block's Sound
            world.playAuxSFX(2001, xPos, yPos, zPos, Block.getIdFromBlock(block)+(meta << 12));
        }

        //If the Block was actually "removed", meaning it will drop an Item
        boolean removed = block.removedByPlayer(world, player, xPos, yPos, zPos, canHarvest);
        //Actually removes the Block from the World
        if(removed){
            //Before the Block is destroyed, special cases
            block.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, meta);

            if(!world.isRemote && !player.capabilities.isCreativeMode){
                //Actually drops the Block's Items etc.
                if(canHarvest){
                    block.harvestBlock(world, player, xPos, yPos, zPos, meta);
                }
                //Only drop XP when no Silk Touch is applied
                if(!EnchantmentHelper.getSilkTouchModifier(player)){
                    //Drop XP depending on Fortune Level
                    block.dropXpOnBlockBreak(world, xPos, yPos, zPos, block.getExpDrop(world, meta, EnchantmentHelper.getFortuneModifier(player)));
                }
            }
        }

        if(!world.isRemote){
            //Update the Client of a Block Change
            if(player instanceof EntityPlayerMP){
                ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(xPos, yPos, zPos, world));
            }
        }
        else{
            //Check the Server if a Block that changed on the Client really changed, if not, revert the change
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, xPos, yPos, zPos, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
        return removed;
    }
}
