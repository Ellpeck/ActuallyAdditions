package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ellpeck.actuallyadditions.items.ItemWingsOfTheBats;

public class LogoutEvent{

    @SubscribeEvent
    public void onLogOutEvent(PlayerEvent.PlayerLoggedOutEvent event){
        //Remove Player from Wings' Fly Permission List
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, true);
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, false);
    }

}
