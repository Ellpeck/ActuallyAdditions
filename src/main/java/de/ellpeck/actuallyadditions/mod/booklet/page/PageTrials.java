/*
 * This file ("PageTrials.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageTrials extends BookletPage{

    @SideOnly(Side.CLIENT)
    private GuiButton button;

    private final int buttonId;

    public PageTrials(int localizationKey, boolean button, boolean text){
        super(localizationKey);

        if(!text){
            this.setNoText();
        }

        if(button){
            this.buttonId = PageLinkButton.nextButtonId;
            PageLinkButton.nextButtonId++;
        }
        else{
            this.buttonId = -1;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initGui(GuiBookletBase gui, int startX, int startY){
        super.initGui(gui, startX, startY);

        if(this.buttonId >= 0){
            this.button = new GuiButton(this.buttonId, startX+125/2-50, startY+120, 100, 20, "");
            gui.getButtonList().add(this.button);
            this.updateButton();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks){
        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
        PageTextOnly.renderTextToPage(gui, this, startX+6, startY+5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected String getLocalizationKey(){
        return "booklet."+ModUtil.MOD_ID+".trials."+this.chapter.getIdentifier()+".text."+this.localizationKey;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void actionPerformed(GuiBookletBase gui, GuiButton button){
        if(this.buttonId >= 0 && button.id == this.buttonId){
            EntityPlayer player = Minecraft.getMinecraft().player;
            PlayerSave data = PlayerData.getDataFromPlayer(player);
            String id = this.chapter.getIdentifier();

            boolean completed = data.completedTrials.contains(id);
            if(completed){
                data.completedTrials.remove(id);
            }
            else{
                data.completedTrials.add(id);
            }
            this.updateButton();

            PacketHandlerHelper.sendPlayerDataToServer(false, 2);
        }
        else{
            super.actionPerformed(gui, button);
        }
    }

    @SideOnly(Side.CLIENT)
    private void updateButton(){
        if(this.buttonId >= 0 && this.button != null){
            EntityPlayer player = Minecraft.getMinecraft().player;
            PlayerSave data = PlayerData.getDataFromPlayer(player);

            boolean completed = data.completedTrials.contains(this.chapter.getIdentifier());
            if(completed){
                this.button.displayString = TextFormatting.DARK_GREEN+StringUtil.localize("booklet."+ModUtil.MOD_ID+".trialFinishButton.completed.name");
            }
            else{
                this.button.displayString = TextFormatting.DARK_RED+StringUtil.localize("booklet."+ModUtil.MOD_ID+".trialFinishButton.uncompleted.name");
            }

        }
    }
}
