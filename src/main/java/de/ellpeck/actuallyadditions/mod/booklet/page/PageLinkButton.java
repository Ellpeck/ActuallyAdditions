/*
 * This file ("PageLinkButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.net.URI;

public class PageLinkButton extends BookletPage{

    public static int nextButtonId = 23782;
    private final int buttonId;

    private final String link;

    public PageLinkButton(int localizationKey, String link){
        super(localizationKey);
        this.link = link;

        this.buttonId = nextButtonId;
        nextButtonId++;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initGui(GuiBookletBase gui, int startX, int startY){
        super.initGui(gui, startX, startY);

        gui.getButtonList().add(new GuiButton(this.buttonId, startX+125/2-50, startY+130, 100, 20, StringUtil.localize("booklet."+ModUtil.MOD_ID+".chapter."+this.chapter.getIdentifier()+".button."+this.localizationKey)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks){
        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
        PageTextOnly.renderTextToPage(gui, this, startX+6, startY+5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void actionPerformed(GuiBookletBase gui, GuiButton button){
        if(button.id == this.buttonId){
            if(Desktop.isDesktopSupported()){
                try{
                    Desktop.getDesktop().browse(new URI(this.link));
                }
                catch(Exception e){
                    ModUtil.LOGGER.error("Couldn't open website from Link Button page!", e);
                }
            }
        }
        else{
            super.actionPerformed(gui, button);
        }
    }
}
