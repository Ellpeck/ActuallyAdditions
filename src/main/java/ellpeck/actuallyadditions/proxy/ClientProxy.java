/*
 * This file ("ClientProxy.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.proxy;


import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.VillagerRegistry;
import ellpeck.actuallyadditions.blocks.render.RenderInventory;
import ellpeck.actuallyadditions.blocks.render.RenderLaserRelay;
import ellpeck.actuallyadditions.blocks.render.RenderSmileyCloud;
import ellpeck.actuallyadditions.blocks.render.RenderTileEntity;
import ellpeck.actuallyadditions.blocks.render.model.*;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.event.InitEvents;
import ellpeck.actuallyadditions.misc.special.SpecialRenderInit;
import ellpeck.actuallyadditions.tile.*;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.playerdata.PersistentClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.util.Calendar;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy{

    public static boolean pumpkinBlurPumpkinBlur;
    public static boolean jingleAllTheWay;
    public static boolean bulletForMyValentine;

    @Override
    public void preInit(FMLPreInitializationEvent event){
        ModUtil.LOGGER.info("PreInitializing ClientProxy...");

        if(ConfigBoolValues.ENABLE_SEASONAL.isEnabled()){
            Calendar c = Calendar.getInstance();
            pumpkinBlurPumpkinBlur = c.get(Calendar.MONTH) == Calendar.OCTOBER;
            jingleAllTheWay = c.get(Calendar.MONTH) == Calendar.DECEMBER && c.get(Calendar.DAY_OF_MONTH) >= 6 && c.get(Calendar.DAY_OF_MONTH) <= 26;
            bulletForMyValentine = c.get(Calendar.MONTH) == Calendar.FEBRUARY && c.get(Calendar.DAY_OF_MONTH) >= 12 && c.get(Calendar.DAY_OF_MONTH) <= 16;
        }
        else{
            ModUtil.LOGGER.warn("You have turned Seasonal Mode off. Therefore, you are evil.");
        }

        PersistentClientData.setTheFile(new File(Minecraft.getMinecraft().mcDataDir, ModUtil.MOD_ID+"Data.dat"));
    }

    @Override
    public void init(FMLInitializationEvent event){
        ModUtil.LOGGER.info("Initializing ClientProxy...");

        InitEvents.initClient();

        AssetUtil.compostRenderId = RenderingRegistry.getNextAvailableRenderId();
        AssetUtil.fishingNetRenderId = RenderingRegistry.getNextAvailableRenderId();
        AssetUtil.furnaceSolarRenderId = RenderingRegistry.getNextAvailableRenderId();
        AssetUtil.coffeeMachineRenderId = RenderingRegistry.getNextAvailableRenderId();
        AssetUtil.phantomBoosterRenderId = RenderingRegistry.getNextAvailableRenderId();
        AssetUtil.smileyCloudRenderId = RenderingRegistry.getNextAvailableRenderId();
        AssetUtil.laserRelayRenderId = RenderingRegistry.getNextAvailableRenderId();

        registerRenderer(TileEntityCompost.class, new RenderTileEntity(new ModelCompost()), AssetUtil.compostRenderId);
        registerRenderer(TileEntityFishingNet.class, new RenderTileEntity(new ModelFishingNet()), AssetUtil.fishingNetRenderId);
        registerRenderer(TileEntityFurnaceSolar.class, new RenderTileEntity(new ModelFurnaceSolar()), AssetUtil.furnaceSolarRenderId);
        registerRenderer(TileEntityCoffeeMachine.class, new RenderTileEntity(new ModelCoffeeMachine()), AssetUtil.coffeeMachineRenderId);
        registerRenderer(TileEntityPhantomBooster.class, new RenderTileEntity(new ModelPhantomBooster()), AssetUtil.phantomBoosterRenderId);
        registerRenderer(TileEntitySmileyCloud.class, new RenderSmileyCloud(new ModelSmileyCloud()), AssetUtil.smileyCloudRenderId);
        registerRenderer(TileEntityLaserRelay.class, new RenderLaserRelay(new ModelLaserRelay()), AssetUtil.laserRelayRenderId);

        VillagerRegistry.instance().registerVillagerSkin(ConfigIntValues.JAM_VILLAGER_ID.getValue(), new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/entity/villager/jamVillager.png"));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        ModUtil.LOGGER.info("PostInitializing ClientProxy...");

        SpecialRenderInit.init();
    }

    private static void registerRenderer(Class<? extends TileEntity> tileClass, RenderTileEntity tileRender, int renderID){
        ClientRegistry.bindTileEntitySpecialRenderer(tileClass, tileRender);
        RenderingRegistry.registerBlockHandler(new RenderInventory(tileRender, renderID));
    }
}
