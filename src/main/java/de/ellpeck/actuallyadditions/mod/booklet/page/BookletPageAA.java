/*
 * This file ("BookletPageAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookletPageAA extends BookletPage{

    protected List<FluidStack> fluidsForPage = new ArrayList<FluidStack>();
    protected final int localizationKey;

    public BookletPageAA(int localizationKey){
        this.localizationKey = localizationKey;
    }

    @Override
    public int getID(){
        return this.chapter.getPageId(this);
    }

    @Override
    public String getText(){
        if(this.hasNoText){
            return null;
        }

        String base = StringUtil.localize("booklet."+ModUtil.MOD_ID+".chapter."+this.chapter.getIdentifier()+".text."+this.localizationKey);
        base = base.replaceAll("<imp>", TextFormatting.DARK_GREEN+"");
        base = base.replaceAll("<item>", TextFormatting.BLUE+"");
        base = base.replaceAll("<r>", TextFormatting.BLACK+"");
        base = base.replaceAll("<n>", "\n");
        base = base.replaceAll("<i>", TextFormatting.ITALIC+"");
        base = base.replaceAll("<tifisgrin>", TextFormatting.DARK_RED+""+TextFormatting.UNDERLINE); //This is fucking important so go read it now

        for(Map.Entry<String, String> entry : this.textReplacements.entrySet()){
            base = base.replaceAll(entry.getKey(), entry.getValue());
        }
        return base;
    }

    @Override
    public void renderPre(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){

    }

    @Override
    public void render(IBookletGui gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){

    }

    @Override
    public void updateScreen(int ticksElapsed){

    }

    @Override
    public FluidStack[] getFluidStacksForPage(){
        return this.fluidsForPage.toArray(new FluidStack[this.fluidsForPage.size()]);
    }

    public BookletPageAA addFluidToPage(Fluid fluid){
        this.fluidsForPage.add(new FluidStack(fluid, 1));
        return this;
    }

    @Override
    public String getClickToSeeRecipeString(){
        return TextFormatting.GOLD+StringUtil.localize("booklet."+ModUtil.MOD_ID+".clickToSeeRecipe");
    }
}
