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
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.inventory.gui.GuiGrinder;
import ellpeck.actuallyadditions.recipe.CrusherRecipeRegistry;
import ellpeck.actuallyadditions.util.ItemUtil;
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
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(80, 40, 24, 22), this.getName()));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(this.getName()) && (getClass() == CrusherRecipeHandler.class || getClass() == CrusherDoubleRecipeHandler.class)){
            for(CrusherRecipeRegistry.CrusherRecipe recipe : CrusherRecipeRegistry.recipes){
                arecipes.add(new CachedCrush(recipe.getRecipeInputs(), recipe.getRecipeOutputOnes(), recipe.getRecipeOutputTwos(), recipe.outputTwoChance, this));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        for(CrusherRecipeRegistry.CrusherRecipe recipe : CrusherRecipeRegistry.recipes){
            if(ItemUtil.contains(recipe.getRecipeOutputOnes(), result, true) || ItemUtil.contains(recipe.getRecipeOutputTwos(), result, true)){
                arecipes.add(new CachedCrush(recipe.getRecipeInputs(), recipe.getRecipeOutputOnes(), recipe.getRecipeOutputTwos(), recipe.outputTwoChance, this));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        for(CrusherRecipeRegistry.CrusherRecipe recipe : CrusherRecipeRegistry.recipes){
            if(ItemUtil.contains(recipe.getRecipeInputs(), ingredient, true)){
                CachedCrush theRecipe = new CachedCrush(recipe.getRecipeInputs(), recipe.getRecipeOutputOnes(), recipe.getRecipeOutputTwos(), recipe.outputTwoChance, this);
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
    public String getOverlayIdentifier(){
        return this.getName();
    }

    @Override
    public void drawExtras(int recipe){
        drawProgressBar(80, 40, 176, 0, 24, 23, 48, 1);
        this.drawChanceString(118, 73, recipe);
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiGrinder.class;
    }

    @Override
    public void drawBackground(int recipeIndex){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(60, 13, 60, 13, 56, 79);
    }

    @Override
    public int recipiesPerPage(){
        return 1;
    }

    protected void drawChanceString(int x, int y, int recipe){
        CachedCrush crush = (CachedCrush)this.arecipes.get(recipe);
        if(crush.resultTwo != null){
            int secondChance = crush.secondChance;
            String secondString = secondChance+"%";
            GuiDraw.drawString(secondString, x, y, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
    }

    protected String getName(){
        return "actuallyadditions."+(this instanceof CrusherDoubleRecipeHandler ? "crushingDouble" : "crushing");
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+this.getName()+".name");
    }

    public static class CrusherDoubleRecipeHandler extends CrusherRecipeHandler{

        @Override
        public ItemStack getStackForInfo(){
            return new ItemStack(InitBlocks.blockGrinderDouble);
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
        public void drawExtras(int recipe){
            drawProgressBar(51, 40, 176, 0, 24, 23, 48, 1);
            this.drawChanceString(66, 93, recipe);
        }

        @Override
        public Class<? extends GuiContainer> getGuiClass(){
            return GuiGrinder.GuiGrinderDouble.class;
        }

        @Override
        public void drawBackground(int recipeIndex){
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GuiDraw.changeTexture(getGuiTexture());
            GuiDraw.drawTexturedModalRect(33, 20, 33, 20, 110, 70);
        }
    }

    public class CachedCrush extends CachedRecipe{

        public PositionedStack ingredient;
        public PositionedStack resultOne;
        public PositionedStack resultTwo;
        public int secondChance;

        public CachedCrush(ArrayList<ItemStack> in, ArrayList<ItemStack> outOne, ArrayList<ItemStack> outTwo, int secondChance, CrusherRecipeHandler handler){
            boolean isDouble = handler instanceof CrusherDoubleRecipeHandler;
            this.ingredient = new PositionedStack(in, isDouble ? 51 : 80, 21);
            this.resultOne = new PositionedStack(outOne, isDouble ? 38 : 66, 69);
            if(outTwo != null && !outTwo.isEmpty()){
                this.resultTwo = new PositionedStack(outTwo, isDouble ? 63 : 94, 69);
            }
            this.secondChance = secondChance;
        }

        @Override
        public PositionedStack getResult(){
            return null;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.ingredient));
        }

        @Override
        public List<PositionedStack> getOtherStacks(){
            ArrayList<PositionedStack> list = new ArrayList<PositionedStack>();
            list.addAll(this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.resultOne)));
            if(this.resultTwo != null){
                list.addAll(this.getCycledIngredients(cycleticks/48, Collections.singletonList(this.resultTwo)));
            }
            return list;
        }
    }
}