/*
 * This file ("PageTextOnly.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageTextOnly extends BookletPage{

    public PageTextOnly(int localizationKey){
        super(localizationKey);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPost(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks){
        super.drawScreenPost(gui, startX, startY, mouseX, mouseY, partialTicks);

        String text = this.getInfoText();
        if(text != null && !text.isEmpty()){
            gui.renderSplitScaledAsciiString(text, startX+6, startY+5, 0, false, 0.75F, 120);
        }
    }
}
