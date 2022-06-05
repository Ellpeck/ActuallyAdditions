/*
 * This file ("GuiCoffeeMachine.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class GuiCoffeeMachine extends AAScreen<ContainerCoffeeMachine> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_coffee_machine");
    private final TileEntityCoffeeMachine machine;

    private EnergyDisplay energy;
    private FluidDisplay fluid;

    public GuiCoffeeMachine(ContainerCoffeeMachine container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.machine = container.machine;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();

        this.addButton(new Button(this.leftPos + 60, this.topPos + 11, 58, 20, new TranslationTextComponent("info.actuallyadditions.gui.ok"),
            (b) -> PacketHandlerHelper.sendButtonPacket(this.machine, 0)));

        this.energy = new EnergyDisplay(this.leftPos + 16, this.topPos + 5, this.machine.storage);
        this.fluid = new FluidDisplay(this.leftPos - 30, this.topPos + 1, this.machine.tank, true, false);
    }

    @Override
    public void render(@Nonnull MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);
        Minecraft mc = Minecraft.getInstance();

        TextComponent text = new TranslationTextComponent("info.actuallyadditions.gui.coffee_amount", this.machine.coffeeCacheAmount, TileEntityCoffeeMachine.COFFEE_CACHE_MAX_AMOUNT);
        if (x >= this.leftPos + 40 && y >= this.topPos + 25 && x <= this.leftPos + 49 && y <= this.topPos + 56) {
            GuiUtils.drawHoveringText(matrices, Collections.singletonList(text), x, y, mc.getWindow().getWidth(), mc.getWindow().getWidth(), -1, font);

        }

        this.energy.render(matrices, x, y);
        this.fluid.render(matrices, x, y);
    }

    @Override
    public void renderBg(@Nonnull MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

        if (this.machine.coffeeCacheAmount > 0) {
            int i = this.machine.getCoffeeScaled(30);
            this.blit(matrices, this.leftPos + 41, this.topPos + 56 - i, 192, 0, 8, i);
        }

        if (this.machine.brewTime > 0) {
            int i = this.machine.getBrewScaled(23);
            this.blit(matrices, this.leftPos + 53, this.topPos + 42, 192, 30, i, 16);

            int j = this.machine.getBrewScaled(26);
            this.blit(matrices, this.leftPos + 99 + 25 - j, this.topPos + 44, 192 + 25 - j, 46, j, 12);
        }

        this.energy.draw(matrices);
        this.fluid.draw(matrices);
    }
}
