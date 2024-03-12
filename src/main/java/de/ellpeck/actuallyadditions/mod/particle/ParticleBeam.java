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
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class ParticleBeam extends Particle {
    public static final ParticleRenderType LASER_RENDER = new ParticleRenderType() {

        @Override
        public void begin(BufferBuilder buffer, TextureManager textureManager) {
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.depthMask(false);
        }

        @Override
        public void end(Tesselator tesselator) {
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        }

        @Override
        public String toString() {
            return "actuallyadditions:laser_particle";
        }
    };

    private final double endX;
    private final double endY;
    private final double endZ;
    private final float[] color;
    private final double rotationTime;
    private final float size;
    private final float alpha;

    public ParticleBeam(ClientLevel world, double startX, double startY, double startZ, double endX, double endY, double endZ,
                        float[] color, float alpha, int maxAge, double rotationTime, float size) {
        super(world, startX, startY, startZ);
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.color = color;
        this.rotationTime = rotationTime;
        this.size = size;
        this.lifetime = maxAge;
        this.alpha = alpha;
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
        public Particle createParticle(BeamParticleData data, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ParticleBeam(worldIn, x, y, z, data.endX, data.endY, data.endZ, data.color, data.alpha, data.maxAge,
                    data.rotationTime, data.size);
        }

        public static ParticleOptions createData(double endX, double endY, double endZ, float[] color, float alpha,
                                                 int maxAge, double rotationTime, float size) {
            return new BeamParticleData(endX, endY, endZ, color, alpha, maxAge, rotationTime, size);
        }

        public static ParticleOptions createData(double endX, double endY, double endZ, int color, float alpha,
                                                 int maxAge, double rotationTime, float size) {
            return new BeamParticleData(endX, endY, endZ, colorFromInt(color), alpha, maxAge, rotationTime, size);
        }

        private static float[] colorFromInt(int color) {
            float red = (float)(FastColor.ARGB32.red(color) / 255.0);
            float green = (float)(FastColor.ARGB32.green(color) / 255.0);
            float blue = (float)(FastColor.ARGB32.blue(color) / 255.0);
            return new float[] {red, green, blue};
        }
    }
}
