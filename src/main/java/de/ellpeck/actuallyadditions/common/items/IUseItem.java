package de.ellpeck.actuallyadditions.common.items;

import net.minecraft.item.ItemStack;

public interface IUseItem {
    boolean canUse(ItemStack stack);
}
