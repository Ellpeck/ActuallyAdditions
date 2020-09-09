package de.ellpeck.actuallyadditions.common.items.lens;

import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.common.items.base.ItemBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemLens extends ItemBase implements ILensItem {

    private final Lens type;

    public ItemLens(String name, Lens type) {
        super(name);
        this.type = type;
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public Lens getLens() {
        return this.type;
    }
}
