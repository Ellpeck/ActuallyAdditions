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

import de.ellpeck.actuallyadditions.mod.attachments.ActuallyAttachments;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerFilter;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.items.DrillItem;
import de.ellpeck.actuallyadditions.mod.items.ItemTag;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FilterSettings {
    public final ItemStackHandlerAA filterInventory;
    public boolean isWhitelist;
    public boolean respectMod;
    public boolean matchDamage;
    public boolean matchNBT;
    private boolean lastWhitelist;
    private boolean lastRespectMod;
    private boolean lastMatchDamage;
    private boolean lastMatchNBT;

    public enum Buttons {
        WHITELIST,
        MOD,
        DAMAGE,
        NBT
    }

    public FilterSettings(int slots, boolean defaultWhitelist, boolean defaultRespectMod, boolean defaultMatchDamage, boolean defaultMatchNBT) {
        this.filterInventory = new ItemStackHandlerAA(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                FilterSettings.this.onContentsChanged();
            }
        };

        this.isWhitelist = defaultWhitelist;
        this.respectMod = defaultRespectMod;
        this.matchDamage = defaultMatchDamage;
        this.matchNBT = defaultMatchNBT;
    }

    public void onContentsChanged() {}

    public static boolean check(ItemStack stack, ItemStackHandlerAA filter, boolean whitelist, boolean mod, boolean damage, boolean nbt) {
        if (!stack.isEmpty()) {
            for (int i = 0; i < filter.getSlots(); i++) {
                ItemStack slot = filter.getStackInSlot(i);

                if (!slot.isEmpty()) {
                    if (SlotFilter.isFilter(slot)) {
                        ItemStackHandlerAA inv = new ItemStackHandlerAA(ContainerFilter.SLOT_AMOUNT);
                        DrillItem.loadSlotsFromNBT(inv, slot);
                        for (int k = 0; k < inv.getSlots(); k++) {
                            ItemStack filterSlot = inv.getStackInSlot(k);
                            if (!filterSlot.isEmpty() && areEqualEnough(filterSlot, stack, mod, damage, nbt)) {
                                return whitelist;
                            }
                        }
                    }
                    else if (slot.getItem() instanceof ItemTag) {
                        var data = slot.getExistingData(ActuallyAttachments.ITEM_TAG);
                        if (data.isPresent()) {
                            var tag = data.get().getTag();
                            if (tag.isPresent()) {
                                if (stack.is(tag.get())) {
                                    return whitelist;
                                }
                            }
                        }
                    }
                    else if (areEqualEnough(slot, stack, mod, damage, nbt)) {
                        return whitelist;
                    }
                }
            }
        }
        return !whitelist;
    }

    private static boolean areEqualEnough(ItemStack first, ItemStack second, boolean mod, boolean damage, boolean nbt) {
        if (mod)
            return checkMod(first, second) && checkDamage(first, second, damage);

        return checkItem(first, second, nbt) && checkDamage(first, second, damage);
    }

    public static boolean checkDamage(ItemStack first, ItemStack second, boolean damage) {
        return !damage || first.getDamageValue() == second.getDamageValue();
    }

    public static boolean checkItem(ItemStack first, ItemStack second, boolean nbt) {
        return nbt? ItemStack.isSameItemSameTags(first, second) : ItemStack.isSameItem(first, second);
    }

    public static boolean checkMod(ItemStack first, ItemStack second) {
        return BuiltInRegistries.ITEM.getKey(first.getItem()).getNamespace().equals(BuiltInRegistries.ITEM.getKey(second.getItem()).getNamespace());
    }

    public void writeToNBT(CompoundTag tag, String name) {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("Whitelist", this.isWhitelist);
        compound.putBoolean("Mod", this.respectMod);
        compound.putBoolean("Damage", this.matchDamage);
        compound.putBoolean("NBT", this.matchNBT);
        compound.put("Items", filterInventory.serializeNBT());
        tag.put(name, compound);
    }

    public void readFromNBT(CompoundTag tag, String name) {
        CompoundTag compound = tag.getCompound(name);
        this.isWhitelist = compound.getBoolean("Whitelist");
        this.respectMod = compound.getBoolean("Mod");
        this.matchDamage = compound.getBoolean("Damage");
        this.matchNBT = compound.getBoolean("NBT");
        this.filterInventory.deserializeNBT(compound.getCompound("Items"));
    }

    public boolean needsUpdateSend() {
        return this.lastWhitelist != this.isWhitelist || this.lastRespectMod != this.respectMod || this.lastMatchDamage != this.matchDamage || this.lastMatchNBT != this.matchNBT;
    }

    public void updateLasts() {
        this.lastWhitelist = this.isWhitelist;
        this.lastRespectMod = this.respectMod;
        this.lastMatchDamage = this.matchDamage;
        this.lastMatchNBT = this.matchNBT;
    }

    public void onButtonPressed(int id) {
        if (id == Buttons.WHITELIST.ordinal()) {
            this.isWhitelist = !this.isWhitelist;
        } else if (id == Buttons.MOD.ordinal()) {
            this.respectMod = !this.respectMod;
        } else if (id == Buttons.DAMAGE.ordinal()) {
            this.matchDamage = !this.matchDamage;
        } else if (id == Buttons.NBT.ordinal()) {
            this.matchNBT = !this.matchNBT;
        }
    }

    public boolean check(ItemStack stack) {
        return !this.needsCheck() || check(stack, this.filterInventory, this.isWhitelist, this.respectMod, this.matchDamage, this.matchNBT);
    }

    public boolean needsCheck() {
        for (int i = 0; i < this.filterInventory.getSlots(); i++) {
            if (!this.filterInventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return this.isWhitelist;
    }
}
