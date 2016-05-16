/*
 * This file ("CoffeeMachineRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.coffee;

import de.ellpeck.actuallyadditions.mod.nei.NEICoffeeMachineRecipe;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class CoffeeMachineRecipeCategory implements IRecipeCategory{

    private IDrawable background;

    public CoffeeMachineRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("guiNEICoffeeMachine"), 0, 0, 126, 88);
    }

    @Nonnull
    @Override
    public String getUid(){
        return NEICoffeeMachineRecipe.NAME;
    }

    @Nonnull
    @Override
    public String getTitle(){
        return StringUtil.localize("container.nei."+NEICoffeeMachineRecipe.NAME+".name");
    }

    @Nonnull
    @Override
    public IDrawable getBackground(){
        return this.background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft){

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft){

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper){
        if(recipeWrapper instanceof CoffeeMachineRecipeWrapper){
            CoffeeMachineRecipeWrapper wrapper = (CoffeeMachineRecipeWrapper)recipeWrapper;

            recipeLayout.getItemStacks().init(0, true, 89, 20);
            recipeLayout.getItemStacks().set(0, wrapper.theIngredient.ingredient);

            recipeLayout.getItemStacks().init(1, true, 44, 38);
            recipeLayout.getItemStacks().set(1, wrapper.cup);

            recipeLayout.getItemStacks().init(2, true, 1, 38);
            recipeLayout.getItemStacks().set(2, wrapper.coffeeBeans);

            recipeLayout.getItemStacks().init(3, false, 44, 69);
            recipeLayout.getItemStacks().set(3, wrapper.theOutput);
        }
    }
}
