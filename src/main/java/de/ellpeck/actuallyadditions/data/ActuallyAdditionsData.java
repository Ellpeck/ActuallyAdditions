package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.conditions.BoolConfigCondition;
import de.ellpeck.actuallyadditions.mod.misc.ActuallyDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ActuallyAdditionsData {

    @SubscribeEvent
    public static void runGenerator(GatherDataEvent event) {
        CraftingHelper.register(BoolConfigCondition.Serializer.INSTANCE);
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = CompletableFuture.supplyAsync(ActuallyAdditionsData::getProvider);
        ExistingFileHelper helper = event.getExistingFileHelper();

        //            generator.addProvider(new GeneratorBlockStates(generator, helper));
        //            generator.addProvider(new GeneratorItemModels(generator, helper));
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
                packOutput, lookupProvider, Set.of(ActuallyAdditions.MODID)));
    }

    private static HolderLookup.Provider getProvider() {
        final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        registryBuilder.add(Registries.DAMAGE_TYPE, (context) -> {
            context.register(ActuallyDamageTypes.ATOMIC_RECONSTRUCTOR, new DamageType("actuallyadditions.atomic_reconstructor", 0.0F));
        });
        // We need the BIOME registry to be present, so we can use a biome tag, doesn't matter that it's empty
        registryBuilder.add(Registries.BIOME, context -> {
        });
        RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
    }
}
