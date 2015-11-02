/*
 * This file ("GuiFermentingBarrel.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.inventory.ContainerFermentingBarrel;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityFermentingBarrel;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiFermentingBarrel extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiFermentingBarrel");
    private TileEntityFermentingBarrel press;

    public GuiFermentingBarrel(InventoryPlayer inventory, TileEntityBase tile){
        super(new ContainerFermentingBarrel(inventory, tile));
        this.press = (TileEntityFermentingBarrel)tile;
        this.xSize = 176;
        this.ySize = 93+86;
    }

    @Override
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        String text1 = this.press.canolaTank.getFluidAmount()+"/"+this.press.canolaTank.getCapacity()+" mB "+StringUtil.localize("fluid.canolaoil");
        if(x >= guiLeft+61 && y >= guiTop+6 && x <= guiLeft+76 && y <= guiTop+88){
            this.func_146283_a(Collections.singletonList(text1), x, y);
        }

        String text2 = this.press.oilTank.getFluidAmount()+"/"+this.press.oilTank.getCapacity()+" mB "+StringUtil.localize("fluid.oil");
        if(x >= guiLeft+99 && y >= guiTop+6 && x <= guiLeft+114 && y <= guiTop+88){
            this.func_146283_a(Collections.singletonList(text2), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.press.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if(this.press.canolaTank.getFluidAmount() > 0){
            int i = this.press.getCanolaTankScaled(83);
            drawTexturedModalRect(this.guiLeft+61, this.guiTop+89-i, 192, 29, 16, i);
        }

        if(this.press.oilTank.getFluidAmount() > 0){
            int i = this.press.getOilTankScaled(83);
            drawTexturedModalRect(this.guiLeft+99, this.guiTop+89-i, 176, 29, 16, i);
        }

        if(this.press.currentProcessTime > 0){
            int i = this.press.getProcessScaled(29);
            drawTexturedModalRect(this.guiLeft+82, this.guiTop+34, 176, 0, 12, i);
        }
    }
}