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
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;
import java.util.UUID;

public class ItemHairyBall extends ItemBase{

    public ItemHairyBall(String name){
        super(name);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void livingUpdateEvent(LivingEvent.LivingUpdateEvent event){
        //Ocelots dropping Hair Balls
        if(event.getEntityLiving() != null){
            if(event.getEntityLiving().world != null && !event.getEntityLiving().world.isRemote){
                if((event.getEntityLiving() instanceof EntityOcelot && ((EntityOcelot)event.getEntityLiving()).isTamed()) || (event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().getUniqueID().equals(/*KittyVanCat*/ UUID.fromString("681d4e20-10ef-40c9-a0a5-ba2f1995ef44")))){
                    if(ConfigBoolValues.DO_CAT_DROPS.isEnabled()){
                        if(event.getEntityLiving().world.rand.nextInt(5000)+1 == 1){
                            EntityItem item = new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX+0.5, event.getEntityLiving().posY+0.5, event.getEntityLiving().posZ+0.5, new ItemStack(InitItems.itemHairyBall));
                            event.getEntityLiving().world.spawnEntity(item);
                        }
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        ItemStack stack = player.getHeldItem(hand);
        if(!world.isRemote){
            ItemStack returnItem = this.getRandomReturnItem(world.rand);
            if(!player.inventory.addItemStackToInventory(returnItem)){
                EntityItem entityItem = new EntityItem(player.world, player.posX, player.posY, player.posZ, returnItem);
                entityItem.setPickupDelay(0);
                player.world.spawnEntity(entityItem);
            }
            stack = StackUtil.addStackSize(stack, -1);

            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, world.rand.nextFloat()*0.1F+0.9F);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public ItemStack getRandomReturnItem(Random rand){
        return WeightedRandom.getRandomItem(rand, ActuallyAdditionsAPI.BALL_OF_FUR_RETURN_ITEMS).returnItem.copy();
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
