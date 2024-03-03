/*
 * This file ("TileEntityHeatCollector.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;

public class TileEntityHeatCollector extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay {

    public static final int ENERGY_PRODUCE = 40;
    public static final int BLOCKS_NEEDED = 4;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(30000, 0, 80);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int oldEnergy;
    private int disappearTime;

    public TileEntityHeatCollector(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.HEAT_COLLECTOR.getTileEntityType(), pos, state);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.storage.writeToNBT(compound);
        if (type == NBTType.SAVE_TILE) {
            compound.putInt("DisappearTime", this.disappearTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        if (type == NBTType.SAVE_TILE) {
            this.disappearTime = compound.getInt("DisappearTime");
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityHeatCollector tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityHeatCollector tile) {
            tile.serverTick();

            ArrayList<Integer> blocksAround = new ArrayList<>();
            if (ENERGY_PRODUCE <= tile.storage.getMaxEnergyStored() - tile.storage.getEnergyStored()) {
                for (int i = 1; i <= 5; i++) {
                    BlockPos coords = pos.relative(WorldUtil.getDirectionBySidesInOrder(i));
                    BlockState relativeState = level.getBlockState(coords);
                    Block block = relativeState.getBlock();
                    if (block != null && level.getFluidState(coords).is(FluidTags.LAVA) || level.getBlockState(coords).getBlock() instanceof MagmaBlock) {
                        blocksAround.add(i);
                    }
                }

                if (blocksAround.size() >= BLOCKS_NEEDED) {
                    tile.storage.receiveEnergyInternal(ENERGY_PRODUCE, false);
                    tile.setChanged();

                    tile.disappearTime++;
                    if (tile.disappearTime >= 1000) {
                        tile.disappearTime = 0;

                        if (level.random.nextInt(200) == 0) {
                            int randomSide = blocksAround.get(level.random.nextInt(blocksAround.size()));
                            level.setBlockAndUpdate(pos.relative(WorldUtil.getDirectionBySidesInOrder(randomSide)), Blocks.AIR.defaultBlockState());
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
    public CustomEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public boolean needsHoldShift() {
        return false;
    }

    @Override
    public int getEnergyToSplitShare() {
        return this.storage.getEnergyStored();
    }

    @Override
    public boolean doesShareEnergy() {
        return true;
    }

    @Override
    public Direction[] getEnergyShareSides() {
        return Direction.values();
    }

    @Override
    public boolean canShareTo(BlockEntity tile) {
        return true;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }

}
