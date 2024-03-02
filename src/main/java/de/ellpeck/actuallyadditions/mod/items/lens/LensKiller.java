/*
 * This file ("LensEvenMoarDeath.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.FakePlayerFactory;

public class LensKiller extends LensDeath {

    @Override
    protected void onAttacked(LivingEntity entity, IAtomicReconstructor tile) {
        if (!tile.getWorldObject().isClientSide) {
            entity.hurt(DamageSource.playerAttack(FakePlayerFactory.getMinecraft((ServerLevel) tile.getWorldObject())), 20);
        }
    }

    @Override
    protected int getUsePerEntity() {
        return 2500;
    }
}
