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
import de.ellpeck.actuallyadditions.mod.misc.ActuallyDamageTypes;
import de.ellpeck.actuallyadditions.mod.misc.MultiMessageDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LensDeath extends Lens {

    @Override
    public boolean invoke(BlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile) {
        List<LivingEntity> entities = tile.getWorldObject().getEntitiesOfClass(LivingEntity.class, new AABB(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), hitBlock.getX() + 1, hitBlock.getY() + 1, hitBlock.getZ() + 1));
        for (LivingEntity entity : entities) {
            int use = this.getUsePerEntity();
            if (tile.getEnergy() >= use) {
                tile.extractEnergy(use);

                this.onAttacked(entity, tile);
            }
        }

        return !hitState.isAir();
    }

    protected void onAttacked(LivingEntity entity, IAtomicReconstructor tile) {
        Holder<DamageType> type = entity.damageSources().damageTypes.getHolderOrThrow(ActuallyDamageTypes.ATOMIC_RECONSTRUCTOR);
        entity.hurt(new MultiMessageDamageSource(type, 5), 20F);
    }

    protected int getUsePerEntity() {
        return 350;
    }

    @Override
    public int getColor() {
        return 0xBCDEFF;
    }

    @Override
    public int getDistance() {
        return 15;
    }
}
