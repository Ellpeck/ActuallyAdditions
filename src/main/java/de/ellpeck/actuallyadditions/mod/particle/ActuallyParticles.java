package de.ellpeck.actuallyadditions.mod.particle;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ActuallyParticles {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, ActuallyAdditions.MODID);


    public static final Supplier<ParticleType<LaserItemParticleData>> LASER_ITEM = PARTICLE_TYPES.register("laser_item", LaserItemParticleType::new);
    public static final Supplier<ParticleType<BeamParticleData>> BEAM = PARTICLE_TYPES.register("beam", BeamParticleType::new);

    public static void init(IEventBus evt) {
        PARTICLE_TYPES.register(evt);
    }
}
