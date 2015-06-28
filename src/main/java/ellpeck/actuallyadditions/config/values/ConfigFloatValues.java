package ellpeck.actuallyadditions.config.values;

import ellpeck.actuallyadditions.config.ConfigCategories;
import ellpeck.actuallyadditions.config.ConfigValues;

public enum ConfigFloatValues{

    EMERALD_SPEED("Emerald: Efficiency", ConfigCategories.TOOL_VALUES, 9.0F, 1.0F, 20.0F, "How fast Emerald Tools are"),
    EMERALD_MAX_DAMAGE("Emerald: Damage", ConfigCategories.TOOL_VALUES, 5.0F, 0.1F, 50.0F, "How much damage an Emerald Tool deals"),

    OBSIDIAN_SPEED("Obsidian: Efficiency", ConfigCategories.TOOL_VALUES, 4.0F, 1.0F, 20.0F, "How fast Obsidian Tools are"),
    OBSIDIAN_MAX_DAMAGE("Obsidian: Damage", ConfigCategories.TOOL_VALUES, 2.0F, 0.1F, 50.0F, "How much damage an Obsidian Tool deals"),

    DRILL_DAMAGE("Drill: Default Damage", ConfigCategories.DRILL_VALUES, 8.0F, 1.0F, 30.0F, "How much Damage the Drill does to an Entity");

    public final String name;
    public final String category;
    public final float defaultValue;
    public final float min;
    public final float max;
    public final String desc;

    ConfigFloatValues(String name, ConfigCategories category, float defaultValue, float min, float max, String desc){
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.desc = desc;
    }

    public float getValue(){
        return ConfigValues.floatValues[this.ordinal()];
    }

}
