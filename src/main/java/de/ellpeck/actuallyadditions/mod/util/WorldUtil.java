/*
 * This file ("WorldUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditionsClient;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public final class WorldUtil {

    public static boolean doItemInteraction(SlotlessableItemHandlerWrapper extractWrapper, SlotlessableItemHandlerWrapper insertWrapper, int maxExtract) {
        return doItemInteraction(extractWrapper, insertWrapper, maxExtract, null);
    }

    public static boolean doItemInteraction(SlotlessableItemHandlerWrapper extractWrapper, SlotlessableItemHandlerWrapper insertWrapper, int maxExtract, FilterSettings filter) {
        return doItemInteraction(extractWrapper, insertWrapper, maxExtract, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, filter);
    }

    public static boolean doItemInteraction(SlotlessableItemHandlerWrapper extractWrapper, SlotlessableItemHandlerWrapper insertWrapper, int maxExtract, int extractSlotStart, int extractSlotEnd, int insertSlotStart, int insertSlotEnd, FilterSettings filter) {
        ItemStack theoreticalExtract = extractItem(extractWrapper, maxExtract, true, extractSlotStart, extractSlotEnd, filter);
        if (StackUtil.isValid(theoreticalExtract)) {
            ItemStack remaining = StackUtil.insertItem(insertWrapper, theoreticalExtract, false, insertSlotStart, insertSlotEnd);
            if (!ItemStack.matches(remaining, theoreticalExtract)) {
                int toExtract = theoreticalExtract.getCount() - remaining.getCount();
                extractItem(extractWrapper, toExtract, false, extractSlotStart, extractSlotEnd, filter);
                return true;
            }
        }
        return false;
    }

    public static ItemStack extractItem(SlotlessableItemHandlerWrapper extractWrapper, int maxExtract, boolean simulate, int slotStart, int slotEnd, FilterSettings filter) {
        ItemStack extracted = ItemStack.EMPTY;

        if (ActuallyAdditions.commonCapsLoaded) {
/*            Object handler = extractWrapper.getSlotlessHandler();
            if (handler instanceof ISlotlessItemHandler) {
                ISlotlessItemHandler slotless = (ISlotlessItemHandler) handler;

                if (filter == null || !filter.needsCheck()) {
                    extracted = slotless.extractItem(maxExtract, simulate);
                    return extracted;
                } else {
                    ItemStack would = slotless.extractItem(maxExtract, true);
                    if (filter.check(would)) {
                        if (simulate) {
                            extracted = would;
                        } else {
                            extracted = slotless.extractItem(maxExtract, false);
                        }
                    }
                    //Leave the possibility to fall back to vanilla when there is a filter
                }
            }*/
        }

        if (!StackUtil.isValid(extracted)) {
            IItemHandler handler = extractWrapper.getNormalHandler();
            if (handler != null) {
                for (int i = Math.max(0, slotStart); i < Math.min(slotEnd, handler.getSlots()); i++) {
                    if (filter == null || !filter.needsCheck() || filter.check(handler.getStackInSlot(i))) {
                        extracted = handler.extractItem(i, maxExtract, simulate);

                        if (StackUtil.isValid(extracted)) {
                            break;
                        }
                    }
                }
            }
        }

        return extracted;
    }

    public static void doEnergyInteraction(Level level, BlockPos posFrom, BlockPos posTo, Direction sideTo, int maxTransfer) {
        if (maxTransfer > 0) {
            Direction opp = sideTo == null
                ? null
                : sideTo.getOpposite();
            IEnergyStorage handlerFrom = level.getCapability(Capabilities.EnergyStorage.BLOCK, posFrom, sideTo);
            IEnergyStorage handlerTo = level.getCapability(Capabilities.EnergyStorage.BLOCK, posTo, opp);
            if (handlerFrom != null && handlerTo != null) {
                int drain = handlerFrom.extractEnergy(maxTransfer, true);
                if (drain > 0) {
                    int filled = handlerTo.receiveEnergy(drain, false);
                    handlerFrom.extractEnergy(filled, false);
                }
            }
        }
    }

    public static void doFluidInteraction(Level level, BlockPos posFrom, BlockPos posTo, Direction sideTo, int maxTransfer) {
        if (maxTransfer > 0) {
            IFluidHandler handlerFrom = level.getCapability(Capabilities.FluidHandler.BLOCK, posFrom, sideTo);
            IFluidHandler handlerTo = level.getCapability(Capabilities.FluidHandler.BLOCK, posTo, sideTo.getOpposite());
            if (handlerFrom != null && handlerTo != null) {
                FluidStack drain = handlerFrom.drain(maxTransfer, IFluidHandler.FluidAction.SIMULATE);
                if (!drain.isEmpty()) {
                    int filled = handlerTo.fill(drain.copy(), IFluidHandler.FluidAction.EXECUTE);
                    handlerFrom.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }
    }

    /**
     * Checks if a given Block with a given Meta is present in given Positions
     *
     * @param positions The Positions, an array of {x, y, z} arrays containing Positions
     * @param block     The Block
     * @param level     The World
     * @return Is every block present?
     */
    public static boolean hasBlocksInPlacesGiven(BlockPos[] positions, Block block, Level level) {
        for (BlockPos pos : positions) {
            BlockState state = level.getBlockState(pos);
            if (!(state.getBlock() == block)) {
                return false;
            }
        }
        return true;
    }

    public static ItemStack useItemAtSide(Direction side, Level level, BlockPos pos, ItemStack stack) {
        if (level instanceof ServerLevel && StackUtil.isValid(stack) && pos != null) {
            BlockPos offsetPos = pos.relative(side);
            BlockState state = level.getBlockState(offsetPos);
            Block block = state.getBlock();
            boolean replaceable = false; //= block.canBeReplaced(world, offsetPos); //TODO

            //Redstone
            if (replaceable && stack.getItem() == Items.REDSTONE) {
                level.setBlock(offsetPos, Blocks.REDSTONE_WIRE.defaultBlockState(), 2);
                return StackUtil.shrink(stack, 1);
            }

            //Plants
/*            if (replaceable && stack.getItem() instanceof IPlantable) { //TODO
                if (((IPlantable) stack.getItem()).getPlant(world, offsetPos).getBlock().canPlaceBlockAt(world, offsetPos)) {
                    if (world.setBlock(offsetPos, ((IPlantable) stack.getItem()).getPlant(world, offsetPos), 2)) {
                        return StackUtil.shrink(stack, 1);
                    }
                }
            }*/

            //Everything else
            try {
                FakePlayer fake = FakePlayerFactory.getMinecraft((ServerLevel) level);
//                if (fake.connection == null) {
//                    fake.connection = new NetHandlerSpaghettiServer(fake);
//                }
                //ItemStack heldBefore = fake.getMainHandItem();
                setHandItemWithoutAnnoyingSound(fake, InteractionHand.MAIN_HAND, stack.copy());
                BlockHitResult ray = new BlockHitResult(new Vec3(0.5, 0.5, 0.5), side.getOpposite(), offsetPos, true);
                fake.gameMode.useItemOn(fake, level, fake.getMainHandItem(), InteractionHand.MAIN_HAND, ray);
                ItemStack result = fake.getItemInHand(InteractionHand.MAIN_HAND);
                //setHandItemWithoutAnnoyingSound(fake, Hand.MAIN_HAND, heldBefore);
                return result;
            } catch (Exception e) {
                ActuallyAdditions.LOGGER.error("Something that places Blocks at " + offsetPos.getX() + ", " + offsetPos.getY() + ", " + offsetPos.getZ() + " in World " + level.dimension() + " threw an Exception! Don't let that happen again!", e);
            }
        }
        return stack;
    }

    public static boolean dropItemAtSide(Direction side, Level level, BlockPos pos, ItemStack stack) {
        BlockPos coords = pos.relative(side);
        if (level.hasChunkAt(coords)) {
            ItemEntity item = new ItemEntity(level, coords.getX() + 0.5, coords.getY() + 0.5, coords.getZ() + 0.5, stack);
            item.setDeltaMovement(0,0,0);

            return level.addFreshEntity(item);
        }
        return false;
    }

    //TODO standardize this to dunswe?
    public static Direction getDirectionBySidesInOrder(int side) {
        switch (side) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.NORTH;
            case 3:
                return Direction.EAST;
            case 4:
                return Direction.SOUTH;
            default:
                return Direction.WEST;
        }
    }

    public static Direction getDirectionByPistonRotation(BlockState state) {
        return state.getValue(BlockStateProperties.FACING);
    }

    public static ArrayList<BlockState> getStatesAround(Level level, BlockPos pos) {
        ArrayList<BlockState> blocks = new ArrayList<>();
        blocks.add(level.getBlockState(pos.relative(Direction.NORTH)));
        blocks.add(level.getBlockState(pos.relative(Direction.EAST)));
        blocks.add(level.getBlockState(pos.relative(Direction.SOUTH)));
        blocks.add(level.getBlockState(pos.relative(Direction.WEST)));
        return blocks;
    }

    public static void setHandItemWithoutAnnoyingSound(Player player, InteractionHand hand, ItemStack stack) {
        if (hand == InteractionHand.MAIN_HAND) {
            player.getInventory().items.set(player.getInventory().selected, stack);
        } else if (hand == InteractionHand.OFF_HAND) {
            player.getInventory().offhand.set(0, stack);
        }
    }

    //I think something is up with this, but I'm not entirely certain what.
    public static float fireFakeHarvestEventsForDropChance(BlockEntity caller, List<ItemStack> drops, Level level, BlockPos pos) {
        if (level instanceof ServerLevel) {
            FakePlayer fake = FakePlayerFactory.getMinecraft((ServerLevel) level);
            BlockPos tePos = caller.getBlockPos();
            fake.setPos(tePos.getX() + 0.5, tePos.getY() + 0.5, tePos.getZ() + 0.5);
            BlockState state = level.getBlockState(pos);

            BreakEvent event = new BreakEvent(level, pos, state, fake);
            NeoForge.EVENT_BUS.post(event);
            if (!event.isCanceled()) {
                return EventHooks.doPlayerHarvestCheck(fake, state, true) ? 1F : 0F;
                //return ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, 0, 1, false, fake); //TODO what?!
            }
        }
        return 0F;
    }

    /**
     * Tries to break a block as if this player had broken it.  This is a complex operation.
     *
     * @param stack  The player's current held stack, main hand.
     * @param level  The player's world.
     * @param player The player that is breaking this block.
     * @param pos    The pos to break.
     * @return If the break was successful.
     */
    public static boolean breakExtraBlock(ItemStack stack, Level level, Player player, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (player.isCreative()) {
            if (block.onDestroyedByPlayer(state, level, pos, player, false, state.getFluidState())) {
                block.destroy(level, pos, state);
            }

            // send update to client
            if (!level.isClientSide) {
                //((ServerPlayerEntity) player).connection.send(new SPacketBlockChange(world, pos)); //TODO dunno what this is
            }
            return true;
        }

        // callback to the tool the player uses. Called on both sides. This damages the tool n stuff.
        stack.mineBlock(level, state, pos, player);

        // server sided handling
        if (!level.isClientSide) {
            // send the blockbreak event
            int xp = CommonHooks.onBlockBreakEvent(level, ((ServerPlayer) player).gameMode.getGameModeForPlayer(), (ServerPlayer) player, pos);
            if (xp == -1) {
                return false;
            }

            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (block.onDestroyedByPlayer(state, level, pos, player, true, state.getFluidState())) { // boolean is if block can be harvested, checked above
                block.destroy(level, pos, state);
                block.playerDestroy(level, player, pos, state, blockEntity, stack);
                block.popExperience(((ServerLevel) level), pos, xp);
            }

            // always send block update to client
            //((ServerPlayerEntity) player).connection.send(new SPacketBlockChange(world, pos)); //TODO how
            return true;
        }
        // client sided handling
        else {
            // clientside we do a "this block has been clicked on long enough to be broken" call. This should not send any new packets
            // the code above, executed on the server, sends a block-updates that give us the correct state of the block we destroy.

            // following code can be found in PlayerControllerMP.onPlayerDestroyBlock
            level.levelEvent(2001, pos, Block.getId(state));
            if (block.onDestroyedByPlayer(state, level, pos, player, true, state.getFluidState())) {
                block.destroy(level, pos, state);
            }
            // callback to the tool
            stack.mineBlock(level, state, pos, player);

            // send an update to the server, so we get an update back

            ActuallyAdditionsClient.sendBreakPacket(pos);
            return true;
        }
    }
}
