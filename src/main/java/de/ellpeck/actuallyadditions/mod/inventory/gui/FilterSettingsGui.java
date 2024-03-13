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
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class FilterSettingsGui {

    private final FilterSettings theSettings;

    public Button allowButton;
    public Button modButton;

    public FilterSettingsGui(FilterSettings settings, int x, int y, Consumer<AbstractButton> buttonConsumer, Consumer<Integer> clickConsumer, int idOffset) {
        this.theSettings = settings;

        this.allowButton = Button.builder(Component.literal("A"), $ -> {
            theSettings.isAllowFilter = !theSettings.isAllowFilter;
            clickConsumer.accept(idOffset);
        })
                .bounds(x, y, 16, 12).build();
        buttonConsumer.accept(this.allowButton);
        y += 14;
        this.modButton = Button.builder(Component.literal("MO"), $ -> {
            theSettings.respectMod = !theSettings.respectMod;
            clickConsumer.accept(idOffset + 1);
                })
                .bounds(x, y, 16, 12).build();
        buttonConsumer.accept(this.modButton);

        this.tick();
    }

/*    public void buttonClicked(int id) {
        CompoundTag data = new CompoundTag();
        data.putInt("ButtonID", id);
        data.putInt("PlayerID", Minecraft.getInstance().player.getId());
        data.putString("WorldID", Minecraft.getInstance().level.dimension().location().toString());
        PacketDistributor.SERVER.noArg().send(new PacketClientToServer(data, PacketHandler.GUI_BUTTON_TO_CONTAINER_HANDLER));
    }*/

    public void tick() {
        if (this.theSettings.isAllowFilter)
            this.allowButton.setMessage(Component.literal("A").withStyle(ChatFormatting.DARK_GREEN));
        else
            this.allowButton.setMessage(Component.literal("D").withStyle(ChatFormatting.RED));
        this.modButton.setMessage(Component.literal("MO").withStyle(this.theSettings.respectMod
            ? ChatFormatting.DARK_GREEN
            : ChatFormatting.RED));
    }

    public void drawHover(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();
        List<Component> list = new ArrayList<>();
        if (this.allowButton.isMouseOver(mouseX, mouseY)) {
            list.add((this.theSettings.isAllowFilter
                ? Component.translatable("info." + ActuallyAdditions.MODID + ".gui.allow")
                : Component.translatable("info." + ActuallyAdditions.MODID + ".gui.deny")).withStyle(ChatFormatting.BOLD));
            list.add(Component.translatable("info." + ActuallyAdditions.MODID + ".gui.filterInfo"));
        } else if (this.modButton.isMouseOver(mouseX, mouseY)) {
            list.add((this.theSettings.respectMod
                ? Component.translatable("info." + ActuallyAdditions.MODID + ".gui.respectMod")
                : Component.translatable("info." + ActuallyAdditions.MODID + ".gui.ignoreMod")).withStyle(ChatFormatting.BOLD));
            list.add(Component.translatable("info." + ActuallyAdditions.MODID + ".gui.respectModInfo"));


        }
        //TODO tooltips still jank
        if (!list.isEmpty()) {
            guiGraphics.renderComponentTooltip(mc.font, list, mouseX, mouseY);
        }
    }
}
