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

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.mod.config.ConfigurationHandler;
import de.ellpeck.actuallyadditions.mod.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.event.CommonEvents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.gen.OreGen;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.items.lens.LensMining;
import de.ellpeck.actuallyadditions.mod.items.lens.LensRecipeHandler;
import de.ellpeck.actuallyadditions.mod.items.lens.Lenses;
import de.ellpeck.actuallyadditions.mod.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.mod.material.InitToolMaterials;
import de.ellpeck.actuallyadditions.mod.misc.BannerHelper;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.MethodHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.proxy.IProxy;
import de.ellpeck.actuallyadditions.mod.recipe.EmpowererHandler;
import de.ellpeck.actuallyadditions.mod.recipe.FuelHandler;
import de.ellpeck.actuallyadditions.mod.recipe.HairyBallHandler;
import de.ellpeck.actuallyadditions.mod.recipe.TreasureChestHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.compat.CompatUtil;
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

@Mod(modid = ModUtil.MOD_ID, name = ModUtil.NAME, version = ModUtil.VERSION, guiFactory = "de.ellpeck.actuallyadditions.mod.config.GuiFactory")
public class ActuallyAdditions{

    @Instance(ModUtil.MOD_ID)
    public static ActuallyAdditions instance;

    @SidedProxy(clientSide = ModUtil.PROXY_CLIENT, serverSide = ModUtil.PROXY_SERVER)
    public static IProxy proxy;

    public static boolean teslaLoaded;
    public static boolean commonCapsLoaded;

    static{
        //For some reason, this has to be done here
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ModUtil.LOGGER.info("Starting PreInitialization Phase...");

        ActuallyAdditionsAPI.methodHandler = new MethodHandler();
        ActuallyAdditionsAPI.connectionHandler = new LaserRelayConnectionHandler();
        Lenses.init();
        InitBooklet.preInit();

        teslaLoaded = Loader.isModLoaded("tesla");
        commonCapsLoaded = Loader.isModLoaded("commoncapabilities");

        MinecraftForge.EVENT_BUS.register(new RegistryHandler());

        new ConfigurationHandler(event.getSuggestedConfigurationFile());
        PacketHandler.init();
        InitToolMaterials.init();
        InitArmorMaterials.init();
        InitFluids.init();
        new UpdateChecker();
        proxy.preInit(event);

        ModUtil.LOGGER.info("PreInitialization Finished.");
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        ModUtil.LOGGER.info("Starting Initialization Phase...");

        BannerHelper.init();
        FuelHandler.init();
        //InitAchievements.init();
        GuiHandler.init();
        new OreGen();
        TileEntityBase.init();
        new CommonEvents();
        InitEntities.init();
        CompatUtil.registerCraftingTweaksCompat();

        proxy.init(event);
        
        RegistryHandler.BLOCKS_TO_REGISTER.clear();

        ModUtil.LOGGER.info("Initialization Finished.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        ModUtil.LOGGER.info("Starting PostInitialization Phase...");

        ItemCoffee.initIngredients();
        CrusherCrafting.init();
        HairyBallHandler.init();
        TreasureChestHandler.init();
        LensRecipeHandler.init();
        EmpowererHandler.init();
        LensMining.init();

        InitBooklet.postInit();
        proxy.postInit(event);

        ModUtil.LOGGER.info("PostInitialization Finished.");
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event){
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(server != null){
            World world = server.getEntityWorld();
            if(world != null && !world.isRemote){
                WorldData.get(world, true).markDirty();
            }
        }
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event){
        WorldData.clear();
    }
}
