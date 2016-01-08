/*
 * This file ("WorldUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.api.Position;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;

public class WorldUtil{

    public static void breakBlockAtSide(EnumFacing side, World world, Position pos){
        breakBlockAtSide(side, world, pos, 0);
    }

    public static void breakBlockAtSide(EnumFacing side, World world, Position pos, int offset){
        Position c = getCoordsFromSide(side, pos, offset);
        if(c != null){
            world.setBlockToAir(pos);
        }
    }

    public static Position getCoordsFromSide(EnumFacing side, Position pos, int offset){
        return new Position(pos.getX()+side.getFrontOffsetX()*(offset+1), pos.getY()+side.getFrontOffsetY()*(offset+1), pos.getZ()+side.getFrontOffsetZ()*(offset+1));
    }
    
    public static void pushEnergyToAllSides(World world, Position pos, EnergyStorage storage){
        WorldUtil.pushEnergy(world, pos, EnumFacing.UP, storage);
        WorldUtil.pushEnergy(world, pos, EnumFacing.DOWN, storage);
        WorldUtil.pushEnergy(world, pos, EnumFacing.NORTH, storage);
        WorldUtil.pushEnergy(world, pos, EnumFacing.EAST, storage);
        WorldUtil.pushEnergy(world, pos, EnumFacing.SOUTH, storage);
        WorldUtil.pushEnergy(world, pos, EnumFacing.WEST, storage);
    }

    public static void pushEnergy(World world, Position pos, EnumFacing side, EnergyStorage storage){
        TileEntity tile = getTileEntityFromSide(side, world, pos);
        if(tile != null && tile instanceof IEnergyReceiver && storage.getEnergyStored() > 0){
            if(((IEnergyReceiver)tile).canConnectEnergy(side.getOpposite())){
                int receive = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), Math.min(storage.getMaxExtract(), storage.getEnergyStored()), false);
                storage.extractEnergy(receive, false);
            }
        }
    }

    public static TileEntity getTileEntityFromSide(EnumFacing side, World world, Position pos){
        Position c = getCoordsFromSide(side, pos, 0);
        if(c != null){
            return world.getTileEntity(pos);
        }
        return null;
    }

    /**
     * Checks if a given Block with a given Meta is present in given Positions
     *
     * @param positions The Positions, an array of {xCoord, yCoord, zCoord} arrays containing Positions
     * @param block     The Block
     * @param meta      The Meta
     * @param world     The World
     * @return Is every block present?
     */
    public static boolean hasBlocksInPlacesGiven(Position[] positions, Block block, int meta, World world){
        for(Position pos : positions){
            if(!(pos.getBlock(world) == block && pos.getMetadata(world) == meta)){
                return false;
            }
        }
        return true;
    }

    public static void pushFluid(World world, Position pos, EnumFacing side, FluidTank tank){
        TileEntity tile = getTileEntityFromSide(side, world, pos);
        if(tile != null && tank.getFluid() != null && tile instanceof IFluidHandler){
            if(((IFluidHandler)tile).canFill(side.getOpposite(), tank.getFluid().getFluid())){
                int receive = ((IFluidHandler)tile).fill(side.getOpposite(), tank.getFluid(), true);
                tank.drain(receive, true);
            }
        }
    }

    public static ItemStack placeBlockAtSide(EnumFacing side, World world, Position pos, ItemStack stack){
        if(world instanceof WorldServer && stack != null && stack.getItem() != null){
            Position offsetPos = pos.getOffsetPosition(side);

            //Fluids
            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
            if(fluid != null && fluid.getFluid().getBlock() != null && fluid.getFluid().getBlock().canPlaceBlockAt(world, offsetPos)){
                Block block = offsetPos.getBlock(world);
                if(!(block instanceof IFluidBlock) && block != Blocks.lava && block != Blocks.water && block != Blocks.flowing_lava && block != Blocks.flowing_water){
                    if(offsetPos.setBlock(world, fluid.getFluid().getBlock(), 0, 2)){
                        return stack.getItem().getContainerItem(stack);
                    }
                }
            }

            //Redstone
            else if(stack.getItem() == Items.redstone){
                offsetPos.setBlock(world, Blocks.redstone_wire, 0, 2);
                stack.stackSize--;
            }

            //Plants
            else if(stack.getItem() instanceof IPlantable){
                if(((IPlantable)stack.getItem()).getPlant(world, offsetPos).getBlock().canPlaceBlockAt(world, offsetPos)){
                    if(offsetPos.setBlockState(world, ((IPlantable)stack.getItem()).getPlant(world, offsetPos), 2)){
                        stack.stackSize--;
                    }
                }
            }
            else{
                try{
                    //Blocks
                    stack.onItemUse(FakePlayerUtil.getFakePlayer(world), world, pos, side, 0, 0, 0);
                    return stack;
                }
                catch(Exception e){
                    ModUtil.LOGGER.error("Something that places Blocks at "+offsetPos.getX()+", "+offsetPos.getY()+", "+offsetPos.getZ()+" in World "+world.provider.getDimensionId()+" threw an Exception! Don't let that happen again!");
                }
            }
        }
        return stack;
    }

    public static void dropItemAtSide(EnumFacing side, World world, Position pos, ItemStack stack){
        Position coords = getCoordsFromSide(side, pos, 0);
        if(coords != null){
            EntityItem item = new EntityItem(world, coords.getX()+0.5, coords.getY()+0.5, coords.getZ()+0.5, stack);
            item.motionX = 0;
            item.motionY = 0;
            item.motionZ = 0;
            world.spawnEntityInWorld(item);
        }
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

    public static EnumFacing getDirectionBySidesInOrder(int side){
        switch(side){
            case 0:
                return EnumFacing.UP;
            case 1:
                return EnumFacing.DOWN;
            case 2:
                return EnumFacing.NORTH;
            case 3:
                return EnumFacing.EAST;
            case 4:
                return EnumFacing.SOUTH;
            default:
                return EnumFacing.WEST;
        }
    }

    public static EnumFacing getDirectionByPistonRotation(int meta){
        switch(meta){
            case 0:
                return EnumFacing.UP;
            case 1:
                return EnumFacing.DOWN;
            case 2:
                return EnumFacing.NORTH;
            case 3:
                return EnumFacing.EAST;
            case 4:
                return EnumFacing.SOUTH;
            default:
                return EnumFacing.WEST;
        }
    }

    public static ArrayList<Material> getMaterialsAround(World world, Position pos){
        ArrayList<Material> blocks = new ArrayList<Material>();
        blocks.add(pos.getOffsetPosition(EnumFacing.NORTH).getMaterial(world));
        blocks.add(pos.getOffsetPosition(EnumFacing.EAST).getMaterial(world));
        blocks.add(pos.getOffsetPosition(EnumFacing.SOUTH).getMaterial(world));
        blocks.add(pos.getOffsetPosition(EnumFacing.WEST).getMaterial(world));
        return blocks;
    }

    /**
     * Add an ArrayList of ItemStacks to an Array of slots
     *
     * @param inventory  The inventory to try to put the items into
     * @param stacks     The stacks to be put into the slots (Items don't actually get removed from there!)
     * @param side       The side to input from
     * @param actuallyDo Do it or just test if it works?
     * @return Does it work?
     */
    public static boolean addToInventory(IInventory inventory, int start, int end, ArrayList<ItemStack> stacks, EnumFacing side, boolean actuallyDo, boolean shouldAlwaysWork){
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
                if(shouldAlwaysWork || ((!(inventory instanceof ISidedInventory) || ((ISidedInventory)inventory).canInsertItem(i, stackToPutIn, side)) && inventory.isItemValidForSlot(i, stackToPutIn))){
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

    public static boolean addToInventory(IInventory inventory, ArrayList<ItemStack> stacks, boolean actuallyDo, boolean shouldAlwaysWork){
        return addToInventory(inventory, stacks, EnumFacing.UP, actuallyDo, shouldAlwaysWork);
    }

    public static boolean addToInventory(IInventory inventory, ArrayList<ItemStack> stacks, EnumFacing side, boolean actuallyDo, boolean shouldAlwaysWork){
        return addToInventory(inventory, 0, inventory.getSizeInventory(), stacks, side, actuallyDo, shouldAlwaysWork);
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
        Vec3 vec3 = new Vec3(d0, d1, d2);
        float f3 = MathHelper.cos(-f2*0.017453292F-(float)Math.PI);
        float f4 = MathHelper.sin(-f2*0.017453292F-(float)Math.PI);
        float f5 = -MathHelper.cos(-f1*0.017453292F);
        float f6 = MathHelper.sin(-f1*0.017453292F);
        float f7 = f4*f5;
        float f8 = f3*f5;
        Vec3 vec31 = vec3.addVector((double)f7*distance, (double)f6*distance, (double)f8*distance);
        return world.rayTraceBlocks(vec3, vec31, p1, p2, p3);
    }

    public static MovingObjectPosition getNearestBlockWithDefaultReachDistance(World world, EntityPlayer player){
        return getMovingObjectPosWithReachDistance(world, player, player instanceof EntityPlayerMP ? ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance() : 5.0D, false, true, false);
    }

    /**
     * Harvests a Block by a Player
     *
     * @param world  The World
     * @param player The Player
     * @return If the Block could be harvested normally (so that it drops an item)
     */
    public static boolean playerHarvestBlock(World world, Position pos, EntityPlayer player){
        Block block = pos.getBlock(world);
        IBlockState state = pos.getBlockState(world);
        int meta = pos.getMetadata(world);
        //If the Block can be harvested or not
        boolean canHarvest = block.canHarvestBlock(world, pos, player);

        //Send Block Breaking Event
        if(player instanceof EntityPlayerMP){
            int event = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP)player).theItemInWorldManager.getGameType(), (EntityPlayerMP)player, pos);
            if(event == -1){
                return false;
            }
        }

        if(!world.isRemote){
            //Server-Side only, special cases
            block.onBlockHarvested(world, pos, state, player);
        }
        else{
            //Shows the Harvest Particles and plays the Block's Sound
            world.playAuxSFX(2001, pos, Block.getIdFromBlock(block)+(meta << 12));
        }

        //If the Block was actually "removed", meaning it will drop an Item
        boolean removed = block.removedByPlayer(world, pos, player, canHarvest);
        //Actually removes the Block from the World
        if(removed){
            //Before the Block is destroyed, special cases
            block.onBlockDestroyedByPlayer(world, pos, state);

            if(!world.isRemote && !player.capabilities.isCreativeMode){
                //Actually drops the Block's Items etc.
                if(canHarvest){
                    block.harvestBlock(world, player, pos, state, pos.getTileEntity(world));
                }
                //Only drop XP when no Silk Touch is applied
                if(!EnchantmentHelper.getSilkTouchModifier(player)){
                    //Drop XP depending on Fortune Level
                    block.dropXpOnBlockBreak(world, pos, block.getExpDrop(world, pos, EnchantmentHelper.getFortuneModifier(player)));
                }
            }
        }

        if(!world.isRemote){
            //Update the Client of a Block Change
            if(player instanceof EntityPlayerMP){
                ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(world, pos));
            }
        }
        else{
            //Check the Server if a Block that changed on the Client really changed, if not, revert the change
            //TODO Check if this is the right action
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
        return removed;
    }
}
