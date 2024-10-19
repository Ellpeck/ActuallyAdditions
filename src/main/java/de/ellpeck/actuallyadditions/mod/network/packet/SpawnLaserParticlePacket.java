package de.ellpeck.actuallyadditions.mod.network.packet;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record SpawnLaserParticlePacket(ItemStack stack, BlockPos in, BlockPos out) implements CustomPacketPayload {
	public static final StreamCodec<RegistryFriendlyByteBuf, SpawnLaserParticlePacket> CODEC = CustomPacketPayload.codec(
			SpawnLaserParticlePacket::write,
			SpawnLaserParticlePacket::new);
	public static final Type<SpawnLaserParticlePacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(ActuallyAdditions.MODID, "spawn_laser_particle"));


	public SpawnLaserParticlePacket(RegistryFriendlyByteBuf buf) {
		this(
				ItemStack.STREAM_CODEC.decode(buf),
				BlockPos.STREAM_CODEC.decode(buf),
				BlockPos.STREAM_CODEC.decode(buf)
		);
	}

	public void write(RegistryFriendlyByteBuf buf) {
		ItemStack.STREAM_CODEC.encode(buf, stack);
		BlockPos.STREAM_CODEC.encode(buf, in);
		BlockPos.STREAM_CODEC.encode(buf, out);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}


}
