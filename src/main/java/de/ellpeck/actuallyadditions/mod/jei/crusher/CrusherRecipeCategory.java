/*
 * This file ("CrusherRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.crusher;

import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class CrusherRecipeCategory implements IRecipeCategory{

    public static final String NAME = "actuallyadditions.crushing";

    private final IDrawable background;

    public CrusherRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("guiGrinder"), 60, 13, 56, 79);
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
        if(recipeWrapper instanceof CrusherRecipeWrapper){
            CrusherRecipeWrapper wrapper = (CrusherRecipeWrapper)recipeWrapper;

            recipeLayout.getItemStacks().init(0, true, 19, 7);
            recipeLayout.getItemStacks().set(0, wrapper.theRecipe.getRecipeInputs());

            recipeLayout.getItemStacks().init(1, true, 7, 55);
            recipeLayout.getItemStacks().set(1, wrapper.theRecipe.getRecipeOutputOnes());

            List<ItemStack> outputTwos = wrapper.theRecipe.getRecipeOutputTwos();
            if(outputTwos != null && !outputTwos.isEmpty()){
                recipeLayout.getItemStacks().init(2, true, 31, 55);
                recipeLayout.getItemStacks().set(2, outputTwos);
            }
        }
    }
}
