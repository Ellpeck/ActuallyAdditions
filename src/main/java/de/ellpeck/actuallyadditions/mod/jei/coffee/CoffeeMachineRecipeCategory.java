/*
 * This file ("CoffeeMachineRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.coffee;

import java.util.Arrays;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;

public class CoffeeMachineRecipeCategory implements IRecipeCategory<CoffeeMachineRecipeWrapper> {

    public static final String NAME = "actuallyadditions.coffee";

    private final IDrawable background;

    public CoffeeMachineRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("gui_nei_coffee_machine"), 0, 0, 126, 92);
    }

    @Override
    public String getUid() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return StringUtil.localize("container.nei." + NAME + ".name");
    }

    @Override
    public String getModName() {
        return ActuallyAdditions.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CoffeeMachineRecipeWrapper wrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 89, 20);
        recipeLayout.getItemStacks().set(0, Arrays.asList(wrapper.ingredient.getInput().getMatchingStacks()));

        recipeLayout.getItemStacks().init(1, true, 44, 38);
        recipeLayout.getItemStacks().set(1, wrapper.cup);

        recipeLayout.getItemStacks().init(2, true, 1, 38);
        recipeLayout.getItemStacks().set(2, Arrays.asList(TileEntityCoffeeMachine.COFFEE.getMatchingStacks()));

        recipeLayout.getItemStacks().init(3, false, 44, 69);
        recipeLayout.getItemStacks().set(3, wrapper.theOutput);
    }
}
