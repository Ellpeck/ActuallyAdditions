/*
 * This file ("ItemCoffeeBean.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemCoffeeBean extends ItemFoodBase{

    public ItemCoffeeBean(String name){
        super(1, 1F, false, name);
        this.setMaxDamage(0);
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
}