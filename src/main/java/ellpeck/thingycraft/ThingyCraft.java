package ellpeck.thingycraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.thingycraft.blocks.InitBlocks;
import ellpeck.thingycraft.container.GuiHandler;
import ellpeck.thingycraft.crafting.CrucibleCraftingManager;
import ellpeck.thingycraft.items.InitItems;
import ellpeck.thingycraft.proxy.IProxy;
import ellpeck.thingycraft.tile.TileEntityCrucible;

@Mod(modid = ThingyCraft.MOD_ID, name = ThingyCraft.NAME, version = ThingyCraft.VERSION)
public class ThingyCraft {

    @Instance(ThingyCraft.MOD_ID)
    public static ThingyCraft instance;

    @SidedProxy(clientSide = "ellpeck.thingycraft.proxy.ClientProxy", serverSide = "ellpeck.thingycraft.proxy.ServerProxy")
    public static IProxy proxy;

    public static final String MOD_ID = "thingycraft";
    public static final String NAME = "ThingyCraft";
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
        GameRegistry.registerTileEntity(TileEntityCrucible.class, ThingyCraft.MOD_ID + "tileEntityCrucible");
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit();
    }
}
