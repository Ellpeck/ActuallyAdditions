/*
 * This file ("StringUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public final class StringUtil{

    public static final int DECIMAL_COLOR_WHITE = 16777215;
    public static final int DECIMAL_COLOR_GRAY_TEXT = 4210752;

    public static final String BUGGED_ITEM_NAME = ModUtil.MOD_ID+".lolWutHowUDoDis";

    /**
     * Localizes a given String via StatCollector
     */
    public static String localize(String text){
        return I18n.translateToLocal(text);
    }

    /**
     * Localizes a given formatted String with the given Replacements
     */
    public static String localizeFormatted(String text, Object... replace){
        return I18n.translateToLocalFormatted(text, replace);
    }

    @SideOnly(Side.CLIENT)
    public static void drawSplitString(FontRenderer renderer, String strg, int x, int y, int width, int color, boolean shadow){
        List list = renderer.listFormattedStringToWidth(strg, width);
        for(int i = 0; i < list.size(); i++){
            String s1 = (String)list.get(i);
            renderer.drawString(s1, x, y+(i*renderer.FONT_HEIGHT), color, shadow);
        }
    }

    public static String getFluidInfo(FluidTank tank){
        return "";
    }
}
