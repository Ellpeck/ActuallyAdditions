///*
// * This file ("RecipeWrapperWithButton.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.jei;
//
//import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.booklet.gui.GuiBooklet;
//import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
//import de.ellpeck.actuallyadditions.mod.inventory.gui.TexturedButton;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.client.Minecraft;
//
//import javax.annotation.Nullable;
//import java.util.Collections;
//import java.util.List;
//
//public abstract class RecipeWrapperWithButton implements IRecipeWr {
//
//    protected final TexturedButton theButton;
//
//    public RecipeWrapperWithButton() {
//        this.theButton = new TexturedButton(GuiBooklet.RES_LOC_GADGETS, this.getButtonX(), this.getButtonY(), 0, 0, 20, 20, btn -> {
//        });
//    }
//
//    public abstract int getButtonX();
//
//    public abstract int getButtonY();
//
//    @Override
//    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
//        if (this.theButton.mousePressed(minecraft, mouseX, mouseY)) {
//            this.theButton.playPressSound(minecraft.getSoundHandler());
//
//            IBookletPage page = this.getPage();
//            if (page != null) {
//                Minecraft.getInstance().displayGuiScreen(BookletUtils.createBookletGuiFromPage(Minecraft.getInstance().currentScreen, page));
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public abstract IBookletPage getPage();
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//        this.theButton.drawButton(minecraft, mouseX, mouseY, 0F);
//    }
//
//    @Nullable
//    @Override
//    public List<String> getTooltipStrings(int mouseX, int mouseY) {
//        if (this.theButton.isMouseOver()) {
//            return Collections.singletonList(StringUtil.localize("booklet.actuallyadditions.clickToSeeRecipe"));
//        } else {
//            return Collections.emptyList();
//        }
//    }
//}
