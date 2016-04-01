/*
 * This file ("BookletPage.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;
import java.util.Map;

public class BookletPageAA extends BookletPage{

    protected int localizationKey;
    private HashMap<String, String> textReplacements = new HashMap<String, String>();
    private boolean hasNoText;

    public BookletPageAA(int localizationKey){
        this.localizationKey = localizationKey;
    }

    @Override
    public int getID(){
        return Util.arrayContains(this.chapter.getPages(), this)+1;
    }

    @Override
    public final String getText(){
        if(this.hasNoText){
            return null;
        }

        String base = StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.chapter.getUnlocalizedName()+".text."+this.localizationKey);
        base = base.replaceAll("<imp>", TextFormatting.DARK_GREEN+"");
        base = base.replaceAll("<item>", TextFormatting.BLUE+"");
        base = base.replaceAll("<r>", TextFormatting.BLACK+"");
        base = base.replaceAll("<n>", "\n");
        base = base.replaceAll("<i>", TextFormatting.ITALIC+"");
        base = base.replaceAll("<tifisgrin>", TextFormatting.DARK_RED+""+TextFormatting.UNDERLINE); //This is fucking important so go read it now

        for(Object o : this.textReplacements.entrySet()){
            Map.Entry e = (Map.Entry)o;
            base = base.replaceAll((String)e.getKey(), (String)e.getValue());
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
    public ItemStack[] getItemStacksForPage(){
        return null;
    }

    @Override
    public String getClickToSeeRecipeString(){
        return TextFormatting.GOLD+StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".clickToSeeRecipe");
    }

    public BookletPage setNoText(){
        this.hasNoText = true;
        return this;
    }

    public BookletPageAA addTextReplacement(String text, int replacement){
        return this.addTextReplacement(text, Integer.toString(replacement));
    }

    public BookletPageAA addTextReplacement(String text, String replacement){
        this.textReplacements.put(text, replacement);
        return this;
    }

    public void addToPagesWithItemStackData(){
        if(!ActuallyAdditionsAPI.bookletPagesWithItemStackData.contains(this)){
            ItemStack[] stacks = this.getItemStacksForPage();
            if(stacks != null && stacks.length > 0){
                //Ensure that there is at least one ItemStack
                for(ItemStack stack : stacks){
                    if(stack != null){
                        ActuallyAdditionsAPI.addPageWithItemStackData(this);
                        break;
                    }
                }
            }
        }
    }
}
