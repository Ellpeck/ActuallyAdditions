/*
 * This file ("CrusherRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.crusher;

import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;

public class CrusherRecipeCategory extends BlankRecipeCategory<CrusherRecipeWrapper>{

    public static final String NAME = "actuallyadditions.crushing";

    private final IDrawable background;

    public CrusherRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("gui_grinder"), 60, 13, 56, 79);
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
    public void setRecipe(IRecipeLayout recipeLayout, CrusherRecipeWrapper wrapper, IIngredients ingredients){
        recipeLayout.getItemStacks().init(0, true, 19, 7);
        recipeLayout.getItemStacks().set(0, wrapper.theRecipe.inputStack);

        recipeLayout.getItemStacks().init(1, false, 7, 55);
        recipeLayout.getItemStacks().set(1, wrapper.theRecipe.outputOneStack);

        if(StackUtil.isValid(wrapper.theRecipe.outputTwoStack)){
            recipeLayout.getItemStacks().init(2, false, 31, 55);
            recipeLayout.getItemStacks().set(2, wrapper.theRecipe.outputTwoStack);
        }
    }
}
