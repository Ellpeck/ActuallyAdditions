/*
 * This file ("GuiInputter.java") is part of the Actually Additions mod for Minecraft.
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
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerInputter;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInputter;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiInputter extends GuiWtfMojang<ContainerInputter> {

    public static final int OFFSET_ADVANCED = 12 + 36;
    public static final String[] SIDES = new String[]{StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.disabled"), StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.up"), StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.down"), StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.north"), StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.east"), StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.south"), StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.west")};
    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_inputter");
    private static final ResourceLocation RES_LOC_ADVANCED = AssetUtil.getGuiLocation("gui_inputter_advanced");
    public final TileEntityInputter tileInputter;
    private final boolean isAdvanced;
    private TextFieldWidget fieldPutStart;
    private TextFieldWidget fieldPutEnd;
    private TextFieldWidget fieldPullStart;
    private TextFieldWidget fieldPullEnd;

    private FilterSettingsGui leftFilter;
    private FilterSettingsGui rightFilter;

    public GuiInputter(ContainerInputter container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.tileInputter = container.tileInputter;
        this.xSize = 176;
        this.ySize = 97 + 86 + (container.isAdvanced
            ? OFFSET_ADVANCED
            : 0);
        this.isAdvanced = container.isAdvanced;
    }

    @Override
    public void init() {
        super.init();

        if (this.isAdvanced) {
            this.leftFilter = new FilterSettingsGui(this.tileInputter.leftFilter, this.guiLeft + 3, this.guiTop + 6, this.buttonList);
            this.rightFilter = new FilterSettingsGui(this.tileInputter.rightFilter, this.guiLeft + 157, this.guiTop + 6, this.buttonList);
        }

        this.fieldPullStart = new TextFieldWidget(this.font, this.guiLeft + 6, this.guiTop + 80 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), 34, 8);
        this.fieldPullStart.setMaxStringLength(5);
        this.fieldPullStart.setEnableBackgroundDrawing(false);
        this.children.add(this.fieldPullStart);

        this.fieldPullEnd = new TextFieldWidget(this.font, this.guiLeft + 50, this.guiTop + 80 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), 34, 8);
        this.fieldPullEnd.setMaxStringLength(5);
        this.fieldPullEnd.setEnableBackgroundDrawing(false);
        this.children.add(this.fieldPullEnd);

        this.fieldPutStart = new TextFieldWidget(this.font, this.guiLeft + 91, this.guiTop + 80 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), 34, 8);
        this.fieldPutStart.setMaxStringLength(5);
        this.fieldPutStart.setEnableBackgroundDrawing(false);
        this.children.add(this.fieldPutStart);

        this.fieldPutEnd = new TextFieldWidget(this.font, this.guiLeft + 135, this.guiTop + 80 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), 34, 8);
        this.fieldPutEnd.setMaxStringLength(5);
        this.fieldPutEnd.setEnableBackgroundDrawing(false);
        this.children.add(this.fieldPutEnd);

        SmallerButton buttonSidePutP = new SmallerButton(0, this.guiLeft + 155, this.guiTop + 43 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), ">");
        SmallerButton buttonSidePutM = new SmallerButton(1, this.guiLeft + 90, this.guiTop + 43 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), "<");

        SmallerButton buttonSidePullP = new SmallerButton(2, this.guiLeft + 70, this.guiTop + 43 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), ">");
        SmallerButton buttonSidePullM = new SmallerButton(3, this.guiLeft + 5, this.guiTop + 43 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), "<");

        this.addButton(buttonSidePutP);
        this.addButton(buttonSidePullP);
        this.addButton(buttonSidePutM);
        this.addButton(buttonSidePullM);

        this.addButton(new TinyButton(TileEntityInputter.OKAY_BUTTON_ID, this.guiLeft + 84, this.guiTop + 91 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0)));
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);

        int newTopOffset = this.guiTop + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0);
        //Info Mode on!
        if (x >= this.guiLeft + 4 && y >= newTopOffset + 65 && x <= this.guiLeft + 4 + 38 && y <= newTopOffset + 65 + 12) {
            this.drawHoveringText(this.font.listFormattedStringToWidth(StringUtil.localizeFormatted("info." + ActuallyAdditions.MODID + ".inputter.info.1").replace("<p>", StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.pull")), 200), x, y);
        }
        if (x >= this.guiLeft + 89 && y >= newTopOffset + 65 && x <= this.guiLeft + 89 + 38 && y <= newTopOffset + 65 + 12) {
            this.drawHoveringText(this.font.listFormattedStringToWidth(StringUtil.localizeFormatted("info." + ActuallyAdditions.MODID + ".inputter.info.1").replace("<p>", StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.put")), 200), x, y);
        }
        if (x >= this.guiLeft + 48 && y >= newTopOffset + 65 && x <= this.guiLeft + 48 + 38 && y <= newTopOffset + 65 + 12) {
            this.drawHoveringText(this.font.listFormattedStringToWidth(StringUtil.localizeFormatted("info." + ActuallyAdditions.MODID + ".inputter.info.2").replace("<p>", StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.pull")), 200), x, y);
        }
        if (x >= this.guiLeft + 133 && y >= newTopOffset + 65 && x <= this.guiLeft + 133 + 38 && y <= newTopOffset + 65 + 12) {
            this.drawHoveringText(this.font.listFormattedStringToWidth(StringUtil.localizeFormatted("info." + ActuallyAdditions.MODID + ".inputter.info.2").replace("<p>", StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.put")), 200), x, y);
        }

        if (this.isAdvanced) {
            this.leftFilter.drawHover(x, y);
            this.rightFilter.drawHover(x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.xSize, -10, this.tileInputter);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.guiLeft, this.guiTop + 97 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bindTexture(this.isAdvanced
            ? RES_LOC_ADVANCED
            : RES_LOC);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 97 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0));

        this.font.drawString(matrices, StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.inbound"), this.guiLeft + 23 + 3, this.guiTop + 32 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        this.font.drawString(matrices, StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.outbound"), this.guiLeft + 104 + 3, this.guiTop + 32 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);

        this.font.drawString(matrices, SIDES[this.tileInputter.sideToPull + 1], this.guiLeft + 24 + 1, this.guiTop + 45 + 3 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        this.font.drawString(matrices, SIDES[this.tileInputter.sideToPut + 1], this.guiLeft + 109 + 1, this.guiTop + 45 + 3 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);

        this.font.drawString(matrices, Integer.toString(this.tileInputter.slotToPutStart), this.guiLeft + 92, this.guiTop + 67 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), StringUtil.DECIMAL_COLOR_WHITE);
        this.font.drawString(matrices, Integer.toString(this.tileInputter.slotToPutEnd), this.guiLeft + 136, this.guiTop + 67 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), StringUtil.DECIMAL_COLOR_WHITE);
        this.font.drawString(matrices, Integer.toString(this.tileInputter.slotToPullStart), this.guiLeft + 7, this.guiTop + 67 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), StringUtil.DECIMAL_COLOR_WHITE);
        this.font.drawString(matrices, Integer.toString(this.tileInputter.slotToPullEnd), this.guiLeft + 51, this.guiTop + 67 + (this.isAdvanced
            ? OFFSET_ADVANCED
            : 0), StringUtil.DECIMAL_COLOR_WHITE);
    }

    //    @Override
    //    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
    //        this.fieldPutStart.mouseClicked(par1, par2, par3);
    //        this.fieldPutEnd.mouseClicked(par1, par2, par3);
    //        this.fieldPullStart.mouseClicked(par1, par2, par3);
    //        this.fieldPullEnd.mouseClicked(par1, par2, par3);
    //
    //        super.mouseClicked(par1, par2, par3);
    //    }

    //    @Override
    //    public void keyTyped(char theChar, int key) throws IOException {
    //        if (key == Keyboard.KEY_RETURN || key == Keyboard.KEY_NUMPADENTER) {
    //            if (this.fieldPutStart.isFocused()) {
    //                this.setVariable(this.fieldPutStart, 0);
    //            }
    //            if (this.fieldPutEnd.isFocused()) {
    //                this.setVariable(this.fieldPutEnd, 1);
    //            }
    //            if (this.fieldPullStart.isFocused()) {
    //                this.setVariable(this.fieldPullStart, 2);
    //            }
    //            if (this.fieldPullEnd.isFocused()) {
    //                this.setVariable(this.fieldPullEnd, 3);
    //            }
    //        } else if (Character.isDigit(theChar) || key == Keyboard.KEY_BACK || key == Keyboard.KEY_DELETE || key == Keyboard.KEY_LEFT || key == Keyboard.KEY_RIGHT) {
    //            this.fieldPutStart.textboxKeyTyped(theChar, key);
    //            this.fieldPutEnd.textboxKeyTyped(theChar, key);
    //            this.fieldPullStart.textboxKeyTyped(theChar, key);
    //            this.fieldPullEnd.textboxKeyTyped(theChar, key);
    //        } else {
    //            super.keyTyped(theChar, key);
    //        }
    //    }

    @Override
    public void tick() {
        super.tick();
        //
        //        this.fieldPutStart.updateCursorCounter();
        //        this.fieldPutEnd.updateCursorCounter();
        //        this.fieldPullStart.updateCursorCounter();
        //        this.fieldPullEnd.updateCursorCounter();

        if (this.isAdvanced) {
            this.leftFilter.tick();
            this.rightFilter.tick();
        }
    }

    public void setVariable(TextFieldWidget field, int sendInt) {
        if (!field.getText().isEmpty()) {
            this.sendPacket(this.parse(field.getText()), sendInt);
            field.setText("");
        }
    }

    private void sendPacket(int text, int textID) {
        PacketHandlerHelper.sendNumberPacket(this.tileInputter, text, textID);
    }

    private int parse(String theInt) {
        try {
            return Integer.parseInt(theInt);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void actionPerformed(Button button) {
        if (button.id == TileEntityInputter.OKAY_BUTTON_ID) {
            this.setVariable(this.fieldPutStart, 0);
            this.setVariable(this.fieldPutEnd, 1);
            this.setVariable(this.fieldPullStart, 2);
            this.setVariable(this.fieldPullEnd, 3);
        } else {
            PacketHandlerHelper.sendButtonPacket(this.tileInputter, button.id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class SmallerButton extends Button {

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");
        private final boolean smaller;

        public SmallerButton(int x, int y, ITextComponent display, IPressable pressable) {
            this(x, y, display, false, pressable);
        }

        public SmallerButton(int x, int y, ITextComponent display, boolean smaller, IPressable pressable) {
            super(x, y, 16, smaller
                ? 12
                : 16, display, pressable);
            this.smaller = smaller;
        }

        @Override
        public void render(MatrixStack matrices, int x, int y, float f) {
            if (this.visible) {
                Minecraft.getInstance().getTextureManager().bindTexture(this.resLoc);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.isHovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
                int k = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);
                this.blit(matrices, this.x, this.y, this.smaller
                    ? 200
                    : 176, k * this.height, this.width, this.height);
                this.mouseDragged(mc, x, y);

                int color = 14737632;
                if (this.packedFGColour != 0) {
                    color = this.packedFGColour;
                } else if (!this.enabled) {
                    color = 10526880;
                } else if (this.hovered) {
                    color = 16777120;
                }

                this.drawCenteredString(mc.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class TinyButton extends Button {

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");

        public TinyButton(int id, int x, int y) {
            super(id, x, y, 8, 8, "");
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y, float f) {
            if (this.visible) {
                mc.getTextureManager().bindTexture(this.resLoc);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
                int k = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);
                this.blit(matrices, this.x, this.y, 192, k * 8, 8, 8);
                this.mouseDragged(mc, x, y);
            }
        }
    }
}
