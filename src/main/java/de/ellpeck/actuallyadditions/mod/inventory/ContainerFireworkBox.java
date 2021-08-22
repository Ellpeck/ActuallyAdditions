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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ContainerFireworkBox extends Container {

    public static ContainerFireworkBox fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new ContainerFireworkBox(windowId, inv);
    }

    public ContainerFireworkBox(int windowId, PlayerInventory inventory) {
        super(ActuallyContainers.FIREWORK_BOX_CONTAINER.get(), windowId);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }
}
