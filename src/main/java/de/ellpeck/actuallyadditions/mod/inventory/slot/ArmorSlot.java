package de.ellpeck.actuallyadditions.mod.inventory.slot;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;

/**
 * A copy of vanilla's ArmorSlot without the owner entity
 * Vanilla's ArmorSlot class is not public, so we have to copy it
 */
public class ArmorSlot extends Slot {
	private final EquipmentSlot slot;
	@Nullable
	private final ResourceLocation emptyIcon;

	public ArmorSlot(
			Container container, EquipmentSlot slot, int slotIndex, int x, int y, @Nullable ResourceLocation emptyIcon
	) {
		super(container, slotIndex, x, y);
		this.slot = slot;
		this.emptyIcon = emptyIcon;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return !stack.isEmpty() && stack.getItem() instanceof ArmorItem;
	}

	@Override
	public boolean mayPickup(Player player) {
		ItemStack itemstack = this.getItem();
		return !itemstack.isEmpty() && !player.isCreative() && EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)
				? false
				: super.mayPickup(player);
	}

	@Override
	public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
		return this.emptyIcon != null ? Pair.of(InventoryMenu.BLOCK_ATLAS, this.emptyIcon) : super.getNoItemIcon();
	}
}
