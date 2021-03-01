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

import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.LanguageMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

public final class StringUtil {

    public static final int DECIMAL_COLOR_WHITE = 16777215;
    public static final int DECIMAL_COLOR_GRAY_TEXT = 4210752;

    public static final String BUGGED_ITEM_NAME = ActuallyAdditions.MODID + ".lolWutHowUDoDis";

    /**
     * Localizes a given String
     */
    @OnlyIn(Dist.CLIENT)
    public static String localize(String text) {
        return I18n.format(text);
    }

    /**
     * Localizes a given formatted String with the given Replacements
     */
    @OnlyIn(Dist.CLIENT)
    public static String localizeFormatted(String text, Object... replace) {
        return I18n.format(text, replace);
    }

    @SuppressWarnings("deprecation")
    //TODO: delete this shit and move ItemPotionRing's getItemStackDisplayName into getUnlocalizedName
    public static String localizeIllegallyOnTheServerDontUseMePls(String langKey) {
        return net.minecraft.util.text.translation.I18n.translateToLocal(langKey);
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawSplitString(FontRenderer renderer, String strg, int x, int y, int width, int color, boolean shadow) {
        List<String> list = renderer.listFormattedStringToWidth(strg, width);
        for (int i = 0; i < list.size(); i++) {
            String s1 = list.get(i);
            renderer.drawString(s1, x, y + i * renderer.FONT_HEIGHT, color, shadow);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderScaledAsciiString(FontRenderer font, String text, float x, float y, int color, boolean shadow, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        boolean oldUnicode = font.getUnicodeFlag();
        font.setUnicodeFlag(false);

        font.drawString(text, x / scale, y / scale, color, shadow);

        font.setUnicodeFlag(oldUnicode);
        GlStateManager.popMatrix();
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderSplitScaledAsciiString(FontRenderer font, String text, int x, int y, int color, boolean shadow, float scale, int length) {
        List<String> lines = font.listFormattedStringToWidth(text, (int) (length / scale));
        for (int i = 0; i < lines.size(); i++) {
            renderScaledAsciiString(font, lines.get(i), x, y + i * (int) (font.FONT_HEIGHT * scale + 3), color, shadow, scale);
        }
    }

    //TODO: Remove
    static LanguageMap cancerino;

    static void setupLangMap() {
        try {
            Method m = LanguageMap.class.getDeclaredMethod("inject", LanguageMap.class, InputStream.class);
            m.setAccessible(true);
            m.invoke(null, cancerino = new LanguageMap(), ActuallyAdditions.class.getResourceAsStream("/assets/actuallyadditions/lang/en_US.lang"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Actually Additions failed to access LanguageMap.inject.  Report this!");
        }
    }

    public static String badTranslate(String someUnlocAAItemName) {
        if (cancerino == null) {
            cancerino = new LanguageMap();
            setupLangMap();
        }
        return cancerino.translateKey("item.actuallyadditions." + someUnlocAAItemName + ".name");
    }
}
