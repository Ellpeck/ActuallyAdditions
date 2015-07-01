package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.util.ModUtil;

public class InitCrafting {

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crafting Recipes...");

        ItemCrafting.init();
        BlockCrafting.init();
        MiscCrafting.init();
        FoodCrafting.init();
        ToolCrafting.init();
    }

}
