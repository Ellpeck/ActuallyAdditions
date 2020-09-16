package de.ellpeck.actuallyadditions.common.items.lens;

import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.lens.LensConversion;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public final class Lenses {
    
    public static final DeferredRegister<Lens> LENSES = DeferredRegister.create(ActuallyAdditions.LENS_REGISTRY, ActuallyAdditions.MODID);
    
    public static final RegistryObject<LensConversion> LENS_CONVERSION = LENSES.register("conversion", LensConversion::new);
    public static final RegistryObject<LensDetonation> LENS_DETONATION = LENSES.register("detonation", LensDetonation::new);
    public static final RegistryObject<LensDeath> LENS_DEATH = LENSES.register("death", LensDeath::new);
    public static final RegistryObject<LensKiller> LENS_KILLER = LENSES.register("killer", LensKiller::new);
    public static final RegistryObject<LensColor> LENS_COLOR = LENSES.register("color", LensColor::new);
    public static final RegistryObject<LensDisruption> LENS_DISRUPTION = LENSES.register("disruption", LensDisruption::new);
    public static final RegistryObject<LensDisenchanting> LENS_DISENCHANTING = LENSES.register("disenchanting", LensDisenchanting::new);
    public static final RegistryObject<LensMining> LENS_MINING = LENSES.register("mining", LensMining::new);
    
    
}
