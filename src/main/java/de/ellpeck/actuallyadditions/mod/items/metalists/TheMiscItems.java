/*
 * This file ("TheMiscItems.java") is part of the Actually Additions mod for Minecraft.
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

public enum TheMiscItems{

    PAPER_CONE("PaperCone", EnumRarity.COMMON),
    MASHED_FOOD("MashedFood", EnumRarity.UNCOMMON),
    KNIFE_BLADE("KnifeBlade", EnumRarity.COMMON),
    KNIFE_HANDLE("KnifeHandle", EnumRarity.COMMON),
    DOUGH("Dough", EnumRarity.COMMON),
    QUARTZ("BlackQuartz", EnumRarity.EPIC),
    RING("Ring", EnumRarity.UNCOMMON),
    COIL("Coil", EnumRarity.COMMON),
    COIL_ADVANCED("CoilAdvanced", EnumRarity.UNCOMMON),
    RICE_DOUGH("RiceDough", EnumRarity.UNCOMMON),
    TINY_COAL("TinyCoal", EnumRarity.COMMON),
    TINY_CHAR("TinyCharcoal", EnumRarity.COMMON),
    RICE_SLIME("RiceSlime", EnumRarity.UNCOMMON),
    CANOLA("Canola", EnumRarity.UNCOMMON),
    CUP("Cup", EnumRarity.UNCOMMON),
    BAT_WING("BatWing", EnumRarity.RARE),
    DRILL_CORE("DrillCore", EnumRarity.UNCOMMON),
    BLACK_DYE("BlackDye", EnumRarity.EPIC),
    LENS("Lens", EnumRarity.UNCOMMON),
    ENDER_STAR("EnderStar", EnumRarity.EPIC),
    SPAWNER_SHARD("SpawnerShard", EnumRarity.EPIC),
    BIOMASS("Biomass", EnumRarity.UNCOMMON),
    BIOCOAL("Biocoal", EnumRarity.RARE),
    CRYSTALLIZED_CANOLA_SEED("CrystallizedCanolaSeed", EnumRarity.UNCOMMON),
    YOUTUBE_ICON("YoutubeIcon", Util.FALLBACK_RARITY);

    public final String name;
    public final EnumRarity rarity;

    TheMiscItems(String name, EnumRarity rarity){
        this.name = name;
        this.rarity = rarity;
    }
}