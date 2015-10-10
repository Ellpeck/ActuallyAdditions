/*
 * This file ("GuiRangedCollector.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.inventory.ContainerRangedCollector;
import ellpeck.actuallyadditions.network.PacketHandler;
import ellpeck.actuallyadditions.network.gui.PacketGuiButton;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityRangedCollector;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiRangedCollector extends GuiContainer{

    private static final ResourceLocation resLoc = AssetUtil.getGuiLocation("guiRangedCollector");
    private TileEntityRangedCollector collector;

    private GuiInputter.SmallerButton whitelistButton;

    private int x;
    private int y;
    private int z;
    private World world;

    public GuiRangedCollector(InventoryPlayer inventory, TileEntityBase tile, int x, int y, int z, World world){
        super(new ContainerRangedCollector(inventory, tile));
        this.collector = (TileEntityRangedCollector)tile;
        this.xSize = 176;
        this.ySize = 86+86;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui(){
        super.initGui();

        this.whitelistButton = new GuiInputter.SmallerButton(0, guiLeft+3, guiTop+16, "");
        this.buttonList.add(this.whitelistButton);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void drawScreen(int x, int y, float f){
        super.drawScreen(x, y, f);

        this.whitelistButton.displayString = this.collector.isWhitelist ? "O" : "X";

        String text1 = this.collector.isWhitelist ? StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".gui.whitelist") : StringUtil.localize("info."+ModUtil.MOD_ID_LOWER+".gui.blacklist");
        if(x >= guiLeft+3 && y >= guiTop+16 && x <= guiLeft+18 && y <= guiTop+31){
            this.func_146283_a(Collections.singletonList(text1), x, y);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.collector.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+86, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resLoc);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 86);
    }

    @Override
    public void actionPerformed(GuiButton button){
        PacketHandler.theNetwork.sendToServer(new PacketGuiButton(x, y, z, world, button.id, Minecraft.getMinecraft().thePlayer));
    }
}