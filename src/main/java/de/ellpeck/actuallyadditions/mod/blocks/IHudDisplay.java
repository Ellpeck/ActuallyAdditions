package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public interface IHudDisplay {

    @OnlyIn(Dist.CLIENT)
    void displayHud(Minecraft minecraft, PlayerEntity player, ItemStack stack, @Nullable RayTraceResult posHit, int scaledWidth, int scaledHeight);

}
