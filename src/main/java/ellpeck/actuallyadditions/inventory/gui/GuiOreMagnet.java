/*
 * This file ("GuiOreMagnet.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.inventory.ContainerOreMagnet;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityOreMagnet;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiOreMagnet extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiOreMagnet");
    private TileEntityOreMagnet magnet;

    public GuiOreMagnet(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerOreMagnet(inventory, tile));
        this.magnet = (TileEntityOreMagnet)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);
        String text1 = this.magnet.storage.getEnergyStored()+"/"+this.magnet.storage.getMaxEnergyStored()+" RF";
        if(x >= guiLeft+43 && y >= guiTop+6 && x <= guiLeft+58 && y <= guiTop+88){
            this.func_146283_a(Collections.singletonList(text1), x, y);
        }
        String text2 = this.magnet.tank.getFluidAmount()+"/"+this.magnet.tank.getCapacity()+" mB "+StringUtil.localize("fluid.oil");
        if(x >= guiLeft+117 && y >= guiTop+6 && x <= guiLeft+132 && y <= guiTop+88){
            this.func_146283_a(Collections.singletonList(text2), x, y);
        }
        //TODO Upgrade Slot Joke
        if(x >= guiLeft+70 && y >= guiTop+42 && x <= guiLeft+70+18 && y <= guiTop+42+18){
            this.func_146283_a(Arrays.asList("@SuppressWarnings(\"unused\")", "This slot is currently unused. Ignore it."), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.magnet.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.magnet.storage.getEnergyStored() > 0){
            int i = this.magnet.getEnergyScaled(83);
            drawTexturedModalRect(this.guiLeft+43, this.guiTop+89-i, 176, 0, 16, i);
        }

        if(this.magnet.tank.getFluidAmount() > 0){
            int i = this.magnet.getTankScaled(83);
            drawTexturedModalRect(this.guiLeft+117, this.guiTop+89-i, 192, 0, 16, i);
        }
    }
}