/*
 * This file ("GuiFireworkBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFireworkBox;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// TODO: FIX ME
@OnlyIn(Dist.CLIENT)
public class GuiFireworkBox extends ContainerScreen<ContainerFireworkBox> {

    //    private final TileEntityFireworkBox tile;

    public GuiFireworkBox(ContainerFireworkBox screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        //        this.tile = tile.;
        this.xSize = 300;
        this.ySize = 120;
    }

    @Override
    public void init() {
        super.init();
        //
        //        this.addButton(new CustomSlider(this.guiLeft, this.guiTop, "Value Play", 0F, 5F, this.tile.intValuePlay, IntFormatter.INSTANCE));
        //        this.addButton(new CustomSlider(this.guiLeft, this.guiTop + 20, "Average Charge Amount", 1F, 4F, this.tile.chargeAmount, IntFormatter.INSTANCE));
        //        this.addButton(new CustomSlider(this.guiLeft, this.guiTop + 40, "Average Flight Time", 1F, 3F, this.tile.flightTime, IntFormatter.INSTANCE));
        //        this.addButton(new CustomSlider(this.guiLeft, this.guiTop + 60, "Effect Chance", 0F, 1F, this.tile.trailOrFlickerChance, null));
        //        this.addButton(new CustomSlider(this.guiLeft, this.guiTop + 80, "Flicker/Trail Ratio", 0F, 1F, this.tile.flickerChance, null));
        //        this.addButton(new CustomSlider(this.guiLeft, this.guiTop + 100, "Color Amount", 1, 6, this.tile.colorAmount, IntFormatter.INSTANCE));
        //
        //        this.addButton(new CustomSlider(this.guiLeft + 150, this.guiTop, "Small Ball", 0F, 1F, this.tile.typeChance0, null));
        //        this.addButton(new CustomSlider(this.guiLeft + 150, this.guiTop + 20, "Large Ball", 0F, 1F, this.tile.typeChance1, null));
        //        this.addButton(new CustomSlider(this.guiLeft + 150, this.guiTop + 40, "Star Shape", 0F, 1F, this.tile.typeChance2, null));
        //        this.addButton(new CustomSlider(this.guiLeft + 150, this.guiTop + 60, "Creeper Shape", 0F, 1F, this.tile.typeChance3, null));
        //        this.addButton(new CustomSlider(this.guiLeft + 150, this.guiTop + 80, "Burst", 0F, 1F, this.tile.typeChance4, null));
        //
        //        this.addButton(new CustomSlider(this, 11, this.guiLeft + 150, this.guiTop + 100, "Area of Effect", 0, 4, this.tile.areaOfEffect, IntFormatter.INSTANCE));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    // TODO: FIX;
    //    @Override
    //    public void setEntryValue(int id, float value) {
    //        Button button = this.buttonList.get(id);
    //        if (button instanceof GuiSlider) {
    //            if (!((GuiSlider) button).isMouseDown) {
    //                System.out.println("SETTING VALUE FOR " + id + "!!");
    //                PacketHandlerHelper.sendNumberPacket(this.tile, value, id);
    //            }
    //        }
    //    }

    //    @Override
    //    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    //
    //    }
    //
    //    @Override
    //    public void drawGuiContainerForegroundLayer(int x, int y) {
    //        AssetUtil.displayNameString(this.font, this.xSize, -10, this.tile);
    //    }
    //
    //    @Override
    //    public void setEntryValue(int id, boolean value) {

    //}

    //    @Override
    //    public void setEntryValue(int id, String value) {
    //
    //    }

    // TODO: FIX
    private static class CustomSlider extends AbstractSlider {
        public CustomSlider(int x, int y, int width, int height, ITextComponent message, double defaultValue) {
            super(x, y, width, height, message, defaultValue);
        }


        //        private final GuiResponder responder;
        //
        //        public CustomSlider(GuiResponder guiResponder, int idIn, int x, int y, String name, float min, float max, float defaultValue, FormatHelper formatter) {
        //            super(guiResponder, idIn, x, y, name, min, max, defaultValue, formatter);
        //            this.responder = guiResponder;
        //        }

        //        @Override
        public void mouseReleased(int mouseX, int mouseY) {
            //            super.mouseReleased(mouseX, mouseY);
            //            this.responder.setEntryValue(this.id, this.getSliderValue());
        }

        @Override
        protected void func_230979_b_() {

        }

        @Override
        protected void func_230972_a_() {

        }
    }

    //private static class IntFormatter implements GuiSlider.FormatHelper {
    //
    //    public static final IntFormatter INSTANCE = new IntFormatter();
    //
    //    @Override
    //    public String getText(int id, String name, float value) {
    //        return name + ": " + (int) value;
    //    }
    //}
}
