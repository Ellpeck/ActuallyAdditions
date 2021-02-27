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

@Deprecated
public enum TheMiscItems {

    PAPER_CONE("paper_cone"),
    MASHED_FOOD("mashed_food"),
    KNIFE_BLADE("knife_blade"),
    KNIFE_HANDLE("knife_handle"),
    DOUGH("dough"),
    QUARTZ("black_quartz"),
    RING("ring"),
    COIL("coil"),
    COIL_ADVANCED("coil_advanced"),
    RICE_DOUGH("rice_dough"),
    TINY_COAL("tiny_coal"),
    TINY_CHAR("tiny_charcoal"),
    RICE_SLIME("rice_slime"),
    CANOLA("canola"),
    CUP("cup"),
    BAT_WING("bat_wing"),
    DRILL_CORE("drill_core"),
    BLACK_DYE("black_dye"),
    LENS("lens"),
    ENDER_STAR("ender_star"),
    SPAWNER_SHARD("spawner_shard"),
    BIOMASS("biomass"),
    BIOCOAL("biocoal"),
    CRYSTALLIZED_CANOLA_SEED("crystallized_canola_seed"),
    EMPOWERED_CANOLA_SEED("empowered_canola_seed"),
    YOUTUBE_ICON("youtube_icon");

    public final String name;

    TheMiscItems(String name) {
        this.name = name;
    }
}
