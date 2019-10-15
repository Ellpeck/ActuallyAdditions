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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityLeafGenerator extends TileEntityBase implements ISharingEnergyProvider, IEnergyDisplay {

    public final CustomEnergyStorage storage = new CustomEnergyStorage(35000, 0, 450);
    private int nextUseCounter;
    private int oldEnergy;

    public TileEntityLeafGenerator() {
        super("leafGenerator");
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.world.isRemote) {
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
                                    BlockPos pos = this.pos.add(reachX, reachY, reachZ);
                                    Block block = this.world.getBlockState(pos).getBlock();
                                    if (block != null && block.isLeaves(this.world.getBlockState(pos), this.world, pos)) {
                                        breakPositions.add(pos);
                                    }
                                }
                            }
                        }

                        if (!breakPositions.isEmpty()) {
                            Collections.shuffle(breakPositions);
                            BlockPos theCoord = breakPositions.get(0);

                            this.world.playEvent(2001, theCoord, Block.getStateId(this.world.getBlockState(theCoord)));

                            this.world.setBlockToAir(theCoord);

                            this.storage.receiveEnergyInternal(energyProduced, false);

                            AssetUtil.spawnLaserWithTimeServer(this.world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), theCoord.getX(), theCoord.getY(), theCoord.getZ(), new float[] { 62F / 255F, 163F / 255F, 74F / 255F }, 25, 0, 0.075F, 0.8F);
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
    public EnumFacing[] getEnergyShareSides() {
        return EnumFacing.values();
    }

    @Override
    public boolean canShareTo(TileEntity tile) {
        return true;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.storage;
    }
}
