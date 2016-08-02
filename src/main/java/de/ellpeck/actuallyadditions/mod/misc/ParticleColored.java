/*
 * This file ("ParticleColored.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleColored extends ParticleRedstone{

    public ParticleColored(World world, double x, double y, double z, float size, float r, float g, float b, float ageMulti){
        super(world, x, y, z, size, r, g, b);
        //To work around Reddust particles resetting the color to red if it's 0 (which is really stupid to be honest)
        this.particleRed = ((float)(Math.random()*0.20000000298023224D)+0.8F)*r*((float)Math.random()*0.4F+0.6F);
        this.particleMaxAge = (int)((8.0D/(Math.random()*0.8D+0.2D))*ageMulti);
    }
}
