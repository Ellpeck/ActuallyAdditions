/*
 * This file ("GuiRangedCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerRangedCollector;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityRangedCollector;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class GuiRangedCollector extends GuiWtfMojang<ContainerRangedCollector> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_ranged_collector");
    private final TileEntityRangedCollector collector;

    private FilterSettingsGui filter;

    public GuiRangedCollector(ContainerRangedCollector container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.collector = container.collector;
        this.imageWidth = 176;
        this.imageHeight = 86 + 86;
    }

    @Override
    public void init() {
        super.init();

        this.filter = new FilterSettingsGui(this.collector.filter, this.leftPos + 3, this.topPos + 6, this.buttonList);
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);

        this.filter.drawHover(matrices, x, y);
    }

    @Override
    public void tick() {
        super.tick();

        this.filter.tick();
    }

    @Override
    public void renderLabels(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.collector);
    }

    @Override
    public void renderBg(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 86, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 86);
    }

    @Override
    public void actionPerformed(Button button) {
        PacketHandlerHelper.sendButtonPacket(this.collector, button.id);
    }
}
