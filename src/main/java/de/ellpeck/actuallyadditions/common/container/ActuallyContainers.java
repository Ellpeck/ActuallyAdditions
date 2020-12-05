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
}
