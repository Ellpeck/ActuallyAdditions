/*
 * This file ("FilterSettingsGui.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
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

    public FilterSettingsGui(FilterSettings settings, int x, int y, List<GuiButton> buttonList){
        this.theSettings = settings;

        this.whitelistButton = new SmallerButton(this.theSettings.whitelistButtonId, x, y, "");
        buttonList.add(this.whitelistButton);

        this.metaButton = new SmallerButton(this.theSettings.metaButtonId, x, y+18, "");
        buttonList.add(this.metaButton);

        this.nbtButton = new SmallerButton(this.theSettings.nbtButtonId, x, y+36, "");
        buttonList.add(this.nbtButton);

        this.update();
    }

    public void update(){
        this.whitelistButton.displayString = (this.theSettings.isWhitelist ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"W";
        this.metaButton.displayString = (this.theSettings.respectMeta ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"M";
        this.nbtButton.displayString = (this.theSettings.respectNBT ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"N";
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
            GuiUtils.drawHoveringText(Collections.singletonList(TextFormatting.BOLD+(this.theSettings.respectMeta ? "Respecting" : "Ignoring")+" Metadata"), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
        else if(this.nbtButton.isMouseOver()){
            GuiUtils.drawHoveringText(Collections.singletonList(TextFormatting.BOLD+(this.theSettings.respectNBT ? "Respecting" : "Ignoring")+" NBT"), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
        }
    }
}
