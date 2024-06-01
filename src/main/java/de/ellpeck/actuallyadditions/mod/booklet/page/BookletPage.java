///*
// * This file ("BookletPage.java") is part of the Actually Additions mod for Minecraft.
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
//import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
//import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
//import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.block.Block;
//import net.minecraft.client.gui.widget.button.Button;
//import net.minecraft.fluid.Fluid;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.fluids.FluidStack;
//
//import java.util.*;
//
//public class BookletPage implements IBookletPage {
//
//    protected final HashMap<String, String> textReplacements = new HashMap<>();
//    protected final int localizationKey;
//    private final int priority;
//    private final List<ItemStack> itemsForPage = new ArrayList<>();
//    private final List<FluidStack> fluidsForPage = new ArrayList<>();
//    protected IBookletChapter chapter;
//    protected boolean hasNoText;
//
//    public BookletPage(int localizationKey) {
//        this(localizationKey, 0);
//    }
//
//    public BookletPage(int localizationKey, int priority) {
//        this.localizationKey = localizationKey;
//        this.priority = priority;
//    }
//
//    @Override
//    public void getItemStacksForPage(List<ItemStack> list) {
//        list.addAll(this.itemsForPage);
//    }
//
//    @Override
//    public void getFluidStacksForPage(List<FluidStack> list) {
//        list.addAll(this.fluidsForPage);
//    }
//
//    @Override
//    public IBookletChapter getChapter() {
//        return this.chapter;
//    }
//
//    @Override
//    public void setChapter(IBookletChapter chapter) {
//        this.chapter = chapter;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public String getInfoText() {
//        if (this.hasNoText) {
//            return null;
//        }
//
//        String base = StringUtil.localize(this.getLocalizationKey());
//        base = base.replaceAll("<imp>", TextFormatting.DARK_GREEN + "");
//        base = base.replaceAll("<item>", TextFormatting.BLUE + "");
//        base = base.replaceAll("<r>", TextFormatting.BLACK + "");
//        base = base.replaceAll("<n>", "\n");
//        base = base.replaceAll("<i>", TextFormatting.ITALIC + "");
//        base = base.replaceAll("<tifisgrin>", TextFormatting.DARK_RED + "" + TextFormatting.UNDERLINE); //This is fucking important so go read it now
//
//        for (Map.Entry<String, String> entry : this.textReplacements.entrySet()) {
//            base = base.replaceAll(entry.getKey(), entry.getValue());
//        }
//        return base;
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    protected String getLocalizationKey() {
//        return "booklet.actuallyadditions.chapter." + this.chapter.getIdentifier() + ".text." + this.localizationKey;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void mouseClicked(GuiBookletBase gui, int mouseX, int mouseY, int mouseButton) {
//
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void mouseReleased(GuiBookletBase gui, int mouseX, int mouseY, int state) {
//
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void mouseClickMove(GuiBookletBase gui, int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
//
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void actionPerformed(GuiBookletBase gui, Button button) {
//
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void init(GuiBookletBase gui, int startX, int startY) {
//
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void updateScreen(GuiBookletBase gui, int startX, int startY, int pageTimer) {
//
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
//
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void drawScreenPost(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
//
//    }
//
//    @Override
//    public boolean shouldBeOnLeftSide() {
//        return (this.chapter.getPageIndex(this) + 1) % 2 != 0;
//    }
//
//    @Override
//    public String getIdentifier() {
//        return this.chapter.getIdentifier() + "." + this.chapter.getPageIndex(this);
//    }
//
//    @Override
//    public String getWebLink() {
//        return "http://ellpeck.de/actaddmanual#" + this.chapter.getIdentifier();
//    }
//
//    public BookletPage setNoText() {
//        this.hasNoText = true;
//        return this;
//    }
//
//    public BookletPage addFluidToPage(Fluid fluid) {
//        this.fluidsForPage.add(new FluidStack(fluid, 1));
//        return this;
//    }
//
//    public BookletPage addItemsToPage(Block... blocks) {
//        for (Block block : blocks) {
//            this.addItemsToPage(new ItemStack(block));
//        }
//        return this;
//    }
//
//    public BookletPage addItemsToPage(ItemStack... stacks) {
//        Collections.addAll(this.itemsForPage, stacks);
//        return this;
//    }
//
//    @Override
//    public BookletPage addTextReplacement(String key, String value) {
//        this.textReplacements.put(key, value);
//        return this;
//    }
//
//    @Override
//    public BookletPage addTextReplacement(String key, float value) {
//        return this.addTextReplacement(key, Float.toString(value));
//    }
//
//    @Override
//    public BookletPage addTextReplacement(String key, int value) {
//        return this.addTextReplacement(key, Integer.toString(value));
//    }
//
//    @Override
//    public int getSortingPriority() {
//        return this.priority;
//    }
//}
