package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

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
        return new Item.Properties();
    }

    public static class Stairs extends StairBlock implements IActuallyBlock {
        public Stairs(BlockState state, Properties properties) {
            super(state, properties);
        }

        @Override
        public AABlockItem createBlockItem() {
            return new AABlockItem(this, this.getItemProperties());
        }

        @Override
        public Item.Properties getItemProperties() {
            return new Item.Properties();
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
            return new Item.Properties();
        }
    }
}
