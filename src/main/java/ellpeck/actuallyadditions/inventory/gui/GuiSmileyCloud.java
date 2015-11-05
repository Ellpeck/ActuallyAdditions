/*
 * This file ("GuiSmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.inventory.ContainerSmileyCloud;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.network.gui.PacketGuiString;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntitySmileyCloud;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiSmileyCloud extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiSmileyCloud");

    private int x;
    private int y;
    private int z;
    private World world;

    private GuiTextField nameField;

    private TileEntitySmileyCloud cloud;

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

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        super.initGui();

        this.nameField = new GuiTextField(this.fontRendererObj, guiLeft+5, guiTop+6, 114, 8);
        this.nameField.setMaxStringLength(20);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setFocused(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        String name = cloud.name == null || cloud.name.isEmpty() ? "" : EnumChatFormatting.GOLD+cloud.name+EnumChatFormatting.RESET+" "+StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".gui.the")+" ";
        String localizedName = name+StringUtil.localize("container."+ModUtil.MOD_ID_LOWER+".cloud.name");
        this.fontRendererObj.drawString(localizedName, xSize/2-this.fontRendererObj.getStringWidth(localizedName)/2, -10, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.nameField.drawTextBox();
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3){
        this.nameField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void keyTyped(char theChar, int key){
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
        PacketHandler.theNetwork.sendToServer(new PacketGuiString(x, y, z, world, text, textID, Minecraft.getMinecraft().thePlayer));
    }
}