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
import de.ellpeck.actuallyadditions.api.recipe.ColorLensChangerByDyeMeta;
import de.ellpeck.actuallyadditions.api.recipe.IColorLensChanger;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigCrafting;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class LensRecipeHandler{

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
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.REDSTONE_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.REDSTONE.ordinal()), 400);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.LAPIS_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.LAPIS.ordinal()), 400);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.DIAMOND_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()), 600);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.EMERALD_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.EMERALD.ordinal()), 1000);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.COAL_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal()), 600);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()), 800);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());

        //Crystal Items
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.REDSTONE), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 40);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.DYE, 1, 4), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), 40);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.DIAMOND), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), 60);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.EMERALD), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), 100);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.COAL), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), 60);
        mainPageRecipes.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 80);

        //Lenses
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), new ItemStack(InitItems.itemColorLens), 5000);
        recipeColorLens = RecipeUtil.lastReconstructorRecipe();

        if(ConfigCrafting.RECONSTRUCTOR_EXPLOSION_LENS.isEnabled()){
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemColorLens), new ItemStack(InitItems.itemExplosionLens), 5000);
            recipeExplosionLens = RecipeUtil.lastReconstructorRecipe();
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemExplosionLens), new ItemStack(InitItems.itemDamageLens), 5000);
        }
        else{
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemColorLens), new ItemStack(InitItems.itemDamageLens), 5000);
        }
        recipeDamageLens = RecipeUtil.lastReconstructorRecipe();

        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(InitItems.itemDamageLens), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), 5000);

        //Misc
        if(ConfigCrafting.RECONSTRUCTOR_MISC.isEnabled()){
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.SAND), new ItemStack(Blocks.SOUL_SAND), 20000);
            recipeSoulSand = RecipeUtil.lastReconstructorRecipe();
            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER), 8000);
            recipeLeather = RecipeUtil.lastReconstructorRecipe();

            ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Items.NETHER_WART), 150000);
            recipeNetherWart = RecipeUtil.lastReconstructorRecipe();
        }

        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.QUARTZ_BLOCK), new ItemStack(InitBlocks.blockTestifiBucksWhiteWall), 10);
        recipeWhiteWall = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensNoneRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), new ItemStack(InitBlocks.blockTestifiBucksGreenWall), 10);
        recipeGreenWall = RecipeUtil.lastReconstructorRecipe();

        IColorLensChanger changer = new ColorLensChangerByDyeMeta();
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Items.DYE, changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.WOOL), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_GLASS), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_HARDENED_CLAY), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.CARPET), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(InitBlocks.blockColoredLamp), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(InitBlocks.blockColoredLampOn), changer);

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
