/*
 * This file ("CrusherRecipeWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.crusher;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import de.ellpeck.actuallyadditions.mod.util.RecipeUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrusherRecipeWrapper extends RecipeWrapperWithButton implements IRecipeWrapper{

    public final CrusherRecipe theRecipe;

    public CrusherRecipeWrapper(CrusherRecipe recipe){
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
        List list = new ArrayList();
        list.add(this.theRecipe.outputOneStack);
        if(this.theRecipe.outputTwoStack != null){
            list.add(this.theRecipe.outputTwoStack);
        }
        return list;
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

        if(this.theRecipe.outputTwoStack != null){
            minecraft.fontRendererObj.drawString(this.theRecipe.outputTwoChance+"%", 60, 60, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
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
        return -5;
    }

    @Override
    public int getButtonY(){
        return 26;
    }

    @Override
    public BookletPage getPage(){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockGrinder));
    }
}
