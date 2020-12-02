package de.ellpeck.actuallyadditions.common.items;

import net.minecraft.item.ItemStack;

public interface IActivates {
    String NBT_TAG = "is-enabled";

    default boolean isEnabled(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(NBT_TAG);
    }

    default void toggle(ItemStack stack) {
        stack.getOrCreateTag().putBoolean(NBT_TAG, !isEnabled(stack));
    }

    default void manualToggle(ItemStack stack, boolean enabled) {
        stack.getOrCreateTag().putBoolean(NBT_TAG, enabled);
    }
}
