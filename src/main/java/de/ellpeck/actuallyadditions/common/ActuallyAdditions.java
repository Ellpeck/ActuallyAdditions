package de.ellpeck.actuallyadditions.common;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ActuallyAdditions.MOD_ID)
public class ActuallyAdditions {
    public static final String MOD_ID = "actuallyadditions";

    public ActuallyAdditions() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ActuallyBlocks.BLOCKS.register(eventBus);
        ActuallyItems.ITEMS.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
    }

    private void setup(FMLCommonSetupEvent event) {

    }

    private void clientSetup(FMLClientSetupEvent event) {

    }
}
