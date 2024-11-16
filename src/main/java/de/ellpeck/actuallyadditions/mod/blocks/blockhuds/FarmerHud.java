package de.ellpeck.actuallyadditions.mod.blocks.blockhuds;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFarmer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FarmerHud implements IBlockHud {
    @Override
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult) || minecraft.level == null) {
            return;
        }

        BlockEntity tile = minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (tile instanceof TileEntityFarmer farmer) {
            guiGraphics.drawString(minecraft.font, Component.translatable("info.actuallyadditions.farmer.area", farmer.getArea(), farmer.getArea()), (int) (resolution.getGuiScaledWidth() / 2.0f + 5), (int) (resolution.getGuiScaledHeight() / 2.0f - 0), 0xFFFFFF);

            Component message;
            if (!stack.isEmpty() && stack.getItem() == CommonConfig.Other.farmerConfigureItem) {
                message = Component.translatable("info.actuallyadditions.farmer.validItem").withStyle(ChatFormatting.GREEN);
            } else {
                message = Component.translatable("info.actuallyadditions.farmer.invalidItem", Component.translatable(CommonConfig.Other.farmerConfigureItem.asItem().getDescriptionId()).getString()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC);
            }
            guiGraphics.drawString(minecraft.font, message, (int) (minecraft.getWindow().getGuiScaledWidth() / 2f + 5), (int) (minecraft.getWindow().getGuiScaledHeight() / 2f + 15), 0xFFFFFF);

        }
    }
}
