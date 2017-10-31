/*
 * This file ("CompostRecipeWrapper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.compost;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.recipe.CompostRecipe;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCompost;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class CompostRecipeWrapper extends RecipeWrapperWithButton{

    public final CompostRecipe theRecipe;

    public CompostRecipeWrapper(CompostRecipe recipe){
        this.theRecipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients){
        ingredients.setInput(ItemStack.class, this.theRecipe.input);
        ingredients.setOutput(ItemStack.class, this.theRecipe.output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY){
        int bake_time = TileEntityCompost.COMPOST_TIME_TICKS / 20;
        minecraft.fontRenderer.drawString(bake_time + "s", 28, 3, 0xFFFFFF, true);
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
    }

    @Override
    public int getButtonX(){
        return 65;
    }

    @Override
    public int getButtonY(){
        return 43;
    }

    @Override
    public IBookletPage getPage(){
        return BookletUtils.findFirstPageForStack(new ItemStack(InitBlocks.blockCompost));
    }
}
