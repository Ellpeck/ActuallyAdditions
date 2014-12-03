package ellpeck.thingycraft;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Util {

    public static ArrayList<GemType> gemList = new ArrayList<GemType>();

    public static final GemType fluidOnyx = new GemType(0, "Onyx", true);
    public static final GemType fluidAlmandineGarnet = new GemType(1, "AlmandineGarnet", true);
    public static final GemType fluidChromeDiopside = new GemType(2, "ChromeDiopside", true);
    public static final GemType fluidJasper = new GemType(3, "Jasper", true);
    public static final GemType fluidSodalite = new GemType(4, "Sodalite", true);
    public static final GemType fluidIolite = new GemType(5, "Iolite", true);
    public static final GemType fluidSmithsonite = new GemType(6, "Smithsonite", true);
    public static final GemType fluidDanburite = new GemType(7, "Danburite", true);
    public static final GemType fluidHematite = new GemType(8, "Hematite", true);
    public static final GemType fluidLepidolite = new GemType(9, "Lepidolite", true);
    public static final GemType fluidTourmaline = new GemType(10, "Tourmaline", true);
    public static final GemType fluidSphene = new GemType(11, "Sphene", true);
    public static final GemType fluidParaibaTourlamine = new GemType(12, "ParaibaTourlamine", true);
    public static final GemType fluidRhodochrosite = new GemType(13, "Rhodochrosite", true);
    public static final GemType fluidClinohumite = new GemType(14, "Clinohumite", true);
    public static final GemType fluidGoshenite = new GemType(15, "Goshenite", true);
    public static final GemType fluidWater = new GemType(16, "Water", false);
    public static final GemType fluidNone = new GemType(17, "None", false);

    public static boolean isShiftPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static String shiftForInfo(){
        return (EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.shiftForInfo.desc"));
    }

}
