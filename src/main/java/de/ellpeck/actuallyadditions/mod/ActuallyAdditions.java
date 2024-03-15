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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.mod.attachments.ActuallyAttachments;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.config.conditions.BoolConfigCondition;
import de.ellpeck.actuallyadditions.mod.crafting.ActuallyRecipes;
import de.ellpeck.actuallyadditions.mod.crafting.CrusherCrafting;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.entity.EntityWorm;
import de.ellpeck.actuallyadditions.mod.entity.InitEntities;
import de.ellpeck.actuallyadditions.mod.event.CommonEvents;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.gen.ActuallyVillages;
import de.ellpeck.actuallyadditions.mod.gen.modifier.BoolConfigFeatureBiomeModifier;
import de.ellpeck.actuallyadditions.mod.gen.village.ActuallyPOITypes;
import de.ellpeck.actuallyadditions.mod.gen.village.ActuallyVillagers;
import de.ellpeck.actuallyadditions.mod.gen.village.InitVillager;
import de.ellpeck.actuallyadditions.mod.inventory.ActuallyContainers;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.ItemCoffee;
import de.ellpeck.actuallyadditions.mod.items.Worm;
import de.ellpeck.actuallyadditions.mod.lootmodifier.ActuallyLootModifiers;
import de.ellpeck.actuallyadditions.mod.misc.BannerHelper;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.LaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.MethodHandler;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import de.ellpeck.actuallyadditions.mod.particle.ActuallyParticles;
import de.ellpeck.actuallyadditions.mod.update.UpdateChecker;
import de.ellpeck.actuallyadditions.mod.util.ResourceReloader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

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

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);
    public static final Supplier<EntityType<EntityWorm>> ENTITY_WORM = ENTITIES.register("worm", () -> EntityType.Builder.of(EntityWorm::new, MobCategory.MISC).build(MODID + ":worm"));

    private static final DeferredRegister<Codec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, MODID);
    public static final DeferredHolder<Codec<? extends ICondition>, Codec<BoolConfigCondition>> BOOL_CONFIG_CONDITION = CONDITION_CODECS.register("bool_config_condition", () -> BoolConfigCondition.CODEC);

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
    public static final Supplier<Codec<BoolConfigFeatureBiomeModifier>> BOOL_CONFIG_MODIFIER = BIOME_MODIFIER_SERIALIZERS.register("bool_config_feature_modifier", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(BoolConfigFeatureBiomeModifier::biomes),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(BoolConfigFeatureBiomeModifier::features),
                    GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(BoolConfigFeatureBiomeModifier::step),
                    Codec.STRING.fieldOf("boolConfig").forGetter(BoolConfigFeatureBiomeModifier::boolConfig)
            ).apply(builder, BoolConfigFeatureBiomeModifier::new))
    );

    public static boolean commonCapsLoaded;

    public ActuallyAdditions(IEventBus eventBus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);

        ActuallyBlocks.init(eventBus);
        ActuallyItems.init(eventBus);
        ActuallyTabs.init(eventBus);
        ActuallyRecipes.init(eventBus);
        AASounds.init(eventBus);
        ActuallyVillagers.init(eventBus);
        ActuallyPOITypes.init(eventBus);
        ActuallyAttachments.init(eventBus);
        ActuallyLootModifiers.init(eventBus);
        BannerHelper.init(eventBus);
        ActuallyContainers.CONTAINERS.register(eventBus);
        ENTITIES.register(eventBus);
        CONDITION_CODECS.register(eventBus);
        BIOME_MODIFIER_SERIALIZERS.register(eventBus);
        eventBus.addListener(this::onConfigReload);
        ActuallyParticles.init(eventBus);
        ActuallyTags.init();

        NeoForge.EVENT_BUS.addListener(this::serverStarted);
        NeoForge.EVENT_BUS.addListener(this::serverStopped);
        NeoForge.EVENT_BUS.addListener(InitVillager::setupTrades);
        NeoForge.EVENT_BUS.register(new CommonEvents());
//        NeoForge.EVENT_BUS.register(new DungeonLoot());
        NeoForge.EVENT_BUS.addListener(ActuallyAdditions::reloadEvent);
        NeoForge.EVENT_BUS.addListener(Worm::onHoe);
        NeoForge.EVENT_BUS.addListener(ActuallyVillages::modifyVillageStructures);
        InitFluids.init(eventBus);

        eventBus.addListener(PacketHandler::register);
        eventBus.addListener(this::setup);

        if (FMLEnvironment.dist.isClient()) {
	        eventBus.addListener(ActuallyAdditionsClient::setup);
	        eventBus.addListener(ActuallyAdditionsClient::setupSpecialRenders);
	        eventBus.addListener(ActuallyAdditionsClient::registerParticleFactories);
            eventBus.register(new ClientRegistryHandler());
        }
        IFarmerBehavior.initBehaviors();
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

        new UpdateChecker();
        InitEntities.init(); // todo: [port] replace
        CrusherCrafting.init();
    }

    private void onConfigReload(ModConfigEvent event) {
        Item item1 = BuiltInRegistries.ITEM.get(new ResourceLocation(CommonConfig.Other.REDSTONECONFIGURATOR.get()));
        Item item2 = BuiltInRegistries.ITEM.get(new ResourceLocation(CommonConfig.Other.RELAYCONFIGURATOR.get()));
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
}
