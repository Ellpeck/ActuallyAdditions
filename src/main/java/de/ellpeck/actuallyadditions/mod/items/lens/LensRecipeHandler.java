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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.ColorLensChangerByDyeMeta;
import de.ellpeck.actuallyadditions.api.recipe.IColorLensChanger;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.recipe.ColorLensRotator;
import de.ellpeck.actuallyadditions.mod.recipe.EnchBookConversion;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public final class LensRecipeHandler {

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
    public static EnchBookConversion recipeEnchBook;

    public static void init() {
        //Crystal Blocks
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.REDSTONE_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.REDSTONE.ordinal()), 400);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.LAPIS_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.LAPIS.ordinal()), 400);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.DIAMOND_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.DIAMOND.ordinal()), 600);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.EMERALD_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.EMERALD.ordinal()), 1000);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.COAL_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.COAL.ordinal()), 600);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.IRON_BLOCK), new ItemStack(InitBlocks.blockCrystal, 1, TheCrystals.IRON.ordinal()), 800);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());

        //Crystal Items
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(Items.REDSTONE), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.REDSTONE.ordinal()), 40);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 4)), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.LAPIS.ordinal()), 40);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(Items.DIAMOND), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.DIAMOND.ordinal()), 60);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(Items.EMERALD), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.EMERALD.ordinal()), 100);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(Items.COAL), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.COAL.ordinal()), 60);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(Items.IRON_INGOT), new ItemStack(InitItems.itemCrystal, 1, TheCrystals.IRON.ordinal()), 80);
        MAIN_PAGE_RECIPES.add(RecipeUtil.lastReconstructorRecipe());

        //Lenses
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromStacks(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal())), new ItemStack(InitItems.itemColorLens), 5000);
        recipeColorLens = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(InitItems.itemColorLens), new ItemStack(InitItems.itemExplosionLens), 5000);
        recipeExplosionLens = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(InitItems.itemExplosionLens), new ItemStack(InitItems.itemDamageLens), 5000);
        recipeDamageLens = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(InitItems.itemDamageLens), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.LENS.ordinal()), 5000);

        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(InitBlocks.blockLaserRelay), new ItemStack(InitBlocks.blockLaserRelayFluids), 2000);
        recipeFluidLaser = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(InitBlocks.blockLaserRelayFluids), new ItemStack(InitBlocks.blockLaserRelayItem), 2000);
        recipeItemLaser = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(InitBlocks.blockLaserRelayItem), new ItemStack(InitBlocks.blockLaserRelay), 2000);

        //Misc
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.SAND), new ItemStack(Blocks.SOUL_SAND), 20000);
        recipeSoulSand = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER), 8000);
        recipeLeather = RecipeUtil.lastReconstructorRecipe();

        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.RED_MUSHROOM), new ItemStack(Items.NETHER_WART), 150000);
        recipeNetherWart = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(Items.QUARTZ), new ItemStack(Items.PRISMARINE_SHARD), 30000);
        recipePrismarine = RecipeUtil.lastReconstructorRecipe();

        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromItems(InitItems.itemCanolaSeed), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CRYSTALLIZED_CANOLA_SEED.ordinal()), 2000);
        recipeCrystallizedCanolaSeed = RecipeUtil.lastReconstructorRecipe();

        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(fromBlock(Blocks.QUARTZ_BLOCK), new ItemStack(InitBlocks.blockTestifiBucksWhiteWall), 10);
        recipeWhiteWall = RecipeUtil.lastReconstructorRecipe();
        ActuallyAdditionsAPI.addReconstructorLensConversionRecipe(Ingredient.fromStacks(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1)), new ItemStack(InitBlocks.blockTestifiBucksGreenWall), 10);
        recipeGreenWall = RecipeUtil.lastReconstructorRecipe();

        ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES.add(recipeEnchBook = new EnchBookConversion());

        IColorLensChanger changer = new ColorLensChangerByDyeMeta();
        if (ConfigBoolValues.COLOR_LENS_USES_OREDICT.isEnabled()) {
            initOredictDyeRotator();
        } else {
            ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Items.DYE, changer);
        }
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.WOOL), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_GLASS), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.STAINED_HARDENED_CLAY), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(Blocks.CARPET), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(InitBlocks.blockColoredLamp), changer);
        ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(Item.getItemFromBlock(InitBlocks.blockColoredLampOn), changer);
    }

    @Deprecated //Use lens-checking method below.
    public static List<LensConversionRecipe> getRecipesFor(ItemStack input) {
        List<LensConversionRecipe> possibleRecipes = new ArrayList<>();
        for (LensConversionRecipe recipe : ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES)
            if (recipe.getInput().apply(input)) possibleRecipes.add(recipe);
        return possibleRecipes;
    }

    @Nullable
    public static LensConversionRecipe findMatchingRecipe(ItemStack input, Lens lens) {
        for (LensConversionRecipe recipe : ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES)
            if (recipe.matches(input, lens)) return recipe;
        return null;
    }

    private static Ingredient fromBlock(Block b) {
        return Ingredient.fromItems(Item.getItemFromBlock(b));
    }

    private static void initOredictDyeRotator() {
        List<ItemStack> stacks = NonNullList.withSize(16, ItemStack.EMPTY);
        List<ItemStack> dyeItems = new ArrayList<>();
        String[] dyes = { "White", "Orange", "Magenta", "LightBlue", "Yellow", "Lime", "Pink", "Gray", "LightGray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
        for (int i = 0; i < dyes.length; i++) {
            List<ItemStack> ores = OreDictionary.getOres("dye" + dyes[i]);
            dyeItems.addAll(ores);
            stacks.set(i, ores.get(ores.size() - 1));
        }
        ColorLensRotator rotator = new ColorLensRotator(stacks);
        for (ItemStack s : dyeItems)
            ActuallyAdditionsAPI.addReconstructorLensColorChangeItem(s.getItem(), rotator);
    }
}
