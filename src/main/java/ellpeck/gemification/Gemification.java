package ellpeck.gemification;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ellpeck.gemification.blocks.InitBlocks;
import ellpeck.gemification.booklet.ChapterList;
import ellpeck.gemification.crafting.InitCrafting;
import ellpeck.gemification.gen.OreGen;
import ellpeck.gemification.inventory.GuiHandler;
import ellpeck.gemification.items.InitItems;
import ellpeck.gemification.proxy.IProxy;
import ellpeck.gemification.tile.TileEntityBase;
import ellpeck.gemification.util.Util;

@Mod(modid = Util.MOD_ID, name = Util.NAME, version = Util.VERSION)
public class Gemification{

    @Instance(Util.MOD_ID)
    public static Gemification instance;

    @SidedProxy(clientSide = "ellpeck.gemification.proxy.ClientProxy", serverSide = "ellpeck.gemification.proxy.ServerProxy")
    public static IProxy proxy;

    @SuppressWarnings("unused")
    @EventHandler()
    public void preInit(FMLPreInitializationEvent event){
        ChapterList.init();
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
        proxy.init();
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit();
    }
}
