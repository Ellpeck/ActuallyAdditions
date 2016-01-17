/*
 * This file ("LensDeath.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.misc.DamageSources;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;

public class LensDeath extends Lens{

    @SuppressWarnings("unchecked")
    @Override
    public boolean invoke(BlockPos hitBlock, IAtomicReconstructor tile){
        int use = 150; //Per Block (because it doesn't only activate when something is hit like the other lenses!)
        if(tile.getEnergy() >= use){
            tile.extractEnergy(use);

            ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>)tile.getWorldObject().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), hitBlock.getX()+1, hitBlock.getY()+1, hitBlock.getZ()+1));
            for(EntityLivingBase entity : entities){
                entity.attackEntityFrom(DamageSources.DAMAGE_ATOMIC_RECONSTRUCTOR, 20F);
            }
        }

        return hitBlock != null && !PosUtil.getBlock(hitBlock, tile.getWorldObject()).isAir(tile.getWorldObject(), hitBlock);
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
