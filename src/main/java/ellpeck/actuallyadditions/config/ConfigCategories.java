/*
 * This file ("ConfigCategories.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.config;

public enum ConfigCategories{

    FOOD_CRAFTING("food crafting", "Crafting Recipes for Food Items"),
    BLOCKS_CRAFTING("block crafting", "Crafting Recipes for Blocks"),
    ITEMS_CRAFTING("item crafting", "Crafting Recipes for Items"),
    TOOL_VALUES("tool values", "Values for Tools"),
    MACHINE_VALUES("machine values", "Values for Machines"),
    MOB_DROPS("mob drops", "Everything regarding Item drops from mobs"),
    WORLD_GEN("world gen", "Everything regarding World Generation"),
    POTION_RING_CRAFTING("ring crafting", "Crafting Recipes for Rings"),
    OTHER("other", "Everything else"),
    FLUIDS("fluids", "Everything regarding fluids"),
    DRILL_VALUES("drill values", "Properties of Drills and their Upgrades"),
    CRUSHER_RECIPES("crusher recipes", "Recipes for the Crusher"),
    ARMOR_VALUES("armor values", "Values for Armor");

    public final String name;
    public final String comment;

    ConfigCategories(String name, String comment){
        this.name = name.toLowerCase();
        this.comment = comment;
    }
}
