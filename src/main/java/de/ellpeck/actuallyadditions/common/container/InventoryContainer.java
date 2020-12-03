package de.ellpeck.actuallyadditions.common.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class InventoryContainer extends Container {
    public static final int DEFAULT_SLOTS_X = 8;

    protected final IItemHandler playerInventory;

    public InventoryContainer(int windowId, PlayerInventory inv, PacketBuffer data) {
        this(windowId, inv);
    }

    public InventoryContainer(int windowId, PlayerInventory inv) {
        super(ActuallyContainers.DRILL_CONTAINER.get(), windowId);
        this.playerInventory = new InvWrapper(inv);

        this.setupInventory();
    }

    private void setupInventory() {
        int index = 0, x = getSlotX(), y = getSlotY();

        // Build the players inventory first, building from bottom to top, right to left. The (i>0) magic handles the
        // space between the hotbar inventory and the players remaining inventory.
        for (int i = 0; i < 4; i++) {
            boolean isHotbar = i < 1;
            for (int j = 0; j < 9; j++) {
                addSlot(new SlotItemHandler(playerInventory, index, x + (j * 18), isHotbar ? y : ((y - 76) + (i * 18))));
                index++;
            }
        }

        this.addContainerSlots(index);
    }

    /**
     * This method will always carry on the index after setting up the players inventory
     */
    protected abstract void addContainerSlots(int index);

    protected abstract int getSlotX();
    protected abstract int getSlotY();
}
