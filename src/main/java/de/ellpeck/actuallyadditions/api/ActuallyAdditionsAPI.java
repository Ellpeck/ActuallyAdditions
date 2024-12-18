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
import de.ellpeck.actuallyadditions.mod.crafting.*;
import de.ellpeck.actuallyadditions.mod.items.lens.*;
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
