package de.ellpeck.actuallyadditions.mod.blocks.blockhuds;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlayerInterface;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PlayerInterfaceHud implements IBlockHud {
    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }

        BlockEntity tile = minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (tile != null) {
            if (tile instanceof TileEntityPlayerInterface face) {
                String name = face.playerName == null
                        ? "Unknown"
                        : face.playerName;
                guiGraphics.drawString(minecraft.font, "Bound to: " + ChatFormatting.RED + name, (int) (resolution.getGuiScaledWidth() / 2f + 5), (int) (resolution.getGuiScaledHeight() / 2f + 5), 0xFFFFFF);
                guiGraphics.drawString(minecraft.font, "UUID: " + ChatFormatting.DARK_GREEN + face.connectedPlayer, (int) (resolution.getGuiScaledWidth() / 2f + 5), (int) (resolution.getGuiScaledHeight() / 2f + 15), 0xFFFFFF);
            }
        }
    }
}
