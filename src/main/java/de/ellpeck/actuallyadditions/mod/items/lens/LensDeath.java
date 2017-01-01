/*
 * This file ("LensDeath.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.misc.DamageSources;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class LensDeath extends Lens{

    @Override
    public boolean invoke(IBlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile){
        ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>)tile.getWorldObject().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), hitBlock.getX()+1, hitBlock.getY()+1, hitBlock.getZ()+1));
        for(EntityLivingBase entity : entities){
            int use = this.getUsePerEntity();
            if(tile.getEnergy() >= use){
                tile.extractEnergy(use);

                this.onAttacked(entity, tile);
            }
        }

        return hitBlock != null && !hitState.getBlock().isAir(hitState, tile.getWorldObject(), hitBlock);
    }

    protected void onAttacked(EntityLivingBase entity, IAtomicReconstructor tile){
        entity.attackEntityFrom(DamageSources.DAMAGE_ATOMIC_RECONSTRUCTOR, 20F);
    }

    protected int getUsePerEntity(){
        return 350;
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
