/*
 * This file ("ItemMagnetRing.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemMagnetRing extends ItemEnergy {

    public ItemMagnetRing() {
        super(200000, 1000);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return !ItemUtil.isEnabled(stack);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level world, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof Player player && !world.isClientSide && !ItemUtil.isEnabled(stack)) {
	        if (player.isCreative() || player.isSpectator()) {
                return;
            }
            if (!entity.isShiftKeyDown()) {
                //Get all the Items in the area
                int range = 5;
                List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, new AABB(entity.getX() - range, entity.getY() - range, entity.getZ() - range, entity.getX() + range, entity.getY() + range, entity.getZ() + range));
                if (!items.isEmpty()) {
                    for (ItemEntity item : items) {
                        if (item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                            continue;
                        }
                        if (item.isAlive() && !item.hasPickUpDelay()) { // TODO config
                            int energyForItem = 50 * item.getItem().getCount();

                            if (this.getEnergyStored(stack) >= energyForItem) {
                                ItemStack oldItem = item.getItem().copy();

                                item.playerTouch(player);

                                if (!player.isCreative()) {
                                    if (!item.isAlive() || !ItemStack.matches(item.getItem(), oldItem)) {
                                        this.extractEnergyInternal(stack, energyForItem, false);
                                    }
                                }
                            }
                        }
                    }
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
}
