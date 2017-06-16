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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

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

        int[] power = ConfigIntListValues.OIL_POWER.getValue();
        int[] time = ConfigIntListValues.OIL_TIME.getValue();
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidCanolaOil.getName(), power[0], time[0]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidOil.getName(), power[1], time[1]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidCrystalOil.getName(), power[2], time[2]);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidEmpoweredOil.getName(), power[3], time[3]);

        ActuallyAdditionsAPI.addFarmerBehavior(new DefaultFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new CactusFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new NetherWartFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new ReedFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new MelonPumpkinFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new EnderlillyFarmerBehavior());
        ActuallyAdditionsAPI.addFarmerBehavior(new RedOrchidFarmerBehavior());

        RecipeSorter.register(ModUtil.MOD_ID+":recipeKeepDataShaped", RecipeKeepDataShaped.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
        RecipeSorter.register(ModUtil.MOD_ID+":recipeKeepDataShapeless", RecipeKeepDataShapeless.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new RecipePotionRingCharging());
        RecipeSorter.register(ModUtil.MOD_ID+":recipePotionRingCharging", RecipePotionRingCharging.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new RecipeBioMash());
        RecipeSorter.register(ModUtil.MOD_ID+":recipeBioMash", RecipeBioMash.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
    }

}
