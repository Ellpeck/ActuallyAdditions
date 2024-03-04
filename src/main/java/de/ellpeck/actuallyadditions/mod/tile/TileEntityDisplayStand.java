/*
 * This file ("TileEntityDisplayStand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.api.misc.IDisplayStandItem;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class TileEntityDisplayStand extends TileEntityInventoryBase implements IEnergyDisplay {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(80000, 1000, 0);
    private int oldEnergy;

    public TileEntityDisplayStand(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.DISPLAY_STAND.getTileEntityType(),  pos, state, 1);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityDisplayStand tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityDisplayStand tile) {
            tile.serverTick();

            if (StackUtil.isValid(tile.inv.getStackInSlot(0)) && !tile.isRedstonePowered) {
                IDisplayStandItem item = tile.convertToDisplayStandItem(tile.inv.getStackInSlot(0).getItem());
                if (item != null) {
                    int energy = item.getUsePerTick(tile.inv.getStackInSlot(0), tile, tile.ticksElapsed);
                    if (tile.storage.getEnergyStored() >= energy) {
                        if (item.update(tile.inv.getStackInSlot(0), tile, tile.ticksElapsed)) {
                            tile.storage.extractEnergyInternal(energy, false);
                        }
                    }
                }
            }

            if (tile.oldEnergy != tile.storage.getEnergyStored() && tile.sendUpdateWithInterval()) {
                tile.oldEnergy = tile.storage.getEnergyStored();
            }
        }
    }

    @Override
    public boolean shouldSyncSlots() {
        return true;
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    private IDisplayStandItem convertToDisplayStandItem(Item item) {
        if (item instanceof IDisplayStandItem) {
            return (IDisplayStandItem) item;
        } else if (item instanceof BlockItem) {
            Block block = Block.byItem(item);
            if (block instanceof IDisplayStandItem) {
                return (IDisplayStandItem) block;
            }
        }
        return null;
    }

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 1;
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }

    public ItemStack getStack() {
        return this.inv.getStackInSlot(0);
    }
}
