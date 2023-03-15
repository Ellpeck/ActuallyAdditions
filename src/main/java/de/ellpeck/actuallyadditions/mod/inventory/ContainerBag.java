/*
 * This file ("ContainerBag.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotDeletion;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.items.DrillItem;
import de.ellpeck.actuallyadditions.mod.items.ItemBag;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContainerBag extends Container implements IButtonReactor {

    public final FilterSettings filter = new FilterSettings(4, false, false, false);
    private final ItemStackHandlerAA bagInventory;
    private final PlayerInventory inventory;
    public boolean autoInsert;
    private boolean oldAutoInsert;

    public static final int SIZE = 28;

    public static ContainerBag fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new ContainerBag(windowId, inv, data.readUUID(), new ItemStackHandlerAA(28));
    }

    public ContainerBag(int windowId, PlayerInventory playerInventory, UUID uuid, ItemStackHandlerAA handler) {
        super(ActuallyContainers.BAG_CONTAINER.get(), windowId);

        this.inventory = playerInventory;
        this.bagInventory = handler; //new ItemStackHandlerAA(SIZE, (slot, stack, automation) -> !isBlacklisted(stack), ItemStackHandlerAA.REMOVE_TRUE);

        for (int i = 0; i < 4; i++) {
            this.addSlot(new SlotFilter(this.filter, i, 155, 10 + i * 18));
        }

        if (false) { // isvoid, move to its own container
            this.addSlot(new SlotDeletion(this.bagInventory, 0, 64, 65) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return ContainerBag.this.filter.check(stack);
                }
            });
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                this.addSlot(new SlotItemHandlerUnconditioned(this.bagInventory, j + i * 7, 10 + j * 18, 10 + i * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return !isBlacklisted(stack) && ContainerBag.this.filter.check(stack);
                    }
                });
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            if (i == playerInventory.selected) {
                this.addSlot(new SlotImmovable(playerInventory, i, 8 + i * 18, 152));
            } else {
                this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 152));
            }
        }

        ItemStack stack = playerInventory.getSelected();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemBag) {
            DrillItem.loadSlotsFromNBT(this.bagInventory, playerInventory.getSelected());
            if (stack.hasTag()) {
                CompoundNBT compound = stack.getOrCreateTag();
                this.filter.readFromNBT(compound, "Filter");
                this.autoInsert = compound.getBoolean("AutoInsert");
            }
        }
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        if (this.filter.needsUpdateSend() || this.autoInsert != this.oldAutoInsert) {
            /*
            for (IContainerListener listener : this.containerListeners) {
                listener.setContainerData(this, 0, this.filter.isWhitelist
                    ? 1
                    : 0);
                listener.setContainerData(this, 1, this.filter.respectMeta
                    ? 1
                    : 0);
                listener.setContainerData(this, 2, this.filter.respectNBT
                    ? 1
                    : 0);
                listener.setContainerData(this, 3, this.filter.respectOredict);
                listener.setContainerData(this, 4, this.autoInsert
                    ? 1
                    : 0);
                listener.setContainerData(this, 5, this.filter.respectMod
                    ? 1
                    : 0);
            }
             */
            this.filter.updateLasts();
            this.oldAutoInsert = this.autoInsert;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setData(int id, int data) {
        if (id == 0) {
            this.filter.isWhitelist = data == 1;
        } else if (id == 1) {
            this.filter.respectNBT = data == 1;
        } else if (id == 2) {
            this.autoInsert = data == 1;
        } else if (id == 3) {
            this.filter.respectMod = data == 1;
        }
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int slot) {
        int inventoryStart = this.bagInventory.getSlots() + 4;
        int inventoryEnd = inventoryStart + 26;
        int hotbarStart = inventoryEnd + 1;
        int hotbarEnd = hotbarStart + 8;

        Slot theSlot = this.slots.get(slot);

        if (theSlot != null && theSlot.hasItem()) {
            ItemStack newStack = theSlot.getItem();
            ItemStack currentStack = newStack.copy();

            //Other Slots in Inventory excluded
            if (slot >= inventoryStart) {
                //Shift from Inventory
                if (!this.filter.check(newStack) || !this.moveItemStackTo(newStack, 4, 32, false)) {
                    if (slot >= inventoryStart && slot <= inventoryEnd) {
                        if (!this.moveItemStackTo(newStack, hotbarStart, hotbarEnd + 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.moveItemStackTo(newStack, inventoryStart, inventoryEnd + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                //

            } else if (!this.moveItemStackTo(newStack, inventoryStart, hotbarEnd + 1, false)) {
                return ItemStack.EMPTY;
            }

            if (newStack.isEmpty()) {
                theSlot.set(ItemStack.EMPTY);
            } else {
                theSlot.setChanged();
            }

            if (newStack.getCount() == currentStack.getCount()) {
                return ItemStack.EMPTY;
            }
            theSlot.onTake(player, newStack);

            return currentStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (SlotFilter.checkFilter(this, slotId, player)) {
            return ItemStack.EMPTY;
        } else if (clickTypeIn == ClickType.SWAP && dragType == this.inventory.selected) {
            return ItemStack.EMPTY;
        } else {
            return super.clicked(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void removed(PlayerEntity player) {
        ItemStack stack = this.inventory.getSelected();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemBag) {
            DrillItem.writeSlotsToNBT(this.bagInventory, this.inventory.getSelected());
            CompoundNBT compound = stack.getOrCreateTag();
            this.filter.writeToNBT(compound, "Filter");
            compound.putBoolean("AutoInsert", this.autoInsert);
        }
        super.removed(player);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true; //!this.sack.isEmpty() && player.getMainHandItem() == this.sack; //TODO fix later
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        if (buttonID == 0) {
            this.autoInsert = !this.autoInsert;
        } else {
            //this.filter.onButtonPressed(buttonID); //TODO
        }
    }

    private static final List<Pair<Item, Integer>> BLACKLIST = new ArrayList<>();

    private static boolean runOnce = false;

    // TODO: [port] FIX THIS
    public static boolean isBlacklisted(ItemStack stack) {
        //TODO replace with modern tagging blocking etc
        return false;
/*        if (!runOnce) {
            runOnce = true;
            for (String s : ConfigStringListValues.SACK_BLACKLIST.getValue()) {
                String[] split = s.split("@");
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(split[0]));
                if (item == null) {
                    ActuallyAdditions.LOGGER.error("Invalid item in sack blacklist: " + s);
                    continue;
                }
                if (split.length == 1) {
                    BLACKLIST.add(Pair.of(item, 0));
                } else if (split.length == 2) {
                    BLACKLIST.add(Pair.of(item, Integer.parseInt(split[1])));
                }
            }
        }
        return BLACKLIST.contains(Pair.of(stack.getItem(), 0));*/
    }
}
