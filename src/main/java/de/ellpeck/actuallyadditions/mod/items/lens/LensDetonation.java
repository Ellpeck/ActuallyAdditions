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

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.api.lens.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;

public class LensDetonation extends Lens{

    @Override
    public boolean invoke(Position hitBlock, IAtomicReconstructor tile){
        if(hitBlock != null && !hitBlock.getBlock(tile.getWorldObj()).isAir(tile.getWorldObj(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ())){
            int use = 250000;
            if(tile.getEnergy() >= use){
                tile.getWorldObj().newExplosion(null, hitBlock.getX()+0.5, hitBlock.getY()+0.5, hitBlock.getZ()+0.5, 10F, true, true);
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