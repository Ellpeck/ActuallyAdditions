package de.ellpeck.actuallyadditions.common.tile;

import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IAcceptor;
import de.ellpeck.actuallyadditions.common.util.ItemStackHandlerAA.IRemover;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public abstract class TileEntityInventoryBase extends TileEntityBase {

    public final ItemStackHandlerAA inv;

    public TileEntityInventoryBase(int slots, String name) {
        super(name);
        this.inv = new TileStackHandler(slots);
    }

    public static void saveSlots(IItemHandler slots, NBTTagCompound compound) {
        if (slots != null && slots.getSlots() > 0) {
            NBTTagList tagList = new NBTTagList();
            for (int i = 0; i < slots.getSlots(); i++) {
                ItemStack slot = slots.getStackInSlot(i);
                NBTTagCompound tagCompound = new NBTTagCompound();
                if (StackUtil.isValid(slot)) {
                    slot.writeToNBT(tagCompound);
                }
                tagList.appendTag(tagCompound);
            }
            compound.setTag("Items", tagList);
        }
    }

    public static void loadSlots(IItemHandlerModifiable slots, NBTTagCompound compound) {
        if (slots != null && slots.getSlots() > 0) {
            NBTTagList tagList = compound.getTagList("Items", 10);
            for (int i = 0; i < slots.getSlots(); i++) {
                NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                slots.setStackInSlot(i, tagCompound != null && tagCompound.hasKey("id") ? new ItemStack(tagCompound) : StackUtil.getEmpty());
            }
        }
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type == NBTType.SAVE_TILE || type == NBTType.SYNC && this.shouldSyncSlots()) {
            saveSlots(this.inv, compound);
        }
    }

    @Override
    public IItemHandler getItemHandler(EnumFacing facing) {
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
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
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
