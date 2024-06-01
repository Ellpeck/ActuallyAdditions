///*
// * This file ("PageFurnace.java") is part of the Actually Additions mod for Minecraft.
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
//import de.ellpeck.actuallyadditions.mod.util.StackUtil;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.datafix.fixes.FurnaceRecipes;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.fml.client.gui.GuiUtils;
//
//import java.util.List;
//import java.util.Map;
//
//public class PageFurnace extends BookletPage {
//
//    private final ItemStack input;
//    private final ItemStack stack;
//
//    public PageFurnace(int localizationKey, ItemStack stack) {
//        this(localizationKey, stack, 0);
//    }
//
//    public PageFurnace(int localizationKey, ItemStack stack, int priority) {
//        super(localizationKey, priority);
//        this.stack = stack;
//        this.input = getInputForOutput(stack);
//    }
//
//    private static ItemStack getInputForOutput(ItemStack stack) {
//        for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
//            ItemStack stack = entry.getValue();
//            if (StackUtil.isValid(stack)) {
//                if (stack.sameItem(stack)) {
//                    return entry.getKey();
//                }
//            }
//        }
//        return ItemStack.EMPTY;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
//        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
//
//        gui.getMinecraft().getTextureManager().bind(GuiBooklet.RES_LOC_GADGETS);
//        GuiUtils.drawTexturedModalRect(startX + 23, startY + 10, 0, 146, 80, 26, 0);
//
//        gui.renderScaledAsciiString("(" + StringUtil.localize("booklet.actuallyadditions.furnaceRecipe") + ")", startX + 32, startY + 42, 0, false, gui.getMediumFontSize());
//
//        PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 57);
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void init(GuiBookletBase gui, int startX, int startY) {
//        super.init(gui, startX, startY);
//
//        gui.addOrModifyItemRenderer(this.input, startX + 23 + 1, startY + 10 + 5, 1F, true);
//        gui.addOrModifyItemRenderer(this.stack, startX + 23 + 59, startY + 10 + 5, 1F, false);
//    }
//
//    @Override
//    public void getItemStacksForPage(List<ItemStack> list) {
//        super.getItemStacksForPage(list);
//
//        list.add(this.stack);
//    }
//}
