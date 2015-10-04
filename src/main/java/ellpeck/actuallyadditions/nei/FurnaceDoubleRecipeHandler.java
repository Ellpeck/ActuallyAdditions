/*
 * This file ("FurnaceDoubleRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.inventory.gui.GuiFurnaceDouble;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FurnaceDoubleRecipeHandler extends TemplateRecipeHandler implements INeiRecipeHandler{

    public static final String NAME = "actuallyadditions.furnaceDouble";

    public FurnaceDoubleRecipeHandler(){
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public ItemStack getStackForInfo(int page){
        return new ItemStack(InitBlocks.blockFurnaceDouble);
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(51, 40, 24, 22), NAME));
        transferRects.add(new RecipeTransferRect(new Rectangle(101, 40, 24, 22), NAME));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(NAME) && getClass() == FurnaceDoubleRecipeHandler.class){
            Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>)FurnaceRecipes.smelting().getSmeltingList();
            for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()){
                arecipes.add(new CachedFurn(recipe.getKey(), recipe.getValue()));
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(ItemStack result){
        Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>)FurnaceRecipes.smelting().getSmeltingList();
        for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()){
            if(NEIServerUtils.areStacksSameType(recipe.getValue(), result)){
                arecipes.add(new CachedFurn(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>)FurnaceRecipes.smelting().getSmeltingList();
        for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()){
            if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)){
                CachedFurn theRecipe = new CachedFurn(recipe.getKey(), recipe.getValue());
                theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.ingredient), ingredient);
                arecipes.add(theRecipe);
            }
        }
    }

    @Override
    public String getGuiTexture(){
        return ModUtil.MOD_ID_LOWER+":textures/gui/guiFurnaceDouble.png";
    }

    @Override
    public String getOverlayIdentifier(){
        return NAME;
    }

    @Override
    public void drawExtras(int recipe){
        drawProgressBar(51, 40, 176, 0, 24, 23, 48, 1);
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return GuiFurnaceDouble.class;
    }

    @Override
    public void drawBackground(int recipeIndex){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(46, 20, 46, 20, 84, 70);
    }

    @Override
    public int recipiesPerPage(){
        return 1;
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+NAME+".name");
    }

    public class CachedFurn extends CachedRecipe{

        public PositionedStack ingredient;
        public PositionedStack resultOne;

        public CachedFurn(ItemStack in, ItemStack resultOne){
            in.stackSize = 1;
            this.ingredient = new PositionedStack(in, 51, 21);
            this.resultOne = new PositionedStack(resultOne, 50, 69);
        }

        @Override
        public PositionedStack getResult(){
            return resultOne;
        }

        @Override
        public List<PositionedStack> getIngredients(){
            return getCycledIngredients(cycleticks/48, Collections.singletonList(ingredient));
        }
    }
}