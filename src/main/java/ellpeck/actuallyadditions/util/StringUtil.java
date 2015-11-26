/*
 * This file ("StringUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import net.minecraft.util.StatCollector;

import java.util.Locale;
import java.util.Objects;

public class StringUtil{

    public static final int DECIMAL_COLOR_WHITE = 16777215;
    public static final int DECIMAL_COLOR_GRAY_TEXT = 4210752;

    public static final String BUGGED_ITEM_NAME = ModUtil.MOD_ID_LOWER+".lolWutHowUDoDis";

    /**
     * Localizes a given String via StatCollector
     */
    public static String localize(String text){
        return StatCollector.translateToLocal(text);
    }

    /**
     * Localizes a given formatted String with the given Replacements
     */
    public static String localizeFormatted(String text, Object... replace){
        return StatCollector.translateToLocalFormatted(text, replace);
    }

    public static boolean equalsToLowerCase(String one, String two){
        return Objects.equals(toLowerCase(one), toLowerCase(two));
    }

    public static String toLowerCase(String string){
        if(string == null){
            return null;
        }
        else{
            return string.toLowerCase(Locale.ROOT);
        }
    }
}
