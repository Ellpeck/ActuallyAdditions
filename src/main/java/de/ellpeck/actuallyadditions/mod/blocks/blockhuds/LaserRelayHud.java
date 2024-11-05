package de.ellpeck.actuallyadditions.mod.blocks.blockhuds;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.items.ItemEngineerGoggles;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LaserRelayHud implements IBlockHud {
    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }

        BlockPos pos = ((BlockHitResult) rayCast).getBlockPos();
        if (minecraft.level != null) {
            boolean wearing = ItemEngineerGoggles.isWearing(player);
            if (wearing || !stack.isEmpty()) {
                boolean compass = stack.getItem() == CommonConfig.Other.relayConfigureItem;
                if (wearing || compass || stack.getItem() instanceof ItemLaserWrench) {
                    BlockEntity tile = minecraft.level.getBlockEntity(pos);
                    if (tile instanceof TileEntityLaserRelay relay) {

                        Component strg = relay.getExtraDisplayString();
                        guiGraphics.drawString(minecraft.font, strg, (int) (resolution.getGuiScaledWidth() / 2f + 5), (int) (resolution.getGuiScaledHeight() / 2f + 5), 0xFFFFFF);

                        Component expl;
                        if (compass) {
                            expl = relay.getCompassDisplayString();
                        } else {
                            expl = Component.translatable("info.actuallyadditions.laserRelay.mode.noCompasss", Component.translatable(CommonConfig.Other.relayConfigureItem.getDescriptionId()).getString()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC);
                        }

                        guiGraphics.drawString(minecraft.font, expl, (int) (resolution.getGuiScaledWidth() / 2f + 5), (int) (resolution.getGuiScaledHeight() / 2f + 15), 0xFFFFFF);
                    }
                }
            }
        }
    }
}
