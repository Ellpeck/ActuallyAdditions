/*
 * This file ("ContainerFireworkBox.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ContainerFireworkBox extends AbstractContainerMenu {

    public static ContainerFireworkBox fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        return new ContainerFireworkBox(windowId, inv);
    }

    public ContainerFireworkBox(int windowId, Inventory inventory) {
        super(ActuallyContainers.FIREWORK_BOX_CONTAINER.get(), windowId);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }
}
