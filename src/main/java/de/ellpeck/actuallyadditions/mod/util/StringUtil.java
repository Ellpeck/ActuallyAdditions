/*
 * This file ("StringUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.translation.I18n;
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

    @SideOnly(Side.CLIENT)
    public static void renderScaledAsciiString(FontRenderer font, String text, int x, int y, int color, boolean shadow, float scale){
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        boolean oldUnicode = font.getUnicodeFlag();
        font.setUnicodeFlag(false);

        font.drawString(text, x/scale, y/scale, color, shadow);

        font.setUnicodeFlag(oldUnicode);
        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
    public static void renderSplitScaledAsciiString(FontRenderer font, String text, int x, int y, int color, boolean shadow, float scale, int length){
        List<String> lines = font.listFormattedStringToWidth(text, (int)(length/scale));
        for(int i = 0; i < lines.size(); i++){
            renderScaledAsciiString(font, lines.get(i), x, y+(i*(int)(font.FONT_HEIGHT*scale+3)), color, shadow, scale);
        }
    }
}
