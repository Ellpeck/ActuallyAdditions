package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class FenceBlock extends net.minecraft.block.FenceBlock implements IActuallyBlock {
    public FenceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockItem createBlockItem() {
        return new BlockItem(this, this.getItemProperties());
    }

    @Override
    public Item.Properties getItemProperties() {
        return new Item.Properties().group(ActuallyAdditions.ACTUALLY_GROUP);
    }
}
