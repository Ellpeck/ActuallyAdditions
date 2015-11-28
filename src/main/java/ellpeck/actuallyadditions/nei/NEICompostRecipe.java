/*
 * This file ("CompostRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.booklet.BookletUtils;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import ellpeck.actuallyadditions.tile.TileEntityCompost;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collections;

public class NEICompostRecipe extends TemplateRecipeHandler implements INEIRecipeHandler{

    public static final String NAME = "actuallyadditions.compost";

    public NEICompostRecipe(){
        super();
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return BookletUtils.getFirstPageForStack(new ItemStack(InitBlocks.blockCompost));
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
        if(outputId.equals(NAME) && getClass() == NEICompostRecipe.class){
            arecipes.add(new CachedCompostRecipe(new ItemStack(InitItems.itemMisc, TileEntityCompost.AMOUNT, TheMiscItems.MASHED_FOOD.ordinal()), new ItemStack(InitItems.itemFertilizer, TileEntityCompost.AMOUNT)));
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        if(NEIServerUtils.areStacksSameType(new ItemStack(InitItems.itemFertilizer), result)){
            arecipes.add(new CachedCompostRecipe(new ItemStack(InitItems.itemMisc, TileEntityCompost.AMOUNT, TheMiscItems.MASHED_FOOD.ordinal()), new ItemStack(InitItems.itemFertilizer, TileEntityCompost.AMOUNT)));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        if(NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(InitItems.itemMisc, TileEntityCompost.AMOUNT, TheMiscItems.MASHED_FOOD.ordinal()), ingredient)){
            CachedCompostRecipe theRecipe = new CachedCompostRecipe(new ItemStack(InitItems.itemMisc, TileEntityCompost.AMOUNT, TheMiscItems.MASHED_FOOD.ordinal()), new ItemStack(InitItems.itemFertilizer, TileEntityCompost.AMOUNT));
            theRecipe.setIngredientPermutation(Collections.singletonList(theRecipe.input), ingredient);
            arecipes.add(theRecipe);
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

    public class CachedCompostRecipe extends CachedRecipe{

        public PositionedStack result;
        public PositionedStack input;
        public int chance;

        public CachedCompostRecipe(ItemStack input, ItemStack result){
            this.result = new PositionedStack(result, 67+32, 19);
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