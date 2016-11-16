/*
 * This file ("PageEmpowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.api.recipe.EmpowererRecipe;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.List;

public class PageEmpowerer extends BookletPage{

    private final EmpowererRecipe recipe;

    public PageEmpowerer(int localizationKey, EmpowererRecipe recipe){
        super(localizationKey);
        this.recipe = recipe;
    }

    @Override
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks){
        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);

        gui.mc.getTextureManager().bindTexture(GuiBooklet.RES_LOC_GADGETS);
        GuiUtils.drawTexturedModalRect(startX+5, startY+10, 117, 74, 116, 72, 0);

        gui.renderScaledAsciiString("(Empowerer Recipe)", startX+6, startY+85, 0, false, 0.65F);

        PageTextOnly.renderTextToPage(gui, this, startX+6, startY+100);
    }

    @Override
    public void initGui(GuiBookletBase gui, int startX, int startY){
        super.initGui(gui, startX, startY);

        if(this.recipe != null){
            gui.addOrModifyItemRenderer(this.recipe.modifier1, startX+5+26, startY+10+1, 1F, true);
            gui.addOrModifyItemRenderer(this.recipe.modifier2, startX+5+1, startY+10+26, 1F, true);
            gui.addOrModifyItemRenderer(this.recipe.modifier3, startX+5+51, startY+10+26, 1F, true);
            gui.addOrModifyItemRenderer(this.recipe.modifier4, startX+5+26, startY+10+51, 1F, true);

            gui.addOrModifyItemRenderer(this.recipe.input, startX+5+26, startY+10+26, 1F, true);
            gui.addOrModifyItemRenderer(this.recipe.output, startX+5+96, startY+10+26, 1F, false);
        }
    }

    @Override
    public void getItemStacksForPage(List<ItemStack> list){
        super.getItemStacksForPage(list);

        if(this.recipe != null){
            list.add(this.recipe.output);
        }
    }
}
