/*
 * This file ("CompostRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.compost;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;

public class CompostRecipeCategory implements IRecipeCategory<CompostRecipeWrapper>{

    public static final String NAME = "actuallyadditions.compost";

    private final IDrawable background;

    public CompostRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("gui_nei_simple"), 0, 0, 96, 60);
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
    public String getModName(){
        return ActuallyAdditions.NAME;
    }

    @Override
    public IDrawable getBackground(){
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CompostRecipeWrapper wrapper, IIngredients ingredients){
        recipeLayout.getItemStacks().init(0, true, 4, 18);
        recipeLayout.getItemStacks().set(0, wrapper.theRecipe.input);

        recipeLayout.getItemStacks().init(1, false, 66, 18);
        recipeLayout.getItemStacks().set(1, wrapper.theRecipe.output);
    }
}
