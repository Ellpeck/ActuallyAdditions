/*
 * This file ("WorldLoadingEvents.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.misc.LaserRelayConnectionHandler;
import net.minecraftforge.event.world.WorldEvent;

public class WorldLoadingEvents{

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event){
        if(LaserRelayConnectionHandler.getInstance() == null){
            LaserRelayConnectionHandler.setInstance(new LaserRelayConnectionHandler());
        }
    }

    @SubscribeEvent
    public void onUnload(WorldEvent.Unload event){
        //Clear Data so that it won't be carried over to other worlds
        LaserRelayConnectionHandler.getInstance().networks.clear();
    }
}
