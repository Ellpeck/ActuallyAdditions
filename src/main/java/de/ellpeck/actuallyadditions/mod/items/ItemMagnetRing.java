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
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

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
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof PlayerEntity && !world.isClientSide && !ItemUtil.isEnabled(stack)) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.isCreative() || player.isSpectator()) {
                return;
            }
            if (!entity.isShiftKeyDown()) {
                //Get all the Items in the area
                int range = 5;
                List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(entity.getX() - range, entity.getY() - range, entity.getZ() - range, entity.getX() + range, entity.getY() + range, entity.getZ() + range));
                if (!items.isEmpty()) {
                    for (ItemEntity item : items) {
                        // TODO: [port] check this data is being saved on the time
                        if (item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                            continue;
                        }
                        if (item.isAlive() && !item.hasPickUpDelay()) {
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
    public ActionResult<ItemStack> use(World worldIn, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (!worldIn.isClientSide && player.isShiftKeyDown()) {
            ItemUtil.changeEnabled(player, hand);
            return ActionResult.success(player.getItemInHand(hand));
        }

        return super.use(worldIn, player, hand);
    }
}
