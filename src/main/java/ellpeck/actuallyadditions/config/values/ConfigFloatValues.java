/*
 * This file ("ConfigFloatValues.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.config.values;

import ellpeck.actuallyadditions.config.ConfigCategories;

public enum ConfigFloatValues{

    EMERALD_SPEED("Emerald: Efficiency", ConfigCategories.TOOL_VALUES, 9.0F, 1.0F, 20.0F, "How fast Emerald Tools are"),
    EMERALD_MAX_DAMAGE("Emerald: Damage", ConfigCategories.TOOL_VALUES, 5.0F, 0.1F, 50.0F, "How much damage an Emerald Tool deals"),

    OBSIDIAN_SPEED("Obsidian: Efficiency", ConfigCategories.TOOL_VALUES, 4.0F, 1.0F, 20.0F, "How fast Obsidian Tools are"),
    OBSIDIAN_MAX_DAMAGE("Obsidian: Damage", ConfigCategories.TOOL_VALUES, 2.0F, 0.1F, 50.0F, "How much damage an Obsidian Tool deals"),

    QUARTZ_SPEED("Quartz: Efficiency", ConfigCategories.TOOL_VALUES, 6.5F, 1.0F, 20.0F, "How fast Quartz Tools are"),
    QUARTZ_MAX_DAMAGE("Quartz: Damage", ConfigCategories.TOOL_VALUES, 2.0F, 0.1F, 50.0F, "How much damage an Quartz Tool deals"),

    DRILL_DAMAGE("Drill: Default Damage", ConfigCategories.DRILL_VALUES, 8.0F, 1.0F, 30.0F, "How much Damage the Drill does to an Entity");

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

    public float getValue(){
        return this.currentValue;
    }

}
