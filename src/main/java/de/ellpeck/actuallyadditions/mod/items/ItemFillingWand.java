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
import de.ellpeck.actuallyadditions.mod.util.Help;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ItemFillingWand extends ItemEnergy {

    public ItemFillingWand() {
        super(500000, 1000);
    }

    private static boolean removeFittingItem(BlockState state, PlayerEntity player) {
        Block block = state.getBlock();
        ItemStack stack = new ItemStack(block, 1);

        if (StackUtil.isValid(stack)) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if (StackUtil.isValid(slot) && slot.isItemEqual(stack)) {
                    slot.shrink(1);
                    if (!StackUtil.isValid(slot)) {
                        player.inventory.setInventorySlotContents(i, StackUtil.getEmpty());
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private static void saveData(BlockState state, ItemStack wand) {
        wand.getOrCreateTag().put("state", NBTUtil.writeBlockState(state));
    }

    private static Optional<BlockState> loadData(ItemStack stack) {
        if (stack.getOrCreateTag().contains("state")) {
            return Optional.of(NBTUtil.readBlockState(stack.getOrCreateTag().getCompound("state")));
        }

        return Optional.empty();
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer() == null) {
            return ActionResultType.PASS;
        }

        ItemStack stack = context.getPlayer().getHeldItem(context.getHand());
        if (!context.getWorld().isRemote && context.getPlayer().getItemInUseCount() <= 0) {
            if (context.getPlayer().isSneaking()) {
                BlockState state = context.getWorld().getBlockState(context.getPos());
                saveData(state, stack);
                return ActionResultType.SUCCESS;
            } else if (loadData(stack).isPresent()) {
                CompoundNBT compound = stack.getOrCreateTag();

                if (compound.getInt("CurrX") == 0 && compound.getInt("CurrY") == 0 && compound.getInt("CurrZ") == 0) {
                    compound.putInt("FirstX", context.getPos().getX());
                    compound.putInt("FirstY", context.getPos().getY());
                    compound.putInt("FirstZ", context.getPos().getZ());

                    context.getPlayer().setActiveHand(context.getHand());
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onItemUse(context);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entity, int timeLeft) {
        if (!world.isRemote) {
            boolean clear = true;
            if (entity instanceof PlayerEntity) {
                RayTraceResult result = WorldUtil.getNearestBlockWithDefaultReachDistance(world, (PlayerEntity) entity);
                if (result instanceof BlockRayTraceResult) {
                    CompoundNBT compound = stack.getOrCreateTag();

                    BlockPos pos = ((BlockRayTraceResult) result).getPos();
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

        super.onPlayerStoppedUsing(stack, world, entity, timeLeft);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);


        if (!world.isRemote) {
            boolean shouldClear = false;

            if (isSelected) {
                if (entity instanceof PlayerEntity && stack.hasTag()) {
                    PlayerEntity player = (PlayerEntity) entity;
                    boolean creative = player.isCreative();

                    CompoundNBT compound = stack.getOrCreateTag();

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

                            if (state.getMaterial().isReplaceable() && replaceState.isValidPosition(world, pos)) {
                                if (creative || removeFittingItem(replaceState, player)) {
                                    world.setBlockState(pos, replaceState, 2);

                                    SoundType sound = replaceState.getBlock().getSoundType(replaceState, world, pos, player);
                                    world.playSound(null, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, sound.getVolume() / 2F + .5F, sound.getPitch() * 0.8F);

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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        IFormattableTextComponent display = loadData(stack)
            .map(state -> state.getBlock().getTranslatedName())
            .orElse(Help.Trans("tooltip", "item_filling_wand.selectedBlock.none"));

        tooltip.add(Help.Trans("tooltip", "item_filling_wand.selectedBlock", display.getString()));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }
}
