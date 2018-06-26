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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntListValues;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.CactusFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.DefaultFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.MelonPumpkinFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.NetherWartFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.ReedFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.exu.EnderlillyFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.exu.RedOrchidFarmerBehavior;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public final class InitCrafting{

    public static void init(){
        ActuallyAdditions.LOGGER.info("Initializing Crafting Recipes...");

        ItemCrafting.init();
        BlockCrafting.init();
        MiscCrafting.init();
        FoodCrafting.init();
        ToolCrafting.init();

        ActuallyAdditionsAPI.addCompostRecipe(Ingredient.fromStacks(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal())), Blocks.LEAVES.getDefaultState(), new ItemStack(InitItems.itemFertilizer), Blocks.DIRT.getDefaultState());
        ActuallyAdditionsAPI.addCompostRecipe(Ingredient.fromItems(InitItems.itemCanolaSeed), Blocks.DIRT.getDefaultState(), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BIOMASS.ordinal()), Blocks.SOUL_SAND.getDefaultState());

        int[] power = ConfigIntListValues.OIL_POWER.getValue();
        int[] time = ConfigIntListValues.OIL_TIME.getValue();
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidCanolaOil.getName(), power[0], time[0]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidRefinedCanolaOil.getName(), power[1], time[1]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidCrystalOil.getName(), power[2], time[2]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidEmpoweredOil.getName(), power[3], time[3]);

        ActuallyAdditionsAPI.addFarmerBehavior(new DefaultFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new CactusFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new NetherWartFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new ReedFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new MelonPumpkinFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new EnderlillyFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new RedOrchidFarmerBehavior());

        new RecipePotionRingCharging(new ResourceLocation(ActuallyAdditions.MODID, "potion_ring_charging"));
        new RecipeBioMash(new ResourceLocation(ActuallyAdditions.MODID, "bio_mash"));
    }
}
