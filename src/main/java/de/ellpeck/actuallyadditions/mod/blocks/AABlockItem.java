package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;

public class AABlockItem extends BlockItem {
    public AABlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    public static class AASeedItem extends BlockNamedItem {
        public AASeedItem(Block block, Properties properties) {
            super(block, properties);
        }

        @Override
        public String getDescriptionId() {
            return this.getOrCreateDescriptionId();
        }
    }
}
