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


import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.event.InitEvents;
import de.ellpeck.actuallyadditions.mod.misc.special.SpecialRenderInit;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.playerdata.PersistentClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy{

    private static Map<ItemStack, ResourceLocation> modelLocationsForRegistering = new HashMap<ItemStack, ResourceLocation>();
    private static Map<Item, ResourceLocation[]> modelVariantsForRegistering = new HashMap<Item, ResourceLocation[]>();

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

        for(Map.Entry<Item, ResourceLocation[]> entry : modelVariantsForRegistering.entrySet()){
            ModelBakery.registerItemVariants(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void init(FMLInitializationEvent event){
        ModUtil.LOGGER.info("Initializing ClientProxy...");

        InitEvents.initClient();

        //TODO Fix Tile rendering
        /*ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompost.class, new RenderTileEntity(new ModelCompost()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFishingNet.class, new RenderTileEntity(new ModelFishingNet()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnaceSolar.class, new RenderTileEntity(new ModelFurnaceSolar()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoffeeMachine.class, new RenderTileEntity(new ModelCoffeeMachine()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhantomBooster.class, new RenderTileEntity(new ModelPhantomBooster()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmileyCloud.class, new RenderSmileyCloud(new ModelSmileyCloud()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserRelay.class, new RenderLaserRelay(new ModelLaserRelay()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBookletStand.class, new RenderTileEntity(new ModelBookletStand()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAtomicReconstructor.class, new RenderReconstructorLens());*/

        //TODO Fix villager
        //VillagerRegistry.instance().registerVillagerSkin(ConfigIntValues.JAM_VILLAGER_ID.getValue(), new ResourceLocation(ModUtil.MOD_ID_LOWER, "textures/entity/villager/jamVillager.png"));

        for(Map.Entry<ItemStack, ResourceLocation> entry : modelLocationsForRegistering.entrySet()){
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(entry.getKey().getItem(), entry.getKey().getItemDamage(), new ModelResourceLocation(entry.getValue(), "inventory"));
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        ModUtil.LOGGER.info("PostInitializing ClientProxy...");

        SpecialRenderInit.init();
    }

    @Override
    public void addRenderRegister(ItemStack stack, ResourceLocation location){
        modelLocationsForRegistering.put(stack, location);
    }

    @Override
    public void addRenderVariant(Item item, ResourceLocation... location){
        modelVariantsForRegistering.put(item, location);
    }
}
