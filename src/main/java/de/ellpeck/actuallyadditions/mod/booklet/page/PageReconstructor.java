///*
// * This file ("PageReconstructor.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.booklet.page;
//
//import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
//import de.ellpeck.actuallyadditions.api.recipe.LensConversionRecipe;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import de.ellpeck.actuallyadditions.mod.util.Util;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.fml.client.gui.GuiUtils;
//
//import java.util.List;
//
//public class PageReconstructor extends BookletPage {
//
//    private final LensConversionRecipe recipe;
//    private boolean isWildcard;
//    private int counter = 0;
//    private int rotate = 0;
//    private ItemStack[] stacks;
//
//    public PageReconstructor(int localizationKey, LensConversionRecipe recipe) {
//        super(localizationKey);
//        this.recipe = recipe;
//        if (recipe != null) {
//            this.stacks = recipe.getInput().getItems();
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
//        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
//
//        gui.getMinecraft().getTextureManager().bind(GuiBooklet.RES_LOC_GADGETS);
//        GuiUtils.drawTexturedModalRect(startX + 30, startY + 10, 80, 146, 68, 48, 0);
//
//        gui.renderScaledAsciiString("(" + StringUtil.localize("booklet.actuallyadditions.reconstructorRecipe") + ")", startX + 6, startY + 63, 0, false, gui.getMediumFontSize());
//
//        PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 88);
//        if (this.recipe != null) {
//            if (this.counter++ % 50 == 0) {
//                gui.addOrModifyItemRenderer(this.stacks[this.rotate++ % this.stacks.length], startX + 30 + 1, startY + 10 + 13, 1F, true);
//            }
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void init(GuiBookletBase gui, int startX, int startY) {
//        super.init(gui, startX, startY);
//
//        if (this.recipe != null) {
//            gui.addOrModifyItemRenderer(this.stacks[0], startX + 30 + 1, startY + 10 + 13, 1F, true);
//            gui.addOrModifyItemRenderer(this.recipe.getOutput(), startX + 30 + 47, startY + 10 + 13, 1F, false);
//        }
//    }
//
//    @Override
//    public void getItemStacksForPage(List<ItemStack> list) {
//        super.getItemStacksForPage(list);
//
//        if (this.recipe != null) {
//            ItemStack copy = this.recipe.getOutput().copy();
//            if (this.isWildcard) {
//                copy.setDamageValue(Util.WILDCARD);
//            }
//            list.add(copy);
//        }
//    }
//
//    public BookletPage setWildcard() {
//        this.isWildcard = true;
//        return this;
//    }
//
//    @Override
//    public int getSortingPriority() {
//        return 20;
//    }
//}
