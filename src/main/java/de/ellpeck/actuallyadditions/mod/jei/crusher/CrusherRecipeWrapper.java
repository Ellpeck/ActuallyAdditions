/*
 * This file ("CrusherRecipeWrapper.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.crusher;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrusherRecipeWrapper extends RecipeWrapperWithButton implements IRecipeWrapper{

    public CrusherRecipe theRecipe;

    public CrusherRecipeWrapper(CrusherRecipe recipe){
        this.theRecipe = recipe;
    }

    @Override
    public List getInputs(){
        return this.theRecipe.getRecipeInputs();
    }

    @Override
    public List getOutputs(){
        List list = new ArrayList();
        list.addAll(this.theRecipe.getRecipeOutputOnes());

        List<ItemStack> outputTwos = this.theRecipe.getRecipeOutputTwos();
        if(outputTwos != null && !outputTwos.isEmpty()){
            list.addAll(outputTwos);
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
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY){
        this.updateButton(minecraft, mouseX, mouseY);

        List<ItemStack> outputTwos = this.theRecipe.getRecipeOutputTwos();
        if(outputTwos != null && !outputTwos.isEmpty()){
            minecraft.fontRendererObj.drawString(this.theRecipe.outputTwoChance+"%", 60, 60, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight){

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY){
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton){
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
