package de.ellpeck.actuallyadditions.common;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.common.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.common.config.ConfigurationHandler;
import de.ellpeck.actuallyadditions.common.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.common.data.WorldData;
import de.ellpeck.actuallyadditions.common.entity.InitEntities;
import de.ellpeck.actuallyadditions.common.event.CommonEvents;
import de.ellpeck.actuallyadditions.common.fluids.InitFluids;
import de.ellpeck.actuallyadditions.common.gen.AAWorldGen;
import de.ellpeck.actuallyadditions.common.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.common.items.ItemCoffee;
import de.ellpeck.actuallyadditions.common.items.lens.LensMining;
import de.ellpeck.actuallyadditions.common.items.lens.LensRecipeHandler;
import de.ellpeck.actuallyadditions.common.items.lens.Lenses;
import de.ellpeck.actuallyadditions.common.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.common.material.InitToolMaterials;
import de.ellpeck.actuallyadditions.common.misc.BannerHelper;
import de.ellpeck.actuallyadditions.common.misc.DungeonLoot;
import de.ellpeck.actuallyadditions.common.misc.apiimpl.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.common.misc.apiimpl.MethodHandler;
import de.ellpeck.actuallyadditions.common.network.PacketHandler;
import de.ellpeck.actuallyadditions.common.proxy.IProxy;
import de.ellpeck.actuallyadditions.common.recipe.EmpowererHandler;
import de.ellpeck.actuallyadditions.common.recipe.HairyBallHandler;
import de.ellpeck.actuallyadditions.common.recipe.TreasureChestHandler;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.common.update.UpdateChecker;
import de.ellpeck.actuallyadditions.common.util.compat.CompatUtil;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ActuallyAdditions.MODID)
public class ActuallyAdditions {

    public static final String MODID = ActuallyAdditionsAPI.MOD_ID;
    public static final String NAME = "Actually Additions";
//    public static final String VERSION = "@VERSION@";
//    public static final String GUIFACTORY = "de.ellpeck.actuallyadditions.common.config.GuiFactory";
//    public static final String DEPS = "required:forge@[14.23.5.2836,);before:craftingtweaks;after:fastbench@[1.3.2,)";
//    public static final boolean DEOBF = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static ActuallyAdditions INSTANCE;

    public static final IForgeRegistry<Lens> LENS_REGISTRY = new RegistryBuilder<Lens>().disableSync().disableSaving().disableOverrides().create();
    
    public static boolean commonCapsLoaded = false;

    // Creative Tab
    public static ItemGroup aaGroup = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.PACKED_ICE); // placeholder until I've done the actual stuff @todo
        }
    };

    public ActuallyAdditions() {
        INSTANCE = this;

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register registers
        InitBlocks.BLOCKS.register(bus);
        // items
        Lenses.LENSES.register(bus);

        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public void serverStarted(FMLServerStartedEvent event) {
// todo: come back to this if it's still needed after migrating the world save to a newer version

//        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
//        if (server != null) {
//            World world = server.getEntityWorld();
//            if (world != null && !world.isRemote) {
//                WorldData.get(world, true).markDirty();
//            }
//        }
    }

    @SubscribeEvent
    public void serverStopped(FMLServerStoppedEvent event) {
        WorldData.clear();
    }


    @SidedProxy(clientSide = "de.ellpeck.actuallyadditions.common.proxy.ClientProxy", serverSide = "de.ellpeck.actuallyadditions.common.proxy.ServerProxy")
    public static IProxy PROXY;

    static {
        FluidRegistry.enableUniversalBucket();
    }


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ActuallyAdditions.LOGGER.info("Starting PreInitialization Phase...");

        ActuallyAdditionsAPI.methodHandler = new MethodHandler();
        ActuallyAdditionsAPI.connectionHandler = new LaserRelayConnectionHandler();
        InitBooklet.preInit();
        CompatUtil.registerCraftingTweaks();

//        commonCapsLoaded = Loader.isModLoaded("commoncapabilities");
//        MinecraftForge.EVENT_BUS.register(new RegistryHandler());

        new ConfigurationHandler(event.getSuggestedConfigurationFile());
        PacketHandler.init();
        InitToolMaterials.init();
        InitArmorMaterials.init();
        InitFluids.init();
        new UpdateChecker();
        PROXY.preInit(event);

        ActuallyAdditions.LOGGER.info("PreInitialization Finished.");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ActuallyAdditions.LOGGER.info("Starting Initialization Phase...");

        BannerHelper.init();
        //InitAchievements.init();
        GuiHandler.init();
        AAWorldGen gen = new AAWorldGen();
        GameRegistry.registerWorldGenerator(gen, 10000);
        MinecraftForge.TERRAIN_GEN_BUS.register(gen);
        TileEntityBase.init();
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        MinecraftForge.EVENT_BUS.register(new DungeonLoot());
        InitEntities.init();

        PROXY.init(event);

        RegistryHandler.BLOCKS_TO_REGISTER.clear();

        ActuallyAdditions.LOGGER.info("Initialization Finished.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ActuallyAdditions.LOGGER.info("Starting PostInitialization Phase...");

        ItemCoffee.initIngredients();
        CrusherCrafting.init();
        HairyBallHandler.init();
        TreasureChestHandler.init();
        LensRecipeHandler.init();
        EmpowererHandler.init();
        LensMining.init();

        InitBooklet.postInit();
        PROXY.postInit(event);

        ConfigurationHandler.redefineConfigs();

        ActuallyAdditions.LOGGER.info("PostInitialization Finished.");
    }
}
