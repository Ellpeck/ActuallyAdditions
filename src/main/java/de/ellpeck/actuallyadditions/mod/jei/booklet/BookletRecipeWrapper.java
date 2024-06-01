///*
// * This file ("BookletRecipeWrapper.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.jei.booklet;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
//import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.jei.RecipeWrapperWithButton;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import mezz.jei.api.ingredients.IIngredients;
//import mezz.jei.api.ingredients.VanillaTypes;
//import net.minecraft.client.Minecraft;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.fluids.FluidStack;
//
//public class BookletRecipeWrapper extends RecipeWrapperWithButton {
//
//    public final IBookletPage thePage;
//
//    public BookletRecipeWrapper(IBookletPage page) {
//        this.thePage = page;
//    }
//
//    @Override
//    public void getIngredients(IIngredients ingredients) {
//        List<ItemStack> itemList = new ArrayList<>();
//        this.thePage.getItemStacksForPage(itemList);
//        ingredients.setInputs(VanillaTypes.ITEM, itemList);
//        ingredients.setOutputs(VanillaTypes.ITEM, itemList);
//
//        List<FluidStack> fluidList = new ArrayList<>();
//        this.thePage.getFluidStacksForPage(fluidList);
//        ingredients.setInputs(VanillaTypes.FLUID, fluidList);
//        ingredients.setOutputs(VanillaTypes.FLUID, fluidList);
//    }
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//        List<String> header = minecraft.fontRenderer.listFormattedStringToWidth(StringUtil.localize("container.nei.actuallyadditions.booklet.header").replaceAll("<item>", TextFormatting.BLUE + "").replaceAll("<r>", TextFormatting.BLACK + ""), 150);
//        for (int i = 0; i < header.size(); i++) {
//            minecraft.fontRenderer.drawString(header.get(i), 0, 17 + i * (minecraft.fontRenderer.FONT_HEIGHT + 1), 0, false);
//        }
//
//        int maxLines = 4;
//        IBookletChapter chapter = this.thePage.getChapter();
//        String aText = chapter.getAllPages()[0].getInfoText();
//        List<String> text = minecraft.fontRenderer.listFormattedStringToWidth(aText != null ? aText : TextFormatting.DARK_RED + StringUtil.localize("container.nei.actuallyadditions.booklet.noText"), 150);
//        for (int i = 0; i < Math.min(maxLines, text.size()); i++) {
//            minecraft.fontRenderer.drawString(text.get(i) + (i == maxLines - 1 && text.size() > maxLines ? TextFormatting.RESET + "" + TextFormatting.BLACK + "..." : ""), 0, 16 + 25 + i * (minecraft.fontRenderer.FONT_HEIGHT + 1), 0, false);
//        }
//        minecraft.fontRenderer.drawString(TextFormatting.ITALIC + chapter.getLocalizedName(), 25, 85, 0, false);
//        minecraft.fontRenderer.drawString(TextFormatting.ITALIC + "Page " + (chapter.getPageIndex(this.thePage) + 1), 25, 95, 0, false);
//
//        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
//    }
//
//    @Override
//    public int getButtonX() {
//        return 0;
//    }
//
//    @Override
//    public int getButtonY() {
//        return 84;
//    }
//
//    @Override
//    public IBookletPage getPage() {
//        return this.thePage;
//    }
//}
