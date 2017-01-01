/*
 * This file ("PageCrusherRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.List;

public class PageCrusherRecipe extends BookletPage{

    private final CrusherRecipe recipe;

    public PageCrusherRecipe(int localizationKey, CrusherRecipe recipe){
        super(localizationKey);
        this.recipe = recipe;
    }

    @Override
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks){
        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);

        gui.mc.getTextureManager().bindTexture(GuiBooklet.RES_LOC_GADGETS);
        GuiUtils.drawTexturedModalRect(startX+38, startY+6, 136, 0, 52, 74, 0);

        gui.renderScaledAsciiString("(Crusher Recipe)", startX+36, startY+85, 0, false, gui.getMediumFontSize());

        PageTextOnly.renderTextToPage(gui, this, startX+6, startY+100);
    }

    @Override
    public void initGui(GuiBookletBase gui, int startX, int startY){
        super.initGui(gui, startX, startY);

        if(this.recipe != null){
            gui.addOrModifyItemRenderer(this.recipe.inputStack, startX+38+18, startY+6+2, 1F, true);
            gui.addOrModifyItemRenderer(this.recipe.outputOneStack, startX+38+4, startY+6+53, 1F, false);

            if(StackUtil.isValid(this.recipe.outputTwoStack)){
                gui.addOrModifyItemRenderer(this.recipe.outputTwoStack, startX+38+30, startY+6+53, 1F, false);
            }
        }
    }

    @Override
    public void getItemStacksForPage(List<ItemStack> list){
        super.getItemStacksForPage(list);

        if(this.recipe != null){
            list.add(this.recipe.outputOneStack);

            if(StackUtil.isValid(this.recipe.outputTwoStack)){
                list.add(this.recipe.outputTwoStack);
            }
        }
    }
}
