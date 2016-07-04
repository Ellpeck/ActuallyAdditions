/*
 * This file ("IBookletGui.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.internal;

import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * This is a helper interface for BookletPage
 * This is not supposed to be implemented.
 * <p>
 * Can be cast to GuiScreen.
 */
public interface IBookletGui{

    /**
     * This method should be used when drawing an ItemStack to a booklet page
     * It displays the hoverover text of the item and also contains the "show more info"-text and clickable part
     *
     * @param renderTransferButton if the "show more info"-text and clickable part should exist
     */
    @SideOnly(Side.CLIENT)
    void renderTooltipAndTransferButton(BookletPage from, ItemStack stack, int x, int y, boolean renderTransferButton, boolean mousePressed);

    int getXSize();

    int getYSize();

    int getGuiLeft();

    int getGuiTop();

    void drawRect(int startX, int startY, int u, int v, int xSize, int ySize);

    IEntrySet getCurrentEntrySet();

    List<GuiButton> getButtonList();
}
