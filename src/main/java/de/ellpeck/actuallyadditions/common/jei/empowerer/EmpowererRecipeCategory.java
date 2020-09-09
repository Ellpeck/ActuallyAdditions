package de.ellpeck.actuallyadditions.jei.empowerer;

import java.util.Arrays;

import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;

public class EmpowererRecipeCategory implements IRecipeCategory<EmpowererRecipeWrapper> {

    public static final String NAME = "actuallyadditions.empowerer";

    private final IDrawable background;

    public EmpowererRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("gui_nei_empowerer"), 0, 0, 135, 80);
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
    public void setRecipe(IRecipeLayout recipeLayout, EmpowererRecipeWrapper wrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 31, 31);
        recipeLayout.getItemStacks().set(0, Arrays.asList(wrapper.theRecipe.getInput().getMatchingStacks()));

        recipeLayout.getItemStacks().init(1, true, 1, 31);
        recipeLayout.getItemStacks().set(1, Arrays.asList(wrapper.theRecipe.getStandOne().getMatchingStacks()));

        recipeLayout.getItemStacks().init(2, true, 31, 1);
        recipeLayout.getItemStacks().set(2, Arrays.asList(wrapper.theRecipe.getStandTwo().getMatchingStacks()));

        recipeLayout.getItemStacks().init(3, true, 61, 31);
        recipeLayout.getItemStacks().set(3, Arrays.asList(wrapper.theRecipe.getStandThree().getMatchingStacks()));

        recipeLayout.getItemStacks().init(4, true, 31, 61);
        recipeLayout.getItemStacks().set(4, Arrays.asList(wrapper.theRecipe.getStandFour().getMatchingStacks()));

        recipeLayout.getItemStacks().init(5, false, 112, 31);
        recipeLayout.getItemStacks().set(5, wrapper.theRecipe.getOutput());
    }
}
