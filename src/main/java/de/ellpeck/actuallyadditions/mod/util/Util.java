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

import net.minecraft.item.EnumRarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Locale;

public final class Util{

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;
    public static final int BUCKET = Fluid.BUCKET_VOLUME;

    public static final EnumRarity CRYSTAL_RED_RARITY = addRarity("crystalRed", TextFormatting.DARK_RED, ModUtil.NAME+" Red Crystal");
    public static final EnumRarity CRYSTAL_BLUE_RARITY = addRarity("crystalBlue", TextFormatting.DARK_BLUE, ModUtil.NAME+" Blue Crystal");
    public static final EnumRarity CRYSTAL_LIGHT_BLUE_RARITY = addRarity("crystalLightBlue", TextFormatting.BLUE, ModUtil.NAME+" Light Blue Crystal");
    public static final EnumRarity CRYSTAL_BLACK_RARITY = addRarity("crystalBlack", TextFormatting.DARK_GRAY, ModUtil.NAME+" Black Crystal");
    public static final EnumRarity CRYSTAL_GREEN_RARITY = addRarity("crystalGreen", TextFormatting.DARK_GREEN, ModUtil.NAME+" Green Crystal");
    public static final EnumRarity CRYSTAL_WHITE_RARITY = addRarity("crystalWhite", TextFormatting.GRAY, ModUtil.NAME+" White Crystal");

    public static final EnumRarity FALLBACK_RARITY = addRarity("fallback", TextFormatting.STRIKETHROUGH, ModUtil.NAME+" Fallback");

    private static EnumRarity addRarity(String name, TextFormatting color, String displayName){
        return EnumHelper.addRarity((ModUtil.MOD_ID+"_"+name).toUpperCase(Locale.ROOT), color, displayName);
    }

    public static boolean isDevVersion(){
        return ModUtil.VERSION.equals("@VERSION@");
    }

    private static String[] splitVersion(){
        return ModUtil.VERSION.split("-");
    }

    public static String getMcVersion(){
        return splitVersion()[0];
    }

    public static String getMajorModVersion(){
        return splitVersion()[1].substring(1);
    }
}