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

import de.ellpeck.actuallyadditions.mod.inventory.ContainerInputter;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInputter;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiInputter extends GuiContainer{

    public static final int OFFSET_ADVANCED = 12+36;
    public static final String[] SIDES = new String[]{
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.disabled"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.up"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.down"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.north"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.east"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.south"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.west")};
    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_inputter");
    private static final ResourceLocation RES_LOC_ADVANCED = AssetUtil.getGuiLocation("gui_inputter_advanced");
    public final TileEntityInputter tileInputter;
    private final boolean isAdvanced;
    private GuiTextField fieldPutStart;
    private GuiTextField fieldPutEnd;
    private GuiTextField fieldPullStart;
    private GuiTextField fieldPullEnd;

    private FilterSettingsGui leftFilter;
    private FilterSettingsGui rightFilter;

    public GuiInputter(InventoryPlayer inventory, TileEntityBase tile, boolean isAdvanced){
        super(new ContainerInputter(inventory, tile, isAdvanced));
        this.tileInputter = (TileEntityInputter)tile;
        this.xSize = 176;
        this.ySize = 97+86+(isAdvanced ? OFFSET_ADVANCED : 0);
        this.isAdvanced = isAdvanced;
    }

    @Override
    public void initGui(){
        super.initGui();

        if(this.isAdvanced){
            this.leftFilter = new FilterSettingsGui(this.tileInputter.leftFilter, this.guiLeft+3, this.guiTop+6, this.buttonList);
            this.rightFilter = new FilterSettingsGui(this.tileInputter.rightFilter, this.guiLeft+157, this.guiTop+6, this.buttonList);
        }

        this.fieldPullStart = new GuiTextField(3000, this.fontRendererObj, this.guiLeft+6, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0), 34, 8);
        this.fieldPullStart.setMaxStringLength(5);
        this.fieldPullStart.setEnableBackgroundDrawing(false);
        this.fieldPullEnd = new GuiTextField(3001, this.fontRendererObj, this.guiLeft+50, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0), 34, 8);
        this.fieldPullEnd.setMaxStringLength(5);
        this.fieldPullEnd.setEnableBackgroundDrawing(false);

        this.fieldPutStart = new GuiTextField(3002, this.fontRendererObj, this.guiLeft+91, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0), 34, 8);
        this.fieldPutStart.setMaxStringLength(5);
        this.fieldPutStart.setEnableBackgroundDrawing(false);
        this.fieldPutEnd = new GuiTextField(3004, this.fontRendererObj, this.guiLeft+135, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0), 34, 8);
        this.fieldPutEnd.setMaxStringLength(5);
        this.fieldPutEnd.setEnableBackgroundDrawing(false);

        SmallerButton buttonSidePutP = new SmallerButton(0, this.guiLeft+155, this.guiTop+43+(this.isAdvanced ? OFFSET_ADVANCED : 0), ">");
        SmallerButton buttonSidePutM = new SmallerButton(1, this.guiLeft+90, this.guiTop+43+(this.isAdvanced ? OFFSET_ADVANCED : 0), "<");

        SmallerButton buttonSidePullP = new SmallerButton(2, this.guiLeft+70, this.guiTop+43+(this.isAdvanced ? OFFSET_ADVANCED : 0), ">");
        SmallerButton buttonSidePullM = new SmallerButton(3, this.guiLeft+5, this.guiTop+43+(this.isAdvanced ? OFFSET_ADVANCED : 0), "<");

        this.buttonList.add(buttonSidePutP);
        this.buttonList.add(buttonSidePullP);
        this.buttonList.add(buttonSidePutM);
        this.buttonList.add(buttonSidePullM);

        this.buttonList.add(new TinyButton(TileEntityInputter.OKAY_BUTTON_ID, this.guiLeft+84, this.guiTop+91+(this.isAdvanced ? OFFSET_ADVANCED : 0)));
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        int newTopOffset = this.guiTop+(this.isAdvanced ? OFFSET_ADVANCED : 0);
        //Info Mode on!
        if(x >= this.guiLeft+4 && y >= newTopOffset+65 && x <= this.guiLeft+4+38 && y <= newTopOffset+65+12){
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.info.1").replace("<p>", StringUtil.localize("info."+ModUtil.MOD_ID+".gui.pull")), 200), x, y);
        }
        if(x >= this.guiLeft+89 && y >= newTopOffset+65 && x <= this.guiLeft+89+38 && y <= newTopOffset+65+12){
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.info.1").replace("<p>", StringUtil.localize("info."+ModUtil.MOD_ID+".gui.put")), 200), x, y);
        }
        if(x >= this.guiLeft+48 && y >= newTopOffset+65 && x <= this.guiLeft+48+38 && y <= newTopOffset+65+12){
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.info.2").replace("<p>", StringUtil.localize("info."+ModUtil.MOD_ID+".gui.pull")), 200), x, y);
        }
        if(x >= this.guiLeft+133 && y >= newTopOffset+65 && x <= this.guiLeft+133+38 && y <= newTopOffset+65+12){
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.info.2").replace("<p>", StringUtil.localize("info."+ModUtil.MOD_ID+".gui.put")), 200), x, y);
        }

        if(this.isAdvanced){
            this.leftFilter.drawHover(x, y);
            this.rightFilter.drawHover(x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.tileInputter);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+97+(this.isAdvanced ? OFFSET_ADVANCED : 0), 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(this.isAdvanced ? RES_LOC_ADVANCED : RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 97+(this.isAdvanced ? OFFSET_ADVANCED : 0));

        this.fontRendererObj.drawString(StringUtil.localize("info."+ModUtil.MOD_ID+".gui.inbound"), this.guiLeft+23+3, this.guiTop+32+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        this.fontRendererObj.drawString(StringUtil.localize("info."+ModUtil.MOD_ID+".gui.outbound"), this.guiLeft+104+3, this.guiTop+32+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);

        this.fontRendererObj.drawString(SIDES[this.tileInputter.sideToPull+1], this.guiLeft+24+1, this.guiTop+45+3+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        this.fontRendererObj.drawString(SIDES[this.tileInputter.sideToPut+1], this.guiLeft+109+1, this.guiTop+45+3+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);

        this.fontRendererObj.drawString(Integer.toString(this.tileInputter.slotToPutStart), this.guiLeft+92, this.guiTop+67+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_WHITE);
        this.fontRendererObj.drawString(Integer.toString(this.tileInputter.slotToPutEnd), this.guiLeft+136, this.guiTop+67+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_WHITE);
        this.fontRendererObj.drawString(Integer.toString(this.tileInputter.slotToPullStart), this.guiLeft+7, this.guiTop+67+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_WHITE);
        this.fontRendererObj.drawString(Integer.toString(this.tileInputter.slotToPullEnd), this.guiLeft+51, this.guiTop+67+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_WHITE);

        this.fieldPutStart.drawTextBox();
        this.fieldPutEnd.drawTextBox();
        this.fieldPullStart.drawTextBox();
        this.fieldPullEnd.drawTextBox();
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException{
        this.fieldPutStart.mouseClicked(par1, par2, par3);
        this.fieldPutEnd.mouseClicked(par1, par2, par3);
        this.fieldPullStart.mouseClicked(par1, par2, par3);
        this.fieldPullEnd.mouseClicked(par1, par2, par3);

        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void keyTyped(char theChar, int key) throws IOException{
        if(key == Keyboard.KEY_RETURN || key == Keyboard.KEY_NUMPADENTER){
            if(this.fieldPutStart.isFocused()){
                this.setVariable(this.fieldPutStart, 0);
            }
            if(this.fieldPutEnd.isFocused()){
                this.setVariable(this.fieldPutEnd, 1);
            }
            if(this.fieldPullStart.isFocused()){
                this.setVariable(this.fieldPullStart, 2);
            }
            if(this.fieldPullEnd.isFocused()){
                this.setVariable(this.fieldPullEnd, 3);
            }
        }
        else if(Character.isDigit(theChar) || key == Keyboard.KEY_BACK || key == Keyboard.KEY_DELETE || key == Keyboard.KEY_LEFT || key == Keyboard.KEY_RIGHT){
            this.fieldPutStart.textboxKeyTyped(theChar, key);
            this.fieldPutEnd.textboxKeyTyped(theChar, key);
            this.fieldPullStart.textboxKeyTyped(theChar, key);
            this.fieldPullEnd.textboxKeyTyped(theChar, key);
        }
        else{
            super.keyTyped(theChar, key);
        }
    }

    @Override
    public void updateScreen(){
        super.updateScreen();

        this.fieldPutStart.updateCursorCounter();
        this.fieldPutEnd.updateCursorCounter();
        this.fieldPullStart.updateCursorCounter();
        this.fieldPullEnd.updateCursorCounter();

        if(this.isAdvanced){
            this.leftFilter.update();
            this.rightFilter.update();
        }
    }

    public void setVariable(GuiTextField field, int sendInt){
        if(!field.getText().isEmpty()){
            this.sendPacket(this.parse(field.getText()), sendInt);
            field.setText("");
        }
    }

    private void sendPacket(int text, int textID){
        PacketHandlerHelper.sendNumberPacket(this.tileInputter, text, textID);
    }

    private int parse(String theInt){
        try{
            return Integer.parseInt(theInt);
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public void actionPerformed(GuiButton button){
        if(button.id == TileEntityInputter.OKAY_BUTTON_ID){
            this.setVariable(this.fieldPutStart, 0);
            this.setVariable(this.fieldPutEnd, 1);
            this.setVariable(this.fieldPullStart, 2);
            this.setVariable(this.fieldPullEnd, 3);
        }
        else{
            PacketHandlerHelper.sendButtonPacket(this.tileInputter, button.id);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class SmallerButton extends GuiButton{

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");
        private final boolean smaller;

        public SmallerButton(int id, int x, int y, String display){
            this(id, x, y, display, false);
        }

        public SmallerButton(int id, int x, int y, String display, boolean smaller){
            super(id, x, y, 16, smaller ? 12 : 16, display);
            this.smaller = smaller;
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y){
            if(this.visible){
                mc.getTextureManager().bindTexture(this.resLoc);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
                int k = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, this.smaller ? 200 : 176, k*this.height, this.width, this.height);
                this.mouseDragged(mc, x, y);

                int color = 14737632;
                if(this.packedFGColour != 0){
                    color = this.packedFGColour;
                }
                else if(!this.enabled){
                    color = 10526880;
                }
                else if(this.hovered){
                    color = 16777120;
                }

                this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition+this.width/2, this.yPosition+(this.height-8)/2, color);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static class TinyButton extends GuiButton{

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("gui_inputter");

        public TinyButton(int id, int x, int y){
            super(id, x, y, 8, 8, "");
        }

        @Override
        public void drawButton(Minecraft mc, int x, int y){
            if(this.visible){
                mc.getTextureManager().bindTexture(this.resLoc);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = x >= this.xPosition && y >= this.yPosition && x < this.xPosition+this.width && y < this.yPosition+this.height;
                int k = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 192, k*8, 8, 8);
                this.mouseDragged(mc, x, y);
            }
        }
    }
}