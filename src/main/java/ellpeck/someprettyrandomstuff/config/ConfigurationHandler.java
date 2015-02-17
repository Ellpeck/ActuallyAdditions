package ellpeck.someprettyrandomstuff.config;

import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler{

    public static final String CATEGORY_FOOD_CRAFTING = "food crafting";
    public static final String CATEGORY_MISC_CRAFTING = "misc crafting";
    public static final String CATEGORY_COMPOST_VALUES = "compost values";
    public static final String CATEGORY_BLOCKS_CRAFTING = "block crafting";
    public static final String CATEGORY_ITEMS_CRAFTING = "items crafting";
    public static final String CATEGORY_ITEM_DAMAGE_VALUES = "item damage values";
    public static final String CATEGORY_TOOL_VALUES = "tool values";
    public static final String CATEGORY_FEEDER_VALUES = "feeder values";

    public static Configuration config;

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
            config.save();
        }

    }
}
