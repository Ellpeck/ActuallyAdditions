/*
 * This file ("TileEntityPhantomRedstoneface.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public class TileEntityPhantomRedstoneface extends TileEntityPhantomface {

    public final int[] providesStrong = new int[Direction.values().length];
    public final int[] providesWeak = new int[Direction.values().length];

    private final int[] lastProvidesStrong = new int[this.providesStrong.length];
    private final int[] lastProvidesWeak = new int[this.providesWeak.length];

    public TileEntityPhantomRedstoneface(BlockPos pos, BlockState state) {
        super(ActuallyBlocks.PHANTOM_REDSTONEFACE.getTileEntityType(), pos, state);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomRedstoneface tile) {
            tile.clientTick();
        }
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof TileEntityPhantomRedstoneface tile) {
            tile.serverTick();

            if (tile.isBoundThingInRange()) {
                BlockState boundState = tile.level.getBlockState(tile.boundPosition);
                if (boundState != null) {
                    Block boundBlock = boundState.getBlock();
                    if (boundBlock != null) {
                        for (int i = 0; i < Direction.values().length; i++) {
                            Direction facing = Direction.values()[i];
                            tile.providesWeak[i] = boundState.getSignal(tile.level, tile.boundPosition, facing);
                            tile.providesStrong[i] = boundState.getDirectSignal(tile.level, tile.boundPosition, facing);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean doesNeedUpdateSend() {
        return super.doesNeedUpdateSend() || !Arrays.equals(this.providesStrong, this.lastProvidesStrong) || !Arrays.equals(this.providesWeak, this.lastProvidesWeak);
    }

    @Override
    protected void onUpdateSent() {
        System.arraycopy(this.providesWeak, 0, this.lastProvidesWeak, 0, this.providesWeak.length);
        System.arraycopy(this.providesStrong, 0, this.lastProvidesStrong, 0, this.providesStrong.length);

        super.onUpdateSent();
    }
}
