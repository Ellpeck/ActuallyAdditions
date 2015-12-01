/*
 * This file ("VanillaPacketSyncer.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class VanillaPacketSyncer{

    public static void sendTileToNearbyPlayers(TileEntity tile){
        List allPlayers = tile.getWorldObj().playerEntities;
        for(Object player : allPlayers){
            if(player instanceof EntityPlayerMP){
                sendTileToPlayer(tile, (EntityPlayerMP)player, 64);
            }
        }
    }

    public static void sendTileToPlayer(TileEntity tile, EntityPlayerMP player, int maxDistance){
        if(player.getDistance(tile.xCoord, tile.yCoord, tile.zCoord) <= maxDistance){
            sendTileToPlayer(tile, player);
        }
    }

    public static void sendTileToPlayer(TileEntity tile, EntityPlayerMP player){
        player.playerNetServerHandler.sendPacket(tile.getDescriptionPacket());
    }
}
