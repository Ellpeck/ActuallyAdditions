package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

@Deprecated
public class ItemBlockBase extends BlockItem {

    public ItemBlockBase(Block block, Item.Properties properties) {
        super(block, properties);
//        this.setHasSubtypes(false);
//        this.setMaxDamage(0);
    }

//    @Override
//    public String getTranslationKey(ItemStack stack) {
//        return this.getTranslationKey();
//    }
//
//    @Override
//    public int getMetadata(int damage) {
//        return damage;
//    }
}