// TODO: [port][note] no longer needed
///*
// * This file ("GuiSmileyCloud.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.inventory.gui;
//
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.inventory.ContainerSmileyCloud;
//import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
//import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
//import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
//import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
//import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.widget.TextFieldWidget;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import org.lwjgl.input.Keyboard;
//
//import java.io.IOException;
//
//@OnlyIn(Dist.CLIENT)
//public class GuiSmileyCloud extends GuiWtfMojang {
//
//    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_smiley_cloud");
//
//    private final int x;
//    private final int y;
//    private final int z;
//    private final World world;
//    private final TileEntitySmileyCloud cloud;
//    private TextFieldWidget nameField;
//
//    public GuiSmileyCloud(TileEntityBase tile, int x, int y, int z, World world) {
//        super(new ContainerSmileyCloud());
//        this.cloud = (TileEntitySmileyCloud) tile;
//        this.x = x;
//        this.y = y;
//        this.z = z;
//        this.world = world;
//        this.xSize = 124;
//        this.ySize = 20;
//    }
//
//    @Override
//    public void init() {
//        super.init();
//
//        this.nameField = new TextFieldWidget(4000, this.font, this.guiLeft + 5, this.guiTop + 6, 114, 8);
//        this.nameField.setMaxStringLength(20);
//        this.nameField.setEnableBackgroundDrawing(false);
//        this.nameField.setFocused(true);
//    }
//
//    @Override
//    public void drawGuiContainerForegroundLayer(int x, int y) {
//        String name = this.cloud.name == null || this.cloud.name.isEmpty()
//            ? ""
//            : TextFormatting.GOLD + this.cloud.name + TextFormatting.RESET + " " + StringUtil.localize("info.actuallyadditions.gui.the") + " ";
//        String localizedName = name + StringUtil.localize("container.actuallyadditions.cloud.name");
//        this.font.drawString(localizedName, this.xSize / 2 - this.font.getStringWidth(localizedName) / 2, -10, StringUtil.DECIMAL_COLOR_WHITE);
//    }
//
//    @Override
//    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//
//        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
//        guiGraphics.blit(matrices, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
//
//        this.nameField.drawTextBox();
//    }
//
//    @Override
//    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
//        this.nameField.mouseClicked(par1, par2, par3);
//        super.mouseClicked(par1, par2, par3);
//    }
//
//    @Override
//    public void keyTyped(char theChar, int key) throws IOException {
//        if (key != 1 && this.nameField.isFocused()) {
//            if (key == Keyboard.KEY_RETURN || key == Keyboard.KEY_NUMPADENTER) {
//                this.setVariable(this.nameField);
//            } else {
//                this.nameField.textboxKeyTyped(theChar, key);
//            }
//        } else {
//            super.keyTyped(theChar, key);
//        }
//    }
//
//    @Override
//    public void updateScreen() {
//        super.updateScreen();
//        this.nameField.updateCursorCounter();
//    }
//
//    public void setVariable(TextFieldWidget field) {
//        this.sendPacket(field.getText(), 0);
//        field.setText("");
//    }
//
//    private void sendPacket(String text, int textID) {
//        CompoundNBT compound = new CompoundNBT();
//        compound.putInt("X", this.x);
//        compound.putInt("Y", this.y);
//        compound.putInt("Z", this.z);
//        compound.putInt("WorldID", this.world.provider.getDimension());
//        compound.putInt("PlayerID", Minecraft.getInstance().player.getEntityId());
//        compound.putInt("TextID", textID);
//        compound.setString("Text", text);
//        PacketDistributor.SERVER.noArg().send(new PacketClientToServer(compound, PacketHandler.GUI_STRING_TO_TILE_HANDLER));
//    }
//}
