/*
 * This file ("TreasureChestLoot.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class TreasureChestLoot extends WeightedRandom.Item{

    public final ItemStack returnItem;
    public final int minAmount;
    public final int maxAmount;

    public TreasureChestLoot(ItemStack returnItem, int chance, int minAmount, int maxAmount){
        super(chance);
        this.returnItem = returnItem;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

}
