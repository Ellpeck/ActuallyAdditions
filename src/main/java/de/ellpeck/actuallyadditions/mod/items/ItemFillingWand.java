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

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemFillingWand extends ItemEnergy {

    public ItemFillingWand(String name) {
        super(500000, 1000, name);
    }

    private static boolean removeFittingItem(IBlockState state, EntityPlayer player) {
        Block block = state.getBlock();
        ItemStack stack = new ItemStack(block, 1, block.damageDropped(state));

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

    private static void saveData(ItemStack pickBlock, IBlockState state, ItemStack wand) {
        if (!wand.hasTagCompound()) wand.setTagCompound(new NBTTagCompound());
        wand.getTagCompound().setInteger("state", Block.getStateId(state));
        wand.getTagCompound().setString("name", pickBlock.getDisplayName());

    }

    private static Pair<IBlockState, String> loadData(ItemStack stack) {
        if (stack.hasTagCompound()) return Pair.of(Block.getStateById(stack.getTagCompound().getInteger("state")), stack.getTagCompound().getString("name"));
        return null;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && player.getItemInUseCount() <= 0) {
            if (player.isSneaking()) {
                IBlockState state = world.getBlockState(pos);
                ItemStack pick = state.getBlock().getPickBlock(state, world.rayTraceBlocks(player.getPositionVector(), new Vec3d(pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ)), world, pos, player);
                saveData(pick, state, stack);
                return EnumActionResult.SUCCESS;
            } else if (loadData(stack) != null) {
                if (!stack.hasTagCompound()) {
                    stack.setTagCompound(new NBTTagCompound());
                }
                NBTTagCompound compound = stack.getTagCompound();

                if (compound.getInteger("CurrX") == 0 && compound.getInteger("CurrY") == 0 && compound.getInteger("CurrZ") == 0) {
                    compound.setInteger("FirstX", pos.getX());
                    compound.setInteger("FirstY", pos.getY());
                    compound.setInteger("FirstZ", pos.getZ());

                    player.setActiveHand(hand);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
        if (!world.isRemote) {
            boolean clear = true;
            if (entity instanceof EntityPlayer) {
                RayTraceResult result = WorldUtil.getNearestBlockWithDefaultReachDistance(world, (EntityPlayer) entity);
                if (result != null && result.getBlockPos() != null) {
                    if (!stack.hasTagCompound()) {
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    NBTTagCompound compound = stack.getTagCompound();

                    BlockPos pos = result.getBlockPos();
                    compound.setInteger("SecondX", pos.getX());
                    compound.setInteger("SecondY", pos.getY());
                    compound.setInteger("SecondZ", pos.getZ());

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
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);

        if (!world.isRemote) {
            boolean shouldClear = false;

            if (isSelected) {
                if (entity instanceof EntityPlayer && stack.hasTagCompound()) {
                    EntityPlayer player = (EntityPlayer) entity;
                    boolean creative = player.capabilities.isCreativeMode;

                    NBTTagCompound compound = stack.getTagCompound();

                    BlockPos firstPos = new BlockPos(compound.getInteger("FirstX"), compound.getInteger("FirstY"), compound.getInteger("FirstZ"));
                    BlockPos secondPos = new BlockPos(compound.getInteger("SecondX"), compound.getInteger("SecondY"), compound.getInteger("SecondZ"));

                    if (!BlockPos.ORIGIN.equals(firstPos) && !BlockPos.ORIGIN.equals(secondPos)) {
                        int energyUse = 1500;

                        Pair<IBlockState, String> data = loadData(stack);
                        if (data != null && (creative || this.getEnergyStored(stack) >= energyUse)) {
                            IBlockState replaceState = data.getLeft();
                            int lowestX = Math.min(firstPos.getX(), secondPos.getX());
                            int lowestY = Math.min(firstPos.getY(), secondPos.getY());
                            int lowestZ = Math.min(firstPos.getZ(), secondPos.getZ());

                            int currX = compound.getInteger("CurrX");
                            int currY = compound.getInteger("CurrY");
                            int currZ = compound.getInteger("CurrZ");

                            BlockPos pos = new BlockPos(lowestX + currX, lowestY + currY, lowestZ + currZ);
                            IBlockState state = world.getBlockState(pos);

                            if (state.getBlock().isReplaceable(world, pos) && replaceState.getBlock().canPlaceBlockAt(world, pos)) {
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
                                compound.setInteger("CurrX", currX);
                                compound.setInteger("CurrY", currY);
                                compound.setInteger("CurrZ", currZ);
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
    public void addInformation(ItemStack stack, World playerIn, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);

        String display = StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".item_filling_wand.selectedBlock.none");

        Pair<IBlockState, String> data = loadData(stack);
        if (data != null) {
            display = data.getRight();
        }

        tooltip.add(String.format("%s: %s", StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".item_filling_wand.selectedBlock"), display));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
