package ellpeck.thingycraft;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

public class Util {

    public static final String[] gemTypes = {"Onyx", "AlmandineGarnet", "ChromeDiopside", "Jasper", "Sodalite", "Iolite", "Smithsonite", "Danburite", "Hematite", "Lepidolite", "Tourmaline", "Sphene", "ParaibaTourlamine", "Rhodochrosite", "Clinohumite", "Goshenite"};

    public static boolean isShiftPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static String shiftForInfo(){
        return (EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.shiftForInfo.desc"));
    }

}
