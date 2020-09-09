package de.ellpeck.actuallyadditions.common.jei.crusher;

import java.util.Arrays;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;

public class CrusherRecipeCategory implements IRecipeCategory<CrusherRecipeWrapper> {

    public static final String NAME = "actuallyadditions.crushing";

    private final IDrawable background;

    public CrusherRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("gui_grinder"), 60, 13, 56, 79);
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
    public void setRecipe(IRecipeLayout recipeLayout, CrusherRecipeWrapper wrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 19, 7);
        recipeLayout.getItemStacks().set(0, Arrays.asList(wrapper.theRecipe.getInput().getMatchingStacks()));

        recipeLayout.getItemStacks().init(1, false, 7, 55);
        recipeLayout.getItemStacks().set(1, wrapper.theRecipe.getOutputOne());

        if (StackUtil.isValid(wrapper.theRecipe.getOutputTwo())) {
            recipeLayout.getItemStacks().init(2, false, 31, 55);
            recipeLayout.getItemStacks().set(2, wrapper.theRecipe.getOutputTwo());
        }
    }
}
