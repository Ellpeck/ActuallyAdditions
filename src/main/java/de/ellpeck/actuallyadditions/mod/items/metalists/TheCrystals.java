/*
 * This file ("TheCrystals.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.item.EnumRarity;

public enum TheCrystals{

    REDSTONE("Red", Util.CRYSTAL_RED_RARITY, 16318464),
    LAPIS("Blue", Util.CRYSTAL_BLUE_RARITY, 131437),
    DIAMOND("LightBlue", Util.CRYSTAL_LIGHT_BLUE_RARITY, 9211636),
    COAL("Black", Util.CRYSTAL_BLACK_RARITY, 986895),
    EMERALD("Green", Util.CRYSTAL_GREEN_RARITY, 382466),
    IRON("White", Util.CRYSTAL_WHITE_RARITY, 11053224);

    public final String name;
    public final EnumRarity rarity;
    public final int color;

    TheCrystals(String name, EnumRarity rarity, int color){
        this.name = name;
        this.rarity = rarity;
        this.color = color;
    }
}