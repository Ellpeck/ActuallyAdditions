package de.ellpeck.actuallyadditions.items;

import de.ellpeck.actuallyadditions.items.base.ItemBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemGeneric extends ItemBase {

    public ItemGeneric(String name) {
        super(name);
        this.setMaxDamage(0);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}