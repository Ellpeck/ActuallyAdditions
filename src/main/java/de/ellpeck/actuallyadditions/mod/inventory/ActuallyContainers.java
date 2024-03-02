package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ActuallyContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ActuallyAdditions.MODID);

    public static final RegistryObject<MenuType<SackContainer>> BAG_CONTAINER = CONTAINERS.register("bag_container", () -> IForgeMenuType.create(SackContainer::fromNetwork));
    public static final RegistryObject<MenuType<ContainerBioReactor>> BIO_REACTOR_CONTAINER = CONTAINERS.register("bioreactor_container", () -> IForgeMenuType.create(ContainerBioReactor::fromNetwork));
    public static final RegistryObject<MenuType<ContainerBreaker>> BREAKER_CONTAINER = CONTAINERS.register("breaker_container", () -> IForgeMenuType.create(ContainerBreaker::fromNetwork));
    public static final RegistryObject<MenuType<ContainerCanolaPress>> CANOLA_PRESS_CONTAINER = CONTAINERS.register("canola_press_container", () -> IForgeMenuType.create(ContainerCanolaPress::fromNetwork));
    public static final RegistryObject<MenuType<ContainerCoalGenerator>> COAL_GENERATOR_CONTAINER = CONTAINERS.register("coal_generator_container", () -> IForgeMenuType.create(ContainerCoalGenerator::fromNetwork));
    public static final RegistryObject<MenuType<ContainerCoffeeMachine>> COFFEE_MACHINE_CONTAINER = CONTAINERS.register("coffee_machine_container", () -> IForgeMenuType.create(ContainerCoffeeMachine::fromNetwork));
    public static final RegistryObject<MenuType<ContainerDirectionalBreaker>> DIRECTIONAL_BREAKER_CONTAINER = CONTAINERS.register("directional_breaker_container", () -> IForgeMenuType.create(ContainerDirectionalBreaker::fromNetwork));
    public static final RegistryObject<MenuType<ContainerDrill>> DRILL_CONTAINER = CONTAINERS.register("drill_container", () -> IForgeMenuType.create(ContainerDrill::fromNetwork));
    public static final RegistryObject<MenuType<ContainerDropper>> DROPPER_CONTAINER = CONTAINERS.register("dropper_container", () -> IForgeMenuType.create(ContainerDropper::fromNetwork));
    public static final RegistryObject<MenuType<ContainerEnervator>> ENERVATOR_CONTAINER = CONTAINERS.register("enervator_container", () -> IForgeMenuType.create(ContainerEnervator::fromNetwork));
    public static final RegistryObject<MenuType<ContainerEnergizer>> ENERGIZER_CONTAINER = CONTAINERS.register("energizer_container", () -> IForgeMenuType.create(ContainerEnergizer::fromNetwork));

    public static final RegistryObject<MenuType<ContainerFarmer>> FARMER_CONTAINER = CONTAINERS.register("farmer_container", () -> IForgeMenuType.create(ContainerFarmer::fromNetwork));
    public static final RegistryObject<MenuType<ContainerFeeder>> FEEDER_CONTAINER = CONTAINERS.register("feeder_container", () -> IForgeMenuType.create(ContainerFeeder::fromNetwork));
    public static final RegistryObject<MenuType<ContainerFermentingBarrel>> FERMENTING_BARREL_CONTAINER = CONTAINERS.register("fermenting_barrel_container", () -> IForgeMenuType.create(ContainerFermentingBarrel::fromNetwork));
    public static final RegistryObject<MenuType<ContainerFilter>> FILTER_CONTAINER = CONTAINERS.register("filter_container", () -> IForgeMenuType.create(ContainerFilter::fromNetwork));
    public static final RegistryObject<MenuType<ContainerFireworkBox>> FIREWORK_BOX_CONTAINER = CONTAINERS.register("firework_box_container", () -> IForgeMenuType.create(ContainerFireworkBox::fromNetwork));
    public static final RegistryObject<MenuType<ContainerFluidCollector>> FLUID_COLLECTOR_CONTAINER = CONTAINERS.register("fluid_collector_container", () -> IForgeMenuType.create(ContainerFluidCollector::fromNetwork));
    public static final RegistryObject<MenuType<ContainerFurnaceDouble>> FURNACE_DOUBLE_CONTAINER = CONTAINERS.register("furnace_double_container", () -> IForgeMenuType.create(ContainerFurnaceDouble::fromNetwork));
    public static final RegistryObject<MenuType<CrusherContainer>> GRINDER_CONTAINER = CONTAINERS.register("grinder_container", () -> IForgeMenuType.create(CrusherContainer::fromNetwork));
    public static final RegistryObject<MenuType<ContainerLaserRelayItemWhitelist>> LASER_RELAY_ITEM_WHITELIST_CONTAINER = CONTAINERS.register("laser_relay_item_whitelist_container", () -> IForgeMenuType.create(ContainerLaserRelayItemWhitelist::fromNetwork));
    public static final RegistryObject<MenuType<ContainerMiner>> MINER_CONTAINER = CONTAINERS.register("miner_container", () -> IForgeMenuType.create(ContainerMiner::fromNetwork));
    public static final RegistryObject<MenuType<ContainerOilGenerator>> OIL_GENERATOR_CONTAINER = CONTAINERS.register("oil_generator_container", () -> IForgeMenuType.create(ContainerOilGenerator::fromNetwork));
    public static final RegistryObject<MenuType<ContainerPhantomPlacer>> PHANTOM_PLACER_CONTAINER = CONTAINERS.register("phantom_placer_container", () -> IForgeMenuType.create(ContainerPhantomPlacer::fromNetwork));
    public static final RegistryObject<MenuType<ContainerRangedCollector>> RANGED_COLLECTOR_CONTAINER = CONTAINERS.register("ranged_collector_container", () -> IForgeMenuType.create(ContainerRangedCollector::fromNetwork));
    public static final RegistryObject<MenuType<ContainerXPSolidifier>> XPSOLIDIFIER_CONTAINER = CONTAINERS.register("xpsolidifier_container", () -> IForgeMenuType.create(ContainerXPSolidifier::fromNetwork));

}

