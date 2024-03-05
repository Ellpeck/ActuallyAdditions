/*
 * This file ("ActuallyAdditionsAPI.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api;

import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IMethodHandler;
import de.ellpeck.actuallyadditions.api.laser.ILaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.lens.LensConversion;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.api.recipe.WeightedOre;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.crafting.CoffeeIngredientRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.ColorChangeRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.FermentingRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.LaserRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.LiquidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.MiningLensRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.PressingRecipe;
import de.ellpeck.actuallyadditions.mod.crafting.SolidFuelRecipe;
import de.ellpeck.actuallyadditions.mod.items.lens.LensColor;
import de.ellpeck.actuallyadditions.mod.items.lens.LensDeath;
import de.ellpeck.actuallyadditions.mod.items.lens.LensDetonation;
import de.ellpeck.actuallyadditions.mod.items.lens.LensDisenchanting;
import de.ellpeck.actuallyadditions.mod.items.lens.LensKiller;
import de.ellpeck.actuallyadditions.mod.items.lens.LensMining;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public final class ActuallyAdditionsAPI {

    public static final String MOD_ID = "actuallyadditions";
    public static final String API_ID = MOD_ID + "api";
    public static final String API_VERSION = "34";

    public static final List<RecipeHolder<CrushingRecipe>> CRUSHER_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<EmpowererRecipe>> EMPOWERER_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<ColorChangeRecipe>> COLOR_CHANGE_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<SolidFuelRecipe>> SOLID_FUEL_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<LiquidFuelRecipe>> LIQUID_FUEL_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<PressingRecipe>> PRESSING_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<FermentingRecipe>> FERMENTING_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<LaserRecipe>> CONVERSION_LASER_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<MiningLensRecipe>> MINING_LENS_RECIPES = new ArrayList<>();

    /**
     * Farmer behaviors are sorted when first accessed, this will not be done until after loading, but do not add behaviors at runtime.
     */
    public static final List<IFarmerBehavior> FARMER_BEHAVIORS = new ArrayList<>();
    public static final List<RecipeHolder<CoffeeIngredientRecipe>> COFFEE_MACHINE_INGREDIENTS = new ArrayList<>();
    //    public static final List<CompostRecipe> COMPOST_RECIPES = new ArrayList<>();
    public static final List<IBookletEntry> BOOKLET_ENTRIES = new ArrayList<>();
    //This is added to automatically, you don't need to add anything to this list
    public static final List<IBookletChapter> ALL_CHAPTERS = new ArrayList<>();
    //This is added to automatically, you don't need to add anything to this list
    public static final List<IBookletPage> BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA = new ArrayList<>();
    @Deprecated
    public static final List<WeightedOre> STONE_ORES = new ArrayList<>();
    @Deprecated
    public static final List<WeightedOre> NETHERRACK_ORES = new ArrayList<>();

    /**
     * Use this to handle things that aren't based in the API itself
     * DO NOT CHANGE/OVERRIDE THIS!!
     * This is getting initialized in Actually Additions' PreInit phase
     */
    public static IMethodHandler methodHandler;

    /**
     * Use this to add, remove or get Laser Relay Connections and Networks
     * The network system is built in a way that doesn't need the individual
     * positions to be Laser Relays, it relies only on BlockPos
     * DO NOT CHANGE/OVERRIDE THIS!!
     * This is getting initialized in Actually Additions' PreInit phase
     */
    public static ILaserRelayConnectionHandler connectionHandler;

    //These are getting initialized in Actually Additions' PreInit phase
    //DO NOT CHANGE/OVERRIDE THESE!!
    public static IBookletEntry entryGettingStarted;
    public static IBookletEntry entryReconstruction;
    public static IBookletEntry entryLaserRelays;
    public static IBookletEntry entryFunctionalNonRF;
    public static IBookletEntry entryFunctionalRF;
    public static IBookletEntry entryGeneratingRF;
    public static IBookletEntry entryItemsNonRF;
    public static IBookletEntry entryItemsRF;
    public static IBookletEntry entryMisc;
    public static IBookletEntry entryUpdatesAndInfos;
    //This is added to automatically, you don't need to add anything to this entry
    public static IBookletEntry entryAllAndSearch;
    public static IBookletEntry entryTrials;

    //These are getting initialized in Actually Additions' PreInit phase
    //DO NOT CHANGE/OVERRIDE THESE!!
    public static final LensConversion lensDefaultConversion = new LensConversion();
    public static final Lens lensDetonation = new LensDetonation();
    public static final Lens lensDeath = new LensDeath();
    public static final Lens lensEvenMoarDeath = new LensKiller();
    public static final Lens lensColor = new LensColor();
    public static final Lens lensDisenchanting = new LensDisenchanting();
    public static final Lens lensMining = new LensMining();

    /**
     * Adds an ore with a specific weight to the list of ores that the lens of the miner will generate inside of stone.
     * Higher weight means higher occurence.
     *
     * @param oreName The ore's name
     * @param weight  The ore's weight
     */
    public static void addMiningLensStoneOre(String oreName, int weight) {
        STONE_ORES.add(new WeightedOre(oreName, weight));
    }

    /**
     * Adds an ore with a specific weight to the list of ores that the lens of the miner will generate inside of netherrack.
     * Higher weight means higher occurence.
     *
     * @param oreName The ore's name
     * @param weight  The ore's weight
     */
    public static void addMiningLensNetherOre(String oreName, int weight) {
        NETHERRACK_ORES.add(new WeightedOre(oreName, weight));
    }

    /**
     * Adds a Recipe to the Crusher Recipe Registry
     *
     * @param input           The input as an ItemStack
     * @param outputOne       The first stack as an ItemStack
     * @param outputTwo       The second stack as an ItemStack (can be ItemStack.EMPTY if there should be none)
     * @param outputTwoChance The chance of the second stack (0 won't occur at all, 100 will all the time)
     */
    public static void addCrusherRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int outputTwoChance) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, BuiltInRegistries.ITEM.getKey(input.getItem()).getPath() + "_crushing");
        CRUSHER_RECIPES.add(new RecipeHolder<>(id, new CrushingRecipe(Ingredient.of(input), outputOne, 1.0f, outputTwo.isEmpty()
            ? ItemStack.EMPTY
            : outputTwo, outputTwoChance)));
    }

    /**
     * Adds a Recipe to the Crusher Recipe Registry
     *
     * @param input           The input as an Ingredient
     * @param outputOne       The first stack as an ItemStack
     * @param outputTwo       The second stack as an ItemStack (can be ItemStack.EMPTY if there should be none)
     * @param outputTwoChance The chance of the second stack (0 won't occur at all, 100 will all the time)
     */
    public static void addCrusherRecipe(Ingredient input, ItemStack outputOne, ItemStack outputTwo, int outputTwoChance) {
        ResourceLocation id = new ResourceLocation(ActuallyAdditions.MODID, BuiltInRegistries.ITEM.getKey(input.getItems()[0].getItem()).getPath() + "_crushing");
        CRUSHER_RECIPES.add(new RecipeHolder<>(id, new CrushingRecipe(input, outputOne, 1.0f, outputTwo.isEmpty()
                ? ItemStack.EMPTY
                : outputTwo, outputTwoChance)));
    }

    /**
     * Adds multiple Recipes to the Crusher Recipe Registry
     * Use this if you want to add OreDictionary recipes easier
     *
     * @param inputs           The inputs as an ItemStack List, stacksizes are ignored
     * @param outputOnes       The first outputs as an ItemStack List, stacksizes are ignored
     * @param outputOneAmounts The amount of the first stack, will be equal for all entries in the list
     * @param outputTwos       The second outputs as a List (can be null or empty if there should be none)
     * @param outputTwoAmounts The amount of the second stack, will be equal for all entries in the list
     * @param outputTwoChance  The chance of the second stack (0 won't occur at all, 100 will all the time)
     */
    public static boolean addCrusherRecipes(List<ItemStack> inputs, List<ItemStack> outputOnes, int outputOneAmounts, List<ItemStack> outputTwos, int outputTwoAmounts, int outputTwoChance) {
        return methodHandler.addCrusherRecipes(inputs, outputOnes, outputOneAmounts, outputTwos, outputTwoAmounts, outputTwoChance);
    }

    //Same thing as above, but with ItemStack outputs.
    @Deprecated //Use Ingredient
    public static boolean addCrusherRecipes(List<ItemStack> inputs, ItemStack outputOne, int outputOneAmount, ItemStack outputTwo, int outputTwoAmount, int outputTwoChance) {
        return methodHandler.addCrusherRecipes(inputs, outputOne, outputOneAmount, outputTwo, outputTwoAmount, outputTwoChance);
    }

    public static void addEmpowererRecipe(ResourceLocation id, Ingredient input, ItemStack output, Ingredient modifier1, Ingredient modifier2, Ingredient modifier3, Ingredient modifier4, int energyPerStand, int time, int particleColor) {
        EmpowererRecipe recipe = new EmpowererRecipe(output, input, NonNullList.of(modifier1, modifier2, modifier3, modifier4), energyPerStand, time, particleColor);
        EMPOWERER_RECIPES.add(new RecipeHolder<>(id, recipe));
    }

    /**
     * Adds a recipe to the Atomic Reconstructor conversion lenses
     * StackSizes can only be 1 and greater ones will be ignored
     *
     * @param input     The input as an ItemStack
     * @param output    The stack as an ItemStack
     * @param energyUse The amount of RF used per conversion
     * @param type      The type of lens used for the conversion. To use the default type, use method below.
     *                  Note how this always has to be the same instance of the lens type that the item also has for it to work!
     */
    @Deprecated
    public static void addReconstructorLensConversionRecipe(ItemStack input, ItemStack output, int energyUse, LensConversion type) {
        //RECONSTRUCTOR_LENS_CONVERSION_RECIPES.add(new LensConversionRecipe(input, stack, energyUse, type));
    }

    @Deprecated
    public static void addReconstructorLensConversionRecipe(ItemStack input, ItemStack output, int energyUse) {
        //addReconstructorLensConversionRecipe(input, stack, energyUse, lensDefaultConversion);
    }

    /**
     * Adds a recipe to the Atomic Reconstructor conversion lenses
     * StackSizes can only be 1 and greater ones will be ignored
     *
     * @param input     The input as an ItemStack
     * @param output    The stack as an ItemStack
     * @param energyUse The amount of RF used per conversion
     * @param type      The type of lens used for the conversion. To use the default type, use method below.
     *                  Note how this always has to be the same instance of the lens type that the item also has for it to work!
     */
    public static void addReconstructorLensConversionRecipe(Ingredient input, ItemStack output, int energyUse, LensConversion type) {
        //RECONSTRUCTOR_LENS_CONVERSION_RECIPES.add(new LensConversionRecipe(input, stack, energyUse, type));
    }

    public static void addReconstructorLensConversionRecipe(Ingredient input, ItemStack output, int energyUse) {
        //addReconstructorLensConversionRecipe(input, stack, energyUse, lensDefaultConversion);
    }
    /**
     * Adds an ingredient to the Coffee Machine ingredient list
     *
     * @param ingredient The ingredient to add
     */
    public static void addCoffeeMachineIngredient(CoffeeIngredient ingredient) {
//        COFFEE_MACHINE_INGREDIENTS.add(ingredient);
    }

    /**
     * Adds a booklet entry to the list of entries
     *
     * @param entry The entry to add
     */
    public static void addBookletEntry(IBookletEntry entry) {
        BOOKLET_ENTRIES.add(entry);
    }

    /**
     * Adds a new farmer behavior to the Farmer
     *
     * @param behavior The behavior to add
     */
    public static void addFarmerBehavior(IFarmerBehavior behavior) {
        FARMER_BEHAVIORS.add(behavior);
    }
}
