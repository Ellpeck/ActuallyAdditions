/*
 * This file ("TheCrystals.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.metalists;

import net.minecraft.item.EnumRarity;

public enum TheCrystals{

    REDSTONE("Red", EnumRarity.rare, 16318464),
    LAPIS("Blue", EnumRarity.uncommon, 131437),
    DIAMOND("LightBlue", EnumRarity.epic, 9211636),
    COAL("Black", EnumRarity.uncommon, 986895),
    EMERALD("Green", EnumRarity.epic, 382466),
    IRON("White", EnumRarity.rare, 11053224);

    public final String name;
    public final EnumRarity rarity;
    public final int color;

    TheCrystals(String name, EnumRarity rarity, int color){
        this.name = name;
        this.rarity = rarity;
        this.color = color;
    }
}