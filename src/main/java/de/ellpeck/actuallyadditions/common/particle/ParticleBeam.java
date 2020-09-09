package de.ellpeck.actuallyadditions.common.particle;

import de.ellpeck.actuallyadditions.common.util.AssetUtil;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleBeam extends Particle {

    private final double endX;
    private final double endY;
    private final double endZ;
    private final float[] color;
    private final double rotationTime;
    private final float size;
    private final float alpha;

    public ParticleBeam(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, float[] color, int maxAge, double rotationTime, float size, float alpha) {
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
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float ageRatio = (float) this.age / (float) this.maxAge;
        float currAlpha = this.alpha - ageRatio * this.alpha;
        AssetUtil.renderLaser(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, this.endX + 0.5, this.endY + 0.5, this.endZ + 0.5, this.rotationTime, currAlpha, this.size, this.color);
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}
