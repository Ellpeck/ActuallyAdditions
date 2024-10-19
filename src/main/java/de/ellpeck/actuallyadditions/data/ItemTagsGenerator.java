package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
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
        this.tag(ActuallyTags.Items.CRYSTAL_BLOCKS)
                .add(ActuallyBlocks.RESTONIA_CRYSTAL.getItem(), ActuallyBlocks.PALIS_CRYSTAL.getItem(),
                        ActuallyBlocks.DIAMATINE_CRYSTAL.getItem(), ActuallyBlocks.VOID_CRYSTAL.getItem(),
                        ActuallyBlocks.EMERADIC_CRYSTAL.getItem(), ActuallyBlocks.ENORI_CRYSTAL.getItem());

        tag(Tags.Items.SLIME_BALLS)
                .add(ActuallyItems.RICE_SLIMEBALL.get());

        tag(ActuallyTags.Items.CROPS_RICE).add(ActuallyItems.RICE.get());
        tag(ActuallyTags.Items.CROPS_COFFEE).add(ActuallyItems.COFFEE_BEANS.get());
        tag(ActuallyTags.Items.CROPS_CANOLA).add(ActuallyItems.CANOLA.get());
        tag(ActuallyTags.Items.CROPS_FLAX).add(ActuallyItems.FLAX_SEEDS.get());
        tag(Tags.Items.CROPS).addTags(ActuallyTags.Items.CROPS_RICE, ActuallyTags.Items.CROPS_COFFEE, ActuallyTags.Items.CROPS_CANOLA, ActuallyTags.Items.CROPS_FLAX);

        tag(ActuallyTags.Items.SEEDS_RICE).add(ActuallyItems.RICE_SEEDS.get());
        tag(ActuallyTags.Items.SEEDS_COFFEE).add(ActuallyItems.COFFEE_BEANS.get());
        tag(ActuallyTags.Items.SEEDS_CANOLA).add(ActuallyItems.CANOLA_SEEDS.get());
        tag(ActuallyTags.Items.SEEDS_FLAX).add(ActuallyItems.FLAX_SEEDS.get());
        tag(Tags.Items.SEEDS).addTags(ActuallyTags.Items.SEEDS_RICE, ActuallyTags.Items.SEEDS_COFFEE, ActuallyTags.Items.SEEDS_CANOLA, ActuallyTags.Items.SEEDS_FLAX);

        tag(ActuallyTags.Items.GEMS_BLACK_QUARTZ).add(ActuallyItems.BLACK_QUARTZ.get());
        tag(ActuallyTags.Items.ORES_BLACK_QUARTZ).add(ActuallyBlocks.BLACK_QUARTZ_ORE.getItem());
        tag(Tags.Items.ORES).addTags(ActuallyTags.Items.ORES_BLACK_QUARTZ);
        tag(Tags.Items.ORES_IN_GROUND_STONE).add(ActuallyBlocks.BLACK_QUARTZ_ORE.getItem());

        tag(ActuallyTags.Items.STORAGE_BLOCKS_BLACK_QUARTZ).add(ActuallyBlocks.BLACK_QUARTZ.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_RESTONIA_CRYSTAL).add(ActuallyBlocks.RESTONIA_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_PALIS_CRYSTAL).add(ActuallyBlocks.PALIS_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_DIAMATINE_CRYSTAL).add(ActuallyBlocks.DIAMATINE_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_VOID_CRYSTAL).add(ActuallyBlocks.VOID_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_EMERADIC_CRYSTAL).add(ActuallyBlocks.EMERADIC_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_ENORI_CRYSTAL).add(ActuallyBlocks.ENORI_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_EMPOWERED_RESTONIA_CRYSTAL).add(ActuallyBlocks.EMPOWERED_RESTONIA_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_EMPOWERED_PALIS_CRYSTAL).add(ActuallyBlocks.EMPOWERED_PALIS_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_EMPOWERED_DIAMATINE_CRYSTAL).add(ActuallyBlocks.EMPOWERED_DIAMATINE_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_EMPOWERED_VOID_CRYSTAL).add(ActuallyBlocks.EMPOWERED_VOID_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_EMPOWERED_EMERADIC_CRYSTAL).add(ActuallyBlocks.EMPOWERED_EMERADIC_CRYSTAL.getItem());
        tag(ActuallyTags.Items.STORAGE_BLOCKS_EMPOWERED_ENORI_CRYSTAL).add(ActuallyBlocks.EMPOWERED_ENORI_CRYSTAL.getItem());

        tag(ActuallyTags.Items.BUCKET_CANOLA_OIL).add(InitFluids.CANOLA_OIL.getBucket());
        tag(ActuallyTags.Items.BUCKET_REFINED_CANOLA_OIL).add(InitFluids.REFINED_CANOLA_OIL.getBucket());
        tag(ActuallyTags.Items.BUCKET_CRYSTALLIZED_OIL).add(InitFluids.CRYSTALLIZED_OIL.getBucket());
        tag(ActuallyTags.Items.BUCKET_EMPOWERED_OIL).add(InitFluids.EMPOWERED_OIL.getBucket());
        tag(Tags.Items.BUCKETS).addTags(
                ActuallyTags.Items.BUCKET_CANOLA_OIL, ActuallyTags.Items.BUCKET_REFINED_CANOLA_OIL,
                ActuallyTags.Items.BUCKET_CRYSTALLIZED_OIL, ActuallyTags.Items.BUCKET_EMPOWERED_OIL
        );

        tag(ActuallyTags.Items.CURIOS_CHARM).add(ActuallyItems.CRAFTER_ON_A_STICK.get());
    }
}
