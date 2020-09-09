package de.ellpeck.actuallyadditions.booklet.page;

import java.util.List;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.api.recipe.CoffeeIngredient;
import de.ellpeck.actuallyadditions.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.common.items.InitItems;
import de.ellpeck.actuallyadditions.common.items.metalists.TheMiscItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageCoffeeMachine extends BookletPage {

    private final CoffeeIngredient ingredient;
    private final ItemStack outcome;
    private int counter = 0;
    private int rotate = 0;
    private final ItemStack[] stacks;

    public PageCoffeeMachine(int localizationKey, CoffeeIngredient ingredient) {
        super(localizationKey);
        this.ingredient = ingredient;
        this.stacks = ingredient.getInput().getMatchingStacks();

        this.outcome = new ItemStack(InitItems.itemCoffee);
        ActuallyAdditionsAPI.methodHandler.addEffectToStack(this.outcome, this.ingredient);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);

        gui.mc.getTextureManager().bindTexture(GuiBooklet.RES_LOC_GADGETS);
        GuiUtils.drawTexturedModalRect(startX + 5, startY + 10, 0, 74, 117, 72, 0);

        gui.renderScaledAsciiString("(Coffee Maker Recipe)", startX + 6, startY + 78, 0, false, gui.getMediumFontSize());
        gui.renderSplitScaledAsciiString("Hover over this to see the effect!", startX + 5, startY + 51, 0, false, gui.getSmallFontSize(), 35);

        PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 90);

        if (this.counter++ % 50 == 0) gui.addOrModifyItemRenderer(this.stacks[this.rotate++ % this.stacks.length], startX + 5 + 82, startY + 10 + 1, 1F, true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initGui(GuiBookletBase gui, int startX, int startY) {
        super.initGui(gui, startX, startY);

        gui.addOrModifyItemRenderer(this.stacks[0], startX + 5 + 82, startY + 10 + 1, 1F, true);
        gui.addOrModifyItemRenderer(this.outcome, startX + 5 + 36, startY + 10 + 42, 1F, false);

        gui.addOrModifyItemRenderer(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.CUP.ordinal()), startX + 5 + 37, startY + 10 + 1, 1F, true);
        gui.addOrModifyItemRenderer(new ItemStack(InitItems.itemCoffee), startX + 5 + 1, startY + 10 + 1, 1F, true);
    }

    @Override
    public void getItemStacksForPage(List<ItemStack> list) {
        super.getItemStacksForPage(list);

        list.add(this.outcome);
    }
}
