package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDropper extends TileEntityInventoryBase {

    private int currentTime;

    public TileEntityDropper() {
        super(9, "dropper");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.setInteger("CurrentTime", this.currentTime);
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInteger("CurrentTime");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
            if (!this.isRedstonePowered && !this.isPulseMode) {
                if (this.currentTime > 0) {
                    this.currentTime--;
                    if (this.currentTime <= 0) {
                        this.doWork();
                    }
                } else {
                    this.currentTime = 5;
                }
            }
        }
    }

    private void doWork() {
        ItemStack theoreticalRemove = this.removeFromInventory(false);
        if (StackUtil.isValid(theoreticalRemove)) {
            IBlockState state = this.world.getBlockState(this.pos);
            ItemStack drop = theoreticalRemove.copy();
            drop.setCount(1);
            WorldUtil.dropItemAtSide(WorldUtil.getDirectionByPistonRotation(state), this.world, this.pos, drop);
            this.removeFromInventory(true);
        }
    }

    public ItemStack removeFromInventory(boolean actuallyDo) {
        for (int i = 0; i < this.inv.getSlots(); i++) {
            if (StackUtil.isValid(this.inv.getStackInSlot(i))) {
                ItemStack slot = this.inv.getStackInSlot(i).copy();
                if (actuallyDo) {
                    this.inv.setStackInSlot(i, StackUtil.shrink(this.inv.getStackInSlot(i), 1));
                    this.markDirty();
                }
                return slot;
            }
        }
        return StackUtil.getEmpty();
    }

    @Override
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        this.doWork();
    }
}
