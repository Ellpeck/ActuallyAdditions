package ellpeck.someprettyrandomstuff.crafting;

import ellpeck.someprettyrandomstuff.util.Util;

public class InitCrafting {

    public static void init(){
        Util.logInfo("Initializing Crafting Recipes...");

        ItemCrafting.init();
        BlockCrafting.init();
        MiscCrafting.init();
        FoodCrafting.init();
        ToolCrafting.init();
        GrinderCrafting.init();

    }

}
