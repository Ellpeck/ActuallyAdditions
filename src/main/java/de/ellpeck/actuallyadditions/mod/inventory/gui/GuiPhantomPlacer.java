/*
 * This file ("GuiPhantomPlacer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerPhantomPlacer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPhantomPlacer;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiPhantomPlacer extends GuiWtfMojang<ContainerPhantomPlacer> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_breaker");
    private final TileEntityPhantomPlacer placer;

    public GuiPhantomPlacer(ContainerPhantomPlacer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.placer = container.placer;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void init() {
        super.init();

        if (!this.placer.isBreaker) {
            this.addButton(new Button(0, this.guiLeft + 63, this.guiTop + 75, 50, 20, this.getSide()));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.placer.isBreaker) {
            this.buttonList.get(0).displayString = this.getSide();
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        super.render(matrices, mouseX, mouseY, partialTicks);

        if (!this.placer.isBreaker && this.buttonList.get(0).isMouseOver()) {
            String loc = "info." + ActuallyAdditions.MODID + ".placer.sides";

            List<String> textList = new ArrayList<>();
            textList.add(TextFormatting.GOLD + StringUtil.localize(loc + ".1"));
            textList.addAll(this.font.listFormattedStringToWidth(StringUtil.localize(loc + ".2"), 200));
            this.drawHoveringText(textList, mouseX, mouseY);
        }
    }

    @Override
    protected void actionPerformed(Button button) throws IOException {
        if (!this.placer.isBreaker) {
            PacketHandlerHelper.sendButtonPacket(this.placer, button.id);
        }
    }

    private String getSide() {
        return GuiInputter.SIDES[this.placer.side + 1];
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.xSize, -10, this.placer);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);
    }
}
