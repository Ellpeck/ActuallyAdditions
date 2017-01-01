/*
 * This file ("GuiFireworkBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFireworkBox;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFireworkBox extends GuiContainer implements GuiResponder{

    private final TileEntityFireworkBox tile;

    public GuiFireworkBox(TileEntity tile){
        super(new ContainerFireworkBox());
        this.tile = (TileEntityFireworkBox)tile;
        this.xSize = 300;
        this.ySize = 120;
    }

    @Override
    public void initGui(){
        super.initGui();

        this.buttonList.add(new CustomSlider(this, 0, this.guiLeft, this.guiTop, "Value Play", 0F, 5F, this.tile.intValuePlay, IntFormatter.INSTANCE));
        this.buttonList.add(new CustomSlider(this, 1, this.guiLeft, this.guiTop+20, "Average Charge Amount", 1F, 4F, this.tile.chargeAmount, IntFormatter.INSTANCE));
        this.buttonList.add(new CustomSlider(this, 2, this.guiLeft, this.guiTop+40, "Average Flight Time", 1F, 3F, this.tile.flightTime, IntFormatter.INSTANCE));
        this.buttonList.add(new CustomSlider(this, 3, this.guiLeft, this.guiTop+60, "Effect Chance", 0F, 1F, this.tile.trailOrFlickerChance, null));
        this.buttonList.add(new CustomSlider(this, 4, this.guiLeft, this.guiTop+80, "Flicker/Trail Ratio", 0F, 1F, this.tile.flickerChance, null));
        this.buttonList.add(new CustomSlider(this, 5, this.guiLeft, this.guiTop+100, "Color Amount", 1, 6, this.tile.colorAmount, IntFormatter.INSTANCE));

        this.buttonList.add(new CustomSlider(this, 6, this.guiLeft+150, this.guiTop, "Small Ball", 0F, 1F, this.tile.typeChance0, null));
        this.buttonList.add(new CustomSlider(this, 7, this.guiLeft+150, this.guiTop+20, "Large Ball", 0F, 1F, this.tile.typeChance1, null));
        this.buttonList.add(new CustomSlider(this, 8, this.guiLeft+150, this.guiTop+40, "Star Shape", 0F, 1F, this.tile.typeChance2, null));
        this.buttonList.add(new CustomSlider(this, 9, this.guiLeft+150, this.guiTop+60, "Creeper Shape", 0F, 1F, this.tile.typeChance3, null));
        this.buttonList.add(new CustomSlider(this, 10, this.guiLeft+150, this.guiTop+80, "Burst", 0F, 1F, this.tile.typeChance4, null));

        this.buttonList.add(new CustomSlider(this, 11, this.guiLeft+150, this.guiTop+100, "Area of Effect", 0, 4, this.tile.areaOfEffect, IntFormatter.INSTANCE));
    }

    @Override
    public void setEntryValue(int id, float value){
        GuiButton button = this.buttonList.get(id);
        if(button instanceof GuiSlider){
            if(!((GuiSlider)button).isMouseDown){
                System.out.println("SETTING VALUE FOR "+id+"!!");
                PacketHandlerHelper.sendNumberPacket(this.tile, value, id);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){

    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, this.xSize, -10, this.tile);
    }

    @Override
    public void setEntryValue(int id, boolean value){

    }

    @Override
    public void setEntryValue(int id, String value){

    }

    private static class CustomSlider extends GuiSlider{

        private final GuiResponder responder;

        public CustomSlider(GuiResponder guiResponder, int idIn, int x, int y, String name, float min, float max, float defaultValue, FormatHelper formatter){
            super(guiResponder, idIn, x, y, name, min, max, defaultValue, formatter);
            this.responder = guiResponder;
        }

        @Override
        public void mouseReleased(int mouseX, int mouseY){
            super.mouseReleased(mouseX, mouseY);
            this.responder.setEntryValue(this.id, this.getSliderValue());
        }
    }

    private static class IntFormatter implements GuiSlider.FormatHelper{

        public static final IntFormatter INSTANCE = new IntFormatter();

        @Override
        public String getText(int id, String name, float value){
            return name+": "+(int)value;
        }
    }
}
