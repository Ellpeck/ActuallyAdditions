/*
 * This file ("BookletPage.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.booklet.page;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import de.ellpeck.actuallyadditions.util.ModUtil;
import de.ellpeck.actuallyadditions.util.StringUtil;
import de.ellpeck.actuallyadditions.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookletPage{

    public boolean arePageStacksWildcard;
    protected int localizationKey;
    protected BookletChapter chapter;
    private HashMap<String, String> textReplacements = new HashMap<String, String>();
    private boolean hasNoText;

    public BookletPage(int localizationKey){
        this.localizationKey = localizationKey;
    }

    public void addToPagesWithItemStackData(){
        if(!InitBooklet.pagesWithItemStackData.contains(this)){
            ItemStack[] stacks = this.getItemStacksForPage();
            if(stacks != null && stacks.length > 0){
                //Ensure that there is at least one ItemStack
                for(ItemStack stack : stacks){
                    if(stack != null){
                        InitBooklet.pagesWithItemStackData.add(this);
                        break;
                    }
                }
            }
        }
    }

    public ItemStack[] getItemStacksForPage(){
        return null;
    }

    public BookletPage setNoText(){
        this.hasNoText = true;
        return this;
    }

    public int getID(){
        return Util.arrayContains(this.chapter.pages, this)+1;
    }

    public final String getText(){
        if(this.hasNoText){
            return null;
        }

        String base = StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.chapter.getUnlocalizedName()+".text."+this.localizationKey);
        base = base.replaceAll("<imp>", EnumChatFormatting.DARK_GREEN+"");
        base = base.replaceAll("<item>", EnumChatFormatting.BLUE+"");
        base = base.replaceAll("<r>", EnumChatFormatting.BLACK+"");
        base = base.replaceAll("<n>", "\n");
        base = base.replaceAll("<i>", EnumChatFormatting.ITALIC+"");
        base = base.replaceAll("<tifisgrin>", EnumChatFormatting.DARK_RED+""+EnumChatFormatting.UNDERLINE); //This is fucking important so go read it now

        for(Object o : this.textReplacements.entrySet()){
            Map.Entry e = (Map.Entry)o;
            base = base.replaceAll((String)e.getKey(), (String)e.getValue());
        }
        return base;
    }

    public BookletPage addTextReplacement(String text, int replacement){
        return this.addTextReplacement(text, Integer.toString(replacement));
    }

    public BookletPage addTextReplacement(String text, String replacement){
        this.textReplacements.put(text, replacement);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){

    }

    @SideOnly(Side.CLIENT)
    public void render(GuiBooklet gui, int mouseX, int mouseY, int ticksElapsed, boolean mousePressed){

    }

    @SideOnly(Side.CLIENT)
    public void updateScreen(int ticksElapsed){

    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    protected void renderTooltipAndTransfer(GuiBooklet gui, ItemStack stack, int x, int y, boolean checkAndTransfer, boolean mousePressed){
        boolean flagBefore = gui.mc.fontRenderer.getUnicodeFlag();
        gui.mc.fontRenderer.setUnicodeFlag(false);

        List list = stack.getTooltip(gui.mc.thePlayer, gui.mc.gameSettings.advancedItemTooltips);

        for(int k = 0; k < list.size(); ++k){
            if(k == 0){
                list.set(k, stack.getRarity().rarityColor+(String)list.get(k));
            }
            else{
                list.set(k, EnumChatFormatting.GRAY+(String)list.get(k));
            }
        }

        if(checkAndTransfer){
            BookletPage page = BookletUtils.getFirstPageForStack(stack);
            if(page != null && page != this){
                list.add(EnumChatFormatting.GOLD+StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".clickToSeeRecipe"));

                if(mousePressed){
                    BookletUtils.openIndexEntry(gui, page.getChapter().entry, InitBooklet.entries.indexOf(page.getChapter().entry)/GuiBooklet.CHAPTER_BUTTONS_AMOUNT+1, true);
                    BookletUtils.openChapter(gui, page.getChapter(), page);
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
                }
            }
        }

        gui.drawHoveringText(list, x, y);

        gui.mc.fontRenderer.setUnicodeFlag(flagBefore);
    }

    public BookletChapter getChapter(){
        return this.chapter;
    }

    public void setChapter(BookletChapter chapter){
        this.chapter = chapter;
    }

    public BookletPage setPageStacksWildcard(){
        this.arePageStacksWildcard = true;
        return this;
    }
}
