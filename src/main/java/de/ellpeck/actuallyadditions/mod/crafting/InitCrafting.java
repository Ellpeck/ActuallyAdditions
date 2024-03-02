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
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.CactusFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.DefaultFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.MelonPumpkinFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.NetherWartFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer.ReedFarmerBehavior;

// TODO: [port] MOVE TO DATA_GENERATOR
@Deprecated
public final class InitCrafting {

    public static void init() {
        ActuallyAdditions.LOGGER.info("Initializing Crafting Recipes...");

        //        ItemCrafting.init();
        //        BlockCrafting.init();
        //        MiscCrafting.init();
        //        FoodCrafting.init();
        //        ToolCrafting.init();

        //        ActuallyAdditionsAPI.addCompostRecipe(Ingredient.fromStacks(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal())), Blocks.LEAVES.getDefaultState(), new ItemStack(InitItems.itemFertilizer), Blocks.DIRT.getDefaultState());
        //        ActuallyAdditionsAPI.addCompostRecipe(Ingredient.fromItems(InitItems.itemCanolaSeed), Blocks.DIRT.getDefaultState(), new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BIOMASS.ordinal()), Blocks.SOUL_SAND.getDefaultState());


        ActuallyAdditionsAPI.addFarmerBehavior(new DefaultFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new CactusFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new NetherWartFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new ReedFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new MelonPumpkinFarmerBehavior());
        //        ActuallyAdditionsAPI.addFarmerBehavior(new EnderlillyFarmerBehavior());
        //        ActuallyAdditionsAPI.addFarmerBehavior(new RedOrchidFarmerBehavior());

        //new RecipePotionRingCharging(new ResourceLocation(ActuallyAdditions.MODID, "potion_ring_charging"));
        //new RecipeBioMash(new ResourceLocation(ActuallyAdditions.MODID, "bio_mash"));
    }
}
