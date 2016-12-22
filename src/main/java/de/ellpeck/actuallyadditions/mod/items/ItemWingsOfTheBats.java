/*
 * This file ("ItemWingsOfTheBats.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;

public class ItemWingsOfTheBats extends ItemBase{

    /**
     * A List containing all of the Players that can currently fly
     * Used so that Flight from other Mods' Items doesn't get broken when
     * these Wings aren't worn
     * <p>
     * Saves Remote Players separately to make de-synced Event Calling
     * not bug out Capabilities when taking off the Wings
     * <p>
     * (Partially excerpted from Botania's Wing System by Vazkii (as I had fiddled around with the system and couldn't make it work) with permission, thanks!)
     */
    public static final ArrayList<String> WINGED_PLAYERS = new ArrayList<String>();

    public ItemWingsOfTheBats(String name){
        super(name);
        this.setMaxStackSize(1);

        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Checks if the Player is winged
     *
     * @param player The Player
     * @return Winged?
     */
    public static boolean isPlayerWinged(EntityPlayer player){
        return WINGED_PLAYERS.contains(player.getUniqueID()+(player.worldObj.isRemote ? "-Remote" : ""));
    }

    /**
     * Same as above, but Remote Checking is done automatically
     */
    public static void removeWingsFromPlayer(EntityPlayer player){
        removeWingsFromPlayer(player, player.worldObj.isRemote);
    }

    /**
     * Removes the Player from the List of Players that have Wings
     *
     * @param player      The Player
     * @param worldRemote If the World the Player is in is remote
     */
    public static void removeWingsFromPlayer(EntityPlayer player, boolean worldRemote){
        WINGED_PLAYERS.remove(player.getUniqueID()+(worldRemote ? "-Remote" : ""));
    }

    /**
     * Adds the Player to the List of Players that have Wings
     *
     * @param player The Player
     */
    public static void addWingsToPlayer(EntityPlayer player){
        WINGED_PLAYERS.add(player.getUniqueID()+(player.worldObj.isRemote ? "-Remote" : ""));
    }

    /**
     * Checks if the Player has Wings in its Inventory
     *
     * @param player The Player
     * @return The Wings
     */
    public static ItemStack getWingItem(EntityPlayer player){
        for(int i = 0; i < player.inventory.getSizeInventory(); i++){
            if(StackUtil.isValid(player.inventory.getStackInSlot(i)) && player.inventory.getStackInSlot(i).getItem() instanceof ItemWingsOfTheBats){
                return player.inventory.getStackInSlot(i);
            }
        }
        return StackUtil.getNull();
    }

    @SubscribeEvent
    public void onLogOutEvent(PlayerEvent.PlayerLoggedOutEvent event){
        //Remove Player from Wings' Fly Permission List
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, true);
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, false);
    }

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event){
        if(event.getEntityLiving().worldObj != null && !event.getEntityLiving().worldObj.isRemote && event.getSource().getEntity() instanceof EntityPlayer){
            //Drop Wings from Bats
            if(ConfigBoolValues.DO_BAT_DROPS.isEnabled() && event.getEntityLiving() instanceof EntityBat){
                if(event.getEntityLiving().worldObj.rand.nextInt(15) <= event.getLootingLevel()*2){
                    event.getDrops().add(new EntityItem(event.getEntityLiving().worldObj, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(InitItems.itemMisc, event.getEntityLiving().worldObj.rand.nextInt(2+event.getLootingLevel())+1, TheMiscItems.BAT_WING.ordinal())));
                }
            }
        }
    }

    @SubscribeEvent
    public void livingUpdateEvent(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            boolean wingsEquipped = StackUtil.isValid(ItemWingsOfTheBats.getWingItem(player));

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

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
