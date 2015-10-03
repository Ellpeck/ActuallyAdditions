/*
 * This file ("CrusherRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.inventory.gui.GuiGrinder;
import ellpeck.actuallyadditions.recipe.CrusherRecipeManualRegistry;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrusherRecipeHandler extends TemplateRecipeHandler implements INeiRecipeHandler{

    public CrusherRecipeHandler(){
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public ItemStack getStackForInfo(){
        return new ItemStack(InitBlocks.blockGrinder);
    }

    @Override
    public int recipiesPerPage(){
        return 1;
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(80, 40, 24, 22), this.getName()));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiGrinder.class;
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+this.getName()+".name");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(this.getName()) && (getClass() == CrusherRecipeHandler.class || getClass() == CrusherDoubleRecipeHandler.class)){
            ArrayList<CrusherRecipeManualRegistry.CrusherRecipe> recipes = CrusherRecipeManualRegistry.recipes;
            for(CrusherRecipeManualRegistry.CrusherRecipe recipe : recipes){
                arecipes.add(new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance, this));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        ArrayList<CrusherRecipeManualRegistry.CrusherRecipe> recipes = CrusherRecipeManualRegistry.recipes;
        for(CrusherRecipeManualRegistry.CrusherRecipe recipe : recipes){
            if(NEIServerUtils.areStacksSameType(recipe.firstOutput, result) || NEIServerUtils.areStacksSameType(recipe.secondOutput, result)){
                arecipes.add(new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance, this));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        ArrayList<CrusherRecipeManualRegistry.CrusherRecipe> recipes = CrusherRecipeManualRegistry.recipes;
        for(CrusherRecipeManualRegistry.CrusherRecipe recipe : recipes){
            if(NEIServerUtils.areStacksSameTypeCrafting(recipe.input, ingredient)){
                CachedCrush theRecipe = new CachedCrush(recipe.input, recipe.firstOutput, recipe.secondOutput, recipe.secondChance, this);
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.ingredient), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID_LOWER+":textures/gui/guiGrinder.png";
    }

    @Override
    public void drawBackground(int recipeIndex){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(60, 13, 60, 13, 56, 79);
    }

    @Override
    public void drawExtras(int recipe){
        drawProgressBar(80, 40, 176, 0, 24, 23, 48, 1);
        this.drawChanceString(118, 73, recipe);
    }

    @Override
    public String getOverlayIdentifier(){
        return this.getName();
    }

    protected String getName(){
        return "actuallyadditions."+(this instanceof CrusherDoubleRecipeHandler ? "crushingDouble" : "crushing");
    }

    protected void drawChanceString(int x, int y, int recipe){
        CachedCrush crush = (CachedCrush)this.arecipes.get(recipe);
        if(crush.resultTwo != null){
            int secondChance = crush.secondChance;
            String secondString = secondChance+"%";
            GuiDraw.drawString(secondString, x, y, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
    }

    public static class CrusherDoubleRecipeHandler extends CrusherRecipeHandler{

        @Override
        public ItemStack getStackForInfo(){
            return new ItemStack(InitBlocks.blockGrinderDouble);
        }

        @Override
        public Class<? extends GuiContainer> getGuiClass(){
            return GuiGrinder.GuiGrinderDouble.class;
        }

        @Override
        public void loadTransferRects(){
            transferRects.add(new RecipeTransferRect(new Rectangle(51, 40, 24, 22), this.getName()));
            transferRects.add(new RecipeTransferRect(new Rectangle(101, 40, 24, 22), this.getName()));
        }

        @Override
        public String getGuiTexture(){
            return ModUtil.MOD_ID_LOWER+":textures/gui/guiGrinderDouble.png";
        }

        @Override
        public void drawBackground(int recipeIndex){
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GuiDraw.changeTexture(getGuiTexture());
            GuiDraw.drawTexturedModalRect(33, 20, 33, 20, 110, 70);
        }

        @Override
        public void drawExtras(int recipe){
            drawProgressBar(51, 40, 176, 0, 24, 23, 48, 1);
            this.drawChanceString(66, 93, recipe);
        }
    }

    public class CachedCrush extends CachedRecipe{

        public PositionedStack ingredient;
        public PositionedStack resultOne;
        public PositionedStack resultTwo;
        public int secondChance;

        public CachedCrush(ItemStack in, ItemStack resultOne, ItemStack resultTwo, int secondChance, CrusherRecipeHandler handler){
            boolean isDouble = handler instanceof CrusherDoubleRecipeHandler;
            in.stackSize = 1;
            this.ingredient = new PositionedStack(in, isDouble ? 51 : 80, 21);
            this.resultOne = new PositionedStack(resultOne, isDouble ? 38 : 66, 69);
            if(resultTwo != null){
                this.resultTwo = new PositionedStack(resultTwo, isDouble ? 63 : 94, 69);
            }
            this.secondChance = secondChance;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return getCycledIngredients(cycleticks/48, Collections.singletonList(ingredient));
        }

        @Override
        public PositionedStack getResult(){
            return resultOne;
        }

        @Override
        public List<PositionedStack> getOtherStacks(){
            ArrayList<PositionedStack> list = new ArrayList<PositionedStack>();
            if(this.resultTwo != null){
                list.add(this.resultTwo);
            }
            return list;
        }
    }
}