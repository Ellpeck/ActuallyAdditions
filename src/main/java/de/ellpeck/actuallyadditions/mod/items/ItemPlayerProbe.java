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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemPlayerProbe extends ItemBase {

    public ItemPlayerProbe() {
        super(ActuallyItems.defaultProps().maxStackSize(1));
    }

    // TODO: [port] might be the wrong event
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            CompoundNBT compound = stack.getOrCreateTag();
            if (compound.contains("UUIDMost")) {
                UUID id = compound.getUniqueId("UUID");
                PlayerEntity player = world.getPlayerByUuid(id);
                if (player != null) {
                    if (player.isSneaking()) {
                        ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                        ((PlayerEntity) entity).sendStatusMessage(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".playerProbe.disconnect.1"), false);
                        player.sendStatusMessage(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".playerProbe.notice"), false);
                        //TheAchievements.GET_UNPROBED.get(player);
                    }
                } else {
                    ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                    ((PlayerEntity) entity).sendStatusMessage(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".playerProbe.disconnect.2"), false);
                }
            }
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.FAIL;
        }

        ItemStack stack = player.getHeldItem(context.getHand());
        TileEntity tile = context.getWorld().getTileEntity(context.getPos());
        if (tile instanceof TileEntityPlayerInterface) {
            CompoundNBT compound = stack.getOrCreateTag();
            if (compound.contains("UUIDMost")) {
                if (!context.getWorld().isRemote) {
                    TileEntityPlayerInterface face = (TileEntityPlayerInterface) tile;
                    face.connectedPlayer = compound.getUniqueId("UUID");
                    face.playerName = compound.getString("Name");
                    face.markDirty();
                    face.sendUpdate();

                    ItemPhantomConnector.clearStorage(stack, "UUIDLeast", "UUIDMost", "Name");
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack aStack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (!player.world.isRemote) {
            ItemStack stack = player.getHeldItemMainhand();
            if (StackUtil.isValid(stack) && stack.getItem() == this) {
                if (entity instanceof PlayerEntity) {
                    PlayerEntity playerHit = (PlayerEntity) entity;

                    if (!playerHit.isSneaking()) {
                        CompoundNBT compound = stack.getOrCreateTag();
                        compound.putString("Name", playerHit.getName().getString());
                        compound.putUniqueId("UUID", playerHit.getUniqueID());
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World playerIn, List<ITextComponent> tooltip, ITooltipFlag advanced) {
        if (stack.getOrCreateTag().contains("Name")) {
            String name = stack.getOrCreateTag().getString("Name");
            tooltip.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + ".playerProbe.probing").appendString(": " + name));
        }
    }
}
