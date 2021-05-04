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

import java.util.List;

public class ItemMagnetRing extends ItemEnergy {

    public ItemMagnetRing() {
        super(200000, 1000);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return !ItemUtil.isEnabled(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof PlayerEntity && !world.isRemote && !ItemUtil.isEnabled(stack)) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.isCreative() || player.isSpectator()) {
                return;
            }
            if (!entity.isSneaking()) {
                //Get all the Items in the area
                int range = 5;
                List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(entity.getPosX() - range, entity.getPosY() - range, entity.getPosZ() - range, entity.getPosX() + range, entity.getPosY() + range, entity.getPosZ() + range));
                if (!items.isEmpty()) {
                    for (ItemEntity item : items) {
                        // TODO: [port] check this data is being saved on the time
                        if (item.getPersistentData().getBoolean("PreventRemoteMovement")) {
                            continue;
                        }
                        if (item.isAlive() && !item.cannotPickup()) {
                            int energyForItem = 50 * item.getItem().getCount();

                            if (this.getEnergyStored(stack) >= energyForItem) {
                                ItemStack oldItem = item.getItem().copy();

                                item.onCollideWithPlayer(player);

                                if (!player.isCreative()) {
                                    if (!item.isAlive() || !ItemStack.areItemStacksEqual(item.getItem(), oldItem)) {
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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        if (!worldIn.isRemote && player.isSneaking()) {
            ItemUtil.changeEnabled(player, hand);
            return ActionResult.resultSuccess(player.getHeldItem(hand));
        }

        return super.onItemRightClick(worldIn, player, hand);
    }
}
