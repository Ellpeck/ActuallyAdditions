/*
 * This file ("LensDetonation.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.state.BlockState;

public class LensDetonation extends Lens {

    private static final int ENERGY_USE = 250000;

    @Override
    public boolean invoke(BlockState state, BlockPos hitBlock, IAtomicReconstructor tile) {
        if (hitBlock != null && !state.isAir()) {
            if (tile.getEnergy() >= ENERGY_USE) {
                tile.getWorldObject().explode(null, hitBlock.getX() + 0.5, hitBlock.getY() + 0.5, hitBlock.getZ() + 0.5, 10F, true, Explosion.BlockInteraction.NONE); // TODO: [port][test] make sure this is the right explosion mode
                tile.extractEnergy(ENERGY_USE);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getColor() {
        return 0x9E2B27;
    }

    @Override
    public int getDistance() {
        return 30;
    }

    @Override
    public boolean canInvoke(IAtomicReconstructor tile, Direction sideToShootTo, int energyUsePerShot) {
        return tile.getEnergy() - energyUsePerShot >= ENERGY_USE;
    }
}
