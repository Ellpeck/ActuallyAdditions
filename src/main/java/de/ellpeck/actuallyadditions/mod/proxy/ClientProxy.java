/*
 * This file ("ClientProxy.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.proxy;


import de.ellpeck.actuallyadditions.mod.blocks.render.*;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.event.ClientEvents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.misc.special.SpecialRenderInit;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.FluidStateMapper;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingBlock;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientProxy implements IProxy{

    private static final List<Item> COLOR_PRODIVIDING_ITEMS_FOR_REGISTERING = new ArrayList<Item>();
    private static final List<Block> COLOR_PRODIVIDING_BLOCKS_FOR_REGISTERING = new ArrayList<Block>();
    private static final Map<ItemStack, ModelResourceLocation> MODEL_LOCATIONS_FOR_REGISTERING = new HashMap<ItemStack, ModelResourceLocation>();

    @Override
    public void preInit(FMLPreInitializationEvent event){
        ModUtil.LOGGER.info("PreInitializing ClientProxy...");

        for(Map.Entry<ItemStack, ModelResourceLocation> entry : MODEL_LOCATIONS_FOR_REGISTERING.entrySet()){
            ModelLoader.setCustomModelResourceLocation(entry.getKey().getItem(), entry.getKey().getItemDamage(), entry.getValue());
        }

        this.registerCustomFluidBlockRenderer(InitFluids.fluidCanolaOil);
        this.registerCustomFluidBlockRenderer(InitFluids.fluidOil);
        this.registerCustomFluidBlockRenderer(InitFluids.fluidCrystalOil);
        this.registerCustomFluidBlockRenderer(InitFluids.fluidEmpoweredOil);

        InitEntities.initClient();
    }

    /**
     * (Excerpted from Tinkers' Construct with permission, thanks guys!)
     */
    private void registerCustomFluidBlockRenderer(Fluid fluid){
        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        FluidStateMapper mapper = new FluidStateMapper(fluid);
        ModelLoader.registerItemVariants(item);
        ModelLoader.setCustomMeshDefinition(item, mapper);
        ModelLoader.setCustomStateMapper(block, mapper);
    }

    @Override
    public void init(FMLInitializationEvent event){
        ModUtil.LOGGER.info("Initializing ClientProxy...");

        new ClientEvents();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompost.class, new RenderCompost());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAtomicReconstructor.class, new RenderReconstructorLens());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmileyCloud.class, new RenderSmileyCloud());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayStand.class, new RenderDisplayStand());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEmpowerer.class, new RenderEmpowerer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBatteryBox.class, new RenderBatteryBox());

        TileEntitySpecialRenderer laser = new RenderLaserRelay();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserRelayEnergy.class, laser);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserRelayEnergyAdvanced.class, laser);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserRelayEnergyExtreme.class, laser);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserRelayItem.class, laser);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserRelayItemWhitelist.class, laser);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaserRelayFluids.class, laser);

        //VillagerRegistry.INSTANCE().registerVillagerSkin(ConfigIntValues.JAM_VILLAGER_ID.getValue(), new ResourceLocation(ModUtil.MOD_ID, "textures/entity/villager/jamVillager.png"));

        for(Item item : COLOR_PRODIVIDING_ITEMS_FOR_REGISTERING){
            if(item instanceof IColorProvidingItem){
                Minecraft.getMinecraft().getItemColors().registerItemColorHandler(((IColorProvidingItem)item).getItemColor(), item);
            }
        }

        for(Block block : COLOR_PRODIVIDING_BLOCKS_FOR_REGISTERING){
            if(block instanceof IColorProvidingBlock){
                Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(((IColorProvidingBlock)block).getBlockColor(), block);
            }
            if(block instanceof IColorProvidingItem){
                Minecraft.getMinecraft().getItemColors().registerItemColorHandler(((IColorProvidingItem)block).getItemColor(), block);
            }
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        ModUtil.LOGGER.info("PostInitializing ClientProxy...");

        new SpecialRenderInit();
    }

    @Override
    public void addRenderRegister(ItemStack stack, ResourceLocation location, String variant){
        MODEL_LOCATIONS_FOR_REGISTERING.put(stack, new ModelResourceLocation(location, variant));
    }

    @Override
    public void addColoredItem(Item item){
        COLOR_PRODIVIDING_ITEMS_FOR_REGISTERING.add(item);
    }

    @Override
    public void addColoredBlock(Block block){
        COLOR_PRODIVIDING_BLOCKS_FOR_REGISTERING.add(block);
    }

    @Override
    public EntityPlayer getCurrentPlayer(){
        return Minecraft.getMinecraft().player;
    }
}
