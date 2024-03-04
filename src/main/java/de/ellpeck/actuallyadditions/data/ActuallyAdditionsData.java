package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.conditions.BoolConfigCondition;
import de.ellpeck.actuallyadditions.mod.misc.ActuallyDamageTypes;
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
import net.neoforged.neoforge.common.crafting.CraftingHelper;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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

        generator.addProvider(true, new LootTableGenerator(packOutput));
        generator.addProvider(true, new BlockRecipeGenerator(packOutput));
        generator.addProvider(true, new ItemRecipeGenerator(packOutput));
        generator.addProvider(true, generatorBlockTags);
        generator.addProvider(true, new ItemTagsGenerator(packOutput, lookupProvider, generatorBlockTags, helper));
        generator.addProvider(true, new DamageTypeTagsGenerator(packOutput, lookupProvider, helper));

        generator.addProvider(true, new BlockStateGenerator(packOutput, helper));
        generator.addProvider(true, new ItemModelGenerator(packOutput, helper));

        generator.addProvider(true, new AdvancementGenerator(packOutput, lookupProvider, helper));
        generator.addProvider(true, new LaserRecipeGenerator(packOutput));
        generator.addProvider(true, new ColorChangeGenerator(packOutput));
        generator.addProvider(true, new EmpoweringRecipeGenerator(packOutput));
        generator.addProvider(true, new CrushingRecipeGenerator(packOutput));
        generator.addProvider(true, new FuelRecipeGenerator(packOutput));
        generator.addProvider(true, new MiscMachineRecipeGenerator(packOutput));
        generator.addProvider(true, new MiningLensGenerator(packOutput));

        generator.addProvider(true, new SoundsGenerator(packOutput, helper));

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
                packOutput, patchedProvider, Set.of(ActuallyAdditions.MODID)));
    }

    private static RegistrySetBuilder.PatchedRegistries getProvider() {
        final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        registryBuilder.add(Registries.DAMAGE_TYPE, (context) -> {
            context.register(ActuallyDamageTypes.ATOMIC_RECONSTRUCTOR, new DamageType("actuallyadditions.atomic_reconstructor", 0.0F));
        });
        // We need the BIOME registry to be present, so we can use a biome tag, doesn't matter that it's empty
        registryBuilder.add(Registries.BIOME, $ -> {
        });
        RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        Cloner.Factory cloner$factory = new Cloner.Factory();
        net.neoforged.neoforge.registries.DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(cloner$factory::addCodec));
        return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup(), cloner$factory);
    }
}
