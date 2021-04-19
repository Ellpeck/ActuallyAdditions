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

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemWaterBowl extends ItemBase {

    public ItemWaterBowl() {
        super(ActuallyItems.defaultProps().maxStackSize(1));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent.RightClickItem event) {
        if (event.getWorld() != null) {
            if (ConfigBoolValues.WATER_BOWL.isEnabled()) {
                if (StackUtil.isValid(event.getItemStack()) && event.getItemStack().getItem() == Items.BOWL) {
                    RayTraceResult rayTrace = WorldUtil.getNearestBlockWithDefaultReachDistance(event.getWorld(), event.getPlayer(), true, false, false);
                    if (rayTrace.getType() != RayTraceResult.Type.BLOCK) {
                        return;
                    }

                    BlockRayTraceResult trace = (BlockRayTraceResult) rayTrace;
                    ActionResult<ItemStack> result = ForgeEventFactory.onBucketUse(event.getPlayer(), event.getWorld(), event.getItemStack(), trace);
                    if (result == null) {
                        if (event.getPlayer().canPlayerEdit(trace.getPos().offset(trace.getFace()), trace.getFace(), event.getItemStack())) {
                            BlockState state = event.getWorld().getBlockState(trace.getPos());
                            Block block = state.getBlock();

                            // TODO: Validate fluid check
                            if ((block == Blocks.WATER) && state.get(BlockStateProperties.LEVEL_0_15) == 0) {
                                event.getPlayer().playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);

                                if (!event.getWorld().isRemote) {
                                    event.getWorld().setBlockState(trace.getPos(), Blocks.AIR.getDefaultState(), 11);
                                    ItemStack reduced = StackUtil.shrink(event.getItemStack(), 1);

                                    ItemStack bowl = new ItemStack(ActuallyItems.itemWaterBowl.get());
                                    if (!StackUtil.isValid(reduced)) {
                                        event.getPlayer().setHeldItem(event.getHand(), bowl);
                                    } else if (!event.getPlayer().inventory.addItemStackToInventory(bowl.copy())) {
                                        ItemEntity entityItem = new ItemEntity(event.getWorld(), event.getPlayer().getPosX(), event.getPlayer().getPosY(), event.getPlayer().getPosZ(), bowl.copy());
                                        entityItem.setPickupDelay(0);
                                        event.getWorld().addEntity(entityItem);
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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        RayTraceResult trace = WorldUtil.getNearestBlockWithDefaultReachDistance(world, player);
        ActionResult<ItemStack> result = ForgeEventFactory.onBucketUse(player, world, stack, trace);
        if (result != null) {
            return result;
        }

        if (trace == null) {
            return ActionResult.resultPass(stack);
        } else if (trace.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.resultPass(stack);
        } else {
            BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;
            BlockPos pos = blockTrace.getPos();

            if (!world.isBlockModifiable(player, pos)) {
                return ActionResult.resultFail(stack);
            } else {
                BlockPos pos1 = world.getBlockState(pos).getMaterial().isReplaceable() && blockTrace.getFace() == Direction.UP
                    ? pos
                    : pos.offset(blockTrace.getFace());

                if (!player.canPlayerEdit(pos1, blockTrace.getFace(), stack)) {
                    return ActionResult.resultFail(stack);
                } else if (this.tryPlaceContainedLiquid(player, world, pos1, false)) {
                    return !player.isCreative()
                        ? ActionResult.resultSuccess(new ItemStack(Items.BOWL))
                        : ActionResult.resultSuccess(stack);
                } else {
                    return ActionResult.resultFail(stack);
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            if (ConfigBoolValues.WATER_BOWL_LOSS.isEnabled()) {
                if (world.getGameTime() % 10 == 0 && world.rand.nextFloat() >= 0.5F) {
                    int lastX = 0;
                    int lastY = 0;

                    if (stack.hasTag()) {
                        CompoundNBT compound = stack.getOrCreateTag();
                        lastX = compound.getInt("lastX");
                        lastY = compound.getInt("lastY");
                    }

                    boolean change = false;
                    if (lastX != 0 && lastX != (int) entity.getPosX() || lastY != 0 && lastY != (int) entity.getPosY()) {
                        if (!entity.isSneaking()) {
                            if (entity instanceof PlayerEntity) {
                                PlayerEntity player = (PlayerEntity) entity;
                                if (this.tryPlaceContainedLiquid(player, world, player.getPosition(), true)) {
                                    this.checkReplace(player, stack, new ItemStack(Items.BOWL), itemSlot);
                                }
                            }
                        }
                        change = true;
                    }

                    if (change || lastX == 0 || lastY == 0) {
                        CompoundNBT compound = stack.getOrCreateTag();
                        compound.putInt("lastX", (int) entity.getPosX());
                        compound.putInt("lastY", (int) entity.getPosY());
                    }
                }
            }
        }
    }

    private void checkReplace(PlayerEntity player, ItemStack old, ItemStack stack, int slot) {
        if (player.inventory.getStackInSlot(slot) == old) {
            player.inventory.setInventorySlotContents(slot, stack);
        } else if (player.inventory.offHandInventory.get(slot) == old) {
            player.inventory.offHandInventory.set(slot, stack);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.areItemsEqual(oldStack, newStack);
    }

    public boolean tryPlaceContainedLiquid(PlayerEntity player, World world, BlockPos pos, boolean finite) {
        BlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();
        boolean nonSolid = !material.isSolid();
        boolean replaceable = state.getMaterial().isReplaceable();

        if (!world.isAirBlock(pos) && !nonSolid && !replaceable) {
            return false;
        } else {
            if (world.getDimensionType().isUltrawarm()) {
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                for (int k = 0; k < 8; k++) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            } else {
                if (!world.isRemote && (nonSolid || replaceable) && !material.isLiquid()) {
                    world.destroyBlock(pos, true);
                }

                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);

                BlockState placeState = Blocks.WATER.getDefaultState();
                world.setBlockState(pos, placeState, 3);
            }

            return true;
        }
    }
}
