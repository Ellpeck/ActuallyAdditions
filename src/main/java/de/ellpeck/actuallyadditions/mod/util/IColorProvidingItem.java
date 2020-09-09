package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IColorProvidingItem {

    @OnlyIn(Dist.CLIENT)
    IItemColor getItemColor();

}
