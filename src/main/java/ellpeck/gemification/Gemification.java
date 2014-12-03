package ellpeck.gemification;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.gemification.blocks.InitBlocks;
import ellpeck.gemification.container.GuiHandler;
import ellpeck.gemification.crafting.CrucibleCraftingManager;
import ellpeck.gemification.items.InitItems;
import ellpeck.gemification.proxy.IProxy;
import ellpeck.gemification.tile.TileEntityCrucible;

@Mod(modid = Gemification.MOD_ID, name = Gemification.NAME, version = Gemification.VERSION)
public class Gemification {

    @Instance(Gemification.MOD_ID)
    public static Gemification instance;

    @SidedProxy(clientSide = "ellpeck.gemification.proxy.ClientProxy", serverSide = "ellpeck.gemification.proxy.ServerProxy")
    public static IProxy proxy;

    public static final String MOD_ID = "gemification";
    public static final String NAME = "Gemification";
    public static final String VERSION = "1.7.10-1.0.1";

    public static final int guiCrucible = 0;

    @SuppressWarnings("unused")
    @EventHandler()
    public void preInit(FMLPreInitializationEvent event){
        InitBlocks.init();
        InitItems.init();
        proxy.preInit();
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void init(FMLInitializationEvent event){
        CrucibleCraftingManager.instance.initRecipes();
        proxy.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        GameRegistry.registerWorldGenerator(new OreGen(), 0);
        GameRegistry.registerTileEntity(TileEntityCrucible.class, Gemification.MOD_ID + "tileEntityCrucible");
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit();
    }
}
