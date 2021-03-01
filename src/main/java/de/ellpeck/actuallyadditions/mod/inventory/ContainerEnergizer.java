/*
 * This file ("ContainerEnergizer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotOutput;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnergizer;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.Objects;

public class ContainerEnergizer extends Container {

    public static final EquipmentSlotType[] VALID_EQUIPMENT_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
    public final TileEntityEnergizer energizer;

    public static ContainerEnergizer fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new ContainerEnergizer(windowId, inv, (TileEntityEnergizer) Objects.requireNonNull(inv.player.world.getTileEntity(data.readBlockPos())));
    }

    public ContainerEnergizer(int windowId, PlayerInventory inventory, TileEntityEnergizer tile) {
        super(ActuallyContainers.ENERGIZER_CONTAINER.get(), windowId);
        this.energizer = tile;

        this.addSlot(new SlotItemHandlerUnconditioned(this.energizer.inv, 0, 76, 73) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return super.isItemValid(stack) && stack.getCapability(CapabilityEnergy.ENERGY, null).isPresent();
            }
        });
        this.addSlot(new SlotOutput(this.energizer.inv, 1, 76, 42));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 155));
        }

        for (int k = 0; k < 4; ++k) {
            EquipmentSlotType slot = VALID_EQUIPMENT_SLOTS[k];
            this.addSlot(new Slot(inventory, 36 + 3 - k, 102, 19 + k * 18) {
                @Override
                public int getSlotStackLimit() {
                    return 1;
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    return StackUtil.isValid(stack) && stack.getItem() instanceof ArmorItem;
                }

                @Override
                public boolean canTakeStack(PlayerEntity player) {
                    ItemStack itemstack = this.getStack();
                    return (itemstack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(player);
                }

                // TODO: [port] add back

                //                @Override
                //                @OnlyIn(Dist.CLIENT)
                //                public String getSlotTexture() {
                //                    return Armor.EMPTY_SLOT_NAMES[slot.getIndex()];
                //                }
            });
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slot) {
        int inventoryStart = 2;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.inventorySlots.get(slot);

        if (theSlot != null && theSlot.getHasStack()) {
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if (slot == 1) {
                if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, true)) {
                    return StackUtil.getEmpty();
                }
                theSlot.onSlotChange(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if (slot >= inventoryStart) {
                //Shift from Inventory
                if (newStack.getCapability(CapabilityEnergy.ENERGY, null).isPresent()) {
                    if (!this.mergeItemStack(newStack, 0, 1, false)) {
                        return StackUtil.getEmpty();
                    }
                }
                //

                else if (slot >= inventoryStart && slot <= inventoryEnd) {
                    if (!this.mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false)) {
                        return StackUtil.getEmpty();
                    }
                } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false)) {
                    return StackUtil.getEmpty();
                }
            } else if (!this.mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false)) {
                return StackUtil.getEmpty();
            }

            if (!StackUtil.isValid(newStack)) {
                theSlot.putStack(StackUtil.getEmpty());
            } else {
                theSlot.onSlotChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) {
                return StackUtil.getEmpty();
            }
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return StackUtil.getEmpty();
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return this.energizer.canPlayerUse(player);
    }
}
