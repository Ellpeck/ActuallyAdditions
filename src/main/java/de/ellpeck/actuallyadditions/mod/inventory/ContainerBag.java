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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotDeletion;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotImmovable;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotItemHandlerUnconditioned;
import de.ellpeck.actuallyadditions.mod.items.ItemBag;
import de.ellpeck.actuallyadditions.mod.items.ItemDrill;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.tile.FilterSettings;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ContainerBag extends Container implements IButtonReactor {

    public final FilterSettings filter = new FilterSettings(4, false, true, false, false, 0, -1000);
    private final ItemStackHandlerAA bagInventory;
    private final PlayerInventory inventory;
    public final boolean isVoid;
    public boolean autoInsert;
    private boolean oldAutoInsert;
    private final ItemStack sack;

    public static ContainerBag fromNetwork(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new ContainerBag(windowId, inv, data.readItem(), data.readBoolean());
    }

    public ContainerBag(int windowId, PlayerInventory inventory, ItemStack sack, boolean isVoid) {
        super(ActuallyContainers.BAG_CONTAINER.get(), windowId);

        this.inventory = inventory;
        this.bagInventory = new ItemStackHandlerAA(getSlotAmount(isVoid), (slot, stack, automation) -> !isBlacklisted(stack), ItemStackHandlerAA.REMOVE_TRUE);
        this.isVoid = isVoid;
        this.sack = sack;

        for (int i = 0; i < 4; i++) {
            this.addSlot(new SlotFilter(this.filter, i, 155, 10 + i * 18));
        }

        if (this.isVoid) {
            this.addSlot(new SlotDeletion(this.bagInventory, 0, 64, 65) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return ContainerBag.this.filter.check(stack);
                }
            });
        } else {
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
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            if (i == inventory.selected) {
                this.addSlot(new SlotImmovable(inventory, i, 8 + i * 18, 152));
            } else {
                this.addSlot(new Slot(inventory, i, 8 + i * 18, 152));
            }
        }

        ItemStack stack = inventory.getSelected();
        if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemBag) {
            ItemDrill.loadSlotsFromNBT(this.bagInventory, inventory.getSelected());
            if (stack.hasTag()) {
                CompoundNBT compound = stack.getOrCreateTag();
                this.filter.readFromNBT(compound, "Filter");
                this.autoInsert = compound.getBoolean("AutoInsert");
            }
        }
    }

    public static int getSlotAmount(boolean isVoid) {
        return isVoid
            ? 1
            : 28;
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
            this.filter.respectMeta = data == 1;
        } else if (id == 2) {
            this.filter.respectNBT = data == 1;
        } else if (id == 3) {
            this.filter.respectOredict = data;
        } else if (id == 4) {
            this.autoInsert = data == 1;
        } else if (id == 5) {
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
                if (this.isVoid || !this.filter.check(newStack) || !this.moveItemStackTo(newStack, 4, 32, false)) {
                    if (slot >= inventoryStart && slot <= inventoryEnd) {
                        if (!this.moveItemStackTo(newStack, hotbarStart, hotbarEnd + 1, false)) {
                            return StackUtil.getEmpty();
                        }
                    } else if (slot >= inventoryEnd + 1 && slot < hotbarEnd + 1 && !this.moveItemStackTo(newStack, inventoryStart, inventoryEnd + 1, false)) {
                        return StackUtil.getEmpty();
                    }
                }
                //

            } else if (!this.moveItemStackTo(newStack, inventoryStart, hotbarEnd + 1, false)) {
                return StackUtil.getEmpty();
            }

            if (!StackUtil.isValid(newStack)) {
                theSlot.set(StackUtil.getEmpty());
            } else {
                theSlot.setChanged();
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
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (SlotFilter.checkFilter(this, slotId, player)) {
            return StackUtil.getEmpty();
        } else if (clickTypeIn == ClickType.SWAP && dragType == this.inventory.selected) {
            return ItemStack.EMPTY;
        } else {
            return super.clicked(slotId, dragType, clickTypeIn, player);
        }
    }

    @Override
    public void removed(PlayerEntity player) {
        ItemStack stack = this.inventory.getSelected();
        if (StackUtil.isValid(stack) && stack.getItem() instanceof ItemBag) {
            ItemDrill.writeSlotsToNBT(this.bagInventory, this.inventory.getSelected());
            CompoundNBT compound = stack.getOrCreateTag();
            this.filter.writeToNBT(compound, "Filter");
            compound.putBoolean("AutoInsert", this.autoInsert);
        }
        super.removed(player);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return !this.sack.isEmpty() && player.getMainHandItem() == this.sack;
    }

    @Override
    public void onButtonPressed(int buttonID, PlayerEntity player) {
        if (buttonID == 0) {
            this.autoInsert = !this.autoInsert;
        } else {
            this.filter.onButtonPressed(buttonID);
        }
    }

    private static final List<Pair<Item, Integer>> BLACKLIST = new ArrayList<>();

    private static boolean runOnce = false;

    // TODO: [port] FIX THIS
    public static boolean isBlacklisted(ItemStack stack) {
        if (!runOnce) {
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
        return BLACKLIST.contains(Pair.of(stack.getItem(), 0));
    }
}
