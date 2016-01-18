/*
 * This file ("JEIActuallyAdditionsPlugin.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.jei.booklet.BookletRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.booklet.BookletRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.reconstructor.ReconstructorRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.reconstructor.ReconstructorRecipeHandler;
import mezz.jei.api.*;

@JEIPlugin
public class JEIActuallyAdditionsPlugin implements IModPlugin{

    private IJeiHelpers helpers;

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers){
        this.helpers = jeiHelpers;
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry){

    }

    @Override
    public void register(IModRegistry registry){
        registry.addRecipeCategories(
                new BookletRecipeCategory(this.helpers.getGuiHelper()),
                new CoffeeMachineRecipeCategory(this.helpers.getGuiHelper()),
                new CrusherRecipeCategory(this.helpers.getGuiHelper()),
                new ReconstructorRecipeCategory(this.helpers.getGuiHelper())
        );

        registry.addRecipeHandlers(
                new BookletRecipeHandler(),
                new CoffeeMachineRecipeHandler(),
                new CrusherRecipeHandler(),
                new ReconstructorRecipeHandler()
        );

        registry.addRecipes(ActuallyAdditionsAPI.bookletPagesWithItemStackData);
        registry.addRecipes(ActuallyAdditionsAPI.coffeeMachineIngredients);
        registry.addRecipes(ActuallyAdditionsAPI.crusherRecipes);
        registry.addRecipes(ActuallyAdditionsAPI.reconstructorLensNoneRecipes);

        INbtIgnoreList ignoreList = this.helpers.getNbtIgnoreList();
        ignoreList.ignoreNbtTagNames(InitItems.itemDrill, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemTeleStaff, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemGrowthRing, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemMagnetRing, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemWaterRemovalRing, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemBattery, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemBatteryDouble, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemBatteryTriple, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemBatteryQuadruple, "Energy");
        ignoreList.ignoreNbtTagNames(InitItems.itemBatteryQuintuple, "Energy");
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry){

    }
}
