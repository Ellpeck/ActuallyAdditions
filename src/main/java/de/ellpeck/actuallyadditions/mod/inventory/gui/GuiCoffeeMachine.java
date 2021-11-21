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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiCoffeeMachine extends GuiWtfMojang<ContainerCoffeeMachine> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_coffee_machine");
    private final TileEntityCoffeeMachine machine;

    private EnergyDisplay energy;
    private FluidDisplay fluid;

    public GuiCoffeeMachine(ContainerCoffeeMachine container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.machine = container.machine;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();

//        Button buttonOkay = new Button(this.leftPos + 60, this.topPos + 11, 58, 20, StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.ok"));
//        this.addButton(buttonOkay);
//
//        this.energy = new EnergyDisplay(this.leftPos + 16, this.topPos + 5, this.machine.storage);
//        this.fluid = new FluidDisplay(this.leftPos - 30, this.topPos + 1, this.machine.tank, true, false);
    }

    @Override
    public void render(MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);

        String text2 = this.machine.coffeeCacheAmount + "/" + TileEntityCoffeeMachine.COFFEE_CACHE_MAX_AMOUNT + " " + StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.coffee");
        if (x >= this.leftPos + 40 && y >= this.topPos + 25 && x <= this.leftPos + 49 && y <= this.topPos + 56) {
            //this.drawHoveringText(Collections.singletonList(text2), x, y);
        }

        this.energy.render(matrices, x, y);
        this.fluid.render(matrices, x, y);
    }

    @Override
    public void renderLabels(MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.machine);
    }

    @Override
    public void renderBg(MatrixStack matrices, float f, int x, int y) {
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

//    @Override
//    public void actionPerformed(Button button) {
//        PacketHandlerHelper.sendButtonPacket(this.machine, button.id);
//    }
}
