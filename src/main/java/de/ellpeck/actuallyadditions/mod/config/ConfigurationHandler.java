/*
 * This file ("ConfigurationHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler{

    public static Configuration config;

    public ConfigurationHandler(File configFile){
        ModUtil.LOGGER.info("Grabbing Configurations...");

        MinecraftForge.EVENT_BUS.register(this);

        config = new Configuration(configFile);
        config.load();

        redefineConfigs();
    }

    private static void redefineConfigs(){
        ConfigValues.defineConfigValues(config);

        if(config.hasChanged()){
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if(event.getModID().equalsIgnoreCase(ModUtil.MOD_ID)){
            redefineConfigs();
        }
    }
}