package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ItemTagsGenerator extends ItemTagsProvider {
    public ItemTagsGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
                             TagsProvider<Block> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockTagProvider.contentsGetter(), ActuallyAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        tag(ActuallyTags.Items.COFFEE_BEANS)
            .add(ActuallyItems.COFFEE_BEANS.get());
        tag(ActuallyTags.Items.TINY_COALS)
            .add(ActuallyItems.TINY_COAL.get())
            .add(ActuallyItems.TINY_CHARCOAL.get());
        tag(ActuallyTags.Items.DRILLS).add(
            ActuallyItems.DRILL_MAIN.get(),
            ActuallyItems.DRILL_BLACK.get(), ActuallyItems.DRILL_BLACK.get(), ActuallyItems.DRILL_BLUE.get(), ActuallyItems.DRILL_BROWN.get(),
            ActuallyItems.DRILL_CYAN.get(), ActuallyItems.DRILL_GRAY.get(), ActuallyItems.DRILL_GREEN.get(), ActuallyItems.DRILL_LIGHT_GRAY.get(),
            ActuallyItems.DRILL_LIME.get(), ActuallyItems.DRILL_MAGENTA.get(), ActuallyItems.DRILL_ORANGE.get(), ActuallyItems.DRILL_PINK.get(),
            ActuallyItems.DRILL_PURPLE.get(), ActuallyItems.DRILL_RED.get(), ActuallyItems.DRILL_WHITE.get(), ActuallyItems.DRILL_YELLOW.get()
        );
        tag(ActuallyTags.Items.CRYSTALS)
                .add(ActuallyItems.RESTONIA_CRYSTAL.get(), ActuallyItems.PALIS_CRYSTAL.get(),
                        ActuallyItems.DIAMATINE_CRYSTAL.get(), ActuallyItems.VOID_CRYSTAL.get(),
                        ActuallyItems.EMERADIC_CRYSTAL.get(), ActuallyItems.ENORI_CRYSTAL.get());

        tag(Tags.Items.SLIMEBALLS)
                .add(ActuallyItems.RICE_SLIMEBALL.get());
    }
}
