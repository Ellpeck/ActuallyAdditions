package de.ellpeck.actuallyadditions.common.container;

import de.ellpeck.actuallyadditions.common.items.useables.DrillItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class DrillContainer extends InventoryContainer {
    public DrillContainer(int windowId, PlayerInventory inv, PacketBuffer data) {
        super(windowId, inv, data);
    }

    public DrillContainer(int windowId, PlayerInventory inv) {
        super(windowId, inv);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        // Close if the player is not holding the item
        return playerIn.getHeldItemMainhand().getItem() instanceof DrillItem;
    }

    @Override
    protected void addContainerSlots(int index) {
//        addSlot(new Slot());
    }

    @Override
    protected int getSlotX() {
        return DEFAULT_SLOTS_X;
    }

    @Override
    protected int getSlotY() {
        return 116;
    }
}
