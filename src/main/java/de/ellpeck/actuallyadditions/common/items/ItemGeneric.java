package de.ellpeck.actuallyadditions.common.items;

import de.ellpeck.actuallyadditions.common.items.base.ItemBase;
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