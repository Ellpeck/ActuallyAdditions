/*
 * This file ("GuiGrinder.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.inventory.CrusherContainer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCrusher;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class CrusherScreen extends AAScreen<CrusherContainer> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_grinder");
    private static final ResourceLocation RES_LOC_DOUBLE = AssetUtil.getGuiLocation("gui_grinder_double");
    private final TileEntityCrusher tileGrinder;
    private final boolean isDouble;
    private EnergyDisplay energy;

    private Button buttonAutoSplit;

    public CrusherScreen(CrusherContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.tileGrinder = container.tileGrinder;
        this.isDouble = container.isDouble;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + (this.isDouble
            ? 13
            : 42), this.topPos + 5, this.tileGrinder.storage);

        if (this.isDouble) {
            this.buttonAutoSplit = new Buttons.SmallerButton( this.leftPos - 10, this.topPos, new TextComponent("S"), (button) -> actionPerformed(0));
            this.addRenderableWidget(this.buttonAutoSplit);
        }

        titleLabelX = (int) (imageWidth / 2.0f - font.width(title) / 2.0f);
        titleLabelY = -10;
    }

    protected void actionPerformed(int id) {
        if (this.isDouble && id == 0) {
            PacketHandlerHelper.sendButtonPacket(this.tileGrinder, id);
        }
    }

    @Override
    public void containerTick() {
        super.containerTick();

        if (this.isDouble) {
            this.buttonAutoSplit.setMessage(new TextComponent("S").withStyle(this.tileGrinder.isAutoSplit
                ? ChatFormatting.DARK_GREEN
                : ChatFormatting.RED));
        }
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int x, int y, float f) {
        super.render(matrixStack, x, y, f);
        this.energy.render(matrixStack, x, y);

        if (this.isDouble && this.buttonAutoSplit.isMouseOver(x,y)) {

            drawString(matrixStack, font, new TranslatableComponent("info.actuallyadditions.gui.autosplititems." + (tileGrinder.isAutoSplit?"on":"off")).withStyle(ChatFormatting.BOLD), x , y, 0xffffff);
        }
    }

    @Override
    public void renderBg(PoseStack matrices, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        RenderSystem.setShaderTexture(0, this.isDouble
            ? RES_LOC_DOUBLE
            : RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.tileGrinder.firstCrushTime > 0) {
            int i = this.tileGrinder.getFirstTimeToScale(23);
            this.blit(matrices, this.leftPos + (this.isDouble
                ? 51
                : 80), this.topPos + 40, 176, 0, 24, i);
        }
        if (this.isDouble) {
            if (this.tileGrinder.secondCrushTime > 0) {
                int i = this.tileGrinder.getSecondTimeToScale(23);
                this.blit(matrices, this.leftPos + 101, this.topPos + 40, 176, 22, 24, i);
            }
        }

        this.energy.draw(matrices);
    }

    public static class CrusherDoubleScreen extends CrusherScreen {

        public CrusherDoubleScreen(CrusherContainer crusherContainer, Inventory inventory, Component tile) {
            super(crusherContainer, inventory, tile);
        }
    }
}
