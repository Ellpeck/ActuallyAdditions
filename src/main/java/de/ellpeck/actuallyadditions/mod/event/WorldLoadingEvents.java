/*
 * This file ("WorldLoadingEvents.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.util.FakePlayerUtil;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldLoadingEvents{

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event){
        WorldData.load(event.getWorld());
    }

    @SubscribeEvent
    public void onUnload(WorldEvent.Unload event){
        WorldData.unload(event.getWorld());
        FakePlayerUtil.unloadFakePlayer();
    }

    @SubscribeEvent
    public void onSave(WorldEvent.Save event){
        WorldData.save(event.getWorld(), true);
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkDataEvent.Save event){
        WorldData.save(event.getWorld(), false);
    }

}
