/*
 * This file ("ReconstructorRecipeWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.empowerer;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmpowererRecipeWrapper extends RecipeWrapperWithButton implements IRecipeWrapper{

    public final EmpowererRecipe theRecipe;

    public EmpowererRecipeWrapper(EmpowererRecipe recipe){
        this.theRecipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients){

    }

    @Override
    public List getInputs(){
        return Arrays.asList(this.theRecipe.input, this.theRecipe.modifier1, this.theRecipe.modifier2, this.theRecipe.modifier3, this.theRecipe.modifier4);
    }

    @Override
    public List getOutputs(){
        return Collections.singletonList(this.theRecipe.output);
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
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY){
        this.updateButton(minecraft, mouseX, mouseY);
    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight){

    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY){
        return null;
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton){
        return this.handleClick(minecraft, mouseX, mouseY);
    }

    @Override
    public int getButtonX(){
        return 2;
    }

    @Override
    public int getButtonY(){
        return 2;
    }

    @Override
    public BookletPage getPage(){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockEmpowerer));
    }
}
