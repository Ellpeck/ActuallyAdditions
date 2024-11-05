package de.ellpeck.actuallyadditions.mod.blocks.blockhuds;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.api.tile.IPhantomTile;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PhantomHud implements IBlockHud {
    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }
        BlockPos pos = ((BlockHitResult) rayCast).getBlockPos();
        BlockEntity tile = minecraft.level.getBlockEntity(pos);
        if (tile != null) {
            if (tile instanceof IPhantomTile phantom) {
                guiGraphics.drawString(minecraft.font, Component.translatable("tooltip.actuallyadditions.blockPhantomRange.desc").append(": " + phantom.getRange()).withStyle(ChatFormatting.GOLD), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 - 40, ChatFormatting.WHITE.getColor());
                if (phantom.hasBoundPosition()) {
                    int distance = Mth.ceil(new Vec3(pos.getX(), pos.getY(), pos.getZ()).distanceTo(new Vec3(phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ())));
                    BlockState state = minecraft.level.getBlockState(phantom.getBoundPosition());
                    Block block = state.getBlock();
                    Item item = Item.byBlock(block);
                    String name = item.getName(new ItemStack(block)).getString();
                    drawWordWrap(guiGraphics, minecraft.font, Component.translatable("tooltip.actuallyadditions.phantom.blockInfo.desc", name, phantom.getBoundPosition().getX(), phantom.getBoundPosition().getY(), phantom.getBoundPosition().getZ(), distance), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 - 30, 200, 0xFFFFFF, true);

                    if (phantom.isBoundThingInRange()) {
                        drawWordWrap(guiGraphics, minecraft.font, Component.translatable("tooltip.actuallyadditions.phantom.connectedRange.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, 200, 0xFFFFFF, true);
                    } else {
                        drawWordWrap(guiGraphics, minecraft.font, Component.translatable("tooltip.actuallyadditions.phantom.connectedNoRange.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, 200, 0xFFFFFF, true);
                    }
                } else {
                    guiGraphics.drawString(minecraft.font, Component.translatable("tooltip.actuallyadditions.phantom.notConnected.desc").withStyle(ChatFormatting.RED), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 25, ChatFormatting.WHITE.getColor());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWordWrap(GuiGraphics gg, Font font, FormattedText text, int x, int y, int width, int color, boolean shadow) {
        for (FormattedCharSequence line : font.split(text, width)) {
            gg.drawString(font, line, x, y, color, shadow);
            y += font.lineHeight;
        }
    }
}
