package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class TreasureChestLoot extends WeightedRandom.Item {

    public final ItemStack returnItem;
    public final int minAmount;
    public final int maxAmount;

    public TreasureChestLoot(ItemStack returnItem, int chance, int minAmount, int maxAmount) {
        super(chance);
        this.returnItem = returnItem;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

}
