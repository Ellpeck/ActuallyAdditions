/*
 * This file ("PickupEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ellpeck.actuallyadditions.achievement.InitAchievements;

public class PickupEvent{

    @SubscribeEvent
    public void onPickupEvent(PlayerEvent.ItemPickupEvent event){
        CraftEvent.checkAchievements(event.pickedUp.getEntityItem(), event.player, InitAchievements.PICKUP_ACH);
    }
}
