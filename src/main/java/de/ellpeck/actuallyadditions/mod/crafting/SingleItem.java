package de.ellpeck.actuallyadditions.mod.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SingleItem implements Container {
    private final ItemStack itemStack;

    public SingleItem(ItemStack itemStack) {
        this.itemStack = itemStack.copy();
        this.itemStack.setCount(1);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return itemStack;
    }

    public ItemStack getItem() {
        return itemStack;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
