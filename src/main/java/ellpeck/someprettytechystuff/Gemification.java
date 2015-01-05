package ellpeck.someprettytechystuff;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ellpeck.someprettytechystuff.blocks.InitBlocks;
import ellpeck.someprettytechystuff.booklet.ChapterList;
import ellpeck.someprettytechystuff.crafting.InitCrafting;
import ellpeck.someprettytechystuff.gen.OreGen;
import ellpeck.someprettytechystuff.inventory.GuiHandler;
import ellpeck.someprettytechystuff.items.InitItems;
import ellpeck.someprettytechystuff.proxy.IProxy;
import ellpeck.someprettytechystuff.tile.TileEntityBase;
import ellpeck.someprettytechystuff.util.Util;

@Mod(modid = Util.MOD_ID, name = Util.NAME, version = Util.VERSION)
public class Gemification{

    @Instance(Util.MOD_ID)
    public static Gemification instance;

    @SidedProxy(clientSide = "ellpeck.someprettytechystuff.proxy.ClientProxy", serverSide = "ellpeck.someprettytechystuff.proxy.ServerProxy")
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
