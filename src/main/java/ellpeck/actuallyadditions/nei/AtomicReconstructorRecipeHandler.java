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
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.recipe.ReconstructorRecipeHandler;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                arecipes.add(new CachedReconstructorRecipe(recipe.input, recipe.output, recipe.type.name));
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
            if(ItemUtil.contains(OreDictionary.getOres(recipe.output, false), result, true)){
                arecipes.add(new CachedReconstructorRecipe(recipe.input, recipe.output, recipe.type.name));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        ArrayList<ReconstructorRecipeHandler.Recipe> recipes = ReconstructorRecipeHandler.recipes;
        for(ReconstructorRecipeHandler.Recipe recipe : recipes){
            if(ItemUtil.contains(OreDictionary.getOres(recipe.input, false), ingredient, true)){
                CachedReconstructorRecipe theRecipe = new CachedReconstructorRecipe(recipe.input, recipe.output, recipe.type.name);
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

        CachedReconstructorRecipe cache = (CachedReconstructorRecipe)this.arecipes.get(recipe);
        if(cache != null && cache.lens != null){
            GuiDraw.drawString(cache.lens, 10, 45, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }

    }

    @Override
    public int recipiesPerPage(){
        return 2;
    }

    public class CachedReconstructorRecipe extends CachedRecipe{

        public PositionedStack result;
        public PositionedStack input;
        public String lens;

        public CachedReconstructorRecipe(String input, String result, String lens){
            List<ItemStack> outputs = OreDictionary.getOres(result, false);
            for(ItemStack ore : outputs){
                ore.stackSize = 1;
            }
            List<ItemStack> inputs = OreDictionary.getOres(input, false);
            for(ItemStack ore : inputs){
                ore.stackSize = 1;
            }

            this.result = new PositionedStack(outputs, 67+32, 19);
            this.input = new PositionedStack(inputs, 5+32, 19);
            this.lens = lens;
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