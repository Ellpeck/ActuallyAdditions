package de.ellpeck.actuallyadditions.common.tile;

import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public abstract class TileEntityInventoryBase<T extends TileEntity> extends TileEntityBase {

    public final ItemStackHandlerAA inv;
    private final LazyOptional<ItemStackHandlerAA> lazyInv;

    public TileEntityInventoryBase(TileEntityType<T> type, int slots, String name) {
        super(type, name);
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
                slots.setStackInSlot(i, tagCompound.contains("id") ? ItemStack.read(tagCompound) : StackUtil.getEmpty());
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
    public LazyOptional<ItemStackHandlerAA> getItemHandler(Direction facing) {
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
    };
}
