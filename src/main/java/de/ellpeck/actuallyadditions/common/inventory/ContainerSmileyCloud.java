package de.ellpeck.actuallyadditions.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerSmileyCloud extends Container {

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}