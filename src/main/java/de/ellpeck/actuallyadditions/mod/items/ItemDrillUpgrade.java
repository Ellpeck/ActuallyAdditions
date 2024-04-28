/*
 * This file ("ItemDrillUpgrade.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemDrillUpgrade extends ItemBase {

    public final UpgradeType type;

    public ItemDrillUpgrade(UpgradeType type) {
        super(ActuallyItems.defaultProps().stacksTo(1));
        this.type = type;
    }

    public static int getSlotToPlaceFrom(ItemStack stack) {
        CompoundTag compound = stack.getTag();
        if (compound != null) {
            return compound.getInt("SlotToPlaceFrom") - 1;
        }
        return -1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && this.type == UpgradeType.PLACER) {
            this.setSlotToPlaceFrom(stack, player.getInventory().selected);
            player.sendSystemMessage(Component.literal("Set the slot to place from to " + (player.getInventory().selected + 1)));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }

    public void setSlotToPlaceFrom(ItemStack stack, int slot) {
        CompoundTag compound = stack.getOrCreateTag();
        compound.putInt("SlotToPlaceFrom", slot + 1);

        stack.setTag(compound);
    }

    public enum UpgradeType {
        SPEED,
        SPEED_II,
        SPEED_III,
        SILK_TOUCH,
        FORTUNE,
        FORTUNE_II,
        THREE_BY_THREE,
        FIVE_BY_FIVE,
        PLACER
    }
}
