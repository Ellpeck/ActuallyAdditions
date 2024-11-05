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

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.Nullable;

public class ParticleBeam extends Particle {
    public static final ParticleRenderType LASER_RENDER = new ParticleRenderType() {

        @Nullable
        @Override
        public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.2F);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public String toString() {
            return "actuallyadditions:laser_particle";
        }
    };

    private final double endX;
    private final double endY;
    private final double endZ;
    private final int color;
    private final double rotationTime;
    private final float size;
    private final float alpha;

    public ParticleBeam(ClientLevel world, double startX, double startY, double startZ, double endX, double endY, double endZ,
                        int color, int maxAge, double rotationTime, float size) {
        super(world, startX, startY, startZ);
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.color = color;
        this.rotationTime = rotationTime;
        this.size = size;
        this.lifetime = maxAge;
        this.alpha = FastColor.ARGB32.alpha(color) / 255.0F;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        float ageRatio = (float) this.age / (float) this.lifetime;
        float currAlpha = this.alpha - ageRatio * this.alpha;
        AssetUtil.renderLaserParticle(buffer, camera, this.x + 0.5, this.y + 0.5, this.z + 0.5, this.endX + 0.5, this.endY + 0.5, this.endZ + 0.5, (float) this.rotationTime, currAlpha, this.size, this.color);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return LASER_RENDER;
    }


    public static class Factory implements ParticleProvider<BeamParticleData> {
        public Factory(SpriteSet sprite) {

        }

        @Override
        public Particle createParticle(BeamParticleData data, ClientLevel worldIn, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new ParticleBeam(worldIn, x, y, z, data.endX(), data.endY(), data.endZ(), data.color(), data.maxAge(),
                    data.rotationTime(), data.size());
        }

        public static ParticleOptions createData(double endX, double endY, double endZ, int color,
                                                 int maxAge, double rotationTime, float size) {
            return new BeamParticleData(endX, endY, endZ, color, maxAge, rotationTime, size);
        }

        public static ParticleOptions createData(double endX, double endY, double endZ, int color, float alpha,
                                                 int maxAge, double rotationTime, float size) {
            return new BeamParticleData(endX, endY, endZ, color, maxAge, rotationTime, size);
        }
    }
}
