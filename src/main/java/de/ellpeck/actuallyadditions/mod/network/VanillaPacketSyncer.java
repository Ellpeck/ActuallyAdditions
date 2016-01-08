/*
 * This file ("VanillaPacketSyncer.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class VanillaPacketSyncer{

    public static void sendTileToNearbyPlayers(TileEntity tile){
        List allPlayers = tile.getWorld().playerEntities;
        for(Object player : allPlayers){
            if(player instanceof EntityPlayerMP){
                sendTileToPlayer(tile, (EntityPlayerMP)player, 64);
            }
        }
    }

    public static void sendTileToPlayer(TileEntity tile, EntityPlayerMP player, int maxDistance){
        if(player.getDistance(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ()) <= maxDistance){
            sendTileToPlayer(tile, player);
        }
    }

    public static void sendTileToPlayer(TileEntity tile, EntityPlayerMP player){
        player.playerNetServerHandler.sendPacket(tile.getDescriptionPacket());
    }
}
