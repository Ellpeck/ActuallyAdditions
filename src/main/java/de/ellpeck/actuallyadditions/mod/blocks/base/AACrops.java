package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

import java.util.function.Supplier;

public class AACrops extends CropBlock {
    Supplier<Item> itemSupplier;
    public AACrops(Properties p_i48421_1_, Supplier<Item> seedSupplier) {
        super(p_i48421_1_);
        this.itemSupplier = seedSupplier;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.itemSupplier.get();
    }
}
