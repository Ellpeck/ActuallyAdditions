package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.ItemWingsOfTheBats;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
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
            ItemStack wings = ItemWingsOfTheBats.getWingItem(player);
            boolean wingsEquipped = wings != null;

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

                    if(((ItemWingsOfTheBats)wings.getItem()).isHastily){
                        //Speed Upgrade with hastily Wings
                        this.setFlySpeed(player, ItemWingsOfTheBats.FLY_SPEED);
                    }
                    else{
                        //When switching from Hastily to not Hastily immediately, still remove the Speed!
                        this.setFlySpeed(player, ItemWingsOfTheBats.STANDARD_FLY_SPEED);
                    }
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
                    //Remove the Speed Effect
                    this.setFlySpeed(player, ItemWingsOfTheBats.STANDARD_FLY_SPEED);
                }
            }
        }
    }

    private void setFlySpeed(EntityPlayer player, float speed){
        try{
            ReflectionHelper.setPrivateValue(PlayerCapabilities.class, player.capabilities, speed, 5);
        }
        catch(Exception e){
            ModUtil.LOGGER.fatal("Something went wrong here!", e);
        }
    }
}
