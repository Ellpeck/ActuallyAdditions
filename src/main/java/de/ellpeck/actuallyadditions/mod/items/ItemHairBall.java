/*
 * This file ("ItemHairyBall.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

public class ItemHairBall extends ItemBase {

    private final UUID KittyVanCatUUID = UUID.fromString("681d4e20-10ef-40c9-a0a5-ba2f1995ef44");

    public ItemHairBall() {
        super();

        // TODO: [port] move this.
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void livingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        //Ocelots dropping Hair Balls
        if (CommonConfig.OTHER.DO_CAT_DROPS.get() && event.getEntityLiving() != null && event.getEntityLiving().level != null && !event.getEntityLiving().level.isClientSide) {
            if (event.getEntityLiving() instanceof OcelotEntity && catIsTamedReflection((OcelotEntity) event.getEntityLiving()) || event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().getUUID().equals(this.KittyVanCatUUID)) {
                if (event.getEntityLiving().level.random.nextInt(CommonConfig.OTHER.FUR_CHANCE.get()) == 0) {
                    ItemEntity item = new ItemEntity(event.getEntityLiving().level, event.getEntityLiving().getX() + 0.5, event.getEntityLiving().getY() + 0.5, event.getEntityLiving().getZ() + 0.5, new ItemStack(ActuallyItems.HAIRY_BALL.get()));
                    event.getEntityLiving().level.addFreshEntity(item);
                }
            }
        }
    }

    public static boolean catIsTamedReflection(OcelotEntity entity) {
        try {
            Method isTrusting = OcelotEntity.class.getDeclaredMethod("isTrusting");
            return (boolean) isTrusting.invoke(entity);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            ItemStack returnItem = this.getRandomReturnItem(world.random);
            if (!player.inventory.add(returnItem)) {
                ItemEntity entityItem = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), returnItem);
                entityItem.setPickUpDelay(0);
                player.level.addFreshEntity(entityItem);
            }
            stack.shrink(1);

            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, world.random.nextFloat() * 0.1F + 0.9F);
        }
        return ActionResult.success(stack);
    }

    public ItemStack getRandomReturnItem(Random rand) {
        return WeightedRandom.getRandomItem(rand, ActuallyAdditionsAPI.BALL_OF_FUR_RETURN_ITEMS).returnItem.copy();
    }
}
