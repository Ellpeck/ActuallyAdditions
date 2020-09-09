package de.ellpeck.actuallyadditions.config;

import java.io.File;

import de.ellpeck.actuallyadditions.ActuallyAdditions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {

    public static Configuration config;

    public ConfigurationHandler(File configFile) {
        ActuallyAdditions.LOGGER.info("Grabbing Configurations...");

        MinecraftForge.EVENT_BUS.register(this);

        config = new Configuration(configFile);
        config.load();

        redefineConfigs();
    }

    public static void redefineConfigs() {
        ConfigValues.defineConfigValues(config);

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(ActuallyAdditions.MODID)) {
            redefineConfigs();
        }
    }
}