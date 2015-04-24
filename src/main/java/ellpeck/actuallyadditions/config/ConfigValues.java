package ellpeck.actuallyadditions.config;

import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.config.values.ConfigFloatValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import net.minecraftforge.common.config.Configuration;

public class ConfigValues{

    public static ConfigCrafting[] craftingConfig = ConfigCrafting.values();
    public static boolean[] craftingValues = new boolean[craftingConfig.length];

    public static ConfigIntValues[] intConfig = ConfigIntValues.values();
    public static int[] intValues = new int[intConfig.length];

    public static ConfigFloatValues[] floatConfig = ConfigFloatValues.values();
    public static float[] floatValues = new float[floatConfig.length];

    public static ConfigBoolValues[] boolConfig = ConfigBoolValues.values();
    public static boolean[] boolValues = new boolean[boolConfig.length];

    public static void defineConfigValues(Configuration config){

        for(int i = 0; i < craftingValues.length; i++){
            ConfigCrafting currConf = craftingConfig[i];
            craftingValues[i] = config.getBoolean(currConf.name, currConf.category, currConf.defaultValue, "If the Crafting Recipe for the " + currConf.name + " is Enabled");
        }

        for(int i = 0; i < intValues.length; i++){
            ConfigIntValues currConf = intConfig[i];
            intValues[i] = config.getInt(currConf.name, currConf.category, currConf.defaultValue, currConf.min, currConf.max, currConf.desc);
        }

        for(int i = 0; i < floatValues.length; i++){
            ConfigFloatValues currConf = floatConfig[i];
            floatValues[i] = config.getFloat(currConf.name, currConf.category, currConf.defaultValue, currConf.min, currConf.max, currConf.desc);
        }

        for(int i = 0; i < boolValues.length; i++){
            ConfigBoolValues currConf = boolConfig[i];
            boolValues[i] = config.getBoolean(currConf.name, currConf.category, currConf.defaultValue, currConf.desc);
        }
    }
}
