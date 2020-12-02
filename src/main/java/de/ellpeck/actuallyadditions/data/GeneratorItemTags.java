package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.ActuallyItems;
import de.ellpeck.actuallyadditions.common.utilities.ActuallyTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class GeneratorItemTags extends ItemTagsProvider {
    public GeneratorItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, ActuallyAdditions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ActuallyTags.Items.DRILLS).add(
                ActuallyItems.DRILL_MAIN.get(),
                ActuallyItems.DRILL_BLACK.get(), ActuallyItems.DRILL_BLACK.get(), ActuallyItems.DRILL_BLUE.get(), ActuallyItems.DRILL_BROWN.get(),
                ActuallyItems.DRILL_CYAN.get(), ActuallyItems.DRILL_GRAY.get(), ActuallyItems.DRILL_GREEN.get(), ActuallyItems.DRILL_LIGHT_GRAY.get(),
                ActuallyItems.DRILL_LIME.get(), ActuallyItems.DRILL_MAGENTA.get(), ActuallyItems.DRILL_ORANGE.get(), ActuallyItems.DRILL_PINK.get(),
                ActuallyItems.DRILL_PURPLE.get(), ActuallyItems.DRILL_RED.get(), ActuallyItems.DRILL_WHITE.get(), ActuallyItems.DRILL_YELLOW.get()
        );
    }
}
