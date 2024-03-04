/*
 * This file ("TileEntityLeafGenerator.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityLeafGenerator extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(35000, 0, 450);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int nextUseCounter;
    private int oldEnergy;

    public TileEntityLeafGenerator(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.LEAF_GENERATOR.getTileEntityType(), pos, state);
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

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLeafGenerator tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLeafGenerator tile) {
            tile.serverTick();

            if (!tile.isRedstonePowered) {

                if (tile.nextUseCounter >= ConfigIntValues.LEAF_GENERATOR_COOLDOWN.getValue()) {
                    tile.nextUseCounter = 0;

                    int energyProduced = ConfigIntValues.LEAF_GENERATOR_CF_PER_LEAF.getValue();
                    if (energyProduced > 0 && energyProduced <= tile.storage.getMaxEnergyStored() - tile.storage.getEnergyStored()) {
                        List<BlockPos> breakPositions = new ArrayList<>();

                        int range = ConfigIntValues.LEAF_GENERATOR_AREA.getValue();
                        for (int reachX = -range; reachX < range + 1; reachX++) {
                            for (int reachZ = -range; reachZ < range + 1; reachZ++) {
                                for (int reachY = -range; reachY < range + 1; reachY++) {
                                    BlockPos offsetPos = pos.offset(reachX, reachY, reachZ);
                                    Block block = level.getBlockState(offsetPos).getBlock();
                                    if (block instanceof LeavesBlock) { // TODO: [port] validate tile is a good way of checking if something is a leaf
                                        breakPositions.add(offsetPos);
                                    }
                                }
                            }
                        }

                        if (!breakPositions.isEmpty()) {
                            Collections.shuffle(breakPositions);
                            BlockPos theCoord = breakPositions.get(0);

                            level.levelEvent(2001, theCoord, Block.getId(level.getBlockState(theCoord)));

                            level.setBlockAndUpdate(theCoord, Blocks.AIR.defaultBlockState());

                            tile.storage.receiveEnergyInternal(energyProduced, false);

                            AssetUtil.spawnLaserWithTimeServer(level, pos.getX(), pos.getY(), pos.getZ(), theCoord.getX(), theCoord.getY(), theCoord.getZ(), 0x3EA34A, 25, 0, 0.075F, 0.8F);
                        }
                    }
                } else {
                    tile.nextUseCounter++;
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
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }
}
