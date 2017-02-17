/*
 * This file ("BookletPage.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class BookletPage implements IBookletPage{

    protected final HashMap<String, String> textReplacements = new HashMap<String, String>();
    protected final int localizationKey;
    private final int priority;
    private final List<ItemStack> itemsForPage = new ArrayList<ItemStack>();
    private final List<FluidStack> fluidsForPage = new ArrayList<FluidStack>();
    protected IBookletChapter chapter;
    protected boolean hasNoText;

    public BookletPage(int localizationKey){
        this(localizationKey, 0);
    }

    public BookletPage(int localizationKey, int priority){
        this.localizationKey = localizationKey;
        this.priority = priority;
    }

    @Override
    public void getItemStacksForPage(List<ItemStack> list){
        list.addAll(this.itemsForPage);
    }

    @Override
    public void getFluidStacksForPage(List<FluidStack> list){
        list.addAll(this.fluidsForPage);
    }

    @Override
    public IBookletChapter getChapter(){
        return this.chapter;
    }

    @Override
    public void setChapter(IBookletChapter chapter){
        this.chapter = chapter;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getInfoText(){
        if(this.hasNoText){
            return null;
        }

        String base = StringUtil.localize(this.getLocalizationKey());
        base = base.replaceAll("<imp>", TextFormatting.DARK_GREEN+"");
        base = base.replaceAll("<item>", TextFormatting.BLUE+"");
        base = base.replaceAll("<r>", TextFormatting.BLACK+"");
        base = base.replaceAll("<n>", "\n");
        base = base.replaceAll("<i>", TextFormatting.ITALIC+"");
        base = base.replaceAll("<tifisgrin>", TextFormatting.DARK_RED+""+TextFormatting.UNDERLINE); //This is fucking important so go read it now

        for(Map.Entry<String, String> entry : this.textReplacements.entrySet()){
            base = base.replaceAll(entry.getKey(), entry.getValue());
        }
        return base;
    }

    @SideOnly(Side.CLIENT)
    protected String getLocalizationKey(){
        return "booklet."+ModUtil.MOD_ID+".chapter."+this.chapter.getIdentifier()+".text."+this.localizationKey;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void mouseClicked(GuiBookletBase gui, int mouseX, int mouseY, int mouseButton){

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void mouseReleased(GuiBookletBase gui, int mouseX, int mouseY, int state){

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void mouseClickMove(GuiBookletBase gui, int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void actionPerformed(GuiBookletBase gui, GuiButton button){

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initGui(GuiBookletBase gui, int startX, int startY){

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateScreen(GuiBookletBase gui, int startX, int startY, int pageTimer){

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks){

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPost(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks){

    }

    @Override
    public boolean shouldBeOnLeftSide(){
        return (this.chapter.getPageIndex(this)+1)%2 != 0;
    }

    @Override
    public String getIdentifier(){
        return this.chapter.getIdentifier()+"."+this.chapter.getPageIndex(this);
    }

    @Override
    public String getWebLink(){
        return "http://ellpeck.de/actaddmanual#"+this.chapter.getIdentifier();
    }

    public BookletPage setNoText(){
        this.hasNoText = true;
        return this;
    }

    public BookletPage addFluidToPage(Fluid fluid){
        this.fluidsForPage.add(new FluidStack(fluid, 1));
        return this;
    }

    public BookletPage addItemsToPage(Block... blocks){
        for(Block block : blocks){
            this.addItemsToPage(new ItemStack(block));
        }
        return this;
    }

    public BookletPage addItemsToPage(ItemStack... stacks){
        Collections.addAll(this.itemsForPage, stacks);
        return this;
    }

    @Override
    public BookletPage addTextReplacement(String key, String value){
        this.textReplacements.put(key, value);
        return this;
    }

    @Override
    public BookletPage addTextReplacement(String key, float value){
        return this.addTextReplacement(key, Float.toString(value));
    }

    @Override
    public BookletPage addTextReplacement(String key, int value){
        return this.addTextReplacement(key, Integer.toString(value));
    }

    @Override
    public int getSortingPriority(){
        return this.priority;
    }
}