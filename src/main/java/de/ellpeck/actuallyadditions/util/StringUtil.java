/*
 * This file ("StringUtil.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidTank;

import java.util.List;
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

    public static void drawSplitString(FontRenderer renderer, String strg, int x, int y, int width, int color, boolean shadow){
        List list = renderer.listFormattedStringToWidth(strg, width);
        for(int i = 0; i < list.size(); i++){
            String s1 = (String)list.get(i);
            renderer.drawString(s1, x, y+(i*renderer.FONT_HEIGHT), color, shadow);
        }
    }

    public static String getFluidInfo(FluidTank tank){
        return tank.getFluid() == null || tank.getFluid().getFluid() == null ? "0/"+tank.getCapacity()+" mB" : tank.getFluidAmount()+"/"+tank.getCapacity()+" mB "+tank.getFluid().getLocalizedName();
    }
}
