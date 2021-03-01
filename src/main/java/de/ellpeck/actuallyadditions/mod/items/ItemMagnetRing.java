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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class ItemMagnetRing extends ItemEnergy {

    public ItemMagnetRing() {
        super(200000, 1000, name);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return !ItemUtil.isEnabled(stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        if (entity instanceof PlayerEntity && !world.isRemote && !ItemUtil.isEnabled(stack)) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.isCreative() || player.isSpectator()) {
                return;
            }
            if (!entity.isSneaking()) {
                //Get all the Items in the area
                int range = 5;
                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range));
                if (!items.isEmpty()) {
                    for (EntityItem item : items) {
                        if (item.getEntityData().getBoolean("PreventRemoteMovement")) {
                            continue;
                        }
                        if (!item.isDead && !item.cannotPickup()) {
                            int energyForItem = 50 * item.getItem().getCount();

                            if (this.getEnergyStored(stack) >= energyForItem) {
                                ItemStack oldItem = item.getItem().copy();

                                item.onCollideWithPlayer(player);

                                if (!player.isCreative()) {
                                    if (item.isDead || !ItemStack.areItemStacksEqual(item.getItem(), oldItem)) {
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
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return super.onItemRightClick(worldIn, player, hand);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
