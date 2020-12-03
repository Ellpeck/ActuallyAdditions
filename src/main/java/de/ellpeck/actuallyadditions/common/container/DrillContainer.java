package de.ellpeck.actuallyadditions.common.container;

import de.ellpeck.actuallyadditions.common.items.useables.DrillItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;

public class DrillContainer extends Container {
    private PlayerInventory inv;

    public DrillContainer(int windowId, PlayerInventory inv, PacketBuffer data) {
        this(windowId, inv);
    }

    public DrillContainer(int windowId, PlayerInventory inv) {
        super(ActuallyContainers.DRILL_CONTAINER.get(), windowId);
        this.inv = inv;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        // Close if the player is not holding the item
        return playerIn.getHeldItemMainhand().getItem() instanceof DrillItem;
    }
}
