/*
 * This file ("GuiMainPage.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.gui;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.mod.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.mod.booklet.button.EntryButton;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiMainPage extends GuiBooklet{

    private GuiButton tutorialButton;
    private String bookletName;
    private boolean showTutorial;

    public GuiMainPage(GuiScreen previousScreen){
        super(previousScreen, null);
    }

    @Override
    public void initGui(){
        super.initGui();

        int flavor = 1;
        if(this.mc.theWorld.rand.nextFloat() <= 0.1){
            flavor = MathHelper.getRandomIntegerInRange(this.mc.theWorld.rand, 2, 7);
        }
        this.bookletName = "info."+ModUtil.MOD_ID+".booklet.manualName.1."+flavor;

        PlayerSave data = PlayerData.getDataFromPlayer(this.mc.thePlayer);
        if(!data.didBookTutorial){
            this.showTutorial = true;

            this.tutorialButton = new GuiButton(666666, this.guiLeft+140/2-50, this.guiTop+146, 100, 20, "Please click me <3");
            this.buttonList.add(this.tutorialButton);
        }

        for(int i = 0; i < BUTTONS_PER_PAGE; i++){
            if(ActuallyAdditionsAPI.BOOKLET_ENTRIES.size() > i){
                IBookletEntry entry = ActuallyAdditionsAPI.BOOKLET_ENTRIES.get(i);
                this.buttonList.add(new EntryButton(i, this.guiLeft+156, this.guiTop+11+i*13, 115, 10, "- "+entry.getLocalizedNameWithFormatting(), null));
            }
            else{
                return;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        if(button instanceof EntryButton){
            if(ActuallyAdditionsAPI.BOOKLET_ENTRIES.size() > button.id){
                IBookletEntry entry = ActuallyAdditionsAPI.BOOKLET_ENTRIES.get(button.id);
                if(entry != null){
                    this.mc.displayGuiScreen(new GuiEntry(this.previousScreen, this, entry, 0, "", false));
                }
            }
        }
        else if(this.showTutorial && button == this.tutorialButton){
            if(this.hasBookmarkButtons()){
                if(!isShiftKeyDown()){
                    for(int i = 0; i < InitBooklet.chaptersIntroduction.length; i++){
                        this.bookmarkButtons[i].assignedPage = InitBooklet.chaptersIntroduction[i].getAllPages()[0];
                    }
                }
                this.showTutorial = false;
                this.tutorialButton.visible = false;

                PlayerSave data = PlayerData.getDataFromPlayer(this.mc.thePlayer);
                data.didBookTutorial = true;
                PacketHandlerHelper.sendPlayerDataPacket(this.mc.thePlayer, true, false);
            }
        }
        else{
            super.actionPerformed(button);
        }
    }

    @Override
    public void drawScreenPre(int mouseX, int mouseY, float partialTicks){
        super.drawScreenPre(mouseX, mouseY, partialTicks);

        String strg = TextFormatting.DARK_GREEN+StringUtil.localize(this.bookletName);
        this.fontRendererObj.drawString(strg, this.guiLeft+72-this.fontRendererObj.getStringWidth(strg)/2-3, this.guiTop+19, 0);
        strg = TextFormatting.DARK_GREEN+StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.manualName.2");
        this.fontRendererObj.drawString(strg, this.guiLeft+72-this.fontRendererObj.getStringWidth(strg)/2-3, this.guiTop+19+this.fontRendererObj.FONT_HEIGHT, 0);

        String versionStrg;
        String playerName = Minecraft.getMinecraft().thePlayer.getName();

        if(Util.isDevVersion()){
            versionStrg = "Dev's Edition";
        }
        else{
            String modVersion = Util.getMajorModVersion();
            if(playerName.equalsIgnoreCase("dqmhose")){
                versionStrg = "Pants Edition";
            }
            else if(playerName.equalsIgnoreCase("TwoOfEight") || playerName.equalsIgnoreCase("BootyToast")){
                versionStrg = "Illustrator's Edition";
            }
            else if(playerName.equalsIgnoreCase("KittyVanCat")){
                versionStrg = "Cat's Edition";
            }
            else if(playerName.equalsIgnoreCase("canitzp")){
                versionStrg = "P's Edition";
            }
            else if(playerName.equalsIgnoreCase("Ellpeck")){
                versionStrg = "Editor's Edition";
            }
            else if(playerName.equalsIgnoreCase("direwolf20")){
                versionStrg = "Edition 20";
            }
            else if(playerName.equalsIgnoreCase("dannydjdk") || playerName.equalsIgnoreCase("andrew_period")){
                versionStrg = "Derp's Edition";
            }
            else if(playerName.equalsIgnoreCase("mezz")){
                versionStrg = "Just Enough Editions";
            }
            else{
                versionStrg = StringUtil.localize("info."+ModUtil.MOD_ID+".booklet.edition")+" "+modVersion;
            }
        }
        strg = TextFormatting.GOLD+TextFormatting.ITALIC.toString()+"-"+versionStrg+"-";
        this.fontRendererObj.drawString(strg, this.guiLeft+72-this.fontRendererObj.getStringWidth(strg)/2-3, this.guiTop+40, 0);

        if(this.showTutorial){
            String text = TextFormatting.BLUE+"It looks like this is the first time you are using this manual. \nIf you click the button below, some useful bookmarks will be stored at the bottom of the GUI. You should definitely check them out to get started with "+ModUtil.NAME+"! \nIf you don't want this, shift-click the button.";
            this.renderSplitScaledAsciiString(text, this.guiLeft+11, this.guiTop+55, 0, false, 0.75F, 120);
        }
    }

    @Override
    public void addOrModifyItemRenderer(ItemStack renderedStack, int x, int y, float scale, boolean shouldTryTransfer){

    }
}
