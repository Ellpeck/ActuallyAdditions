/*
 * This file ("ActuallyAdditions.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.mod.config.ConfigurationHandler;
import de.ellpeck.actuallyadditions.mod.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.event.CommonEvents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.gen.AAWorldGen;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.items.lens.LensMining;
import de.ellpeck.actuallyadditions.mod.items.lens.LensRecipeHandler;
import de.ellpeck.actuallyadditions.mod.items.lens.Lenses;
import de.ellpeck.actuallyadditions.mod.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.mod.material.InitToolMaterials;
import de.ellpeck.actuallyadditions.mod.misc.BannerHelper;
import de.ellpeck.actuallyadditions.mod.misc.DungeonLoot;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.MethodHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.proxy.IProxy;
import de.ellpeck.actuallyadditions.mod.recipe.EmpowererHandler;
import de.ellpeck.actuallyadditions.mod.recipe.HairyBallHandler;
import de.ellpeck.actuallyadditions.mod.recipe.TreasureChestHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.compat.CompatUtil;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ActuallyAdditions.MODID, name = ActuallyAdditions.NAME, version = ActuallyAdditions.VERSION, guiFactory = ActuallyAdditions.GUIFACTORY, dependencies = ActuallyAdditions.DEPS)
public class ActuallyAdditions {

    public static final String MODID = ActuallyAdditionsAPI.MOD_ID;
    public static final String NAME = "Actually Additions";
    public static final String VERSION = "@VERSION@";
    public static final String GUIFACTORY = "de.ellpeck.actuallyadditions.mod.config.GuiFactory";
    public static final String DEPS = "required:forge@[14.23.5.2836,);before:craftingtweaks;after:fastbench@[1.3.2,)";
    public static final boolean DEOBF = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    @Instance
    public static ActuallyAdditions INSTANCE;

    @SidedProxy(clientSide = "de.ellpeck.actuallyadditions.mod.proxy.ClientProxy", serverSide = "de.ellpeck.actuallyadditions.mod.proxy.ServerProxy")
    public static IProxy PROXY;

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public static boolean commonCapsLoaded;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ActuallyAdditions.LOGGER.info("Starting PreInitialization Phase...");

        ActuallyAdditionsAPI.methodHandler = new MethodHandler();
        ActuallyAdditionsAPI.connectionHandler = new LaserRelayConnectionHandler();
        Lenses.init();
        InitBooklet.preInit();
        CompatUtil.registerCraftingTweaks();
        commonCapsLoaded = Loader.isModLoaded("commoncapabilities");

        MinecraftForge.EVENT_BUS.register(new RegistryHandler());

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

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server != null) {
            World world = server.getEntityWorld();
            if (world != null && !world.isRemote) {
                WorldData.get(world, true).markDirty();
            }
        }
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        WorldData.clear();
    }
}
