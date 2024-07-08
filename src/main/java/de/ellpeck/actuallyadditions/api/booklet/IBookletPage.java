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
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

//TODO: We're using Patchouli API for the new booklets. Do we still need this?
public interface IBookletPage {

    void getItemStacksForPage(List<ItemStack> list);

    void getFluidStacksForPage(List<FluidStack> list);

    IBookletChapter getChapter();

    void setChapter(IBookletChapter chapter);

    
    String getInfoText();

    
    void mouseClicked(GuiBookletBase gui, int mouseX, int mouseY, int mouseButton);

    
    void mouseReleased(GuiBookletBase gui, int mouseX, int mouseY, int state);

    
    void mouseClickMove(GuiBookletBase gui, int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);

    
    void actionPerformed(GuiBookletBase gui, Button button);

    
    void init(GuiBookletBase gui, int startX, int startY);

    
    void updateScreen(GuiBookletBase gui, int startX, int startY, int pageTimer);

    
    void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks);

    
    void drawScreenPost(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks);

    boolean shouldBeOnLeftSide();

    String getIdentifier();

    String getWebLink();

    IBookletPage addTextReplacement(String key, String value);

    IBookletPage addTextReplacement(String key, float value);

    IBookletPage addTextReplacement(String key, int value);

    int getSortingPriority();
}
