/*
 * This file ("WorldLoadingEvents.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.misc.WorldData;
import de.ellpeck.actuallyadditions.mod.util.FakePlayerUtil;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldLoadingEvents{

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event){
        WorldData.makeDirty();
    }

    @SubscribeEvent
    public void onUnload(WorldEvent.Unload event){
        WorldData.makeDirty();
        FakePlayerUtil.unloadFakePlayer();
    }

    @SubscribeEvent
    public void onSave(WorldEvent.Save event){
        WorldData.makeDirty();
    }
}
