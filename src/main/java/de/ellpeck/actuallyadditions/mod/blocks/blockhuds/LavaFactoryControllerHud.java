package de.ellpeck.actuallyadditions.mod.blocks.blockhuds;

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLavaFactoryController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LavaFactoryControllerHud implements IBlockHud {
    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
        if (!(rayCast instanceof BlockHitResult)) {
            return;
        }

        TileEntityLavaFactoryController factory = (TileEntityLavaFactoryController) minecraft.level.getBlockEntity(((BlockHitResult) rayCast).getBlockPos());
        if (factory != null) {
            int state = factory.isMultiblock();
            if (state == TileEntityLavaFactoryController.NOT_MULTI) {
                guiGraphics.drawWordWrap(minecraft.font, Component.translatable("tooltip.actuallyadditions.factory.notPart.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 5, 200, 0xFFFFFF);
            } else if (state == TileEntityLavaFactoryController.HAS_AIR || state == TileEntityLavaFactoryController.HAS_LAVA) {
                guiGraphics.drawWordWrap(minecraft.font, Component.translatable("tooltip.actuallyadditions.factory.working.desc"), resolution.getGuiScaledWidth() / 2 + 5, resolution.getGuiScaledHeight() / 2 + 5, 200, 0xFFFFFF);
            }
        }
    }
}
