package de.ellpeck.actuallyadditions.client.render.tiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.useables.BatteryItem;
import de.ellpeck.actuallyadditions.common.tiles.BatteryBoxTile;
import de.ellpeck.actuallyadditions.common.utilities.ClientHelp;
import de.ellpeck.actuallyadditions.common.utilities.Help;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.energy.CapabilityEnergy;

public class BatteryBoxTileRender extends TileEntityRenderer<BatteryBoxTile> {
    public BatteryBoxTileRender(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BatteryBoxTile tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = tile.getItemStackHandler().getStackInSlot(0);
        if (stack.isEmpty() || !(stack.getItem() instanceof BatteryItem))
            return;

        matrices.push();
        matrices.translate(.5f, .35f, .5f);
        matrices.rotate(Vector3f.ZP.rotationDegrees(180));

        matrices.push();
        matrices.scale(0.0075F, 0.0075F, 0.0075F);
        matrices.translate(0F, 0F, -60F);

        // Display the energy value on each side of the block.
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energy -> {
            FontRenderer font = Minecraft.getInstance().fontRenderer;

            String energyTotal = Help.cleanEnergyValues(energy, false);
            String energyName = ClientHelp.i18n("energy.crystal-flux-long");

            for (int i = 0; i < 4; i++) {
                font.drawString(matrices, energyTotal, -font.getStringWidth(energyTotal) / 2F, 10F, 0xFFFFFF);
                font.drawString(matrices, energyName,  -font.getStringWidth(energyName) / 2F, 20F, 0xFFFFFF);

                matrices.translate(-60F, 0F, 60F);
                matrices.rotate(Vector3f.YP.rotationDegrees(90));
            }
        });

        matrices.pop(); // text rotation
        matrices.pop(); // rotation + centering

        double boop = Util.milliTime() / 800D;
        float scale = stack.getItem() instanceof BlockItem ? 0.85F : 0.65F;

        matrices.push();
        matrices.translate(.5f, 1f + Math.sin(boop % (2 * Math.PI)) * 0.065, .5f);
        matrices.rotate(Vector3f.YP.rotationDegrees((float) (boop * 40D % 360)));
        matrices.scale(scale, scale, scale);

        try {
            Minecraft.getInstance().getItemRenderer().renderItem(
                    stack, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrices, buffer
            );
        } catch (Exception e) {
            ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in a battery box! The item is " + stack.getItem().getRegistryName() + "!", e);
        }

        matrices.pop();
    }
}
