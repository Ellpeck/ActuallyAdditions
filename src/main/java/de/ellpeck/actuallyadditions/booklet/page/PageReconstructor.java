package de.ellpeck.actuallyadditions.booklet.page;

import java.util.List;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import de.ellpeck.actuallyadditions.common.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageReconstructor extends BookletPage {

    private final LensConversionRecipe recipe;
    private boolean isWildcard;
    private int counter = 0;
    private int rotate = 0;
    private ItemStack[] stacks;

    public PageReconstructor(int localizationKey, LensConversionRecipe recipe) {
        super(localizationKey);
        this.recipe = recipe;
        if (recipe != null) this.stacks = recipe.getInput().getMatchingStacks();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);

        gui.mc.getTextureManager().bindTexture(GuiBooklet.RES_LOC_GADGETS);
        GuiUtils.drawTexturedModalRect(startX + 30, startY + 10, 80, 146, 68, 48, 0);

        gui.renderScaledAsciiString("(" + StringUtil.localize("booklet." + ActuallyAdditions.MODID + ".reconstructorRecipe") + ")", startX + 6, startY + 63, 0, false, gui.getMediumFontSize());

        PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 88);
        if (this.recipe != null) {
            if (this.counter++ % 50 == 0) gui.addOrModifyItemRenderer(this.stacks[this.rotate++ % this.stacks.length], startX + 30 + 1, startY + 10 + 13, 1F, true);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initGui(GuiBookletBase gui, int startX, int startY) {
        super.initGui(gui, startX, startY);

        if (this.recipe != null) {
            gui.addOrModifyItemRenderer(this.stacks[0], startX + 30 + 1, startY + 10 + 13, 1F, true);
            gui.addOrModifyItemRenderer(this.recipe.getOutput(), startX + 30 + 47, startY + 10 + 13, 1F, false);
        }
    }

    @Override
    public void getItemStacksForPage(List<ItemStack> list) {
        super.getItemStacksForPage(list);

        if (this.recipe != null) {
            ItemStack copy = this.recipe.getOutput().copy();
            if (this.isWildcard) {
                copy.setItemDamage(Util.WILDCARD);
            }
            list.add(copy);
        }
    }

    public BookletPage setWildcard() {
        this.isWildcard = true;
        return this;
    }

    @Override
    public int getSortingPriority() {
        return 20;
    }
}
