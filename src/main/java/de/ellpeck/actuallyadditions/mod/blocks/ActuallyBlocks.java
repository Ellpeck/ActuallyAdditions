/*
 * This file ("InitBlocks.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.AACrops;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.Crystals;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.registration.AABlockReg;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public final class ActuallyBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ActuallyAdditions.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ActuallyAdditions.MODID);

    public static final Item.Properties defaultBlockItemProperties = new Item.Properties().stacksTo(64);

    public static final BlockBehaviour.Properties miscBlockProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5f, 10f);

    // Casings
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> WOOD_CASING = new AABlockReg<>("wood_casing", () -> new ActuallyBlock(miscBlockProperties), ActuallyBlock::createBlockItem);
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> IRON_CASING = new AABlockReg<>("iron_casing", () -> new ActuallyBlock(miscBlockProperties), ActuallyBlock::createBlockItem);
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> ENDER_CASING = new AABlockReg<>("ender_casing", () -> new ActuallyBlock(miscBlockProperties), ActuallyBlock::createBlockItem);
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> LAVA_FACTORY_CASING = new AABlockReg<>("lava_factory_casing", () -> new ActuallyBlock(miscBlockProperties), ActuallyBlock::createBlockItem);

    // Machines
    public static final AABlockReg<BlockFeeder, AABlockItem, TileEntityFeeder> FEEDER = new AABlockReg<>("feeder", BlockFeeder::new, (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFeeder::new);
    public static final AABlockReg<BlockCrusher, AABlockItem, TileEntityCrusher> CRUSHER = new AABlockReg<>("crusher", () -> new BlockCrusher(false),
        (b) -> new AABlockItem.BlockEntityEnergyItem(b, defaultBlockItemProperties), TileEntityCrusher::new);
    public static final AABlockReg<BlockCrusher, AABlockItem, TileEntityCrusher> CRUSHER_DOUBLE = new AABlockReg<>("crusher_double", () -> new BlockCrusher(true),
        (b) -> new AABlockItem.BlockEntityEnergyItem(b, defaultBlockItemProperties), TileEntityCrusherDouble::new);

    public static final AABlockReg<BlockEnergizer, AABlockItem, TileEntityEnergizer> ENERGIZER = new AABlockReg<>("energizer", () -> new BlockEnergizer(true),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityEnergizer::new);
    public static final AABlockReg<BlockEnergizer, AABlockItem, TileEntityEnervator> ENERVATOR = new AABlockReg<>("enervator", () -> new BlockEnergizer(false),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityEnervator::new);

    public static final AABlockReg<BlockLavaFactoryController, AABlockItem, TileEntityLavaFactoryController> LAVA_FACTORY_CONTROLLER
        = new AABlockReg<>("lava_factory_controller", BlockLavaFactoryController::new, (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLavaFactoryController::new);

    public static final AABlockReg<BlockLampController, AABlockItem, ?> LAMP_CONTROLLER = new AABlockReg<>("lamp_controller", BlockLampController::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static final AABlockReg<BlockCanolaPress, AABlockItem, TileEntityCanolaPress> CANOLA_PRESS = new AABlockReg<>("canola_press", BlockCanolaPress::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityCanolaPress::new);

    public static final AABlockReg<BlockFermentingBarrel, AABlockItem, TileEntityFermentingBarrel> FERMENTING_BARREL = new AABlockReg<>("fermenting_barrel", BlockFermentingBarrel::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFermentingBarrel::new);

    public static final AABlockReg<BlockOilGenerator, AABlockItem, TileEntityOilGenerator> OIL_GENERATOR = new AABlockReg<>("oil_generator", BlockOilGenerator::new,
        (b) -> new AABlockItem.BlockEntityEnergyItem(b, defaultBlockItemProperties), TileEntityOilGenerator::new);

    public static final AABlockReg<BlockCoalGenerator, AABlockItem, TileEntityCoalGenerator> COAL_GENERATOR = new AABlockReg<>("coal_generator", BlockCoalGenerator::new,
        (b) -> new AABlockItem.BlockEntityEnergyItem(b, defaultBlockItemProperties), TileEntityCoalGenerator::new);

    public static final AABlockReg<BlockLeafGenerator, AABlockItem, TileEntityLeafGenerator> LEAF_GENERATOR = new AABlockReg<>("leaf_generator", BlockLeafGenerator::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties) , TileEntityLeafGenerator::new);

    public static final AABlockReg<BlockXPSolidifier, AABlockItem, TileEntityXPSolidifier> XP_SOLIDIFIER = new AABlockReg<>("xp_solidifier", BlockXPSolidifier::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityXPSolidifier::new);

    public static final AABlockReg<BlockBreaker, AABlockItem, TileEntityBreaker> BREAKER = new AABlockReg<>("breaker", () -> new BlockBreaker(false),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityBreaker::new);
    public static final AABlockReg<BlockBreaker, AABlockItem, TileEntityPlacer> PLACER = new AABlockReg<>("placer", () -> new BlockBreaker(true),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPlacer::new);
    public static final AABlockReg<BlockDropper, AABlockItem, TileEntityDropper> DROPPER = new AABlockReg<>("dropper", BlockDropper::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityDropper::new);
    public static final AABlockReg<BlockFluidCollector, AABlockItem, TileEntityFluidPlacer> FLUID_PLACER = new AABlockReg<>("fluid_placer", () -> new BlockFluidCollector(true),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFluidPlacer::new);
    public static final AABlockReg<BlockFluidCollector, AABlockItem, TileEntityFluidCollector> FLUID_COLLECTOR = new AABlockReg<>("fluid_collector", () -> new BlockFluidCollector(false),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFluidCollector::new);

    public static final AABlockReg<BlockFarmer, AABlockItem, TileEntityFarmer> FARMER = new AABlockReg<>("farmer", BlockFarmer::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFarmer::new);

    public static final AABlockReg<BlockBioReactor, AABlockItem, TileEntityBioReactor> BIOREACTOR = new AABlockReg<>("bio_reactor", BlockBioReactor::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityBioReactor::new);

    public static final AABlockReg<BlockVerticalDigger, AABlockItem, TileEntityVerticalDigger> VERTICAL_DIGGER = new AABlockReg<>("vertical_digger", BlockVerticalDigger::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityVerticalDigger::new);

    public static final AABlockReg<BlockAtomicReconstructor, AABlockItem, TileEntityAtomicReconstructor> ATOMIC_RECONSTRUCTOR = new AABlockReg<>("atomic_reconstructor", BlockAtomicReconstructor::new,
            BlockAtomicReconstructor.TheItemBlock::new, TileEntityAtomicReconstructor::new);
    public static final AABlockReg<BlockRangedCollector, AABlockItem, TileEntityRangedCollector> RANGED_COLLECTOR = new AABlockReg<>("ranged_collector", BlockRangedCollector::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityRangedCollector::new);
    public static final AABlockReg<BlockLongRangeBreaker, AABlockItem, TileEntityLongRangeBreaker> LONG_RANGE_BREAKER = new AABlockReg<>("long_range_breaker", BlockLongRangeBreaker::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLongRangeBreaker::new);

    public static final AABlockReg<BlockCoffeeMachine, AABlockItem, TileEntityCoffeeMachine> COFFEE_MACHINE = new AABlockReg<>("coffee_machine", BlockCoffeeMachine::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityCoffeeMachine::new);
    public static final AABlockReg<BlockPoweredFurnace, AABlockItem, TileEntityPoweredFurnace> POWERED_FURNACE = new AABlockReg<>("powered_furnace", BlockPoweredFurnace::new,
        (b) -> new AABlockItem.BlockEntityEnergyItem(b, defaultBlockItemProperties), TileEntityPoweredFurnace::new);

    public static final AABlockReg<Crate, AABlockItem, CrateBE> CRATE_SMALL = new AABlockReg<>("crate_small", () -> new Crate(Crate.Size.SMALL),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), (pos, state) -> new CrateBE(pos, state, Crate.Size.SMALL)); //TODO


    // Crystal Blocks
    public static final AABlockReg<BlockCrystal, AABlockItem,?> ENORI_CRYSTAL = new AABlockReg<>("enori_crystal_block", () -> new BlockCrystal(false), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> RESTONIA_CRYSTAL = new AABlockReg<>("restonia_crystal_block", () -> new BlockCrystal(false), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> PALIS_CRYSTAL = new AABlockReg<>("palis_crystal_block", () -> new BlockCrystal(false), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> DIAMATINE_CRYSTAL = new AABlockReg<>("diamatine_crystal_block", () -> new BlockCrystal(false), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> VOID_CRYSTAL = new AABlockReg<>("void_crystal_block", () -> new BlockCrystal(false), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> EMERADIC_CRYSTAL = new AABlockReg<>("emeradic_crystal_block", () -> new BlockCrystal(false), BlockCrystal::createBlockItem);
    // Empowered Crystal Blocks
    public static final AABlockReg<BlockCrystal, AABlockItem,?> EMPOWERED_ENORI_CRYSTAL = new AABlockReg<>("empowered_enori_crystal_block", () -> new BlockCrystal(true), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> EMPOWERED_RESTONIA_CRYSTAL = new AABlockReg<>("empowered_restonia_crystal_block", () -> new BlockCrystal(true), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> EMPOWERED_PALIS_CRYSTAL = new AABlockReg<>("empowered_palis_crystal_block", () -> new BlockCrystal(true), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> EMPOWERED_DIAMATINE_CRYSTAL = new AABlockReg<>("empowered_diamatine_crystal_block", () -> new BlockCrystal(true), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> EMPOWERED_VOID_CRYSTAL = new AABlockReg<>("empowered_void_crystal_block", () -> new BlockCrystal(true), BlockCrystal::createBlockItem);
    public static final AABlockReg<BlockCrystal, AABlockItem,?> EMPOWERED_EMERADIC_CRYSTAL = new AABlockReg<>("empowered_emeradic_crystal_block", () -> new BlockCrystal(true), BlockCrystal::createBlockItem);
    // Crystal Clusters
    public static final AABlockReg<CrystalClusterBlock, AABlockItem, ?> ENORI_CRYSTAL_CLUSTER = new AABlockReg<>("enori_crystal_cluster", () -> new CrystalClusterBlock(Crystals.IRON), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<CrystalClusterBlock, AABlockItem, ?> RESTONIA_CRYSTAL_CLUSTER = new AABlockReg<>("restonia_crystal_cluster", () -> new CrystalClusterBlock(Crystals.REDSTONE), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<CrystalClusterBlock, AABlockItem, ?> PALIS_CRYSTAL_CLUSTER = new AABlockReg<>("palis_crystal_cluster", () -> new CrystalClusterBlock(Crystals.LAPIS), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<CrystalClusterBlock, AABlockItem, ?> DIAMATINE_CRYSTAL_CLUSTER = new AABlockReg<>("diamatine_crystal_cluster", () -> new CrystalClusterBlock(Crystals.DIAMOND), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<CrystalClusterBlock, AABlockItem, ?> VOID_CRYSTAL_CLUSTER = new AABlockReg<>("void_crystal_cluster", () -> new CrystalClusterBlock(Crystals.COAL), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<CrystalClusterBlock, AABlockItem, ?> EMERADIC_CRYSTAL_CLUSTER = new AABlockReg<>("emeradic_crystal_cluster", () -> new CrystalClusterBlock(Crystals.EMERALD), (b) -> new AABlockItem(b, defaultBlockItemProperties));

    // LAMPS! SO MANY LAMPS
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_WHITE = new AABlockReg<>("lamp_white", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_ORANGE = new AABlockReg<>("lamp_orange", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_MAGENTA = new AABlockReg<>("lamp_magenta", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_LIGHT_BLUE = new AABlockReg<>("lamp_light_blue", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_YELLOW = new AABlockReg<>("lamp_yellow", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_LIME = new AABlockReg<>("lamp_lime", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_PINK = new AABlockReg<>("lamp_pink", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_GRAY = new AABlockReg<>("lamp_gray", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_LIGHT_GRAY = new AABlockReg<>("lamp_light_gray", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_CYAN = new AABlockReg<>("lamp_cyan", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_PURPLE = new AABlockReg<>("lamp_purple", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_BLUE = new AABlockReg<>("lamp_blue", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_BROWN = new AABlockReg<>("lamp_brown", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_GREEN = new AABlockReg<>("lamp_green", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_RED = new AABlockReg<>("lamp_red", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockColoredLamp, AABlockItem, ?> LAMP_BLACK = new AABlockReg<>("lamp_black", BlockColoredLamp::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));

    // Empowerer / Display Stands
    public static final AABlockReg<BlockDisplayStand, AABlockItem, TileEntityEmpowerer> EMPOWERER = new AABlockReg<>("empowerer", () -> new BlockDisplayStand(true),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityEmpowerer::new);
    public static final AABlockReg<BlockDisplayStand, AABlockItem, TileEntityDisplayStand> DISPLAY_STAND = new AABlockReg<>("display_stand", () -> new BlockDisplayStand(false),
        (b) -> new AABlockItem.BlockEntityEnergyItem(b, defaultBlockItemProperties), TileEntityDisplayStand::new);

    // Interface Blocks
    public static final AABlockReg<BlockPlayerInterface, AABlockItem, TileEntityPlayerInterface> PLAYER_INTERFACE = new AABlockReg<>("player_interface", BlockPlayerInterface::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPlayerInterface::new);
    public static final AABlockReg<BlockItemInterface, AABlockItem, TileEntityItemInterface> ITEM_INTERFACE = new AABlockReg<>("item_interface", BlockItemInterface::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityItemInterface::new);
    public static final AABlockReg<BlockItemInterfaceHopping, AABlockItem, TileEntityItemInterfaceHopping> ITEM_INTERFACE_HOPPING = new AABlockReg<>("hopping_item_interface", BlockItemInterfaceHopping::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityItemInterfaceHopping::new);

    // Phantom stuff
    public static final AABlockReg<BlockPhantom, AABlockItem, TileEntityPhantomItemface> PHANTOM_ITEMFACE = new AABlockReg<>("phantom_itemface", () -> new BlockPhantom(BlockPhantom.Type.ITEMFACE),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPhantomItemface::new);
    public static final AABlockReg<BlockPhantom, AABlockItem, TileEntityPhantomPlacer> PHANTOM_PLACER = new AABlockReg<>("phantom_placer", () -> new BlockPhantom(BlockPhantom.Type.PLACER),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPhantomPlacer::new);
    public static final AABlockReg<BlockPhantom, AABlockItem, TileEntityPhantomLiquiface> PHANTOM_LIQUIFACE = new AABlockReg<>("phantom_liquiface", () -> new BlockPhantom(BlockPhantom.Type.LIQUIFACE),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPhantomLiquiface::new);
    public static final AABlockReg<BlockPhantom, AABlockItem, TileEntityPhantomEnergyface> PHANTOM_ENERGYFACE = new AABlockReg<>("phantom_energyface", () -> new BlockPhantom(BlockPhantom.Type.ENERGYFACE),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPhantomEnergyface::new);
    public static final AABlockReg<BlockPhantom, AABlockItem, TileEntityPhantomRedstoneface> PHANTOM_REDSTONEFACE = new AABlockReg<>("phantom_redstoneface", () -> new BlockPhantom(BlockPhantom.Type.REDSTONEFACE),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPhantomRedstoneface::new);
    public static final AABlockReg<BlockPhantom, AABlockItem, TileEntityPhantomBreaker> PHANTOM_BREAKER = new AABlockReg<>("phantom_breaker", () -> new BlockPhantom(BlockPhantom.Type.BREAKER),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPhantomBreaker::new);
    public static final AABlockReg<BlockPhantomBooster, AABlockItem, ?> PHANTOM_BOOSTER = new AABlockReg<>("phantom_booster", BlockPhantomBooster::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    // Misc Tiles
    public static final AABlockReg<BlockBatteryBox, AABlockItem, TileEntityBatteryBox> BATTERY_BOX = new AABlockReg<>("battery_box", BlockBatteryBox::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityBatteryBox::new);

    public static final AABlockReg<BlockFireworkBox, AABlockItem, TileEntityFireworkBox> FIREWORK_BOX = new AABlockReg<>("firework_box", BlockFireworkBox::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFireworkBox::new);

    public static final AABlockReg<BlockShockSuppressor, AABlockItem, TileEntityShockSuppressor> SHOCK_SUPPRESSOR = new AABlockReg<>("shock_suppressor", BlockShockSuppressor::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityShockSuppressor::new);
    public static final AABlockReg<BlockHeatCollector, AABlockItem, TileEntityHeatCollector> HEAT_COLLECTOR = new AABlockReg<>("heat_collector", BlockHeatCollector::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityHeatCollector::new);

    // Freakin-Lasers
    public static final AABlockReg<BlockLaserRelay, AABlockItem, TileEntityLaserRelayEnergy> LASER_RELAY = new AABlockReg<>("laser_relay", () -> new BlockLaserRelay(BlockLaserRelay.Type.ENERGY_BASIC),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLaserRelayEnergy::new);
    public static final AABlockReg<BlockLaserRelay, AABlockItem, TileEntityLaserRelayEnergyAdvanced> LASER_RELAY_ADVANCED = new AABlockReg<>("laser_relay_advanced", () -> new BlockLaserRelay(BlockLaserRelay.Type.ENERGY_ADVANCED),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLaserRelayEnergyAdvanced::new);
    public static final AABlockReg<BlockLaserRelay, AABlockItem,TileEntityLaserRelayEnergyExtreme> LASER_RELAY_EXTREME = new AABlockReg<>("laser_relay_extreme", () -> new BlockLaserRelay(BlockLaserRelay.Type.ENERGY_EXTREME),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLaserRelayEnergyExtreme::new);
    public static final AABlockReg<BlockLaserRelay, AABlockItem,TileEntityLaserRelayFluids> LASER_RELAY_FLUIDS = new AABlockReg<>("laser_relay_fluids", () -> new BlockLaserRelay(BlockLaserRelay.Type.FLUIDS),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLaserRelayFluids::new);
    public static final AABlockReg<BlockLaserRelay, AABlockItem,TileEntityLaserRelayItem> LASER_RELAY_ITEM = new AABlockReg<>("laser_relay_item", () -> new BlockLaserRelay(BlockLaserRelay.Type.ITEM),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLaserRelayItem::new);
    public static final AABlockReg<BlockLaserRelay, AABlockItem, TileEntityLaserRelayItemAdvanced> LASER_RELAY_ITEM_ADVANCED = new AABlockReg<>("laser_relay_item_advanced", () -> new BlockLaserRelay(BlockLaserRelay.Type.ITEM_WHITELIST),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLaserRelayItemAdvanced::new);



    // Misc building blocks
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> BLACK_QUARTZ_ORE = new AABlockReg<>("black_quartz_ore", () -> new ActuallyBlock(miscBlockProperties),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> ETHETIC_GREEN_BLOCK = new AABlockReg<>("ethetic_green_block", () -> new ActuallyBlock(miscBlockProperties), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> ETHETIC_WHITE_BLOCK = new AABlockReg<>("ethetic_white_block", () -> new ActuallyBlock(miscBlockProperties), (b) -> new AABlockItem(b, defaultBlockItemProperties));

    //Black Quartz
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> BLACK_QUARTZ = new AABlockReg<>("black_quartz_block", () -> new ActuallyBlock(miscBlockProperties), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> BLACK_QUARTZ_PILLAR = new AABlockReg<>("black_quartz_pillar_block", () -> new ActuallyBlock(miscBlockProperties), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> CHISELED_BLACK_QUARTZ = new AABlockReg<>("chiseled_black_quartz_block", () -> new ActuallyBlock(miscBlockProperties), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> SMOOTH_BLACK_QUARTZ = new AABlockReg<>("smooth_black_quartz_block", () -> new ActuallyBlock(miscBlockProperties), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> BLACK_QUARTZ_BRICK = new AABlockReg<>("black_quartz_brick_block", () -> new ActuallyBlock(miscBlockProperties), (b) -> new AABlockItem(b, defaultBlockItemProperties));

    //Walls
    public static final AABlockReg<WallBlock, AABlockItem, ?> ETHETIC_GREEN_WALL = new AABlockReg<>("ethetic_green_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(ETHETIC_GREEN_BLOCK.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> ETHETIC_WHITE_WALL = new AABlockReg<>("ethetic_white_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(ETHETIC_WHITE_BLOCK.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> BLACK_QUARTZ_WALL = new AABlockReg<>("black_quartz_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> CHISELED_BLACK_QUARTZ_WALL = new AABlockReg<>("chiseled_black_quartz_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CHISELED_BLACK_QUARTZ.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> SMOOTH_BLACK_QUARTZ_WALL = new AABlockReg<>("smooth_black_quartz_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CHISELED_BLACK_QUARTZ.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> BLACK_QUARTZ_BRICK_WALL = new AABlockReg<>("black_quartz_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CHISELED_BLACK_QUARTZ.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> BLACK_QUARTZ_PILLAR_WALL = new AABlockReg<>("black_quartz_pillar_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ_PILLAR.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));

    //Slabs
    public static final AABlockReg<SlabBlock, AABlockItem, ?> ETHETIC_GREEN_SLAB = new AABlockReg<>("ethetic_green_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ETHETIC_GREEN_BLOCK.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> ETHETIC_WHITE_SLAB = new AABlockReg<>("ethetic_white_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ETHETIC_WHITE_BLOCK.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> BLACK_QUARTZ_SLAB = new AABlockReg<>("black_quartz_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> CHISELED_BLACK_QUARTZ_SLAB = new AABlockReg<>("chiseled_black_quartz_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CHISELED_BLACK_QUARTZ.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> BLACK_QUARTZ_PILLAR_SLAB = new AABlockReg<>("black_quartz_pillar_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ_PILLAR.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> SMOOTH_BLACK_QUARTZ_SLAB = new AABlockReg<>("smooth_black_quartz_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ_PILLAR.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> BLACK_QUARTZ_BRICK_SLAB = new AABlockReg<>("black_quartz_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ_PILLAR.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));


    //Stairs
    public static final AABlockReg<StairBlock, AABlockItem, ?> ETHETIC_GREEN_STAIRS = new AABlockReg<>("ethetic_green_stairs", () -> new StairBlock(() -> ETHETIC_GREEN_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ETHETIC_GREEN_BLOCK.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairBlock, AABlockItem, ?> ETHETIC_WHITE_STAIRS = new AABlockReg<>("ethetic_white_stairs", () -> new StairBlock(() -> ETHETIC_WHITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ETHETIC_WHITE_BLOCK.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairBlock, AABlockItem, ?> BLACK_QUARTZ_STAIR = new AABlockReg<>("black_quartz_stair", () -> new StairBlock(() -> BLACK_QUARTZ.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairBlock, AABlockItem, ?> CHISELED_BLACK_QUARTZ_STAIR = new AABlockReg<>("chiseled_black_quartz_stair", () -> new StairBlock(() -> CHISELED_BLACK_QUARTZ.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CHISELED_BLACK_QUARTZ.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairBlock, AABlockItem, ?> BLACK_QUARTZ_PILLAR_STAIR = new AABlockReg<>("black_quartz_pillar_stair", () -> new StairBlock(() -> BLACK_QUARTZ_PILLAR.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ_PILLAR.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairBlock, AABlockItem, ?> SMOOTH_BLACK_QUARTZ_STAIR = new AABlockReg<>("smooth_black_quartz_stair", () -> new StairBlock(() -> BLACK_QUARTZ_PILLAR.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ_PILLAR.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairBlock, AABlockItem, ?> BLACK_QUARTZ_BRICK_STAIR = new AABlockReg<>("black_quartz_brick_stair", () -> new StairBlock(() -> BLACK_QUARTZ_PILLAR.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BLACK_QUARTZ_PILLAR.get())), (b) -> new AABlockItem(b, defaultBlockItemProperties));




    // Other Misc Blocks
    public static final AABlockReg<BlockTinyTorch, AABlockItem, ?> TINY_TORCH = new AABlockReg<>("tiny_torch", BlockTinyTorch::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties));


    public static Supplier<AACrops> CANOLA = BLOCKS.register("canola", () -> new AACrops(defaultCropProps(), ActuallyItems.CANOLA_SEEDS));
    public static Supplier<AACrops> RICE = BLOCKS.register("rice", () -> new AACrops(defaultCropProps(), ActuallyItems.RICE_SEEDS));
    public static Supplier<AACrops> FLAX = BLOCKS.register("flax", () -> new AACrops(defaultCropProps(), ActuallyItems.FLAX_SEEDS));
    public static Supplier<AACrops> COFFEE = BLOCKS.register("coffee", () -> new AACrops(defaultCropProps(), ActuallyItems.COFFEE_BEANS));

    public static final AABlockReg<BlockGreenhouseGlass, AABlockItem, ?> GREENHOUSE_GLASS = new AABlockReg<>("greenhouse_glass", BlockGreenhouseGlass::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static BlockBehaviour.Properties defaultPickProps(float hardness, float resistance) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(hardness, resistance).sound(SoundType.STONE);
    }

    public static BlockBehaviour.Properties defaultPickProps() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5F, 10.0F).sound(SoundType.STONE);
    }

    public static BlockBehaviour.Properties defaultCropProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP);
    }

    public static void init(IEventBus evt) {
        BLOCKS.register(evt);
        TILES.register(evt);
        evt.addListener(ActuallyBlocks::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        List<BlockEntityType<? extends TileEntityBase>> types = List.of(
                FEEDER.getTileEntityType(),
                CRUSHER.getTileEntityType(),
                CRUSHER_DOUBLE.getTileEntityType(),
                ENERGIZER.getTileEntityType(),
                ENERVATOR.getTileEntityType(),
                LAVA_FACTORY_CONTROLLER.getTileEntityType(),
                CANOLA_PRESS.getTileEntityType(),
                FERMENTING_BARREL.getTileEntityType(),
                OIL_GENERATOR.getTileEntityType(),
                COAL_GENERATOR.getTileEntityType(),
                LEAF_GENERATOR.getTileEntityType(),
                XP_SOLIDIFIER.getTileEntityType(),
                BREAKER.getTileEntityType(),
                PLACER.getTileEntityType(),
                DROPPER.getTileEntityType(),
                FLUID_PLACER.getTileEntityType(),
                FLUID_COLLECTOR.getTileEntityType(),
                FARMER.getTileEntityType(),
                BIOREACTOR.getTileEntityType(),
                VERTICAL_DIGGER.getTileEntityType(),
                ATOMIC_RECONSTRUCTOR.getTileEntityType(),
                RANGED_COLLECTOR.getTileEntityType(),
                LONG_RANGE_BREAKER.getTileEntityType(),
                COFFEE_MACHINE.getTileEntityType(),
                POWERED_FURNACE.getTileEntityType(),
                EMPOWERER.getTileEntityType(),
                DISPLAY_STAND.getTileEntityType(),
                PLAYER_INTERFACE.getTileEntityType(),
                ITEM_INTERFACE.getTileEntityType(),
                ITEM_INTERFACE_HOPPING.getTileEntityType(),
                PHANTOM_ITEMFACE.getTileEntityType(),
                PHANTOM_PLACER.getTileEntityType(),
                PHANTOM_LIQUIFACE.getTileEntityType(),
                PHANTOM_ENERGYFACE.getTileEntityType(),
                PHANTOM_REDSTONEFACE.getTileEntityType(),
                PHANTOM_BREAKER.getTileEntityType(),
                BATTERY_BOX.getTileEntityType(),
                FIREWORK_BOX.getTileEntityType(),
                SHOCK_SUPPRESSOR.getTileEntityType(),
                HEAT_COLLECTOR.getTileEntityType(),
                LASER_RELAY.getTileEntityType(),
                LASER_RELAY_ADVANCED.getTileEntityType(),
                LASER_RELAY_EXTREME.getTileEntityType(),
                LASER_RELAY_FLUIDS.getTileEntityType(),
                LASER_RELAY_ITEM.getTileEntityType(),
                LASER_RELAY_ITEM_ADVANCED.getTileEntityType()
        );

        types.forEach(type -> {
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, type, TileEntityBase::getItemHandler);
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, type, TileEntityBase::getEnergyStorage);
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, type, TileEntityBase::getFluidHandler);
        });
    }
}
