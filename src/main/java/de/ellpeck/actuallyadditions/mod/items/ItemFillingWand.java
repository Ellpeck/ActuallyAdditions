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

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ItemFillingWand extends ItemEnergy {

    public ItemFillingWand() {
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
        wand.getOrCreateTag().put("state", NbtUtils.writeBlockState(state));
    }

    private static Optional<BlockState> loadData(ItemStack stack) {
        if (stack.getOrCreateTag().contains("state")) {
            return Optional.of(NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), stack.getOrCreateTag().getCompound("state")));
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
                CompoundTag compound = stack.getOrCreateTag();

                if (compound.getInt("CurrX") == 0 && compound.getInt("CurrY") == 0 && compound.getInt("CurrZ") == 0) {
                    compound.putInt("FirstX", context.getClickedPos().getX());
                    compound.putInt("FirstY", context.getClickedPos().getY());
                    compound.putInt("FirstZ", context.getClickedPos().getZ());

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
            if (entity instanceof Player) {
                HitResult result = WorldUtil.getNearestBlockWithDefaultReachDistance(world, (Player) entity);
                if (result instanceof BlockHitResult) {
                    CompoundTag compound = stack.getOrCreateTag();

                    BlockPos pos = ((BlockHitResult) result).getBlockPos();
                    compound.putInt("SecondX", pos.getX());
                    compound.putInt("SecondY", pos.getY());
                    compound.putInt("SecondZ", pos.getZ());

                    clear = false;
                }
            }

            if (clear) {
                ItemPhantomConnector.clearStorage(stack, "FirstX", "FirstY", "FirstZ");
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
                if (entity instanceof Player player && stack.hasTag()) {
	                boolean creative = player.isCreative();

                    CompoundTag compound = stack.getOrCreateTag();

                    BlockPos firstPos = new BlockPos(compound.getInt("FirstX"), compound.getInt("FirstY"), compound.getInt("FirstZ"));
                    BlockPos secondPos = new BlockPos(compound.getInt("SecondX"), compound.getInt("SecondY"), compound.getInt("SecondZ"));

                    if (!BlockPos.ZERO.equals(firstPos) && !BlockPos.ZERO.equals(secondPos)) {
                        int energyUse = 1500;

                        Optional<BlockState> data = loadData(stack);
                        if (data.isPresent() && (creative || this.getEnergyStored(stack) >= energyUse)) {
                            BlockState replaceState = data.get(); // not the best way to do this.
                            int lowestX = Math.min(firstPos.getX(), secondPos.getX());
                            int lowestY = Math.min(firstPos.getY(), secondPos.getY());
                            int lowestZ = Math.min(firstPos.getZ(), secondPos.getZ());

                            int currX = compound.getInt("CurrX");
                            int currY = compound.getInt("CurrY");
                            int currZ = compound.getInt("CurrZ");

                            BlockPos pos = new BlockPos(lowestX + currX, lowestY + currY, lowestZ + currZ);
                            BlockState state = world.getBlockState(pos);

                            if (state.canBeReplaced() && replaceState.canSurvive(world, pos)) {
                                if (creative || removeFittingItem(replaceState, player)) {
                                    world.setBlock(pos, replaceState, 2);

                                    SoundType sound = replaceState.getBlock().getSoundType(replaceState, world, pos, player);
                                    world.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, sound.getVolume() / 2F + .5F, sound.getPitch() * 0.8F);

                                    if (!creative) {
                                        this.extractEnergyInternal(stack, energyUse, false);
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
                                compound.putInt("CurrX", currX);
                                compound.putInt("CurrY", currY);
                                compound.putInt("CurrZ", currZ);
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
                ItemPhantomConnector.clearStorage(stack, "FirstX", "FirstY", "FirstZ", "SecondX", "SecondY", "SecondZ", "CurrX", "CurrY", "CurrZ");
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        MutableComponent display = loadData(stack)
            .map(state -> state.getBlock().getName())
            .orElse(Component.translatable("tooltip.actuallyadditions.item_filling_wand.selected_block.none"));

        tooltip.add(Component.translatable("tooltip.actuallyadditions.item_filling_wand.selected_block", display.getString()));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }
}
