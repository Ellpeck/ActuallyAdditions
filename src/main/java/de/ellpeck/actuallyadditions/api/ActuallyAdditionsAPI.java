/*
 * This file ("ActuallyAdditionsAPI.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.internal.IMethodHandler;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.lens.LensConversion;
import de.ellpeck.actuallyadditions.api.recipe.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ActuallyAdditionsAPI{

    public static final String MOD_ID = "actuallyadditions";
    public static final String API_ID = MOD_ID+"api";
    public static final String API_VERSION = "18";

    public static final List<CrusherRecipe> CRUSHER_RECIPES = new ArrayList<CrusherRecipe>();
    public static final List<BallOfFurReturn> BALL_OF_FUR_RETURN_ITEMS = new ArrayList<BallOfFurReturn>();
    public static final List<TreasureChestLoot> TREASURE_CHEST_LOOT = new ArrayList<TreasureChestLoot>();
    public static final List<LensConversionRecipe> RECONSTRUCTOR_LENS_CONVERSION_RECIPES = new ArrayList<LensConversionRecipe>();
    public static final Map<Item, IColorLensChanger> RECONSTRUCTOR_LENS_COLOR_CHANGERS = new HashMap<Item, IColorLensChanger>();
    public static final List<CoffeeIngredient> COFFEE_MACHINE_INGREDIENTS = new ArrayList<CoffeeIngredient>();
    public static final List<CompostRecipe> COMPOST_RECIPES = new ArrayList<CompostRecipe>();
    public static final List<IBookletEntry> BOOKLET_ENTRIES = new ArrayList<IBookletEntry>();
    public static final List<BookletPage> BOOKLET_PAGES_WITH_ITEM_DATA = new ArrayList<BookletPage>();
    /**
     * Use this to handle things that aren't based in the API itself
     * DO NOT CHANGE/OVERRIDE THIS!!
     * This is getting initialized in Actually Additions' PreInit phase
     */
    public static IMethodHandler methodHandler;
    //These are getting initialized in Actually Additions' PreInit phase
    //DO NOT CHANGE/OVERRIDE THESE!!
    public static IBookletEntry entryGettingStarted;
    public static IBookletEntry entryFunctionalNonRF;
    public static IBookletEntry entryFunctionalRF;
    public static IBookletEntry entryGeneratingRF;
    public static IBookletEntry entryItemsNonRF;
    public static IBookletEntry entryItemsRF;
    public static IBookletEntry entryMisc;
    public static IBookletEntry allAndSearch;

    //These are getting initialized in Actually Additions' PreInit phase
    //DO NOT CHANGE/OVERRIDE THESE!!
    public static LensConversion lensDefaultConversion;
    public static Lens lensDetonation;
    public static Lens lensDeath;
    public static Lens lensColor;
    public static Lens lensDisruption;
    public static Lens lensDisenchanting;

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
            CRUSHER_RECIPES.add(new CrusherRecipe(input, outputOne, outputOneAmount, outputTwo, outputTwoAmount, outputTwoChance));
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
        CRUSHER_RECIPES.add(new CrusherRecipe(input, outputOne, outputTwo, outputTwoChance));
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
            CRUSHER_RECIPES.add(new CrusherRecipe(input, outputOne, outputOneAmount));
        }
    }

    /**
     * Adds a new conversion recipe to the compost.
     * StackSize is regarded on both input and output and they can be different.
     *
     * @param input         The itemstack to be input into the compost
     * @param inputDisplay  The block to display when there is input in the compost
     * @param output        The itemstack to be output from the compost once conversion finishes
     * @param outputDisplay The block to display when there is output in the compost
     */
    public static void addCompostRecipe(ItemStack input, Block inputDisplay, ItemStack output, Block outputDisplay){
        COMPOST_RECIPES.add(new CompostRecipe(input, inputDisplay, output, outputDisplay));
    }

    /**
     * Adds an item to the list of possible items to be returned when right-clicking a Ball Of Fur
     *
     * @param stack  The ItemStack to be returned
     * @param chance The chance (this is from WeightedRandom.Item)
     */
    public static void addBallOfFurReturnItem(ItemStack stack, int chance){
        BALL_OF_FUR_RETURN_ITEMS.add(new BallOfFurReturn(stack, chance));
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
        TREASURE_CHEST_LOOT.add(new TreasureChestLoot(stack, chance, minAmount, maxAmount));
    }

    /**
     * Adds a recipe to the Atomic Reconstructor conversion lenses
     * StackSizes can only be 1 and greater ones will be ignored
     *
     * @param input     The input as an ItemStack
     * @param output    The output as an ItemStack
     * @param energyUse The amount of RF used per conversion
     * @param type      The type of lens used for the conversion. To use the default type, use method below.
     *                  Note how this always has to be the same instance of the lens type that the item also has for it to work!
     */
    public static void addReconstructorLensConversionRecipe(ItemStack input, ItemStack output, int energyUse, LensConversion type){
        RECONSTRUCTOR_LENS_CONVERSION_RECIPES.add(new LensConversionRecipe(input, output, energyUse, type));
    }

    public static void addReconstructorLensConversionRecipe(ItemStack input, ItemStack output, int energyUse){
        addReconstructorLensConversionRecipe(input, output, energyUse, lensDefaultConversion);
    }

    /**
     * Adds a recipe to the Atomic Reconstructor conversion lenses
     *
     * @param input     The input's OreDictionary name
     * @param output    The output's OreDictionary name
     * @param energyUse The amount of RF used per conversion
     * @param type      The type of lens used for the conversion. To use the default type, use method below
     *                  Note how this always has to be the same instance of the lens type that the item also has for it to work!
     */
    public static void addReconstructorLensConversionRecipe(String input, String output, int energyUse, LensConversion type){
        RECONSTRUCTOR_LENS_CONVERSION_RECIPES.add(new LensConversionRecipe(input, output, energyUse, type));
    }

    public static void addReconstructorLensConversionRecipe(String input, String output, int energyUse){
        addReconstructorLensConversionRecipe(input, output, energyUse, lensDefaultConversion);
    }

    /**
     * Adds an item and the way it is modified to the Atomic Reconstructor's color lens.
     * This also works for blocks, but they have to be in their item form.
     * The way it is modified is an instance of IColorLensChanger. When modifying the item,
     * its modifyItem() method will be called with a stack containing the item.
     *
     * @param item    The item (or block's item) to add
     * @param changer The change mechanism
     */
    public static void addReconstructorLensColorChangeItem(Item item, IColorLensChanger changer){
        RECONSTRUCTOR_LENS_COLOR_CHANGERS.put(item, changer);
    }

    /**
     * Adds an ingredient to the Coffee Machine ingredient list
     *
     * @param ingredient The ingredient to add
     */
    public static void addCoffeeMachineIngredient(CoffeeIngredient ingredient){
        COFFEE_MACHINE_INGREDIENTS.add(ingredient);
    }

    /**
     * Adds a booklet entry to the list of entries
     *
     * @param entry The entry to add
     */
    public static void addBookletEntry(IBookletEntry entry){
        BOOKLET_ENTRIES.add(entry);
    }

    /**
     * Adds a page to the pages with ItemStack data
     * This should be done with every page that uses getItemStacksForPage()
     *
     * @param page The page to add
     */
    public static void addPageWithItemStackData(BookletPage page){
        BOOKLET_PAGES_WITH_ITEM_DATA.add(page);
    }
}
