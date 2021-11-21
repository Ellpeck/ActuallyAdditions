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

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.CrusherContainer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCrusher;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrusherScreen extends GuiWtfMojang<CrusherContainer> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_grinder");
    private static final ResourceLocation RES_LOC_DOUBLE = AssetUtil.getGuiLocation("gui_grinder_double");
    private final TileEntityCrusher tileGrinder;
    private final boolean isDouble;
    private EnergyDisplay energy;

    private Button buttonAutoSplit;

    public CrusherScreen(CrusherContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
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
            this.buttonAutoSplit = new Buttons.SmallerButton( this.leftPos - 10, this.topPos, new StringTextComponent("S"), (button) -> actionPerformed(0));
            this.addButton(this.buttonAutoSplit);
        }
    }

    protected void actionPerformed(int id) {
        if (this.isDouble && id == 0) {
            PacketHandlerHelper.sendButtonPacket(this.tileGrinder, id);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isDouble) {
            this.buttonAutoSplit.setMessage(new StringTextComponent("S").withStyle(this.tileGrinder.isAutoSplit
                ? TextFormatting.DARK_GREEN
                : TextFormatting.RED));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float f) {
        super.render(matrixStack, x, y, f);
        this.energy.render(matrixStack, x, y);

        if (this.isDouble && this.buttonAutoSplit.isMouseOver(x,y)) {

            drawString(matrixStack, font, new TranslationTextComponent("info.actuallyadditions.gui.autosplititems." + (tileGrinder.isAutoSplit?"on":"off")).withStyle(TextFormatting.BOLD), x , y, 0xffffff);
        }
    }

    @Override
    public void renderLabels(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.tileGrinder);
    }

    @Override
    public void renderBg(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(this.isDouble
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

        public CrusherDoubleScreen(CrusherContainer crusherContainer, PlayerInventory inventory, ITextComponent tile) {
            super(crusherContainer, inventory, tile);
        }
    }
}
