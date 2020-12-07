package de.ellpeck.actuallyadditions.common;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.common.config.Config;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
            return new ItemStack(ActuallyItems.BOOKLET.get());
        }
    };

    public ActuallyAdditions() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

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
        RenderTypeLookup.setRenderLayer(ActuallyBlocks.CRYSTAL_CLUSTER_VOID.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ActuallyBlocks.CRYSTAL_CLUSTER_ENORI.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ActuallyBlocks.CRYSTAL_CLUSTER_RESTONIA.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ActuallyBlocks.CRYSTAL_CLUSTER_PALIS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ActuallyBlocks.CRYSTAL_CLUSTER_DIAMATINE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ActuallyBlocks.CRYSTAL_CLUSTER_EMERADIC.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ActuallyBlocks.GREENHOUSE_GLASS.get(), RenderType.getCutout());
    }
}
