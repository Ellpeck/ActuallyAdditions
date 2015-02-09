package ellpeck.someprettyrandomstuff.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class Util{

    public static final String MOD_ID = "SomePrettyRandomStuff";
    public static final String NAME = "Some Pretty Random Stuff";
    public static final String VERSION = "1.8-0.0.1";

    public static final Logger SPRS_LOGGER = LogManager.getLogger(MOD_ID);

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;

    public static boolean isShiftPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    @SuppressWarnings("unused")
    public static boolean isControlPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }

    public static String shiftForInfo(){
        return (char)167+"2" + (char)167+"o" + StatCollector.translateToLocal("tooltip.shiftForInfo.desc");
    }

    public static String addStandardInformation(Item item){
        if(isShiftPressed()) return StatCollector.translateToLocal("tooltip." + item.getUnlocalizedName().substring(5) + ".desc");
        else return shiftForInfo();
    }

    public static void preInitIcons(Item item, IItemEnum[] theArray){
        String[] bakeryArray = new String[theArray.length];
        for (int j = 0; j < theArray.length; j++){
            bakeryArray[j] = Util.MOD_ID + ":" + item.getUnlocalizedName().substring(5) + theArray[j].getName();
        }
        ModelBakery.addVariantName(item, bakeryArray);
    }

    public static void initIcons(Item item, IItemEnum[] theArray){
        for(int j = 0; j < theArray.length; j++){
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, j, new ModelResourceLocation(Util.MOD_ID + ":" + item.getUnlocalizedName().substring(5) + theArray[j].getName(), "inventory"));
        }
    }

    public static void initIcons(Item item){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Util.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }

    public static void logInfo(String text){
        SPRS_LOGGER.log(Level.INFO, text);
    }

    public static boolean isClientSide(){
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }
}
