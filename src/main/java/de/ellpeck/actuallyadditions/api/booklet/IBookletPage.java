/*
 * This file ("IBookletPage.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.booklet;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IBookletPage{

    void getItemStacksForPage(List<ItemStack> list);

    void getFluidStacksForPage(List<FluidStack> list);

    IBookletChapter getChapter();

    void setChapter(IBookletChapter chapter);

    @SideOnly(Side.CLIENT)
    String getInfoText();

    @SideOnly(Side.CLIENT)
    void mouseClicked(GuiBookletBase gui, int mouseX, int mouseY, int mouseButton);

    @SideOnly(Side.CLIENT)
    void mouseReleased(GuiBookletBase gui, int mouseX, int mouseY, int state);

    @SideOnly(Side.CLIENT)
    void mouseClickMove(GuiBookletBase gui, int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);

    @SideOnly(Side.CLIENT)
    void actionPerformed(GuiBookletBase gui, GuiButton button);

    @SideOnly(Side.CLIENT)
    void initGui(GuiBookletBase gui, int startX, int startY);

    @SideOnly(Side.CLIENT)
    void updateScreen(GuiBookletBase gui, int startX, int startY, int pageTimer);

    @SideOnly(Side.CLIENT)
    void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks);

    @SideOnly(Side.CLIENT)
    void drawScreenPost(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks);

    boolean shouldBeOnLeftSide();

    String getIdentifier();

    String getWebLink();

    IBookletPage addTextReplacement(String key, String value);

    IBookletPage addTextReplacement(String key, float value);

    IBookletPage addTextReplacement(String key, int value);

    int getSortingPriority();
}
