/*
 * This file ("TileEntityPhantomLiquiface.java") is part of the Actually Additions mod for Minecraft.
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
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class TileEntityPhantomLiquiface extends TileEntityPhantomface implements ISharingFluidHandler {

    public TileEntityPhantomLiquiface(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.PHANTOM_LIQUIFACE.getTileEntityType(), pos, state);
        this.type = BlockPhantom.Type.LIQUIFACE;
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomLiquiface tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomLiquiface tile) {
            tile.serverTick();
        }
    }

    @Override
    public boolean isBoundThingInRange() {
        if (super.isBoundThingInRange()) {
            BlockEntity tile = this.level.getBlockEntity(this.getBoundPosition());
            if (tile != null && !(tile instanceof TileEntityLaserRelayFluids)) {
                for (Direction facing : Direction.values()) {
                    if (this.level.getCapability(Capabilities.FluidHandler.BLOCK, this.getBoundPosition(), facing) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public IFluidHandler getFluidHandler(Direction facing) {
        if (this.isBoundThingInRange()) {
            BlockEntity tile = this.level.getBlockEntity(this.getBoundPosition());
            if (tile != null) {
                return this.level.getCapability(Capabilities.FluidHandler.BLOCK, this.getBoundPosition(), facing);
            }
        }
        return null;
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean doesShareFluid() {
        return true;
    }

    @Override
    public Direction[] getFluidShareSides() {
        return Direction.values();
    }
}
