package de.ellpeck.actuallyadditions.mod.blocks.blockhuds;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityVerticalDigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class MinerHud implements IBlockHud {
    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }
        BlockEntity tile = minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (tile instanceof TileEntityVerticalDigger miner) {
            String info = miner.checkY == 0
                    ? "Done Mining!"
                    : miner.checkY == -1
                    ? "Calculating positions..."
                    : "Mining at " + (miner.getBlockPos().getX() + miner.checkX) + ", " + miner.checkY + ", " + (miner.getBlockPos().getZ() + miner.checkZ) + ".";
            guiGraphics.drawString(minecraft.font, info, (int) (resolution.getGuiScaledWidth() / 2f + 5), (int) (resolution.getGuiScaledHeight() / 2f - 20), 0xFFFFFF);
        }
    }
}
