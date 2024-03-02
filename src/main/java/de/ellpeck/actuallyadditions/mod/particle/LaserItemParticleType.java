package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class LaserItemParticleType extends ParticleType<LaserItemParticleData> {
    public LaserItemParticleType() {
        super(false, LaserItemParticleData.DESERIALIZER);
    }

    @Override
    public Codec<LaserItemParticleData> codec() {
        return LaserItemParticleData.CODEC;
    }
}
