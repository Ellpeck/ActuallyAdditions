/*
 * This file ("LensDetonation.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.lens;

import de.ellpeck.actuallyadditions.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.util.Position;

public class LensDetonation extends Lens{

    @Override
    public boolean invoke(Position hitBlock, TileEntityAtomicReconstructor tile){
        if(hitBlock != null && !hitBlock.getBlock(tile.getWorldObj()).isAir(tile.getWorldObj(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ())){
            int use = 500000;
            if(tile.storage.getEnergyStored() >= use){
                tile.getWorldObj().newExplosion(null, hitBlock.getX()+0.5, hitBlock.getY()+0.5, hitBlock.getZ()+0.5, 10F, true, true);
                tile.storage.extractEnergy(use, false);
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
