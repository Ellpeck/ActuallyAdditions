package ellpeck.actuallyadditions.config;

import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler{

    public static final String ISSUES_WARNING = " [THIS COULD CAUSE ISSUES, CHANGE AT YOUR OWN RISK!]";

    public static void init(File configFile){
        ModUtil.LOGGER.info("Grabbing Configurations...");
        Configuration config = new Configuration(configFile);

        try{
            config.load();
            ConfigValues.defineConfigValues(config);
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Loading the Config File failed!", e);
        }
        finally{
            if(config.hasChanged()){
                config.save();
            }
        }

    }
}