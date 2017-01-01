/*
 * This file ("SlotImmovable.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotImmovable extends Slot{

    public SlotImmovable(IInventory inventory, int id, int x, int y){
        super(inventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack){
        return false;
    }

    @Override
    public void putStack(ItemStack stack){

    }


    @Override
    public ItemStack decrStackSize(int i){
        return null;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player){
        return false;
    }
}
