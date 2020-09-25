package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

@Deprecated
public class BallOfFurReturn extends WeightedRandom.Item {

    public final ItemStack returnItem;

    public BallOfFurReturn(ItemStack returnItem, int chance) {
        super(chance);
        this.returnItem = returnItem;
    }

}
