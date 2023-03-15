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

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FilterSettingsGui extends AbstractGui {

    private final FilterSettings theSettings;

    public Buttons.SmallerButton whitelistButton;
    public Buttons.SmallerButton nbtButton;
    public Buttons.SmallerButton modButton;

    public FilterSettingsGui(FilterSettings settings, int x, int y, List<Widget> buttonList) {
        this.theSettings = settings;

        this.whitelistButton = new Buttons.SmallerButton( x, y, new StringTextComponent("WH"), true, Button::onPress); //TODO these need translation keys
        buttonList.add(this.whitelistButton);
        y += 14;
        this.nbtButton = new Buttons.SmallerButton( x, y, new StringTextComponent("NB"), true, Button::onPress);//TODO also button actions
        buttonList.add(this.nbtButton);
        y += 14;
        this.modButton = new Buttons.SmallerButton( x, y, new StringTextComponent("MO"), true, Button::onPress);
        buttonList.add(this.modButton);

        this.tick();
    }

    public void tick() {
        this.whitelistButton.setMessage(new StringTextComponent("WH").withStyle(this.theSettings.isWhitelist
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED));
        this.whitelistButton.setMessage(new StringTextComponent("NB").withStyle(this.theSettings.respectNBT
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED));
        this.whitelistButton.setMessage(new StringTextComponent("MO").withStyle(this.theSettings.respectMod
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED));
    }

    public void drawHover(MatrixStack stack, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();
        List<ITextComponent> list = new ArrayList<>();
        if (this.whitelistButton.isMouseOver(mouseX, mouseY)) {
            list.add((this.theSettings.isWhitelist
                ? new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".gui.whitelist")
                : new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".gui.blacklist")).withStyle(TextFormatting.BOLD));
            list.add(new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".gui.whitelistInfo"));
        } else if (this.nbtButton.isMouseOver(mouseX, mouseY)) {
            list.add((this.theSettings.respectNBT
                ? new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".gui.respectNBT")
                : new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".gui.ignoreNBT")).withStyle(TextFormatting.BOLD));
        } else if (this.modButton.isMouseOver(mouseX, mouseY)) {
            list.add((this.theSettings.respectMod
                ? new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".gui.respectMod")
                : new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".gui.ignoreMod")).withStyle(TextFormatting.BOLD));
            list.add(new TranslationTextComponent("info." + ActuallyAdditions.MODID + ".gui.respectModInfo"));


        }
        //TODO tooltips still jank
        if (!list.isEmpty())
            GuiUtils.drawHoveringText(stack, list, mouseX, mouseY, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), 200, mc.font);
    }
}
