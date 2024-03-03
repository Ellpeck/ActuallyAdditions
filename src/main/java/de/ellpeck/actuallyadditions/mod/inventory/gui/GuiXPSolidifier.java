/*
 * This file ("GuiXPSolidifier.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerXPSolidifier;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityXPSolidifier;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class GuiXPSolidifier extends AAScreen<ContainerXPSolidifier> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_xp_solidifier");
    private final TileEntityXPSolidifier solidifier;

    public GuiXPSolidifier(ContainerXPSolidifier container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.solidifier = container.solidifier;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void init() {
        super.init();
//
//        Button buttonOne = new GuiInputter.SmallerButton(this.leftPos + 62, this.topPos + 44, new StringTextComponent("1"), btn -> {
//        });
//        Button buttonFive = new GuiInputter.SmallerButton(this.leftPos + 80, this.topPos + 44, new StringTextComponent("5"), btn -> {
//        });
//        Button buttonTen = new GuiInputter.SmallerButton(this.leftPos + 99, this.topPos + 44, new StringTextComponent("10"), btn -> {
//        });
//        Button buttonTwenty = new GuiInputter.SmallerButton(this.leftPos + 62, this.topPos + 61, new StringTextComponent("20"), btn -> {
//        });
//        Button buttonThirty = new GuiInputter.SmallerButton(this.leftPos + 80, this.topPos + 61, new StringTextComponent("30"), btn -> {
//        });
//        Button buttonForty = new GuiInputter.SmallerButton(this.leftPos + 99, this.topPos + 61, new StringTextComponent("40"), btn -> {
//        });
//        Button buttonFifty = new GuiInputter.SmallerButton(this.leftPos + 62, this.topPos + 78, new StringTextComponent("50"), btn -> {
//        });
//        Button buttonSixtyFour = new GuiInputter.SmallerButton(this.leftPos + 80, this.topPos + 78, new StringTextComponent("64"), btn -> {
//        });
//        Button buttonAll = new GuiInputter.SmallerButton(this.leftPos + 99, this.topPos + 78, new StringTextComponent("All"), btn -> {
//        });
//
//        this.addButton(buttonOne);
//        this.addButton(buttonFive);
//        this.addButton(buttonTen);
//        this.addButton(buttonTwenty);
//        this.addButton(buttonThirty);
//        this.addButton(buttonForty);
//        this.addButton(buttonFifty);
//        this.addButton(buttonSixtyFour);
//        this.addButton(buttonAll);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float f, int x, int y) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        guiGraphics.blit(AssetUtil.GUI_INVENTORY_LOCATION, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        guiGraphics.blit(RES_LOC, this.leftPos, this.topPos, 0, 0, 176, 93);

        guiGraphics.drawCenteredString(this.font, Integer.toString(this.solidifier.amount), this.leftPos + 88, this.topPos + 30, 0xFFFFFF);
    }

//    @Override
//    public void actionPerformed(Button button) {
//        PacketHandlerHelper.sendButtonPacket(this.solidifier, button.id);
//
//        this.solidifier.onButtonPressed(button.id, Minecraft.getInstance().player);
//    }
}
