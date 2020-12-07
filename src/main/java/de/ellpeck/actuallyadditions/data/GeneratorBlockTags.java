package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class GeneratorBlockTags extends BlockTagsProvider {
    public GeneratorBlockTags(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, ActuallyAdditions.MOD_ID, existingFileHelper);
    }

    @Override
    public void registerTags() {
        getOrCreateBuilder(BlockTags.WALLS).add(
                ActuallyBlocks.WHITE_WALL.get(),
                ActuallyBlocks.GREEN_WALL.get(),
                ActuallyBlocks.BLACK_QUARTZ_WALL.get(),
                ActuallyBlocks.BLACK_SMOOTH_QUARTZ_WALL.get(),
                ActuallyBlocks.BLACK_PILLAR_QUARTZ_WALL.get(),
                ActuallyBlocks.BLACK_CHISELED_QUARTZ_WALL.get(),
                ActuallyBlocks.BLACK_BRICK_QUARTZ_WALL.get()
        );
    }

    /**
     * Resolves a Path for the location to save the given tag.
     */
    @Override
    protected Path makePath(ResourceLocation id) {
        return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Override
    public String getName() {
        return "Block Tags";
    }
}
