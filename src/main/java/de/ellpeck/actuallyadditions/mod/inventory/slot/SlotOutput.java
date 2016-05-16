/*
 * This file ("SlotOutput.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot{

    public SlotOutput(IInventory inventory, int id, int x, int y){
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack){
        return false;
    }
}
