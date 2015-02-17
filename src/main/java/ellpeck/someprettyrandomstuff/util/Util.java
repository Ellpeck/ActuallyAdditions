package ellpeck.someprettyrandomstuff.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@SuppressWarnings("unused")
public class Util{

    public static final String VERSION = "1.7.10-0.0.1";

    public static final String MOD_ID = "SomePrettyRandomStuff";
    public static final String NAME = "Some Pretty Random Stuff";
    public static final String MOD_ID_LOWER = MOD_ID.toLowerCase();

    public static final Logger SPRS_LOGGER = LogManager.getLogger(MOD_ID);

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;

    public static final String BLACK = (char)167 + "0";
    public static final String BLUE = (char)167 + "1";
    public static final String GREEN = (char)167 + "2";
    public static final String TEAL = (char)167 + "3";
    public static final String RED = (char)167 + "4";
    public static final String PURPLE = (char)167 + "5";
    public static final String ORANGE = (char)167 + "6";
    public static final String LIGHT_GRAY = (char)167 + "7";
    public static final String GRAY = (char)167 + "8";
    public static final String LIGHT_BLUE = (char)167 + "9";
    public static final String BRIGHT_GREEN = (char)167 + "a";
    public static final String BRIGHT_BLUE = (char)167 + "b";
    public static final String LIGHT_RED = (char)167 + "c";
    public static final String PINK = (char)167 + "d";
    public static final String YELLOW = (char)167 + "e";
    public static final String WHITE = (char)167 + "f";
    public static final String BOLD = (char)167 + "l";
    public static final String UNDERLINE = (char)167 + "n";
    public static final String ITALIC = (char)167 + "o";

    public static boolean isShiftPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    @SuppressWarnings("unused")
    public static boolean isControlPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }

    public static String shiftForInfo(){
        return GREEN + ITALIC + StatCollector.translateToLocal("tooltip.shiftForInfo.desc");
    }

    public static String addStandardInformation(Item item){
        if(isShiftPressed()) return StatCollector.translateToLocal("tooltip." + getSubbedUnlocalized(item) + ".desc");
        else return shiftForInfo();
    }

    public static void logInfo(String text){
        SPRS_LOGGER.log(Level.INFO, text);
    }

    public static boolean isClientSide(){
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }

    public static String getSubbedUnlocalized(Item item){
        return item.getUnlocalizedName().substring(5);
    }

    public static String getSubbedUnlocalized(Block block){
        return block.getUnlocalizedName().substring(5);
    }

    public static void registerItems(Item[] items){
        for(Item item : items){
            GameRegistry.registerItem(item, getSubbedUnlocalized(item));
        }
    }

    public static ResourceLocation getGuiLocation(String file){
        return new ResourceLocation(MOD_ID_LOWER, "textures/gui/" + file + ".png");
    }
}