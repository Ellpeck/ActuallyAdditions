/*
 * This file ("LogoutEvent.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.items.ItemWingsOfTheBats;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketServerToClient;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerConnectionEvents{

    @SubscribeEvent
    public void onLogOutEvent(PlayerEvent.PlayerLoggedOutEvent event){
        //Remove Player from Wings' Fly Permission List
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, true);
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, false);
    }

    @SubscribeEvent
    public void onLogInEvent(PlayerEvent.PlayerLoggedInEvent event){
        if(!event.player.worldObj.isRemote && event.player instanceof EntityPlayerMP){
            NBTTagCompound data = PlayerData.getDataFromPlayer(event.player);
            if(!data.hasNoTags()){
                PacketHandler.theNetwork.sendTo(new PacketServerToClient(data, PacketHandler.PLAYER_DATA_TO_CLIENT_HANDLER), (EntityPlayerMP)event.player);
                ModUtil.LOGGER.info("Sending Player Data to player "+event.player.getName()+"!");
            }
        }
    }

}
