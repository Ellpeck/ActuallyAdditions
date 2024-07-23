package de.ellpeck.actuallyadditions.mod.components;

import com.mojang.serialization.Codec;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;
import java.util.function.Supplier;

public class ActuallyComponents {
	private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, ActuallyAdditions.MODID);

	public static final Supplier<DataComponentType<Integer>> ENERGY_STORAGE = DATA_COMPONENT_TYPES.register("energy", () ->
			DataComponentType.<Integer>builder()
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.INT)
					.build());


	public static final Supplier<DataComponentType<ResourceLocation>> ITEM_TAG = DATA_COMPONENT_TYPES.register("item_tag", () ->
			DataComponentType.<ResourceLocation>builder()
					.persistent(ResourceLocation.CODEC)
					.networkSynchronized(ResourceLocation.STREAM_CODEC)
					.build());

	public static final Supplier<DataComponentType<Boolean>> AUTO_INSERT = DATA_COMPONENT_TYPES.register("auto_insert", () ->
			DataComponentType.<Boolean>builder()
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build());

	public static final Supplier<DataComponentType<Boolean>> PULSE_MODE = DATA_COMPONENT_TYPES.register("pulse_mode", () ->
			DataComponentType.<Boolean>builder()
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build());

	public static final Supplier<DataComponentType<Boolean>> WHITELIST = DATA_COMPONENT_TYPES.register("whitelist", () ->
			DataComponentType.<Boolean>builder()
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build());

	public static final Supplier<DataComponentType<Boolean>> MOD = DATA_COMPONENT_TYPES.register("mod", () ->
			DataComponentType.<Boolean>builder()
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build());

	public static final Supplier<DataComponentType<Boolean>> DAMAGE = DATA_COMPONENT_TYPES.register("damage", () ->
			DataComponentType.<Boolean>builder()
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build());

	public static final Supplier<DataComponentType<Boolean>> COMPONENTS = DATA_COMPONENT_TYPES.register("components", () ->
			DataComponentType.<Boolean>builder()
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build());

	public static final Supplier<DataComponentType<Boolean>> ENABLED = DATA_COMPONENT_TYPES.register("enabled", () ->
			DataComponentType.<Boolean>builder()
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build());

	public static final Supplier<DataComponentType<ItemContainerContents>> CONTENTS = DATA_COMPONENT_TYPES.register("contents", () ->
			DataComponentType.<ItemContainerContents>builder()
					.persistent(ItemContainerContents.CODEC)
					.networkSynchronized(ItemContainerContents.STREAM_CODEC)
					.build());

	public static final Supplier<DataComponentType<UUID>> UUID = DATA_COMPONENT_TYPES.register("uuid", () ->
			DataComponentType.<UUID>builder()
					.persistent(UUIDUtil.CODEC)
					.networkSynchronized(UUIDUtil.STREAM_CODEC)
					.build());
	public static final Supplier<DataComponentType<String>> NAME = DATA_COMPONENT_TYPES.register("name", () ->
			DataComponentType.<String>builder()
					.persistent(Codec.STRING)
					.networkSynchronized(ByteBufCodecs.STRING_UTF8)
					.build());

	public static final Supplier<DataComponentType<ResourceKey<Level>>> LEVEL = DATA_COMPONENT_TYPES.register("level", () ->
			DataComponentType.<ResourceKey<Level>>builder()
					.persistent(ResourceKey.codec(Registries.DIMENSION))
					.networkSynchronized(ResourceKey.streamCodec(Registries.DIMENSION))
					.build());
	public static final Supplier<DataComponentType<BlockPos>> POSITION = DATA_COMPONENT_TYPES.register("position", () ->
			DataComponentType.<BlockPos>builder()
					.persistent(BlockPos.CODEC)
					.networkSynchronized(BlockPos.STREAM_CODEC)
					.build());
	public static final Supplier<DataComponentType<LastXY>> LAST_XY = DATA_COMPONENT_TYPES.register("last_xy", () ->
			DataComponentType.<LastXY>builder()
					.persistent(LastXY.CODEC)
					.networkSynchronized(LastXY.STREAM_CODEC)
					.build());

	public static final Supplier<DataComponentType<Integer>> SLOT = DATA_COMPONENT_TYPES.register("slot", () ->
			DataComponentType.<Integer>builder()
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.INT)
					.build());


	/*
	 * This is a supplier for an attachment type that can be used to attach an energy storage to an item.
	 * Implementation is based on EnderIO's https://github.com/Team-EnderIO/EnderIO/blob/e1f022df745131ed5fea718bd860880a5785d4c7/src/core/java/com/enderio/core/common/attachment/AttachmentUtil.java#L47-L60
	 */
//	public static Supplier<AttachmentType<CustomEnergyStorage>> itemEnergyStorageAttachment() {
//		return () -> AttachmentType.serializable(holder -> {
//			if (holder instanceof ItemStack itemStack) {
//				int capacity = 1000;
//				int maxTransfer = 1000;
//				if (itemStack.getItem() instanceof ItemEnergy itemEnergy) {
//					capacity = itemEnergy.maxPower;
//					maxTransfer = itemEnergy.transfer;
//				}
//
//
//				return new CustomEnergyStorage(capacity, maxTransfer, maxTransfer);
//			} else {
//				throw new IllegalStateException("Cannot attach energy handler item to a non-item.");
//			}
//		}).build();
//	}

	public static void init(IEventBus evt) {
		DATA_COMPONENT_TYPES.register(evt);
	}
}
