package de.ellpeck.actuallyadditions.blocks.base;

import net.minecraft.item.Rarity;

public interface IBaseBlock {
    
    BlockItemBase getItemBlock();
    
    default boolean shouldAddCreative() {
        return true;
    }
    
    default Rarity getRarity() {
        return Rarity.COMMON;
    }
    
}
