package de.ellpeck.actuallyadditions.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageTextOnly extends BookletPage {

    public PageTextOnly(int localizationKey, int priority) {
        super(localizationKey, priority);
    }

    public PageTextOnly(int localizationKey) {
        super(localizationKey);
    }

    @SideOnly(Side.CLIENT)
    public static void renderTextToPage(GuiBookletBase gui, BookletPage page, int x, int y) {
        String text = page.getInfoText();
        if (text != null && !text.isEmpty()) {
            gui.renderSplitScaledAsciiString(text, x, y, 0, false, gui.getMediumFontSize(), 120);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
        renderTextToPage(gui, this, startX + 6, startY + 5);
    }
}
