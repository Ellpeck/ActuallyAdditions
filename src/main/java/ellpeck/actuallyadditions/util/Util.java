package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

@SuppressWarnings("unused")
public class Util{

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;

    public static void logInfo(String text){
        ModUtil.AA_LOGGER.log(Level.INFO, text);
    }

    public static void registerEvent(Object o){
        FMLCommonHandler.instance().bus().register(o);
        MinecraftForge.EVENT_BUS.register(o);
    }
}