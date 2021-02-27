/*
 * This file ("TileEntityInventoryBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public abstract class TileEntityInventoryBase extends TileEntityBase {

    public final ItemStackHandlerAA inv;
    public final LazyOptional<IItemHandler> lazyInv;

    public TileEntityInventoryBase(TileEntityType<?> type, int slots) {
        super(type);
        this.inv = new TileStackHandler(slots);
        this.lazyInv = LazyOptional.of(() -> this.inv);
    }

    public static void saveSlots(IItemHandler slots, CompoundNBT compound) {
        if (slots != null && slots.getSlots() > 0) {
            ListNBT tagList = new ListNBT();
            for (int i = 0; i < slots.getSlots(); i++) {
                ItemStack slot = slots.getStackInSlot(i);
                CompoundNBT tagCompound = new CompoundNBT();
                if (StackUtil.isValid(slot)) {
                    slot.write(tagCompound);
                }
                tagList.add(tagCompound);
            }
            compound.put("Items", tagList);
        }
    }

    public static void loadSlots(IItemHandlerModifiable slots, CompoundNBT compound) {
        if (slots != null && slots.getSlots() > 0) {
            ListNBT tagList = compound.getList("Items", 10);
            for (int i = 0; i < slots.getSlots(); i++) {
                CompoundNBT tagCompound = tagList.getCompound(i);
                slots.setStackInSlot(i, tagCompound.contains("id")
                    ? ItemStack.read(tagCompound)
                    : StackUtil.getEmpty());
            }
        }
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE || type == NBTType.SYNC && this.shouldSyncSlots()) {
            saveSlots(this.inv, compound);
        }
    }

    @Override
    public LazyOptional<IItemHandler> getItemHandler(Direction facing) {
        return this.lazyInv;
    }

    public IAcceptor getAcceptor() {
        return ItemStackHandlerAA.ACCEPT_TRUE;
    }

    public IRemover getRemover() {
        return ItemStackHandlerAA.REMOVE_TRUE;
    }

    public int getMaxStackSize(int slot) {
        return 64;
    }

    public boolean shouldSyncSlots() {
        return false;
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (this.shouldSyncSlots()) {
            this.sendUpdate();
        }
    }

    @Override
    public int getComparatorStrength() {
        return ItemHandlerHelper.calcRedstoneFromInventory(this.inv);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE || type == NBTType.SYNC && this.shouldSyncSlots()) {
            loadSlots(this.inv, compound);
        }
    }

    protected class TileStackHandler extends ItemStackHandlerAA {

        protected TileStackHandler(int slots) {
            super(slots);
        }

        @Override
        public IAcceptor getAcceptor() {
            return TileEntityInventoryBase.this.getAcceptor();
        }

        @Override
        public IRemover getRemover() {
            return TileEntityInventoryBase.this.getRemover();
        }

        @Override
        public int getSlotLimit(int slot) {
            return TileEntityInventoryBase.this.getMaxStackSize(slot);
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TileEntityInventoryBase.this.markDirty();
        }
    }
}
