/*
 * This file ("TileEntityDropper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.inventory.ContainerDropper;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TileEntityDropper extends TileEntityInventoryBase implements MenuProvider {

    private int currentTime;

    public TileEntityDropper(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.DROPPER.getTileEntityType(),  pos, state,9);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("CurrentTime", this.currentTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentTime = compound.getInt("CurrentTime");
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityDropper tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityDropper tile) {
            tile.serverTick();
            
            if (!tile.isRedstonePowered && !tile.isPulseMode) {
                if (tile.currentTime > 0) {
                    tile.currentTime--;
                    if (tile.currentTime <= 0) {
                        tile.doWork();
                    }
                } else {
                    tile.currentTime = 5;
                }
            }
        }
    }

    private void doWork() {
        ItemStack theoreticalRemove = this.removeFromInventory(false);
        if (StackUtil.isValid(theoreticalRemove)) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            ItemStack drop = theoreticalRemove.copy();
            drop.setCount(1);
            WorldUtil.dropItemAtSide(WorldUtil.getDirectionByPistonRotation(state), this.level, this.worldPosition, drop);
            this.removeFromInventory(true);
        }
    }

    public ItemStack removeFromInventory(boolean actuallyDo) {
        for (int i = 0; i < this.inv.getSlots(); i++) {
            if (StackUtil.isValid(this.inv.getStackInSlot(i))) {
                ItemStack slot = this.inv.getStackInSlot(i).copy();
                if (actuallyDo) {
                    this.inv.setStackInSlot(i, StackUtil.shrink(this.inv.getStackInSlot(i), 1));
                    this.setChanged();
                }
                return slot;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isRedstoneToggle() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        this.doWork();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.actuallyadditions.dropper");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ContainerDropper(windowId, playerInventory, this);
    }
}
