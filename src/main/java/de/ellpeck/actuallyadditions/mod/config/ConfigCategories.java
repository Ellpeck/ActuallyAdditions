/*
 * This file ("ConfigCategories.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config;

import java.util.Locale;

public enum ConfigCategories{

    TOOL_VALUES("Tool Values", "Values for Tools"),
    MACHINE_VALUES("Machine Values", "Values for Machines"),
    MOB_DROPS("Mob Drops", "Everything regarding Item drops from mobs"),
    WORLD_GEN("World Gen", "Everything regarding World Generation"),
    OTHER("Other", "Everything else");

    public final String name;
    public final String comment;

    ConfigCategories(String name, String comment){
        this.name = name.toLowerCase(Locale.ROOT);
        this.comment = comment;
    }
}
