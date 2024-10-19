package de.ellpeck.actuallyadditions.mod.network.packet;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SpawnLaserPacket(double startX, double startY, double startZ,
                               double endX, double endY, double endZ,
                               int color, int maxAge, double rotationTime, float size,
                               float alpha) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, SpawnLaserPacket> CODEC = CustomPacketPayload.codec(
			SpawnLaserPacket::write,
			SpawnLaserPacket::new);
	public static final Type<SpawnLaserPacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "spawn_laser"));


	public SpawnLaserPacket(FriendlyByteBuf buf) {
		this(
				buf.readDouble(),
				buf.readDouble(),
				buf.readDouble(),
				buf.readDouble(),
				buf.readDouble(),
				buf.readDouble(),
				buf.readInt(),
				buf.readInt(),
				buf.readDouble(),
				buf.readFloat(),
				buf.readFloat()
		);
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeDouble(startX);
		buf.writeDouble(startY);
		buf.writeDouble(startZ);
		buf.writeDouble(endX);
		buf.writeDouble(endY);
		buf.writeDouble(endZ);
		buf.writeInt(color);
		buf.writeInt(maxAge);
		buf.writeDouble(rotationTime);
		buf.writeFloat(size);
		buf.writeFloat(alpha);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}


}
