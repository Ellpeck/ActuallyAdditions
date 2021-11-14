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
            if (event.getEntityLiving().level != null && !event.getEntityLiving().level.isClientSide && event.getSource().getEntity() instanceof PlayerEntity && event.getEntityLiving().level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                //Drop Solidified XP
                if (event.getEntityLiving() instanceof CreatureEntity) {
                    if (event.getEntityLiving().level.random.nextInt(10) <= event.getLootingLevel() * 2) {
                        event.getDrops().add(new ItemEntity(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), new ItemStack(ActuallyItems.SOLIDIFIED_EXPERIENCE.get(), event.getEntityLiving().level.random.nextInt(2 + event.getLootingLevel()) + 1)));
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
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

            if (CommonConfig.OTHER.SOLID_XP_ALWAYS_ORBS.get() || player instanceof FakePlayer) {
                ExperienceOrbEntity orb = new ExperienceOrbEntity(world, player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, amount);
                orb.getPersistentData().putBoolean(ActuallyAdditions.MODID + "FromSolidified", true);
                world.addFreshEntity(orb);
            } else {
                player.giveExperienceLevels(amount);
            }
        }
        return ActionResult.success(stack);
    }
}
