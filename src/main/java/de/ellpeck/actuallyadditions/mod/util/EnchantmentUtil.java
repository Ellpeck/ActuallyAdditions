package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class EnchantmentUtil {
	public static Holder<Enchantment> getEnchantmentHolder(RegistryAccess registryAccess, ResourceKey<Enchantment> resourceKey) {
		return registryAccess.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(resourceKey);
	}

	public static Holder<Enchantment> getEnchantmentHolder(Entity entity, ResourceKey<Enchantment> resourceKey) {
		return getEnchantmentHolder(entity.registryAccess(), resourceKey);
	}

	public static Holder<Enchantment> getEnchantmentHolder(Level level, ResourceKey<Enchantment> resourceKey) {
		return getEnchantmentHolder(level.registryAccess(), resourceKey);
	}
}
