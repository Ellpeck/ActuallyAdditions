/*
 * This file ("TileEntityPhantomEnergyface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.tile;

import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.BlockPhantom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class TileEntityPhantomEnergyface extends TileEntityPhantomface implements ISharingEnergyProvider {

    public TileEntityPhantomEnergyface(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.PHANTOM_ENERGYFACE.getTileEntityType(), pos, state);
        this.type = BlockPhantom.Type.ENERGYFACE;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomEnergyface tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomEnergyface tile) {
            tile.serverTick();
        }
    }

    @Override
    public boolean isBoundThingInRange() {
        if (super.isBoundThingInRange()) {
            BlockEntity tile = this.level.getBlockEntity(this.boundPosition);
            if (tile != null && !(tile instanceof TileEntityLaserRelayEnergy)) {
                for (Direction facing : Direction.values()) {
                    if (this.level.getCapability(Capabilities.EnergyStorage.BLOCK, tile.getBlockPos(), facing) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public IEnergyStorage getEnergyStorage(Direction facing) {
        if (this.isBoundThingInRange()) {
            BlockEntity tile = this.level.getBlockEntity(this.getBoundPosition());
            if (tile != null) {
                return this.level.getCapability(Capabilities.EnergyStorage.BLOCK, this.getBoundPosition(), facing);
            }
        }
        return null;
    }

    @Override
    public int getEnergyToSplitShare() {
        return Integer.MAX_VALUE;
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
}
