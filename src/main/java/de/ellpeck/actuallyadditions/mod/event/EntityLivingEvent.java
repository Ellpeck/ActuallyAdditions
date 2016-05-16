/*
 * This file ("EntityLivingEvent.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemWingsOfTheBats;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EntityLivingEvent{

    @SubscribeEvent
    public void livingUpdateEvent(LivingUpdateEvent event){
        //Ocelots dropping Hair Balls
        if(event.getEntityLiving() != null && event.getEntityLiving().worldObj != null && !event.getEntityLiving().worldObj.isRemote){
            if((event.getEntityLiving() instanceof EntityOcelot && ((EntityOcelot)event.getEntityLiving()).isTamed()) || (event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().getUniqueID().equals(/*KittyVanCat*/ UUID.fromString("681d4e20-10ef-40c9-a0a5-ba2f1995ef44")))){
                if(ConfigBoolValues.DO_CAT_DROPS.isEnabled()){
                    if(Util.RANDOM.nextInt(5000)+1 == 1){
                        EntityItem item = new EntityItem(event.getEntityLiving().worldObj, event.getEntityLiving().posX+0.5, event.getEntityLiving().posY+0.5, event.getEntityLiving().posZ+0.5, new ItemStack(InitItems.itemHairyBall));
                        event.getEntityLiving().worldObj.spawnEntityInWorld(item);
                    }
                }
            }
        }

        //Wings allowing Flight
        this.doWingStuff(event);
    }

    /**
     * Makes players be able to fly if they have Wings Of The Bats equipped
     * (Partially excerpted from Botania's Wing System by Vazkii (as I had fiddled around with the system and couldn't make it work) with permission, thanks!)
     */
    private void doWingStuff(LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            boolean wingsEquipped = ItemWingsOfTheBats.getWingItem(player) != null;

            //If Player isn't (really) winged
            if(!ItemWingsOfTheBats.isPlayerWinged(player)){
                if(wingsEquipped){
                    //Make the Player actually winged
                    ItemWingsOfTheBats.addWingsToPlayer(player);
                }
            }
            //If Player is (or should be) winged
            else{
                if(wingsEquipped){
                    //Allow the Player to fly when he has Wings equipped
                    player.capabilities.allowFlying = true;
                }
                else{
                    //Make the Player not winged
                    ItemWingsOfTheBats.removeWingsFromPlayer(player);
                    //Reset Player's Values
                    if(!player.capabilities.isCreativeMode){
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                        //Enables Fall Damage again (Automatically gets disabled for some reason)
                        player.capabilities.disableDamage = false;
                    }
                }
            }
        }
    }
}
