package de.ellpeck.actuallyadditions.mod.jei;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.mod.inventory.gui.TexturedButton;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public abstract class RecipeWrapperWithButton implements IRecipeWrapper {

    protected final TexturedButton theButton;

    public RecipeWrapperWithButton() {
        this.theButton = new TexturedButton(GuiBooklet.RES_LOC_GADGETS, 23782, this.getButtonX(), this.getButtonY(), 0, 0, 20, 20);
    }

    public abstract int getButtonX();

    public abstract int getButtonY();

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        if (this.theButton.mousePressed(minecraft, mouseX, mouseY)) {
            this.theButton.playPressSound(minecraft.getSoundHandler());

            IBookletPage page = this.getPage();
            if (page != null) {
                Minecraft.getMinecraft().displayGuiScreen(BookletUtils.createBookletGuiFromPage(Minecraft.getMinecraft().currentScreen, page));
                return true;
            }
        }
        return false;
    }

    public abstract IBookletPage getPage();

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        this.theButton.drawButton(minecraft, mouseX, mouseY, 0F);
    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (this.theButton.isMouseOver()) {
            return Collections.singletonList(StringUtil.localize("booklet." + ActuallyAdditions.MODID + ".clickToSeeRecipe"));
        } else {
            return Collections.emptyList();
        }
    }
}
