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
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class LensEvenMoarDeath extends LensDeath{

    @Override
    protected void onAttacked(EntityLivingBase entity, IAtomicReconstructor tile){
        if(tile.getWorldObject() instanceof WorldServer){
            try{
                FakePlayer fake = FakePlayerFactory.getMinecraft((WorldServer)tile.getWorldObject());
                ReflectionHelper.setPrivateValue(EntityLivingBase.class, entity, fake, 37);

                ReflectionHelper.setPrivateValue(EntityLivingBase.class, entity, 100, 38);
            }
            catch(Exception e){
                ModUtil.LOGGER.error("A Damage Lens at "+tile.getX()+", "+tile.getY()+", "+tile.getZ()+" in World "+tile.getWorldObject().provider.getDimension()+" threw an Exception! Don't let that happen again!", e);
            }
        }

        super.onAttacked(entity, tile);
    }

    @Override
    protected int getUsePerEntity(){
        return 2500;
    }
}
