/*
 * This file ("WorldUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

public class WorldUtil{

    public static void breakBlockAtSide(EnumFacing side, World world, BlockPos pos){
        breakBlockAtSide(side, world, pos, 0);
    }

    public static void breakBlockAtSide(EnumFacing side, World world, BlockPos pos, int offset){
        world.setBlockToAir(getCoordsFromSide(side, pos, offset));
    }

    public static BlockPos getCoordsFromSide(EnumFacing side, BlockPos pos, int offset){
        return new BlockPos(pos.getX()+side.getFrontOffsetX()*(offset+1), pos.getY()+side.getFrontOffsetY()*(offset+1), pos.getZ()+side.getFrontOffsetZ()*(offset+1));
    }

    public static void pushEnergyToAllSides(World world, BlockPos pos, EnergyStorage storage){
        pushEnergy(world, pos, EnumFacing.UP, storage);
        pushEnergy(world, pos, EnumFacing.DOWN, storage);
        pushEnergy(world, pos, EnumFacing.NORTH, storage);
        pushEnergy(world, pos, EnumFacing.EAST, storage);
        pushEnergy(world, pos, EnumFacing.SOUTH, storage);
        pushEnergy(world, pos, EnumFacing.WEST, storage);
    }

    public static void pushEnergy(World world, BlockPos pos, EnumFacing side, EnergyStorage storage){
        TileEntity tile = getTileEntityFromSide(side, world, pos);
        if(tile != null && tile instanceof IEnergyReceiver && storage.getEnergyStored() > 0){
            if(((IEnergyReceiver)tile).canConnectEnergy(side.getOpposite())){
                int receive = ((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), Math.min(storage.getMaxExtract(), storage.getEnergyStored()), false);
                storage.extractEnergy(receive, false);
            }
        }
    }

    public static TileEntity getTileEntityFromSide(EnumFacing side, World world, BlockPos pos){
        BlockPos c = getCoordsFromSide(side, pos, 0);
        return world.getTileEntity(c);
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
    public static boolean hasBlocksInPlacesGiven(BlockPos[] positions, Block block, int meta, World world){
        for(BlockPos pos : positions){
            if(!(PosUtil.getBlock(pos, world) == block && PosUtil.getMetadata(pos, world) == meta)){
                return false;
            }
        }
        return true;
    }

    public static void pushFluid(TileEntity tileFrom, EnumFacing side){
        TileEntity tileTo = getTileEntityFromSide(side, tileFrom.getWorld(), tileFrom.getPos());
        if(tileTo != null){
            if(tileFrom instanceof net.minecraftforge.fluids.IFluidHandler && tileTo instanceof net.minecraftforge.fluids.IFluidHandler){
                net.minecraftforge.fluids.IFluidHandler handlerTo = (net.minecraftforge.fluids.IFluidHandler)tileTo;
                net.minecraftforge.fluids.IFluidHandler handlerFrom = (net.minecraftforge.fluids.IFluidHandler)tileFrom;
                FluidStack drain =  handlerFrom.drain(side, Integer.MAX_VALUE, false);
                if(drain != null){
                    if(handlerTo.canFill(side.getOpposite(), drain.getFluid())){
                        int filled = handlerTo.fill(side.getOpposite(), drain.copy(), true);
                        handlerFrom.drain(side, filled, true);
                    }
                }
            }
            else{
                IFluidHandler handlerFrom = tileFrom.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
                IFluidHandler handlerTo = tileTo.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
                if(handlerFrom != null && handlerTo != null){
                    FluidStack drain = handlerFrom.drain(Integer.MAX_VALUE, false);
                    if(drain != null){
                        int filled = handlerTo.fill(drain.copy(), true);
                        handlerFrom.drain(filled, true);
                    }
                }
            }
        }
    }

    public static ItemStack useItemAtSide(EnumFacing side, World world, BlockPos pos, ItemStack stack){
        if(world instanceof WorldServer && stack != null && stack.getItem() != null){
            BlockPos offsetPos = pos.offset(side);
            Block block = PosUtil.getBlock(offsetPos, world);
            boolean replaceable = block.isReplaceable(world, offsetPos);

            //Fluids
            if(replaceable && !(block instanceof IFluidBlock) && !(block instanceof BlockLiquid)){
                FluidStack fluid = null;
                //TODO Remove when FluidContainerRegistry is gone
                if(FluidContainerRegistry.isFilledContainer(stack)){
                    fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
                }
                else if(stack.getItem() instanceof IFluidContainerItem){
                    fluid = ((IFluidContainerItem)stack.getItem()).getFluid(stack);
                }

                if(fluid != null && fluid.amount >= Util.BUCKET && fluid.getFluid().getBlock() != null && fluid.getFluid().getBlock().canPlaceBlockAt(world, offsetPos)){
                    if(PosUtil.setBlock(offsetPos, world, fluid.getFluid().getBlock(), 0, 2)){
                        return stack.getItem().getContainerItem(stack);
                    }
                }
            }

            //Redstone
            if(replaceable && stack.getItem() == Items.REDSTONE){
                PosUtil.setBlock(offsetPos, world, Blocks.REDSTONE_WIRE, 0, 2);
                stack.stackSize--;
                return stack;
            }

            //Plants
            if(replaceable && stack.getItem() instanceof IPlantable){
                if(((IPlantable)stack.getItem()).getPlant(world, offsetPos).getBlock().canPlaceBlockAt(world, offsetPos)){
                    if(world.setBlockState(offsetPos, ((IPlantable)stack.getItem()).getPlant(world, offsetPos), 2)){
                        stack.stackSize--;
                        return stack;
                    }
                }
            }

            //Everything else
            try{
                EntityPlayer fake = FakePlayerUtil.getFakePlayer(world);
                stack.onItemUse(fake, world, offsetPos, fake.getActiveHand(), side.getOpposite(), 0.5F, 0.5F, 0.5F);
                return stack;
            }
            catch(Exception e){
                ModUtil.LOGGER.error("Something that places Blocks at "+offsetPos.getX()+", "+offsetPos.getY()+", "+offsetPos.getZ()+" in World "+world.provider.getDimension()+" threw an Exception! Don't let that happen again!", e);
            }
        }
        return stack;
    }

    public static void dropItemAtSide(EnumFacing side, World world, BlockPos pos, ItemStack stack){
        BlockPos coords = getCoordsFromSide(side, pos, 0);
        EntityItem item = new EntityItem(world, coords.getX()+0.5, coords.getY()+0.5, coords.getZ()+0.5, stack);
        item.motionX = 0;
        item.motionY = 0;
        item.motionZ = 0;
        world.spawnEntityInWorld(item);
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
        return EnumFacing.values()[meta];
    }

    public static ArrayList<Material> getMaterialsAround(World world, BlockPos pos){
        ArrayList<Material> blocks = new ArrayList<Material>();
        blocks.add(PosUtil.getMaterial(pos.offset(EnumFacing.NORTH), world));
        blocks.add(PosUtil.getMaterial(pos.offset(EnumFacing.EAST), world));
        blocks.add(PosUtil.getMaterial(pos.offset(EnumFacing.SOUTH), world));
        blocks.add(PosUtil.getMaterial(pos.offset(EnumFacing.WEST), world));
        return blocks;
    }

    public static boolean addToInventory(IInventory inventory, List<ItemStack> stacks, boolean actuallyDo, boolean shouldAlwaysWork){
        return addToInventory(inventory, stacks, EnumFacing.UP, actuallyDo, shouldAlwaysWork);
    }

    public static boolean addToInventory(IInventory inventory, List<ItemStack> stacks, EnumFacing side, boolean actuallyDo, boolean shouldAlwaysWork){
        return addToInventory(inventory, 0, inventory.getSizeInventory(), stacks, side, actuallyDo, shouldAlwaysWork);
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
    public static boolean addToInventory(IInventory inventory, int start, int end, List<ItemStack> stacks, EnumFacing side, boolean actuallyDo, boolean shouldAlwaysWork){
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
        if(!actuallyDo){
            for(int i = 0; i < backupSlots.length; i++){
                inventory.setInventorySlotContents(i, backupSlots[i]);
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

    public static RayTraceResult getNearestPositionWithAir(World world, EntityPlayer player, int reach){
        return getMovingObjectPosWithReachDistance(world, player, reach, false, false, true);
    }

    private static RayTraceResult getMovingObjectPosWithReachDistance(World world, EntityPlayer player, double distance, boolean p1, boolean p2, boolean p3){
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        double d0 = player.posX;
        double d1 = player.posY+(double)player.getEyeHeight();
        double d2 = player.posZ;
        Vec3d vec3 = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1*0.017453292F-(float)Math.PI);
        float f3 = MathHelper.sin(-f1*0.017453292F-(float)Math.PI);
        float f4 = -MathHelper.cos(-f*0.017453292F);
        float f5 = MathHelper.sin(-f*0.017453292F);
        float f6 = f3*f4;
        float f7 = f2*f4;
        Vec3d vec31 = vec3.addVector((double)f6*distance, (double)f5*distance, (double)f7*distance);
        return world.rayTraceBlocks(vec3, vec31, p1, p2, p3);
    }

    public static RayTraceResult getNearestBlockWithDefaultReachDistance(World world, EntityPlayer player){
        return getNearestBlockWithDefaultReachDistance(world, player, false, true, false);
    }

    public static RayTraceResult getNearestBlockWithDefaultReachDistance(World world, EntityPlayer player, boolean stopOnLiquids, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock){
        return getMovingObjectPosWithReachDistance(world, player, player instanceof EntityPlayerMP ? ((EntityPlayerMP)player).interactionManager.getBlockReachDistance() : 5.0D, stopOnLiquids, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
    }

    /**
     * Harvests a Block by a Player
     *
     * @param world  The World
     * @param player The Player
     * @return If the Block could be harvested normally (so that it drops an item)
     */
    public static boolean playerHarvestBlock(World world, BlockPos pos, EntityPlayer player){
        Block block = PosUtil.getBlock(pos, world);
        IBlockState state = world.getBlockState(pos);
        TileEntity tile = world.getTileEntity(pos);
        ItemStack stack = player.getHeldItemMainhand();

        //If the Block can be harvested or not
        boolean canHarvest = block.canHarvestBlock(world, pos, player);

        //Send Block Breaking Event
        int xp = -1;
        if(player instanceof EntityPlayerMP){
            xp = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP)player).interactionManager.getGameType(), (EntityPlayerMP)player, pos);
            if(xp == -1){
                return false;
            }
        }

        if(!world.isRemote){
            //Server-Side only, special cases
            block.onBlockHarvested(world, pos, state, player);
        }
        else{
            //Shows the Harvest Particles and plays the Block's Sound
            world.playEvent(2001, pos, Block.getStateId(state));
        }

        //If the Block was actually "removed", meaning it will drop an Item
        boolean removed = block.removedByPlayer(state, world, pos, player, canHarvest);
        //Actually removes the Block from the World
        if(removed){
            //Before the Block is destroyed, special cases
            block.onBlockDestroyedByPlayer(world, pos, state);

            if(!world.isRemote && !player.capabilities.isCreativeMode){
                //Actually drops the Block's Items etc.
                if(canHarvest){
                    block.harvestBlock(world, player, pos, state, tile, stack);
                }
                //Only drop XP when no Silk Touch is applied
                if(EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) <= 0){
                    if(xp >= 0){
                        block.dropXpOnBlockBreak(world, pos, xp);
                    }
                }
            }
        }

        if(!world.isRemote){
            //Update the Client of a Block Change
            if(player instanceof EntityPlayerMP){
                ((EntityPlayerMP)player).connection.sendPacket(new SPacketBlockChange(world, pos));
            }
        }
        else{
            //Check the Server if a Block that changed on the Client really changed, if not, revert the change
            Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
        return removed;
    }
}
