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
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
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
import de.ellpeck.actuallyadditions.mod.nei.NEICoffeeMachineRecipe;
import de.ellpeck.actuallyadditions.mod.util.Util;
import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIActuallyAdditionsPlugin implements IModPlugin{

    @Override
    public void register(IModRegistry registry){
        registry.addRecipeCategories(
                new BookletRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new CoffeeMachineRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new CrusherRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new ReconstructorRecipeCategory(registry.getJeiHelpers().getGuiHelper())
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

        registry.addRecipeClickArea(GuiCoffeeMachine.class, 53, 42, 22, 16, NEICoffeeMachineRecipe.NAME);
        registry.addRecipeClickArea(GuiGrinder.class, 80, 40, 24, 22, CrusherRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiGrinder.GuiGrinderDouble.class, 51, 40, 74, 22, CrusherRecipeCategory.NAME);
        registry.addRecipeClickArea(GuiFurnaceDouble.class, 51, 40, 74, 22, VanillaRecipeCategoryUid.SMELTING);

        INbtIgnoreList ignoreList = registry.getJeiHelpers().getNbtIgnoreList();
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

        IItemBlacklist blacklist = registry.getJeiHelpers().getItemBlacklist();
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockRice));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockCanola));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockFlax));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockCoffee));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockWildPlant, 1, Util.WILDCARD));
        blacklist.addItemToBlacklist(new ItemStack(InitBlocks.blockColoredLampOn, 1, Util.WILDCARD));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime){

    }
}
