/*
 * This file ("GuiGiantChest.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerGiantChest;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChest;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChestLarge;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGiantChestMedium;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiGiantChest extends GuiContainer{

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_giant_chest");

    private final TileEntityGiantChest chest;
    private final int page;

    public GuiGiantChest(InventoryPlayer inventory, TileEntityBase tile, int page){
        super(new ContainerGiantChest(inventory, tile, page));
        this.chest = (TileEntityGiantChest)tile;
        this.page = page;

        this.xSize = 242;
        this.ySize = 172+86;
    }

    @Override
    public void initGui(){
        super.initGui();

        if(this.page > 0){
            this.buttonList.add(new GuiButton(this.page-1, this.guiLeft+13, this.guiTop+172, 20, 20, "<"));
        }

        if((this.page == 0 && this.chest instanceof TileEntityGiantChestMedium) || (this.page <= 1 && this.chest instanceof TileEntityGiantChestLarge)){
            this.buttonList.add(new GuiButton(this.page+1, this.guiLeft+209, this.guiTop+172, 20, 20, ">"));
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        if(button.id >= 0 && button.id < 3){
            PacketHandlerHelper.sendButtonPacket(this.chest, button.id);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.chest);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(RES_LOC);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 242, 190);
        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft+33, this.guiTop+172, 0, 0, 176, 86);
    }
}