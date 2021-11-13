/*
 * This file ("ItemDust.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheDusts;


public class ItemDust extends ItemBase {

    public static final TheDusts[] ALL_DUSTS = TheDusts.values();

    public ItemDust() {
        //super(name);
    }

/*
    @Override
    public Rarity getRarity(ItemStack stack) {
        return stack.getItemDamage() >= ALL_DUSTS.length
                ? EnumRarity.COMMON
                : ALL_DUSTS[stack.getItemDamage()].rarity;
    }

 */

    /*
    @Override
    public int getItemBurnTime(ItemStack stack) {
        if (stack.getMetadata() == 6) {
            return 1200;
        }
        return super.getItemBurnTime(stack);
    }

     */
}
