package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ActuallyContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ActuallyAdditions.MODID);

    public static final RegistryObject<ContainerType<ContainerBag>> BAG_CONTAINER
        = CONTAINERS.register("bag_container", () -> IForgeContainerType.create(ContainerBag::fromNetwork));

    public static final RegistryObject<ContainerType<ContainerBioReactor>> BIO_REACTOR_CONTAINER
        = CONTAINERS.register("bioreactor_container", () -> IForgeContainerType.create(ContainerBioReactor::fromNetwork));

}
