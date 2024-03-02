/*
 * This file ("TileEntityLaserRelayEnergyAdvanced.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLaserRelayEnergyAdvanced extends TileEntityLaserRelayEnergy {

    public static final int CAP = 10000;

    public TileEntityLaserRelayEnergyAdvanced(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.LASER_RELAY_ADVANCED.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayEnergyAdvanced tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityLaserRelayEnergyAdvanced tile) {
            tile.serverTick();
        }
    }

    @Override
    public int getEnergyCap() {
        return CAP;
    }

    @Override
    public double getLossPercentage() {
        return 8;
    }
}
