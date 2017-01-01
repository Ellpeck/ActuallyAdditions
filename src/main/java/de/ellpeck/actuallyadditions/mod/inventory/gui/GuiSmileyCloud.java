/*
 * This file ("GuiSmileyCloud.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerSmileyCloud;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiSmileyCloud extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_smiley_cloud");

    private final int x;
    private final int y;
    private final int z;
    private final World world;
    private final TileEntitySmileyCloud cloud;
    private GuiTextField nameField;

    public GuiSmileyCloud(TileEntityBase tile, int x, int y, int z, World world){
        super(new ContainerSmileyCloud());
        this.cloud = (TileEntitySmileyCloud)tile;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.xSize = 124;
        this.ySize = 20;
    }

    @Override
    public void initGui(){
        super.initGui();

        this.nameField = new GuiTextField(4000, this.fontRendererObj, this.guiLeft+5, this.guiTop+6, 114, 8);
        this.nameField.setMaxStringLength(20);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setFocused(true);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        String name = this.cloud.name == null || this.cloud.name.isEmpty() ? "" : TextFormatting.GOLD+this.cloud.name+TextFormatting.RESET+" "+StringUtil.localize("info."+ModUtil.MOD_ID+".gui.the")+" ";
        String localizedName = name+StringUtil.localize("container."+ModUtil.MOD_ID+".cloud.name");
        this.fontRendererObj.drawString(localizedName, this.xSize/2-this.fontRendererObj.getStringWidth(localizedName)/2, -10, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.nameField.drawTextBox();
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException{
        this.nameField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void keyTyped(char theChar, int key) throws IOException{
        if(key != 1 && this.nameField.isFocused()){
            if(key == Keyboard.KEY_RETURN || key == Keyboard.KEY_NUMPADENTER){
                this.setVariable(this.nameField);
            }
            else{
                this.nameField.textboxKeyTyped(theChar, key);
            }
        }
        else{
            super.keyTyped(theChar, key);
        }
    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        this.nameField.updateCursorCounter();
    }

    public void setVariable(GuiTextField field){
        this.sendPacket(field.getText(), 0);
        field.setText("");
    }

    private void sendPacket(String text, int textID){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("X", this.x);
        compound.setInteger("Y", this.y);
        compound.setInteger("Z", this.z);
        compound.setInteger("WorldID", this.world.provider.getDimension());
        compound.setInteger("PlayerID", Minecraft.getMinecraft().player.getEntityId());
        compound.setInteger("TextID", textID);
        compound.setString("Text", text);
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_STRING_TO_TILE_HANDLER));
    }
}