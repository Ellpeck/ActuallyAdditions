package ellpeck.thingycraft;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

public class Util {

    public static final String[] gemTypes = {"Malachite", "Agate", "MaliGarnet", "MawSitSit", "Melanite", "Moldavite", "Amazonite", "Amber", "Amethyst", "Nuummite", "Apatite", "Onyx", "Orthoclase", "Bloodstone", "Peridot", "ChromeDiopside"};

    public static boolean isShiftPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static String shiftForInfo(){
        return (EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.shiftForInfo.desc"));
    }

}
