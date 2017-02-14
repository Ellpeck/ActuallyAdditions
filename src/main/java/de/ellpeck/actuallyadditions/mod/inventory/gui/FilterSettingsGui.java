/*
 * This file ("FilterSettingsGui.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiInputter.SmallerButton;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class FilterSettingsGui extends Gui{

    private final FilterSettings theSettings;

    public SmallerButton whitelistButton;
    public SmallerButton metaButton;
    public SmallerButton nbtButton;
    public SmallerButton modButton;
    public SmallerButton oredictButton;

    public FilterSettingsGui(FilterSettings settings, int x, int y, List<GuiButton> buttonList){
        this.theSettings = settings;

        this.whitelistButton = new SmallerButton(this.theSettings.whitelistButtonId, x, y, "", true);
        buttonList.add(this.whitelistButton);
        y += 14;
        this.metaButton = new SmallerButton(this.theSettings.metaButtonId, x, y, "", true);
        buttonList.add(this.metaButton);
        y += 14;
        this.nbtButton = new SmallerButton(this.theSettings.nbtButtonId, x, y, "", true);
        buttonList.add(this.nbtButton);
        y += 14;
        this.oredictButton = new SmallerButton(this.theSettings.oredictButtonId, x, y, "", true);
        buttonList.add(this.oredictButton);
        y += 15;
        this.modButton = new SmallerButton(this.theSettings.modButtonId, x, y, "", true);
        buttonList.add(this.modButton);

        this.update();
    }

    public void update(){
        this.whitelistButton.displayString = (this.theSettings.isWhitelist ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"WH";
        this.metaButton.displayString = (this.theSettings.respectMeta ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"ME";
        this.nbtButton.displayString = (this.theSettings.respectNBT ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"NB";
        this.modButton.displayString = (this.theSettings.respectMod ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"MO";
        this.oredictButton.displayString = (this.theSettings.respectOredict == 0 ? TextFormatting.RED : (this.theSettings.respectOredict == 1 ? TextFormatting.GREEN : TextFormatting.DARK_GREEN))+"OR";
    }

    public void drawHover(int mouseX, int mouseY){
        Minecraft mc = Minecraft.getMinecraft();

        if(this.whitelistButton.isMouseOver()){
            List<String> list = new ArrayList<String>();
            list.add(TextFormatting.BOLD+(this.theSettings.isWhitelist ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.whitelist") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.blacklist")));
            list.addAll(mc.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".gui.whitelistInfo"), 200));
            GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
        else if(this.metaButton.isMouseOver()){
            GuiUtils.drawHoveringText(Collections.singletonList(TextFormatting.BOLD+(this.theSettings.respectMeta ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.respectMeta") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.ignoreMeta"))), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
        else if(this.nbtButton.isMouseOver()){
            GuiUtils.drawHoveringText(Collections.singletonList(TextFormatting.BOLD+(this.theSettings.respectNBT ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.respectNBT") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.ignoreNBT"))), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
        else if(this.modButton.isMouseOver()){
            List<String> list = new ArrayList<String>();
            list.add(TextFormatting.BOLD+(this.theSettings.respectMod ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.respectMod") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.ignoreMod")));
            
            list.addAll(mc.fontRendererObj.listFormattedStringToWidth(StringUtil.localize("info."+ModUtil.MOD_ID+".gui.respectModInfo"), 200));

            GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
        else if(this.oredictButton.isMouseOver()){
            List<String> list = new ArrayList<String>();
            list.add(TextFormatting.BOLD+(this.theSettings.respectOredict == 0 ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.ignoreOredict") : (this.theSettings.respectOredict == 1 ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.respectOredictSoft") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.respectOredictHard"))));
            
            String type = null;
            if(this.theSettings.respectOredict == 1){
                type = "one";
            }
            else if(this.theSettings.respectOredict == 2){
                type = "all";
            }

            if(type != null){
            	list.addAll(mc.fontRendererObj.listFormattedStringToWidth(StringUtil.localize("info."+ModUtil.MOD_ID+".gui.respectOredictInfo."+type), 200));
            }
            GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
    }
}
