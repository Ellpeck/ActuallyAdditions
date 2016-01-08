/*
 * This file ("ClientProxy.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.proxy;


import de.ellpeck.actuallyadditions.mod.blocks.render.RenderLaserRelay;
import de.ellpeck.actuallyadditions.mod.blocks.render.RenderReconstructorLens;
import de.ellpeck.actuallyadditions.mod.blocks.render.RenderSmileyCloud;
import de.ellpeck.actuallyadditions.mod.blocks.render.RenderTileEntity;
import de.ellpeck.actuallyadditions.mod.blocks.render.model.*;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.event.InitEvents;
import de.ellpeck.actuallyadditions.mod.misc.special.SpecialRenderInit;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.playerdata.PersistentClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

        registerRenderer(TileEntityCompost.class, new RenderTileEntity(new ModelCompost()), AssetUtil.compostRenderId);
        registerRenderer(TileEntityFishingNet.class, new RenderTileEntity(new ModelFishingNet()), AssetUtil.fishingNetRenderId);
        registerRenderer(TileEntityFurnaceSolar.class, new RenderTileEntity(new ModelFurnaceSolar()), AssetUtil.furnaceSolarRenderId);
        registerRenderer(TileEntityCoffeeMachine.class, new RenderTileEntity(new ModelCoffeeMachine()), AssetUtil.coffeeMachineRenderId);
        registerRenderer(TileEntityPhantomBooster.class, new RenderTileEntity(new ModelPhantomBooster()), AssetUtil.phantomBoosterRenderId);
        registerRenderer(TileEntitySmileyCloud.class, new RenderSmileyCloud(new ModelSmileyCloud()), AssetUtil.smileyCloudRenderId);
        registerRenderer(TileEntityLaserRelay.class, new RenderLaserRelay(new ModelLaserRelay()), AssetUtil.laserRelayRenderId);
        registerRenderer(TileEntityBookletStand.class, new RenderTileEntity(new ModelBookletStand()), AssetUtil.bookletStandRenderId);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAtomicReconstructor.class, new RenderReconstructorLens());

        //TODO Fix villager
        //VillagerRegistry.instance().registerVillagerSkin(ConfigIntValues.JAM_VILLAGER_ID.getValue(), new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/entity/villager/jamVillager.png"));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        ModUtil.LOGGER.info("PostInitializing ClientProxy...");

        SpecialRenderInit.init();
    }

    private static void registerRenderer(Class<? extends TileEntity> tileClass, RenderTileEntity tileRender, int renderID){
        ClientRegistry.bindTileEntitySpecialRenderer(tileClass, tileRender);
        //TODO Fix inventory rendering
        //RenderingRegistry.registerBlockHandler(new RenderInventory(tileRender, renderID));
    }
}
