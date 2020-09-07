/*
 * This file ("GuiBookletBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.booklet.internal;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public abstract class GuiBookletBase extends Screen {

    protected GuiBookletBase(ITextComponent titleIn) {
        super(new StringTextComponent(""));
    }

    public abstract void renderScaledAsciiString(String text, int x, int y, int color, boolean shadow, float scale);

    public abstract void renderSplitScaledAsciiString(String text, int x, int y, int color, boolean shadow, float scale, int length);

    public abstract List<Button> getButtonList();

    public abstract int getGuiLeft();

    public abstract int getGuiTop();

    public abstract int getSizeX();

    public abstract int getSizeY();

    public abstract void addOrModifyItemRenderer(ItemStack renderedStack, int x, int y, float scale, boolean shouldTryTransfer);

    public abstract float getSmallFontSize();

    public abstract float getMediumFontSize();

    public abstract float getLargeFontSize();
}
