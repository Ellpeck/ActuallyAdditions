/*
 * This file ("EmpowererRecipeWrapper.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmpowererRecipeWrapper extends RecipeWrapperWithButton{

    public final EmpowererRecipe theRecipe;

    public EmpowererRecipeWrapper(EmpowererRecipe recipe){
        this.theRecipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients){
        ingredients.setInputs(ItemStack.class, Arrays.asList(this.theRecipe.input, this.theRecipe.modifier1, this.theRecipe.modifier2, this.theRecipe.modifier3, this.theRecipe.modifier4));
        ingredients.setOutput(ItemStack.class, this.theRecipe.output);
    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight){

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
