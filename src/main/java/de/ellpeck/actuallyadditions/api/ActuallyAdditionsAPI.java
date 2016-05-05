/*
 * This file ("ActuallyAdditionsAPI.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.recipe.*;
import de.ellpeck.actuallyadditions.api.recipe.coffee.CoffeeIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActuallyAdditionsAPI{

    public static final String MOD_ID = "actuallyadditions";
    public static final String API_ID = MOD_ID+"api";
    public static final String API_VERSION = "9";

    public static List<CrusherRecipe> crusherRecipes = new ArrayList<CrusherRecipe>();
    public static List<BallOfFurReturn> ballOfFurReturnItems = new ArrayList<BallOfFurReturn>();
    public static List<TreasureChestLoot> treasureChestLoot = new ArrayList<TreasureChestLoot>();
    public static List<LensNoneRecipe> reconstructorLensNoneRecipes = new ArrayList<LensNoneRecipe>();
    public static Map<Item, IColorLensChanger> reconstructorLensColorChangers = new HashMap<Item, IColorLensChanger>();
    public static List<CoffeeIngredient> coffeeMachineIngredients = new ArrayList<CoffeeIngredient>();

    public static List<IBookletEntry> bookletEntries = new ArrayList<IBookletEntry>();
    public static List<BookletPage> bookletPagesWithItemStackData = new ArrayList<BookletPage>();

    //These are getting initlized in Actually Additions' PreInit phase
    public static IBookletEntry entryGettingStarted;
    public static IBookletEntry entryFunctionalNonRF;
    public static IBookletEntry entryFunctionalRF;
    public static IBookletEntry entryGeneratingRF;
    public static IBookletEntry entryItemsNonRF;
    public static IBookletEntry entryItemsRF;
    public static IBookletEntry entryMisc;
    public static IBookletEntry allAndSearch;

    /**
     * Adds a Recipe to the Crusher Recipe Registry
     * The second output will be nothing
     *
     * @param input           The input's OreDictionary name
     * @param outputOne       The first output's OreDictionary name
     * @param outputOneAmount The amount of the first output
     */
    public static void addCrusherRecipe(String input, String outputOne, int outputOneAmount){
        addCrusherRecipe(input, outputOne, outputOneAmount, "", 0, 0);
    }

    /**
     * Adds a Recipe to the Crusher Recipe Registry
     *
     * @param input           The input's OreDictionary name
     * @param outputOne       The first output's OreDictionary name
     * @param outputOneAmount The amount of the first output
     * @param outputTwo       The second output's OreDictionary name
     * @param outputTwoAmount The amount of the second output
     * @param outputTwoChance The chance of the second output (0 won't occur at all, 100 will all the time)
     */
    public static void addCrusherRecipe(String input, String outputOne, int outputOneAmount, String outputTwo, int outputTwoAmount, int outputTwoChance){
        if(!OreDictionary.getOres(input, false).isEmpty() && !OreDictionary.getOres(outputOne, false).isEmpty() && (outputTwo == null || outputTwo.isEmpty() || !OreDictionary.getOres(outputTwo, false).isEmpty())){
            crusherRecipes.add(new CrusherRecipe(input, outputOne, outputOneAmount, outputTwo, outputTwoAmount, outputTwoChance));
        }
    }

    /**
     * Adds a Recipe to the Crusher Recipe Registry
     * The second output will be nothing
     *
     * @param input     The input as an ItemStack
     * @param outputOne The first output as an ItemStack
     */
    public static void addCrusherRecipe(ItemStack input, ItemStack outputOne){
        addCrusherRecipe(input, outputOne, null, 0);
    }

    /**
     * Adds a Recipe to the Crusher Recipe Registry
     * The second output will be nothing
     *
     * @param input           The input as an ItemStack
     * @param outputOne       The first output as an ItemStack
     * @param outputTwo       The second output as an ItemStack
     * @param outputTwoChance The chance of the second output (0 won't occur at all, 100 will all the time)
     */
    public static void addCrusherRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int outputTwoChance){
        crusherRecipes.add(new CrusherRecipe(input, outputOne, outputTwo, outputTwoChance));
    }

    /**
     * Adds a Recipe to the Crusher Recipe Registry
     * The second output will be nothing
     *
     * @param input           The input as an ItemStack
     * @param outputOne       The first output's OreDictionary name
     * @param outputOneAmount The amount of the first output
     */
    public static void addCrusherRecipe(ItemStack input, String outputOne, int outputOneAmount){
        if(!OreDictionary.getOres(outputOne, false).isEmpty()){
            crusherRecipes.add(new CrusherRecipe(input, outputOne, outputOneAmount));
        }
    }

    /**
     * Adds an item to the list of possible items to be returned when right-clicking a Ball Of Fur
     *
     * @param stack  The ItemStack to be returned
     * @param chance The chance (this is from WeightedRandom.Item)
     */
    public static void addBallOfFurReturnItem(ItemStack stack, int chance){
        ActuallyAdditionsAPI.ballOfFurReturnItems.add(new BallOfFurReturn(stack, chance));
    }

    /**
     * Adds an item to the list of possible items to be returned when opening a Treasure Chest
     *
     * @param stack     The ItemStack to be returned, the stacksize is ignored
     * @param chance    The chance (this is from WeightedRandom.Item)
     * @param minAmount The minimum stacksize of the returned stack
     * @param maxAmount The maximum stacksize of the returned stack
     */
    public static void addTreasureChestLoot(ItemStack stack, int chance, int minAmount, int maxAmount){
        ActuallyAdditionsAPI.treasureChestLoot.add(new TreasureChestLoot(stack, chance, minAmount, maxAmount));
    }

    /**
     * Adds a recipe to the Atomic Reconstructor conversion without lens
     * StackSizes can only be 1 and greater ones will be ignored
     *
     * @param input     The input as an ItemStack
     * @param output    The output as an ItemStack
     * @param energyUse The amount of RF used per conversion
     */
    public static void addReconstructorLensNoneRecipe(ItemStack input, ItemStack output, int energyUse){
        reconstructorLensNoneRecipes.add(new LensNoneRecipe(input, output, energyUse));
    }

    /**
     * Adds a recipe to the Atomic Reconstructor conversion without lens
     *
     * @param input     The input's OreDictionary name
     * @param output    The output's OreDictionary name
     * @param energyUse The amount of RF used per conversion
     */
    public static void addReconstructorLensNoneRecipe(String input, String output, int energyUse){
        reconstructorLensNoneRecipes.add(new LensNoneRecipe(input, output, energyUse));
    }

    /**
     * Adds an item and the way it is modified to the Atomic Reconstructor's color lens.
     * This also works for blocks, but they have to be in their item form.
     * The way it is modified is an instance of IColorLensChanger. When modifying the item,
     * its modifyItem() method will be called with a stack containing the item.
     *
     * @param item The item (or block's item) to add
     * @param changer The change mechanism
     */
    public static void addReconstructorLensColorChangeItem(Item item, IColorLensChanger changer){
        reconstructorLensColorChangers.put(item, changer);
    }

    /**
     * Adds an ingredient to the Coffee Machine ingredient list
     *
     * @param ingredient The ingredient to add
     */
    public static void addCoffeeMachineIngredient(CoffeeIngredient ingredient){
        coffeeMachineIngredients.add(ingredient);
    }

    /**
     * Adds a booklet entry to the list of entries
     *
     * @param entry The entry to add
     */
    public static void addBookletEntry(IBookletEntry entry){
        bookletEntries.add(entry);
    }

    /**
     * Adds a page to the pages with ItemStack data
     * This should be done with every page that uses getItemStacksForPage()
     *
     * @param page The page to add
     */
    public static void addPageWithItemStackData(BookletPage page){
        bookletPagesWithItemStackData.add(page);
    }
}
