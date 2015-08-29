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

    FOOD_CRAFTING("food crafting"),
    MISC_CRAFTING("misc crafting"),
    BLOCKS_CRAFTING("block crafting"),
    ITEMS_CRAFTING("item crafting"),
    TOOL_VALUES("tool values"),
    MACHINE_VALUES("machine values"),
    MOB_DROPS("mob drops"),
    WORLD_GEN("world gen"),
    POTION_RING_CRAFTING("ring crafting"),
    OTHER("other"),
    FLUIDS("fluids"),
    DRILL_VALUES("drill values"),
    CRUSHER_RECIPES("crusher recipes"),
    ARMOR_VALUES("armor values");

    public final String name;

    ConfigCategories(String name){
        this.name = name;
    }
}
