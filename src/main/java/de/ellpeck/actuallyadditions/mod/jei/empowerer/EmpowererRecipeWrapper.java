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

import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

public class EmpowererRecipeWrapper extends RecipeWrapperWithButton {

    public final EmpowererRecipe theRecipe;

    public EmpowererRecipeWrapper(EmpowererRecipe recipe) {
        this.theRecipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputs = new ArrayList<>();
        for (ItemStack s : this.theRecipe.getInput().getMatchingStacks())
            inputs.add(s);
        for (ItemStack s : this.theRecipe.getStandOne().getMatchingStacks())
            inputs.add(s);
        for (ItemStack s : this.theRecipe.getStandTwo().getMatchingStacks())
            inputs.add(s);
        for (ItemStack s : this.theRecipe.getStandThree().getMatchingStacks())
            inputs.add(s);
        for (ItemStack s : this.theRecipe.getStandFour().getMatchingStacks())
            inputs.add(s);

        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, this.theRecipe.getOutput());
    }

    @Override
    public int getButtonX() {
        return 2;
    }

    @Override
    public int getButtonY() {
        return 2;
    }

    @Override
    public IBookletPage getPage() {
        return BookletUtils.findFirstPageForStack(new ItemStack(InitBlocks.blockEmpowerer));
    }
}
