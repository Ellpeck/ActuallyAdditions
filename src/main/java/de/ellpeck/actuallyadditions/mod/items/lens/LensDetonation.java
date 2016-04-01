/*
 * This file ("LensDetonation.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class LensDetonation extends Lens{

    @Override
    public boolean invoke(IBlockState state, BlockPos hitBlock, IAtomicReconstructor tile){
        if(hitBlock != null && !PosUtil.getBlock(hitBlock, tile.getWorldObject()).isAir(state, tile.getWorldObject(), hitBlock)){
            int use = 250000;
            if(tile.getEnergy() >= use){
                tile.getWorldObject().newExplosion(null, hitBlock.getX()+0.5, hitBlock.getY()+0.5, hitBlock.getZ()+0.5, 10F, true, true);
                tile.extractEnergy(use);
            }
            return true;
        }
        return false;
    }

    @Override
    public float[] getColor(){
        return new float[]{158F/255F, 43F/255F, 39F/255F};
    }

    @Override
    public int getDistance(){
        return 30;
    }
}