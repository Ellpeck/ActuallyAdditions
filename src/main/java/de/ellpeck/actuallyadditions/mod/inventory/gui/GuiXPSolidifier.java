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

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerXPSolidifier;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityXPSolidifier;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;


@OnlyIn(Dist.CLIENT)
public class GuiXPSolidifier extends AAScreen<ContainerXPSolidifier> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_xp_solidifier");
    private final TileEntityXPSolidifier solidifier;

    public GuiXPSolidifier(ContainerXPSolidifier container, PlayerInventory inventory, ITextComponent title) {
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
    public void renderLabels(@Nonnull MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.solidifier);
    }

    @Override
    public void renderBg(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(RES_LOC);
        this.blit(matrices, this.leftPos, this.topPos, 0, 0, 176, 93);

        drawCenteredString(matrices, this.font, Integer.toString(this.solidifier.amount), this.leftPos + 88, this.topPos + 30, StringUtil.DECIMAL_COLOR_WHITE);
    }

//    @Override
//    public void actionPerformed(Button button) {
//        PacketHandlerHelper.sendButtonPacket(this.solidifier, button.id);
//
//        this.solidifier.onButtonPressed(button.id, Minecraft.getInstance().player);
//    }
}
