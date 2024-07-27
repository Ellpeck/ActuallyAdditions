/*
 * This file ("ItemFillingWand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class Filler extends ItemEnergy {

    public Filler() {
        super(500000, 1000);
    }

    private static boolean removeFittingItem(BlockState state, Player player) {
        Block block = state.getBlock();
        ItemStack stack = new ItemStack(block, 1);

        if (StackUtil.isValid(stack)) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack slot = player.getInventory().getItem(i);
                if (StackUtil.isValid(slot) && ItemStack.isSameItem(slot, stack)) {
                    slot.shrink(1);
                    if (!StackUtil.isValid(slot)) {
                        player.getInventory().setItem(i, ItemStack.EMPTY);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private static void saveData(BlockState state, ItemStack wand) {
        wand.set(ActuallyComponents.BLOCKSTATE, state);
    }

    private static Optional<BlockState> loadData(ItemStack stack) {
        if (stack.has(ActuallyComponents.BLOCKSTATE)) {
            return Optional.ofNullable(stack.get(ActuallyComponents.BLOCKSTATE));
        }
        return Optional.empty();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) {
            return InteractionResult.PASS;
        }

        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        if (!context.getLevel().isClientSide && context.getPlayer().getUseItemRemainingTicks() <= 0) {
            if (context.getPlayer().isCrouching()) {
                BlockState state = context.getLevel().getBlockState(context.getClickedPos());
                saveData(state, stack);
                return InteractionResult.SUCCESS;
            } else if (loadData(stack).isPresent()) {
                if (stack.getOrDefault(ActuallyComponents.FILLER_CURRENT, BlockPos.ZERO).equals(BlockPos.ZERO)) {

                    stack.set(ActuallyComponents.FILLER_FIRST, new BlockPos(context.getClickedPos()));

                    context.getPlayer().startUsingItem(context.getHand());
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        if (!world.isClientSide) {
            boolean clear = true;
            if (entity instanceof Player player) {
                HitResult result = player.pick(Util.getReachDistance(player), 1f, false);
                if (result instanceof BlockHitResult) {
                    BlockPos pos = ((BlockHitResult) result).getBlockPos();

                    stack.set(ActuallyComponents.FILLER_SECOND, new BlockPos(pos));

                    clear = false;
                }
            }

            if (clear) {
                ItemPhantomConnector.clearStorage(stack, ActuallyComponents.FILLER_FIRST.get(), ActuallyComponents.FILLER_SECOND.get(), ActuallyComponents.FILLER_CURRENT.get());
            }
        }

        super.releaseUsing(stack, world, entity, timeLeft);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);


        if (!world.isClientSide) {
            boolean shouldClear = false;

            if (isSelected) {
                if (entity instanceof Player player) {
                    boolean creative = player.isCreative();

                    BlockPos firstPos = stack.getOrDefault(ActuallyComponents.FILLER_FIRST, BlockPos.ZERO);
                    BlockPos secondPos = stack.getOrDefault(ActuallyComponents.FILLER_SECOND, BlockPos.ZERO);
                    BlockPos currentPos = stack.getOrDefault(ActuallyComponents.FILLER_CURRENT, BlockPos.ZERO);

                    if (!BlockPos.ZERO.equals(firstPos) && !BlockPos.ZERO.equals(secondPos)) {
                        int energyUse = 1500;

                        Optional<BlockState> data = loadData(stack);
                        if (data.isPresent() && (creative || this.getEnergyStored(stack) >= energyUse)) {
                            BlockState replaceState = data.get(); // not the best way to do this.
                            int lowestX = Math.min(firstPos.getX(), secondPos.getX());
                            int lowestY = Math.min(firstPos.getY(), secondPos.getY());
                            int lowestZ = Math.min(firstPos.getZ(), secondPos.getZ());

                            int currX = currentPos.getX();
                            int currY = currentPos.getY();
                            int currZ = currentPos.getZ();

                            BlockPos pos = new BlockPos(lowestX + currX, lowestY + currY, lowestZ + currZ);
                            BlockState state = world.getBlockState(pos);

                            if (state.canBeReplaced() && replaceState.canSurvive(world, pos)) {
                                if (creative || removeFittingItem(replaceState, player)) {
                                    world.setBlock(pos, replaceState, 2);

                                    SoundType sound = replaceState.getBlock().getSoundType(replaceState, world, pos, player);
                                    world.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, sound.getVolume() / 2F + .5F, sound.getPitch() * 0.8F);

                                    if (!creative) {
                                        this.extractEnergy(stack, energyUse, false);
                                    }
                                } else {
                                    shouldClear = true;
                                }
                            }

                            int distX = Math.abs(secondPos.getX() - firstPos.getX());
                            int distY = Math.abs(secondPos.getY() - firstPos.getY());
                            int distZ = Math.abs(secondPos.getZ() - firstPos.getZ());

                            currX++;
                            if (currX > distX) {
                                currX = 0;
                                currY++;
                                if (currY > distY) {
                                    currY = 0;
                                    currZ++;
                                    if (currZ > distZ) {
                                        shouldClear = true;
                                    }
                                }
                            }

                            if (!shouldClear) {
                                stack.set(ActuallyComponents.FILLER_CURRENT, new BlockPos(currX, currY, currZ));
                            }
                        } else {
                            shouldClear = true;
                        }
                    }
                }
            } else {
                shouldClear = true;
            }

            if (shouldClear) {
                ItemPhantomConnector.clearStorage(stack, ActuallyComponents.FILLER_FIRST.get(), ActuallyComponents.FILLER_SECOND.get(), ActuallyComponents.FILLER_CURRENT.get());
            }
        }
    }

    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);

        MutableComponent display = loadData(stack)
            .map(state -> state.getBlock().getName())
            .orElse(Component.translatable("tooltip.actuallyadditions.item_filling_wand.selected_block.none"));

        tooltip.add(Component.translatable("tooltip.actuallyadditions.item_filling_wand.selected_block", display.getString()));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return Integer.MAX_VALUE;
    }
}
