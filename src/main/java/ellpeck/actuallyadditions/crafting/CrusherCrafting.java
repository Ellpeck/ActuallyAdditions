/*
 * This file ("CrusherCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.recipe.CrusherRecipeRegistry;
import ellpeck.actuallyadditions.util.ModUtil;

public class CrusherCrafting{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crusher Recipes...");

        if(ConfigCrafting.REDSTONE.isEnabled()){
            CrusherRecipeRegistry.addRecipe("oreRedstone", "dustRedstone", 10);
        }
        if(ConfigCrafting.LAPIS.isEnabled()){
            CrusherRecipeRegistry.addRecipe("oreLapis", "gemLapis", 12);
        }
        if(ConfigCrafting.COAL.isEnabled()){
            CrusherRecipeRegistry.addRecipe("coal", "dustCoal", 1);
        }
        if(ConfigCrafting.COAL_ORE.isEnabled()){
            CrusherRecipeRegistry.addRecipe("oreCoal", "coal", 3);
        }
        if(ConfigCrafting.COAL_BLOCKS.isEnabled()){
            CrusherRecipeRegistry.addRecipe("blockCoal", "coal", 9);
        }
        if(ConfigCrafting.QUARTZ_ORE.isEnabled()){
            CrusherRecipeRegistry.addRecipe("oreQuartz", "gemQuartz", 3);
        }

        if(ConfigCrafting.COBBLESTONE.isEnabled()){
            CrusherRecipeRegistry.addRecipe("cobblestone", "sand", 1);
        }
        if(ConfigCrafting.GRAVEL.isEnabled()){
            CrusherRecipeRegistry.addRecipe("gravel", "flint", 1);
        }
        if(ConfigCrafting.STONE.isEnabled()){
            CrusherRecipeRegistry.addRecipe("stone", "cobblestone", 1);
        }
        if(ConfigCrafting.RICE_SUGAR.isEnabled()){
            CrusherRecipeRegistry.addRecipe("cropRice", "sugar", 2);
        }

        if(ConfigCrafting.NICKEL.isEnabled()){
            CrusherRecipeRegistry.addRecipe("oreNickel", "dustNickel", 2, "dustPlatinum", 1, 15);
        }
        if(ConfigCrafting.IRON.isEnabled()){
            CrusherRecipeRegistry.addRecipe("oreIron", "dustIron", 2, "dustGold", 1, 20);
        }

        CrusherRecipeRegistry.searchCases.add(new CrusherRecipeRegistry.SearchCase("oreNether", 6));
        CrusherRecipeRegistry.searchCases.add(new CrusherRecipeRegistry.SearchCase("orePoor", 4, "nugget"));
        CrusherRecipeRegistry.searchCases.add(new CrusherRecipeRegistry.SearchCase("denseore", 8));
        CrusherRecipeRegistry.searchCases.add(new CrusherRecipeRegistry.SearchCase("gem", 1));
        CrusherRecipeRegistry.searchCases.add(new CrusherRecipeRegistry.SearchCase("ingot", 1));
        CrusherRecipeRegistry.searchCases.add(new CrusherRecipeRegistry.SearchCase("ore", 2));

        CrusherRecipeRegistry.registerFinally();
    }
}
