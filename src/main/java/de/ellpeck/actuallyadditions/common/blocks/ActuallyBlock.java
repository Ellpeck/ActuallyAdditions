package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ActuallyBlock extends Block implements IActuallyBlock {
    public ActuallyBlock(Properties properties) {
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
