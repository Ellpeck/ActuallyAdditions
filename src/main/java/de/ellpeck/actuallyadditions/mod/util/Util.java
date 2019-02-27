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

import java.util.Locale;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.oredict.OreDictionary;

public final class Util{

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;
    public static final int BUCKET = Fluid.BUCKET_VOLUME;

    public static final EnumRarity CRYSTAL_RED_RARITY = addRarity("crystalRed", TextFormatting.DARK_RED, ActuallyAdditions.NAME+" Red Crystal");
    public static final EnumRarity CRYSTAL_BLUE_RARITY = addRarity("crystalBlue", TextFormatting.DARK_BLUE, ActuallyAdditions.NAME+" Blue Crystal");
    public static final EnumRarity CRYSTAL_LIGHT_BLUE_RARITY = addRarity("crystalLightBlue", TextFormatting.BLUE, ActuallyAdditions.NAME+" Light Blue Crystal");
    public static final EnumRarity CRYSTAL_BLACK_RARITY = addRarity("crystalBlack", TextFormatting.DARK_GRAY, ActuallyAdditions.NAME+" Black Crystal");
    public static final EnumRarity CRYSTAL_GREEN_RARITY = addRarity("crystalGreen", TextFormatting.DARK_GREEN, ActuallyAdditions.NAME+" Green Crystal");
    public static final EnumRarity CRYSTAL_WHITE_RARITY = addRarity("crystalWhite", TextFormatting.GRAY, ActuallyAdditions.NAME+" White Crystal");

    public static final EnumRarity FALLBACK_RARITY = addRarity("fallback", TextFormatting.STRIKETHROUGH, ActuallyAdditions.NAME+" Fallback");

    private static EnumRarity addRarity(String name, TextFormatting color, String displayName){
        return EnumHelper.addRarity((ActuallyAdditions.MODID+"_"+name).toUpperCase(Locale.ROOT), color, displayName);
    }

    public static boolean isDevVersion(){
        return ActuallyAdditions.VERSION.equals("@VERSION@");
    }

    public static boolean isClient(){
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    private static String[] splitVersion(){
        return ActuallyAdditions.VERSION.split("-");
    }

    public static String getMcVersion(){
        return splitVersion()[0];
    }

    public static String getMajorModVersion(){
        return splitVersion()[1].substring(1);
    }
}