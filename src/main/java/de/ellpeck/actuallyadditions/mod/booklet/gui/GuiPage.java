///*
// * This file ("GuiPage.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.booklet.gui;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.mojang.blaze3d.systems.RenderSystem;
//import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
//import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
//import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
//import de.ellpeck.actuallyadditions.mod.booklet.page.ItemDisplay;
//import de.ellpeck.actuallyadditions.mod.inventory.gui.TexturedButton;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.gui.widget.button.Button;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.awt.*;
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@OnlyIn(Dist.CLIENT)
//public class GuiPage extends GuiBooklet {
//
//    public final IBookletPage[] pages = new IBookletPage[2];
//    private final List<ItemDisplay> itemDisplays = new ArrayList<>();
//    private int pageTimer;
//
//    private Button buttonViewOnline;
//
//    public GuiPage(Screen previousScreen, GuiBookletBase parentPage, IBookletPage page1, IBookletPage page2) {
//        super(previousScreen, parentPage);
//
//        this.pages[0] = page1;
//        this.pages[1] = page2;
//    }
//
//    @Override
//    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
//        super.mouseClicked(mouseX, mouseY, mouseButton);
//
//        for (ItemDisplay display : this.itemDisplays) {
//            display.onMousePress(mouseButton, mouseX, mouseY);
//        }
//
//        for (IBookletPage page : this.pages) {
//            if (page != null) {
//                page.mouseClicked(this, mouseX, mouseY, mouseButton);
//            }
//        }
//    }
//
//    @Override
//    public void mouseReleased(int mouseX, int mouseY, int state) {
//        super.mouseReleased(mouseX, mouseY, state);
//
//        for (IBookletPage page : this.pages) {
//            if (page != null) {
//                page.mouseReleased(this, mouseX, mouseY, state);
//            }
//        }
//    }
//
//    @Override
//    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
//        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
//
//        for (IBookletPage page : this.pages) {
//            if (page != null) {
//                page.mouseClickMove(this, mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
//            }
//        }
//    }
///*
//    @Override
//    public void actionPerformed(GuiButton button) throws IOException {
//        if (button == this.buttonViewOnline) {
//            List<String> links = this.getWebLinks();
//            if (Desktop.isDesktopSupported()) {
//                for (String link : links) {
//                    try {
//                        Desktop.getDesktop().browse(new URI(link));
//                    } catch (Exception e) {
//                        ActuallyAdditions.LOGGER.error("Couldn't open website from Booklet page!", e);
//                    }
//                }
//            }
//        } else {
//            super.actionPerformed(button);
//
//            for (IBookletPage page : this.pages) {
//                if (page != null) {
//                    page.actionPerformed(this, button);
//                }
//            }
//        }
//    }
// */
//    @Override
//    public void init() {
//        this.itemDisplays.clear();
//        super.init();
//
//        List<String> links = this.getWebLinks();
//        if (links != null && !links.isEmpty()) {
//            this.buttonViewOnline = new TexturedButton(RES_LOC_GADGETS, this.guiLeft + this.xSize - 24, this.guiTop + this.ySize - 25, 0, 172, 16, 16, Collections.singletonList(TextFormatting.GOLD + StringUtil.localize("booklet.actuallyadditions.onlineButton.name")), btn -> {
//            });
//            this.addButton(this.buttonViewOnline);
//        }
//
//        for (int i = 0; i < this.pages.length; i++) {
//            IBookletPage page = this.pages[i];
//            if (page != null) {
//                page.init(this, this.guiLeft + 6 + i * 142, this.guiTop + 7);
//            }
//        }
//    }
//
//    private List<String> getWebLinks() {
//        List<String> links = new ArrayList<>();
//
//        for (IBookletPage page : this.pages) {
//            if (page != null) {
//                String link = page.getWebLink();
//                if (link != null && !links.contains(link)) {
//                    links.add(link);
//                }
//            }
//        }
//
//        return links;
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//
//        for (int i = 0; i < this.pages.length; i++) {
//            IBookletPage page = this.pages[i];
//            if (page != null) {
//                page.updateScreen(this, this.guiLeft + 6 + i * 142, this.guiTop + 7, this.pageTimer);
//            }
//        }
//
//        this.pageTimer++;
//    }
//
//    @Override
//    public void drawScreenPre(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
//        super.drawScreenPre(stack, mouseX, mouseY, partialTicks);
//
//        if (this.pages[0] != null) {
//            IBookletChapter chapter = this.pages[0].getChapter();
//            String name = chapter.getLocalizedName();
//            this.font.draw(stack, name, this.guiLeft + this.xSize / 2 - this.font.width(name) / 2, this.guiTop - 1, 0xFFFFFF);
//        }
//
//        for (int i = 0; i < this.pages.length; i++) {
//            IBookletPage page = this.pages[i];
//            if (page != null) {
//                IBookletChapter chapter = this.pages[i].getChapter();
//                String pageStrg = "Page " + (chapter.getPageIndex(this.pages[i]) + 1) + "/" + chapter.getAllPages().length;
//                this.renderScaledAsciiString(pageStrg, this.guiLeft + 25 + i * 136, this.guiTop + this.ySize - 7, 0xFFFFFF, false, this.getLargeFontSize());
//
//                RenderSystem.color3f(1f, 1f, 1f);
//                page.drawScreenPre(this, this.guiLeft + 6 + i * 142, this.guiTop + 7, mouseX, mouseY, partialTicks);
//            }
//        }
//        for (ItemDisplay display : this.itemDisplays) {
//            display.drawPre();
//        }
//    }
//
//    @Override
//    public void drawScreenPost(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
//        super.drawScreenPost(stack, mouseX, mouseY, partialTicks);
//
//        for (int i = 0; i < this.pages.length; i++) {
//            IBookletPage page = this.pages[i];
//            if (page != null) {
//                RenderSystem.color3f(1F, 1F, 1F);
//                page.drawScreenPost(this, this.guiLeft + 6 + i * 142, this.guiTop + 7, mouseX, mouseY, partialTicks);
//            }
//        }
//        for (ItemDisplay display : this.itemDisplays) {
//            display.drawPost(mouseX, mouseY);
//        }
//    }
//
//    @Override
//    public void addOrModifyItemRenderer(ItemStack renderedStack, int x, int y, float scale, boolean shouldTryTransfer) {
//        for (ItemDisplay display : this.itemDisplays) {
//            if (display.x == x && display.y == y && display.scale == scale) {
//                display.stack = renderedStack;
//                return;
//            }
//        }
//
//        this.itemDisplays.add(new ItemDisplay(this, x, y, scale, renderedStack, shouldTryTransfer));
//    }
//
//    @Override
//    public boolean hasPageLeftButton() {
//        IBookletPage page = this.pages[0];
//        if (page != null) {
//            IBookletChapter chapter = page.getChapter();
//            if (chapter != null) {
//                return chapter.getPageIndex(page) > 0;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onPageLeftButtonPressed() {
//        IBookletPage page = this.pages[0];
//        if (page != null) {
//            IBookletChapter chapter = page.getChapter();
//            if (chapter != null) {
//                IBookletPage[] pages = chapter.getAllPages();
//
//                int pageNumToOpen = chapter.getPageIndex(page) - 1;
//                if (pageNumToOpen >= 0 && pageNumToOpen < pages.length) {
//                    this.minecraft.setScreen(BookletUtils.createPageGui(this.previousScreen, this.parentPage, pages[pageNumToOpen]));
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean hasPageRightButton() {
//        IBookletPage page = this.pages[1];
//        if (page != null) {
//            IBookletChapter chapter = page.getChapter();
//            if (chapter != null) {
//                int pageIndex = chapter.getPageIndex(page);
//                int pageAmount = chapter.getAllPages().length;
//                return pageIndex + 1 < pageAmount;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onPageRightButtonPressed() {
//        IBookletPage page = this.pages[1];
//        if (page != null) {
//            IBookletChapter chapter = page.getChapter();
//            if (chapter != null) {
//                IBookletPage[] pages = chapter.getAllPages();
//
//                int pageNumToOpen = chapter.getPageIndex(page) + 1;
//                if (pageNumToOpen >= 0 && pageNumToOpen < pages.length) {
//                    this.minecraft.setScreen(BookletUtils.createPageGui(this.previousScreen, this.parentPage, pages[pageNumToOpen]));
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean hasBackButton() {
//        return true;
//    }
//
//    @Override
//    public void onBackButtonPressed() {
//        if (!hasShiftDown()) {
//            this.minecraft.setScreen(this.parentPage);
//        } else {
//            super.onBackButtonPressed();
//        }
//    }
//}
