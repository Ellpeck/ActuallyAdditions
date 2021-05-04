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
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemSolidifiedExperience extends ItemBase {

    public static final int SOLID_XP_AMOUNT = 8;

    public ItemSolidifiedExperience() {
        super();

        // TODO: [port] move this to another place
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event) {
        if (ConfigBoolValues.DO_XP_DROPS.isEnabled()) {
            if (event.getEntityLiving().world != null && !event.getEntityLiving().world.isRemote && event.getSource().getTrueSource() instanceof PlayerEntity && event.getEntityLiving().world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                //Drop Solidified XP
                if (event.getEntityLiving() instanceof CreatureEntity) {
                    if (event.getEntityLiving().world.rand.nextInt(10) <= event.getLootingLevel() * 2) {
                        event.getDrops().add(new ItemEntity(event.getEntityLiving().world, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), new ItemStack(ActuallyItems.SOLIDIFIED_EXPERIENCE.get(), event.getEntityLiving().world.rand.nextInt(2 + event.getLootingLevel()) + 1)));
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            int amount;
            if (!player.isSneaking()) {
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

            if (ConfigBoolValues.SOLID_XP_ALWAYS_ORBS.currentValue || player instanceof FakePlayer) {
                ExperienceOrbEntity orb = new ExperienceOrbEntity(world, player.getPosX() + 0.5, player.getPosY() + 0.5, player.getPosZ() + 0.5, amount);
                orb.getPersistentData().putBoolean(ActuallyAdditions.MODID + "FromSolidified", true);
                world.addEntity(orb);
            } else {
                player.addExperienceLevel(amount);
            }
        }
        return ActionResult.resultSuccess(stack);
    }
}
