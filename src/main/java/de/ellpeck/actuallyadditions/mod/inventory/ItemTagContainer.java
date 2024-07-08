package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.components.ActuallyComponents;
import de.ellpeck.actuallyadditions.mod.items.ItemTag;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemTagContainer extends AbstractContainerMenu implements IButtonReactor {
    public ItemTagContainer(int windowId, Inventory playerInventory) {
        super(ActuallyContainers.ITEM_TAG_CONTAINER.get(), windowId);
    }

    public static ItemTagContainer fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        return new ItemTagContainer(windowId, inv);
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@Nonnull Player pPlayer) {
        return pPlayer.getInventory().getSelected().getItem() instanceof ItemTag;
    }

    @Override
    public void onButtonPressed(int buttonID, Player player) {
        ItemStack stack = player.getInventory().getSelected();
	    stack.remove(ActuallyComponents.ITEM_TAG);
    }
}
