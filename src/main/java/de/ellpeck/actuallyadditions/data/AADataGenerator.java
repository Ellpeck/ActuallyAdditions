package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AADataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

    }
}
