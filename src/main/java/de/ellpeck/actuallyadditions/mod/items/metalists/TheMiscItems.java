/*
 * This file ("TheMiscItems.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.metalists;

import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.item.EnumRarity;

public enum TheMiscItems{

    PAPER_CONE("paper_cone", EnumRarity.COMMON),
    MASHED_FOOD("mashed_food", EnumRarity.UNCOMMON),
    KNIFE_BLADE("knife_blade", EnumRarity.COMMON),
    KNIFE_HANDLE("knife_handle", EnumRarity.COMMON),
    DOUGH("dough", EnumRarity.COMMON),
    QUARTZ("black_quartz", EnumRarity.EPIC),
    RING("ring", EnumRarity.UNCOMMON),
    COIL("coil", EnumRarity.COMMON),
    COIL_ADVANCED("coil_advanced", EnumRarity.UNCOMMON),
    RICE_DOUGH("rice_dough", EnumRarity.UNCOMMON),
    TINY_COAL("tiny_coal", EnumRarity.COMMON),
    TINY_CHAR("tiny_charcoal", EnumRarity.COMMON),
    RICE_SLIME("rice_slime", EnumRarity.UNCOMMON),
    CANOLA("canola", EnumRarity.UNCOMMON),
    CUP("cup", EnumRarity.UNCOMMON),
    BAT_WING("bat_wing", EnumRarity.RARE),
    DRILL_CORE("drill_core", EnumRarity.UNCOMMON),
    BLACK_DYE("black_dye", EnumRarity.EPIC),
    LENS("lens", EnumRarity.UNCOMMON),
    ENDER_STAR("ender_star", EnumRarity.EPIC),
    SPAWNER_SHARD("spawner_shard", EnumRarity.EPIC),
    BIOMASS("biomass", EnumRarity.UNCOMMON),
    BIOCOAL("biocoal", EnumRarity.RARE),
    CRYSTALLIZED_CANOLA_SEED("crystallized_canola_seed", EnumRarity.UNCOMMON),
    EMPOWERED_CANOLA_SEED("empowered_canola_seed", EnumRarity.RARE),
    YOUTUBE_ICON("youtube_icon", Util.FALLBACK_RARITY);

    public final String name;
    public final EnumRarity rarity;

    TheMiscItems(String name, EnumRarity rarity){
        this.name = name;
        this.rarity = rarity;
    }
}