/*
 * This file ("ServerProxy.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

@SuppressWarnings("unused")
public class ServerProxy implements IProxy{

    @Override
    public void preInit(FMLPreInitializationEvent event){
        ModUtil.LOGGER.info("PreInitializing ServerProxy...");
    }

    @Override
    public void init(FMLInitializationEvent event){
        ModUtil.LOGGER.info("Initializing ServerProxy...");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        ModUtil.LOGGER.info("PostInitializing ServerProxy...");
    }

    @Override
    public World getWorld(int worldID){
        return DimensionManager.getWorld(worldID);
    }
}
