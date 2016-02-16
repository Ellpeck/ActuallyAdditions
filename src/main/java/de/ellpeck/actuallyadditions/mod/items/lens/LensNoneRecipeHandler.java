/*
 * This file ("LensNoneRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class LensNoneRecipeHandler{

    public static ArrayList<LensNoneRecipe> mainPageRecipes = new ArrayList<LensNoneRecipe>();
    public static LensNoneRecipe recipeColorLens;
    public static LensNoneRecipe recipeSoulSand;
    public static LensNoneRecipe recipeGreenWall;
    public static LensNoneRecipe recipeWhiteWall;
    public static LensNoneRecipe recipeExplosionLens;
    public static LensNoneRecipe recipeDamageLens;
    public static LensNoneRecipe recipeLeather;
    public static LensNoneRecipe recipeNetherWart;

    public static void init(){
        //Crystal Blocks
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.redstone_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.REDSTONE.ordinal()), 400);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.lapis_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.LAPIS.ordinal()), 400);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.diamond_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()), 600);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.emerald_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.EMERALD.ordinal()), 1000);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.coal_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal()), 600);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.iron_block), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()), 800);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());

        //Crystal Items
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.redstone), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 40);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.dye, 1, 4), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), 40);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.diamond), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), 60);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.emerald), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), 100);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.coal), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), 60);
        mainPageRecipes.add(Util.GetRecipes.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.iron_ingot), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 80);

        //Lenses
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), new ItemStack(InitItems.itemColorLens), 5000);
        recipeColorLens = Util.GetRecipes.lastReconstructorRecipe();

        if(ConfigCrafting.RECONSTRUCTOR_EXPLOSION_LENS.isEnabled()){
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemColorLens), new ItemStack(InitItems.itemExplosionLens), 5000);
            recipeExplosionLens = Util.GetRecipes.lastReconstructorRecipe();
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemExplosionLens), new ItemStack(InitItems.itemDamageLens), 5000);
        }
        else{
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemColorLens), new ItemStack(InitItems.itemDamageLens), 5000);
        }
        recipeDamageLens = Util.GetRecipes.lastReconstructorRecipe();

        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemDamageLens), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), 5000);

        //Misc
        if(ConfigCrafting.RECONSTRUCTOR_MISC.isEnabled()){
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.sand), new ItemStack(Blocks.soul_sand), 20000);
            recipeSoulSand = Util.GetRecipes.lastReconstructorRecipe();
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.rotten_flesh), new ItemStack(Items.leather), 8000);
            recipeLeather = Util.GetRecipes.lastReconstructorRecipe();

            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.red_mushroom), new ItemStack(Items.nether_wart), 150000);
            recipeNetherWart = Util.GetRecipes.lastReconstructorRecipe();
        }

        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.quartz_block), new ItemStack(InitBlocks.blockTestifiBucksWhiteWall), 10);
        recipeWhiteWall = Util.GetRecipes.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.quartz_block, 1, 1), new ItemStack(InitBlocks.blockTestifiBucksGreenWall), 10);
        recipeGreenWall = Util.GetRecipes.lastReconstructorRecipe();
    }

    public static ArrayList<LensNoneRecipe> getRecipesFor(ItemStack input){
        ArrayList<LensNoneRecipe> possibleRecipes = new ArrayList<LensNoneRecipe>();
        for(LensNoneRecipe recipe : ActuallyAdditionsAPI.reconstructorLensNoneRecipes){
            if(ItemUtil.contains(recipe.getInputs(), input, true)){
                possibleRecipes.add(recipe);
            }
        }
        return possibleRecipes;
    }
}
