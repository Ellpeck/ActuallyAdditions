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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class TileEntityLavaFactoryController extends TileEntityBase implements IEnergyDisplay {

    public static final int NOT_MULTI = 0;
    public static final int HAS_LAVA = 1;
    public static final int HAS_AIR = 2;
    public static final int ENERGY_USE = 150000;
    public final CustomEnergyStorage storage = new CustomEnergyStorage(300000, 2000, 0);

    private int currentWorkTime;
    private int oldEnergy;

    public TileEntityLavaFactoryController(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.LAVA_FACTORY_CONTROLLER.getTileEntityType(), pos, state);
    }

    @Override
    public void writeSyncableNBT(CompoundTag compound, NBTType type) {
        super.writeSyncableNBT(compound, type);
        this.storage.writeToNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            compound.putInt("WorkTime", this.currentWorkTime);
        }
    }

    @Override
    public void readSyncableNBT(CompoundTag compound, NBTType type) {
        super.readSyncableNBT(compound, type);
        this.storage.readFromNBT(compound);
        if (type != NBTType.SAVE_BLOCK) {
            this.currentWorkTime = compound.getInt("WorkTime");
        }
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLavaFactoryController tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLavaFactoryController tile) {
            tile.serverTick();
            
            if (tile.storage.getEnergyStored() >= ENERGY_USE && tile.isMultiblock() == HAS_AIR) {
                tile.currentWorkTime++;
                if (tile.currentWorkTime >= 200) {
                    tile.currentWorkTime = 0;
                    level.setBlock(tile.worldPosition.above(), Blocks.LAVA.defaultBlockState(), 2);
                    tile.storage.extractEnergyInternal(ENERGY_USE, false);
                }
            } else {
                tile.currentWorkTime = 0;
            }

            if (tile.oldEnergy != tile.storage.getEnergyStored() && tile.sendUpdateWithInterval()) {
                tile.oldEnergy = tile.storage.getEnergyStored();
            }
        }
    }

    public int isMultiblock() {
        BlockPos thisPos = this.worldPosition;
        BlockPos[] positions = new BlockPos[]{thisPos.offset(1, 1, 0), thisPos.offset(-1, 1, 0), thisPos.offset(0, 1, 1), thisPos.offset(0, 1, -1)};

        if (this.level != null && WorldUtil.hasBlocksInPlacesGiven(positions, ActuallyBlocks.LAVA_FACTORY_CASING.get(), this.level)) {
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
    public IEnergyStorage getEnergyStorage(Direction facing) {
        return this.storage;
    }
}
