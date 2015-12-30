/*
 * This file ("TheMiscItems.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.metalists;

import net.minecraft.item.EnumRarity;

public enum TheMiscItems{

    PAPER_CONE("PaperCone", EnumRarity.common),
    MASHED_FOOD("MashedFood", EnumRarity.uncommon),
    KNIFE_BLADE("KnifeBlade", EnumRarity.common),
    KNIFE_HANDLE("KnifeHandle", EnumRarity.common),
    DOUGH("Dough", EnumRarity.common),
    QUARTZ("BlackQuartz", EnumRarity.epic),
    RING("Ring", EnumRarity.uncommon),
    COIL("Coil", EnumRarity.common),
    COIL_ADVANCED("CoilAdvanced", EnumRarity.uncommon),
    RICE_DOUGH("RiceDough", EnumRarity.uncommon),
    TINY_COAL("TinyCoal", EnumRarity.common),
    TINY_CHAR("TinyCharcoal", EnumRarity.common),
    RICE_SLIME("RiceSlime", EnumRarity.uncommon),
    CANOLA("Canola", EnumRarity.uncommon),
    CUP("Cup", EnumRarity.uncommon),
    BAT_WING("BatWing", EnumRarity.rare),
    DRILL_CORE("DrillCore", EnumRarity.uncommon),
    BLACK_DYE("BlackDye", EnumRarity.epic),
    LENS("Lens", EnumRarity.uncommon);

    public final String name;
    public final EnumRarity rarity;

    TheMiscItems(String name, EnumRarity rarity){
        this.name = name;
        this.rarity = rarity;
    }
}