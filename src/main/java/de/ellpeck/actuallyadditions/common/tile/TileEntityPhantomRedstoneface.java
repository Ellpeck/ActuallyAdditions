package de.ellpeck.actuallyadditions.common.tile;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityPhantomRedstoneface extends TileEntityPhantomface {

    public final int[] providesStrong = new int[EnumFacing.values().length];
    public final int[] providesWeak = new int[EnumFacing.values().length];

    private final int[] lastProvidesStrong = new int[this.providesStrong.length];
    private final int[] lastProvidesWeak = new int[this.providesWeak.length];

    public TileEntityPhantomRedstoneface() {
        super("redstoneface");
    }

    @Override
    public void updateEntity() {
        if (!this.world.isRemote) {
            if (this.isBoundThingInRange()) {
                IBlockState boundState = this.world.getBlockState(this.boundPosition);
                if (boundState != null) {
                    Block boundBlock = boundState.getBlock();
                    if (boundBlock != null) {
                        for (int i = 0; i < EnumFacing.values().length; i++) {
                            EnumFacing facing = EnumFacing.values()[i];
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
