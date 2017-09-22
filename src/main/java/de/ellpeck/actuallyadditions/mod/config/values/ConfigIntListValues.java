/*
 * This file ("ConfigIntListValues.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config.values;

import de.ellpeck.actuallyadditions.mod.config.ConfigCategories;
import net.minecraftforge.common.config.Configuration;

public enum ConfigIntListValues{

    ORE_GEN_DIMENSION_BLACKLIST("OreGen Dimension Blacklist", ConfigCategories.WORLD_GEN, new int[0], "The list of IDs that Actually Additions OreGen (Ex: Black Quartz) is banned in. This also applies for other world gen like lush caves."),
    PLANT_DIMENSION_BLACKLIST("Plant Blacklist", ConfigCategories.WORLD_GEN, new int[0], "The list of IDs of the dimensions that Actually Additions Plants (Rice for example) are banned in."),
    OIL_POWER("Oil Gen: Power Values", ConfigCategories.MACHINE_VALUES, new int[]{40, 80, 100, 120}, "The amount of power that the 4 tiers of oils generate (in order)"),
    OIL_TIME("Oil Gen: Time Values", ConfigCategories.MACHINE_VALUES, new int[]{100, 120, 280, 400}, "The amount of time that the 4 tiers of oils work for (in order)");

    public final String name;
    public final String category;
    public final int[] defaultValue;
    public final String desc;

    public int[] currentValue;

    ConfigIntListValues(String name, ConfigCategories category, int[] defaultValue, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.desc = desc;
    }

    public void initializeValue(Configuration config){
        this.currentValue = config.get(this.category, this.name, this.defaultValue, this.desc).getIntList();
    }

    public int[] getValue(){
        return this.currentValue;
    }

}
