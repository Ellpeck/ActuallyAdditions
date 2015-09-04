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

import ellpeck.actuallyadditions.booklet.BookletChapter;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public class BookletPage implements IBookletPage{

    protected int id;
    protected BookletChapter chapter;

    public BookletPage(int id){
        this.id = id;
    }

    @Override
    public int getID(){
        return this.id;
    }

    @Override
    public void setChapter(BookletChapter chapter){
        this.chapter = chapter;
    }

    @Override
    public BookletChapter getChapter(){
        return this.chapter;
    }

    @Override
    public String getText(){
        return StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.chapter.getUnlocalizedName()+".text."+this.id).replaceAll("<imp>", EnumChatFormatting.DARK_GREEN+"").replaceAll("<item>", EnumChatFormatting.BLUE+"").replaceAll("<r>", EnumChatFormatting.BLACK+"").replaceAll("<n>", "\n").replaceAll("<i>", EnumChatFormatting.ITALIC+"").replaceAll("<rs>", EnumChatFormatting.RESET+"");
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY, boolean mouseClick){

    }

    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY, boolean mouseClick){

    }

    @Override
    public ItemStack getItemStackForPage(){
        return null;
    }

    @SuppressWarnings("unchecked")
    protected void renderTooltipAndTransfer(GuiBooklet gui, ItemStack stack, int x, int y, boolean checkAndTransfer, boolean mouseClick){
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
            for(IBookletPage page : InitBooklet.pagesWithItemStackData){
                if(page.getItemStackForPage() != null && page.getItemStackForPage().isItemEqual(stack)){
                    list.add(EnumChatFormatting.GOLD+StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".clickToSeeRecipe"));

                    if(mouseClick){
                        gui.openIndexEntry(page.getChapter().entry, InitBooklet.entries.indexOf(page.getChapter().entry)/GuiBooklet.BUTTONS_PER_PAGE+1, true);
                        gui.openChapter(page.getChapter(), page);
                        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
                    }

                    break;
                }
            }
        }

        gui.drawHoveringText(list, x, y);
    }

    protected void renderItem(GuiBooklet gui, ItemStack stack, int x, int y){
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0);
        RenderItem.getInstance().renderItemAndEffectIntoGUI(gui.unicodeRenderer, gui.mc.getTextureManager(), stack, 0, 0);
        RenderItem.getInstance().renderItemOverlayIntoGUI(gui.mc.fontRenderer, gui.mc.getTextureManager(), stack, 0, 0);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();

    }
}
