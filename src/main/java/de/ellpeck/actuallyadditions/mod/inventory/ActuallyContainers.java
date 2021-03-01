package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ActuallyContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ActuallyAdditions.MODID);

    public static final RegistryObject<ContainerType<ContainerBag>> BAG_CONTAINER = CONTAINERS.register("bag_container", () -> IForgeContainerType.create(ContainerBag::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerBioReactor>> BIO_REACTOR_CONTAINER = CONTAINERS.register("bioreactor_container", () -> IForgeContainerType.create(ContainerBioReactor::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerBreaker>> BREAKER_CONTAINER = CONTAINERS.register("breaker_container", () -> IForgeContainerType.create(ContainerBreaker::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerCanolaPress>> CANOLA_PRESS_CONTAINER = CONTAINERS.register("canola_press_container", () -> IForgeContainerType.create(ContainerCanolaPress::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerCoalGenerator>> COAL_GENERATOR_CONTAINER = CONTAINERS.register("coal_generator_container", () -> IForgeContainerType.create(ContainerCoalGenerator::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerCoffeeMachine>> COFFEE_MACHINE_CONTAINER = CONTAINERS.register("coffee_machine_container", () -> IForgeContainerType.create(ContainerCoffeeMachine::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerDirectionalBreaker>> DIRECTIONAL_BREAKER_CONTAINER = CONTAINERS.register("directional_breaker_container", () -> IForgeContainerType.create(ContainerDirectionalBreaker::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerDropper>> DROPPER_CONTAINER = CONTAINERS.register("dropper_container", () -> IForgeContainerType.create(ContainerDropper::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerEnervator>> ENERVATOR_CONTAINER = CONTAINERS.register("enervator_container", () -> IForgeContainerType.create(ContainerEnervator::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerEnergizer>> ENERGIZER_CONTAINER = CONTAINERS.register("energizer_container", () -> IForgeContainerType.create(ContainerEnergizer::fromNetwork));
}

