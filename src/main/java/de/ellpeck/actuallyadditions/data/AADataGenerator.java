package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.data.recipes.CrusherRecipeGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ActuallyAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AADataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
    
        dataGenerator.addProvider(new BlockStateGenerator(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(new CrusherRecipeGenerator(dataGenerator));
    }
}
