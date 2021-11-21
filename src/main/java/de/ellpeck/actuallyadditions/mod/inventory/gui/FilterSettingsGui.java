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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FilterSettingsGui extends AbstractGui {

    private final FilterSettings theSettings;

    public Buttons.SmallerButton whitelistButton;
    public Buttons.SmallerButton metaButton;
    public Buttons.SmallerButton nbtButton;
    public Buttons.SmallerButton modButton;
    public Buttons.SmallerButton oredictButton;

    public FilterSettingsGui(FilterSettings settings, int x, int y, List<Button> buttonList) {
        this.theSettings = settings;

        this.whitelistButton = new Buttons.SmallerButton( x, y, new TranslationTextComponent(""), true, Button::onPress); //TODO these need translation keys
        buttonList.add(this.whitelistButton);
        y += 14;
        this.metaButton = new Buttons.SmallerButton( x, y, new TranslationTextComponent(""), true, Button::onPress); //TODO also button actions
        buttonList.add(this.metaButton);
        y += 14;
        this.nbtButton = new Buttons.SmallerButton( x, y, new TranslationTextComponent(""), true, Button::onPress);
        buttonList.add(this.nbtButton);
        y += 14;
        this.oredictButton = new Buttons.SmallerButton( x, y, new TranslationTextComponent(""), true, Button::onPress);
        buttonList.add(this.oredictButton);
        y += 15;
        this.modButton = new Buttons.SmallerButton( x, y, new TranslationTextComponent(""), true, Button::onPress);
        buttonList.add(this.modButton);

        this.tick();
    }

    public void tick() {
        this.whitelistButton.setMessage(new StringTextComponent("WH").withStyle(this.theSettings.isWhitelist
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED));
        this.whitelistButton.setMessage(new StringTextComponent("ME").withStyle(this.theSettings.respectMeta
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED));
        this.whitelistButton.setMessage(new StringTextComponent("NB").withStyle(this.theSettings.respectNBT
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED));
        this.whitelistButton.setMessage(new StringTextComponent("MO").withStyle(this.theSettings.respectMod
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED));
        this.whitelistButton.setMessage(new StringTextComponent("OR").withStyle(this.theSettings.respectOredict == 0
            ? TextFormatting.DARK_GREEN
            : this.theSettings.respectOredict == 1
            ? TextFormatting.GREEN
            : TextFormatting.DARK_GREEN));
    }

    public void drawHover(int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();

        if (this.whitelistButton.isMouseOver(mouseX, mouseY)) {
            List<String> list = new ArrayList<>();
            list.add(TextFormatting.BOLD + (this.theSettings.isWhitelist
                ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.whitelist")
                : StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.blacklist")));
            //list.addAll(mc.font.substrByWidth(StringUtil.localizeFormatted("info." + ActuallyAdditions.MODID + ".gui.whitelistInfo"), 200));
            //GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.font);
        } else if (this.metaButton.isMouseOver(mouseX, mouseY)) {
            //GuiUtils.drawHoveringText(Collections.singletonList(TextFormatting.BOLD + (this.theSettings.respectMeta
            //    ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.respectMeta")
            //    : StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.ignoreMeta"))), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.font);
        } else if (this.nbtButton.isMouseOver(mouseX, mouseY)) {
            //GuiUtils.drawHoveringText(Collections.singletonList(TextFormatting.BOLD + (this.theSettings.respectNBT
            //    ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.respectNBT")
            //    : StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.ignoreNBT"))), mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.font);
        } else if (this.modButton.isMouseOver(mouseX, mouseY)) {
            List<String> list = new ArrayList<>();
            list.add(TextFormatting.BOLD + (this.theSettings.respectMod
                ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.respectMod")
                : StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.ignoreMod")));

            //list.addAll(mc.font.listFormattedStringToWidth(StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.respectModInfo"), 200));

            //GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.font);
        } else if (this.oredictButton.isMouseOver(mouseX, mouseY)) {
            List<String> list = new ArrayList<>();
            list.add(TextFormatting.BOLD + (this.theSettings.respectOredict == 0
                ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.ignoreOredict")
                : this.theSettings.respectOredict == 1
                    ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.respectOredictSoft")
                    : StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.respectOredictHard")));

            String type = null;
            if (this.theSettings.respectOredict == 1) {
                type = "one";
            } else if (this.theSettings.respectOredict == 2) {
                type = "all";
            }

            if (type != null) {
                //list.addAll(mc.font.listFormattedStringToWidth(StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.respectOredictInfo." + type), 200));
            }
            //GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.font);
        }
    }
}
