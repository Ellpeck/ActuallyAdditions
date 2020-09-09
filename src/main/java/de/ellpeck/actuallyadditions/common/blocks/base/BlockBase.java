package de.ellpeck.actuallyadditions.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockBase extends Block implements IBaseBlock{
    
    public BlockBase(Properties properties) {
        super(properties);
    }

    @Override
    public BlockItemBase getItemBlock() {
        return new BlockItemBase(this, new Item.Properties());
    }
    
}
