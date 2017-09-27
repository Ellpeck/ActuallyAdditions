/*
 * This file ("InitCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntListValues;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.*;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.exu.EnderlillyFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.exu.RedOrchidFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class InitCrafting{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crafting Recipes...");

        ItemCrafting.init();
        BlockCrafting.init();
        MiscCrafting.init();
        FoodCrafting.init();
        ToolCrafting.init();

        ActuallyAdditionsAPI.addCompostRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal()), Blocks.LEAVES, new ItemStack(InitItems.itemFertilizer), Blocks.DIRT);
        ActuallyAdditionsAPI.addCompostRecipe(new ItemStack(InitItems.itemCanolaSeed), Blocks.DIRT, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BIOMASS.ordinal()), Blocks.SOUL_SAND);

        int[] energyProduction = ConfigIntListValues.OIL_GENERATOR_ENERGY_PRODUCTION.getValue();
        int[] productionTime = ConfigIntListValues.OIL_GENERATOR_BURN_TIME.getValue();
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidCanolaOil.getName(), energyProduction[0], productionTime[0]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidRefinedCanolaOil.getName(), energyProduction[1], productionTime[1]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidCrystalOil.getName(), energyProduction[2], productionTime[2]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidEmpoweredOil.getName(), energyProduction[3], productionTime[3]);

        ActuallyAdditionsAPI.addFarmerBehavior(new DefaultFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new CactusFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new NetherWartFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new ReedFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new MelonPumpkinFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new EnderlillyFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new RedOrchidFarmerBehavior());

        //RecipeSorter.register(ModUtil.MOD_ID+":recipeKeepDataShaped", RecipeKeepDataShaped.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
        //RecipeSorter.register(ModUtil.MOD_ID+":recipeKeepDataShapeless", RecipeKeepDataShapeless.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        new RecipePotionRingCharging(new ResourceLocation(ModUtil.MOD_ID, "potion_ring_charging"));
        //RecipeSorter.register(ModUtil.MOD_ID+":recipePotionRingCharging", RecipePotionRingCharging.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        new RecipeBioMash(new ResourceLocation(ModUtil.MOD_ID, "bio_mash"));
        //RecipeSorter.register(ModUtil.MOD_ID+":recipeBioMash", RecipeBioMash.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
    }
}
