/*
 * This file ("ActuallyAdditions.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.booklet.InitBooklet;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.mod.crafting.TargetNBTIngredient;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.event.CommonEvents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.inventory.ActuallyContainers;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.items.lens.LensMining;
import de.ellpeck.actuallyadditions.mod.items.lens.LensRecipeHandler;
import de.ellpeck.actuallyadditions.mod.items.lens.Lenses;
import de.ellpeck.actuallyadditions.mod.misc.BannerHelper;
import de.ellpeck.actuallyadditions.mod.misc.DungeonLoot;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.MethodHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.recipe.HairyBallHandler;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.ResourceReloader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@Mod(modid = ActuallyAdditions.MODID, name = ActuallyAdditions.NAME, version = ActuallyAdditions.VERSION, guiFactory = ActuallyAdditions.GUIFACTORY, dependencies = ActuallyAdditions.DEPS)
@Mod(ActuallyAdditions.MODID)
public class ActuallyAdditions {

    public static final String MODID = ActuallyAdditionsAPI.MOD_ID;

    @Deprecated
    public static final String NAME = "Actually Additions";
    @Deprecated
    public static final String VERSION = "@VERSION@";
    @Deprecated
    public static final String GUIFACTORY = "de.ellpeck.actuallyadditions.mod.config.GuiFactory";
    public static final String DEPS = "required:forge@[14.23.5.2836,);before:craftingtweaks;after:fastbench@[1.3.2,)";
    //    public static final boolean DEOBF = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static final ItemGroup GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ActuallyItems.ITEM_BOOKLET.get());
        }
    };
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    @Deprecated
    public static ActuallyAdditions INSTANCE;

    // TODO: [port] eval
    //    static {
    //        FluidRegistry.enableUniversalBucket();
    //    }
    public static boolean commonCapsLoaded;

    public ActuallyAdditions() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ActuallyBlocks.BLOCKS.register(eventBus);
        ActuallyBlocks.TILES.register(eventBus);
        ActuallyRecipes.init(eventBus);
        ActuallyContainers.CONTAINERS.register(eventBus);

        MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
        MinecraftForge.EVENT_BUS.addListener(this::serverStopped);
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        MinecraftForge.EVENT_BUS.register(new DungeonLoot());
        MinecraftForge.EVENT_BUS.addListener(ActuallyAdditions::reloadEvent);
        InitFluids.init(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
    }

    private void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();

        ActuallyAdditionsAPI.methodHandler = new MethodHandler();
        ActuallyAdditionsAPI.connectionHandler = new LaserRelayConnectionHandler();
        Lenses.init();
//        CompatUtil.registerCraftingTweaks();
        event.enqueueWork(() -> CraftingHelper.register(TargetNBTIngredient.Serializer.NAME, TargetNBTIngredient.SERIALIZER));

        commonCapsLoaded = false; // Loader.isModLoaded("commoncapabilities");

        new UpdateChecker();
        BannerHelper.init();
        InitEntities.init(); // todo: [port] replace
        //AAWorldGen gen = new AAWorldGen();
        ItemCoffee.initIngredients();
        CrusherCrafting.init();
        HairyBallHandler.init();
        LensRecipeHandler.init();
        LensMining.init();
        InitBooklet.init();
    }

    private static void reloadEvent(AddReloadListenerEvent event){
        event.addListener(new ResourceReloader(event.getDataPackRegistries()));
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ActuallyAdditionsClient.setup();
    }

    public void serverStarted(FMLServerStartedEvent event) {
        // TODO: [port] check if this is needed

        //        if (event.getServer() != null) {
        //            World world = event.getServer().getWorld(OVERWORLD);
        //            if (world != null && !world.isRemote) {
        //                WorldData.get(world, true).markDirty();
        //            }
        //        }
    }

    public void serverStopped(FMLServerStoppedEvent event) {
        // TODO: [port] check if this is needed
        WorldData.clear();
    }
}
