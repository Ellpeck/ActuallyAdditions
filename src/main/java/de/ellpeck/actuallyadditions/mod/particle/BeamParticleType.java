package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class BeamParticleType extends ParticleType<BeamParticleData> {
    public BeamParticleType() {
        super(false, BeamParticleData.DESERIALIZER);
    }

    @Override
    public Codec<BeamParticleData> codec() {
        return BeamParticleData.CODEC;
    }
}
