package de.ellpeck.actuallyadditions.mod.network.packet;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StringToTilePacket(ResourceLocation dimension, BlockPos pos, int playerId,
                                 String text, int textId) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, StringToTilePacket> CODEC = CustomPacketPayload.codec(
			StringToTilePacket::write,
			StringToTilePacket::new);
	public static final Type<StringToTilePacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "string_to_tile"));


	public StringToTilePacket(FriendlyByteBuf buf) {
		this(
				ResourceLocation.STREAM_CODEC.decode(buf),
				BlockPos.STREAM_CODEC.decode(buf),
				buf.readInt(),
				buf.readUtf(),
				buf.readInt()
		);
	}

	public void write(FriendlyByteBuf buf) {
		ResourceLocation.STREAM_CODEC.encode(buf, dimension);
		BlockPos.STREAM_CODEC.encode(buf, pos);
		buf.writeInt(playerId);
		buf.writeUtf(text);
		buf.writeInt(textId);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
