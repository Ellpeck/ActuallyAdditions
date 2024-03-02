/*
 * This file ("ItemCrystal.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.world.item.ItemStack;

public class ItemCrystal extends ItemBase {
    private final boolean isEmpowered;

    public ItemCrystal(boolean isEmpowered) {
        super();
        this.isEmpowered = isEmpowered;
    }

    public ItemCrystal() {
        this.isEmpowered = false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.isEmpowered;
    }

    //    @Override
    //    public String getTranslationKey(ItemStack stack) {
    //        return stack.getItemDamage() >= BlockCrystal.ALL_CRYSTALS.length
    //            ? StringUtil.BUGGED_ITEM_NAME
    //            : this.getTranslationKey() + "_" + BlockCrystal.ALL_CRYSTALS[stack.getItemDamage()].name;
    //    }
}
