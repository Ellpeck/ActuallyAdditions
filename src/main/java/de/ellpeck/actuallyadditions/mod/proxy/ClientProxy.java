/*
 * This file ("ClientProxy.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.proxy;


import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.booklet.BookletPage;
import de.ellpeck.actuallyadditions.api.booklet.IBookletChapter;
import de.ellpeck.actuallyadditions.api.booklet.IBookletEntry;
import de.ellpeck.actuallyadditions.mod.blocks.render.*;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.event.ClientEvents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.misc.special.SpecialRenderInit;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.FluidStateMapper;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class ClientProxy implements IProxy{

    private static final List<Item> COLOR_PRODIVIDING_ITEMS_FOR_REGISTERING = new ArrayList<Item>();
    private static final Map<ItemStack, ModelResourceLocation> MODEL_LOCATIONS_FOR_REGISTERING = new HashMap<ItemStack, ModelResourceLocation>();
    public static boolean pumpkinBlurPumpkinBlur;
    public static boolean jingleAllTheWay;
    public static boolean bulletForMyValentine;
    public static int bookletWordCount;
    public static int bookletCharCount;

    private static void countBookletWords(){
        bookletWordCount = 0;
        bookletCharCount = 0;
        String bookletText = "";

        for(IBookletEntry entry : ActuallyAdditionsAPI.BOOKLET_ENTRIES){
            if(entry != ActuallyAdditionsAPI.allAndSearch){
                bookletWordCount += entry.getLocalizedName().split(" ").length;
                bookletCharCount += entry.getLocalizedName().length();
                bookletText += entry.getLocalizedName()+"\n\n";

                for(IBookletChapter chapter : entry.getChapters()){
                    bookletWordCount += chapter.getLocalizedName().split(" ").length;
                    bookletCharCount += chapter.getLocalizedName().length();
                    bookletText += chapter.getLocalizedName()+"\n";

                    for(BookletPage page : chapter.getPages()){
                        if(page.getText() != null){
                            bookletWordCount += page.getText().split(" ").length;
                            bookletCharCount += page.getText().length();
                            bookletText += page.getText()+"\n";
                        }
                    }
                    bookletText += "\n";

                }
                bookletText += "\n";
            }
        }

        if(ConfigBoolValues.BOOKLET_TEXT_TO_FILE.isEnabled()){
            File file = new File(Minecraft.getMinecraft().mcDataDir, ModUtil.MOD_ID+"booklettext.txt");
            try{
                file.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(TextFormatting.getTextWithoutFormattingCodes(bookletText));
                writer.close();
                ModUtil.LOGGER.info("Wrote booklet text to file!");
            }
            catch(Exception e){
                ModUtil.LOGGER.error("Couldn't write booklet text to file!", e);
            }
        }
    }

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

        for(Map.Entry<ItemStack, ModelResourceLocation> entry : MODEL_LOCATIONS_FOR_REGISTERING.entrySet()){
            ModelLoader.setCustomModelResourceLocation(entry.getKey().getItem(), entry.getKey().getItemDamage(), entry.getValue());
        }

        this.registerCustomFluidBlockRenderer(InitFluids.fluidCanolaOil);
        this.registerCustomFluidBlockRenderer(InitFluids.fluidOil);

        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        if(manager instanceof IReloadableResourceManager){
            ((IReloadableResourceManager)manager).registerReloadListener(new IResourceManagerReloadListener(){
                @Override
                public void onResourceManagerReload(IResourceManager resourceManager){
                    countBookletWords();
                }
            });
        }

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

        //VillagerRegistry.INSTANCE().registerVillagerSkin(ConfigIntValues.JAM_VILLAGER_ID.getValue(), new ResourceLocation(ModUtil.MOD_ID, "textures/entity/villager/jamVillager.png"));

        for(Item item : COLOR_PRODIVIDING_ITEMS_FOR_REGISTERING){
            if(item instanceof IColorProvidingItem){
                Minecraft.getMinecraft().getItemColors().registerItemColorHandler(((IColorProvidingItem)item).getColor(), item);
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
}
