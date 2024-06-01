///*
// * This file ("PageCrusherRecipe.java") is part of the Actually Additions mod for Minecraft.
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
//import de.ellpeck.actuallyadditions.mod.crafting.CrushingRecipe;
//import de.ellpeck.actuallyadditions.mod.util.StackUtil;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.fml.client.gui.GuiUtils;
//
//import java.util.List;
//
//public class PageCrusherRecipe extends BookletPage {
//
//    private final CrushingRecipe recipe;
//    private int counter = 0;
//    private int rotate = 0;
//    private final ItemStack[] stacks;
//
//    public PageCrusherRecipe(int localizationKey, CrushingRecipe recipe) {
//        super(localizationKey);
//        this.recipe = recipe;
//        this.stacks = recipe.getInput().getItems();
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
//        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
//
//        gui.getMinecraft().getTextureManager().bind(GuiBooklet.RES_LOC_GADGETS);
//        GuiUtils.drawTexturedModalRect(startX + 38, startY + 6, 136, 0, 52, 74, 0);
//
//        gui.renderScaledAsciiString("(" + StringUtil.localize("booklet.actuallyadditions.crusherRecipe") + ")", startX + 36, startY + 85, 0, false, gui.getMediumFontSize());
//
//        PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 100);
//
//        if (this.counter++ % 50 == 0) {
//            gui.addOrModifyItemRenderer(this.stacks[this.rotate++ % this.stacks.length], startX + 38 + 18, startY + 6 + 1, 1F, true);
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void init(GuiBookletBase gui, int startX, int startY) {
//        super.init(gui, startX, startY);
//
//        if (this.recipe != null) {
//            gui.addOrModifyItemRenderer(this.stacks[this.rotate++ % this.stacks.length], startX + 38 + 18, startY + 6 + 1, 1F, true);
//            gui.addOrModifyItemRenderer(this.recipe.getOutputOne(), startX + 38 + 4, startY + 6 + 53, 1F, false);
//
//            if (StackUtil.isValid(this.recipe.getOutputTwo())) {
//                gui.addOrModifyItemRenderer(this.recipe.getOutputTwo(), startX + 38 + 30, startY + 6 + 53, 1F, false);
//            }
//        }
//    }
//
//    @Override
//    public void getItemStacksForPage(List<ItemStack> list) {
//        super.getItemStacksForPage(list);
//
//        if (this.recipe != null) {
//            list.add(this.recipe.getOutputOne());
//
//            if (StackUtil.isValid(this.recipe.getOutputTwo())) {
//                list.add(this.recipe.getOutputTwo());
//            }
//        }
//    }
//}
