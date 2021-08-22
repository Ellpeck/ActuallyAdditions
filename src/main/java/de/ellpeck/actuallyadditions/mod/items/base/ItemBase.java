/*
 * This file ("ItemBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.item.Item;

import net.minecraft.item.Item.Properties;

public class ItemBase extends Item {
    public ItemBase(Properties props) {
        super(props);
    }

    public ItemBase() {
        super(ActuallyItems.defaultProps());
    }
}
