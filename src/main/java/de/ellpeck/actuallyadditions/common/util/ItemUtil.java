package de.ellpeck.actuallyadditions.common.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class ItemUtil {

    public static Item getItemFromName(String name) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
    }

//    @Deprecated // canitzp: should be removed
//    public static void registerBlock(Block block, BlockAtomicReconstructor.BlockItem itemBlock, String name, boolean addTab) {
//        block.setTranslationKey(ActuallyAdditions.MODID + "." + name);
//
//        block.setRegistryName(ActuallyAdditions.MODID, name);
//        RegistryHandler.BLOCKS_TO_REGISTER.add(block);
//
//        itemBlock.setRegistryName(block.getRegistryName());
//        RegistryHandler.ITEMS_TO_REGISTER.add(itemBlock);
//
//        block.setCreativeTab(addTab ? CreativeTab.INSTANCE : null);
//
//        IMCHandler.doBlockIMC(block);
//
//        if (block instanceof IColorProvidingBlock) {
//            ActuallyAdditions.PROXY.addColoredBlock(block);
//        }
//    }

//    @Deprecated // canitzp: should be removed
//    public static void registerItem(Item item, String name, boolean addTab) {
//        item.setTranslationKey(ActuallyAdditions.MODID + "." + name);
//
//        item.setRegistryName(ActuallyAdditions.MODID, name);
//        RegistryHandler.ITEMS_TO_REGISTER.add(item);
//
//        item.setCreativeTab(addTab ? CreativeTab.INSTANCE : null);
//
//        IMCHandler.doItemIMC(item);
//
//        if (item instanceof IColorProvidingItem) {
//            ActuallyAdditions.PROXY.addColoredItem(item);
//        }
//    }

    public static boolean contains(ItemStack[] array, ItemStack stack, boolean checkWildcard) {
        return getPlaceAt(array, stack, checkWildcard) != -1;
    }

    public static int getPlaceAt(ItemStack[] array, ItemStack stack, boolean checkWildcard) {
        return getPlaceAt(Arrays.asList(array), stack, checkWildcard);
    }

    public static int getPlaceAt(List<ItemStack> list, ItemStack stack, boolean checkWildcard) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!StackUtil.isValid(stack) && !StackUtil.isValid(list.get(i)) || areItemsEqual(stack, list.get(i), checkWildcard)) { return i; }
            }
        }
        return -1;
    }

    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2, boolean checkWildcard) {
        // todo: fix wildcard
        return StackUtil.isValid(stack1) && StackUtil.isValid(stack2) && (stack1.isItemEqual(stack2) || checkWildcard && stack1.getItem() == stack2.getItem() && (stack1.getDamage() == Util.WILDCARD || stack2.getDamage() == Util.WILDCARD));
    }

    /**
     * Returns true if list contains stack or if both contain null
     */
    public static boolean contains(List<ItemStack> list, ItemStack stack, boolean checkWildcard) {
        return !(list == null || list.isEmpty()) && getPlaceAt(list, stack, checkWildcard) != -1;
    }

    public static void addEnchantment(ItemStack stack, Enchantment e, int level) {
        if (!hasEnchantment(stack, e)) {
            stack.addEnchantment(e, level);
        }
    }

    public static boolean hasEnchantment(ItemStack stack, Enchantment e) {
        return EnchantmentHelper.getEnchantments(stack).containsKey(e);
    }

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
