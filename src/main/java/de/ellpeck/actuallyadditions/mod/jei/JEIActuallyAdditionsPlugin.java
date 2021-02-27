/*
 * This file ("JEIActuallyAdditionsPlugin.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.recipe.*;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiGrinder;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.jei.booklet.BookletRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.booklet.BookletRecipeWrapper;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineRecipeWrapper;
import de.ellpeck.actuallyadditions.mod.jei.compost.CompostRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.compost.CompostRecipeWrapper;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherRecipeWrapper;
import de.ellpeck.actuallyadditions.mod.jei.empowerer.EmpowererRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.empowerer.EmpowererRecipeWrapper;
import de.ellpeck.actuallyadditions.mod.jei.reconstructor.ReconstructorRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.reconstructor.ReconstructorRecipeWrapper;
import de.ellpeck.actuallyadditions.mod.util.Util;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIActuallyAdditionsPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new CoffeeMachineRecipeCategory(helpers.getGuiHelper()), new CompostRecipeCategory(helpers.getGuiHelper()), new CrusherRecipeCategory(helpers.getGuiHelper()), new ReconstructorRecipeCategory(helpers.getGuiHelper()), new EmpowererRecipeCategory(helpers.getGuiHelper()), new BookletRecipeCategory(helpers.getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();

        registry.handleRecipes(IBookletPage.class, BookletRecipeWrapper::new, BookletRecipeCategory.NAME);
        registry.handleRecipes(CoffeeIngredient.class, CoffeeMachineRecipeWrapper::new, CoffeeMachineRecipeCategory.NAME);
        registry.handleRecipes(CrusherRecipe.class, CrusherRecipeWrapper::new, CrusherRecipeCategory.NAME);
        registry.handleRecipes(LensConversionRecipe.class, ReconstructorRecipeWrapper.FACTORY, ReconstructorRecipeCategory.NAME);
        registry.handleRecipes(EmpowererRecipe.class, EmpowererRecipeWrapper::new, EmpowererRecipeCategory.NAME);
        registry.handleRecipes(CompostRecipe.class, CompostRecipeWrapper::new, CompostRecipeCategory.NAME);

        registry.addRecipes(ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA, BookletRecipeCategory.NAME);
        registry.addRecipes(ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS, CoffeeMachineRecipeCategory.NAME);
        registry.addRecipes(ActuallyAdditionsAPI.CRUSHER_RECIPES, CrusherRecipeCategory.NAME);
        registry.addRecipes(ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES, ReconstructorRecipeCategory.NAME);
        registry.addRecipes(ActuallyAdditionsAPI.EMPOWERER_RECIPES, EmpowererRecipeCategory.NAME);
        registry.addRecipes(ActuallyAdditionsAPI.COMPOST_RECIPES, CompostRecipeCategory.NAME);

        registry.addRecipeClickArea(GuiCoffeeMachine.class, 53, 42, 22, 16, CoffeeMachineRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiGrinder.class, 80, 40, 24, 22, CrusherRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiGrinder.GuiGrinderDouble.class, 51, 40, 74, 22, CrusherRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiFurnaceDouble.class, 51, 40, 74, 22, VanillaRecipeCategoryUid.SMELTING);

        IIngredientBlacklist blacklist = helpers.getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(InitBlocks.blockRice.get()));
        blacklist.addIngredientToBlacklist(new ItemStack(InitBlocks.blockCanola.get()));
        blacklist.addIngredientToBlacklist(new ItemStack(InitBlocks.blockFlax.get()));
        blacklist.addIngredientToBlacklist(new ItemStack(InitBlocks.blockCoffee.get()));
        blacklist.addIngredientToBlacklist(new ItemStack(InitBlocks.blockWildPlant.get(), 1, Util.WILDCARD));
        blacklist.addIngredientToBlacklist(new ItemStack(InitBlocks.blockColoredLampOn.get(), 1, Util.WILDCARD));
        blacklist.addIngredientToBlacklist(new ItemStack(InitItems.itemMisc.get(), 1, TheMiscItems.YOUTUBE_ICON.ordinal()));

        registry.addRecipeCatalyst(new ItemStack(InitItems.itemCrafterOnAStick.get()), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockFurnaceDouble.get()), VanillaRecipeCategoryUid.SMELTING);
        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockGrinder.get()), CrusherRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockGrinderDouble.get()), CrusherRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockCoffeeMachine.get()), CoffeeMachineRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockAtomicReconstructor.get()), ReconstructorRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockEmpowerer.get()), EmpowererRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(InitItems.itemBooklet.get()), BookletRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(InitBlocks.blockCompost.get()), CompostRecipeCategory.NAME);
    }
}
