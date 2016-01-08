/*
 * This file ("NEIBookletRecipe.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.INEIRecipeHandler;
import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.mod.booklet.page.PagePicture;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NEIBookletRecipe extends TemplateRecipeHandler implements INEIRecipeHandler{

    public static final String NAME = "actuallyadditions.booklet";

    public NEIBookletRecipe(){
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public BookletPage getPageForInfo(int page){
        return ((CachedInfoStack)this.arecipes.get(page)).thePage;
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(0, 18, 165, Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT), NAME));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(NAME) && getClass() == NEIBookletRecipe.class){
            for(BookletPage page : ActuallyAdditionsAPI.bookletPagesWithItemStackData){
                ItemStack[] stacks = page.getItemStacksForPage();

                //So that you don't see things like Mashed Food more than once
                ArrayList<ItemStack> nonDoubleStacks = new ArrayList<ItemStack>();
                for(ItemStack stack : stacks){
                    if(!ItemUtil.contains(nonDoubleStacks, stack, true)){
                        arecipes.add(new CachedInfoStack(stack, page));
                        nonDoubleStacks.add(stack);
                    }
                }
            }
        }
        else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(ItemStack result){
        ArrayList<BookletPage> allPages = BookletUtils.getPagesForStack(result);
        for(BookletPage page : allPages){
            CachedInfoStack theRecipe = new CachedInfoStack(result, page);
            arecipes.add(theRecipe);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        ArrayList<BookletPage> allPages = BookletUtils.getPagesForStack(ingredient);
        for(BookletPage page : allPages){
            CachedInfoStack theRecipe = new CachedInfoStack(ingredient, page);
            arecipes.add(theRecipe);
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
        CachedInfoStack stack = (CachedInfoStack)this.arecipes.get(recipe);
        if(stack.theStack != null){
            List header = Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".booklet.header").replaceAll("<item>", EnumChatFormatting.BLUE+"").replaceAll("<r>", EnumChatFormatting.BLACK+""), 165);
            for(int i = 0; i < header.size(); i++){
                GuiDraw.drawString((String)header.get(i), 0, 18+i*(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT+1), 0, false);
            }

            int maxLines = 5;
            IBookletChapter chapter = stack.thePage.getChapter();
            String aText = (chapter.getPages()[0] instanceof PagePicture && chapter.getPages().length > 1 ? chapter.getPages()[1] : chapter.getPages()[0]).getText();
            List text = Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(aText != null ? aText : EnumChatFormatting.DARK_RED+StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".booklet.noText"), 165);
            for(int i = 0; i < Math.min(maxLines, text.size()); i++){
                GuiDraw.drawString(text.get(i)+(i == maxLines-1 && text.size() > maxLines ? EnumChatFormatting.RESET+""+EnumChatFormatting.BLACK+"..." : ""), 0, 18+25+i*(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT+1), 0, false);
            }
            GuiDraw.drawString(EnumChatFormatting.ITALIC+chapter.getLocalizedName(), 0, 97, 0, false);
            GuiDraw.drawString(EnumChatFormatting.ITALIC+"Page "+stack.thePage.getID(), 0, 107, 0, false);
        }
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass(){
        return null;
    }

    @Override
    public void drawBackground(int recipe){

    }

    @Override
    public void drawForeground(int recipe){
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawExtras(recipe);
    }

    @Override
    public int recipiesPerPage(){
        return 1;
    }

    @Override
    public String getRecipeName(){
        return StringUtil.localize("container.nei."+NAME+".name");
    }

    public class CachedInfoStack extends CachedRecipe{

        public ItemStack theStack;
        public BookletPage thePage;

        public CachedInfoStack(ItemStack theStack, BookletPage thePage){
            this.theStack = theStack;
            this.thePage = thePage;
        }

        @Override
        public PositionedStack getResult(){
            if(this.theStack != null){
                ItemStack newStack = this.theStack.copy();
                newStack.stackSize = 1;
                return new PositionedStack(newStack, 0, 0);
            }
            return null;
        }
    }
}