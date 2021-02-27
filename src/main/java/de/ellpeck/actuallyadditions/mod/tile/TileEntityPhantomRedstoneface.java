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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Arrays;

public class TileEntityPhantomRedstoneface extends TileEntityPhantomface {

    public final int[] providesStrong = new int[Direction.values().length];
    public final int[] providesWeak = new int[Direction.values().length];

    private final int[] lastProvidesStrong = new int[this.providesStrong.length];
    private final int[] lastProvidesWeak = new int[this.providesWeak.length];

    public TileEntityPhantomRedstoneface() {
        super(ActuallyTiles.PHANTOMREDSTONEFACE_TILE.get());
    }

    @Override
    public void updateEntity() {
        if (!this.world.isRemote) {
            if (this.isBoundThingInRange()) {
                BlockState boundState = this.world.getBlockState(this.boundPosition);
                if (boundState != null) {
                    Block boundBlock = boundState.getBlock();
                    if (boundBlock != null) {
                        for (int i = 0; i < Direction.values().length; i++) {
                            Direction facing = Direction.values()[i];
                            this.providesWeak[i] = boundState.getWeakPower(this.world, this.boundPosition, facing);
                            this.providesStrong[i] = boundState.getStrongPower(this.world, this.boundPosition, facing);
                        }
                    }
                }
            }
        }

        super.updateEntity();
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

    @Override
    protected boolean isCapabilitySupported(Capability<?> capability) {
        return false;
    }
}
