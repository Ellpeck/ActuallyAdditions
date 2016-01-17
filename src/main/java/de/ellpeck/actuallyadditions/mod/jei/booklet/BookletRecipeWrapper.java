/*
 * This file ("BookletRecipeWrapper.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.jei.booklet;

import com.google.common.collect.ImmutableList;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.integration.RecipeWrapperWithButton;
import de.ellpeck.actuallyadditions.mod.booklet.page.PagePicture;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class BookletRecipeWrapper extends RecipeWrapperWithButton implements IRecipeWrapper{

    public BookletPage thePage;

    public BookletRecipeWrapper(BookletPage page){
        this.thePage = page;
    }

    @Override
    public List getInputs(){
        return Arrays.asList(this.thePage.getItemStacksForPage());
    }

    @Override
    public List getOutputs(){
        return Arrays.asList(this.thePage.getItemStacksForPage());
    }

    @Override
    public List<FluidStack> getFluidInputs(){
        return ImmutableList.of();
    }

    @Override
    public List<FluidStack> getFluidOutputs(){
        return ImmutableList.of();
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight){

    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY){
        List header = minecraft.fontRendererObj.listFormattedStringToWidth(StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".booklet.header").replaceAll("<item>", EnumChatFormatting.BLUE+"").replaceAll("<r>", EnumChatFormatting.BLACK+""), 150);
        for(int i = 0; i < header.size(); i++){
            minecraft.fontRendererObj.drawString((String)header.get(i), 0, 17+i*(minecraft.fontRendererObj.FONT_HEIGHT+1), 0, false);
        }

        int maxLines = 4;
        IBookletChapter chapter = this.thePage.getChapter();
        String aText = (chapter.getPages()[0] instanceof PagePicture && chapter.getPages().length > 1 ? chapter.getPages()[1] : chapter.getPages()[0]).getText();
        List text = minecraft.fontRendererObj.listFormattedStringToWidth(aText != null ? aText : EnumChatFormatting.DARK_RED+StringUtil.localize("container.nei."+ModUtil.MOD_ID_LOWER+".booklet.noText"), 150);
        for(int i = 0; i < Math.min(maxLines, text.size()); i++){
            minecraft.fontRendererObj.drawString(text.get(i)+(i == maxLines-1 && text.size() > maxLines ? EnumChatFormatting.RESET+""+EnumChatFormatting.BLACK+"..." : ""), 0, 16+25+i*(minecraft.fontRendererObj.FONT_HEIGHT+1), 0, false);
        }
        minecraft.fontRendererObj.drawString(EnumChatFormatting.ITALIC+chapter.getLocalizedName(), 25, 85, 0, false);
        minecraft.fontRendererObj.drawString(EnumChatFormatting.ITALIC+"Page "+this.thePage.getID(), 25, 95, 0, false);

        this.updateButton(minecraft, mouseX, mouseY);
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight){

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY){
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton){
        return this.handleClick(minecraft, mouseX, mouseY);
    }

    @Override
    public BookletPage getPage(){
        return this.thePage;
    }
}
