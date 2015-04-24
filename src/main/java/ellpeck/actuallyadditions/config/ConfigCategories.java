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
    POTION_RING_CRAFTING("ring crafting");

    public final String name;

    ConfigCategories(String name){
        this.name = name;
    }
}
