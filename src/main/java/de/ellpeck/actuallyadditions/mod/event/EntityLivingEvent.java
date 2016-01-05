/*
 * This file ("EntityLivingEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.ItemWingsOfTheBats;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import java.util.UUID;

public class EntityLivingEvent{

    @SubscribeEvent
    public void livingUpdateEvent(LivingUpdateEvent event){
        //Ocelots dropping Hair Balls
        if(event.entityLiving != null && event.entityLiving.worldObj != null && !event.entityLiving.worldObj.isRemote){
            if((event.entityLiving instanceof EntityOcelot && ((EntityOcelot)event.entityLiving).isTamed()) || (event.entityLiving instanceof EntityPlayer && event.entityLiving.getUniqueID().equals(/*KittyVanCat*/ UUID.fromString("681d4e20-10ef-40c9-a0a5-ba2f1995ef44")))){
                if(ConfigBoolValues.DO_CAT_DROPS.isEnabled()){
                    if(Util.RANDOM.nextInt(5000)+1 == 1){
                        EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX+0.5, event.entityLiving.posY+0.5, event.entityLiving.posZ+0.5, new ItemStack(InitItems.itemHairyBall));
                        event.entityLiving.worldObj.spawnEntityInWorld(item);
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
        if(event.entityLiving instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.entityLiving;
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
