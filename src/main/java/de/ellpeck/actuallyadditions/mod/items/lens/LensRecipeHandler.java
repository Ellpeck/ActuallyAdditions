/*
 * This file ("LensRecipeHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.ColorLensChangerByDyeMeta;
import de.ellpeck.actuallyadditions.api.recipe.IColorLensChanger;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
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

public final class LensRecipeHandler{

    public static final ArrayList<LensConversionRecipe> MAIN_PAGE_RECIPES = new ArrayList<LensConversionRecipe>();
    public static LensConversionRecipe recipeColorLens;
    public static LensConversionRecipe recipeSoulSand;
    public static LensConversionRecipe recipeGreenWall;
    public static LensConversionRecipe recipeWhiteWall;
    public static LensConversionRecipe recipeExplosionLens;
    public static LensConversionRecipe recipeDamageLens;
    public static LensConversionRecipe recipeLeather;
    public static LensConversionRecipe recipeNetherWart;
    public static LensConversionRecipe recipePrismarine;
    public static LensConversionRecipe recipeCrystallizedCanolaSeed;
    public static LensConversionRecipe recipeItemLaser;
    public static LensConversionRecipe recipeFluidLaser;

    public static void init(){
        //Crystal Blocks
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.REDSTONE_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_RESTONIA_BLOCK_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.LAPIS_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.LAPIS.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_PALIS_BLOCK_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.DIAMOND_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_DIAMATINE_BLOCK_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.EMERALD_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.EMERALD.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_EMERADIC_BLOCK_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.COAL_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_VOID_BLOCK_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_ENORI_BLOCK_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());

        //Crystal Items
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Items.REDSTONE), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_RESTONIA_CRYSTAL_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Items.DYE, 1, 4), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_PALIS_CRYSTAL_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Items.DIAMOND), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_DIAMATINE_CRYSTAL_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Items.EMERALD), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_EMERADIC_CRYSTAL_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Items.COAL), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_VOID_CRYSTAL_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_ENORI_CRYSTAL_COST.getValue());
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());

        //Lenses
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), new ItemStack(InitItems.itemColorLens),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_LENS_COST.getValue());
        recipeColorLens = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(InitItems.itemColorLens), new ItemStack(InitItems.itemExplosionLens),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_LENS_COST.getValue());
        recipeExplosionLens = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(InitItems.itemExplosionLens), new ItemStack(InitItems.itemDamageLens),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_LENS_COST.getValue());
        recipeDamageLens = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(InitItems.itemDamageLens), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_LENS_COST.getValue());

        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(InitBlocks.blockLaserRelay), new ItemStack(InitBlocks.blockLaserRelayFluids),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_LASER_RELAY_COST.getValue());
        recipeFluidLaser = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(InitBlocks.blockLaserRelayFluids), new ItemStack(InitBlocks.blockLaserRelayItem),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_LASER_RELAY_COST.getValue());
        recipeItemLaser = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(InitBlocks.blockLaserRelayItem), new ItemStack(InitBlocks.blockLaserRelay),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_LASER_RELAY_COST.getValue());

        //Misc
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.SAND), new ItemStack(Blocks.SOUL_SAND),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_SOUL_SAND_COST.getValue());
        recipeSoulSand = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_LEATHER_COST.getValue());
        recipeLeather = RecipeUtil.lastReconstructorRecipe();

        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Items.NETHER_WART),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_NETHER_WART_COST.getValue());
        recipeNetherWart = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Items.QUARTZ), new ItemStack(Items.PRISMARINE_SHARD),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_PRISMARINE_COST.getValue());
        recipePrismarine = RecipeUtil.lastReconstructorRecipe();

        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(InitItems.itemCanolaSeed), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CRYSTALLIZED_CANOLA_SEED.ordinal()),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_CRYSTALLIZED_CANOLA_COST.getValue());
        recipeCrystallizedCanolaSeed = RecipeUtil.lastReconstructorRecipe();

        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.QUARTZ_BLOCK), new ItemStack(InitBlocks.blockTestifiBucksWhiteWall),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_ETHETIC_QUARTZ_COST.getValue());
        recipeWhiteWall = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), new ItemStack(InitBlocks.blockTestifiBucksGreenWall),
                                                                  ConfigIntValues.ATOMIC_RECONSTRUCTOR_ETHETIC_GREEN_BLOCK_COST.getValue());
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

    public static ArrayList<LensConversionRecipe> getRecipesFor(ItemStack input){
        ArrayList<LensConversionRecipe> possibleRecipes = new ArrayList<LensConversionRecipe>();
        for(LensConversionRecipe recipe : ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES){
            if(ItemUtil.areItemsEqual(recipe.inputStack, input, true)){
                possibleRecipes.add(recipe);
            }
        }
        return possibleRecipes;
    }
}
