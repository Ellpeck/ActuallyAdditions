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
import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItemAdvanced;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuiLaserRelayItemWhitelist extends AAScreen<ContainerLaserRelayItemWhitelist> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_laser_relay_item_whitelist");
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

//        this.leftFilter = new FilterSettingsGui(this.tile.leftFilter, this.leftPos + 3, this.topPos + 6, this.buttonList);
//        this.rightFilter = new FilterSettingsGui(this.tile.rightFilter, this.leftPos + 157, this.topPos + 6, this.buttonList);
//
//        this.buttonSmartWhitelistLeft = new Buttons.SmallerButton(2, this.leftPos + 3, this.topPos + 79, "S");
//        this.buttonSmartWhitelistRight = new Buttons.SmallerButton(3, this.leftPos + 157, this.topPos + 79, "S");
//        this.addButton(this.buttonSmartWhitelistLeft);
//        this.addButton(this.buttonSmartWhitelistRight);
    }
//
//    @Override
//    public void actionPerformed(Button button) {
//        PacketHandlerHelper.sendButtonPacket(this.tile, button.id);
//    }

    @Override
    public void render(@Nonnull PoseStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);
//
//        if (this.buttonSmartWhitelistLeft.isMouseOver() || this.buttonSmartWhitelistRight.isMouseOver()) {
//            List<String> list = new ArrayList<>();
//            list.add(TextFormatting.BOLD + StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.smart"));
//            list.addAll(this.font.listFormattedStringToWidth(StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.smartInfo"), 200));
//            this.drawHoveringText(list, x, y);
//        }

        this.leftFilter.drawHover(matrices, x, y);
        this.rightFilter.drawHover(matrices, x, y);
    }

    @Override
    public void renderLabels(@Nonnull PoseStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.tile);

        String s1 = I18n.get("info." + ActuallyAdditions.MODID + ".gui.inbound");
        String s2 = I18n.get("info." + ActuallyAdditions.MODID + ".gui.outbound");
        this.font.draw(matrices, s1, 46 - this.font.width(s1) / 2, 80, 0x404040);
        this.font.draw(matrices, s2, 131 - this.font.width(s2) / 2, 80, 0x404040);
    }

    @Override
    public void renderBg(PoseStack matrices, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        RenderSystem.setShaderTexture(0, RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

    }
}
