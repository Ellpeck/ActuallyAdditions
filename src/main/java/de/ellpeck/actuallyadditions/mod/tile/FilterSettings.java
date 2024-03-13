/*
 * This file ("FilterSettings.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.items.DrillItem;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FilterSettings {
    public final ItemStackHandlerAA filterInventory;
    public boolean isAllowFilter;
    public boolean respectMod;
    private boolean lastWhitelist;
    private boolean lastRespectMod;

    public enum Buttons {
        WHITELIST,
        MOD
    }

    public FilterSettings(int slots, boolean defaultWhitelist, boolean defaultRespectMod) {
        this.filterInventory = new ItemStackHandlerAA(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                FilterSettings.this.onContentsChanged();
            }
        };

        this.isAllowFilter = defaultWhitelist;
        this.respectMod = defaultRespectMod;
    }

    public void onContentsChanged() {}

    public static boolean check(ItemStack stack, ItemStackHandlerAA filter, boolean whitelist, boolean mod) {
        if (!stack.isEmpty()) {
            for (int i = 0; i < filter.getSlots(); i++) {
                ItemStack slot = filter.getStackInSlot(i);

                if (!slot.isEmpty()) {
                    if (SlotFilter.isFilter(slot)) {
                        ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerFilter.SLOT_AMOUNT);
                        DrillItem.loadSlotsFromNBT(inv, slot);
                        for (int k = 0; k < inv.getSlots(); k++) {
                            ItemStack filterSlot = inv.getStackInSlot(k);
                            if (!filterSlot.isEmpty() && areEqualEnough(filterSlot, stack, mod)) {
                                return whitelist;
                            }
                        }
                    } else if (areEqualEnough(slot, stack, mod)) {
                        return whitelist;
                    }
                }
            }
        }
        return !whitelist;
    }

    private static boolean areEqualEnough(ItemStack first, ItemStack second, boolean mod) {
        Item firstItem = first.getItem();
        Item secondItem = second.getItem();
        if (mod && BuiltInRegistries.ITEM.getKey(firstItem).getNamespace().equals(BuiltInRegistries.ITEM.getKey(secondItem).getNamespace())) {
            return true;
        }

        return firstItem == secondItem;
    }

    public void writeToNBT(CompoundTag tag, String name) {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("Whitelist", this.isAllowFilter);
        compound.putBoolean("Mod", this.respectMod);
        compound.put("Items", filterInventory.serializeNBT());
        tag.put(name, compound);
    }

    public void readFromNBT(CompoundTag tag, String name) {
        CompoundTag compound = tag.getCompound(name);
        this.isAllowFilter = compound.getBoolean("Whitelist");
        this.respectMod = compound.getBoolean("Mod");
        this.filterInventory.deserializeNBT(compound.getCompound("Items"));
    }

    public boolean needsUpdateSend() {
        return this.lastWhitelist != this.isAllowFilter || this.lastRespectMod != this.respectMod;
    }

    public void updateLasts() {
        this.lastWhitelist = this.isAllowFilter;
        this.lastRespectMod = this.respectMod;
    }

    public void onButtonPressed(int id) {
        if (id == Buttons.WHITELIST.ordinal()) {
            this.isAllowFilter = !this.isAllowFilter;
        } else if (id == Buttons.MOD.ordinal()) {
            this.respectMod = !this.respectMod;
        }
    }

    public boolean check(ItemStack stack) {
        return !this.needsCheck() || check(stack, this.filterInventory, this.isAllowFilter, this.respectMod);
    }

    public boolean needsCheck() {
        for (int i = 0; i < this.filterInventory.getSlots(); i++) {
            if (!this.filterInventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return this.isAllowFilter;
    }
}
