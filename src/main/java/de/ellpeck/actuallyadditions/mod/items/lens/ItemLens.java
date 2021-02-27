/*
 * This file ("ItemLens.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;

public class ItemLens extends ItemBase implements ILensItem {

    private final Lens type;

    public ItemLens(Lens type) {
        super(InitItems.defaultProps().maxStackSize(1));
        this.type = type;
    }

    @Override
    public Lens getLens() {
        return this.type;
    }
}
