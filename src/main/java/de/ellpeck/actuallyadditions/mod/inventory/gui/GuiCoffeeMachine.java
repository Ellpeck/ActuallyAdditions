/*
 * This file ("GuiCoffeeMachine.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.network.gui.PacketGuiButton;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiCoffeeMachine extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiCoffeeMachine");
    private TileEntityCoffeeMachine machine;
    private int x;
    private int y;
    private int z;
    private World world;

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

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        super.initGui();

        GuiButton buttonOkay = new GuiButton(0, guiLeft+60, guiTop+11, 58, 20, StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".gui.ok"));
        this.buttonList.add(buttonOkay);
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        String text1 = this.machine.storage.getEnergyStored()+"/"+this.machine.storage.getMaxEnergyStored()+" RF";
        if(x >= guiLeft+16 && y >= guiTop+5 && x <= guiLeft+23 && y <= guiTop+89){
            this.func_146283_a(Collections.singletonList(text1), x, y);
        }
        String text3 = StringUtil.getFluidInfo(this.machine.tank);
        if(x >= guiLeft+27 && y >= guiTop+5 && x <= guiLeft+33 && y <= guiTop+70){
            this.func_146283_a(Collections.singletonList(text3), x, y);
        }

        String text2 = this.machine.coffeeCacheAmount+"/"+TileEntityCoffeeMachine.COFFEE_CACHE_MAX_AMOUNT+" "+StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".gui.coffee");
        if(x >= guiLeft+40 && y >= guiTop+25 && x <= guiLeft+49 && y <= guiTop+56){
            this.func_146283_a(Collections.singletonList(text2), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.machine.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.machine.storage.getEnergyStored() > 0){
            int i = this.machine.getEnergyScaled(83);
            drawTexturedModalRect(this.guiLeft+17, this.guiTop+89-i, 176, 0, 6, i);
        }
        if(this.machine.tank.getFluidAmount() > 0){
            int i = this.machine.getWaterScaled(64);
            drawTexturedModalRect(this.guiLeft+27, this.guiTop+70-i, 182, 0, 6, i);
        }

        if(this.machine.coffeeCacheAmount > 0){
            int i = this.machine.getCoffeeScaled(30);
            drawTexturedModalRect(this.guiLeft+41, this.guiTop+56-i, 192, 0, 8, i);
        }

        if(this.machine.brewTime > 0){
            int i = this.machine.getBrewScaled(23);
            drawTexturedModalRect(this.guiLeft+53, this.guiTop+42, 192, 30, i, 16);

            int j = this.machine.getBrewScaled(26);
            drawTexturedModalRect(this.guiLeft+99+25-j, this.guiTop+44, 192+25-j, 46, j, 12);
        }
    }

    @Override
    public void actionPerformed(GuiButton button){
        PacketHandler.theNetwork.sendToServer(new PacketGuiButton(x, y, z, world, button.id, Minecraft.getMinecraft().thePlayer));
    }
}