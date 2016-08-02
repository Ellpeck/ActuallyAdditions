/*
 * This file ("SlotDeletion.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDeletion extends Slot{

    public SlotDeletion(IInventory inv, int slot, int x, int y){
        super(inv, slot, x, y);
    }

    @Override
    public void putStack(ItemStack stack){
        this.onSlotChanged();
    }

    @Override
    public boolean canTakeStack(EntityPlayer player){
        return false;
    }
}
