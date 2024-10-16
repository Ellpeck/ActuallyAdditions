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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public abstract class TileEntityInventoryBase extends TileEntityBase {

    public final ItemStackHandlerAA inv;

    public TileEntityInventoryBase(BlockEntityType<?> type, BlockPos pos, BlockState state, int slots) {
        super(type, pos, state);
        this.inv = new TileStackHandler(slots);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.writeSyncableNBT(compound, lookupProvider, type);
        if (type == NBTType.SAVE_TILE || type == NBTType.SYNC && this.shouldSyncSlots()) {
            compound.put("Items", this.inv.serializeNBT(lookupProvider));
        }
    }

    @Override
    public IItemHandler getItemHandler(Direction facing) {
        return this.inv;
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
    public void setChanged() {
        super.setChanged();

        if (this.shouldSyncSlots()) {
            this.sendUpdate();
        }
    }

    @Override
    public int getComparatorStrength() {
        return ItemHandlerHelper.calcRedstoneFromInventory(this.inv);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.readSyncableNBT(compound, lookupProvider, type);
        if (type == NBTType.SAVE_TILE || type == NBTType.SYNC && this.shouldSyncSlots()) {
            this.inv.deserializeNBT(lookupProvider, compound.getCompound("Items"));
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
            TileEntityInventoryBase.this.setChanged();
        }
    }
}
