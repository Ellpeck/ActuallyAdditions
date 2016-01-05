/*
 * This file ("IBookletGui.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.internal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import net.minecraft.item.ItemStack;

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
     * @param renderTransferButton if the "show more info"-text and clickable part should exist-
     */
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    void renderTooltipAndTransferButton(BookletPage from, ItemStack stack, int x, int y, boolean renderTransferButton, boolean mousePressed);

    int getXSize();

    int getYSize();

    int getGuiLeft();

    int getGuiTop();

    void drawTexturedModalRect(int startX, int startY, int u, int v, int xSize, int ySize);

    EntrySet getCurrentEntrySet();
}
