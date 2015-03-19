package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.util.Util;

public class InitCrafting {

    public static void init(){
        Util.logInfo("Initializing Crafting Recipes...");

        ItemCrafting.init();
        BlockCrafting.init();
        MiscCrafting.init();
        FoodCrafting.init();
        ToolCrafting.init();

    }

}
