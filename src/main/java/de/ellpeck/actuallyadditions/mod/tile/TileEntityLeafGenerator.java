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
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TileEntityLeafGenerator extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(35000, 0, 450);
    private int nextUseCounter;
    private int oldEnergy;

    public TileEntityLeafGenerator(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.LEAF_GENERATOR.getTileEntityType(), pos, state);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.writeSyncableNBT(compound, lookupProvider, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, HolderLookup.Provider lookupProvider, NBTType type) {
        super.readSyncableNBT(compound, lookupProvider, type);
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

                if (tile.nextUseCounter >= CommonConfig.Machines.LEAF_GENERATOR_COOLDOWN.get()) {
                    tile.nextUseCounter = 0;

                    int energyProduced = CommonConfig.Machines.LEAF_GENERATOR_CF_PER_LEAF.get();
                    if (energyProduced > 0 && energyProduced <= tile.storage.getMaxEnergyStored() - tile.storage.getEnergyStored()) {
                        int range = CommonConfig.Machines.LEAF_GENERATOR_AREA.get();
                        List<BlockPos> breakPositions = BlockPos.betweenClosedStream(
                                pos.offset(-range, -range, -range),
                                pos.offset(range, range, range)).map(BlockPos::immutable).collect(Collectors.toList());
                        breakPositions.removeIf(blockPos -> {
                            BlockState offsetState = level.getBlockState(blockPos);
                            return !(offsetState.getBlock() instanceof LeavesBlock || offsetState.is(BlockTags.LEAVES));
                        });

                        if (!breakPositions.isEmpty()) {
                            Collections.shuffle(breakPositions);
                            BlockPos theCoord = breakPositions.getFirst();

                            level.levelEvent(2001, theCoord, Block.getId(level.getBlockState(theCoord)));

                            level.setBlockAndUpdate(theCoord, Blocks.AIR.defaultBlockState());

                            tile.storage.receiveEnergy(energyProduced, false);

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
        return this.storage;
    }
}
