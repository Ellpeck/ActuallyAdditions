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
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCrafter;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiGrinder;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.jei.booklet.BookletRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.booklet.BookletRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.empowerer.EmpowererRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.empowerer.EmpowererRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.reconstructor.ReconstructorRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.reconstructor.ReconstructorRecipeHandler;
import de.ellpeck.actuallyadditions.mod.util.Util;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIActuallyAdditionsPlugin implements IModPlugin{

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry){

    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry){

    }

    @Override
    public void register(IModRegistry registry){
        IJeiHelpers helpers = registry.getJeiHelpers();

        registry.addRecipeCategories(
                new CoffeeMachineRecipeCategory(helpers.getGuiHelper()),
                new CrusherRecipeCategory(helpers.getGuiHelper()),
                new ReconstructorRecipeCategory(helpers.getGuiHelper()),
                new EmpowererRecipeCategory(helpers.getGuiHelper()),
                new BookletRecipeCategory(helpers.getGuiHelper())
        );

        registry.addRecipeHandlers(
                new CoffeeMachineRecipeHandler(),
                new CrusherRecipeHandler(),
                new ReconstructorRecipeHandler(),
                new EmpowererRecipeHandler(),
                new BookletRecipeHandler()
        );

        registry.addRecipes(ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_OR_FLUID_DATA);
        registry.addRecipes(ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS);
        registry.addRecipes(ActuallyAdditionsAPI.CRUSHER_RECIPES);
        registry.addRecipes(ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES);
        registry.addRecipes(ActuallyAdditionsAPI.EMPOWERER_RECIPES);

        registry.addRecipeClickArea(GuiCoffeeMachine.class, 53, 42, 22, 16, CoffeeMachineRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiGrinder.class, 80, 40, 24, 22, CrusherRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiGrinder.GuiGrinderDouble.class, 51, 40, 74, 22, CrusherRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiFurnaceDouble.class, 51, 40, 74, 22, VanillaRecipeCategoryUid.SMELTING);

        IItemBlacklist blacklist = helpers.getItemBlacklist();
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockRice));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockCanola));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockFlax));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockCoffee));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockWildPlant, 1, Util.WILDCARD));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockColoredLampOn, 1, Util.WILDCARD));
        blacklist.addItemToBlacklist(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.YOUTUBE_ICON.ordinal()));

        IRecipeTransferRegistry transfer = registry.getRecipeTransferRegistry();
        transfer.addRecipeTransferHandler(ContainerCrafter.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);

        registry.addRecipeCategoryCraftingItem(new ItemStack(InitItems.itemCrafterOnAStick), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockFurnaceDouble), VanillaRecipeCategoryUid.SMELTING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockGrinder), CrusherRecipeCategory.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockGrinderDouble), CrusherRecipeCategory.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockCoffeeMachine), CoffeeMachineRecipeCategory.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockAtomicReconstructor), ReconstructorRecipeCategory.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockEmpowerer), EmpowererRecipeCategory.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitItems.itemBooklet), BookletRecipeCategory.NAME);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime){

    }
}
