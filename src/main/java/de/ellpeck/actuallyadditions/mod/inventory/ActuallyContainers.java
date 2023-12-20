package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ActuallyContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ActuallyAdditions.MODID);

    public static final RegistryObject<ContainerType<SackContainer>> BAG_CONTAINER = CONTAINERS.register("bag_container", () -> IForgeContainerType.create(SackContainer::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerBioReactor>> BIO_REACTOR_CONTAINER = CONTAINERS.register("bioreactor_container", () -> IForgeContainerType.create(ContainerBioReactor::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerBreaker>> BREAKER_CONTAINER = CONTAINERS.register("breaker_container", () -> IForgeContainerType.create(ContainerBreaker::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerCanolaPress>> CANOLA_PRESS_CONTAINER = CONTAINERS.register("canola_press_container", () -> IForgeContainerType.create(ContainerCanolaPress::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerCoalGenerator>> COAL_GENERATOR_CONTAINER = CONTAINERS.register("coal_generator_container", () -> IForgeContainerType.create(ContainerCoalGenerator::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerCoffeeMachine>> COFFEE_MACHINE_CONTAINER = CONTAINERS.register("coffee_machine_container", () -> IForgeContainerType.create(ContainerCoffeeMachine::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerDirectionalBreaker>> DIRECTIONAL_BREAKER_CONTAINER = CONTAINERS.register("directional_breaker_container", () -> IForgeContainerType.create(ContainerDirectionalBreaker::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerDrill>> DRILL_CONTAINER = CONTAINERS.register("drill_container", () -> IForgeContainerType.create(ContainerDrill::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerDropper>> DROPPER_CONTAINER = CONTAINERS.register("dropper_container", () -> IForgeContainerType.create(ContainerDropper::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerEnervator>> ENERVATOR_CONTAINER = CONTAINERS.register("enervator_container", () -> IForgeContainerType.create(ContainerEnervator::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerEnergizer>> ENERGIZER_CONTAINER = CONTAINERS.register("energizer_container", () -> IForgeContainerType.create(ContainerEnergizer::fromNetwork));

    public static final RegistryObject<ContainerType<ContainerFarmer>> FARMER_CONTAINER = CONTAINERS.register("farmer_container", () -> IForgeContainerType.create(ContainerFarmer::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerFeeder>> FEEDER_CONTAINER = CONTAINERS.register("feeder_container", () -> IForgeContainerType.create(ContainerFeeder::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerFermentingBarrel>> FERMENTING_BARREL_CONTAINER = CONTAINERS.register("fermenting_barrel_container", () -> IForgeContainerType.create(ContainerFermentingBarrel::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerFilter>> FILTER_CONTAINER = CONTAINERS.register("filter_container", () -> IForgeContainerType.create(ContainerFilter::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerFireworkBox>> FIREWORK_BOX_CONTAINER = CONTAINERS.register("firework_box_container", () -> IForgeContainerType.create(ContainerFireworkBox::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerFluidCollector>> FLUID_COLLECTOR_CONTAINER = CONTAINERS.register("fluid_collector_container", () -> IForgeContainerType.create(ContainerFluidCollector::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerFurnaceDouble>> FURNACE_DOUBLE_CONTAINER = CONTAINERS.register("furnace_double_container", () -> IForgeContainerType.create(ContainerFurnaceDouble::fromNetwork));
    public static final RegistryObject<ContainerType<CrusherContainer>> GRINDER_CONTAINER = CONTAINERS.register("grinder_container", () -> IForgeContainerType.create(CrusherContainer::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerLaserRelayItemWhitelist>> LASER_RELAY_ITEM_WHITELIST_CONTAINER = CONTAINERS.register("laser_relay_item_whitelist_container", () -> IForgeContainerType.create(ContainerLaserRelayItemWhitelist::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerMiner>> MINER_CONTAINER = CONTAINERS.register("miner_container", () -> IForgeContainerType.create(ContainerMiner::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerOilGenerator>> OIL_GENERATOR_CONTAINER = CONTAINERS.register("oil_generator_container", () -> IForgeContainerType.create(ContainerOilGenerator::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerPhantomPlacer>> PHANTOM_PLACER_CONTAINER = CONTAINERS.register("phantom_placer_container", () -> IForgeContainerType.create(ContainerPhantomPlacer::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerRangedCollector>> RANGED_COLLECTOR_CONTAINER = CONTAINERS.register("ranged_collector_container", () -> IForgeContainerType.create(ContainerRangedCollector::fromNetwork));
    public static final RegistryObject<ContainerType<ContainerXPSolidifier>> XPSOLIDIFIER_CONTAINER = CONTAINERS.register("xpsolidifier_container", () -> IForgeContainerType.create(ContainerXPSolidifier::fromNetwork));

}

