/*
 * This file ("GuiBag.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.inventory.ContainerBag;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiBag extends GuiWtfMojang<ContainerBag> {
    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_bag");
    private static final ResourceLocation RES_LOC_VOID = AssetUtil.getGuiLocation("gui_void_bag");

    private final ContainerBag container;
    private final boolean isVoid;
    private FilterSettingsGui filter;
    private Button buttonAutoInsert;

    public GuiBag(ContainerBag container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory);
        this.imageWidth = 176;
        this.imageHeight = 90 + 86;
        this.isVoid = container.isVoid;
        this.container = container;
    }

    @Override
    public void init() {
        super.init();

        this.filter = new FilterSettingsGui(this.container.filter, this.leftPos + 138, this.topPos + 10, this.buttons);

        this.buttonAutoInsert = new Button(0, this.leftPos - 21, this.topPos + 8, 20, 20, (this.container.autoInsert
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED) + "I");
        this.addButton(this.buttonAutoInsert);
    }

    @Override
    protected void actionPerformed(Button button) throws IOException {
        CompoundNBT data = new CompoundNBT();
        data.putInt("ButtonID", button.id);
        data.putInt("PlayerID", Minecraft.getInstance().player.getId());
        data.putInt("WorldID", Minecraft.getInstance().level.provider.getDimension());
        PacketHandler.THE_NETWORK.sendToServer(new PacketClientToServer(data, PacketHandler.GUI_BUTTON_TO_CONTAINER_HANDLER));
    }

    @Override
    public void tick() {
        super.tick();
        this.filter.tick();

        //this.buttonAutoInsert.displayString = (this.container.autoInsert
        //    ? TextFormatting.DARK_GREEN
        //    : TextFormatting.RED) + "I";
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.font, this.imageWidth, -10, StringUtil.localize("container." + ActuallyAdditions.MODID + "." + (this.isVoid
            ? "voidBag"
            : "bag") + ".name"));
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        this.filter.drawHover(mouseX, mouseY);

        if (this.buttonAutoInsert.isMouseOver()) {
            List<String> text = new ArrayList<>();
            text.add(TextFormatting.BOLD + "Auto-Insert " + (this.container.autoInsert
                ? "On"
                : "Off"));
            text.addAll(this.font.listFormattedStringToWidth("Turn this on to make items that get picked up automatically go into the bag.", 200));
            text.addAll(this.font.listFormattedStringToWidth(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Note that this WON'T work when you are holding the bag in your hand.", 200));
            this.renderToolTip(stack, text, mouseX, mouseY, this.getMinecraft().font);
        }
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrixStack, this.leftPos, this.topPos + 90, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(this.isVoid
            ? RES_LOC_VOID
            : RES_LOC);
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, 176, 90);
    }
}
