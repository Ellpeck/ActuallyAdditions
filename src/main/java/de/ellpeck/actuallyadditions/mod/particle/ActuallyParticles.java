package de.ellpeck.actuallyadditions.mod.particle;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ActuallyParticles {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ActuallyAdditions.MODID);


    public static final RegistryObject<ParticleType<LaserItemParticleData>> LASER_ITEM = PARTICLE_TYPES.register("laser_item", LaserItemParticleType::new);
    public static final RegistryObject<ParticleType<BeamParticleData>> BEAM = PARTICLE_TYPES.register("beam", BeamParticleType::new);

    public static void init(IEventBus evt) {
        PARTICLE_TYPES.register(evt);
    }
}
