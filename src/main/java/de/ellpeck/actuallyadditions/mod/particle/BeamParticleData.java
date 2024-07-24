package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import javax.annotation.Nonnull;

public record BeamParticleData(double endX, double endY, double endZ, int color, int maxAge, double rotationTime,
                               float size) implements ParticleOptions {
	public static final MapCodec<BeamParticleData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
					Codec.DOUBLE.fieldOf("endX").forGetter(d -> d.endX),
					Codec.DOUBLE.fieldOf("endY").forGetter(d -> d.endY),
					Codec.DOUBLE.fieldOf("endZ").forGetter(d -> d.endZ),
					Codec.INT.fieldOf("color").forGetter(d -> d.color),
					Codec.INT.fieldOf("maxAge").forGetter(d -> d.maxAge),
					Codec.DOUBLE.fieldOf("rotationTime").forGetter(d -> d.rotationTime),
					Codec.FLOAT.fieldOf("size").forGetter(d -> d.size)
			)
			.apply(instance, BeamParticleData::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, BeamParticleData> STREAM_CODEC = StreamCodec.of(
			BeamParticleData::toNetwork, BeamParticleData::fromNetwork
	);

	public static BeamParticleData fromNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer) {
		return new BeamParticleData(pBuffer.readDouble(), pBuffer.readDouble(), pBuffer.readDouble(), pBuffer.readInt(),
				pBuffer.readInt(), pBuffer.readDouble(), pBuffer.readFloat());
	}

	public static void toNetwork(@Nonnull RegistryFriendlyByteBuf pBuffer, BeamParticleData pRecipe) {
		pBuffer.writeDouble(pRecipe.endX());
		pBuffer.writeDouble(pRecipe.endY());
		pBuffer.writeDouble(pRecipe.endZ());
		pBuffer.writeInt(pRecipe.color());
		pBuffer.writeInt(pRecipe.maxAge());
		pBuffer.writeDouble(pRecipe.rotationTime());
		pBuffer.writeFloat(pRecipe.size());
	}

	@Override
	public ParticleType<?> getType() {
		return ActuallyParticles.BEAM.get();
	}
}