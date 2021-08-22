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

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityLeafGenerator extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(35000, 0, 450);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);
    private int nextUseCounter;
    private int oldEnergy;

    public TileEntityLeafGenerator() {
        super(ActuallyTiles.LEAFGENERATOR_TILE.get());
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (!this.isRedstonePowered) {

                if (this.nextUseCounter >= ConfigIntValues.LEAF_GENERATOR_COOLDOWN.getValue()) {
                    this.nextUseCounter = 0;

                    int energyProduced = ConfigIntValues.LEAF_GENERATOR_CF_PER_LEAF.getValue();
                    if (energyProduced > 0 && energyProduced <= this.storage.getMaxEnergyStored() - this.storage.getEnergyStored()) {
                        List<BlockPos> breakPositions = new ArrayList<>();

                        int range = ConfigIntValues.LEAF_GENERATOR_AREA.getValue();
                        for (int reachX = -range; reachX < range + 1; reachX++) {
                            for (int reachZ = -range; reachZ < range + 1; reachZ++) {
                                for (int reachY = -range; reachY < range + 1; reachY++) {
                                    BlockPos pos = this.worldPosition.offset(reachX, reachY, reachZ);
                                    Block block = this.level.getBlockState(pos).getBlock();
                                    if (block instanceof LeavesBlock) { // TODO: [port] validate this is a good way of checking if something is a leaf
                                        breakPositions.add(pos);
                                    }
                                }
                            }
                        }

                        if (!breakPositions.isEmpty()) {
                            Collections.shuffle(breakPositions);
                            BlockPos theCoord = breakPositions.get(0);

                            this.level.levelEvent(2001, theCoord, Block.getId(this.level.getBlockState(theCoord)));

                            this.level.setBlockAndUpdate(theCoord, Blocks.AIR.defaultBlockState());

                            this.storage.receiveEnergyInternal(energyProduced, false);

                            AssetUtil.spawnLaserWithTimeServer(this.level, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), theCoord.getX(), theCoord.getY(), theCoord.getZ(), new float[]{62F / 255F, 163F / 255F, 74F / 255F}, 25, 0, 0.075F, 0.8F);
                        }
                    }
                } else {
                    this.nextUseCounter++;
                }
            }

            if (this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
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
    public boolean canShareTo(TileEntity tile) {
        return true;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }
}
