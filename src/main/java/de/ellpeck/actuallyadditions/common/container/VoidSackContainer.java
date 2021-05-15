package de.ellpeck.actuallyadditions.common.container;

import de.ellpeck.actuallyadditions.common.items.useables.VoidSack;
import de.ellpeck.actuallyadditions.common.utilities.ContainerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.wrapper.InvWrapper;

public class VoidSackContainer extends Container {
    ItemStack stack;
    public VoidSackContainer(int windowId, PlayerInventory inv, ItemStack stackIn) {
        super(ActuallyContainers.VOID_SACK_CONTAINER.get(), windowId);
        this.stack = stackIn;


        ContainerHelper.setupPlayerInventory(new InvWrapper(inv), 0, ContainerHelper.DEFAULT_SLOTS_X, 105, this::addSlot);

        addSlot(new Slot(new VoidInventory(), ContainerHelper.PLAYER_INVENTORY_END_SLOT+1, 13,18));
    }

    public static VoidSackContainer fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new VoidSackContainer(windowId, inv, inv.player.getHeldItemMainhand());
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return playerIn.getHeldItemMainhand().getItem() instanceof VoidSack;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot fromSlot = this.inventorySlots.get(index);

        if (fromSlot == null || !fromSlot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack fromStack = fromSlot.getStack();

        // Voids an item that is shift clicked...
        if (index <= ContainerHelper.PLAYER_INVENTORY_END_SLOT && !(fromStack.getItem() instanceof VoidSack)) {
            ItemStack copied = fromStack.copy();
            fromStack.shrink(fromStack.getCount());
            return copied;
        }

        return ItemStack.EMPTY;
    }

    // Possibly cursed, but does the job
    private static class VoidInventory implements IInventory {
        @Override
        public int getSizeInventory() { return 1; }
        @Override
        public boolean isEmpty() { return true; }
        @Override
        public ItemStack getStackInSlot(int index) { return ItemStack.EMPTY; }
        @Override
        public ItemStack decrStackSize(int index, int count) { return ItemStack.EMPTY; }
        @Override
        public ItemStack removeStackFromSlot(int index) { return ItemStack.EMPTY; }
        @Override
        public void setInventorySlotContents(int index, ItemStack stack) {}
        @Override
        public void markDirty() {}
        @Override
        public boolean isUsableByPlayer(PlayerEntity player) { return true; }
        @Override
        public void clear() {}
    }
}
