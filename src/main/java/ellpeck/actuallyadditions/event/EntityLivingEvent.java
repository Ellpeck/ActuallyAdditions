package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemWingsOfTheBats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import java.util.Random;

public class EntityLivingEvent{

    @SubscribeEvent
    public void livingUpdateEvent(LivingUpdateEvent event){
        //Ocelots dropping Hair Balls
        if(!event.entityLiving.worldObj.isRemote){
            if(event.entityLiving instanceof EntityOcelot){
                EntityOcelot theOcelot = (EntityOcelot)event.entityLiving;
                if(ConfigBoolValues.DO_CAT_DROPS.isEnabled() && theOcelot.isTamed()){
                    if(new Random().nextInt(ConfigIntValues.CAT_DROP_CHANCE.getValue())+1 == 1){
                        EntityItem item = new EntityItem(theOcelot.worldObj, theOcelot.posX + 0.5, theOcelot.posY + 0.5, theOcelot.posZ + 0.5, new ItemStack(InitItems.itemHairyBall));
                        theOcelot.worldObj.spawnEntityInWorld(item);
                    }
                }
            }
        }

        //Wings allowing Flight
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
