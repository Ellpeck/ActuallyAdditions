package ellpeck.actuallyadditions.config;

import ellpeck.actuallyadditions.util.Util;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler{

    public static void init(File configFile){
        Util.logInfo("Grabbing Configurations...");
        Configuration config = new Configuration(configFile);

        try{
            config.load();
            ConfigValues.defineConfigValues(config);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(config.hasChanged()){
                config.save();
            }
        }

    }
}