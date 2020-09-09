package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BlockItemBase extends BlockItem {

    public BlockItemBase(Block block) {
        super(block, new Properties());
        this.setRegistryName(block.getRegistryName());
    }
    
}