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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemPlayerProbe extends ItemBase {

    public ItemPlayerProbe() {
        super(ActuallyItems.defaultProps().stacksTo(1));
    }

    // TODO: [port] might be the wrong event
    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide) {
            CompoundTag compound = stack.getOrCreateTag();
            if (compound.contains("UUID")) {
                UUID id = compound.getUUID("UUID");
                Player player = world.getPlayerByUUID(id);
                if (player != null) {
                    if (player.isShiftKeyDown()) {
                        ItemPhantomConnector.clearStorage(stack, "UUID", "Name");
                        ((Player) entity).displayClientMessage(Component.translatable("tooltip.actuallyadditions.playerProbe.disconnect.1"), false);
                        player.displayClientMessage(Component.translatable("tooltip.actuallyadditions.playerProbe.notice"), false);
                        //TheAchievements.GET_UNPROBED.get(player);
                    }
                } else {
                    ItemPhantomConnector.clearStorage(stack, "UUID", "Name");
                    ((Player) entity).displayClientMessage(Component.translatable("tooltip.actuallyadditions.playerProbe.disconnect.2"), false);
                }
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }

        ItemStack stack = player.getItemInHand(context.getHand());
        BlockEntity tile = context.getLevel().getBlockEntity(context.getClickedPos());
        if (tile instanceof TileEntityPlayerInterface face) {
            CompoundTag compound = stack.getOrCreateTag();
            if (compound.contains("UUID")) {
                if (!context.getLevel().isClientSide) {
                    face.connectedPlayer = compound.getUUID("UUID");
                    face.playerName = compound.getString("Name");
                    face.setChanged();
                    face.sendUpdate();

                    ItemPhantomConnector.clearStorage(stack, "UUID", "Name");
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack aStack, Player player, LivingEntity entity, InteractionHand hand) {
        if (!player.level().isClientSide) {
            ItemStack stack = player.getMainHandItem();
            if (StackUtil.isValid(stack) && stack.getItem() == this) {
                if (entity instanceof Player playerHit) {

	                if (!playerHit.isShiftKeyDown()) {
                        CompoundTag compound = stack.getOrCreateTag();
                        compound.putString("Name", playerHit.getName().getString());
                        compound.putUUID("UUID", playerHit.getUUID());
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level playerIn, List<Component> tooltip, TooltipFlag advanced) {
        if (stack.getOrCreateTag().contains("Name")) {
            String name = stack.getOrCreateTag().getString("Name");
            tooltip.add(Component.translatable("tooltip.actuallyadditions.playerProbe.probing").append(": " + name));
        }
    }
}
