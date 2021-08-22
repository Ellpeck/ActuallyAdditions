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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemDrillUpgrade extends ItemBase {

    public final UpgradeType type;

    public ItemDrillUpgrade(UpgradeType type) {
        super(ActuallyItems.defaultProps().stacksTo(1));
        this.type = type;
    }

    public static int getSlotToPlaceFrom(ItemStack stack) {
        CompoundNBT compound = stack.getTagCompound();
        if (compound != null) {
            return compound.getInt("SlotToPlaceFrom") - 1;
        }
        return -1;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && this.type == UpgradeType.PLACER) {
            this.setSlotToPlaceFrom(stack, player.inventory.selected);
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }
        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    public void setSlotToPlaceFrom(ItemStack stack, int slot) {
        CompoundNBT compound = stack.getOrCreateTag();
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
