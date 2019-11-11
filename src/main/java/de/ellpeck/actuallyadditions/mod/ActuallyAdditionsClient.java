package de.ellpeck.actuallyadditions.mod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = ActuallyAdditions.MODID, bus = Bus.MOD)
public class ActuallyAdditionsClient {

	@SubscribeEvent
	public static void setup(FMLClientSetupEvent e) {

	}

}
