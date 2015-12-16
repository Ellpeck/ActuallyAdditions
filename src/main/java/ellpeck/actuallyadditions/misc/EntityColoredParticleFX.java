/*
 * This file ("EntityColoredParticleFX.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityColoredParticleFX extends EntityReddustFX{

    public EntityColoredParticleFX(World world, double x, double y, double z, float size, float r, float g, float b, int ageMulti){
        super(world, x, y, z, size, r, g, b);
        //To work around Reddust particles resetting the color to red if it's 0 (which is really stupid to be honest)
        this.particleRed = ((float)(Math.random()*0.20000000298023224D)+0.8F)*r*((float)Math.random()*0.4F+0.6F);
        this.particleMaxAge = (int)(8.0D/(Math.random()*0.8D+0.2D))*ageMulti;
    }
}
