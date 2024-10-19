package de.ellpeck.actuallyadditions.mod.network.packet;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ButtonToTilePacket(ResourceLocation dimension, BlockPos pos, int playerId,
                                 int buttonId) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, ButtonToTilePacket> CODEC = CustomPacketPayload.codec(
			ButtonToTilePacket::write,
			ButtonToTilePacket::new);
	public static final Type<ButtonToTilePacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "button_to_tile"));


	public ButtonToTilePacket(FriendlyByteBuf buf) {
		this(
				ResourceLocation.STREAM_CODEC.decode(buf),
				BlockPos.STREAM_CODEC.decode(buf),
				buf.readInt(),
				buf.readInt()
		);
	}

	public void write(FriendlyByteBuf buf) {
		ResourceLocation.STREAM_CODEC.encode(buf, dimension);
		BlockPos.STREAM_CODEC.encode(buf, pos);
		buf.writeInt(playerId);
		buf.writeInt(buttonId);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
