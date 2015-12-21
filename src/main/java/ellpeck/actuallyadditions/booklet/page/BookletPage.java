/*
 * This file ("BookletPage.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.booklet.page;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.booklet.BookletUtils;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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

    @SideOnly(Side.CLIENT)
    public static void renderItem(GuiScreen gui, ItemStack stack, int x, int y, float scale){
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslated(x, y, 0);
        GL11.glScalef(scale, scale, scale);

        Minecraft mc = Minecraft.getMinecraft();
        boolean flagBefore = mc.fontRenderer.getUnicodeFlag();
        mc.fontRenderer.setUnicodeFlag(false);
        RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, 0, 0);
        RenderItem.getInstance().renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, 0, 0);
        mc.fontRenderer.setUnicodeFlag(flagBefore);

        //GL+MC+NEI suck
        if(gui instanceof GuiBooklet){
            RenderHelper.disableStandardItemLighting();
        }
        GL11.glPopMatrix();
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

        String base = StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.chapter.getUnlocalizedName()+".text."+this.localizationKey).replaceAll("<imp>", EnumChatFormatting.DARK_GREEN+"").replaceAll("<item>", EnumChatFormatting.BLUE+"").replaceAll("<r>", EnumChatFormatting.BLACK+"").replaceAll("<n>", "\n").replaceAll("<i>", EnumChatFormatting.ITALIC+"").replaceAll("<rs>", EnumChatFormatting.RESET+"");
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
