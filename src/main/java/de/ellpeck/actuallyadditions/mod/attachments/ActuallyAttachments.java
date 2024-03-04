package de.ellpeck.actuallyadditions.mod.attachments;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ActuallyAttachments {
	private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ActuallyAdditions.MODID);

	public static final Supplier<AttachmentType<CustomEnergyStorage>> ENERGY_STORAGE = ATTACHMENT_TYPES.register(
			"energy", () -> AttachmentType.serializable((holder) -> new CustomEnergyStorage(250000, 1000, 1000)).build());

	public static void init(IEventBus evt) {
		ATTACHMENT_TYPES.register(evt);
	}
}
