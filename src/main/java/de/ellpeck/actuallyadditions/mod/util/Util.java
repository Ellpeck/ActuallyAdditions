/*
 * This file ("Util.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.oredict.OreDictionary;

public final class Util {

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;
    public static final int BUCKET = Fluid.BUCKET_VOLUME;

    public static final IRarity CRYSTAL_RED_RARITY = addRarity("crystalRed", TextFormatting.DARK_RED, ActuallyAdditions.NAME + " Red Crystal");
    public static final IRarity CRYSTAL_BLUE_RARITY = addRarity("crystalBlue", TextFormatting.DARK_BLUE, ActuallyAdditions.NAME + " Blue Crystal");
    public static final IRarity CRYSTAL_LIGHT_BLUE_RARITY = addRarity("crystalLightBlue", TextFormatting.BLUE, ActuallyAdditions.NAME + " Light Blue Crystal");
    public static final IRarity CRYSTAL_BLACK_RARITY = addRarity("crystalBlack", TextFormatting.DARK_GRAY, ActuallyAdditions.NAME + " Black Crystal");
    public static final IRarity CRYSTAL_GREEN_RARITY = addRarity("crystalGreen", TextFormatting.DARK_GREEN, ActuallyAdditions.NAME + " Green Crystal");
    public static final IRarity CRYSTAL_WHITE_RARITY = addRarity("crystalWhite", TextFormatting.GRAY, ActuallyAdditions.NAME + " White Crystal");

    public static final IRarity FALLBACK_RARITY = addRarity("fallback", TextFormatting.STRIKETHROUGH, ActuallyAdditions.NAME + " Fallback");

    private static IRarity addRarity(String name, TextFormatting color, String displayName) {
        return new Rarity(color, displayName);
    }

    public static boolean isDevVersion() {
        return ActuallyAdditions.VERSION.equals("@VERSION@");
    }

    public static boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
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