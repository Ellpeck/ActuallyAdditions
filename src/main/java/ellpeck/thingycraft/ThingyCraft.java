package ellpeck.thingycraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.thingycraft.blocks.InitBlocks;
import ellpeck.thingycraft.items.InitItems;

@Mod(modid = ThingyCraft.MOD_ID, name = ThingyCraft.NAME, version = ThingyCraft.VERSION)
public class ThingyCraft {

    @Instance(ThingyCraft.MOD_ID)
    public static ThingyCraft instance;

    public static final String MOD_ID = "thingycraft";
    public static final String NAME = "ThingyCraft";
    public static final String VERSION = "1.7.10-1.0";

    @SuppressWarnings("unused")
    @EventHandler()
    public void preInit(FMLPreInitializationEvent event){
        InitBlocks.init();
        InitItems.init();
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void init(FMLInitializationEvent event){
        GameRegistry.registerWorldGenerator(new OreGen(), 0);
    }

    @SuppressWarnings("unused")
    @EventHandler()
    public void postInit(FMLPostInitializationEvent event){

    }
}
