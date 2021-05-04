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
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
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
        if (ConfigBoolValues.DO_CAT_DROPS.isEnabled() && event.getEntityLiving() != null && event.getEntityLiving().world != null && !event.getEntityLiving().world.isRemote) {
            if (event.getEntityLiving() instanceof OcelotEntity && catIsTamedReflection((OcelotEntity) event.getEntityLiving()) || event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().getUniqueID().equals(this.KittyVanCatUUID)) {
                if (event.getEntityLiving().world.rand.nextInt(ConfigIntValues.FUR_CHANCE.getValue()) == 0) {
                    ItemEntity item = new ItemEntity(event.getEntityLiving().world, event.getEntityLiving().getPosX() + 0.5, event.getEntityLiving().getPosY() + 0.5, event.getEntityLiving().getPosZ() + 0.5, new ItemStack(ActuallyItems.HAIRY_BALL.get()));
                    event.getEntityLiving().world.addEntity(item);
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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            ItemStack returnItem = this.getRandomReturnItem(world.rand);
            if (!player.inventory.addItemStackToInventory(returnItem)) {
                ItemEntity entityItem = new ItemEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), returnItem);
                entityItem.setPickupDelay(0);
                player.world.addEntity(entityItem);
            }
            stack.shrink(1);

            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, world.rand.nextFloat() * 0.1F + 0.9F);
        }
        return ActionResult.resultSuccess(stack);
    }

    public ItemStack getRandomReturnItem(Random rand) {
        return WeightedRandom.getRandomItem(rand, ActuallyAdditionsAPI.BALL_OF_FUR_RETURN_ITEMS).returnItem.copy();
    }
}
