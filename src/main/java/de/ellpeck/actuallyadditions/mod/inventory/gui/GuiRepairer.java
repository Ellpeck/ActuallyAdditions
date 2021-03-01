// TODO: [port][note] no longer needed
///*
// * This file ("GuiRepairer.java") is part of the Actually Additions mod for Minecraft.
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
//import com.mojang.blaze3d.platform.GlStateManager;
//import de.ellpeck.actuallyadditions.mod.inventory.ContainerRepairer;
//import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
//import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//
//@OnlyIn(Dist.CLIENT)
//public class GuiRepairer extends GuiWtfMojang {
//
//    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_repairer");
//    private final TileEntityItemRepairer tileRepairer;
//    private EnergyDisplay energy;
//
//    public GuiRepairer(PlayerInventory inventory, TileEntityBase tile) {
//        super(new ContainerRepairer(inventory, tile));
//        this.tileRepairer = (TileEntityItemRepairer) tile;
//        this.xSize = 176;
//        this.ySize = 93 + 86;
//    }
//
//    @Override
//    public void initGui() {
//        super.initGui();
//        this.energy = new EnergyDisplay(this.guiLeft + 27, this.guiTop + 5, this.tileRepairer.storage);
//    }
//
//    @Override
//    public void drawScreen(int x, int y, float f) {
//        super.drawScreen(x, y, f);
//        this.energy.drawOverlay(x, y);
//    }
//
//    @Override
//    public void drawGuiContainerForegroundLayer(int x, int y) {
//        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.tileRepairer);
//    }
//
//    @Override
//    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//
//        this.getMinecraft().getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
//        this.blit(matrices, this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);
//
//        this.getMinecraft().getTextureManager().bindTexture(RES_LOC);
//        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, 176, 93);
//
//        if (TileEntityItemRepairer.canBeRepaired(this.tileRepairer.inv.getStackInSlot(TileEntityItemRepairer.SLOT_INPUT))) {
//            int i = this.tileRepairer.getItemDamageToScale(22);
//            this.blit(matrices, this.guiLeft + 73, this.guiTop + 52, 176, 28, i, 16);
//        }
//
//        this.energy.draw();
//    }
//}
