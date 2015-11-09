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

    REDSTONE("Red", EnumRarity.rare, 12595250),
    LAPIS("Blue", EnumRarity.uncommon, 24983),
    DIAMOND("LightBlue", EnumRarity.epic, 40140),
    COAL("Black", EnumRarity.uncommon, 2500135);

    public final String name;
    public final EnumRarity rarity;
    public final int color;

    TheCrystals(String name, EnumRarity rarity, int color){
        this.name = name;
        this.rarity = rarity;
        this.color = color;
    }
}