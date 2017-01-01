/*
 * This file ("EmpowererRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.empowerer;

import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;

public class EmpowererRecipeCategory extends BlankRecipeCategory<EmpowererRecipeWrapper>{

    public static final String NAME = "actuallyadditions.empowerer";

    private final IDrawable background;

    public EmpowererRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("gui_nei_empowerer"), 0, 0, 135, 80);
    }

    @Override
    public String getUid(){
        return NAME;
    }

    @Override
    public String getTitle(){
        return StringUtil.localize("container.nei."+NAME+".name");
    }

    @Override
    public IDrawable getBackground(){
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EmpowererRecipeWrapper wrapper, IIngredients ingredients){
        recipeLayout.getItemStacks().init(0, true, 31, 31);
        recipeLayout.getItemStacks().set(0, wrapper.theRecipe.input);

        recipeLayout.getItemStacks().init(1, true, 1, 31);
        recipeLayout.getItemStacks().set(1, wrapper.theRecipe.modifier1);

        recipeLayout.getItemStacks().init(2, true, 31, 1);
        recipeLayout.getItemStacks().set(2, wrapper.theRecipe.modifier2);

        recipeLayout.getItemStacks().init(3, true, 61, 31);
        recipeLayout.getItemStacks().set(3, wrapper.theRecipe.modifier3);

        recipeLayout.getItemStacks().init(4, true, 31, 61);
        recipeLayout.getItemStacks().set(4, wrapper.theRecipe.modifier4);

        recipeLayout.getItemStacks().init(5, false, 112, 31);
        recipeLayout.getItemStacks().set(5, wrapper.theRecipe.output);
    }
}
