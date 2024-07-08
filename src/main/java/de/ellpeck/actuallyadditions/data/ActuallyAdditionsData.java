package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.gen.ActuallyBiomeModifiers;
import de.ellpeck.actuallyadditions.mod.gen.ActuallyConfiguredFeatures;
import de.ellpeck.actuallyadditions.mod.gen.ActuallyPlacedFeatures;
import de.ellpeck.actuallyadditions.mod.gen.ActuallyProcessorLists;
import de.ellpeck.actuallyadditions.mod.misc.ActuallyDamageTypes;
import de.ellpeck.actuallyadditions.mod.misc.BannerHelper;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ActuallyAdditionsData {

    @SubscribeEvent
    public static void runGenerator(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<RegistrySetBuilder.PatchedRegistries> patchedProvider = CompletableFuture.supplyAsync(ActuallyAdditionsData::getProvider);
        CompletableFuture<HolderLookup.Provider> lookupProvider = CompletableFuture.supplyAsync(() -> ActuallyAdditionsData.getProvider().full());
        ExistingFileHelper helper = event.getExistingFileHelper();

        //            generator.addProvider(new GeneratorLanguage(generator));
        BlockTagsGenerator generatorBlockTags = new BlockTagsGenerator(packOutput, lookupProvider, helper);

        generator.addProvider(true, new LootTableGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new BlockRecipeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new ItemRecipeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, generatorBlockTags);
        generator.addProvider(true, new ItemTagsGenerator(packOutput, lookupProvider, generatorBlockTags, helper));
        generator.addProvider(true, new PoiTypeTagsGenerator(packOutput, lookupProvider, helper));
        generator.addProvider(true, new DamageTypeTagsGenerator(packOutput, lookupProvider, helper));
        generator.addProvider(true, new BannerPatternTagsGenerator(packOutput, lookupProvider, helper));

        generator.addProvider(true, new BlockStateGenerator(packOutput, helper));
        generator.addProvider(true, new ItemModelGenerator(packOutput, helper));

        generator.addProvider(true, new AdvancementGenerator(packOutput, lookupProvider, helper));
        generator.addProvider(true, new LaserRecipeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new ColorChangeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new EmpoweringRecipeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new CrushingRecipeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new FuelRecipeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new MiscMachineRecipeGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new MiningLensGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new CoffeeIngredientGenerator(packOutput, lookupProvider));

        generator.addProvider(true, new SoundsGenerator(packOutput, helper));

//        generator.addProvider(true, new PachouliGenerator(packOutput));

        generator.addProvider(true, new GlobalLootModifierGenerator(packOutput, lookupProvider));

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
                packOutput, patchedProvider, Set.of(ActuallyAdditions.MODID)));
    }

    private static RegistrySetBuilder.PatchedRegistries getProvider() {
        final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        registryBuilder.add(Registries.DAMAGE_TYPE, (context) -> {
            context.register(ActuallyDamageTypes.ATOMIC_RECONSTRUCTOR, new DamageType("actuallyadditions.atomic_reconstructor", 0.0F));
        });
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ActuallyConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ActuallyPlacedFeatures::bootstrap);
        registryBuilder.add(Registries.PROCESSOR_LIST, ActuallyProcessorLists::bootstrap);
        registryBuilder.add(Registries.BANNER_PATTERN, BannerHelper::bootstrap);
        registryBuilder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ActuallyBiomeModifiers::bootstrap);
        // We need the BIOME registry to be present, so we can use a biome tag, doesn't matter that it's empty
        registryBuilder.add(Registries.BIOME, $ -> {
        });
        RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        Cloner.Factory cloner$factory = new Cloner.Factory();
        net.neoforged.neoforge.registries.DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(cloner$factory::addCodec));
        return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup(), cloner$factory);
    }
}
