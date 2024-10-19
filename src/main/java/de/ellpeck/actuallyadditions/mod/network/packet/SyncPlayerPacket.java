package de.ellpeck.actuallyadditions.mod.network.packet;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncPlayerPacket(CompoundTag tag) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SyncPlayerPacket> CODEC = CustomPacketPayload.codec(
			SyncPlayerPacket::write,
			SyncPlayerPacket::new);
	public static final Type<SyncPlayerPacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "sync_player"));


	public SyncPlayerPacket(FriendlyByteBuf buf) {
		this(buf.readNbt());
	}
	public void write(FriendlyByteBuf buf) {
		buf.writeNbt(tag);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
