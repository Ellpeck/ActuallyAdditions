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
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface IBookletPage {

    void getItemStacksForPage(List<ItemStack> list);

    void getFluidStacksForPage(List<FluidStack> list);

    IBookletChapter getChapter();

    void setChapter(IBookletChapter chapter);

    @OnlyIn(Dist.CLIENT)
    String getInfoText();

    @OnlyIn(Dist.CLIENT)
    void mouseClicked(GuiBookletBase gui, int mouseX, int mouseY, int mouseButton);

    @OnlyIn(Dist.CLIENT)
    void mouseReleased(GuiBookletBase gui, int mouseX, int mouseY, int state);

    @OnlyIn(Dist.CLIENT)
    void mouseClickMove(GuiBookletBase gui, int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);

    @OnlyIn(Dist.CLIENT)
    void actionPerformed(GuiBookletBase gui, Button button);

    @OnlyIn(Dist.CLIENT)
    void initGui(GuiBookletBase gui, int startX, int startY);

    @OnlyIn(Dist.CLIENT)
    void updateScreen(GuiBookletBase gui, int startX, int startY, int pageTimer);

    @OnlyIn(Dist.CLIENT)
    void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks);

    @OnlyIn(Dist.CLIENT)
    void drawScreenPost(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks);

    boolean shouldBeOnLeftSide();

    String getIdentifier();

    String getWebLink();

    IBookletPage addTextReplacement(String key, String value);

    IBookletPage addTextReplacement(String key, float value);

    IBookletPage addTextReplacement(String key, int value);

    int getSortingPriority();
}
