/*
 * This file ("GuiXPSolidifier.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerXPSolidifier;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityXPSolidifier;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class GuiXPSolidifier extends GuiWtfMojang<ContainerXPSolidifier> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_xp_solidifier");
    private final TileEntityXPSolidifier solidifier;

    public GuiXPSolidifier(ContainerXPSolidifier container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.solidifier = container.solidifier;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void init() {
        super.init();

        Button buttonOne = new GuiInputter.SmallerButton(0, this.guiLeft + 62, this.guiTop + 44, "1");
        Button buttonFive = new GuiInputter.SmallerButton(1, this.guiLeft + 80, this.guiTop + 44, "5");
        Button buttonTen = new GuiInputter.SmallerButton(2, this.guiLeft + 99, this.guiTop + 44, "10");
        Button buttonTwenty = new GuiInputter.SmallerButton(3, this.guiLeft + 62, this.guiTop + 61, "20");
        Button buttonThirty = new GuiInputter.SmallerButton(4, this.guiLeft + 80, this.guiTop + 61, "30");
        Button buttonForty = new GuiInputter.SmallerButton(5, this.guiLeft + 99, this.guiTop + 61, "40");
        Button buttonFifty = new GuiInputter.SmallerButton(6, this.guiLeft + 62, this.guiTop + 78, "50");
        Button buttonSixtyFour = new GuiInputter.SmallerButton(7, this.guiLeft + 80, this.guiTop + 78, "64");
        Button buttonAll = new GuiInputter.SmallerButton(8, this.guiLeft + 99, this.guiTop + 78, "All");

        this.addButton(buttonOne);
        this.addButton(buttonFive);
        this.addButton(buttonTen);
        this.addButton(buttonTwenty);
        this.addButton(buttonThirty);
        this.addButton(buttonForty);
        this.addButton(buttonFifty);
        this.addButton(buttonSixtyFour);
        this.addButton(buttonAll);
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.xSize, -10, this.solidifier);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrices, float f, int x, int y) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);

        drawCenteredString(matrices, this.font, Integer.toString(this.solidifier.amount), this.guiLeft + 88, this.guiTop + 30, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @Override
    public void actionPerformed(Button button) {
        PacketHandlerHelper.sendButtonPacket(this.solidifier, button.id);

        this.solidifier.onButtonPressed(button.id, Minecraft.getInstance().player);
    }
}
