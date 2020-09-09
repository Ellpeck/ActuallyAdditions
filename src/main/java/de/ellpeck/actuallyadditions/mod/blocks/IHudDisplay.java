package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IHudDisplay {

    @OnlyIn(Dist.CLIENT)
    void displayHud(Minecraft minecraft, PlayerEntity player, ItemStack stack, BlockRayTraceResult posHit, MainWindow window);

}
