package de.ellpeck.actuallyadditions.mod.network.packet;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record TileUpdatePacket(BlockPos pos, CompoundTag data) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, TileUpdatePacket> CODEC = CustomPacketPayload.codec(
			TileUpdatePacket::write,
			TileUpdatePacket::new);
	public static final Type<TileUpdatePacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "tile_update"));


	public TileUpdatePacket(FriendlyByteBuf buf) {
		this(
				buf.readBlockPos(),
				buf.readNbt()
		);
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
		buf.writeNbt(data);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}


}
