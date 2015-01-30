package ellpeck.someprettyrandomstuff.util;

import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

@SuppressWarnings("unused")
public class Util{

    public static final String MOD_ID = "someprettyrandomstuff";
    public static final String NAME = "SomePrettyRandomStuff";
    public static final String VERSION = "1.7.10-0.0.1";

    public static boolean isShiftPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static boolean isControlPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }


    public static String shiftForInfo() {
        return (char)167+"2" + (char)167+"o" + StatCollector.translateToLocal("tooltip.shiftForInfo.desc");
    }

    public static String addStandardInformation(Item item){
        if(isShiftPressed()) return StatCollector.translateToLocal("tooltip." + item.getUnlocalizedName().substring(5) + ".desc");
        else return shiftForInfo();
    }
}
