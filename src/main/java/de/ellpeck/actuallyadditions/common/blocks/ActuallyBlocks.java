package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.building.CrystalBlock;
import de.ellpeck.actuallyadditions.common.blocks.building.CrystalClusterBlock;
import de.ellpeck.actuallyadditions.common.blocks.building.WallBlock;
import de.ellpeck.actuallyadditions.common.blocks.functional.*;
import de.ellpeck.actuallyadditions.common.blocks.misc.TinyTorchBlock;
import de.ellpeck.actuallyadditions.common.blocks.plant.PlantBlock;
import de.ellpeck.actuallyadditions.common.blocks.types.Crystals;
import de.ellpeck.actuallyadditions.common.blocks.types.LaserRelays;
import de.ellpeck.actuallyadditions.common.blocks.types.PhantomType;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ActuallyBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ActuallyAdditions.MOD_ID);

    // Plant!
    public static final RegistryObject<Block> RICE = BLOCKS.register("rice_block", () -> new PlantBlock(1, 2));
    public static final RegistryObject<Block> CANOLA = BLOCKS.register("canola_block", () -> new PlantBlock(2, 3));
    public static final RegistryObject<Block> FLAX = BLOCKS.register("flax_block", () -> new PlantBlock(2, 4));
    public static final RegistryObject<Block> COFFEE = BLOCKS.register("coffee_block", () -> new PlantBlock(2, 2));

    // TILE BLOCKS
    public static final RegistryObject<Block> BATTERY_BOX = BLOCKS.register("battery_box_block", BatteryBoxBlock::new);
    public static final RegistryObject<Block> HOPPING_ITEM_INTERFACE = BLOCKS.register("hopping_item_interface_block", ItemViewerHoppingBlock::new);
    public static final RegistryObject<Block> FARMER = BLOCKS.register("farmer_block", FarmerBlock::new);
    public static final RegistryObject<Block> BIO_REACTOR = BLOCKS.register("bio_reactor_block", BioReactorBlock::new);
    public static final RegistryObject<Block> EMPOWERER = BLOCKS.register("empowerer_block", EmpowererBlock::new);
    public static final RegistryObject<Block> TINY_TORCH = BLOCKS.register("tiny_torch_block", TinyTorchBlock::new);
    public static final RegistryObject<Block> SHOCK_SUPPRESSOR = BLOCKS.register("shock_suppressor_block", ShockSuppressorBlock::new);
    public static final RegistryObject<Block> DISPLAY_STAND = BLOCKS.register("display_stand_block", DisplayStandBlock::new);
    public static final RegistryObject<Block> PLAYER_INTERFACE = BLOCKS.register("player_interface_block", PlayerInterfaceBlock::new);
    public static final RegistryObject<Block> ITEM_INTERFACE = BLOCKS.register("item_interface_block", ItemViewerBlock::new);
    public static final RegistryObject<Block> FIREWORK_BOX = BLOCKS.register("firework_box_block", FireworkBoxBlock::new);
    public static final RegistryObject<Block> MINER = BLOCKS.register("miner_block", MinerBlock::new);
    public static final RegistryObject<Block> ATOMIC_RECONSTRUCTOR = BLOCKS.register("atomic_reconstructor_block", AtomicReconstructorBlock::new);
    public static final RegistryObject<Block> TREASURE_CHEST = BLOCKS.register("treasure_chest_block", TreasureChestBlock::new);
    public static final RegistryObject<Block> ENERGIZER = BLOCKS.register("energizer_block", () -> new EnergizerBlock(true));
    public static final RegistryObject<Block> ENERVATOR = BLOCKS.register("enervator_block", () -> new EnergizerBlock(false));
    public static final RegistryObject<Block> LAVA_FACTORY_CONTROLLER = BLOCKS.register("lava_factory_controller_block", LavaFactoryControllerBlock::new);
    public static final RegistryObject<Block> CANOLA_PRESS = BLOCKS.register("canola_press_block", CanolaPressBlock::new);
    public static final RegistryObject<Block> PHANTOMFACE = BLOCKS.register("phantomface_block", () -> new PhantomBlock(PhantomType.FACE));
    public static final RegistryObject<Block> PHANTOM_PLACER = BLOCKS.register("phantom_placer_block", () -> new PhantomBlock(PhantomType.PLACER));
    public static final RegistryObject<Block> PHANTOM_LIQUIFACE = BLOCKS.register("phantom_liquiface_block", () -> new PhantomBlock(PhantomType.LIQUIFACE));
    public static final RegistryObject<Block> PHANTOM_ENERGYFACE = BLOCKS.register("phantom_energyface_block", () -> new PhantomBlock(PhantomType.ENERGYFACE));
    public static final RegistryObject<Block> PHANTOM_REDSTONEFACE = BLOCKS.register("phantom_redstoneface_block", () -> new PhantomBlock(PhantomType.REDSTONEFACE));
    public static final RegistryObject<Block> PHANTOM_BREAKER = BLOCKS.register("phantom_breaker_block", () -> new PhantomBlock(PhantomType.BREAKER));
    public static final RegistryObject<Block> COAL_GENERATOR = BLOCKS.register("coal_generator_block", CoalGeneratorBlock::new);
    public static final RegistryObject<Block> OIL_GENERATOR = BLOCKS.register("oil_generator_block", OilGeneratorBlock::new);
    public static final RegistryObject<Block> FERMENTING_BARREL = BLOCKS.register("fermenting_barrel_block", FermentingBarrelBlock::new);
    public static final RegistryObject<Block> FEEDER = BLOCKS.register("feeder_block", FeederBlock::new);
    public static final RegistryObject<Block> CRUSHER = BLOCKS.register("crusher_block", () -> new GrinderBlock(false));
    public static final RegistryObject<Block> CRUSHER_DOUBLE = BLOCKS.register("crusher_double_block", () -> new GrinderBlock(true));
    public static final RegistryObject<Block> POWERED_FURNACE = BLOCKS.register("powered_furnace_block", FurnaceDoubleBlock::new);
    public static final RegistryObject<Block> ESD = BLOCKS.register("esd_block", () -> new InputterBlock(false));
    public static final RegistryObject<Block> ESD_ADVANCED = BLOCKS.register("esd_advanced_block", () -> new InputterBlock(true));
    public static final RegistryObject<Block> FISHING_NET = BLOCKS.register("fishing_net_block", FishingNetBlock::new);
    public static final RegistryObject<Block> SOLAR_PANEL = BLOCKS.register("solar_panel_block", FurnaceSolarBlock::new);
    public static final RegistryObject<Block> HEAT_COLLECTOR = BLOCKS.register("heat_collector_block", HeatCollectorBlock::new);
    public static final RegistryObject<Block> ITEM_REPAIRER = BLOCKS.register("item_repairer_block", ItemRepairerBlock::new);
    public static final RegistryObject<Block> GREENHOUSE_GLASS = BLOCKS.register("greenhouse_glass_block", GreenhouseGlassBlock::new);
    public static final RegistryObject<Block> BREAKER = BLOCKS.register("breaker_block", () -> new BreakerBlock(false));
    public static final RegistryObject<Block> PLACER = BLOCKS.register("placer_block", () -> new BreakerBlock(true));
    public static final RegistryObject<Block> DROPPER = BLOCKS.register("dropper_block", () -> new DropperBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> FLUID_PLACER = BLOCKS.register("fluid_placer_block", () -> new FluidCollectorBlock(true));
    public static final RegistryObject<Block> FLUID_COLLECTOR = BLOCKS.register("fluid_collector_block", () -> new FluidCollectorBlock(false));
    public static final RegistryObject<Block> COFFEE_MACHINE = BLOCKS.register("coffee_machine_block", CoffeeMachineBlock::new);
    public static final RegistryObject<Block> PHANTOM_BOOSTER = BLOCKS.register("phantom_booster_block", PhantomBoosterBlock::new);
    public static final RegistryObject<Block> RANGED_COLLECTOR = BLOCKS.register("ranged_collector_block", RangedCollectorBlock::new);
    public static final RegistryObject<Block> DIRECTIONAL_BREAKER = BLOCKS.register("directional_breaker_block", DirectionalBreakerBlock::new);
    public static final RegistryObject<Block> LEAF_GENERATOR = BLOCKS.register("leaf_generator_block", LeafGeneratorBlock::new);
    public static final RegistryObject<Block> SMILEY_CLOUD = BLOCKS.register("smiley_cloud_block", SmileyCloudBlock::new);
    public static final RegistryObject<Block> XP_SOLIDIFIER = BLOCKS.register("xp_solidifier_block", XPSolidifierBlock::new);

    // CRYSTALS
    public static final RegistryObject<Block> CRYSTAL_ENORI = BLOCKS.register("crystal_enori_block", () -> new CrystalBlock(false));
    public static final RegistryObject<Block> CRYSTAL_RESTONIA = BLOCKS.register("crystal_restonia_block", () -> new CrystalBlock(false));
    public static final RegistryObject<Block> CRYSTAL_PALIS = BLOCKS.register("crystal_palis_block", () -> new CrystalBlock(false));
    public static final RegistryObject<Block> CRYSTAL_DIAMATINE = BLOCKS.register("crystal_diamatine_block", () -> new CrystalBlock(false));
    public static final RegistryObject<Block> CRYSTAL_VOID = BLOCKS.register("crystal_void_block", () -> new CrystalBlock(false));
    public static final RegistryObject<Block> CRYSTAL_EMERADIC = BLOCKS.register("crystal_emeradic_block", () -> new CrystalBlock(false));
    
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_ENORI = BLOCKS.register("crystal_enori_empowered_block", () -> new CrystalBlock(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_RESTONIA = BLOCKS.register("crystal_restonia_empowered_block", () -> new CrystalBlock(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_PALIS = BLOCKS.register("crystal_palis_empowered_block", () -> new CrystalBlock(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_DIAMATINE = BLOCKS.register("crystal_diamatine_empowered_block", () -> new CrystalBlock(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_VOID = BLOCKS.register("crystal_void_empowered_block", () -> new CrystalBlock(true));
    public static final RegistryObject<Block> CRYSTAL_EMPOWERED_EMERADIC = BLOCKS.register("crystal_emeradic_empowered_block", () -> new CrystalBlock(true));

    public static final RegistryObject<Block> CRYSTAL_CLUSTER_RESTONIA = BLOCKS.register("crystal_cluster_restonia_block", () -> new CrystalClusterBlock(Crystals.REDSTONE));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_PALIS = BLOCKS.register("crystal_cluster_palis_block", () -> new CrystalClusterBlock(Crystals.LAPIS));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_DIAMATINE = BLOCKS.register("crystal_cluster_diamatine_block", () -> new CrystalClusterBlock(Crystals.DIAMOND));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_VOID = BLOCKS.register("crystal_cluster_void_block", () -> new CrystalClusterBlock(Crystals.COAL));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_EMERADIC = BLOCKS.register("crystal_cluster_emeradic_block", () -> new CrystalClusterBlock(Crystals.EMERALD));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_ENORI = BLOCKS.register("crystal_cluster_enori_block", () -> new CrystalClusterBlock(Crystals.IRON));
    
    // LASERS
    public static final RegistryObject<Block> ENERGY_LASER_RELAY = BLOCKS.register("energy_laser_relay_block", () -> new LaserRelayBlock(LaserRelays.ENERGY_BASIC));
    public static final RegistryObject<Block> ENERGY_LASER_RELAY_ADVANCED = BLOCKS.register("energy_laser_relay_advanced_block", () -> new LaserRelayBlock(LaserRelays.ENERGY_ADVANCED));
    public static final RegistryObject<Block> ENERGY_LASER_RELAY_EXTREME = BLOCKS.register("energy_laser_relay_extreme_block", () -> new LaserRelayBlock(LaserRelays.ENERGY_EXTREME));
    public static final RegistryObject<Block> FLUIDS_LASER_RELAY = BLOCKS.register("fluids_laser_relay_block", () -> new LaserRelayBlock(LaserRelays.FLUIDS));
    public static final RegistryObject<Block> ITEM_LASER_RELAY = BLOCKS.register("item_laser_relay_block", () -> new LaserRelayBlock(LaserRelays.ITEM));
    public static final RegistryObject<Block> ADVANCED_ITEM_LASER_RELAY = BLOCKS.register("advanced_item_laser_relay_block", () -> new LaserRelayBlock(LaserRelays.ITEM_WHITELIST));

    // BUILDING BLOCKS
    public static final RegistryObject<Block> GREEN_BLOCK = BLOCKS.register("green_block", GenericBlock::new);
    public static final RegistryObject<Block> WHITE_BLOCK = BLOCKS.register("white_block", GenericBlock::new);
    public static final RegistryObject<Block> GREEN_STAIRS = BLOCKS.register("green_stairs_block", () -> new StairsBlock(() -> GREEN_BLOCK.get().getDefaultState(), Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> WHITE_STAIRS = BLOCKS.register("white_stairs_block", () -> new StairsBlock(() -> WHITE_BLOCK.get().getDefaultState(), Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> GREEN_SLAB = BLOCKS.register("green_slab_block", () -> new SlabBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> WHITE_SLAB = BLOCKS.register("white_slab_block", () -> new SlabBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> GREEN_WALL = BLOCKS.register("green_wall_block", () -> new WallBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> WHITE_WALL = BLOCKS.register("white_wall_block", () -> new WallBlock(Block.Properties.create(Material.ROCK)));

    public static final RegistryObject<Block> BLACK_QUARTZ = BLOCKS.register("black_quartz_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BLACK_QUARTZ_CHISELED = BLOCKS.register("black_quartz_chiseled_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BLACK_QUARTZ_PILLAR = BLOCKS.register("black_quartz_pillar_block", () -> new Block(Block.Properties.create(Material.ROCK)));

    public static final RegistryObject<Block> BLACK_QUARTZ_WALL = BLOCKS.register("black_quartz_wall_block", () -> new WallBlock(AbstractBlock.Properties.from(BLACK_QUARTZ.get())));
    public static final RegistryObject<Block> BLACK_CHISELED_QUARTZ_WALL = BLOCKS.register("black_chiseled_quartz_wall_block", () -> new WallBlock(AbstractBlock.Properties.from(BLACK_QUARTZ_CHISELED.get())));
    public static final RegistryObject<Block> BLACK_PILLAR_QUARTZ_WALL = BLOCKS.register("black_pillar_quartz_wall_block", () -> new WallBlock(AbstractBlock.Properties.from(BLACK_QUARTZ_PILLAR.get())));
    public static final RegistryObject<Block> BLACK_QUARTZ_STAIR = BLOCKS.register("black_quartz_stair_block", () -> new StairsBlock(() -> BLACK_QUARTZ.get().getDefaultState(), Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BLACK_CHISELED_QUARTZ_STAIR = BLOCKS.register("black_chiseled_quartz_stair_block", () -> new StairsBlock(() -> BLACK_QUARTZ.get().getDefaultState(), Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BLACK_PILLAR_QUARTZ_STAIR = BLOCKS.register("black_pillar_quartz_stair_block", () -> new StairsBlock(() -> BLACK_QUARTZ.get().getDefaultState(), Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BLACK_QUARTZ_SLAB = BLOCKS.register("black_quartz_slab_block", () -> new SlabBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BLACK_CHISELED_QUARTZ_SLAB = BLOCKS.register("black_chiseled_quartz_slab_block", () -> new SlabBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BLACK_PILLAR_QUARTZ_SLAB = BLOCKS.register("black_pillar_quartz_slab_block", () -> new SlabBlock(Block.Properties.create(Material.ROCK)));

    // LAMPS! SO MANY LAMPS
    public static final RegistryObject<Block> LAMP_WHITE = BLOCKS.register("lamp_white_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_ORANGE = BLOCKS.register("lamp_orange_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_MAGENTA = BLOCKS.register("lamp_magenta_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_LIGHT_BLUE = BLOCKS.register("lamp_light_blue_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_YELLOW = BLOCKS.register("lamp_yellow_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_LIME = BLOCKS.register("lamp_lime_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_PINK = BLOCKS.register("lamp_pink_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_GRAY = BLOCKS.register("lamp_gray_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_LIGHT_GRAY = BLOCKS.register("lamp_light_gray_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_CYAN = BLOCKS.register("lamp_cyan_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_PURPLE = BLOCKS.register("lamp_purple_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_BLUE = BLOCKS.register("lamp_blue_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_BROWN = BLOCKS.register("lamp_brown_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_GREEN = BLOCKS.register("lamp_green_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_RED = BLOCKS.register("lamp_red_block", ColoredLampBlock::new);
    public static final RegistryObject<Block> LAMP_BLACK = BLOCKS.register("lamp_black_block", ColoredLampBlock::new);

    public static final RegistryObject<Block> LAMP_CONTROLLER = BLOCKS.register("lamp_controller_block", LampPowererBlock::new);

    // MISC BLOCKS
    public static final RegistryObject<Block> ENDERPEARL = BLOCKS.register("enderpearl_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> CHARCOAL = BLOCKS.register("charcoal_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> ORE_BLACK_QUARTZ = BLOCKS.register("ore_black_quartz_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> ENDER_CASING = BLOCKS.register("ender_casing_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> IRON_CASING = BLOCKS.register("iron_casing_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> IRON_CASING_SNOW = BLOCKS.register("iron_casing_snow_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> LAVA_FACTORY_CASE = BLOCKS.register("lava_factory_case_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> WOOD_CASING = BLOCKS.register("wood_casing_block", () -> new Block(Block.Properties.create(Material.ROCK)));

// I don't know what this is :cry:
//    public static final RegistryObject<Block> WildPlant
//            = BLOCKS.register("wild_block", WildPlantBlock::new);

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        for(RegistryObject<Block> entry : BLOCKS.getEntries()) {
            if (!(entry.get() instanceof IActuallyBlock)) {
                continue;
            }

            event.getRegistry().register(
                    ((IActuallyBlock) entry.get())
                            .createBlockItem()
                            .setRegistryName(Objects.requireNonNull(entry.get().getRegistryName()).getPath())
            );
        }
    }
}
