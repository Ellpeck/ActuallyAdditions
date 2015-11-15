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
import ellpeck.actuallyadditions.recipe.ReconstructorRecipeHandler;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class AtomicReconstructorRecipeHandler extends TemplateRecipeHandler implements INeiRecipeHandler{

    public static final String NAME = "actuallyadditions.reconstructor";

    public AtomicReconstructorRecipeHandler(){
        super();
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public ItemStack getStackForInfo(int page){
        return new ItemStack(InitBlocks.blockAtomicReconstructor);
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
        if(outputId.equals(NAME) && getClass() == AtomicReconstructorRecipeHandler.class){
            ArrayList<ReconstructorRecipeHandler.Recipe> recipes = ReconstructorRecipeHandler.recipes;
            for(ReconstructorRecipeHandler.Recipe recipe : recipes){
                arecipes.add(new CachedReconstructorRecipe(recipe.input, recipe.output));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        ArrayList<ReconstructorRecipeHandler.Recipe> recipes = ReconstructorRecipeHandler.recipes;
        for(ReconstructorRecipeHandler.Recipe recipe : recipes){
            if(ItemUtil.contains(OreDictionary.getOres(recipe.output), result, true)){
                arecipes.add(new CachedReconstructorRecipe(recipe.input, recipe.output));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        ArrayList<ReconstructorRecipeHandler.Recipe> recipes = ReconstructorRecipeHandler.recipes;
        for(ReconstructorRecipeHandler.Recipe recipe : recipes){
            if(ItemUtil.contains(OreDictionary.getOres(recipe.input), ingredient, true)){
                CachedReconstructorRecipe theRecipe = new CachedReconstructorRecipe(recipe.input, recipe.output);
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.input), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID_LOWER+":textures/gui/guiNEISimple.png";
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
    public int recipiesPerPage(){
        return 2;
    }

    public class CachedReconstructorRecipe extends CachedRecipe{

        public PositionedStack result;
        public PositionedStack input;

        public CachedReconstructorRecipe(String input, String result){
            this.result = new PositionedStack(OreDictionary.getOres(result), 67+32, 19);
            this.input = new PositionedStack(OreDictionary.getOres(input), 5+32, 19);
        }

        @Override
        public PositionedStack getResult(){
            return result;
        }

        @Override
        public PositionedStack getIngredient(){
            return input;
        }
    }
}