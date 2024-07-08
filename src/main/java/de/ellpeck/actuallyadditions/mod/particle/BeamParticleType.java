package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class BeamParticleType extends ParticleType<BeamParticleData> {

    public BeamParticleType() {
        super(false);
    }

    @NotNull
    @Override
    public MapCodec<BeamParticleData> codec() {
        return BeamParticleData.CODEC;
    }

    @NotNull
    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, BeamParticleData> streamCodec() {
        return BeamParticleData.STREAM_CODEC;
    }
}
