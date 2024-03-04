package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ActuallyContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, ActuallyAdditions.MODID);

    public static final Supplier<MenuType<SackContainer>> BAG_CONTAINER = CONTAINERS.register("bag_container", () -> IMenuTypeExtension.create(SackContainer::fromNetwork));
    public static final Supplier<MenuType<ContainerBioReactor>> BIO_REACTOR_CONTAINER = CONTAINERS.register("bioreactor_container", () -> IMenuTypeExtension.create(ContainerBioReactor::fromNetwork));
    public static final Supplier<MenuType<ContainerBreaker>> BREAKER_CONTAINER = CONTAINERS.register("breaker_container", () -> IMenuTypeExtension.create(ContainerBreaker::fromNetwork));
    public static final Supplier<MenuType<ContainerCanolaPress>> CANOLA_PRESS_CONTAINER = CONTAINERS.register("canola_press_container", () -> IMenuTypeExtension.create(ContainerCanolaPress::fromNetwork));
    public static final Supplier<MenuType<ContainerCoalGenerator>> COAL_GENERATOR_CONTAINER = CONTAINERS.register("coal_generator_container", () -> IMenuTypeExtension.create(ContainerCoalGenerator::fromNetwork));
    public static final Supplier<MenuType<ContainerCoffeeMachine>> COFFEE_MACHINE_CONTAINER = CONTAINERS.register("coffee_machine_container", () -> IMenuTypeExtension.create(ContainerCoffeeMachine::fromNetwork));
    public static final Supplier<MenuType<ContainerDirectionalBreaker>> DIRECTIONAL_BREAKER_CONTAINER = CONTAINERS.register("directional_breaker_container", () -> IMenuTypeExtension.create(ContainerDirectionalBreaker::fromNetwork));
    public static final Supplier<MenuType<ContainerDrill>> DRILL_CONTAINER = CONTAINERS.register("drill_container", () -> IMenuTypeExtension.create(ContainerDrill::fromNetwork));
    public static final Supplier<MenuType<ContainerDropper>> DROPPER_CONTAINER = CONTAINERS.register("dropper_container", () -> IMenuTypeExtension.create(ContainerDropper::fromNetwork));
    public static final Supplier<MenuType<ContainerEnervator>> ENERVATOR_CONTAINER = CONTAINERS.register("enervator_container", () -> IMenuTypeExtension.create(ContainerEnervator::fromNetwork));
    public static final Supplier<MenuType<ContainerEnergizer>> ENERGIZER_CONTAINER = CONTAINERS.register("energizer_container", () -> IMenuTypeExtension.create(ContainerEnergizer::fromNetwork));

    public static final Supplier<MenuType<ContainerFarmer>> FARMER_CONTAINER = CONTAINERS.register("farmer_container", () -> IMenuTypeExtension.create(ContainerFarmer::fromNetwork));
    public static final Supplier<MenuType<ContainerFeeder>> FEEDER_CONTAINER = CONTAINERS.register("feeder_container", () -> IMenuTypeExtension.create(ContainerFeeder::fromNetwork));
    public static final Supplier<MenuType<ContainerFermentingBarrel>> FERMENTING_BARREL_CONTAINER = CONTAINERS.register("fermenting_barrel_container", () -> IMenuTypeExtension.create(ContainerFermentingBarrel::fromNetwork));
    public static final Supplier<MenuType<ContainerFilter>> FILTER_CONTAINER = CONTAINERS.register("filter_container", () -> IMenuTypeExtension.create(ContainerFilter::fromNetwork));
    public static final Supplier<MenuType<ContainerFireworkBox>> FIREWORK_BOX_CONTAINER = CONTAINERS.register("firework_box_container", () -> IMenuTypeExtension.create(ContainerFireworkBox::fromNetwork));
    public static final Supplier<MenuType<ContainerFluidCollector>> FLUID_COLLECTOR_CONTAINER = CONTAINERS.register("fluid_collector_container", () -> IMenuTypeExtension.create(ContainerFluidCollector::fromNetwork));
    public static final Supplier<MenuType<ContainerFurnaceDouble>> FURNACE_DOUBLE_CONTAINER = CONTAINERS.register("furnace_double_container", () -> IMenuTypeExtension.create(ContainerFurnaceDouble::fromNetwork));
    public static final Supplier<MenuType<CrusherContainer>> GRINDER_CONTAINER = CONTAINERS.register("grinder_container", () -> IMenuTypeExtension.create(CrusherContainer::fromNetwork));
    public static final Supplier<MenuType<ContainerLaserRelayItemWhitelist>> LASER_RELAY_ITEM_WHITELIST_CONTAINER = CONTAINERS.register("laser_relay_item_whitelist_container", () -> IMenuTypeExtension.create(ContainerLaserRelayItemWhitelist::fromNetwork));
    public static final Supplier<MenuType<ContainerMiner>> MINER_CONTAINER = CONTAINERS.register("miner_container", () -> IMenuTypeExtension.create(ContainerMiner::fromNetwork));
    public static final Supplier<MenuType<ContainerOilGenerator>> OIL_GENERATOR_CONTAINER = CONTAINERS.register("oil_generator_container", () -> IMenuTypeExtension.create(ContainerOilGenerator::fromNetwork));
    public static final Supplier<MenuType<ContainerPhantomPlacer>> PHANTOM_PLACER_CONTAINER = CONTAINERS.register("phantom_placer_container", () -> IMenuTypeExtension.create(ContainerPhantomPlacer::fromNetwork));
    public static final Supplier<MenuType<ContainerRangedCollector>> RANGED_COLLECTOR_CONTAINER = CONTAINERS.register("ranged_collector_container", () -> IMenuTypeExtension.create(ContainerRangedCollector::fromNetwork));
    public static final Supplier<MenuType<ContainerXPSolidifier>> XPSOLIDIFIER_CONTAINER = CONTAINERS.register("xpsolidifier_container", () -> IMenuTypeExtension.create(ContainerXPSolidifier::fromNetwork));

}

