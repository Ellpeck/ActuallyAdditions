/*
 * This file ("PageButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.internal.IBookletGui;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public abstract class PageButton extends PageTextOnly{

    private GuiButton button;

    public PageButton(int id){
        super(id);
    }

    @Override
    public void onOpened(IBookletGui gui){
        String text = StringUtil.localize("booklet."+ModUtil.MOD_ID+".chapter."+this.chapter.getUnlocalizedName()+".page."+this.localizationKey+".button");
        int width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
        this.button = new GuiButton(-1239, gui.getGuiLeft()+gui.getXSize()/2-width/2-8, gui.getGuiTop()+gui.getYSize()-40, width+15, 20, text);
        gui.getButtonList().add(this.button);
    }

    @Override
    public void onClosed(IBookletGui gui){
        gui.getButtonList().remove(this.button);
    }

    @Override
    public boolean onActionPerformed(IBookletGui gui, GuiButton button){
        return button == this.button && this.onAction();
    }

    public abstract boolean onAction();
}
