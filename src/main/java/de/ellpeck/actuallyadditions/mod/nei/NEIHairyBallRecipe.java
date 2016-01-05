/*
 * This file ("NEIHairyBallRecipe.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.BallOfFurReturn;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.booklet.page.BookletPage;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class NEIHairyBallRecipe extends TemplateRecipeHandler implements INEIRecipeHandler{

    public static final String NAME = "actuallyadditions.ballOfHair";

    public NEIHairyBallRecipe(){
        super();
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitItems.itemHairyBall));
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
        if(outputId.equals(NAME) && getClass() == NEIHairyBallRecipe.class){
            List<BallOfFurReturn> recipes = ActuallyAdditionsAPI.ballOfFurReturnItems;
            for(BallOfFurReturn recipe : recipes){
                arecipes.add(new CachedBallRecipe(new ItemStack(InitItems.itemHairyBall), recipe.returnItem, recipe.itemWeight));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        List<BallOfFurReturn> recipes = ActuallyAdditionsAPI.ballOfFurReturnItems;
        for(BallOfFurReturn recipe : recipes){
            if(NEIServerUtils.areStacksSameType(recipe.returnItem, result)){
                arecipes.add(new CachedBallRecipe(new ItemStack(InitItems.itemHairyBall), recipe.returnItem, recipe.itemWeight));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        List<BallOfFurReturn> recipes = ActuallyAdditionsAPI.ballOfFurReturnItems;
        for(BallOfFurReturn recipe : recipes){
            ItemStack stack = new ItemStack(InitItems.itemHairyBall);
            if(NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)){
                CachedBallRecipe theRecipe = new CachedBallRecipe(stack, recipe.returnItem, recipe.itemWeight);
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
        CachedBallRecipe recipe = (CachedBallRecipe)this.arecipes.get(rec);
        if(recipe.result != null){
            int secondChance = recipe.chance;
            String secondString = secondChance+"%";
            GuiDraw.drawString(secondString, 65+32, 45, StringUtil.DECIMAL_COLOR_GRAY_TEXT, false);
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

    public class CachedBallRecipe extends CachedRecipe{

        public PositionedStack result;
        public PositionedStack input;
        public int chance;

        public CachedBallRecipe(ItemStack input, ItemStack result, int chance){
            this.result = new PositionedStack(result, 67+32, 19);
            this.chance = chance;
            this.input = new PositionedStack(input, 5+32, 19);
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