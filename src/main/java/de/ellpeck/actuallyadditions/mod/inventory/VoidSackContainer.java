package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.components.FilterSettingsComponent;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotDeletion;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class VoidSackContainer extends AbstractContainerMenu implements IButtonReactor {
    public FilterSettings filter;

    private final ItemStackHandlerAA bagInventory;
    private final Inventory inventory;
    public boolean autoInsert;

    public static VoidSackContainer fromNetwork(int windowId, Inventory inv, RegistryFriendlyByteBuf data) {
        boolean insert = data.readBoolean();
        int filterOptions = data.readInt();
        return new VoidSackContainer(windowId, inv, insert, new FilterSettings(4, filterOptions));
    }

    public VoidSackContainer(int pContainerId, Inventory  pPlayerInventory, boolean autoInsert, FilterSettings filterIn) {
        super(ActuallyContainers.VOID_SACK_CONTAINER.get(), pContainerId);

        this.filter = filterIn;
        this.inventory = pPlayerInventory;
        this.bagInventory = new ItemStackHandlerAA(1);
        this.autoInsert = autoInsert;

        for (int c = 0; c < 4; c++) {
            this.addSlot(new SlotFilter(this.filter, c, 98 + c * 17, 17));
        }

        this.addSlot(new SlotDeletion(this.bagInventory, 0, 13, 18) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return VoidSackContainer.this.filter.check(stack);
            }
        });

        // Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 47 + row * 18));
            }
        }

        // Player Hotbar
        for (int i = 0; i < 9; i++) {
            if (i == inventory.selected) {
                this.addSlot(new SlotImmovable(inventory, i, 8 + i * 18, 105));
            } else {
                this.addSlot(new Slot(inventory, i, 8 + i * 18, 105));
            }
        }
    }

    @Override
    public void clicked(int slotId, int dragType, @Nonnull ClickType clickTypeIn, @Nonnull Player player) {
        if (SlotFilter.checkFilter(this, slotId, player)) {
            return;
        } else if (clickTypeIn == ClickType.SWAP && dragType == this.inventory.selected) {
            return;
        } else {
            super.clicked(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void removed(@Nonnull Player player) {
        ItemStack stack = this.inventory.getSelected();
        stack.set(ActuallyComponents.AUTO_INSERT, this.autoInsert);
        stack.set(ActuallyComponents.FILTER_SETTINGS, new FilterSettingsComponent(filter));
        super.removed(player);
    }

    @Override
    public void onButtonPressed(int buttonID, Player player) {
        if (buttonID == 0) {
            this.autoInsert = !this.autoInsert;
        } else {
            this.filter.onButtonPressed(buttonID - 1);
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {
        return ItemStack.EMPTY; //TODO old behavior was shift clicking moved stacks around the normal inventory
    }

    @Override
    public boolean stillValid(@Nonnull Player pPlayer) {
        return true;
    }
}
