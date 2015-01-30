package ellpeck.someprettyrandomstuff;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.crafting.InitCrafting;
import ellpeck.someprettyrandomstuff.event.UpdateEvent;
import ellpeck.someprettyrandomstuff.gen.OreGen;
import ellpeck.someprettyrandomstuff.inventory.GuiHandler;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.proxy.IProxy;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.util.Util;

@Mod(modid = Util.MOD_ID, name = Util.NAME, version = Util.VERSION)
public class SPTS{

    @Instance(Util.MOD_ID)
    public static SPTS instance;

    @SidedProxy(clientSide = "ellpeck.someprettyrandomstuff.proxy.ClientProxy", serverSide = "ellpeck.someprettyrandomstuff.proxy.ServerProxy")
    public static IProxy proxy;

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
        InitCrafting.init();
        GuiHandler.init();
        OreGen.init();
        TileEntityBase.init();
        UpdateEvent.init();
        proxy.init();
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit();
    }
}
