package de.ellpeck.actuallyadditions.common.container;

import de.ellpeck.actuallyadditions.common.tiles.FeederTileEntity;
import de.ellpeck.actuallyadditions.common.utilities.ContainerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Objects;

public class FeederContainer extends Container {
    private final PlayerInventory inv;
    public final FeederTileEntity tile;

    public static FeederContainer fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new FeederContainer(windowId, inv, (FeederTileEntity) Objects.requireNonNull(inv.player.world.getTileEntity(data.readBlockPos())));
    }

    public FeederContainer(int windowId, PlayerInventory inv, FeederTileEntity tile) {
        super(ActuallyContainers.FEEDER_CONTAINER.get(), windowId);

        this.inv = inv;
        this.tile = tile;

        ContainerHelper.setupPlayerInventory(new InvWrapper(inv), 0, ContainerHelper.DEFAULT_SLOTS_X, 132, this::addSlot);

        this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(e -> addSlot(new SlotItemHandler(e, 0, 80, 45)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot fromSlot = this.inventorySlots.get(index);

        if (fromSlot == null || !fromSlot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack fromStack = fromSlot.getStack();

        if (index > ContainerHelper.PLAYER_INVENTORY_END_SLOT) {
            if (!this.mergeItemStack(fromStack, 0, ContainerHelper.PLAYER_INVENTORY_END_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!this.mergeItemStack(fromStack, ContainerHelper.PLAYER_INVENTORY_END_SLOT + 1, ContainerHelper.PLAYER_INVENTORY_END_SLOT + 2, false)) {
                return ItemStack.EMPTY;
            }
        }

        return fromStack;
    }
}
