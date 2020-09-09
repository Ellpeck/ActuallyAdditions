package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;

public final class Util {

    @Deprecated // canitzp: Wildcards and Oredict not supported anymore -> Tags
    public static final int WILDCARD = -1;//OreDictionary.WILDCARD_VALUE;
    public static final int BUCKET = 1000;

    public static final Rarity CRYSTAL_RED_RARITY = addRarity("crystalRed", TextFormatting.DARK_RED, ActuallyAdditions.NAME + " Red Crystal");
    public static final Rarity CRYSTAL_BLUE_RARITY = addRarity("crystalBlue", TextFormatting.DARK_BLUE, ActuallyAdditions.NAME + " Blue Crystal");
    public static final Rarity CRYSTAL_LIGHT_BLUE_RARITY = addRarity("crystalLightBlue", TextFormatting.BLUE, ActuallyAdditions.NAME + " Light Blue Crystal");
    public static final Rarity CRYSTAL_BLACK_RARITY = addRarity("crystalBlack", TextFormatting.DARK_GRAY, ActuallyAdditions.NAME + " Black Crystal");
    public static final Rarity CRYSTAL_GREEN_RARITY = addRarity("crystalGreen", TextFormatting.DARK_GREEN, ActuallyAdditions.NAME + " Green Crystal");
    public static final Rarity CRYSTAL_WHITE_RARITY = addRarity("crystalWhite", TextFormatting.GRAY, ActuallyAdditions.NAME + " White Crystal");

    public static final Rarity FALLBACK_RARITY = addRarity("fallback", TextFormatting.STRIKETHROUGH, ActuallyAdditions.NAME + " Fallback");

    private static Rarity addRarity(String name, TextFormatting color, String displayName) {
        return Rarity.create(displayName, color);
    }

    public static boolean isDevVersion() {
        return ActuallyAdditions.VERSION.equals("@VERSION@");
    }

    @Deprecated // canitzp: should not be used and removed asap
    public static boolean isClient() {
        return false;//FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    private static String[] splitVersion() {
        return ActuallyAdditions.VERSION.split("-");
    }

    public static String getMcVersion() {
        return splitVersion()[0];
    }

    public static String getMajorModVersion() {
        return splitVersion()[1].substring(1);
    }
}