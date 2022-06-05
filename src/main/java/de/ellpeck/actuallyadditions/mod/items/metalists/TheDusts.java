/*
 * This file ("TheDusts.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import net.minecraft.item.Rarity;

@Deprecated
public enum TheDusts {

    IRON("iron", 7826534, Rarity.COMMON),
    GOLD("gold", 14335744, Rarity.UNCOMMON),
    DIAMOND("diamond", 292003, Rarity.RARE),
    EMERALD("emerald", 4319527, Rarity.EPIC),
    LAPIS("lapis", 1849791, Rarity.UNCOMMON),
    QUARTZ("quartz", 0xFFFFFF, Rarity.UNCOMMON),
    COAL("coal", 0, Rarity.UNCOMMON),
    QUARTZ_BLACK("quartz_black", 18, Rarity.RARE);

    public final String name;
    public final int color;
    public final Rarity rarity;

    TheDusts(String name, int color, Rarity rarity) {
        this.name = name;
        this.color = color;
        this.rarity = rarity;
    }

}
