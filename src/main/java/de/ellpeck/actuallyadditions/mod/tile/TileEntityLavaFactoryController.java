/*
 * This file ("TileEntityLavaFactoryController.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;

public class TileEntityLavaFactoryController extends TileEntityBase implements IEnergyDisplay {

    public static final int NOT_MULTI = 0;
    public static final int HAS_LAVA = 1;
    public static final int HAS_AIR = 2;
    public static final int ENERGY_USE = 150000;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 2000, 0);
    public final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> this.storage);

    private int currentWorkTime;
    private int oldEnergy;

    public TileEntityLavaFactoryController() {
        super(ActuallyTiles.LAVAFACTORYCONTROLLER_TILE.get());
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("WorkTime", this.currentWorkTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentWorkTime = compound.getInt("WorkTime");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            if (this.storage.getEnergyStored() >= ENERGY_USE && this.isMultiblock() == HAS_AIR) {
                this.currentWorkTime++;
                if (this.currentWorkTime >= 200) {
                    this.currentWorkTime = 0;
                    this.level.setBlock(this.worldPosition.above(), Blocks.LAVA.defaultBlockState(), 2);
                    this.storage.extractEnergyInternal(ENERGY_USE, false);
                }
            } else {
                this.currentWorkTime = 0;
            }

            if (this.oldEnergy != this.storage.getEnergyStored() && this.sendUpdateWithInterval()) {
                this.oldEnergy = this.storage.getEnergyStored();
            }
        }
    }

    public int isMultiblock() {
        BlockPos thisPos = this.worldPosition;
        BlockPos[] positions = new BlockPos[]{thisPos.offset(1, 1, 0), thisPos.offset(-1, 1, 0), thisPos.offset(0, 1, 1), thisPos.offset(0, 1, -1)};

        if (this.level != null && WorldUtil.hasBlocksInPlacesGiven(positions, ActuallyBlocks.LAVA_CASING.get(), this.level)) {
            BlockPos pos = thisPos.above();
            BlockState state = this.level.getBlockState(pos);
            Block block = state.getBlock();
            if (block == Blocks.LAVA) {
                return HAS_LAVA;
            }
            if (this.level.isEmptyBlock(pos)) {
                return HAS_AIR;
            }
        }
        return NOT_MULTI;
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
    public LazyOptional<IEnergyStorage> getEnergyStorage(Direction facing) {
        return this.lazyEnergy;
    }
}
