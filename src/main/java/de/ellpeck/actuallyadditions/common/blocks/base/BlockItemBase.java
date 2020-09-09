package de.ellpeck.actuallyadditions.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {

    public <T extends Block & IBaseBlock> BlockItemBase(T block , Item.Properties properties) {
        super(block, properties.rarity(block.getRarity()));
    }
    
}