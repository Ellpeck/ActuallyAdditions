/*
 * This file ("GuiInputter.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerInputter;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.PacketGuiButton;
import de.ellpeck.actuallyadditions.mod.network.gui.PacketGuiNumber;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiInputter extends GuiContainer{

    public static final int OFFSET_ADVANCED = 12+36;
    public static final String[] sideString = new String[]{
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.disabled"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.up"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.down"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.north"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.east"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.south"),
            StringUtil.localize("info."+ModUtil.MOD_ID+".gui.west")};
    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiInputter");
    private static final ResourceLocation resLocAdvanced = AssetUtil.getGuiLocation("guiInputterAdvanced");
    public TileEntityInputter tileInputter;
    private int x;
    private int y;
    private int z;
    private World world;
    private SmallerButton whitelistPut;
    private SmallerButton whitelistPull;
    private GuiTextField fieldPutStart;
    private GuiTextField fieldPutEnd;
    private GuiTextField fieldPullStart;
    private GuiTextField fieldPullEnd;
    private boolean isAdvanced;

    public GuiInputter(InventoryPlayer inventory, TileEntityBase tile, int x, int y, int z, World world, boolean isAdvanced){
        super(new ContainerInputter(inventory, tile, isAdvanced));
        this.tileInputter = (TileEntityInputter)tile;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.xSize = 176;
        this.ySize = 93+86+(isAdvanced ? OFFSET_ADVANCED : 0);
        this.isAdvanced = isAdvanced;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        super.initGui();

        this.fieldPullStart = new GuiTextField(3000, this.fontRendererObj, this.guiLeft+13, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0), 27, 8);
        this.fieldPullStart.setMaxStringLength(4);
        this.fieldPullStart.setEnableBackgroundDrawing(false);
        this.fieldPullEnd = new GuiTextField(3001, this.fontRendererObj, this.guiLeft+50, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0), 27, 8);
        this.fieldPullEnd.setMaxStringLength(4);
        this.fieldPullEnd.setEnableBackgroundDrawing(false);

        this.fieldPutStart = new GuiTextField(3002, this.fontRendererObj, this.guiLeft+98, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0), 27, 8);
        this.fieldPutStart.setMaxStringLength(4);
        this.fieldPutStart.setEnableBackgroundDrawing(false);
        this.fieldPutEnd = new GuiTextField(3004, this.fontRendererObj, this.guiLeft+135, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0), 27, 8);
        this.fieldPutEnd.setMaxStringLength(4);
        this.fieldPutEnd.setEnableBackgroundDrawing(false);

        SmallerButton buttonSidePutP = new SmallerButton(0, this.guiLeft+155, this.guiTop+43+(this.isAdvanced ? OFFSET_ADVANCED : 0), ">");
        SmallerButton buttonSidePutM = new SmallerButton(1, this.guiLeft+90, this.guiTop+43+(this.isAdvanced ? OFFSET_ADVANCED : 0), "<");

        SmallerButton buttonSidePullP = new SmallerButton(2, this.guiLeft+70, this.guiTop+43+(this.isAdvanced ? OFFSET_ADVANCED : 0), ">");
        SmallerButton buttonSidePullM = new SmallerButton(3, this.guiLeft+5, this.guiTop+43+(this.isAdvanced ? OFFSET_ADVANCED : 0), "<");

        this.whitelistPull = new SmallerButton(TileEntityInputter.WHITELIST_PULL_BUTTON_ID, this.guiLeft+3, this.guiTop+16, "");
        this.whitelistPut = new SmallerButton(TileEntityInputter.WHITELIST_PUT_BUTTON_ID, this.guiLeft+157, this.guiTop+16, "");

        this.buttonList.add(buttonSidePutP);
        this.buttonList.add(buttonSidePullP);
        this.buttonList.add(buttonSidePutM);
        this.buttonList.add(buttonSidePullM);
        if(this.isAdvanced){
            this.buttonList.add(this.whitelistPut);
            this.buttonList.add(this.whitelistPull);
        }

        this.buttonList.add(new TinyButton(TileEntityInputter.OKAY_BUTTON_ID, this.guiLeft+84, this.guiTop+80+(this.isAdvanced ? OFFSET_ADVANCED : 0)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        this.whitelistPull.displayString = this.tileInputter.isPullWhitelist ? "O" : "X";
        this.whitelistPut.displayString = this.tileInputter.isPutWhitelist ? "O" : "X";

        if(this.isAdvanced){
            List infoList = this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.whitelistInfo"), 200);
            String text1 = this.tileInputter.isPullWhitelist ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.whitelist") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.blacklist");
            if(x >= this.guiLeft+3 && y >= this.guiTop+16 && x <= this.guiLeft+18 && y <= this.guiTop+31){
                ArrayList list = new ArrayList();
                list.add(TextFormatting.BOLD+text1);
                list.addAll(infoList);
                this.drawHoveringText(list, x, y);
            }
            String text2 = this.tileInputter.isPutWhitelist ? StringUtil.localize("info."+ModUtil.MOD_ID+".gui.whitelist") : StringUtil.localize("info."+ModUtil.MOD_ID+".gui.blacklist");
            if(x >= this.guiLeft+157 && y >= this.guiTop+16 && x <= this.guiLeft+172 && y <= this.guiTop+31){
                ArrayList list = new ArrayList();
                list.add(TextFormatting.BOLD+text2);
                list.addAll(infoList);
                this.drawHoveringText(list, x, y);
            }
        }

        int newTopOffset = this.guiTop+(this.isAdvanced ? OFFSET_ADVANCED : 0);
        //Info Mode on!
        if(x >= this.guiLeft+11 && y >= newTopOffset+65 && x <= this.guiLeft+11+31 && y <= newTopOffset+65+12){
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.info.1").replace("<p>", StringUtil.localize("info."+ModUtil.MOD_ID+".gui.pull")), 200), x, y);
        }
        if(x >= this.guiLeft+96 && y >= newTopOffset+65 && x <= this.guiLeft+96+31 && y <= newTopOffset+65+12){
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.info.1").replace("<p>", StringUtil.localize("info."+ModUtil.MOD_ID+".gui.put")), 200), x, y);
        }
        if(x >= this.guiLeft+48 && y >= newTopOffset+65 && x <= this.guiLeft+48+31 && y <= newTopOffset+65+12){
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.info.2").replace("<p>", StringUtil.localize("info."+ModUtil.MOD_ID+".gui.pull")), 200), x, y);
        }
        if(x >= this.guiLeft+133 && y >= newTopOffset+65 && x <= this.guiLeft+133+31 && y <= newTopOffset+65+12){
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(StringUtil.localizeFormatted("info."+ModUtil.MOD_ID+".inputter.info.2").replace("<p>", StringUtil.localize("info."+ModUtil.MOD_ID+".gui.put")), 200), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.tileInputter.getName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93+(this.isAdvanced ? OFFSET_ADVANCED : 0), 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(this.isAdvanced ? resLocAdvanced : resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93+(this.isAdvanced ? OFFSET_ADVANCED : 0));

        this.fontRendererObj.drawString(StringUtil.localize("info."+ModUtil.MOD_ID+".gui.pull"), this.guiLeft+22+3, this.guiTop+32+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        this.fontRendererObj.drawString(StringUtil.localize("info."+ModUtil.MOD_ID+".gui.put"), this.guiLeft+107+3, this.guiTop+32+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);

        this.fontRendererObj.drawString(sideString[this.tileInputter.sideToPull+1], this.guiLeft+24+1, this.guiTop+45+3+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);
        this.fontRendererObj.drawString(sideString[this.tileInputter.sideToPut+1], this.guiLeft+109+1, this.guiTop+45+3+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_GRAY_TEXT);

        this.fontRendererObj.drawString(Integer.toString(this.tileInputter.slotToPutStart), this.guiLeft+99, this.guiTop+67+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_WHITE);
        this.fontRendererObj.drawString(Integer.toString(this.tileInputter.slotToPutEnd), this.guiLeft+136, this.guiTop+67+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_WHITE);
        this.fontRendererObj.drawString(Integer.toString(this.tileInputter.slotToPullStart), this.guiLeft+14, this.guiTop+67+(this.isAdvanced ? OFFSET_ADVANCED : 0), StringUtil.DECIMAL_COLOR_WHITE);
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
    }

    public void setVariable(GuiTextField field, int sendInt){
        if(!field.getText().isEmpty()){
            this.sendPacket(this.parse(field.getText()), sendInt);
            field.setText("");
        }
    }

    private void sendPacket(int text, int textID){
        PacketHandler.theNetwork.sendToServer(new PacketGuiNumber(this.x, this.y, this.z, this.world, text, textID, Minecraft.getMinecraft().thePlayer));
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
            PacketHandler.theNetwork.sendToServer(new PacketGuiButton(this.x, this.y, this.z, this.world, button.id, Minecraft.getMinecraft().thePlayer));
        }
    }

    public static class SmallerButton extends GuiButton{

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiInputter");

        public SmallerButton(int id, int x, int y, String display){
            super(id, x, y, 16, 16, display);
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
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, k*16, 16, 16);
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

    public static class TinyButton extends GuiButton{

        public final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiInputter");

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