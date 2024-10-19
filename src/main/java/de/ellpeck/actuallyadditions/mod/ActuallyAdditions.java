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
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.event.CommonEvents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.gen.ActuallyBiomeModifiers;
import de.ellpeck.actuallyadditions.mod.gen.ActuallyVillages;
import de.ellpeck.actuallyadditions.mod.gen.village.ActuallyPOITypes;
import de.ellpeck.actuallyadditions.mod.gen.village.ActuallyVillagers;
import de.ellpeck.actuallyadditions.mod.gen.village.InitVillager;
import de.ellpeck.actuallyadditions.mod.inventory.ActuallyContainers;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.Worm;
import de.ellpeck.actuallyadditions.mod.lootmodifier.ActuallyLootModifiers;
import de.ellpeck.actuallyadditions.mod.material.ArmorMaterials;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.MethodHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.particle.ActuallyParticles;
import de.ellpeck.actuallyadditions.mod.util.ResourceReloader;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static boolean commonCapsLoaded;

    public ActuallyAdditions(IEventBus eventBus, ModContainer container, Dist dist) {
        container.registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);

        ActuallyBlocks.init(eventBus);
        ActuallyItems.init(eventBus);
        ActuallyTabs.init(eventBus);
        ActuallyRecipes.init(eventBus);
        AASounds.init(eventBus);
        ActuallyVillagers.init(eventBus);
        ActuallyPOITypes.init(eventBus);
        ActuallyComponents.init(eventBus);
        ActuallyLootModifiers.init(eventBus);
        ActuallyContainers.init(eventBus);
        ArmorMaterials.init(eventBus);
        InitEntities.init(eventBus);
        InitFluids.init(eventBus);
        ActuallyBiomeModifiers.init(eventBus);
        ActuallyParticles.init(eventBus);
        ActuallyTags.init();
        ActuallyAdditionsClient.init(eventBus);
        eventBus.addListener(this::onConfigReload);

        NeoForge.EVENT_BUS.addListener(this::serverStarted);
        NeoForge.EVENT_BUS.addListener(this::serverStopped);
        NeoForge.EVENT_BUS.addListener(InitVillager::setupTrades);
        NeoForge.EVENT_BUS.register(new CommonEvents());
//        NeoForge.EVENT_BUS.register(new DungeonLoot());
        NeoForge.EVENT_BUS.addListener(ActuallyAdditions::reloadEvent);
        NeoForge.EVENT_BUS.addListener(Worm::onHoe);
        NeoForge.EVENT_BUS.addListener(ActuallyVillages::modifyVillageStructures);

        eventBus.addListener(PacketHandler::register);
        eventBus.addListener(this::setup);

        if (dist.isClient()) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
            eventBus.addListener(ActuallyAdditionsClient::setup);
            eventBus.addListener(ActuallyAdditionsClient::setupMenus);
            eventBus.addListener(ActuallyAdditionsClient::setupSpecialRenders);
            eventBus.addListener(ActuallyAdditionsClient::registerParticleFactories);
        }
        IFarmerBehavior.initBehaviors();

        Util.curiosLoaded = ModList.get().isLoaded("curios");
    }

    private static void reloadEvent(AddReloadListenerEvent event) {
        event.addListener(new ResourceReloader(event.getServerResources()));
    }

    private void setup(FMLCommonSetupEvent event) {
        ActuallyAdditionsAPI.methodHandler = new MethodHandler();
        ActuallyAdditionsAPI.connectionHandler = new LaserRelayConnectionHandler();
        //Lenses.init();
//        CompatUtil.registerCraftingTweaks();

        commonCapsLoaded = false; // Loader.isModLoaded("commoncapabilities");

//        new UpdateChecker();
    }

    private void onConfigReload(ModConfigEvent event) {
        Item item1 = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(CommonConfig.Other.REDSTONECONFIGURATOR.get()));
        Item item2 = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(CommonConfig.Other.RELAYCONFIGURATOR.get()));
        CommonConfig.Other.redstoneConfigureItem = item1 != null?item1: Items.AIR;
        CommonConfig.Other.relayConfigureItem = item2 != null?item2: Items.AIR;
    }

    public void serverStarted(ServerStartedEvent event) {
        // TODO: [port] check if this is needed

        //        if (event.getServer() != null) {
        //            World world = event.getServer().getWorld(OVERWORLD);
        //            if (world != null && !world.isRemote) {
        //                WorldData.get(world, true).markDirty();
        //            }
        //        }
    }

    public void serverStopped(ServerStoppedEvent event) {
        // TODO: [port] check if this is needed
        WorldData.clear();
    }

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
