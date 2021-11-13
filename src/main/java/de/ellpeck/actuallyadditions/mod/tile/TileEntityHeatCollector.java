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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MagmaBlock;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
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

    public TileEntityHeatCollector() {
        super(ActuallyBlocks.HEAT_COLLECTOR.getTileEntityType());
    }

    @Override
    public void writeSyncableNBT(CompoundNBT compound, NBTType type) {
        super.writeSyncableNBT(compound, type);

        this.storage.writeToNBT(compound);
        if (type == NBTType.SAVE_TILE) {
            compound.putInt("DisappearTime", this.disappearTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundNBT compound, NBTType type) {
        super.readSyncableNBT(compound, type);

        this.storage.readFromNBT(compound);
        if (type == NBTType.SAVE_TILE) {
            this.disappearTime = compound.getInt("DisappearTime");
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.level.isClientSide) {
            ArrayList<Integer> blocksAround = new ArrayList<>();
            if (ENERGY_PRODUCE <= this.storage.getMaxEnergyStored() - this.storage.getEnergyStored()) {
                for (int i = 1; i <= 5; i++) {
                    BlockPos coords = this.worldPosition.relative(WorldUtil.getDirectionBySidesInOrder(i));
                    BlockState state = this.level.getBlockState(coords);
                    Block block = state.getBlock();
                    if (block != null && this.level.getBlockState(coords).getMaterial() == Material.LAVA || this.level.getBlockState(coords).getBlock() instanceof MagmaBlock) {
                        blocksAround.add(i);
                    }
                }

                if (blocksAround.size() >= BLOCKS_NEEDED) {
                    this.storage.receiveEnergyInternal(ENERGY_PRODUCE, false);
                    this.setChanged();

                    this.disappearTime++;
                    if (this.disappearTime >= 1000) {
                        this.disappearTime = 0;

                        if (this.level.random.nextInt(200) == 0) {
                            int randomSide = blocksAround.get(this.level.random.nextInt(blocksAround.size()));
                            this.level.setBlockAndUpdate(this.worldPosition.relative(WorldUtil.getDirectionBySidesInOrder(randomSide)), Blocks.AIR.defaultBlockState());
                        }
                    }
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
