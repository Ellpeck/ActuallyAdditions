/*
 * This file ("ParticleBeam.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;

public class ParticleBeam extends Particle {

    private final double endX;
    private final double endY;
    private final double endZ;
    private final float[] color;
    private final double rotationTime;
    private final float size;
    private final float alpha;

    public ParticleBeam(ClientWorld world, double startX, double startY, double startZ, double endX, double endY, double endZ, float[] color, int maxAge, double rotationTime, float size, float alpha) {
        super(world, startX, startY, startZ);
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.color = color;
        this.rotationTime = rotationTime;
        this.size = size;
        this.maxAge = maxAge;
        this.alpha = alpha;
    }

    @Override
    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        float ageRatio = (float) this.age / (float) this.maxAge;
        float currAlpha = this.alpha - ageRatio * this.alpha;
        AssetUtil.renderLaser(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, this.endX + 0.5, this.endY + 0.5, this.endZ + 0.5, this.rotationTime, currAlpha, this.size, this.color);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
