/*
 * This file ("NEITreasureChestRecipe.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.integration.INEIRecipeHandler;
import de.ellpeck.actuallyadditions.api.recipe.TreasureChestLoot;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class NEITreasureChestRecipe extends TemplateRecipeHandler implements INEIRecipeHandler{

    public static final String NAME = "actuallyadditions.treasureChest";

    public NEITreasureChestRecipe(){
        super();
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockTreasureChest));
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
        if(outputId.equals(NAME) && getClass() == NEITreasureChestRecipe.class){
            List<TreasureChestLoot> recipes = ActuallyAdditionsAPI.treasureChestLoot;
            for(TreasureChestLoot recipe : recipes){
                arecipes.add(new CachedTreasure(new ItemStack(InitBlocks.blockTreasureChest), recipe.returnItem, recipe.itemWeight, recipe.minAmount, recipe.maxAmount));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        List<TreasureChestLoot> recipes = ActuallyAdditionsAPI.treasureChestLoot;
        for(TreasureChestLoot recipe : recipes){
            if(NEIServerUtils.areStacksSameType(recipe.returnItem, result)){
                arecipes.add(new CachedTreasure(new ItemStack(InitBlocks.blockTreasureChest), recipe.returnItem, recipe.itemWeight, recipe.minAmount, recipe.maxAmount));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        List<TreasureChestLoot> recipes = ActuallyAdditionsAPI.treasureChestLoot;
        for(TreasureChestLoot recipe : recipes){
            ItemStack stack = new ItemStack(InitBlocks.blockTreasureChest);
            if(NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)){
                CachedTreasure theRecipe = new CachedTreasure(stack, recipe.returnItem, recipe.itemWeight, recipe.minAmount, recipe.maxAmount);
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
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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