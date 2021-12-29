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

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPoweredFurnace;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GuiFurnaceDouble extends AAScreen<ContainerFurnaceDouble> {

    private static final ResourceLocation RES_LOC = AssetUtil.getGuiLocation("gui_furnace_double");
    private final TileEntityPoweredFurnace tileFurnace;
    private EnergyDisplay energy;

    private Button buttonAutoSplit;

    public GuiFurnaceDouble(ContainerFurnaceDouble container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.tileFurnace = container.furnace;
        this.imageWidth = 176;
        this.imageHeight = 93 + 86;
    }

    @Override
    public void render(@Nonnull MatrixStack matrices, int x, int y, float f) {
        super.render(matrices, x, y, f);
        this.energy.render(matrices, x, y);

//        if (this.buttonAutoSplit.isMouseOver(x, y)) {
//            this.drawHoveringText(Collections.singletonList(TextFormatting.BOLD + (this.tileFurnace.isAutoSplit
//                ? StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.autoSplitItems.on")
//                : StringUtil.localize("info." + ActuallyAdditions.MODID + ".gui.autoSplitItems.off"))), x, y);
//        }
    }

    @Override
    public void init() {
        super.init();
        this.energy = new EnergyDisplay(this.leftPos + 27, this.topPos + 5, this.tileFurnace.storage);

//        this.buttonAutoSplit = new GuiInputter.SmallerButton(0, this.leftPos, this.topPos, "S");
//        this.addButton(this.buttonAutoSplit);
    }


    @Override
    public void tick() {
        super.tick();

        this.buttonAutoSplit.setMessage(new StringTextComponent("S").withStyle(this.tileFurnace.isAutoSplit
            ? TextFormatting.DARK_GREEN
            : TextFormatting.RED));
    }

//    @Override
//    protected void actionPerformed(Button button) throws IOException {
//        if (button.id == 0) {
//            PacketHandlerHelper.sendButtonPacket(this.tileFurnace, button.id);
//        }
//    }

    @Override
    public void renderLabels(@Nonnull MatrixStack matrices, int x, int y) {
        AssetUtil.displayNameString(matrices, this.font, this.imageWidth, -10, this.tileFurnace);
    }

    @Override
    public void renderBg(MatrixStack matrices, float f, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bind(AssetUtil.GUI_INVENTORY_LOCATION);
        this.blit(matrices, this.leftPos, this.topPos + 93, 0, 0, 176, 86);

        this.getMinecraft().getTextureManager().bind(RES_LOC);
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
