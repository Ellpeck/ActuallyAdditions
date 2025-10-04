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

import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.components.LastXY;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import javax.annotation.Nonnull;

public class ItemWaterBowl extends ItemBase {

    public ItemWaterBowl() {
        super(ActuallyItems.defaultProps().stacksTo(1));
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent.RightClickItem event) {
        if (CommonConfig.Other.WATER_BOWL.get() && event.getItemStack().is(Items.BOWL)) {
            Player player = event.getEntity();
            Level level = event.getLevel();
            BlockHitResult trace = getPlayerPOVHitResult(
                    level, player, ClipContext.Fluid.SOURCE_ONLY //Using pick will also return flowing water
            );
            if (trace.getType() != HitResult.Type.BLOCK) {
                return;
            }

            ItemStack bowl = event.getItemStack();
            BlockPos pos = trace.getBlockPos();

            if (level.mayInteract(player, pos) && player.mayUseItemAt(pos.relative(trace.getDirection()), trace.getDirection(), bowl)) {
                BlockState blockState = level.getBlockState(pos);
                FluidState fluidState = blockState.getFluidState();

                if (fluidState.isSourceOfType(Fluids.WATER)) {
                    if (blockState.getBlock() instanceof BucketPickup pickup) {
                        ItemStack fillResult = pickup.pickupBlock(player, level, pos, blockState);
                        if (!fillResult.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(bowl.getItem()));
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
                        }

                        pickup.getPickupSound(blockState).ifPresent(soundEvent -> player.playSound(soundEvent, 1.0F, 1.0F));
                    }

                    ItemStack waterBowl = new ItemStack(ActuallyItems.WATER_BOWL.get());
                    ItemStack inHand = ItemUtils.createFilledResult(bowl, player, waterBowl);
                    player.setItemInHand(event.getHand(), inHand);
                }
            }
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        HitResult trace = player.pick(player.blockInteractionRange(), 1.0F, false);

        if (trace.getType() == HitResult.Type.MISS) {
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
    public void inventoryTick(@Nonnull ItemStack stack, Level world, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide) {
            if (CommonConfig.Other.WATER_BOWL_LOSS.get()) {
                if (world.getGameTime() % 10 == 0 && world.random.nextFloat() >= 0.5F) {
                    int lastX = 0;
                    int lastY = 0;

                    if (stack.has(ActuallyComponents.LAST_XY)) {
                        LastXY lastXY = stack.get(ActuallyComponents.LAST_XY);
                        lastX = lastXY.x();
                        lastY = lastXY.y();
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
                        stack.set(ActuallyComponents.LAST_XY, new LastXY((int) entity.getX(), (int) entity.getY()));
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
    public boolean shouldCauseReequipAnimation(@Nonnull ItemStack oldStack, @Nonnull ItemStack newStack, boolean slotChanged) {
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
