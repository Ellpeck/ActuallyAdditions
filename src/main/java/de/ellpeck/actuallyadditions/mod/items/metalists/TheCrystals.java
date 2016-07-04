/*
 * This file ("TheCrystals.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.item.EnumRarity;

public enum TheCrystals{

    REDSTONE("Red", Util.CRYSTAL_RED_RARITY),
    LAPIS("Blue", Util.CRYSTAL_BLUE_RARITY),
    DIAMOND("LightBlue", Util.CRYSTAL_LIGHT_BLUE_RARITY),
    COAL("Black", Util.CRYSTAL_BLACK_RARITY),
    EMERALD("Green", Util.CRYSTAL_GREEN_RARITY),
    IRON("White", Util.CRYSTAL_WHITE_RARITY);

    public final String name;
    public final EnumRarity rarity;

    TheCrystals(String name, EnumRarity rarity){
        this.name = name;
        this.rarity = rarity;
    }
}