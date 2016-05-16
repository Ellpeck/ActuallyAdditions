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

import de.ellpeck.actuallyadditions.mod.items.ItemWingsOfTheBats;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class LogoutEvent{

    @SubscribeEvent
    public void onLogOutEvent(PlayerEvent.PlayerLoggedOutEvent event){
        //Remove Player from Wings' Fly Permission List
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, true);
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, false);
    }

}
