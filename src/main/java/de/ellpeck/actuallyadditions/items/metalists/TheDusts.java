/*
 * This file ("TheDusts.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.metalists;

import de.ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.item.EnumRarity;

public enum TheDusts{

    IRON("Iron", 7826534, EnumRarity.common),
    GOLD("Gold", 14335744, EnumRarity.uncommon),
    DIAMOND("Diamond", 292003, EnumRarity.rare),
    EMERALD("Emerald", 4319527, EnumRarity.epic),
    LAPIS("Lapis", 1849791, EnumRarity.uncommon),
    QUARTZ("Quartz", StringUtil.DECIMAL_COLOR_WHITE, EnumRarity.uncommon),
    COAL("Coal", 0, EnumRarity.uncommon),
    QUARTZ_BLACK("QuartzBlack", 18, EnumRarity.rare);

    public final String name;
    public final int color;
    public final EnumRarity rarity;

    TheDusts(String name, int color, EnumRarity rarity){
        this.name = name;
        this.color = color;
        this.rarity = rarity;
    }


}