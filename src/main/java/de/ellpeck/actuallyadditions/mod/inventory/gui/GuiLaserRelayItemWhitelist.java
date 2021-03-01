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

import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiInputter.SmallerButton;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiLaserRelayItemWhitelist extends GuiWtfMojang<ContainerLaserRelayItemWhitelist> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_laser_relay_item_whitelist");
    private final TileEntityLaserRelayItemWhitelist tile;

    private FilterSettingsGui leftFilter;
    private FilterSettingsGui rightFilter;

    private Button buttonSmartWhitelistLeft;
    private Button buttonSmartWhitelistRight;

    public GuiLaserRelayItemWhitelist(ContainerLaserRelayItemWhitelist container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.tile = container.tile;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        this.leftFilter.update();
        this.rightFilter.update();
    }

    @Override
    public void init() {
        super.init();

        this.leftFilter = new FilterSettingsGui(this.tile.leftFilter, this.guiLeft + 3, this.guiTop + 6, this.buttonList);
        this.rightFilter = new FilterSettingsGui(this.tile.rightFilter, this.guiLeft + 157, this.guiTop + 6, this.buttonList);

        this.buttonSmartWhitelistLeft = new SmallerButton(2, this.guiLeft + 3, this.guiTop + 79, "S");
        this.buttonSmartWhitelistRight = new SmallerButton(3, this.guiLeft + 157, this.guiTop + 79, "S");
        this.addButton(this.buttonSmartWhitelistLeft);
        this.addButton(this.buttonSmartWhitelistRight);
    }

    @Override
    public void actionPerformed(Button button) {
        PacketHandlerHelper.sendButtonPacket(this.tile, button.id);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        if (this.buttonSmartWhitelistLeft.isMouseOver() || this.buttonSmartWhitelistRight.isMouseOver()) {
            List<String> list = new ArrayList<>();
            list.add(TextFormatting.BOLD + StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.smart"));
            list.addAll(this.font.listFormattedStringToWidth(StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.smartInfo"), 200));
            this.drawHoveringText(list, x, y);
        }

        this.leftFilter.drawHover(x, y);
        this.rightFilter.drawHover(x, y);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.font, this.xSize, -10, this.tile);

        String s1 = StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.inbound");
        String s2 = StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.outbound");
        this.font.drawString(s1, 46 - this.font.getStringWidth(s1) / 2, 80, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        this.font.drawString(s2, 131 - this.font.getStringWidth(s2) / 2, 80, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);

    }
}
