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
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.config.conditions.BoolConfigCondition;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.mod.crafting.TargetNBTIngredient;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.entity.EntityWorm;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.event.CommonEvents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.inventory.ActuallyContainers;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.misc.BannerHelper;
import de.ellpeck.actuallyadditions.mod.misc.DungeonLoot;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.MethodHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.particle.ActuallyParticles;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.ResourceReloader;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static final ItemGroup GROUP = new ItemGroup(MODID) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ActuallyItems.ITEM_BOOKLET.get());
        }
    };
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    public static final RegistryObject<EntityType<EntityWorm>> ENTITY_WORM = ENTITIES.register("worm", () -> EntityType.Builder.of(EntityWorm::new, EntityClassification.MISC).build(MODID + ":worm"));

    public static boolean commonCapsLoaded;

    public ActuallyAdditions() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ActuallyBlocks.init(eventBus);
        ActuallyItems.init(eventBus);
        ActuallyRecipes.init(eventBus);
        AASounds.init(eventBus);
        ActuallyContainers.CONTAINERS.register(eventBus);
        ENTITIES.register(eventBus);
        eventBus.addListener(this::onConfigReload);
        ActuallyParticles.init(eventBus);

        MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
        MinecraftForge.EVENT_BUS.addListener(this::serverStopped);
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        MinecraftForge.EVENT_BUS.register(new DungeonLoot());
        MinecraftForge.EVENT_BUS.addListener(ActuallyAdditions::reloadEvent);
        InitFluids.init(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::particleFactoryRegister);
        IFarmerBehavior.initBehaviors();
    }

    private static void reloadEvent(AddReloadListenerEvent event) {
        event.addListener(new ResourceReloader(event.getDataPackRegistries()));
    }

    private void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();

        event.enqueueWork(() -> {
            CraftingHelper.register(BoolConfigCondition.Serializer.INSTANCE);
        });

        ActuallyAdditionsAPI.methodHandler = new MethodHandler();
        ActuallyAdditionsAPI.connectionHandler = new LaserRelayConnectionHandler();
        //Lenses.init();
//        CompatUtil.registerCraftingTweaks();
        event.enqueueWork(() -> CraftingHelper.register(TargetNBTIngredient.Serializer.NAME, TargetNBTIngredient.SERIALIZER));

        commonCapsLoaded = false; // Loader.isModLoaded("commoncapabilities");

        new UpdateChecker();
        BannerHelper.init();
        InitEntities.init(); // todo: [port] replace
        //AAWorldGen gen = new AAWorldGen();
        ItemCoffee.initIngredients();
        CrusherCrafting.init();
        //LensMining.init();
    }

    private void onConfigReload(ModConfig.ModConfigEvent event) {
        Item item1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(CommonConfig.Other.REDSTONECONFIGURATOR.get()));
        Item item2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(CommonConfig.Other.RELAYCONFIGURATOR.get()));
        CommonConfig.Other.redstoneConfigureItem = item1 != null?item1: Items.AIR;
        CommonConfig.Other.relayConfigureItem = item2 != null?item2: Items.AIR;
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ActuallyAdditionsClient.setup(event);
    }

    private void particleFactoryRegister(ParticleFactoryRegisterEvent event) {
        ActuallyAdditionsClient.registerParticleFactories();
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
