///*
// * This file ("PageEmpowerer.java") is part of the Actually Additions mod for Minecraft.
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
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
//import de.ellpeck.actuallyadditions.mod.crafting.EmpowererRecipe;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.fml.client.gui.GuiUtils;
//
//import java.util.List;
//
//public class PageEmpowerer extends BookletPage {
//
//    private final EmpowererRecipe recipe;
//    private int counter = 0;
//    private int rotate = 0;
//    ItemStack[] inputs;
//    ItemStack[] stand1;
//    ItemStack[] stand2;
//    ItemStack[] stand3;
//    ItemStack[] stand4;
//
//    public PageEmpowerer(int localizationKey, EmpowererRecipe recipe) {
//        super(localizationKey);
//        this.recipe = recipe;
//        if (recipe != null) {
//            this.inputs = recipe.getInput().getItems();
//            this.stand1 = recipe.getStandOne().getItems();
//            this.stand2 = recipe.getStandTwo().getItems();
//            this.stand3 = recipe.getStandThree().getItems();
//            this.stand4 = recipe.getStandFour().getItems();
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
//        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
//
//        gui.getMinecraft().getTextureManager().bind(GuiBooklet.RES_LOC_GADGETS);
//        GuiUtils.drawTexturedModalRect(startX + 5, startY + 10, 117, 74, 116, 72, 0);
//
//        gui.renderScaledAsciiString("(" + StringUtil.localize("booklet.actuallyadditions.empowererRecipe") + ")", startX + 6, startY + 85, 0, false, gui.getMediumFontSize());
//
//        PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 100);
//        if (this.recipe != null) {
//            this.updateInputs(gui, startX, startY);
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void init(GuiBookletBase gui, int startX, int startY) {
//        super.init(gui, startX, startY);
//
//        if (this.recipe != null) {
//            gui.addOrModifyItemRenderer(this.stand1[0], startX + 5 + 26, startY + 10 + 1, 1F, true);
//            gui.addOrModifyItemRenderer(this.stand2[0], startX + 5 + 1, startY + 10 + 26, 1F, true);
//            gui.addOrModifyItemRenderer(this.stand3[0], startX + 5 + 51, startY + 10 + 26, 1F, true);
//            gui.addOrModifyItemRenderer(this.stand4[0], startX + 5 + 26, startY + 10 + 51, 1F, true);
//
//            gui.addOrModifyItemRenderer(this.inputs[0], startX + 5 + 26, startY + 10 + 26, 1F, true);
//            gui.addOrModifyItemRenderer(this.recipe.getOutput(), startX + 5 + 96, startY + 10 + 26, 1F, false);
//        }
//    }
//
//    private void updateInputs(GuiBookletBase gui, int startX, int startY) {
//        if (this.counter++ % 50 == 0) {
//            this.rotate++;
//            gui.addOrModifyItemRenderer(this.stand1[this.rotate % this.stand1.length], startX + 5 + 26, startY + 10 + 1, 1F, true);
//            gui.addOrModifyItemRenderer(this.stand2[this.rotate % this.stand2.length], startX + 5 + 1, startY + 10 + 26, 1F, true);
//            gui.addOrModifyItemRenderer(this.stand3[this.rotate % this.stand3.length], startX + 5 + 51, startY + 10 + 26, 1F, true);
//            gui.addOrModifyItemRenderer(this.stand4[this.rotate % this.stand4.length], startX + 5 + 26, startY + 10 + 51, 1F, true);
//
//            gui.addOrModifyItemRenderer(this.inputs[this.rotate % this.inputs.length], startX + 5 + 26, startY + 10 + 26, 1F, true);
//        }
//    }
//
//    @Override
//    public void getItemStacksForPage(List<ItemStack> list) {
//        super.getItemStacksForPage(list);
//
//        if (this.recipe != null) {
//            list.add(this.recipe.getOutput());
//        }
//    }
//
//    @Override
//    public int getSortingPriority() {
//        return 20;
//    }
//}
