package de.ellpeck.actuallyadditions.mod.config.values;

import de.ellpeck.actuallyadditions.mod.config.ConfigCategories;
import net.minecraftforge.common.config.Configuration;

public enum ConfigFloatValues
{
    DRILL_SPEED_I_COST_MULTIPLIER("Drill: Speed I cost multiplier", ConfigCategories.TOOL_ENERGY_VALUES, 1.5F, 1.0F, 10.0F, "Energy consumption multiplier for Speed I upgrade"),
    DRILL_SPEED_II_COST_MULTIPLIER("Drill: Speed II cost multiplier", ConfigCategories.TOOL_ENERGY_VALUES, 1.5F, 1.0F, 10.0F, "Energy consumption multiplier for Speed II upgrade; total cost for this upgrade is multiplied on Speed I cost"),
    DRILL_SPEED_III_COST_MULTIPLIER("Drill: Speed III cost multiplier", ConfigCategories.TOOL_ENERGY_VALUES, 1.5F, 1.0F, 10.0F, "Energy consumption multiplier for Speed III upgrade; total cost for this upgrade is multiplied on Speed I and Speed II costs"),
    DRILL_SILK_TOUCH_COST_MULTIPLIER("Drill: Silk Touch cost multiplier", ConfigCategories.TOOL_ENERGY_VALUES, 2.0F, 1.0F, 10.0F, "Energy consumption multiplier for Silk Touch upgrade"),
    DRILL_FORTUNE_I_COST_MULTIPLIER("Drill: Fortune I cost multiplier", ConfigCategories.TOOL_ENERGY_VALUES, 1.5F, 1.0F, 10.0F, "Energy consumption multiplier for Fortune I upgrade"),
    DRILL_FORTUNE_II_COST_MULTIPLIER("Drill: Fortune II cost multiplier", ConfigCategories.TOOL_ENERGY_VALUES, 1.5F, 1.0F, 10.0F, "Energy consumption multiplier for Fortune II upgrade; total cost for this upgrade is multiplied on Fortune I cost"),
    DRILL_SIZE_3_COST_MULTIPLIER("Drill: 3x3 Upgrade cost multiplier", ConfigCategories.TOOL_ENERGY_VALUES, 1.25F, 1.0F, 10.0F, "Energy consumption multiplier for 3x3 digging upgrade"),
    DRILL_SIZE_5_COST_MULTIPLIER("Drill: 5x5 Upgrade cost multiplier", ConfigCategories.TOOL_ENERGY_VALUES, 1.25F, 1.0F, 10.0F, "Energy consumption multiplier for 5x5 digging upgrade; total cost for this upgrade is multiplied on 3x3 digging upgrade cost");

    public final String name;
    public final String category;
    public final float defaultValue;
    public final float min;
    public final float max;
    public final String desc;

    public float currentValue;

    ConfigFloatValues(String name, ConfigCategories category, float defaultValue, float min, float max, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.desc = desc;
    }

    public void initializeValue(Configuration config){
        this.currentValue = config.getFloat(this.name, this.category, this.defaultValue, this.min, this.max, this.desc);
    }

    public float getValue(){
        return this.currentValue;
    }
}
