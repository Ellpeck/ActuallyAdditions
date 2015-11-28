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
import ellpeck.actuallyadditions.util.Util;

import java.util.ArrayList;

public class CrusherCrafting{

    public static CrusherRecipeRegistry.CrusherRecipe recipeSugar;
    public static CrusherRecipeRegistry.CrusherRecipe recipeIronHorseArmor;
    public static CrusherRecipeRegistry.CrusherRecipe recipeGoldHorseArmor;
    public static CrusherRecipeRegistry.CrusherRecipe recipeDiamondHorseArmor;
    public static ArrayList<CrusherRecipeRegistry.CrusherRecipe> miscRecipes = new ArrayList<CrusherRecipeRegistry.CrusherRecipe>();

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crusher Recipes...");

        CrusherRecipeRegistry.addRecipe("itemBone", "boneMeal", 6);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("sugarCane", "sugar", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());

        CrusherRecipeRegistry.addRecipe("flowerDandelion", "dyeYellow", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerPoppy", "dyeRed", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerOrchid", "dyeLightBlue", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerAllium", "dyeMagenta", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerBluet", "dyeLightGray", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerRedTulip", "dyeRed", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerOrangeTulip", "dyeOrange", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerWhiteTulip", "dyeLightGray", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerPinkTulip", "dyePink", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerDaisy", "dyeLightGray", 3);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerSunflower", "dyeYellow", 4);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerLilac", "dyeMagenta", 4);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerRoseBush", "dyeRed", 4);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());
        CrusherRecipeRegistry.addRecipe("flowerPeony", "dyePink", 4);
        miscRecipes.add(Util.GetRecipes.lastCrusherRecipe());

        CrusherRecipeRegistry.addRecipe("oreRedstone", "dustRedstone", 10);
        CrusherRecipeRegistry.addRecipe("oreLapis", "gemLapis", 12);
        CrusherRecipeRegistry.addRecipe("coal", "dustCoal", 1);
        CrusherRecipeRegistry.addRecipe("oreCoal", "coal", 3);
        CrusherRecipeRegistry.addRecipe("blockCoal", "coal", 9);
        CrusherRecipeRegistry.addRecipe("oreQuartz", "gemQuartz", 3);
        CrusherRecipeRegistry.addRecipe("cobblestone", "sand", 1);
        CrusherRecipeRegistry.addRecipe("gravel", "flint", 1);
        CrusherRecipeRegistry.addRecipe("stone", "cobblestone", 1);
        CrusherRecipeRegistry.addRecipe("cropRice", "sugar", 2);
        recipeSugar = Util.GetRecipes.lastCrusherRecipe();

        CrusherRecipeRegistry.addRecipe("oreNickel", "dustNickel", 2, "dustPlatinum", 1, 15);
        CrusherRecipeRegistry.addRecipe("oreIron", "dustIron", 2, "dustGold", 1, 20);

        if(ConfigCrafting.HORSE_ARMORS.isEnabled()){
            CrusherRecipeRegistry.addRecipe("armorHorseIron", "dustIron", 8);
            recipeIronHorseArmor = Util.GetRecipes.lastCrusherRecipe();

            CrusherRecipeRegistry.addRecipe("armorHorseGold", "dustGold", 8);
            recipeGoldHorseArmor = Util.GetRecipes.lastCrusherRecipe();

            CrusherRecipeRegistry.addRecipe("armorHorseDiamond", "dustDiamond", 8);
            recipeDiamondHorseArmor = Util.GetRecipes.lastCrusherRecipe();
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
