/*
 * This file ("BookletRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.booklet;

import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

import java.util.Arrays;

public class BookletRecipeCategory implements IRecipeCategory{

    public static final String NAME = "actuallyadditions.booklet";

    private final IDrawable background;

    public BookletRecipeCategory(IGuiHelper helper){
        this.background = helper.createBlankDrawable(160, 100);
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
    public void drawExtras(Minecraft minecraft){

    }

    @Override
    public void drawAnimations(Minecraft minecraft){

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper){

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients){
        if(recipeWrapper instanceof BookletRecipeWrapper){
            BookletRecipeWrapper wrapper = (BookletRecipeWrapper)recipeWrapper;
            recipeLayout.getItemStacks().init(0, true, 70, -4);
            recipeLayout.getItemStacks().set(0, Arrays.asList(wrapper.thePage.getItemStacksForPage()));
        }
    }
}
