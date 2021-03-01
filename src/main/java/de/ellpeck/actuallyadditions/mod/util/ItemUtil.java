/*
 * This file ("ItemUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public final class ItemUtil {

    public static Item getItemFromName(String name) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
    }

    //    public static boolean contains(ItemStack[] array, ItemStack stack, boolean checkWildcard) {
    //        return getPlaceAt(array, stack, checkWildcard) != -1;
    //    }
    //
    //    public static int getPlaceAt(ItemStack[] array, ItemStack stack, boolean checkWildcard) {
    //        return getPlaceAt(Arrays.asList(array), stack, checkWildcard);
    //    }
    //
    //    public static int getPlaceAt(List<ItemStack> list, ItemStack stack, boolean checkWildcard) {
    //        if (list != null && list.size() > 0) {
    //            for (int i = 0; i < list.size(); i++) {
    //                if (!StackUtil.isValid(stack) && !StackUtil.isValid(list.get(i)) || areItemsEqual(stack, list.get(i), checkWildcard)) {
    //                    return i;
    //                }
    //            }
    //        }
    //        return -1;
    //    }

    @Deprecated
    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2, boolean checkWildcard) {
        return stack1.isItemEqual(stack2);
        //return StackUtil.isValid(stack1) && StackUtil.isValid(stack2) && (stack1.isItemEqual(stack2) || checkWildcard && stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == Util.WILDCARD || stack2.getItemDamage() == Util.WILDCARD));
    }

    //    /**
    //     * Returns true if list contains stack or if both contain null
    //     */
    //    public static boolean contains(List<ItemStack> list, ItemStack stack, boolean checkWildcard) {
    //        return !(list == null || list.isEmpty()) && getPlaceAt(list, stack, checkWildcard) != -1;
    //    }

    @Deprecated
    public static void addEnchantment(ItemStack stack, Enchantment e, int level) {
        if (!EnchantmentHelper.getEnchantments(stack).containsKey(e)) {
            stack.addEnchantment(e, level);
        }
    }

    // TODO: [port] ensure this still works :D
    public static void removeEnchantment(ItemStack stack, Enchantment e) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        enchantments.remove(e);

        EnchantmentHelper.setEnchantments(enchantments, stack);
    }

    public static boolean canBeStacked(ItemStack stack1, ItemStack stack2) {
        return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    public static boolean isEnabled(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("IsEnabled");
    }

    public static void changeEnabled(PlayerEntity player, Hand hand) {
        changeEnabled(player.getHeldItem(hand));
    }

    public static void changeEnabled(ItemStack stack) {
        boolean isEnabled = isEnabled(stack);
        stack.getOrCreateTag().putBoolean("IsEnabled", !isEnabled);
    }
}
