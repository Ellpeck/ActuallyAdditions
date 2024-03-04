/*
 * This file ("ItemSolidifiedExperience.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.FakePlayer;

public class ItemSolidifiedExperience extends ItemBase {

    public static final int SOLID_XP_AMOUNT = 8;

    public ItemSolidifiedExperience() {
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            int amount;
            if (!player.isShiftKeyDown()) {
                amount = SOLID_XP_AMOUNT;
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            } else {
                amount = SOLID_XP_AMOUNT * stack.getCount();
                if (!player.isCreative()) {
                    stack.setCount(0);
                }
            }

            if (CommonConfig.Other.SOLID_XP_ALWAYS_ORBS.get() || player instanceof FakePlayer) {
                ExperienceOrb orb = new ExperienceOrb(world, player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, amount);
                orb.getPersistentData().putBoolean(ActuallyAdditions.MODID + "FromSolidified", true);
                world.addFreshEntity(orb);
            } else {
                player.giveExperiencePoints(amount);
            }
        }
        return InteractionResultHolder.success(stack);
    }
}
