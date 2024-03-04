/*
 * This file ("ItemWaterBowl.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class ItemWaterBowl extends ItemBase {

    public ItemWaterBowl() {
        super(ActuallyItems.defaultProps().stacksTo(1));
        //MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel() != null) {
            if (CommonConfig.Other.WATER_BOWL.get()) {
                if (StackUtil.isValid(event.getItemStack()) && event.getItemStack().getItem() == Items.BOWL) {
                    HitResult rayTrace = WorldUtil.getNearestBlockWithDefaultReachDistance(event.getLevel(), event.getEntity(), true, false, false);
                    if (rayTrace.getType() != HitResult.Type.BLOCK) {
                        return;
                    }

                    BlockHitResult trace = (BlockHitResult) rayTrace;
                    InteractionResultHolder<ItemStack> result = EventHooks.onBucketUse(event.getEntity(), event.getLevel(), event.getItemStack(), trace);
                    if (result == null) {
                        if (event.getEntity().mayUseItemAt(trace.getBlockPos().relative(trace.getDirection()), trace.getDirection(), event.getItemStack())) {
                            BlockState state = event.getLevel().getBlockState(trace.getBlockPos());
                            Block block = state.getBlock();

                            // TODO: Validate fluid check
                            if ((block == Blocks.WATER) && state.getValue(BlockStateProperties.LEVEL) == 0) {
                                event.getEntity().playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);

                                if (!event.getLevel().isClientSide) {
                                    event.getLevel().setBlock(trace.getBlockPos(), Blocks.AIR.defaultBlockState(), 11);
                                    ItemStack reduced = StackUtil.shrink(event.getItemStack(), 1);

                                    ItemStack bowl = new ItemStack(ActuallyItems.WATER_BOWL.get());
                                    if (!StackUtil.isValid(reduced)) {
                                        event.getEntity().setItemInHand(event.getHand(), bowl);
                                    } else if (!event.getEntity().getInventory().add(bowl.copy())) {
                                        ItemEntity entityItem = new ItemEntity(event.getLevel(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), bowl.copy());
                                        entityItem.setPickUpDelay(0);
                                        event.getLevel().addFreshEntity(entityItem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        HitResult trace = WorldUtil.getNearestBlockWithDefaultReachDistance(world, player);
        InteractionResultHolder<ItemStack> result = EventHooks.onBucketUse(player, world, stack, trace);
        if (result != null) {
            return result;
        }

        if (trace == null) {
            return InteractionResultHolder.pass(stack);
        } else if (trace.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        } else {
            BlockHitResult blockTrace = (BlockHitResult) trace;
            BlockPos pos = blockTrace.getBlockPos();

            if (!world.mayInteract(player, pos)) {
                return InteractionResultHolder.fail(stack);
            } else {
                BlockPos pos1 = world.getBlockState(pos).canBeReplaced() && blockTrace.getDirection() == Direction.UP
                    ? pos
                    : pos.relative(blockTrace.getDirection());

                if (!player.mayUseItemAt(pos1, blockTrace.getDirection(), stack)) {
                    return InteractionResultHolder.fail(stack);
                } else if (this.tryPlaceContainedLiquid(player, world, pos1, false)) {
                    return !player.isCreative()
                        ? InteractionResultHolder.success(new ItemStack(Items.BOWL))
                        : InteractionResultHolder.success(stack);
                } else {
                    return InteractionResultHolder.fail(stack);
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide) {
            if (CommonConfig.Other.WATER_BOWL_LOSS.get()) {
                if (world.getGameTime() % 10 == 0 && world.random.nextFloat() >= 0.5F) {
                    int lastX = 0;
                    int lastY = 0;

                    if (stack.hasTag()) {
                        CompoundTag compound = stack.getOrCreateTag();
                        lastX = compound.getInt("lastX");
                        lastY = compound.getInt("lastY");
                    }

                    boolean change = false;
                    if (lastX != 0 && lastX != (int) entity.getX() || lastY != 0 && lastY != (int) entity.getY()) {
                        if (!entity.isShiftKeyDown()) {
                            if (entity instanceof Player player) {
	                            if (this.tryPlaceContainedLiquid(player, world, player.blockPosition(), true)) {
                                    this.checkReplace(player, stack, new ItemStack(Items.BOWL), itemSlot);
                                }
                            }
                        }
                        change = true;
                    }

                    if (change || lastX == 0 || lastY == 0) {
                        CompoundTag compound = stack.getOrCreateTag();
                        compound.putInt("lastX", (int) entity.getX());
                        compound.putInt("lastY", (int) entity.getY());
                    }
                }
            }
        }
    }

    private void checkReplace(Player player, ItemStack old, ItemStack stack, int slot) {
        if (player.getInventory().getItem(slot) == old) {
            player.getInventory().setItem(slot, stack);
        } else if (player.getInventory().offhand.get(slot) == old) {
            player.getInventory().offhand.set(slot, stack);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }

    public boolean tryPlaceContainedLiquid(Player player, Level world, BlockPos pos, boolean finite) {
        BlockState state = world.getBlockState(pos);
        boolean nonSolid = !state.isSolid();
        boolean replaceable = state.canBeReplaced();

        if (!world.isEmptyBlock(pos) && !nonSolid && !replaceable) {
            return false;
        } else {
            if (world.dimensionType().ultraWarm()) {
                world.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                for (int k = 0; k < 8; k++) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            } else {
                if (!world.isClientSide && (nonSolid || replaceable) && world.getFluidState(pos).isEmpty()) {
                    world.destroyBlock(pos, true);
                }

                world.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);

                BlockState placeState = Blocks.WATER.defaultBlockState();
                world.setBlock(pos, placeState, 3);
            }

            return true;
        }
    }
}
