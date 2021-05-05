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
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ActuallyBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ActuallyAdditions.MODID);

    public static final AbstractBlock.Properties miscBlockProperties = AbstractBlock.Properties.create(Material.ROCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f,10f);
    @Deprecated
    public static final RegistryObject<Block> blockMisc = BLOCKS.register("misc", () -> new Block(miscBlockProperties)); // TODO this isnt a real block?
    public static final RegistryObject<Block> WOOD_CASING = BLOCKS.register("wood_casing", () -> new Block(miscBlockProperties));
    public static final RegistryObject<Block> IRON_CASING = BLOCKS.register("iron_casing", () -> new Block(miscBlockProperties));
    public static final RegistryObject<Block> ENDER_CASING = BLOCKS.register("ender_casing", () -> new Block(miscBlockProperties));
    public static final RegistryObject<Block> LAVA_CASING = BLOCKS.register("lava_casing", () -> new Block(miscBlockProperties));
    public static final RegistryObject<Block> WILD_PLANT = BLOCKS.register("wild", BlockWildPlant::new);
    public static final RegistryObject<Block> FEEDER = BLOCKS.register("feeder", BlockFeeder::new);
    public static final RegistryObject<Block> GRINDER = BLOCKS.register("grinder", () -> new BlockGrinder(false));
    public static final RegistryObject<Block> GRINDER_DOUBLE = BLOCKS.register("grinder_double", () -> new BlockGrinder(true));


    public static final RegistryObject<Block> CRYSTAL_CLUSTER_REDSTONE = BLOCKS.register("crystal_cluster_redstone", () -> new BlockCrystalCluster(TheCrystals.REDSTONE));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_LAPIS = BLOCKS.register("crystal_cluster_lapis", () -> new BlockCrystalCluster(TheCrystals.LAPIS));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_DIAMOND = BLOCKS.register("crystal_cluster_diamond", () -> new BlockCrystalCluster(TheCrystals.DIAMOND));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_COAL = BLOCKS.register("crystal_cluster_coal", () -> new BlockCrystalCluster(TheCrystals.COAL));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_EMERALD = BLOCKS.register("crystal_cluster_emerald", () -> new BlockCrystalCluster(TheCrystals.EMERALD));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_IRON = BLOCKS.register("crystal_cluster_iron", () -> new BlockCrystalCluster(TheCrystals.IRON));
    public static final RegistryObject<Block> BATTERY_BOX = BLOCKS.register("battery_box", BlockBatteryBox::new);
    public static final RegistryObject<Block> ITEM_VIEWER_HOPPING = BLOCKS.register("item_viewer_hopping", BlockItemViewerHopping::new);
    public static final RegistryObject<Block> FARMER = BLOCKS.register("farmer", BlockFarmer::new);
    public static final RegistryObject<Block> BIOREACTOR = BLOCKS.register("bio_reactor", BlockBioReactor::new);
    public static final RegistryObject<Block> EMPOWERER = BLOCKS.register("empowerer", BlockEmpowerer::new);
    public static final RegistryObject<Block> TINY_TORCH = BLOCKS.register("tiny_torch", BlockTinyTorch::new);
    public static final RegistryObject<Block> SHOCK_SUPPRESSOR = BLOCKS.register("shock_suppressor", BlockShockSuppressor::new);
    public static final RegistryObject<Block> DISPLAY_STAND = BLOCKS.register("display_stand", BlockDisplayStand::new);
    public static final RegistryObject<Block> PLAYER_INTERFACE = BLOCKS.register("player_interface", BlockPlayerInterface::new);
    public static final RegistryObject<Block> ITEM_VIEWER = BLOCKS.register("item_viewer", BlockItemViewer::new);
    public static final RegistryObject<Block> FIREWORK_BOX = BLOCKS.register("firework_box", BlockFireworkBox::new);
    public static final RegistryObject<Block> MINER = BLOCKS.register("miner", BlockMiner::new);
    public static final RegistryObject<Block> ATOMIC_RECONSTRUCTOR = BLOCKS.register("atomic_reconstructor", BlockAtomicReconstructor::new);
    public static final RegistryObject<Block> LASER_RELAY = BLOCKS.register("laser_relay", () -> new BlockLaserRelay(BlockLaserRelay.Type.ENERGY_BASIC));
    public static final RegistryObject<Block> LASER_RELAY_ADVANCED = BLOCKS.register("laser_relay_advanced", () -> new BlockLaserRelay(BlockLaserRelay.Type.ENERGY_ADVANCED));
    public static final RegistryObject<Block> LASER_RELAY_EXTREME = BLOCKS.register("laser_relay_extreme", () -> new BlockLaserRelay(BlockLaserRelay.Type.ENERGY_EXTREME));
    public static final RegistryObject<Block> LASER_RELAY_FLUIDS = BLOCKS.register("laser_relay_fluids", () -> new BlockLaserRelay(BlockLaserRelay.Type.FLUIDS));
    public static final RegistryObject<Block> LASER_RELAY_ITEM = BLOCKS.register("laser_relay_item", () -> new BlockLaserRelay(BlockLaserRelay.Type.ITEM));
    public static final RegistryObject<Block> LASER_RELAY_ITEM_WHITELIST = BLOCKS.register("laser_relay_item_whitelist", () -> new BlockLaserRelay(BlockLaserRelay.Type.ITEM_WHITELIST));
    public static final RegistryObject<Block> RANGED_COLLECTOR = BLOCKS.register("ranged_collector", BlockRangedCollector::new);
    public static final RegistryObject<Block> DIRECTIONAL_BREAKER = BLOCKS.register("directional_breaker", BlockDirectionalBreaker::new);
    public static final RegistryObject<Block> LEAF_GENERATOR = BLOCKS.register("leaf_generator", BlockLeafGenerator::new);
    public static final RegistryObject<Block> XP_SOLIDIFIER = BLOCKS.register("xp_solidifier", BlockXPSolidifier::new);
    public static final RegistryObject<Block> ETHETIC_GREEN_BLOCK = BLOCKS.register("ethetic_green_block", BlockGeneric::new);
    public static final RegistryObject<Block> ETHETIC_WHITE_BLOCK = BLOCKS.register("ethetic_white_block", BlockGeneric::new);
    public static final RegistryObject<Block> ETHETIC_GREEN_STAIRS = BLOCKS.register("ethetic_green_stairs", () -> new StairsBlock(() -> ETHETIC_GREEN_BLOCK.get().getDefaultState(), AbstractBlock.Properties.from(ETHETIC_GREEN_BLOCK.get())));
    public static final RegistryObject<Block> ETHETIC_WHITE_STAIRS = BLOCKS.register("ethetic_white_stairs", () -> new StairsBlock(() -> ETHETIC_WHITE_BLOCK.get().getDefaultState(), AbstractBlock.Properties.from(ETHETIC_WHITE_BLOCK.get())));
    public static final RegistryObject<Block> ETHETIC_GREEN_SLAB = BLOCKS.register("ethetic_green_slab", () -> new SlabBlock(AbstractBlock.Properties.from(ETHETIC_GREEN_BLOCK.get())));
    public static final RegistryObject<Block> ETHETIC_WHITE_SLAB = BLOCKS.register("ethetic_white_slab", () -> new SlabBlock(AbstractBlock.Properties.from(ETHETIC_WHITE_BLOCK.get())));
    public static final RegistryObject<Block> ETHETIC_GREEN_FENCE = BLOCKS.register("ethetic_green_fence", () -> new WallBlock(AbstractBlock.Properties.from(ETHETIC_GREEN_BLOCK.get())));
    public static final RegistryObject<Block> ETHETIC_WHITE_FENCE = BLOCKS.register("ethetic_white_fence", () -> new WallBlock(AbstractBlock.Properties.from(ETHETIC_WHITE_BLOCK.get())));

    public static final RegistryObject<Block> CRYSTAL_ENORI = BLOCKS.register("crystal_enori_block", () -> new BlockCrystal(false));
    public static final RegistryObject<Block> CRYSTAL_RESTONIA = BLOCKS.register("crystal_restonia_block", () -> new BlockCrystal(false));
    public static final RegistryObject<Block> CRYSTAL_PALIS = BLOCKS.register("crystal_palis_block", () -> new BlockCrystal(false));
    public static final RegistryObject<Block> CRYSTAL_DIAMATINE = BLOCKS.register("crystal_diamatine_block", () -> new BlockCrystal(false));
    public static final RegistryObject<Block> CRYSTAL_VOID = BLOCKS.register("crystal_void_block", () -> new BlockCrystal(false));
    public static final RegistryObject<Block> CRYSTAL_EMERADIC = BLOCKS.register("crystal_emeradic_block", () -> new BlockCrystal(false));

    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_ENORI = BLOCKS.register("crystal_enori_empowered_block", () -> new BlockCrystal(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_RESTONIA = BLOCKS.register("crystal_restonia_empowered_block", () -> new BlockCrystal(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_PALIS = BLOCKS.register("crystal_palis_empowered_block", () -> new BlockCrystal(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_DIAMATINE = BLOCKS.register("crystal_diamatine_empowered_block", () -> new BlockCrystal(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_VOID = BLOCKS.register("crystal_void_empowered_block", () -> new BlockCrystal(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_EMERADIC = BLOCKS.register("crystal_emeradic_empowered_block", () -> new BlockCrystal(true));

    public static final RegistryObject<Block> LAMP_WHITE = BLOCKS.register("lamp_white_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_ORANGE = BLOCKS.register("lamp_orange_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_MAGENTA = BLOCKS.register("lamp_magenta_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_LIGHT_BLUE = BLOCKS.register("lamp_light_blue_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_YELLOW = BLOCKS.register("lamp_yellow_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_LIME = BLOCKS.register("lamp_lime_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_PINK = BLOCKS.register("lamp_pink_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_GRAY = BLOCKS.register("lamp_gray_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_LIGHT_GRAY = BLOCKS.register("lamp_light_gray_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_CYAN = BLOCKS.register("lamp_cyan_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_PURPLE = BLOCKS.register("lamp_purple_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_BLUE = BLOCKS.register("lamp_blue_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_BROWN = BLOCKS.register("lamp_brown_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_GREEN = BLOCKS.register("lamp_green_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_RED = BLOCKS.register("lamp_red_block", BlockColoredLamp::new);
    public static final RegistryObject<Block> LAMP_BLACK = BLOCKS.register("lamp_black_block", BlockColoredLamp::new);

    //    public static final RegistryObject<Block> blockColoredLamp = BLOCKS.register("colored_lamp", () -> new BlockColoredLamp());
    //    public static final RegistryObject<Block> blockColoredLampOn = BLOCKS.register("colored_lamp_on", () -> new BlockColoredLamp());
    public static final RegistryObject<Block> LAMP_POWERER = BLOCKS.register("lamp_powerer", BlockLampPowerer::new);
    //    public static final RegistryObject<Block> blockTreasureChest = BLOCKS.register("treasure_chest", BlockTreasureChest::new);
    public static final RegistryObject<Block> ENERGIZER = BLOCKS.register("energizer", () -> new BlockEnergizer(true));
    public static final RegistryObject<Block> ENERVATOR = BLOCKS.register("enervator", () -> new BlockEnergizer(false));
    public static final RegistryObject<Block> LAVA_FACTORY_CONTROLLER = BLOCKS.register("lava_factory_controller", BlockLavaFactoryController::new);
    public static final RegistryObject<Block> CANOLA_PRESS = BLOCKS.register("canola_press", BlockCanolaPress::new);
    public static final RegistryObject<Block> PHANTOMFACE = BLOCKS.register("phantomface", () -> new BlockPhantom(BlockPhantom.Type.FACE));
    public static final RegistryObject<Block> PHANTOM_PLACER = BLOCKS.register("phantom_placer", () -> new BlockPhantom(BlockPhantom.Type.PLACER));
    public static final RegistryObject<Block> PHANTOM_LIQUIFACE = BLOCKS.register("phantom_liquiface", () -> new BlockPhantom(BlockPhantom.Type.LIQUIFACE));
    public static final RegistryObject<Block> PHANTOM_ENERGYFACE = BLOCKS.register("phantom_energyface", () -> new BlockPhantom(BlockPhantom.Type.ENERGYFACE));
    public static final RegistryObject<Block> PHANTOM_REDSTONEFACE = BLOCKS.register("phantom_redstoneface", () -> new BlockPhantom(BlockPhantom.Type.REDSTONEFACE));
    public static final RegistryObject<Block> PHANTOM_BREAKER = BLOCKS.register("phantom_breaker", () -> new BlockPhantom(BlockPhantom.Type.BREAKER));
    public static final RegistryObject<Block> COAL_GENERATOR = BLOCKS.register("coal_generator", BlockCoalGenerator::new);
    public static final RegistryObject<Block> OIL_GENERATOR = BLOCKS.register("oil_generator", BlockOilGenerator::new);
    public static final RegistryObject<Block> FERMENTING_BARREL = BLOCKS.register("fermenting_barrel", BlockFermentingBarrel::new);
    public static final RegistryObject<Block> RICE = BLOCKS.register("rice", () -> new BlockPlant(ActuallyItems.RICE_SEED.get()));// TODO: [port][replace] ensure values match these new BlockPlant(1, 2));
    public static final RegistryObject<Block> CANOLA = BLOCKS.register("canola", () -> new BlockPlant(ActuallyItems.CANOLA_SEED.get()));// TODO: [port][replace] ensure values match these new BlockPlant(2, 3));
    public static final RegistryObject<Block> FLAX = BLOCKS.register("flax", () -> new BlockPlant(ActuallyItems.FLAX_SEED.get()));// TODO: [port][replace] ensure values match these new BlockPlant(2, 4));
    public static final RegistryObject<Block> COFFEE = BLOCKS.register("coffee", () -> new BlockPlant(ActuallyItems.COFFEE_SEED.get()));// TODO: [port][replace] ensure values match these new BlockPlant(2, 2));
    public static final RegistryObject<Block> FURNACE_DOUBLE = BLOCKS.register("furnace_double", BlockFurnaceDouble::new);
    public static final RegistryObject<Block> INPUTTER = BLOCKS.register("inputter", () -> new BlockInputter(false));
    public static final RegistryObject<Block> INPUTTER_ADVANCED = BLOCKS.register("inputter_advanced", () -> new BlockInputter(true));
    //    public static final RegistryObject<Block> blockFurnaceSolar = BLOCKS.register("furnace_solar", BlockFurnaceSolar::new);
    public static final RegistryObject<Block> HEAT_COLLECTOR = BLOCKS.register("heat_collector", BlockHeatCollector::new);
    public static final RegistryObject<Block> GREENHOUSE_GLASS = BLOCKS.register("greenhouse_glass", BlockGreenhouseGlass::new);
    public static final RegistryObject<Block> BREAKER = BLOCKS.register("breaker", () -> new BlockBreaker(false));
    public static final RegistryObject<Block> PLACER = BLOCKS.register("placer", () -> new BlockBreaker(true));
    public static final RegistryObject<Block> DROPPER = BLOCKS.register("dropper", BlockDropper::new);
    public static final RegistryObject<Block> FLUID_PLACER = BLOCKS.register("fluid_placer", () -> new BlockFluidCollector(true));
    public static final RegistryObject<Block> FLUID_COLLECTOR = BLOCKS.register("fluid_collector", () -> new BlockFluidCollector(false));
    public static final RegistryObject<Block> COFFEE_MACHINE = BLOCKS.register("coffee_machine", BlockCoffeeMachine::new);
    public static final RegistryObject<Block> PHANTOM_BOOSTER = BLOCKS.register("phantom_booster", BlockPhantomBooster::new);
    public static final RegistryObject<Block> QUARTZ_WALL = BLOCKS.register("quartz_wall", () -> new WallBlock(AbstractBlock.Properties.from(blockMisc.get())));
    public static final RegistryObject<Block> CHISELED_QUARTZ_WALL = BLOCKS.register("chiseled_quartz_wall", () -> new WallBlock(AbstractBlock.Properties.from(blockMisc.get())));
    public static final RegistryObject<Block> PILLAR_QUARTZ_WALL = BLOCKS.register("pillar_quartz_wall", () -> new WallBlock(AbstractBlock.Properties.from(blockMisc.get())));
    public static final RegistryObject<Block> QUARTZ_STAIR = BLOCKS.register("quartz_stair", () -> new StairsBlock(() -> blockMisc.get().getDefaultState(), AbstractBlock.Properties.from(blockMisc.get())));
    public static final RegistryObject<Block> CHISELED_QUARTZ_STAIR = BLOCKS.register("chiseled_quartz_stair", () -> new StairsBlock(() -> blockMisc.get().getDefaultState(), AbstractBlock.Properties.from(blockMisc.get())));
    public static final RegistryObject<Block> PILLAR_QUARTZ_STAIR = BLOCKS.register("pillar_quartz_stair", () -> new StairsBlock(() -> blockMisc.get().getDefaultState(), AbstractBlock.Properties.from(blockMisc.get())));
    public static final RegistryObject<Block> QUARTZ_SLAB = BLOCKS.register("quartz_slab", () -> new SlabBlock(AbstractBlock.Properties.from(blockMisc.get())));
    public static final RegistryObject<Block> CHISELED_QUARTZ_SLAB = BLOCKS.register("chiseled_quartz_slab", () -> new SlabBlock(AbstractBlock.Properties.from(blockMisc.get())));
    public static final RegistryObject<Block> PILLAR_QUARTZ_SLAB = BLOCKS.register("pillar_quartz_slab", () -> new SlabBlock(AbstractBlock.Properties.from(blockMisc.get())));

    public static AbstractBlock.Properties defaultPickProps(int harvestLevel, float hardness, float resistance) {
        return AbstractBlock.Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE);
    }

    public static AbstractBlock.Properties defaultPickProps(int harvestLevel) {
        return AbstractBlock.Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 10.0F).sound(SoundType.STONE);
    }
}
