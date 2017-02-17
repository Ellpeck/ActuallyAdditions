/*
 * This file ("TrialsButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.button;

import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.inventory.gui.TexturedButton;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;

public class TrialsButton extends TexturedButton{

    private final boolean isTrials;

    public TrialsButton(GuiBooklet gui){
        super(GuiBooklet.RES_LOC_GADGETS, -152000, gui.getGuiLeft()+gui.getSizeX(), gui.getGuiTop()+10, 0, 204, 52, 16);
        this.isTrials = gui.areTrialsOpened();
        this.enabled = !this.isTrials;
    }

    @Override
    public void drawButton(Minecraft minecraft, int x, int y){
        super.drawButton(minecraft, x, y);

        if(this.visible){
            if(this.hovered || this.isTrials){
                this.drawCenteredString(minecraft.fontRendererObj, StringUtil.localize("booklet."+ModUtil.MOD_ID+".trialsButton.name"), this.xPosition+(this.width-8)/2, this.yPosition+(this.height-8)/2, 14737632);
            }
        }
    }

    @Override
    protected int getHoverState(boolean mouseOver){
        if(mouseOver || this.isTrials){
            return 2;
        }
        else if(!this.enabled){
            return 0;
        }
        else{
            return 1;
        }
    }
}
