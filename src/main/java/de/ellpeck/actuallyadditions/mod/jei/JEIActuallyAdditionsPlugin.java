/*
 * This file ("JEIActuallyAdditionsPlugin.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCrafter;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiGrinder;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.jei.booklet.BookletRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.booklet.BookletRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.coffee.CoffeeMachineRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.crusher.CrusherRecipeHandler;
import de.ellpeck.actuallyadditions.mod.jei.reconstructor.ReconstructorRecipeCategory;
import de.ellpeck.actuallyadditions.mod.jei.reconstructor.ReconstructorRecipeHandler;
import de.ellpeck.actuallyadditions.mod.nei.NEIBookletRecipe;
import de.ellpeck.actuallyadditions.mod.nei.NEICoffeeMachineRecipe;
import de.ellpeck.actuallyadditions.mod.nei.NEIReconstructorRecipe;
import de.ellpeck.actuallyadditions.mod.util.Util;
import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class JEIActuallyAdditionsPlugin implements IModPlugin{

    @Override
    public void register(@Nonnull IModRegistry registry){
        IJeiHelpers helpers = registry.getJeiHelpers();

        registry.addRecipeCategories(
                new BookletRecipeCategory(helpers.getGuiHelper()),
                new CoffeeMachineRecipeCategory(helpers.getGuiHelper()),
                new CrusherRecipeCategory(helpers.getGuiHelper()),
                new ReconstructorRecipeCategory(helpers.getGuiHelper())
        );

        registry.addRecipeHandlers(
                new BookletRecipeHandler(),
                new CoffeeMachineRecipeHandler(),
                new CrusherRecipeHandler(),
                new ReconstructorRecipeHandler()
        );

        registry.addRecipes(ActuallyAdditionsAPI.BOOKLET_PAGES_WITH_ITEM_DATA);
        registry.addRecipes(ActuallyAdditionsAPI.COFFEE_MACHINE_INGREDIENTS);
        registry.addRecipes(ActuallyAdditionsAPI.CRUSHER_RECIPES);
        registry.addRecipes(ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_CONVERSION_RECIPES);

        registry.addRecipeClickArea(GuiCoffeeMachine.class, 53, 42, 22, 16, NEICoffeeMachineRecipe.NAME);
        registry.addRecipeClickArea(GuiGrinder.class, 80, 40, 24, 22, CrusherRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiGrinder.GuiGrinderDouble.class, 51, 40, 74, 22, CrusherRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiFurnaceDouble.class, 51, 40, 74, 22, VanillaRecipeCategoryUid.SMELTING);

        INbtIgnoreList ignoreList = helpers.getNbtIgnoreList();
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

        IItemBlacklist blacklist = helpers.getItemBlacklist();
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockRice));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockCanola));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockFlax));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockCoffee));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockWildPlant, 1, Util.WILDCARD));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockColoredLampOn, 1, Util.WILDCARD));

        IRecipeTransferRegistry transfer = registry.getRecipeTransferRegistry();
        transfer.addRecipeTransferHandler(ContainerCrafter.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);

        registry.addRecipeCategoryCraftingItem(new ItemStack(InitItems.itemCrafterOnAStick), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockFurnaceDouble), VanillaRecipeCategoryUid.SMELTING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockGrinder), CrusherRecipeCategory.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockGrinderDouble), CrusherRecipeCategory.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockCoffeeMachine), NEICoffeeMachineRecipe.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitBlocks.blockAtomicReconstructor), NEIReconstructorRecipe.NAME);
        registry.addRecipeCategoryCraftingItem(new ItemStack(InitItems.itemBooklet), NEIBookletRecipe.NAME);
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime){

    }
}
