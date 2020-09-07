package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemCoffeeBean extends ItemFoodBase {

    public ItemCoffeeBean(String name) {
        super(1, 1F, false, name);
        this.setMaxDamage(0);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}