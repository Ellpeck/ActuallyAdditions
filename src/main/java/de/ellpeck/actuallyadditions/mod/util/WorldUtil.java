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

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.compat.TeslaUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public final class WorldUtil{

    public static boolean doItemInteraction(int slotExtract, int slotInsert, TileEntity extract, TileEntity insert, EnumFacing extractSide, EnumFacing insertSide){
        if(extract.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, extractSide) && insert.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, insertSide)){
            IItemHandler extractCap = extract.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, extractSide);
            IItemHandler insertCap = insert.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, insertSide);

            ItemStack theoreticalExtract = extractCap.extractItem(slotExtract, Integer.MAX_VALUE, true);
            if(StackUtil.isValid(theoreticalExtract)){
                ItemStack remaining = insertCap.insertItem(slotInsert, theoreticalExtract, false);
                if(!ItemStack.areItemStacksEqual(remaining, theoreticalExtract)){
                    int toExtract = !StackUtil.isValid(remaining) ? StackUtil.getStackSize(theoreticalExtract) : StackUtil.getStackSize(theoreticalExtract)-StackUtil.getStackSize(remaining);
                    extractCap.extractItem(slotExtract, toExtract, false);
                    return true;
                }
            }
        }
        return false;
    }

    public static void doEnergyInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer){
        if(maxTransfer > 0){
            if(tileFrom instanceof IEnergyProvider && tileTo instanceof IEnergyReceiver){
                IEnergyReceiver handlerTo = (IEnergyReceiver)tileTo;
                IEnergyProvider handlerFrom = (IEnergyProvider)tileFrom;

                int drain = handlerFrom.extractEnergy(sideTo, maxTransfer, true);
                if(drain > 0){
                    if(handlerTo.canConnectEnergy(sideTo.getOpposite())){
                        int filled = handlerTo.receiveEnergy(sideTo.getOpposite(), drain, false);
                        handlerFrom.extractEnergy(sideTo, filled, false);
                        return;
                    }
                }
            }

            if(ActuallyAdditions.teslaLoaded){
                if(TeslaUtil.doWrappedTeslaRFInteraction(tileFrom, tileTo, sideTo, maxTransfer)){
                    return;
                }
            }

            if(tileFrom.hasCapability(CapabilityEnergy.ENERGY, sideTo) && tileTo.hasCapability(CapabilityEnergy.ENERGY, sideTo.getOpposite())){
                IEnergyStorage handlerFrom = tileFrom.getCapability(CapabilityEnergy.ENERGY, sideTo);
                IEnergyStorage handlerTo = tileTo.getCapability(CapabilityEnergy.ENERGY, sideTo.getOpposite());

                if(handlerFrom != null && handlerTo != null){
                    int drain = handlerFrom.extractEnergy(maxTransfer, true);
                    if(drain > 0){
                        int filled = handlerTo.receiveEnergy(drain, false);
                        handlerFrom.extractEnergy(filled, false);
                    }
                }
            }
        }
    }

    public static void doFluidInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer){
        if(maxTransfer > 0){
            if(tileFrom.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo) && tileTo.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo.getOpposite())){
                IFluidHandler handlerFrom = tileFrom.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo);
                IFluidHandler handlerTo = tileTo.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo.getOpposite());
                FluidStack drain = handlerFrom.drain(maxTransfer, false);
                if(drain != null){
                    int filled = handlerTo.fill(drain.copy(), true);
                    handlerFrom.drain(filled, true);
                }
            }
        }
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
            IBlockState state = world.getBlockState(pos);
            if(!(state.getBlock() == block && block.getMetaFromState(state) == meta)){
                return false;
            }
        }
        return true;
    }

    public static ItemStack useItemAtSide(EnumFacing side, World world, BlockPos pos, ItemStack stack){
        if(world instanceof WorldServer && StackUtil.isValid(stack) && pos != null){
            BlockPos offsetPos = pos.offset(side);
            IBlockState state = world.getBlockState(offsetPos);
            Block block = state.getBlock();
            boolean replaceable = block.isReplaceable(world, offsetPos);

            //Redstone
            if(replaceable && stack.getItem() == Items.REDSTONE){
                world.setBlockState(offsetPos, Blocks.REDSTONE_WIRE.getDefaultState(), 2);
                return StackUtil.addStackSize(stack, -1);
            }

            //Plants
            if(replaceable && stack.getItem() instanceof IPlantable){
                if(((IPlantable)stack.getItem()).getPlant(world, offsetPos).getBlock().canPlaceBlockAt(world, offsetPos)){
                    if(world.setBlockState(offsetPos, ((IPlantable)stack.getItem()).getPlant(world, offsetPos), 2)){
                        return StackUtil.addStackSize(stack, -1);
                    }
                }
            }

            //Everything else
            try{
                if(world instanceof WorldServer){
                    FakePlayer fake = FakePlayerFactory.getMinecraft((WorldServer)world);
                    stack.onItemUse(fake, world, offsetPos, fake.getActiveHand(), side.getOpposite(), 0.5F, 0.5F, 0.5F);
                    return stack;
                }
            }
            catch(Exception e){
                ModUtil.LOGGER.error("Something that places Blocks at "+offsetPos.getX()+", "+offsetPos.getY()+", "+offsetPos.getZ()+" in World "+world.provider.getDimension()+" threw an Exception! Don't let that happen again!", e);
            }
        }
        return stack;
    }

    public static void dropItemAtSide(EnumFacing side, World world, BlockPos pos, ItemStack stack){
        BlockPos coords = pos.offset(side);
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
        blocks.add(world.getBlockState(pos.offset(EnumFacing.NORTH)).getMaterial());
        blocks.add(world.getBlockState(pos.offset(EnumFacing.EAST)).getMaterial());
        blocks.add(world.getBlockState(pos.offset(EnumFacing.SOUTH)).getMaterial());
        blocks.add(world.getBlockState(pos.offset(EnumFacing.WEST)).getMaterial());
        return blocks;
    }

    public static boolean addToInventory(IInventory inventory, List<ItemStack> stacks, boolean actuallyDo, boolean shouldAlwaysWork){
        return addToInventory(inventory, stacks, EnumFacing.UP, actuallyDo, shouldAlwaysWork);
    }

    public static boolean addToInventory(IInventory inventory, List<ItemStack> stacks, EnumFacing side, boolean actuallyDo, boolean shouldAlwaysWork){
        return addToInventory(inventory, 0, inventory.getSizeInventory(), stacks, side, actuallyDo, shouldAlwaysWork);
    }

    //TODO This is disgusting and has to be updated to the capability system

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
                backupSlots[i] = StackUtil.validateCopy(stack);
            }
        }

        int working = 0;
        for(ItemStack stackToPutIn : stacks){
            for(int i = start; i < end; i++){
                if(shouldAlwaysWork || ((!(inventory instanceof ISidedInventory) || ((ISidedInventory)inventory).canInsertItem(i, stackToPutIn, side)) && inventory.isItemValidForSlot(i, stackToPutIn))){
                    ItemStack stackInQuestion = inventory.getStackInSlot(i);
                    if(StackUtil.isValid(stackToPutIn) && (!StackUtil.isValid(stackInQuestion) || (ItemUtil.canBeStacked(stackInQuestion, stackToPutIn) && stackInQuestion.getMaxStackSize() >= StackUtil.getStackSize(stackInQuestion)+StackUtil.getStackSize(stackToPutIn)))){
                        if(!StackUtil.isValid(stackInQuestion)){
                            inventory.setInventorySlotContents(i, stackToPutIn.copy());
                        }
                        else{
                            inventory.setInventorySlotContents(i, StackUtil.addStackSize(stackInQuestion, StackUtil.getStackSize(stackToPutIn)));
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

    public static int findFirstFilledSlot(NonNullList<ItemStack> slots){
        for(int i = 0; i < slots.size(); i++){
            if(StackUtil.isValid(slots.get(i))){
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

    //Cobbled together from Tinkers' Construct (with permission, thanks!) and PlayerInteractionManager code.
    //Breaking blocks is a hideous pain so yea.
    //This doesn't do any additional harvestability checks that the blocks itself don't do!
    public static boolean playerHarvestBlock(ItemStack stack, World world, EntityPlayer player, BlockPos pos){
        if(world.isAirBlock(pos)){
            return false;
        }

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if(!world.isRemote){
            world.playEvent(player, 2001, pos, Block.getStateId(state));
        }
        else{
            world.playEvent(2001, pos, Block.getStateId(state));
        }

        if(player.capabilities.isCreativeMode){

            block.onBlockHarvested(world, pos, state, player);
            if(block.removedByPlayer(state, world, pos, player, false)){
                block.onBlockDestroyedByPlayer(world, pos, state);
            }

            if(!world.isRemote){
                if(player instanceof EntityPlayerMP){
                    ((EntityPlayerMP)player).connection.sendPacket(new SPacketBlockChange(world, pos));
                }
            }

            return true;
        }

        stack.onBlockDestroyed(world, state, pos, player);

        if(!world.isRemote){
            if(player instanceof EntityPlayerMP){
                EntityPlayerMP playerMp = (EntityPlayerMP)player;

                int xp = ForgeHooks.onBlockBreakEvent(world, playerMp.interactionManager.getGameType(), playerMp, pos);
                if(xp == -1){
                    return false;
                }

                TileEntity tileEntity = world.getTileEntity(pos);
                if(block.removedByPlayer(state, world, pos, player, true)){
                    block.onBlockDestroyedByPlayer(world, pos, state);
                    block.harvestBlock(world, player, pos, state, tileEntity, stack);
                    block.dropXpOnBlockBreak(world, pos, xp);
                }

                playerMp.connection.sendPacket(new SPacketBlockChange(world, pos));
                return true;
            }
        }
        else{
            if(block.removedByPlayer(state, world, pos, player, true)){
                block.onBlockDestroyedByPlayer(world, pos, state);
            }

            if(StackUtil.getStackSize(stack) <= 0 && stack == player.getHeldItemMainhand()){
                ForgeEventFactory.onPlayerDestroyItem(player, stack, EnumHand.MAIN_HAND);
                player.setHeldItem(EnumHand.MAIN_HAND, null);
            }

            Minecraft mc = Minecraft.getMinecraft();
            mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, mc.objectMouseOver.sideHit));

            return true;
        }
        return false;
    }
}
