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

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL14;

public class ParticleLaserItem extends Particle {
    private final double otherX;
    private final double otherY;
    private final double otherZ;

    private final ItemStack stack;

    private ParticleLaserItem(ClientWorld world, double posX, double posY, double posZ, ItemStack stack, double motionY) {
        this(world, posX, posY, posZ, stack, motionY, 0, 0, 0);
    }

    public ParticleLaserItem(ClientWorld world, double posX, double posY, double posZ, ItemStack stack, double motionY, double otherX, double otherY, double otherZ) {
        super(world, posX + (world.rand.nextDouble() - 0.5) / 8, posY, posZ + (world.rand.nextDouble() - 0.5) / 8);
        this.stack = stack;
        this.otherX = otherX;
        this.otherY = otherY;
        this.otherZ = otherZ;

        this.motionX = 0;
        this.motionY = motionY;
        this.motionZ = 0;

        this.maxAge = 10;
        this.canCollide = false;
    }

    @Override
    public void setExpired() {
        super.setExpired();

        if (this.otherX != 0 || this.otherY != 0 || this.otherZ != 0) {
            Particle fx = new ParticleLaserItem(this.world, this.otherX, this.otherY, this.otherZ, this.stack, -0.025);
            Minecraft.getInstance().particles.addEffect(fx);
        }
    }

    @Override
    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        RenderSystem.pushMatrix();
        RenderHelper.enableStandardItemLighting();

        Vector3d cam = renderInfo.getProjectedView();
        RenderSystem.translated(this.posX - cam.getX(), this.posY - cam.getY(), this.posZ - cam.getZ());
        RenderSystem.scalef(0.3F, 0.3F, 0.3F);

        double boop = Util.milliTime();
        RenderSystem.rotatef((float) (boop * 40D % 360), 0, 1, 0);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param);

        float ageRatio = (float) this.age / (float) this.maxAge;
        float color = this.motionY < 0
            ? 1F - ageRatio
            : ageRatio;
        GL14.glBlendColor(color, color, color, color);

        AssetUtil.renderItemWithoutScrewingWithColors(this.stack);

        RenderHelper.disableStandardItemLighting();
        RenderSystem.popMatrix();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
