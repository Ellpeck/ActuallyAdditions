/*
 * This file ("CrusherCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
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

public final class CrusherCrafting{

    public static final ArrayList<CrusherRecipe> MISC_RECIPES = new ArrayList<CrusherRecipe>();
    public static CrusherRecipe recipeIronHorseArmor;
    public static CrusherRecipe recipeGoldHorseArmor;
    public static CrusherRecipe recipeDiamondHorseArmor;

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crusher Recipes...");

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.BONE), new ItemStack(Items.DYE, 6, 15));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.REEDS), new ItemStack(Items.SUGAR, 3));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.YELLOW_FLOWER), new ItemStack(Items.DYE, 3, 11));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 0), new ItemStack(Items.DYE, 3, 1));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 1), new ItemStack(Items.DYE, 3, 12));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 2), new ItemStack(Items.DYE, 3, 13));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 3), new ItemStack(Items.DYE, 3, 7));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 4), new ItemStack(Items.DYE, 3, 1));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 5), new ItemStack(Items.DYE, 3, 14));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 6), new ItemStack(Items.DYE, 3, 7));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 7), new ItemStack(Items.DYE, 3, 9));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 8), new ItemStack(Items.DYE, 3, 7));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new ItemStack(Items.DYE, 4, 11));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), new ItemStack(Items.DYE, 4, 13));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), new ItemStack(Items.DYE, 4, 1));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), new ItemStack(Items.DYE, 4, 9));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe("oreRedstone", "dustRedstone", 10);
        ActuallyAdditionsAPI.addCrusherRecipe("oreLapis", "gemLapis", 12);
        ActuallyAdditionsAPI.addCrusherRecipe("coal", "dustCoal", 1);
        ActuallyAdditionsAPI.addCrusherRecipe("oreCoal", "coal", 3);
        ActuallyAdditionsAPI.addCrusherRecipe("blockCoal", "coal", 9);
        ActuallyAdditionsAPI.addCrusherRecipe("oreQuartz", "gemQuartz", 3);
        ActuallyAdditionsAPI.addCrusherRecipe("cobblestone", "sand", 1);
        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Items.FLINT), new ItemStack(Items.FLINT), 50);
        ActuallyAdditionsAPI.addCrusherRecipe("stone", "cobblestone", 1);

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(InitItems.itemFoods, 1, TheFoods.RICE.ordinal()), new ItemStack(Items.SUGAR, 2));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.GLOWSTONE_DUST, 4));
        MISC_RECIPES.add(RecipeUtil.lastCrusherRecipe());

        ActuallyAdditionsAPI.addCrusherRecipe("oreNickel", "dustNickel", 2, "dustPlatinum", 1, 15);
        ActuallyAdditionsAPI.addCrusherRecipe("oreIron", "dustIron", 2, "dustGold", 1, 20);

        if(ConfigCrafting.HORSE_ARMORS.isEnabled()){
            ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.IRON_HORSE_ARMOR), "dustIron", 8);
            recipeIronHorseArmor = RecipeUtil.lastCrusherRecipe();

            ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.GOLDEN_HORSE_ARMOR), "dustGold", 8);
            recipeGoldHorseArmor = RecipeUtil.lastCrusherRecipe();

            ActuallyAdditionsAPI.addCrusherRecipe(new ItemStack(Items.DIAMOND_HORSE_ARMOR), "dustDiamond", 8);
            recipeDiamondHorseArmor = RecipeUtil.lastCrusherRecipe();
        }

        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("oreNether", 6));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("orePoor", 4, "nugget"));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("denseore", 8));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("gem", 1));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("ingot", 1));
        CrusherRecipeRegistry.SEARCH_CASES.add(new CrusherRecipeRegistry.SearchCase("ore", 2));

        CrusherRecipeRegistry.registerFinally();
    }
}
