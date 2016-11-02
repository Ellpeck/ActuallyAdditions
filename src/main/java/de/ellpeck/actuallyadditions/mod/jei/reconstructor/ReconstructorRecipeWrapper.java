/*
 * This file ("ReconstructorRecipeWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.reconstructor;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReconstructorRecipeWrapper extends RecipeWrapperWithButton{

    public final LensConversionRecipe theRecipe;

    public ReconstructorRecipeWrapper(LensConversionRecipe recipe){
        this.theRecipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients){

    }

    @Override
    public List getInputs(){
        return Collections.singletonList(this.theRecipe.inputStack);
    }

    @Override
    public List getOutputs(){
        return Collections.singletonList(this.theRecipe.outputStack);
    }

    @Override
    public List<FluidStack> getFluidInputs(){
        return new ArrayList<FluidStack>();
    }

    @Override
    public List<FluidStack> getFluidOutputs(){
        return new ArrayList<FluidStack>();
    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight){

    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY){
        minecraft.fontRendererObj.drawString(this.theRecipe.energyUse+" RF", 55, 0, 0xFFFFFF, true);
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
    }

    @Override
    public int getButtonX(){
        return 3;
    }

    @Override
    public int getButtonY(){
        return 40;
    }

    @Override
    public BookletPage getPage(){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockAtomicReconstructor));
    }
}
