///*
// * This file ("BookmarkButton.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.booklet.button;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.platform.GlStateManager;
//import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
//import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
//import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiPage;
//import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
//import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
//import de.ellpeck.actuallyadditions.mod.util.StackUtil;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.gui.widget.button.Button;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.text.StringTextComponent;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.fml.client.gui.GuiUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@OnlyIn(Dist.CLIENT)
//public class BookmarkButton extends Button {
//
//    private final GuiBooklet booklet;
//    public IBookletPage assignedPage;
//
//    public BookmarkButton(int x, int y, GuiBooklet booklet) {
//        super(x, y, 16, 16, StringComponent.empty(), btn -> {
//            BookmarkButton button = (BookmarkButton) btn;
//            if (button.assignedPage != null) {
//                if (Screen.hasShiftDown()) {
//                    button.assignedPage = null;
//                } else if (!(button.booklet instanceof GuiPage) || ((GuiPage) button.booklet).pages[0] != button.assignedPage) {
//                    GuiPage gui = BookletUtils.createPageGui(button.booklet.previousScreen, button.booklet, button.assignedPage);
//                    Minecraft.getInstance().setScreen(gui);
//                }
//            } else {
//                if (button.booklet instanceof GuiPage) {
//                    button.assignedPage = ((GuiPage) button.booklet).pages[0];
//                }
//            }
//        });
//        this.booklet = booklet;
//    }
//
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
//        if (this.visible) {
//            Minecraft.getInstance().getTextureManager().bind(GuiBooklet.RES_LOC_GADGETS);
//            GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            this.isHovered = mouseX >= this.x && mouseY >= this.y && this.x < this.x + this.width && this.y < this.y + this.height;
//            int offset = this.isHovered
//                ? 1
//                : 0;
//
//            GlStateManager._enableBlend();
//            GlStateManager._blendFuncSeparate(770, 771, 1, 0);
//            GlStateManager._blendFunc(770, 771);
//            int renderHeight = 25;
//            guiGraphics.blit(matrices, this.x, this.y, 224 + (this.assignedPage == null
//                ? 0
//                : 16), 14 - renderHeight + offset * renderHeight, this.width, renderHeight);
//
//            // TODO: FIX THIS
//            //            this.mouseDragged(minecraft, mouseX, mouseY);
//
//            if (this.assignedPage != null) {
//                ItemStack display = this.assignedPage.getChapter().getDisplayItemStack();
//                if (StackUtil.isValid(display)) {
//                    GlStateManager._pushMatrix();
//                    AssetUtil.renderStackToGui(display, this.x + 2, this.y + 1, 0.725F);
//                    GlStateManager._popMatrix();
//                }
//            }
//        }
//    }
//
//    public void drawHover(MatrixStack stack, int mouseX, int mouseY) {
//        if (this.isMouseOver(mouseX, mouseY)) {
//            List<String> list = new ArrayList<>();
//
//            if (this.assignedPage != null) {
//                IBookletChapter chapter = this.assignedPage.getChapter();
//
//                list.add(TextFormatting.GOLD + chapter.getLocalizedName() + ", Page " + (chapter.getPageIndex(this.assignedPage) + 1));
//                list.add(StringUtil.localize("booklet.actuallyadditions.bookmarkButton.bookmark.openDesc"));
//                list.add(TextFormatting.ITALIC + StringUtil.localize("booklet.actuallyadditions.bookmarkButton.bookmark.removeDesc"));
//            } else {
//                list.add(TextFormatting.GOLD + StringUtil.localize("booklet.actuallyadditions.bookmarkButton.noBookmark.name"));
//
//                if (this.booklet instanceof GuiPage) {
//                    list.add(StringUtil.localize("booklet.actuallyadditions.bookmarkButton.noBookmark.pageDesc"));
//                } else {
//                    list.add(StringUtil.localize("booklet.actuallyadditions.bookmarkButton.noBookmark.notPageDesc"));
//                }
//            }
//
//            Minecraft mc = Minecraft.getInstance();
//            GuiUtils.drawHoveringText(stack, list.stream().map(StringTextComponent::new).collect(Collectors.toList()), mouseX, mouseY, mc.screen.width, mc.screen.height, -1, mc.font);
//        }
//    }
//}
