package de.ellpeck.actuallyadditions.mod.attachments;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ActuallyAttachments {
	private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ActuallyAdditions.MODID);

	public static final Supplier<AttachmentType<CustomEnergyStorage>> ENERGY_STORAGE = ATTACHMENT_TYPES.register(
			"energy", ActuallyAttachments.itemEnergyStorageAttachment());

	/*
	 * This is a supplier for an attachment type that can be used to attach an energy storage to an item.
	 * Implementation is based on EnderIO's https://github.com/Team-EnderIO/EnderIO/blob/e1f022df745131ed5fea718bd860880a5785d4c7/src/core/java/com/enderio/core/common/attachment/AttachmentUtil.java#L47-L60
	 */
	public static Supplier<AttachmentType<CustomEnergyStorage>> itemEnergyStorageAttachment() {
		return () -> AttachmentType.serializable(holder -> {
			if (holder instanceof ItemStack itemStack) {
				int capacity = 1000;
				int maxTransfer = 1000;
				if (itemStack.getItem() instanceof ItemEnergy itemEnergy) {
					capacity = itemEnergy.maxPower;
					maxTransfer = itemEnergy.transfer;
				}


				return new CustomEnergyStorage(capacity, maxTransfer, maxTransfer);
			} else {
				throw new IllegalStateException("Cannot attach energy handler item to a non-item.");
			}
		}).build();
	}

	public static void init(IEventBus evt) {
		ATTACHMENT_TYPES.register(evt);
	}
}
