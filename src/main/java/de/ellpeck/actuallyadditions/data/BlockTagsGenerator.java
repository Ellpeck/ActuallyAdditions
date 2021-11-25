package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, ActuallyAdditions.MODID, existingFileHelper);
    }

    @Override
    public void addTags() {
        tag(BlockTags.WALLS).add(
            ActuallyBlocks.ETHETIC_WHITE_WALL.get(),
            ActuallyBlocks.ETHETIC_GREEN_WALL.get(),
            ActuallyBlocks.BLACK_QUARTZ_WALL.get(),
            ActuallyBlocks.SMOOTH_BLACK_QUARTZ_WALL.get(),
            ActuallyBlocks.BLACK_QUARTZ_PILLAR_WALL.get(),
            ActuallyBlocks.CHISELED_BLACK_QUARTZ_WALL.get(),
            ActuallyBlocks.BLACK_QUARTZ_BRICK_WALL.get()
        );
    }

    /**
     * Resolves a Path for the location to save the given tag.
     */
    @Override
    protected Path getPath(ResourceLocation id) {
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
