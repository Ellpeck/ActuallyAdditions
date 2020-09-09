package de.ellpeck.actuallyadditions.items;

import de.ellpeck.actuallyadditions.items.base.ItemBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemLaserRelayUpgrade extends ItemBase {

    public ItemLaserRelayUpgrade(String name) {
        super(name);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}
