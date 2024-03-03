package de.ellpeck.actuallyadditions.registration;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class AABlockReg<B extends Block, I extends Item, T extends BlockEntity> implements Supplier<Block> {
    private final String name;
    private RegistryObject<B> block;
    private RegistryObject<I> item;
    private RegistryObject<BlockEntityType<T>> tileEntityType;

    public AABlockReg(String name, Supplier<B> blockSupplier, Function<B, I> itemSupplier, BlockEntityType.BlockEntitySupplier<T> tileSupplier) {
        this.name = name;
        this.block = ActuallyBlocks.BLOCKS.register(name, blockSupplier);
        this.item = ActuallyItems.ITEMS.register(name, () -> itemSupplier.apply(block.get()));
        this.tileEntityType = ActuallyBlocks.TILES.register(name, () -> BlockEntityType.Builder.of(tileSupplier, block.get()).build(null));
    }
    public AABlockReg(String name, Supplier<B> blockSupplier, Function<B, I> itemSupplier) {
        this.name = name;
        this.block = ActuallyBlocks.BLOCKS.register(name, blockSupplier);
        this.item = ActuallyItems.ITEMS.register(name, () -> itemSupplier.apply(block.get()));
    }

 /*
    public AABlock(BlockBuilder builder) {
        this.name = builder.name;
        this.block = ActuallyBlocks.BLOCKS.register(name, builder.blockSupplier);
        this.item = ActuallyItems.ITEMS.register(name, () -> builder.itemSupplier.apply(block.get()));
        if (builder.hasTile)
            this.tileEntityType = ActuallyTiles.TILES.register(name, () -> TileEntityType.Builder.create(builder.tileSupplier, block.get()).build(null));
    }

  */

    public String getName() {return name;}

    @Override
    public Block get() {
        return block.get();
    }

    public B getBlock() {
        return block.get();
    }

    public I getItem() {
        return item.get();
    }

    @Nonnull
    public BlockEntityType<T> getTileEntityType() { return Objects.requireNonNull(tileEntityType.get());}


    public static class BlockBuilder {
        private final String name;
        private Supplier<Block> blockSupplier;
        private Function<Block, Item> itemSupplier = (b) -> new BlockItem(b, ActuallyItems.defaultProps());
        private boolean hasTile = false;
        private Supplier<BlockEntityType<?>> tileSupplier;

        private BlockBuilder(String nameIn, Supplier<Block> blockSupplierIn) {
            this.name = nameIn;
            this.blockSupplier = blockSupplierIn;
        }

        public static BlockBuilder block(String nameIn, Supplier<Block> blockSupplierIn) {
            return new BlockBuilder(nameIn, blockSupplierIn);
        }

        public BlockBuilder item(Function<Block, Item> itemSupplierIn) {
            itemSupplier = itemSupplierIn;

            return this;
        }

        public BlockBuilder tile(Supplier<BlockEntityType<?>> tileSupplierIn) {
            tileSupplier = tileSupplierIn;
            hasTile = true;

            return this;
        }
/*
        public AABlock build() {
            return new AABlock(this);
        }

 */

    }
}
