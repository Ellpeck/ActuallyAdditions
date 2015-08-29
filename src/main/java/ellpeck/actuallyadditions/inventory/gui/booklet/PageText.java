/*
 * This file ("PageText.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory.gui.booklet;

import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

import java.util.List;

public class PageText implements IBookletPage{

    private int id;
    private BookletChapter chapter;

    public PageText(int id){
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
        return StringUtil.localize("booklet."+ModUtil.MOD_ID_LOWER+".chapter."+this.chapter.getUnlocalizedName()+".text."+this.id);
    }

    @Override
    public void renderPre(GuiBooklet gui, int mouseX, int mouseY){

    }

    @Override
    public void render(GuiBooklet gui, int mouseX, int mouseY){
        gui.unicodeRenderer.drawSplitString(gui.currentPage.getText(), gui.guiLeft+14, gui.guiTop+11, 115, 0);
    }

    @Override
    public ItemStack getItemStackForPage(){
        return null;
    }

    @SuppressWarnings("unchecked")
    protected void renderTooltipAndTransfer(GuiBooklet gui, ItemStack stack, int x, int y, boolean checkAndTransfer){
        List list = stack.getTooltip(gui.mc.thePlayer, gui.mc.gameSettings.advancedItemTooltips);

        for(int k = 0; k < list.size(); ++k){
            if(k == 0){
                list.set(k, stack.getRarity().rarityColor+(String)list.get(k));
            }
            else{
                list.set(k, StringUtil.GRAY+list.get(k));
            }
        }

        if(checkAndTransfer){
            for(IBookletPage page : InitBooklet.pagesWithItemStackData){
                if(page.getItemStackForPage() != null && page.getItemStackForPage().isItemEqual(stack)){
                    list.add(StringUtil.ORANGE+"Click to see Recipe!");

                    if(Mouse.isButtonDown(0)){
                        gui.openChapter(page.getChapter(), page);
                    }
                    break;
                }
            }
        }

        gui.drawHoveringText(list, x, y);
    }
}
