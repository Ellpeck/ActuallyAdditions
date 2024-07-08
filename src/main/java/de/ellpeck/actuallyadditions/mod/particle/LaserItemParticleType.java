package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class LaserItemParticleType extends ParticleType<LaserItemParticleData> {
	public LaserItemParticleType() {
		super(false);
	}

	@NotNull
	@Override
	public MapCodec<LaserItemParticleData> codec() {
		return LaserItemParticleData.CODEC;
	}

	@NotNull
	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, LaserItemParticleData> streamCodec() {
		return LaserItemParticleData.STREAM_CODEC;
	}
}
