/*
 * This file ("GuiBag.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.SackContainer;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SackGui extends AAScreen<SackContainer> {
    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_bag");

    private final SackContainer container;
    private FilterSettingsGui filter;
    private Button buttonAutoInsert;

    public SackGui(SackContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 90 + 86;
        this.container = container;
    }

    @Override
    public void init() {
        super.init();

        this.filter = new FilterSettingsGui(this.container.filter, this.leftPos + 137, this.topPos + 10, true, this::addRenderableWidget, this::buttonClicked,  1);

        this.buttonAutoInsert = Button.builder(
                Component.literal("I")
                .withStyle(this.container.autoInsert? ChatFormatting.DARK_GREEN : ChatFormatting.RED),
                (button) -> {
                    this.container.autoInsert = !this.container.autoInsert;
                    this.buttonAutoInsert.setMessage(Component.literal(this.container.autoInsert? "I" : "O")
                            .withStyle(this.container.autoInsert? ChatFormatting.DARK_GREEN : ChatFormatting.RED));
                    this.buttonClicked(0);
                }).pos(leftPos - 21, topPos + 8).size(20, 20)
                .build();

        this.addRenderableWidget(this.buttonAutoInsert);
    }

    public void buttonClicked(int id) {
        CompoundTag data = new CompoundTag();
        data.putInt("ButtonID", id);
        data.putInt("PlayerID", Minecraft.getInstance().player.getId());
        data.putString("WorldID", Minecraft.getInstance().level.dimension().location().toString());
        PacketDistributor.SERVER.noArg().send(new PacketClientToServer(data, PacketHandler.GUI_BUTTON_TO_CONTAINER_HANDLER));
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.filter.tick();
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.filter.drawHover(guiGraphics, mouseX, mouseY);

        if (this.buttonAutoInsert.isMouseOver(mouseX, mouseY)) {
            List<Component> text = new ArrayList<>();
            text.add(Component.literal("Auto-Insert " + (this.container.autoInsert
                ? "On"
                : "Off")).withStyle(ChatFormatting.BOLD));
            text.add(Component.literal("Turn this on to make items that get picked up automatically go into the sack.")); //TODO how to word wrap these to 200?
            text.add(Component.literal("Note that this WON'T work when you are holding the sack in your hand.").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)); //TODO this too
            guiGraphics.renderTooltip(font, text, Optional.empty(), mouseX, mouseY); //TODO i have no idea what im doing here...
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 90, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 90);
    }
}
