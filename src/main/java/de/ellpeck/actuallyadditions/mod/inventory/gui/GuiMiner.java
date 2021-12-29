/*
 * This file ("GuiMiner.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.inventory.ContainerMiner;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityVerticalDigger;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;


@OnlyIn(Dist.CLIENT)
public class GuiMiner extends AAScreen<ContainerMiner> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_breaker");
    private final TileEntityVerticalDigger miner;

    public GuiMiner(ContainerMiner container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.miner = container.miner;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();

//        Button buttonMode = new Button(this.leftPos + this.imageWidth / 2 - 51, this.topPos + 75, 50, 20, "Mode", button -> {
//        });
//        this.addButton(buttonMode);
//
//        Button buttonReset = new Button(this.leftPos + this.imageWidth / 2 + 1, this.topPos + 75, 50, 20, "Reset", button -> {
//        });
//        this.addButton(buttonReset);
    }

    @Override
    public void renderLabels(@Nonnull MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.miner);
    }

    @Override
    public void renderBg(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

        String mining = this.miner.onlyMineOres
            ? "Only Mining Ores"
            : "Mining Everything";
        this.font.draw(matrices, mining, this.leftPos + this.imageWidth / 2 - this.font.width(mining) / 2, this.topPos + 8, StringUtil.DECIMAL_COLOR_GRAY_TEXT);
    }

//    @Override
//    public void actionPerformed(Button button) {
//        PacketHandlerHelper.sendButtonPacket(this.miner, button.id);
//    }
}
