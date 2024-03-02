/*
 * This file ("GuiFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPoweredFurnace;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class GuiFurnaceDouble extends AAScreen<ContainerFurnaceDouble> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_furnace_double");
    private final TileEntityPoweredFurnace tileFurnace;
    private EnergyDisplay energy;

    private Button buttonAutoSplit;

    public GuiFurnaceDouble(ContainerFurnaceDouble container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.tileFurnace = container.furnace;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void render(@Nonnull PoseStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);
        this.energy.render(matrices, x, y);

        if (this.buttonAutoSplit.isMouseOver(x, y)) {
            renderComponentTooltip(matrices, Collections.singletonList(this.tileFurnace.isAutoSplit
                ? new TranslatableComponent("info." + ActuallyAdditions.MODID + ".gui.autoSplitItems.on").withStyle(ChatFormatting.BOLD)
                : new TranslatableComponent("info." + ActuallyAdditions.MODID + ".gui.autoSplitItems.off").withStyle(ChatFormatting.BOLD)), x, y);
        }
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 27, this.topPos + 5, this.tileFurnace.storage);
        this.buttonAutoSplit = new Buttons.SmallerButton(this.leftPos, this.topPos, new TextComponent("S"), (button) -> PacketHandlerHelper.sendButtonPacket(this.tileFurnace, 0));
        buttonAutoSplit.setFGColor(this.tileFurnace.isAutoSplit ? ChatFormatting.DARK_GREEN.getColor() : ChatFormatting.RED.getColor());
        this.addRenderableWidget(this.buttonAutoSplit);
    }


    @Override
    public void containerTick() {
        super.containerTick();
        buttonAutoSplit.setFGColor(this.tileFurnace.isAutoSplit ? ChatFormatting.DARK_GREEN.getColor() : ChatFormatting.RED.getColor());
    }

    @Override
    public void renderBg(@Nonnull PoseStack matrices, float f, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        RenderSystem.setShaderTexture(0, RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.tileFurnace.firstSmeltTime > 0) {
            int i = this.tileFurnace.getFirstTimeToScale(23);
            this.blit(matrices, this.leftPos + 51, this.topPos + 40, 176, 0, 24, i);
        }
        if (this.tileFurnace.secondSmeltTime > 0) {
            int i = this.tileFurnace.getSecondTimeToScale(23);
            this.blit(matrices, this.leftPos + 101, this.topPos + 40, 176, 22, 24, i);
        }

        this.energy.draw(matrices);
    }
}
