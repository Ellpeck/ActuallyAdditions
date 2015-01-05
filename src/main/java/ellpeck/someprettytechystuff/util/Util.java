package ellpeck.someprettytechystuff.util;

import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Util{

    public static final String MOD_ID = "someprettytechystuff";
    public static final String NAME = "Gemification";
    public static final String VERSION = "1.7.10-1.0.1";

    public static ArrayList<GemType> gemList = new ArrayList<GemType>();

    public static final GemType onyx = new GemType(0, "Onyx", true);
    public static final GemType almandineGarnet = new GemType(1, "AlmandineGarnet", true);
    public static final GemType chromeDiopside = new GemType(2, "ChromeDiopside", true);
    public static final GemType jasper = new GemType(3, "Jasper", true);
    public static final GemType sodalite = new GemType(4, "Sodalite", true);
    public static final GemType iolite = new GemType(5, "Iolite", true);
    public static final GemType smithsonite = new GemType(6, "Smithsonite", true);
    public static final GemType danburite = new GemType(7, "Danburite", true);
    public static final GemType hematite = new GemType(8, "Hematite", true);
    public static final GemType lepidolite = new GemType(9, "Lepidolite", true);
    public static final GemType tourmaline = new GemType(10, "Tourmaline", true);
    public static final GemType sphene = new GemType(11, "Sphene", true);
    public static final GemType paraibaTourlamine = new GemType(12, "ParaibaTourlamine", true);
    public static final GemType rhodochrosite = new GemType(13, "Rhodochrosite", true);
    public static final GemType clinohumite = new GemType(14, "Clinohumite", true);
    public static final GemType goshenite = new GemType(15, "Goshenite", true);
    public static final GemType fluidWater = new GemType(16, "Water", false);
    public static final GemType fluidNone = new GemType(17, "None", false);

    public static boolean isShiftPressed(){
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static String shiftForInfo() {
        return (char)167+"2" + (char)167+"o" + StatCollector.translateToLocal("tooltip.shiftForInfo.desc");
    }

    public static String addStandardInformation(Item item){
        if(isShiftPressed()) return StatCollector.translateToLocal("tooltip." + item.getUnlocalizedName().substring(5) + ".desc");
        else return shiftForInfo();
    }
}
