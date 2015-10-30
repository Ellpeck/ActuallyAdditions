/*
 * This file ("BookletInfoRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.booklet.BookletChapter;
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.booklet.page.PagePicture;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class BookletInfoRecipeHandler extends TemplateRecipeHandler implements INeiRecipeHandler{

    public static final String NAME = "actuallyadditions.booklet";

    public BookletInfoRecipeHandler(){
        RecipeInfo.setGuiOffset(this.getGuiClass(), 0, 0);
    }

    @Override
    public ItemStack getStackForInfo(int page){
        return ((CachedInfoStack)this.arecipes.get(page)).theStack;
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(0, 18, 165, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT), NAME));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals(NAME) && getClass() == BookletInfoRecipeHandler.class){
            for(BookletPage page : InitBooklet.pagesWithItemStackData){
                for(ItemStack stack : page.getItemStacksForPage()){
                    arecipes.add(new CachedInfoStack(stack));
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
        for(BookletPage page : InitBooklet.pagesWithItemStackData){
            if(ItemUtil.contains(page.getItemStacksForPage(), result, true)){
                CachedInfoStack theRecipe = new CachedInfoStack(result);
                arecipes.add(theRecipe);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        for(BookletPage page : InitBooklet.pagesWithItemStackData){
            if(ItemUtil.contains(page.getItemStacksForPage(), ingredient, true)){
                CachedInfoStack theRecipe = new CachedInfoStack(ingredient);
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
        CachedInfoStack stack = (CachedInfoStack)this.arecipes.get(recipe);
        if(stack.theStack != null){
            List header = Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".booklet.header").replaceAll("<item>", EnumChatFormatting.BLUE+"").replaceAll("<r>", EnumChatFormatting.BLACK+""), 165);
            for(int i = 0; i < header.size(); i++){
                GuiDraw.drawString((String)header.get(i), 0, 18+i*(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT+1), 0, false);
            }

            for(BookletPage page : InitBooklet.pagesWithItemStackData){
                if(ItemUtil.contains(page.getItemStacksForPage(), stack.theStack, true)){
                    int maxLines = 6;

                    BookletChapter chapter = page.getChapter();
                    String aText = (chapter.pages[0] instanceof PagePicture && chapter.pages.length > 1 ? chapter.pages[1] : chapter.pages[0]).getText();
                    List text = Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(aText != null ? aText : EnumChatFormatting.DARK_RED+StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".booklet.noText"), 165);
                    for(int i = 0; i < Math.min(maxLines, text.size()); i++){
                        GuiDraw.drawString(text.get(i)+(i == maxLines-1 && text.size() > maxLines ? EnumChatFormatting.RESET+""+EnumChatFormatting.BLACK+"..." : ""), 0, 18+25+i*(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT+1), 0, false);
                    }
                    break;
                }
            }
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

        public CachedInfoStack(ItemStack theStack){
            this.theStack = theStack;
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