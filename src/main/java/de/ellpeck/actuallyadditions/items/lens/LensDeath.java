/*
 * This file ("LensDeath.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.items.lens;

import de.ellpeck.actuallyadditions.misc.DamageSources;
import de.ellpeck.actuallyadditions.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.util.Position;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;

public class LensDeath extends Lens{

    @SuppressWarnings("unchecked")
    @Override
    public boolean invoke(Position hitBlock, TileEntityAtomicReconstructor tile){
        int use = 150; //Per Block (because it doesn't only activate when something is hit like the other lenses!)
        if(tile.storage.getEnergyStored() >= use){
            tile.storage.extractEnergy(use, false);

            ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>)tile.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), hitBlock.getX()+1, hitBlock.getY()+1, hitBlock.getZ()+1));
            for(EntityLivingBase entity : entities){
                entity.attackEntityFrom(DamageSources.DAMAGE_ATOMIC_RECONSTRUCTOR, 20F);
            }
        }

        return hitBlock != null && !hitBlock.getBlock(tile.getWorldObj()).isAir(tile.getWorldObj(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ());
    }

    @Override
    public float[] getColor(){
        return new float[]{188F/255F, 222F/255F, 1F};
    }

    @Override
    public int getDistance(){
        return 15;
    }
}
