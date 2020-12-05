package de.ellpeck.actuallyadditions.common.container;

import de.ellpeck.actuallyadditions.common.items.misc.DrillAugmentItem;
import de.ellpeck.actuallyadditions.common.items.useables.DrillItem;
import de.ellpeck.actuallyadditions.common.utilities.ContainerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class DrillContainer extends Container {
    private final ItemStackHandler handler;
    private final PlayerInventory inv;
    private final ItemStack stack;

    public static DrillContainer fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        // Seeing as this container only works on the main hand, this works nicely
        return new DrillContainer(windowId, inv, inv.player.getHeldItemMainhand());
    }

    public DrillContainer(int windowId, PlayerInventory inv, ItemStack stack) {
        super(ActuallyContainers.DRILL_CONTAINER.get(), windowId);

        this.inv = inv;
        this.stack = stack;

        ContainerHelper.setupPlayerInventory(new InvWrapper(inv), 0, ContainerHelper.DEFAULT_SLOTS_X, 116, this::addSlot);

        this.handler = new ItemStackHandler(5) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof DrillAugmentItem;
            }
        };

        this.handler.deserializeNBT(this.stack.getOrCreateChildTag("augments"));

        this.addContainerSlots();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        // Close if the player is not holding the item
        return playerIn.getHeldItemMainhand().getItem() instanceof DrillItem;
    }

    protected void addContainerSlots() {
        for (int i = 0; i < 5; i ++) {
            addSlot(new SlotItemHandler(handler, i, 44 +  (i * ContainerHelper.SLOT_SPACING), 19));
        }
    }

    @Nonnull
    @Override
    public ItemStack slotClick(int slotId, int dragType, @Nonnull ClickType clickTypeIn, @Nonnull PlayerEntity player) {
        // Don't let the user pickup, throw or swap the drill, any drill
        if ((clickTypeIn == ClickType.SWAP || clickTypeIn == ClickType.THROW || clickTypeIn == ClickType.PICKUP)
                && slotId >= 0
                && slotId <= this.inventorySlots.size()
                && inv.getStackInSlot(slotId).getItem() instanceof DrillItem) {
            return ItemStack.EMPTY;
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot fromSlot = this.inventorySlots.get(index);

        if (fromSlot == null || !fromSlot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack fromStack = fromSlot.getStack();

        // Moves items from the Drill to the players inventory
        if (index > ContainerHelper.PLAYER_INVENTORY_END_SLOT) {
            if (!this.mergeItemStack(fromStack, 0, ContainerHelper.PLAYER_INVENTORY_END_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Moves the item from the players inventory to the drill
            if (!this.mergeItemStack(fromStack, ContainerHelper.PLAYER_INVENTORY_END_SLOT + 1, ContainerHelper.PLAYER_INVENTORY_END_SLOT + 6, false)) {
                return ItemStack.EMPTY;
            }
        }

        return fromStack;
    }

    @Override
    public void onContainerClosed(@Nonnull PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);

        this.stack.getOrCreateTag().put("augments", this.handler.serializeNBT());
    }
}
