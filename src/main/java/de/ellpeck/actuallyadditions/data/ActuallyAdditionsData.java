package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ActuallyAdditionsData {

    @SubscribeEvent
    public static void runGenerator(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeClient()) {
            //            generator.addProvider(new GeneratorBlockStates(generator, helper));
            //            generator.addProvider(new GeneratorItemModels(generator, helper));
            //            generator.addProvider(new GeneratorLanguage(generator));
        }

        if (event.includeServer()) {
            BlockTagsGenerator generatorBlockTags = new BlockTagsGenerator(generator, helper);

            //            generator.addProvider(new GeneratorLoot(generator));
            generator.addProvider(new BlockRecipeGenerator(generator));
            generator.addProvider(new ItemRecipeGenerator(generator));
            generator.addProvider(generatorBlockTags);
            generator.addProvider(new ItemTagsGenerator(generator, generatorBlockTags, helper));

            generator.addProvider(new LaserRecipeGenerator(generator));
        }
    }
}
