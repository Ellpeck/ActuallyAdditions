/*
 * This file ("GuiCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiCoffeeMachine extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("guiCoffeeMachine");
    private final TileEntityCoffeeMachine machine;
    private final int x;
    private final int y;
    private final int z;
    private final World world;

    public GuiCoffeeMachine(InventoryPlayer inventory, TileEntityBase tile, int x, int y, int z, World world){
        super(new ContainerCoffeeMachine(inventory, tile));
        this.machine = (TileEntityCoffeeMachine)tile;
        this.xSize = 176;
        this.ySize = 93+86;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    @Override
    public void initGui(){
        super.initGui();

        GuiButton buttonOkay = new GuiButton(0, this.guiLeft+60, this.guiTop+11, 58, 20, StringUtil.localize("info."+ModUtil.MOD_ID+".gui.ok"));
        this.buttonList.add(buttonOkay);
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        String text1 = this.machine.storage.getEnergyStored()+"/"+this.machine.storage.getMaxEnergyStored()+" RF";
        if(x >= this.guiLeft+16 && y >= this.guiTop+5 && x <= this.guiLeft+23 && y <= this.guiTop+89){
            this.drawHoveringText(Collections.singletonList(text1), x, y);
        }
        String text3 = StringUtil.getFluidInfo(this.machine.tank);
        if(x >= this.guiLeft+27 && y >= this.guiTop+5 && x <= this.guiLeft+33 && y <= this.guiTop+70){
            this.drawHoveringText(Collections.singletonList(text3), x, y);
        }

        String text2 = this.machine.coffeeCacheAmount+"/"+TileEntityCoffeeMachine.COFFEE_CACHE_MAX_AMOUNT+" "+StringUtil.localize("info."+ModUtil.MOD_ID+".gui.coffee");
        if(x >= this.guiLeft+40 && y >= this.guiTop+25 && x <= this.guiLeft+49 && y <= this.guiTop+56){
            this.drawHoveringText(Collections.singletonList(text2), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.machine);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.machine.storage.getEnergyStored() > 0){
            int i = this.machine.getEnergyScaled(83);
            this.drawTexturedModalRect(this.guiLeft+17, this.guiTop+89-i, 176, 0, 6, i);
        }
        if(this.machine.tank.getFluidAmount() > 0){
            int i = this.machine.getWaterScaled(64);
            this.drawTexturedModalRect(this.guiLeft+27, this.guiTop+70-i, 182, 0, 6, i);
        }

        if(this.machine.coffeeCacheAmount > 0){
            int i = this.machine.getCoffeeScaled(30);
            this.drawTexturedModalRect(this.guiLeft+41, this.guiTop+56-i, 192, 0, 8, i);
        }

        if(this.machine.brewTime > 0){
            int i = this.machine.getBrewScaled(23);
            this.drawTexturedModalRect(this.guiLeft+53, this.guiTop+42, 192, 30, i, 16);

            int j = this.machine.getBrewScaled(26);
            this.drawTexturedModalRect(this.guiLeft+99+25-j, this.guiTop+44, 192+25-j, 46, j, 12);
        }
    }

    @Override
    public void actionPerformed(GuiButton button){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("X", this.x);
        compound.setInteger("Y", this.y);
        compound.setInteger("Z", this.z);
        compound.setInteger("WorldID", this.world.provider.getDimension());
        compound.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
        compound.setInteger("ButtonID", button.id);
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
    }
}