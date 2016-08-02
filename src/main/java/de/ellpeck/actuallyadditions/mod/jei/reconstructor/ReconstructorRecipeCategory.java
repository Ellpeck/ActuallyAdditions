/*
 * This file ("ReconstructorRecipeCategory.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.reconstructor;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ReconstructorRecipeCategory implements IRecipeCategory{

    public static final String NAME = "actuallyadditions.reconstructor";

    private static final ItemStack RECONSTRUCTOR = new ItemStack(InitBlocks.blockAtomicReconstructor);
    private final IDrawable background;

    public ReconstructorRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(AssetUtil.getGuiLocation("guiNEIAtomicReconstructor"), 0, 0, 96, 60);
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
        AssetUtil.renderStackToGui(RECONSTRUCTOR, 34, 19, 1.0F);
    }

    @Override
    public void drawAnimations(Minecraft minecraft){

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper){
        if(recipeWrapper instanceof ReconstructorRecipeWrapper){
            ReconstructorRecipeWrapper wrapper = (ReconstructorRecipeWrapper)recipeWrapper;

            recipeLayout.getItemStacks().init(0, true, 4, 18);
            recipeLayout.getItemStacks().set(0, RecipeUtil.getConversionLensInputs(wrapper.theRecipe));

            recipeLayout.getItemStacks().init(1, true, 66, 18);
            recipeLayout.getItemStacks().set(1, RecipeUtil.getConversionLensOutputs(wrapper.theRecipe));

        }
    }
}
