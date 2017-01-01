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

import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.item.EnumRarity;

public enum TheDusts{

    IRON("iron", 7826534, EnumRarity.COMMON),
    GOLD("gold", 14335744, EnumRarity.UNCOMMON),
    DIAMOND("diamond", 292003, EnumRarity.RARE),
    EMERALD("emerald", 4319527, EnumRarity.EPIC),
    LAPIS("lapis", 1849791, EnumRarity.UNCOMMON),
    QUARTZ("quartz", StringUtil.DECIMAL_COLOR_WHITE, EnumRarity.UNCOMMON),
    COAL("coal", 0, EnumRarity.UNCOMMON),
    QUARTZ_BLACK("quartz_black", 18, EnumRarity.RARE);

    public final String name;
    public final int color;
    public final EnumRarity rarity;

    TheDusts(String name, int color, EnumRarity rarity){
        this.name = name;
        this.color = color;
        this.rarity = rarity;
    }


}