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

        if (event.includeClient()) {
            ExistingFileHelper helper = event.getExistingFileHelper();

            generator.addProvider(new GeneratorBlockStates(generator, helper));
            generator.addProvider(new GeneratorItemModels(generator, helper));
            generator.addProvider(new GeneratorLanguage(generator));
            generator.addProvider(new GeneratorBlockTags(generator, helper));
        }

        if (event.includeServer()) {
            generator.addProvider(new GeneratorLoot(generator));
        }
    }
}
