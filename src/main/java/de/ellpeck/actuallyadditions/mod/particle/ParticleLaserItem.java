/*
 * This file ("ParticleLaserItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;

public class ParticleLaserItem extends Particle {
    private final double otherX;
    private final double otherY;
    private final double otherZ;

    private final ItemStack stack;

    private ParticleLaserItem(ClientWorld world, double posX, double posY, double posZ, ItemStack stack, double motionY) {
        this(world, posX, posY, posZ, stack, motionY, 0, 0, 0);
    }

    public ParticleLaserItem(ClientWorld world, double posX, double posY, double posZ, ItemStack stack, double motionY, double otherX, double otherY, double otherZ) {
        super(world, posX + (world.random.nextDouble() - 0.5) / 8, posY, posZ + (world.random.nextDouble() - 0.5) / 8);
        this.stack = stack;
        this.otherX = otherX;
        this.otherY = otherY;
        this.otherZ = otherZ;

        this.xd = 0;
        this.yd = motionY;
        this.zd = 0;

        this.lifetime = 10;
        this.hasPhysics = false;
    }

    @Override
    public void remove() {
        super.remove();

        if (this.otherX != 0 || this.otherY != 0 || this.otherZ != 0) {
            this.level.addParticle(Factory.createData(this.stack, 0, 0, 0),
                    this.otherX, this.otherY, this.otherZ, 0, -0.025, 0);
        }
    }

    @Override
    public void render(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        IRenderTypeBuffer.Impl renderBuffer = mc.renderBuffers().bufferSource();
        Vector3d cam = renderInfo.getPosition();

        RenderSystem.pushMatrix();
        RenderHelper.turnBackOn();
        MatrixStack matrices = new MatrixStack();
        matrices.pushPose();

        matrices.translate(x - cam.x, y - cam.y, z - cam.z);
        matrices.scale(0.3F, 0.3F, 0.3F);

        double boop = Util.getMillis() / 600D;
        matrices.mulPose(Vector3f.YP.rotationDegrees((float) (boop * 40D % 360)));

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        float ageRatio = (float) this.age / (float) this.lifetime;
        float color = this.yd < 0
                ? 1F - ageRatio
                : ageRatio;
        RenderSystem.blendColor(color, color, color, color);

        int blockLight = level.getBrightness(LightType.BLOCK, new BlockPos(x, y, z));
        int skyLight = level.getBrightness(LightType.SKY, new BlockPos(x, y, z));
        AssetUtil.renderItemWithoutScrewingWithColors(this.stack, matrices, LightTexture.pack(blockLight, skyLight), OverlayTexture.NO_OVERLAY);

        RenderHelper.turnOff();
        matrices.popPose();
        RenderSystem.popMatrix();
        renderBuffer.endBatch();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements IParticleFactory<LaserItemParticleData> {
        public Factory(IAnimatedSprite sprite) {

        }

        @Override
        public Particle createParticle(LaserItemParticleData data, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ParticleLaserItem(worldIn, x, y, z, data.stack, ySpeed, data.outputX, data.outputY, data.outputZ);
        }

        public static IParticleData createData(ItemStack stack, double outputX, double outputY, double outputZ) {
            return new LaserItemParticleData(stack, outputX, outputY, outputZ);
        }
    }
}
