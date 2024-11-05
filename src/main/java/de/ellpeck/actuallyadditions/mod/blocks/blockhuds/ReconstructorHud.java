package de.ellpeck.actuallyadditions.mod.blocks.blockhuds;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ReconstructorHud implements IBlockHud {
    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult) || minecraft.level == null) {
            return;
        }

        BlockEntity tile = minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (tile instanceof TileEntityAtomicReconstructor) {
            ItemStack slot = ((TileEntityAtomicReconstructor) tile).inv.getStackInSlot(0);
            Component lens_name;
            if (slot.isEmpty()) {
                lens_name = Component.translatable("info.actuallyadditions.nolens");
            } else {
                lens_name = slot.getItem().getName(slot);

                AssetUtil.renderStackToGui(slot, resolution.getGuiScaledWidth() / 2 + 15, resolution.getGuiScaledHeight() / 2 - 19, 1F);
            }
            guiGraphics.drawString(minecraft.font, lens_name.plainCopy().withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC).getString(), (int) (resolution.getGuiScaledWidth() / 2.0f + 35), (int) (resolution.getGuiScaledHeight() / 2.0f - 15), 0xFFFFFF);
        }
    }
}
