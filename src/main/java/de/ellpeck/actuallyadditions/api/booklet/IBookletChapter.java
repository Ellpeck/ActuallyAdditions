package de.ellpeck.actuallyadditions.api.booklet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IBookletChapter {

    IBookletPage[] getAllPages();

    @OnlyIn(Dist.CLIENT)
    String getLocalizedName();

    @OnlyIn(Dist.CLIENT)
    String getLocalizedNameWithFormatting();

    IBookletEntry getEntry();

    ItemStack getDisplayItemStack();

    String getIdentifier();

    int getPageIndex(IBookletPage page);

    int getSortingPriority();
}
