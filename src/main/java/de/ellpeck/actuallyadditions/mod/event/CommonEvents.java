/*
 * This file ("CommonEvents.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.achievement.InitAchievements;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.misc.DungeonLoot;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Locale;

public class CommonEvents{

    public CommonEvents(){
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new DungeonLoot());
    }

    public static void checkAchievements(ItemStack gotten, EntityPlayer player, InitAchievements.Type type){
        if(gotten != null && player != null){
            for(TheAchievements ach : TheAchievements.values()){
                if(ach.type == type){
                    if(ItemUtil.contains(ach.itemsToBeGotten, gotten, true)){
                        ach.get(player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDropEvent(LivingDropsEvent event){
        if(event.getEntityLiving().world != null && !event.getEntityLiving().world.isRemote && event.getSource().getEntity() instanceof EntityPlayer){
            //Drop Cobwebs from Spiders
            if(ConfigBoolValues.DO_SPIDER_DROPS.isEnabled() && event.getEntityLiving() instanceof EntitySpider){
                if(event.getEntityLiving().world.rand.nextInt(20) <= event.getLootingLevel()*2){
                    event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(Blocks.WEB, event.getEntityLiving().world.rand.nextInt(2+event.getLootingLevel())+1)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLogInEvent(PlayerEvent.PlayerLoggedInEvent event){
        if(!event.player.world.isRemote && event.player instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.player;
            PacketHandlerHelper.syncPlayerData(player, true);
            ModUtil.LOGGER.info("Sending Player Data to player "+player.getName()+" with UUID "+player.getUniqueID()+".");
        }
    }

    @SubscribeEvent
    public void onCraftedEvent(PlayerEvent.ItemCraftedEvent event){
        checkAchievements(event.crafting, event.player, InitAchievements.Type.CRAFTING);

        if(ConfigBoolValues.GIVE_BOOKLET_ON_FIRST_CRAFT.isEnabled()){
            if(!event.player.world.isRemote && StackUtil.isValid(event.crafting) && event.crafting.getItem() != InitItems.itemBooklet){

                String name = event.crafting.getItem().getRegistryName().toString();
                if(name != null && name.toLowerCase(Locale.ROOT).contains(ModUtil.MOD_ID)){
                    PlayerData.PlayerSave save = PlayerData.getDataFromPlayer(event.player);
                    if(save != null && !save.bookGottenAlready){
                        save.bookGottenAlready = true;
                        WorldData.get(event.player.getEntityWorld()).markDirty();

                        EntityItem entityItem = new EntityItem(event.player.world, event.player.posX, event.player.posY, event.player.posZ, new ItemStack(InitItems.itemBooklet));
                        entityItem.setPickupDelay(0);
                        event.player.world.spawnEntity(entityItem);
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
    public void onPickupEvent(EntityItemPickupEvent event){
        checkAchievements(event.getItem().getEntityItem(), event.getEntityPlayer(), InitAchievements.Type.PICK_UP);
    }

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event){
        WorldData.loadLegacy(event.getWorld());
    }
}
