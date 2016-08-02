/*
 * This file ("CommonEvents.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Locale;

public class CommonEvents{

    public CommonEvents(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void checkAchievements(ItemStack gotten, EntityPlayer player, InitAchievements.Type type){
        for(TheAchievements ach : TheAchievements.values()){
            if(ach.type == type){
                if(gotten != null && ach.chieve.theItemStack != null && gotten.getItem() == ach.chieve.theItemStack.getItem()){
                    if(gotten.getItemDamage() == ach.chieve.theItemStack.getItemDamage()){
                        player.addStat(ach.chieve, 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void livingDeathEvent(LivingDeathEvent event){
        if(event.getEntityLiving().worldObj != null && !event.getEntityLiving().worldObj.isRemote && event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);

            NBTTagList deaths = data.theCompound.getTagList("Deaths", 10);
            while(deaths.tagCount() >= 5){
                deaths.removeTag(0);
            }

            NBTTagCompound death = new NBTTagCompound();
            death.setDouble("X", player.posX);
            death.setDouble("Y", player.posY);
            death.setDouble("Z", player.posZ);
            deaths.appendTag(death);

            data.theCompound.setTag("Deaths", deaths);

            //player.addChatComponentMessage(new TextComponentTranslation("info."+ModUtil.MOD_ID+".deathRecorded"));
        }
    }

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event){
        if(event.getEntityLiving().worldObj != null && !event.getEntityLiving().worldObj.isRemote && event.getSource().getEntity() instanceof EntityPlayer){
            //Drop Cobwebs from Spiders
            if(ConfigBoolValues.DO_SPIDER_DROPS.isEnabled() && event.getEntityLiving() instanceof EntitySpider){
                if(Util.RANDOM.nextInt(20) <= event.getLootingLevel()*2){
                    event.getEntityLiving().entityDropItem(new ItemStack(Blocks.WEB, Util.RANDOM.nextInt(2+event.getLootingLevel())+1), 0);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLogInEvent(PlayerEvent.PlayerLoggedInEvent event){
        if(!event.player.worldObj.isRemote && event.player instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.player;
            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
            if(!data.theCompound.hasNoTags()){
                NBTTagCompound compound = new NBTTagCompound();
                compound.setUniqueId("UUID", player.getUniqueID());
                compound.setTag("Data", data.theCompound);
                compound.setBoolean("Log", true);
                PacketHandler.theNetwork.sendTo(new PacketServerToClient(compound, PacketHandler.PLAYER_DATA_TO_CLIENT_HANDLER), player);
                ModUtil.LOGGER.info("Sending Player Data to player "+player.getName()+" with UUID "+player.getUniqueID()+" with info "+data.theCompound+".");
            }
            else{
                ModUtil.LOGGER.info("Not sending Player Data to player "+player.getName()+" with UUID "+player.getUniqueID()+" because he doesn't have any.");
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
                    PlayerData.PlayerSave compound = PlayerData.getDataFromPlayer(event.player);
                    if(compound != null && !compound.theCompound.getBoolean("BookGottenAlready")){
                        compound.theCompound.setBoolean("BookGottenAlready", true);

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

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event){
        WorldData.load(event.getWorld());
    }

    @SubscribeEvent
    public void onUnload(WorldEvent.Unload event){
        WorldData.unload(event.getWorld());
    }

    @SubscribeEvent
    public void onSave(WorldEvent.Save event){
        WorldData.save(event.getWorld());
    }
}
