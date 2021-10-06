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
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockPlant;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.Crystals;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.registration.AABlockReg;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ActuallyBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ActuallyAdditions.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ActuallyAdditions.MODID);

    private static final Item.Properties defaultBlockItemProperties = new Item.Properties().tab(ActuallyAdditions.GROUP).stacksTo(64);

    public static final AbstractBlock.Properties miscBlockProperties = AbstractBlock.Properties.of(Material.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE).strength(1.5f, 10f);

    // Casings
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> WOOD_CASING = new AABlockReg<>("wood_casing", () -> new ActuallyBlock(miscBlockProperties), ActuallyBlock::createBlockItem);
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> IRON_CASING = new AABlockReg<>("iron_casing", () -> new ActuallyBlock(miscBlockProperties), ActuallyBlock::createBlockItem);
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> ENDER_CASING = new AABlockReg<>("ender_casing", () -> new ActuallyBlock(miscBlockProperties), ActuallyBlock::createBlockItem);
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> LAVA_FACTORY_CASING = new AABlockReg<>("lava_factory_casing", () -> new ActuallyBlock(miscBlockProperties), ActuallyBlock::createBlockItem);

    // Machines
    public static final AABlockReg<BlockFeeder, AABlockItem, TileEntityFeeder> FEEDER = new AABlockReg<>("feeder", BlockFeeder::new, (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFeeder::new);
    public static final AABlockReg<BlockCrusher, AABlockItem, TileEntityCrusher> CRUSHER = new AABlockReg<>("crusher", () -> new BlockCrusher(false),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityCrusher::new);
    public static final AABlockReg<BlockCrusher, AABlockItem, TileEntityCrusher> CRUSHER_DOUBLE = new AABlockReg<>("crusher_double", () -> new BlockCrusher(false),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityCrusherDouble::new);

    public static final AABlockReg<BlockEnergizer, AABlockItem, TileEntityEnergizer> ENERGIZER = new AABlockReg<>("energizer", () -> new BlockEnergizer(true),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityEnergizer::new);
    public static final AABlockReg<BlockEnergizer, AABlockItem, TileEntityEnergizer> ENERVATOR = new AABlockReg<>("enervator", () -> new BlockEnergizer(false),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityEnergizer::new);

    public static final AABlockReg<BlockLavaFactoryController, AABlockItem, TileEntityLavaFactoryController> LAVA_FACTORY_CONTROLLER
        = new AABlockReg<>("lava_factory_controller", BlockLavaFactoryController::new, (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLavaFactoryController::new);

    public static final AABlockReg<BlockLampPowerer, AABlockItem, ?> LAMP_POWERER = new AABlockReg<>("lamp_powerer", BlockLampPowerer::new, (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static final AABlockReg<BlockCanolaPress, AABlockItem, TileEntityCanolaPress> CANOLA_PRESS = new AABlockReg<>("canola_press", BlockCanolaPress::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityCanolaPress::new);

    public static final AABlockReg<BlockFermentingBarrel, AABlockItem, TileEntityFermentingBarrel> FERMENTING_BARREL = new AABlockReg<>("fermenting_barrel", BlockFermentingBarrel::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFermentingBarrel::new);

    public static final AABlockReg<BlockOilGenerator, AABlockItem, TileEntityOilGenerator> OIL_GENERATOR = new AABlockReg<>("oil_generator", BlockOilGenerator::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityOilGenerator::new);

    public static final AABlockReg<BlockCoalGenerator, AABlockItem, TileEntityCoalGenerator> COAL_GENERATOR = new AABlockReg<>("coal_generator", BlockCoalGenerator::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityCoalGenerator::new);

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
    public static final AABlockReg<BlockFluidCollector, AABlockItem, TileEntityFluidCollector> FLUID_PLACER = new AABlockReg<>("fluid_placer", () -> new BlockFluidCollector(true),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFluidCollector::new);
    public static final AABlockReg<BlockFluidCollector, AABlockItem, TileEntityFluidPlacer> FLUID_COLLECTOR = new AABlockReg<>("fluid_collector", () -> new BlockFluidCollector(false),
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFluidPlacer::new);

    public static final AABlockReg<BlockFarmer, AABlockItem, TileEntityFarmer> FARMER = new AABlockReg<>("farmer", BlockFarmer::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityFarmer::new);

    public static final AABlockReg<BlockBioReactor, AABlockItem, TileEntityBioReactor> BIOREACTOR = new AABlockReg<>("bio_reactor", BlockBioReactor::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityBioReactor::new);

    public static final AABlockReg<BlockVerticalDigger, AABlockItem, TileEntityVerticalDigger> VERTICAL_DIGGER = new AABlockReg<>("vertical_digger", BlockVerticalDigger::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityVerticalDigger::new);

    public static final AABlockReg<BlockAtomicReconstructor, AABlockItem, TileEntityAtomicReconstructor> ATOMIC_RECONSTRUCTOR = new AABlockReg<>("atomic_reconstructor", BlockAtomicReconstructor::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityAtomicReconstructor::new);
    public static final AABlockReg<BlockRangedCollector, AABlockItem, TileEntityRangedCollector> RANGED_COLLECTOR = new AABlockReg<>("ranged_collector", BlockRangedCollector::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityRangedCollector::new);
    public static final AABlockReg<BlockLongRangeBreaker, AABlockItem, TileEntityLongRangeBreaker> LONG_RANGE_BREAKER = new AABlockReg<>("long_range_breaker", BlockLongRangeBreaker::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityLongRangeBreaker::new);

    public static final AABlockReg<BlockCoffeeMachine, AABlockItem, TileEntityCoffeeMachine> COFFEE_MACHINE = new AABlockReg<>("coffee_machine", BlockCoffeeMachine::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityCoffeeMachine::new);
    public static final AABlockReg<BlockPoweredFurnace, AABlockItem, TileEntityPoweredFurnace> POWERED_FURNACE = new AABlockReg<>("powered_furnace", BlockPoweredFurnace::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPoweredFurnace::new);


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
    public static final AABlockReg<BlockEmpowerer, AABlockItem, TileEntityEmpowerer> EMPOWERER = new AABlockReg<>("empowerer", BlockEmpowerer::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityEmpowerer::new);
    public static final AABlockReg<BlockDisplayStand, AABlockItem, TileEntityDisplayStand> DISPLAY_STAND = new AABlockReg<>("display_stand", BlockDisplayStand::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityDisplayStand::new);

    // Interface Blocks
    public static final AABlockReg<BlockPlayerInterface, AABlockItem, TileEntityPlayerInterface> PLAYER_INTERFACE = new AABlockReg<>("player_interface", BlockPlayerInterface::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPlayerInterface::new);
    public static final AABlockReg<BlockItemInterface, AABlockItem, TileEntityItemInterface> ITEM_VIEWER = new AABlockReg<>("item_viewer", BlockItemInterface::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityItemInterface::new);
    public static final AABlockReg<BlockItemInterfaceHopping, AABlockItem, TileEntityItemInterfaceHopping> ITEM_VIEWER_HOPPING = new AABlockReg<>("item_viewer_hopping", BlockItemInterfaceHopping::new,
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
    public static final AABlockReg<BlockPhantomBooster, AABlockItem, TileEntityPhantomBooster> PHANTOM_BOOSTER = new AABlockReg<>("phantom_booster", BlockPhantomBooster::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties), TileEntityPhantomBooster::new);

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
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> ENDER_PEARL_BLOCK = new AABlockReg<>("ender_pearl_block", () -> new ActuallyBlock(miscBlockProperties),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> ETHETIC_GREEN_BLOCK = new AABlockReg<>("ethetic_green_block", () -> new ActuallyBlock(miscBlockProperties),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> ETHETIC_WHITE_BLOCK = new AABlockReg<>("ethetic_white_block", () -> new ActuallyBlock(miscBlockProperties),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> BLACK_QUARTZ_BLOCK = new AABlockReg<>("black_quartz_block", () -> new ActuallyBlock(miscBlockProperties),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> BLACK_QUARTZ_PILLAR_BLOCK = new AABlockReg<>("black_quartz_pillar_block", () -> new ActuallyBlock(miscBlockProperties),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<ActuallyBlock, AABlockItem, ?> CHISELED_BLACK_QUARTZ_BLOCK = new AABlockReg<>("chiseled_black_quartz_block", () -> new ActuallyBlock(miscBlockProperties),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static final AABlockReg<StairsBlock, AABlockItem, ?> ETHETIC_GREEN_STAIRS = new AABlockReg<>("ethetic_green_stairs", () -> new StairsBlock(() -> ETHETIC_GREEN_BLOCK.get().defaultBlockState(), AbstractBlock.Properties.copy(ETHETIC_GREEN_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairsBlock, AABlockItem, ?> ETHETIC_WHITE_STAIRS = new AABlockReg<>("ethetic_white_stairs", () -> new StairsBlock(() -> ETHETIC_WHITE_BLOCK.get().defaultBlockState(), AbstractBlock.Properties.copy(ETHETIC_WHITE_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairsBlock, AABlockItem, ?> BLACK_QUARTZ_STAIR = new AABlockReg<>("black_quartz_stair", () -> new StairsBlock(() -> BLACK_QUARTZ_BLOCK.get().defaultBlockState(), AbstractBlock.Properties.copy(BLACK_QUARTZ_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairsBlock, AABlockItem, ?> CHISELED_BLACK_QUARTZ_STAIR = new AABlockReg<>("chiseled_black_quartz_stair", () -> new StairsBlock(() -> CHISELED_BLACK_QUARTZ_BLOCK.get().defaultBlockState(), AbstractBlock.Properties.copy(CHISELED_BLACK_QUARTZ_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<StairsBlock, AABlockItem, ?> BLACK_QUARTZ_PILLAR_STAIR = new AABlockReg<>("black_quartz_pillar_stair", () -> new StairsBlock(() -> BLACK_QUARTZ_PILLAR_BLOCK.get().defaultBlockState(), AbstractBlock.Properties.copy(BLACK_QUARTZ_PILLAR_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static final AABlockReg<WallBlock, AABlockItem, ?> ETHETIC_GREEN_WALL = new AABlockReg<>("ethetic_green_wall", () -> new WallBlock(AbstractBlock.Properties.copy(ETHETIC_GREEN_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> ETHETIC_WHITE_WALL = new AABlockReg<>("ethetic_white_wall", () -> new WallBlock(AbstractBlock.Properties.copy(ETHETIC_WHITE_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> BLACK_QUARTZ_WALL = new AABlockReg<>("black_quartz_wall", () -> new WallBlock(AbstractBlock.Properties.copy(BLACK_QUARTZ_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> CHISELED_BLACK_QUARTZ_WALL = new AABlockReg<>("chiseled_black_quartz_wall", () -> new WallBlock(AbstractBlock.Properties.copy(CHISELED_BLACK_QUARTZ_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<WallBlock, AABlockItem, ?> BLACK_QUARTZ_PILLAR_WALL = new AABlockReg<>("black_quartz_pillar_wall", () -> new WallBlock(AbstractBlock.Properties.copy(BLACK_QUARTZ_PILLAR_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static final AABlockReg<SlabBlock, AABlockItem, ?> ETHETIC_GREEN_SLAB = new AABlockReg<>("ethetic_green_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(ETHETIC_GREEN_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> ETHETIC_WHITE_SLAB = new AABlockReg<>("ethetic_white_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(ETHETIC_WHITE_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> BLACK_QUARTZ_SLAB = new AABlockReg<>("black_quartz_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(BLACK_QUARTZ_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> CHISELED_BLACK_QUARTZ_SLAB = new AABlockReg<>("chiseled_black_quartz_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(CHISELED_BLACK_QUARTZ_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<SlabBlock, AABlockItem, ?> BLACK_QUARTZ_PILLAR_SLAB = new AABlockReg<>("black_quartz_pillar_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(BLACK_QUARTZ_PILLAR_BLOCK.get())),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));


    // Other Misc Blocks
    public static final AABlockReg<BlockTinyTorch, AABlockItem, ?> TINY_TORCH = new AABlockReg<>("tiny_torch", BlockTinyTorch::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    //public static final AABlockReg<> WILD_PLANT = new AABlockReg<>("wild", BlockWildPlant::new); //TODO: what is this?

    //TODO: Are plants normal blocks / blockitems? i have no idea... news at 11...
   public static final AABlockReg<BlockPlant, AABlockItem, ?> RICE = new AABlockReg<>("rice", () -> new BlockPlant(ActuallyItems.RICE_SEED.get()),
       (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockPlant, AABlockItem, ?> CANOLA = new AABlockReg<>("canola", () -> new BlockPlant(ActuallyItems.CANOLA_SEED.get()),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockPlant, AABlockItem, ?> FLAX = new AABlockReg<>("flax", () -> new BlockPlant(ActuallyItems.FLAX_SEED.get()),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockPlant, AABlockItem, ?> COFFEE = new AABlockReg<>("coffee", () -> new BlockPlant(ActuallyItems.COFFEE_SEED.get()),
        (b) -> new AABlockItem(b, defaultBlockItemProperties));
    public static final AABlockReg<BlockGreenhouseGlass, AABlockItem, ?> GREENHOUSE_GLASS = new AABlockReg<>("greenhouse_glass", BlockGreenhouseGlass::new,
        (b) -> new AABlockItem(b, defaultBlockItemProperties));

    public static AbstractBlock.Properties defaultPickProps(int harvestLevel, float hardness, float resistance) {
        return AbstractBlock.Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).strength(hardness, resistance).sound(SoundType.STONE);
    }

    public static AbstractBlock.Properties defaultPickProps(int harvestLevel) {
        return AbstractBlock.Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).strength(1.5F, 10.0F).sound(SoundType.STONE);
    }
}
