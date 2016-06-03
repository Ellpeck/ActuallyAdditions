/*
 * This file ("PlayerObtainEvents.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.achievement.InitAchievements;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.misc.WorldData;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.playerdata.PlayerServerData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Locale;

public class PlayerObtainEvents{

    public static void checkAchievements(ItemStack gotten, EntityPlayer player, InitAchievements.Type type){
        for(TheAchievements ach : TheAchievements.values()){
            if(ach.type == type){
                if(gotten != null && ach.ach.theItemStack != null && gotten.getItem() == ach.ach.theItemStack.getItem()){
                    if(gotten.getItemDamage() == ach.ach.theItemStack.getItemDamage()){
                        player.addStat(ach.ach, 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onCraftedEvent(PlayerEvent.ItemCraftedEvent event){
        checkAchievements(event.crafting, event.player, InitAchievements.Type.CRAFTING);

        if(ConfigBoolValues.GIVE_BOOKLET_ON_FIRST_CRAFT.isEnabled()){
            if(!event.player.worldObj.isRemote && event.crafting != null && event.crafting.getItem() != null && event.crafting.getItem() != InitItems.itemBooklet){

                String name = event.crafting.getItem().getRegistryName().toString();
                if(name != null && name.toLowerCase(Locale.ROOT).contains(ModUtil.MOD_ID)){
                    NBTTagCompound compound = PlayerServerData.getDataFromPlayer(event.player);
                    if(compound != null && !compound.getBoolean("BookGottenAlready")){
                        compound.setBoolean("BookGottenAlready", true);
                        WorldData.get(event.player.worldObj).markDirty();

                        EntityItem entityItem = new EntityItem(event.player.worldObj, event.player.posX, event.player.posY, event.player.posZ, new ItemStack(InitItems.itemBooklet));
                        entityItem.setPickupDelay(0);
                        event.player.worldObj.spawnEntityInWorld(entityItem);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSmeltedEvent(PlayerEvent.ItemSmeltedEvent event){
        checkAchievements(event.smelting, event.player, InitAchievements.Type.SMELTING);
    }

    @SubscribeEvent
    public void onPickupEvent(PlayerEvent.ItemPickupEvent event){
        checkAchievements(event.pickedUp.getEntityItem(), event.player, InitAchievements.Type.PICK_UP);
    }
}
