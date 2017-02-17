/*
 * This file ("PageFurnace.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public class PageFurnace extends BookletPage{

    private final ItemStack input;
    private final ItemStack output;

    public PageFurnace(int localizationKey, ItemStack output){
        this(localizationKey, output, 0);
    }

    public PageFurnace(int localizationKey, ItemStack output, int priority){
        super(localizationKey, priority);
        this.output = output;
        this.input = getInputForOutput(output);
    }

    private static ItemStack getInputForOutput(ItemStack output){
        for(Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()){
            ItemStack stack = entry.getValue();
            if(StackUtil.isValid(stack)){
                if(stack.isItemEqual(output)){
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks){
        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);

        gui.mc.getTextureManager().bindTexture(GuiBooklet.RES_LOC_GADGETS);
        GuiUtils.drawTexturedModalRect(startX+23, startY+10, 0, 146, 80, 26, 0);

        gui.renderScaledAsciiString("("+StringUtil.localize("booklet."+ModUtil.MOD_ID+".furnaceRecipe")+")", startX+32, startY+42, 0, false, gui.getMediumFontSize());

        PageTextOnly.renderTextToPage(gui, this, startX+6, startY+57);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initGui(GuiBookletBase gui, int startX, int startY){
        super.initGui(gui, startX, startY);

        gui.addOrModifyItemRenderer(this.input, startX+23+1, startY+10+5, 1F, true);
        gui.addOrModifyItemRenderer(this.output, startX+23+59, startY+10+5, 1F, false);
    }

    @Override
    public void getItemStacksForPage(List<ItemStack> list){
        super.getItemStacksForPage(list);

        list.add(this.output);
    }
}
