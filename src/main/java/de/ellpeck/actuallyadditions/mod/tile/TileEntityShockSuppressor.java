/*
 * This file ("TileEntityShockSuppressor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileEntityShockSuppressor extends TileEntityBase implements IEnergyDisplay {

    public static final List<TileEntityShockSuppressor> SUPPRESSORS = new ArrayList<>();

    public static final int USE_PER = 300;
    public static final int RANGE = 5;

    public CustomEnergyStorage storage = new CustomEnergyStorage(300000, 400, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int oldEnergy;

    public TileEntityShockSuppressor(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.SHOCK_SUPPRESSOR.getTileEntityType(), pos, state);
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();

        if (!this.level.isClientSide) {
            SUPPRESSORS.remove(this);
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();

        if (!this.level.isClientSide) {
            SUPPRESSORS.remove(this);
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityShockSuppressor tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityShockSuppressor tile) {
            tile.serverTick();

            if (!tile.isRemoved() && !SUPPRESSORS.contains(tile)) {
                SUPPRESSORS.add(tile);
            }

            if (tile.oldEnergy != tile.storage.getEnergyStored() && tile.sendUpdateWithInterval()) {
                tile.oldEnergy = tile.storage.getEnergyStored();
            }
        }
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

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }
}
