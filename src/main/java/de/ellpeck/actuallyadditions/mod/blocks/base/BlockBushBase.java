package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;

public class BlockBushBase extends BushBlock implements IBaseBlock {
    
    public BlockBushBase(Properties properties) {
        super(properties.sound(SoundType.PLANT));
    }
    
    @Override
    public BlockItemBase getItemBlock(){
        return new BlockItemBase(this, new Item.Properties());
    }
    
}
