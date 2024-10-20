/*
 * This file ("ItemBattery.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ItemBattery extends ItemEnergy {

    public ItemBattery(int capacity, int transfer) {
        super(capacity, transfer);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return ItemUtil.isEnabled(stack);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level world, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof Player player && ItemUtil.isEnabled(stack) && !isSelected) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack slot = player.getInventory().getItem(i);
                if (!slot.isEmpty() && slot.getCount() == 1) {
                    Optional<IEnergyStorage> energy = Optional.ofNullable(slot.getCapability(Capabilities.EnergyStorage.ITEM));
                    energy.ifPresent(cap -> {
                        int extractable = this.extractEnergy(stack, Integer.MAX_VALUE, true);
                        int received = cap.receiveEnergy(extractable, false);

                        if (received > 0) {
                            this.extractEnergy(stack, received, false);
                        }
                    });
                }
            }
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (!worldIn.isClientSide && player.isShiftKeyDown()) {
            ItemUtil.changeEnabled(player, hand);
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        return super.use(worldIn, player, hand);
    }

    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull TooltipContext playerIn, @Nonnull List<Component> list, @Nonnull TooltipFlag advanced) {
        super.appendHoverText(stack, playerIn, list, advanced);
        list.add(Component.translatable("tooltip.actuallyadditions.battery." + (ItemUtil.isEnabled(stack)
            ? "discharge"
            : "noDischarge")));
        list.add(Component.translatable("tooltip.actuallyadditions.battery.changeMode"));
    }
}
