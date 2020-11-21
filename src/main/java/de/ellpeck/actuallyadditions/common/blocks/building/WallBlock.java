package de.ellpeck.actuallyadditions.common.blocks.building;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.IActuallyBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class WallBlock extends net.minecraft.block.WallBlock implements IActuallyBlock {
    public WallBlock(Properties properties) {
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
