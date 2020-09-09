package de.ellpeck.actuallyadditions.common.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class LensDetonation extends Lens {

    private static final int ENERGY_USE = 250000;

    @Override
    public boolean invoke(IBlockState state, BlockPos hitBlock, IAtomicReconstructor tile) {
        if (hitBlock != null && !state.getBlock().isAir(state, tile.getWorldObject(), hitBlock)) {
            if (tile.getEnergy() >= ENERGY_USE) {
                tile.getWorldObject().newExplosion(null, hitBlock.getX() + 0.5, hitBlock.getY() + 0.5, hitBlock.getZ() + 0.5, 10F, true, true);
                tile.extractEnergy(ENERGY_USE);
            }
            return true;
        }
        return false;
    }

    @Override
    public float[] getColor() {
        return new float[] { 158F / 255F, 43F / 255F, 39F / 255F };
    }

    @Override
    public int getDistance() {
        return 30;
    }

    @Override
    public boolean canInvoke(IAtomicReconstructor tile, EnumFacing sideToShootTo, int energyUsePerShot) {
        return tile.getEnergy() - energyUsePerShot >= ENERGY_USE;
    }
}