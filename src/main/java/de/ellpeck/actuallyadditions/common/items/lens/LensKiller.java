package de.ellpeck.actuallyadditions.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class LensKiller extends LensDeath {

    @Override
    protected void onAttacked(EntityLivingBase entity, IAtomicReconstructor tile) {
        if (!tile.getWorldObject().isRemote) {
            entity.attackEntityFrom(DamageSource.causePlayerDamage(FakePlayerFactory.getMinecraft((WorldServer) tile.getWorldObject())), 20);
        }
    }

    @Override
    protected int getUsePerEntity() {
        return 2500;
    }
}
