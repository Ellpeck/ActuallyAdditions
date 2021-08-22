package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.Item;

import java.util.function.Supplier;

/**
 * Using a custom class here to declare common rules between all of our blocks.
 * This also provides a simple instance of check for our blocks.
 *
 * @implNote Every block should extend this class in some form or use the {@link IActuallyBlock}
 */
public class ActuallyBlock extends Block implements IActuallyBlock {
    public ActuallyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public AABlockItem createBlockItem() {
        return new AABlockItem(this, this.getItemProperties());
    }

    @Override
    public Item.Properties getItemProperties() {
        return new Item.Properties().group(ActuallyAdditions.GROUP);
    }

    public static class Stairs extends StairsBlock implements IActuallyBlock {
        public Stairs(Supplier<BlockState> state, Properties properties) {
            super(state, properties);
        }

        @Override
        public AABlockItem createBlockItem() {
            return new AABlockItem(this, this.getItemProperties());
        }

        @Override
        public Item.Properties getItemProperties() {
            return new Item.Properties().group(ActuallyAdditions.GROUP);
        }
    }

    public static class Slabs extends SlabBlock implements IActuallyBlock {
        public Slabs(Properties properties) {
            super(properties);
        }

        @Override
        public AABlockItem createBlockItem() {
            return new AABlockItem(this, this.getItemProperties());
        }

        @Override
        public Item.Properties getItemProperties() {
            return new Item.Properties().group(ActuallyAdditions.GROUP);
        }
    }
}
