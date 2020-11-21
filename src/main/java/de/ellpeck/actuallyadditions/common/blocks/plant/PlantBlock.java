package de.ellpeck.actuallyadditions.common.blocks.plant;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.IActuallyBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class PlantBlock extends CropsBlock implements IActuallyBlock {
    public PlantBlock(int minDropAmount, int maxDropAmount) {
        super(Properties.create(Material.ROCK));
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
