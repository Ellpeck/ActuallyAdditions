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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
    public void onLogInEvent(EntityJoinWorldEvent event){
        if(!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
            PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);
            if(!data.theCompound.hasNoTags()){
                PacketHandler.theNetwork.sendTo(new PacketServerToClient(data.theCompound, PacketHandler.PLAYER_DATA_TO_CLIENT_HANDLER), player);
            }
        }
    }

}
