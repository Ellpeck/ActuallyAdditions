package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.block.CropsBlock;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

import java.util.function.Supplier;

public class AACrops extends CropsBlock {
    Supplier<Item> itemSupplier;
    public AACrops(Properties p_i48421_1_, Supplier<Item> seedSupplier) {
        super(p_i48421_1_);
        this.itemSupplier = seedSupplier;
    }

    @Override
    protected IItemProvider getBaseSeedId() {
        return this.itemSupplier.get();
    }
}
