/*
 * This file ("ItemPlayerProbe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlayerInterface;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemPlayerProbe extends ItemBase {

    public ItemPlayerProbe(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            if (stack.hasTagCompound()) {
                CompoundNBT compound = stack.getTagCompound();
                if (compound.hasKey("UUIDMost")) {
                    UUID id = compound.getUniqueId("UUID");
                    PlayerEntity player = world.getPlayerEntityByUUID(id);
                    if (player != null) {
                        if (player.isSneaking()) {
                            ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                            entity.sendMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".playerProbe.disconnect.1"));
                            player.sendMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".playerProbe.notice"));
                            //TheAchievements.GET_UNPROBED.get(player);
                        }
                    } else {
                        ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                        entity.sendMessage(new TextComponentTranslation("tooltip." + ActuallyAdditions.MODID + ".playerProbe.disconnect.2"));
                    }
                }
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityPlayerInterface) {
            if (stack.hasTagCompound()) {
                CompoundNBT compound = stack.getTagCompound();
                if (compound.hasKey("UUIDMost")) {
                    if (!world.isRemote) {
                        TileEntityPlayerInterface face = (TileEntityPlayerInterface) tile;
                        face.connectedPlayer = compound.getUniqueId("UUID");
                        face.playerName = compound.getString("Name");
                        face.markDirty();
                        face.sendUpdate();

                        ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                    }
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack aStack, PlayerEntity player, EntityLivingBase entity, Hand hand) {
        if (!player.world.isRemote) {
            ItemStack stack = player.getHeldItemMainhand();
            if (StackUtil.isValid(stack) && stack.getItem() == this) {
                if (entity instanceof PlayerEntity) {
                    PlayerEntity playerHit = (PlayerEntity) entity;

                    if (!playerHit.isSneaking()) {
                        if (!stack.hasTagCompound()) {
                            stack.setTagCompound(new CompoundNBT());
                        }

                        CompoundNBT compound = stack.getTagCompound();
                        compound.setString("Name", playerHit.getName());
                        compound.setUniqueId("UUID", playerHit.getUniqueID());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
        if (stack.hasTagCompound()) {
            String name = stack.getTagCompound().getString("Name");
            if (name != null) {
                tooltip.add(StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".playerProbe.probing") + ": " + name);
            }
        }
    }
}
