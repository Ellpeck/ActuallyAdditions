/*
 * This file ("TreasureChestRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.recipe.TreasureChestHandler;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class TreasureChestRecipeHandler extends TemplateRecipeHandler implements INeiRecipeHandler{

    public static final String NAME = "actuallyadditions.treasureChest";

    public TreasureChestRecipeHandler(){
        super();
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public ItemStack getStackForInfo(int page){
        return new ItemStack(InitBlocks.blockTreasureChest);
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
        if(outputId.equals(NAME) && getClass() == TreasureChestRecipeHandler.class){
            ArrayList<TreasureChestHandler.Return> recipes = TreasureChestHandler.returns;
            for(TreasureChestHandler.Return recipe : recipes){
                arecipes.add(new CachedTreasure(recipe.input, recipe.returnItem, recipe.itemWeight, recipe.minAmount, recipe.maxAmount));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        ArrayList<TreasureChestHandler.Return> recipes = TreasureChestHandler.returns;
        for(TreasureChestHandler.Return recipe : recipes){
            if(NEIServerUtils.areStacksSameType(recipe.returnItem, result)){
                arecipes.add(new CachedTreasure(recipe.input, recipe.returnItem, recipe.itemWeight, recipe.minAmount, recipe.maxAmount));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        ArrayList<TreasureChestHandler.Return> recipes = TreasureChestHandler.returns;
        for(TreasureChestHandler.Return recipe : recipes){
            if(NEIServerUtils.areStacksSameTypeCrafting(recipe.input, ingredient)){
                CachedTreasure theRecipe = new CachedTreasure(recipe.input, recipe.returnItem, recipe.itemWeight, recipe.minAmount, recipe.maxAmount);
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
    public void drawExtras(int rec){
        CachedTreasure recipe = (CachedTreasure)this.arecipes.get(rec);
        if(recipe.result != null){
            GuiDraw.drawString(recipe.minAmount+"-"+recipe.maxAmount+" "+StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".treasureChest.info")+" "+recipe.chance+"%", 55, 45, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
        }
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

    public class CachedTreasure extends CachedRecipe{

        public PositionedStack result;
        public PositionedStack input;
        public int chance;
        public int minAmount;
        public int maxAmount;

        public CachedTreasure(ItemStack input, ItemStack result, int chance, int minAmount, int maxAmount){
            this.result = new PositionedStack(result, 67+32, 19);
            this.chance = chance;
            this.input = new PositionedStack(input, 5+32, 19);
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
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