package de.ellpeck.actuallyadditions.common.container;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ActuallyContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ActuallyAdditions.MOD_ID);

    public static final RegistryObject<ContainerType<DrillContainer>> DRILL_CONTAINER
            = CONTAINERS.register("drill_container", () -> IForgeContainerType.create(DrillContainer::fromNetwork));

    public static final RegistryObject<ContainerType<FeederContainer>> FEEDER_CONTAINER
            = CONTAINERS.register("feeder_container", () -> IForgeContainerType.create(FeederContainer::fromNetwork));

    public static final RegistryObject<ContainerType<VoidSackContainer>> VOID_SACK_CONTAINER
        = CONTAINERS.register("void_sack_container", () -> IForgeContainerType.create(VoidSackContainer::fromNetwork));
}
