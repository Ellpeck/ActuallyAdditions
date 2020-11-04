package de.ellpeck.actuallyadditions.common;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ActuallyAdditions.MOD_ID)
public class ActuallyAdditions {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "actuallyadditions";

    public static final ItemGroup ACTUALLY_GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ActuallyBlocks.blockEmpowerer.get());
        }
    };

    public ActuallyAdditions() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ActuallyBlocks.BLOCKS.register(eventBus);
        ActuallyItems.ITEMS.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {

    }

    private void clientSetup(FMLClientSetupEvent event) {

    }
}
