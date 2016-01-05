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

import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.BallOfFurReturn;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.api.recipe.TreasureChestLoot;
import de.ellpeck.actuallyadditions.api.recipe.coffee.CoffeeIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ActuallyAdditionsAPI{

    public static List<CrusherRecipe> crusherRecipes = new ArrayList<CrusherRecipe>();
    public static List<BallOfFurReturn> ballOfFurReturnItems = new ArrayList<BallOfFurReturn>();
    public static List<TreasureChestLoot> treasureChestLoot = new ArrayList<TreasureChestLoot>();
    public static List<Lens> reconstructorLenses = new ArrayList<Lens>();
    public static List<LensNoneRecipe> reconstructorLensNoneRecipes = new ArrayList<LensNoneRecipe>();
    public static List<CoffeeIngredient> coffeeMachineIngredients = new ArrayList<CoffeeIngredient>();

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
     * Adds an ingredient to the Coffee Machine ingredient list
     * @param ingredient The ingredient to add
     */
    public static void addCoffeeMachineIngredient(CoffeeIngredient ingredient){
        coffeeMachineIngredients.add(ingredient);
    }
}
