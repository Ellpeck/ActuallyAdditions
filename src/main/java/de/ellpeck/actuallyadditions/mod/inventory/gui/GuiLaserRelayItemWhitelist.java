/*
 * This file ("GuiLaserRelayItemWhitelist.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItemAdvanced;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiLaserRelayItemWhitelist extends AAScreen<ContainerLaserRelayItemWhitelist> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_laser_relay_item_whitelist");
    private final Component inboundText = Component.translatable("info.actuallyadditions.gui.inbound");
    private final Component outboundText = Component.translatable("info.actuallyadditions.gui.outbound");
    private final TileEntityLaserRelayItemAdvanced tile;

    private FilterSettingsGui leftFilter;
    private FilterSettingsGui rightFilter;

    private Button buttonSmartWhitelistLeft;
    private Button buttonSmartWhitelistRight;

    public GuiLaserRelayItemWhitelist(ContainerLaserRelayItemWhitelist container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.tile = container.tile;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void containerTick() {
        super.containerTick();

        this.leftFilter.tick();
        this.rightFilter.tick();
    }

    @Override
    public void init() {
        super.init();

        this.leftFilter = new FilterSettingsGui(this.tile.leftFilter, this.leftPos + 3, this.topPos + 6, true, this::addRenderableWidget, this::buttonClicked,  0);
        this.rightFilter = new FilterSettingsGui(this.tile.rightFilter, this.leftPos + 157, this.topPos + 6, true, this::addRenderableWidget, this::buttonClicked,  4);

        this.buttonSmartWhitelistLeft = this.addRenderableWidget(Button.builder(
                        Component.literal("S"),
                        (button) -> {
                            PacketHandlerHelper.sendButtonPacket(this.tile, 2);
                        }).bounds(this.leftPos + 3, this.topPos + 79, 16, 16)
                .build());

        this.buttonSmartWhitelistRight = this.addRenderableWidget(Button.builder(
                        Component.literal("S"),
                        (button) -> {
                            PacketHandlerHelper.sendButtonPacket(this.tile, 3);
                        }).bounds(this.leftPos + 157, this.topPos + 79, 16, 16)
                .build());
    }

    public void buttonClicked(int id) {
        CompoundTag data = new CompoundTag();
        data.putInt("ButtonID", id);
        data.putInt("PlayerID", Minecraft.getInstance().player.getId());
        data.putString("WorldID", Minecraft.getInstance().level.dimension().location().toString());
        PacketDistributor.SERVER.noArg().send(new PacketClientToServer(data, PacketHandler.GUI_BUTTON_TO_CONTAINER_HANDLER));
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.leftFilter.drawHover(guiGraphics, mouseX, mouseY);
        this.rightFilter.drawHover(guiGraphics, mouseX, mouseY);

        if (this.buttonSmartWhitelistLeft.isMouseOver(mouseX, mouseY) || this.buttonSmartWhitelistRight.isMouseOver(mouseX, mouseY)) {
            List<FormattedCharSequence> list = new ArrayList<>();
            list.add(Component.translatable("info.actuallyadditions.gui.smart").withStyle(ChatFormatting.BOLD).getVisualOrderText());
            list.addAll(this.font.split(Component.translatable("info.actuallyadditions.gui.smartInfo"), 200));
            guiGraphics.renderTooltip(this.font, list, mouseX, mouseY); //renderTooltip
        }
    }

    @Override
    public void renderLabels(@Nonnull GuiGraphics guiGraphics, int x, int y) {
        AssetUtil.displayNameString(guiGraphics, this.font, this.imageWidth, -10, this.title.getString());

        guiGraphics.drawString(font, inboundText, 46 - this.font.width(inboundText) / 2, 80, 0x404040, false);
        guiGraphics.drawString(font, outboundText, 131 - this.font.width(outboundText) / 2, 80, 0x404040, false);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);
    }
}
