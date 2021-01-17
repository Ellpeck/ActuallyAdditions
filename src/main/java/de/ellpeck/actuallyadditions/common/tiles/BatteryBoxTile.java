package de.ellpeck.actuallyadditions.common.tiles;

import de.ellpeck.actuallyadditions.common.items.useables.BatteryItem;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BatteryBoxTile extends ActuallyTile {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            BatteryBoxTile.this.markDirty();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof BatteryItem;
        }
    };

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemStackHandler);

    public BatteryBoxTile() {
        super(ActuallyTiles.BATTERY_BOX_TILE.get());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                ? handler.cast()
                : LazyOptional.empty();
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);

        if (nbt.contains("items")) {
            itemStackHandler.deserializeNBT(nbt.getCompound("items"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("items", itemStackHandler.serializeNBT());
        return super.write(compound);
    }

    @Override
    public void remove() {
        super.remove();
        this.handler.invalidate();
    }
}
