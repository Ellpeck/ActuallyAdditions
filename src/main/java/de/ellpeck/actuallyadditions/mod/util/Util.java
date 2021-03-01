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
import net.minecraftforge.fml.loading.FMLLoader;

public final class Util {

    @Deprecated
    public static final int WILDCARD = Short.MAX_VALUE;//OreDictionary.WILDCARD_VALUE;

    @Deprecated
    public static final int BUCKET = 1000;

    public static boolean isDevVersion() {
        return ActuallyAdditions.VERSION.equals("@VERSION@");
    }

    public static boolean isClient() {
        return FMLLoader.getDist().isClient();
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
