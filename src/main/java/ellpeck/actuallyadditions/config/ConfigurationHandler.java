package ellpeck.actuallyadditions.config;

import ellpeck.actuallyadditions.util.Util;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler{

    public static final String CATEGORY_FOOD_CRAFTING = "food crafting";
    public static final String CATEGORY_MISC_CRAFTING = "misc crafting";
    public static final String CATEGORY_BLOCKS_CRAFTING = "block crafting";
    public static final String CATEGORY_ITEMS_CRAFTING = "items crafting";
    public static final String CATEGORY_TOOL_VALUES = "tool values";
    public static final String CATEGORY_MACHINE_VALUES = "machine values";
    public static final String CATEGORY_MOB_DROPS = "mob drops";
    public static final String CATEGORY_WORLD_GEN = "world gen";


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