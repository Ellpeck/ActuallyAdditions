/*
 * This file ("AtomicReconstructorRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.booklet.BookletUtils;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.items.lens.LensNoneRecipeHandler;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NEIReconstructorRecipe extends TemplateRecipeHandler implements INEIRecipeHandler{

    public static final String NAME = "actuallyadditions.reconstructor";

    public NEIReconstructorRecipe(){
        super();
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockAtomicReconstructor));
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+NAME+".name");
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(31+32, 18, 22, 16), NAME));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(NAME) && getClass() == NEIReconstructorRecipe.class){
            ArrayList<LensNoneRecipeHandler.Recipe> recipes = LensNoneRecipeHandler.recipes;
            for(LensNoneRecipeHandler.Recipe recipe : recipes){
                arecipes.add(new CachedReconstructorRecipe(recipe));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        ArrayList<LensNoneRecipeHandler.Recipe> recipes = LensNoneRecipeHandler.recipes;
        for(LensNoneRecipeHandler.Recipe recipe : recipes){
            if(ItemUtil.contains(recipe.getOutputs(), result, true)){
                arecipes.add(new CachedReconstructorRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        ArrayList<LensNoneRecipeHandler.Recipe> recipes = LensNoneRecipeHandler.recipes;
        for(LensNoneRecipeHandler.Recipe recipe : recipes){
            if(ItemUtil.contains(recipe.getInputs(), ingredient, true)){
                CachedReconstructorRecipe theRecipe = new CachedReconstructorRecipe(recipe);
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.input), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID_LOWER+":textures/gui/guiNEIAtomicReconstructor.png";
    }

    @Override
    public String getOverlayIdentifier(){
        return NAME;
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return null;
    }

    @Override
    public void drawBackground(int recipeIndex){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(32, 0, 0, 0, 96, 60);
    }

    @Override
    public void drawForeground(int recipe){
        if(Minecraft.getMinecraft().currentScreen != null){
            BookletPage.renderItem(Minecraft.getMinecraft().currentScreen, new ItemStack(InitBlocks.blockAtomicReconstructor), 32+34, 19, 1.0F);
        }
    }

    @Override
    public int recipiesPerPage(){
        return 2;
    }

    public class CachedReconstructorRecipe extends CachedRecipe{

        public PositionedStack result;
        public PositionedStack input;

        public CachedReconstructorRecipe(LensNoneRecipeHandler.Recipe recipe){
            this.result = new PositionedStack(recipe.getOutputs(), 67+32, 19);
            this.input = new PositionedStack(recipe.getInputs(), 5+32, 19);
        }

        @Override
        public PositionedStack getResult(){
            return null;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.input));
        }

        @Override
        public java.util.List<PositionedStack> getOtherStacks(){
            ArrayList<PositionedStack> list = new ArrayList<PositionedStack>();
            list.addAll(this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.result)));
            return list;
        }
    }
}