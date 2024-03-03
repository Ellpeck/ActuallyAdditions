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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FilterSettingsGui {

    private final FilterSettings theSettings;

    public Buttons.SmallerButton whitelistButton;
    public Buttons.SmallerButton nbtButton;
    public Buttons.SmallerButton modButton;

    public FilterSettingsGui(FilterSettings settings, int x, int y, List<Renderable> buttonList) {
        this.theSettings = settings;

        this.whitelistButton = new Buttons.SmallerButton( x, y, Component.literal("WH"), true, Button::onPress); //TODO these need translation keys
        buttonList.add(this.whitelistButton);
        y += 14;
        this.nbtButton = new Buttons.SmallerButton( x, y, Component.literal("NB"), true, Button::onPress);//TODO also button actions
        buttonList.add(this.nbtButton);
        y += 14;
        this.modButton = new Buttons.SmallerButton( x, y, Component.literal("MO"), true, Button::onPress);
        buttonList.add(this.modButton);

        this.tick();
    }

    public void tick() {
        this.whitelistButton.setMessage(Component.literal("WH").withStyle(this.theSettings.isWhitelist
            ? ChatFormatting.DARK_GREEN
            : ChatFormatting.RED));
        this.whitelistButton.setMessage(Component.literal("NB").withStyle(this.theSettings.respectNBT
            ? ChatFormatting.DARK_GREEN
            : ChatFormatting.RED));
        this.whitelistButton.setMessage(Component.literal("MO").withStyle(this.theSettings.respectMod
            ? ChatFormatting.DARK_GREEN
            : ChatFormatting.RED));
    }

    public void drawHover(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();
        List<Component> list = new ArrayList<>();
        if (this.whitelistButton.isMouseOver(mouseX, mouseY)) {
            list.add((this.theSettings.isWhitelist
                ? Component.translatable("info." + ActuallyAdditions.MODID + ".gui.whitelist")
                : Component.translatable("info." + ActuallyAdditions.MODID + ".gui.blacklist")).withStyle(ChatFormatting.BOLD));
            list.add(Component.translatable("info." + ActuallyAdditions.MODID + ".gui.whitelistInfo"));
        } else if (this.nbtButton.isMouseOver(mouseX, mouseY)) {
            list.add((this.theSettings.respectNBT
                ? Component.translatable("info." + ActuallyAdditions.MODID + ".gui.respectNBT")
                : Component.translatable("info." + ActuallyAdditions.MODID + ".gui.ignoreNBT")).withStyle(ChatFormatting.BOLD));
        } else if (this.modButton.isMouseOver(mouseX, mouseY)) {
            list.add((this.theSettings.respectMod
                ? Component.translatable("info." + ActuallyAdditions.MODID + ".gui.respectMod")
                : Component.translatable("info." + ActuallyAdditions.MODID + ".gui.ignoreMod")).withStyle(ChatFormatting.BOLD));
            list.add(Component.translatable("info." + ActuallyAdditions.MODID + ".gui.respectModInfo"));


        }
        //TODO tooltips still jank
        if (!list.isEmpty()) {
            guiGraphics.renderComponentTooltip(mc.font, list, mouseX, mouseY); //TODO: Check if this is correct, used to call GuiUtils.drawHoveringText
        }
    }
}
