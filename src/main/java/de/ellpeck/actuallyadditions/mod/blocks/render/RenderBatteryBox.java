package de.ellpeck.actuallyadditions.mod.blocks.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ItemBattery;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBatteryBox;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraftforge.energy.CapabilityEnergy;

import java.text.NumberFormat;

// todo: migrate to client package
public class RenderBatteryBox extends TileEntityRenderer<TileEntityBatteryBox> {

    public RenderBatteryBox(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntityBatteryBox tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
//        BlockPos pos = tile.getPos();
        ItemStack stack = tile.inv.getStackInSlot(0);
        if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemBattery) {
            matrices.push();
//            matrices.translate((float) pos.getX() + 0.5F, (float) pos.getY() + 1F, (float) pos.getZ() + 0.5F);

            matrices.push();

            matrices.scale(0.0075F, 0.0075F, 0.0075F);
            matrices.rotate(new Quaternion(180F, 1F, 0F, 0F));
            matrices.translate(0F, 0F, -50F);

            stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> {
                NumberFormat format = NumberFormat.getInstance();
                FontRenderer font = Minecraft.getInstance().fontRenderer;

                String s = format.format(cap.getEnergyStored()) + "/" + format.format(cap.getMaxEnergyStored());
                float lengthS = -font.getStringWidth(s) / 2F;
                String s2 = "Crystal Flux";
                float lengthS2 = -font.getStringWidth(s2) / 2F;

                for (int i = 0; i < 4; i++) {
                    font.drawString(s, lengthS, 10F, 0xFFFFFF);
                    font.drawString(s2, lengthS2, 20F, 0xFFFFFF);

                    matrices.translate(-50F, 0F, 50F);
                    matrices.rotate(new Quaternion(90F, 0F, 1F, 0F));
                }
            });

            matrices.pop();

            double boop = Util.milliTime() / 800D;
            matrices.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
            matrices.rotate(new Quaternion((float) (boop * 40D % 360), 0, 1, 0));

            float scale = stack.getItem() instanceof BlockItem ? 0.85F : 0.65F;
            matrices.scale(scale, scale, scale);
            try {
                AssetUtil.renderItemInWorld(stack);
            } catch (Exception e) {
                ActuallyAdditions.LOGGER.error("Something went wrong trying to render an item in a battery box! The item is " + stack.getItem().getRegistryName() + "!", e);
            }

            matrices.pop();
        }
    }
}
