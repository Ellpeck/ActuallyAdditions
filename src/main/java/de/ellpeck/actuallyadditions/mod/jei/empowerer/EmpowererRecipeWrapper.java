/*
 * This file ("EmpowererRecipeWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.empowerer;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

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
    public int getButtonX(){
        return 2;
    }

    @Override
    public int getButtonY(){
        return 2;
    }

    @Override
    public IBookletPage getPage(){
        return BookletUtils.findFirstPageForStack(new ItemStack(InitBlocks.blockEmpowerer));
    }
}
