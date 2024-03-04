package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

import java.util.function.Supplier;

public class AACrops extends CropBlock {
    Supplier<? extends Item> itemSupplier;
    public AACrops(Properties properties, Supplier<? extends Item> seedSupplier) {
        super(properties);
        this.itemSupplier = seedSupplier;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.itemSupplier.get();
    }
}
