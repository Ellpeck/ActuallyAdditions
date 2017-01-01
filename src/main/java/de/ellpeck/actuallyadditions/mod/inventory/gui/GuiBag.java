/*
 * This file ("GuiBag.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerBag;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBag extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_bag");
    private static final ResourceLocation RES_LOC_VOID = AssetUtil.getGuiLocation("gui_void_bag");

    private final ContainerBag container;
    private final boolean isVoid;
    private FilterSettingsGui filter;
    private GuiButton buttonAutoInsert;

    public GuiBag(InventoryPlayer inventory, boolean isVoid){
        this(isVoid, new ContainerBag(inventory, isVoid));
    }

    private GuiBag(boolean isVoid, ContainerBag container){
        super(container);
        this.xSize = 176;
        this.ySize = 90+86;
        this.isVoid = isVoid;
        this.container = container;
    }

    @Override
    public void initGui(){
        super.initGui();

        this.filter = new FilterSettingsGui(this.container.filter, this.guiLeft+138, this.guiTop+10, this.buttonList);

        this.buttonAutoInsert = new GuiButton(0, this.guiLeft-21, this.guiTop+8, 20, 20, (this.container.autoInsert ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"I");
        this.buttonList.add(this.buttonAutoInsert);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        NBTTagCompound data = new NBTTagCompound();
        data.setInteger("ButtonID", button.id);
        data.setInteger("PlayerID", Minecraft.getMinecraft().player.getEntityId());
        data.setInteger("WorldID", Minecraft.getMinecraft().world.provider.getDimension());
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(data, PacketHandler.GUI_BUTTON_TO_CONTAINER_HANDLER));
    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        this.filter.update();

        this.buttonAutoInsert.displayString = (this.container.autoInsert ? TextFormatting.DARK_GREEN : TextFormatting.RED)+"I";
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, StringUtil.localize("container."+ModUtil.MOD_ID+"."+(this.isVoid ? "voidBag" : "bag")+".name"));
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+90, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(this.isVoid ? RES_LOC_VOID : RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 90);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.filter.drawHover(mouseX, mouseY);

        if(this.buttonAutoInsert.isMouseOver()){
            List<String> text = new ArrayList<String>();
            text.add(TextFormatting.BOLD+"Auto-Insert "+(this.container.autoInsert ? "On" : "Off"));
            text.addAll(this.mc.fontRendererObj.listFormattedStringToWidth("Turn this on to make items that get picked up automatically go into the bag.", 200));
            text.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(TextFormatting.GRAY+""+TextFormatting.ITALIC+"Note that this WON'T work when you are holding the bag in your hand.", 200));
            this.drawHoveringText(text, mouseX, mouseY);
        }
    }
}