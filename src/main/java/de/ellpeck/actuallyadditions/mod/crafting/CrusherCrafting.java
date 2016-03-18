/*
 * This file ("CrusherCrafting.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.recipe.CrusherRecipeRegistry;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class CrusherCrafting{

    public static CrusherRecipe recipeIronHorseArmor;
    public static CrusherRecipe recipeGoldHorseArmor;
    public static CrusherRecipe recipeDiamondHorseArmor;
    public static ArrayList<CrusherRecipe> miscRecipes = new ArrayList<CrusherRecipe>();

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crusher Recipes...");

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.bone), new ItemStack(Items.dye, 6, 15));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.reeds), new ItemStack(Items.sugar, 3));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.yellow_flower), new ItemStack(Items.dye, 3, 11));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Items.dye, 3, 1));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 1), new ItemStack(Items.dye, 3, 12));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 2), new ItemStack(Items.dye, 3, 13));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 3), new ItemStack(Items.dye, 3, 7));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 4), new ItemStack(Items.dye, 3, 1));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 5), new ItemStack(Items.dye, 3, 14));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 6), new ItemStack(Items.dye, 3, 7));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 7), new ItemStack(Items.dye, 3, 9));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.red_flower, 1, 8), new ItemStack(Items.dye, 3, 7));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Items.dye, 4, 11));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.double_plant, 1, 1), new ItemStack(Items.dye, 4, 13));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.double_plant, 1, 4), new ItemStack(Items.dye, 4, 1));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.double_plant, 1, 5), new ItemStack(Items.dye, 4, 9));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe("oreRedstone", "dustRedstone", 10);
        ActuallyAdditionsAPI.addCrusherRecipe("oreLapis", "gemLapis", 12);
        ActuallyAdditionsAPI.addCrusherRecipe("coal", "dustCoal", 1);
        ActuallyAdditionsAPI.addCrusherRecipe("oreCoal", "coal", 3);
        ActuallyAdditionsAPI.addCrusherRecipe("blockCoal", "coal", 9);
        ActuallyAdditionsAPI.addCrusherRecipe("oreQuartz", "gemQuartz", 3);
        ActuallyAdditionsAPI.addCrusherRecipe("cobblestone", "sand", 1);
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.gravel), new ItemStack(Items.flint), new ItemStack(Items.flint), 50);
        ActuallyAdditionsAPI.addCrusherRecipe("stone", "cobblestone", 1);

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.sugar, 2));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.glowstone), new ItemStack(Items.glowstone_dust, 4));
        miscRecipes.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe("oreNickel", "dustNickel", 2, "dustPlatinum", 1, 15);
        ActuallyAdditionsAPI.addCrusherRecipe("oreIron", "dustIron", 2, "dustGold", 1, 20);

        if(ConfigCrafting.HORSE_ARMORS.isEnabled()){
            ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.iron_horse_armor), "dustIron", 8);
            recipeIronHorseArmor = RecipeUtil.lastCrusherRecipe();

            ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.golden_horse_armor), "dustGold", 8);
            recipeGoldHorseArmor = RecipeUtil.lastCrusherRecipe();

            ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.diamond_horse_armor), "dustDiamond", 8);
            recipeDiamondHorseArmor = RecipeUtil.lastCrusherRecipe();
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
