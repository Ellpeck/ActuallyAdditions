package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ActuallyAdditions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ActuallyGens {

    @SubscribeEvent
    public static void runGenerator(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeClient()) {

            generator.addProvider(new GeneratorBlockStates(generator, helper));
            generator.addProvider(new GeneratorItemModels(generator, helper));
            generator.addProvider(new GeneratorLanguage(generator));
        }

        if (event.includeServer()) {
            GeneratorBlockTags generatorBlockTags = new GeneratorBlockTags(generator, helper);

            generator.addProvider(new GeneratorLoot(generator));
            generator.addProvider(new GeneratorRecipes(generator));
            generator.addProvider(generatorBlockTags);
            generator.addProvider(new GeneratorItemTags(generator, generatorBlockTags, helper));
        }
    }
}
